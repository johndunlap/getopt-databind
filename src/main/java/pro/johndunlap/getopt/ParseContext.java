/*
 * The MIT License (MIT)
 *
 * Copyright © 2022
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pro.johndunlap.getopt;

import pro.johndunlap.getopt.annotation.GetOptIgnore;
import pro.johndunlap.getopt.annotation.GetOptNamed;
import pro.johndunlap.getopt.annotation.GetOptOrdered;
import pro.johndunlap.getopt.exception.*;

import java.lang.reflect.Field;
import java.util.*;

import static java.lang.String.format;

public class ParseContext<T> {
    private final Map<String, Field> fields = new HashMap<>();
    private final List<Field> orderedFields = new ArrayList<>();
    private final Stack<String> queue;
    private final T instance;
    private String currentName;
    private int currentOrderedIndex = 0;

    public ParseContext(Class<T> classType, String[] args) throws ParseException {
        this.queue = new Stack<>();

        // Add the string args to the stack in reverse order
        for (int i = args.length - 1; i >= 0; i--) {
            this.queue.push(args[i]);
        }

        // Attempt to construct the instance which will be returned
        try {
            this.instance = classType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new MissingDefaultConstructorException(format("Class %s must have a public default constructor", classType.getCanonicalName()), e, classType);
        }

        // Associate flag names with class fields
        for (Field field : classType.getDeclaredFields()) {
            if (field.getAnnotation(GetOptIgnore.class) != null) {
                continue;
            }

            if (field.getAnnotation(GetOptOrdered.class) != null) {
                orderedFields.add(field);
            } else {
                GetOptNamed namedOption = field.getAnnotation(GetOptNamed.class);

                // Initialize boolean fields to false by default
                if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    // TODO: Use a unified setter mechanism that attempts to use a setter method
                    field.setAccessible(true);
                    try {
                        field.set(instance, false);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (namedOption != null) {
                    if (!namedOption.longName().equals("")) {
                        if (!fields.containsKey(namedOption.longName())) {
                            fields.put(namedOption.longName(), field);
                        } else {
                            throw new DuplicateOptionException("Duplicate option name: " + namedOption.longName(), field);
                        }

                    } if (namedOption.shortName() != ' ') {
                        if (!fields.containsKey(namedOption.shortName() + "")) {
                            fields.put(namedOption.shortName() + "", field);
                        } else {
                            throw new DuplicateOptionException("Duplicate option name: " + namedOption.shortName(), field);
                        }
                    }
                } else {
                    // Attempt to infer usable long and short names from the field name
                    String longName = Parser.camelCaseToHyphenCase(field.getName());
                    String shortName = field.getName().charAt(0) + "";

                    if (!fields.containsKey(longName)) {
                        fields.put(longName, field);
                    } if (!fields.containsKey(shortName)) {
                        fields.put(shortName, field);
                    }
                }
            }
        }

        // Sort the ordered fields
        orderedFields.sort((f1, f2) -> {
            GetOptOrdered f1o = f1.getAnnotation(GetOptOrdered.class);
            GetOptOrdered f2o = f2.getAnnotation(GetOptOrdered.class);
            return Integer.compare(f1o.order(), f2o.order());
        });
    }

    public ParseContext<T> setCurrentName(String currentName) {
        this.currentName = currentName;
        return this;
    }

    public T getInstance() {
        return instance;
    }

    public Stack<String> getQueue() {
        return queue;
    }

    public void setOrderedValue(String value) throws ParseException {
        try {
            Field field = orderedFields.get(currentOrderedIndex++);

            // TODO: Use setter methods if one exists

            field.setAccessible(true);
            field.set(instance, parse(field, value, field.getType()));
        } catch (RuntimeException|IllegalAccessException e) {
            throw new InaccessibleFieldException(format("Failed to set value %s for flag %s", value, currentName), e, instance.getClass());
        }
    }

    public void setNamedValue(String value) throws ParseException {
        try {
            Field field = fields.get(currentName);

            // Quietly return if the field cannot be found. This may be the result of the user passing the wrong flag
            if (field == null) {
                return;
            }

            // TODO: Use setter methods if one exists

            // We couldn't find a setter method so inject the value reflectively
            field.setAccessible(true);
            field.set(instance, parse(field, value, field.getType()));
        } catch (RuntimeException|IllegalAccessException e) {
            throw new InaccessibleFieldException(format("Failed to set value %s for flag %s", value, currentName), e, instance.getClass());
        }
    }

    private Object parse(Field field, String value, Class<?> type) throws ParseException {
        Object parsed = null;

        try {
            GetOptNamed named = field.getAnnotation(GetOptNamed.class);
            Class<? extends ValueParser<?>> valueParser = null;

            if (named != null && !named.parser().equals(DefaultValueParser.class)) {
                valueParser = named.parser();
            }

            if (type.equals(String.class)) {
                return value;
            } if (type.equals(Integer.class) || type.equals(int.class)) {
                parsed = Integer.parseInt(value);
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                parsed = Short.parseShort(value);
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                parsed = Long.parseLong(value);
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                parsed = Float.parseFloat(value);
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                parsed = Double.parseDouble(value);
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                parsed = Byte.parseByte(value);
            } else if (type.equals(Character.class) || type.equals(char.class)) {
                // TODO: Raise an exception if multiple characters are assigned to a char
                parsed = value.charAt(0);
            } else if (isBoolean(type)) {
                if (value == null) {
                    return false;
                } else {
                    return Boolean.parseBoolean(value);
                }
            } else if (valueParser != null) {
                try {
                    parsed = valueParser.getConstructor().newInstance().parse(value);
                } catch (Exception e) {
                    throw new RethrownException(e);
                }
            } else {
                throw new UnsupportedTypeConversionException("Unsupported type: " + type.getCanonicalName());
            }

            return parsed;
        } catch (NumberFormatException e) {
            throw new NumericParseException(field, value, format(
                "The value %s could not be assigned to the field %s because it cannot be parsed into an integer",
                value,
                field.getName()
            ));
        }
    }

    private static boolean isBoolean(Class<?> type) {
        return type.equals(Boolean.class) || type.equals(boolean.class);
    }

    public boolean isBoolean() {
        Field field = fields.get(currentName);

        if (field == null) {
            return false;
        }

        return isBoolean(field.getType());
    }
}
