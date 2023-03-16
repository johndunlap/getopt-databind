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

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
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
                    if (!namedOption.flag().equals("")) {
                        if (!fields.containsKey(namedOption.flag())) {
                            fields.put(namedOption.flag(), field);
                        } else {
                            throw new DuplicateOptionException("Duplicate option name: " + namedOption.flag(), field);
                        }

                    } if (namedOption.code() != ' ') {
                        if (!fields.containsKey(namedOption.code() + "")) {
                            fields.put(namedOption.code() + "", field);
                        } else {
                            throw new DuplicateOptionException("Duplicate option name: " + namedOption.code(), field);
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

    public void setOrderedValue(String stringValue) throws ParseException {
        int orderedIndex = currentOrderedIndex;
        try {
            Field field = orderedFields.get(currentOrderedIndex++);

            GetOptOrdered ordered = field.getAnnotation(GetOptOrdered.class);

            // Bean properties without this annotation are considered to be named properties not ordered properties
            if (ordered == null) {
                throw new NullPointerException(GetOptOrdered.class.getName() + " is missing. This should never happen");
            }

            Class<? extends ValueParser<?>> valueParser = null;

            if (!ordered.parser().equals(DefaultValueParser.class)) {
                valueParser = ordered.parser();
            }

            Class<?> fieldType = field.getType();
            Object existingValue = ReflectionUtil.getFieldValue(field, instance);

            // Are we dealing with a collection?
            if (Collection.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
                Object parsedValue = parse(stringValue, ordered.collectionType(), valueParser);

                // Add a value to the collection
                existingValue = addToCollection(field, existingValue, fieldType, ordered.collectionType(), parsedValue);

                // Overwrite the collection in the instance
                ReflectionUtil.setFieldValue(field, instance, existingValue);
            } else {
                Object parsedValue = parse(stringValue, fieldType, valueParser);
                ReflectionUtil.setFieldValue(field, instance, parsedValue);
            }
        } catch (RuntimeException|IllegalAccessException e) {
            e.printStackTrace();
            throw new InaccessibleFieldException(format("Failed to set value %s for position %s", stringValue, orderedIndex), e, instance.getClass());
        }
    }

    /**
     * This is broken out into its own function because the annotations required for silencing
     * the warnings cannot be used at the statement level.
     * @param collection The collection which will be added to
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Object addToCollection(Field field, Object collection, Class<?> collectionType, Class<?> elementType, Object parsedValue) {
        // Attempt to initialize the collection if it is null
        if (collection == null) {
            if (Collection.class.isAssignableFrom(collectionType)) {
                // Instantiate a new collection if possible. Note that this will not be possible in all cases
                //  because, outside built-in collections, it is not feasible to determine which concrete collection
                //  type to instantiate. In this case, an exception should be thrown.
                if (List.class.isAssignableFrom(collectionType)) {
                    collection = new ArrayList<>();
                } else if (Set.class.isAssignableFrom(collectionType)) {
                    collection = new HashSet<>();
                } else if (Queue.class.isAssignableFrom(collectionType)) {
                    collection = new LinkedList<>();
                } else {
                    throw new AssertionError(collectionType.getCanonicalName()
                            + " is not a supported collection type. To work around this, please initialize"
                            + " " + field + " with an empty collection.");
                }

                ((Collection) collection).add(parsedValue);
            } else if (collectionType.isArray()) {
                collection = Array.newInstance(elementType, 1);
                ((Object[]) collection)[0] = parsedValue;
            }
        } else {
            if (Collection.class.isAssignableFrom(collectionType)) {
                ((Collection) collection).add(parsedValue);
            } else if (collectionType.isArray()) {
                List<Object> tmpList = new ArrayList<>(Arrays.asList((Object[]) collection));
                tmpList.add(parsedValue);
                collection = tmpList.toArray(new Object[0]);
            }
        }

        // Return the updated collection
        return collection;
    }

    public void setNamedValue(String value) throws ParseException {
        try {
            Field field = fields.get(currentName);

            // Quietly return if the field cannot be found. This may be the result of the user passing the wrong flag
            if (field == null) {
                return;
            }

            // TODO: Is there a way to do this without querying the annotation again?
            GetOptNamed named = field.getAnnotation(GetOptNamed.class);
            Class<? extends ValueParser<?>> valueParser = null;

            if (named != null && !named.parser().equals(DefaultValueParser.class)) {
                valueParser = named.parser();
            }

            Class<?> fieldType = field.getType();
            Object existingValue = ReflectionUtil.getFieldValue(field, instance);

            // Are we dealing with a collection?
            if (Collection.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
                // It is not possible to add an element to a collection without this annotation because we need to know
                // what type the collection contains
                if (named == null) {
                    throw new NullPointerException(GetOptNamed.class.getName() + " is missing. This should never happen");
                }

                Object parsedValue = parse(value, named.collectionType(), valueParser);

                // Add a value to the collection
                existingValue = addToCollection(field, existingValue, fieldType, named.collectionType(), parsedValue);

                // Overwrite the collection in the instance
                ReflectionUtil.setFieldValue(field, instance, existingValue);
            } else {
                ReflectionUtil.setFieldValue(field, instance, parse(value, field.getType(), valueParser));
            }
        } catch (RuntimeException|IllegalAccessException e) {
            throw new InaccessibleFieldException(format("Failed to set value %s for flag %s", value, currentName), e, instance.getClass());
        }
    }

    private Object parse(String value, Class<?> fieldType, Class<? extends ValueParser<?>> valueParser) throws ParseException {
        Object parsed = null;

        try {
            if (fieldType.equals(String.class)) {
                return value;
            }

            if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                parsed = Integer.parseInt(value);
            } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
                parsed = Short.parseShort(value);
            } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                parsed = Long.parseLong(value);
            } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                parsed = Float.parseFloat(value);
            } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                parsed = Double.parseDouble(value);
            } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
                parsed = Byte.parseByte(value);
            } else if (fieldType.equals(BigInteger.class)) {
                parsed = new BigInteger(value);
            } else if (fieldType.equals(BigDecimal.class)) {
                parsed = new BigDecimal(value);
            }

            else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
                // Throw an exception if the wrong number of characters are passed
                if (value == null || value.length() != 1) {
                    throw new ParseException(value, format("Value %s must contain exactly one character", value));
                }

                parsed = value.charAt(0);
            } else if (isBoolean(fieldType)) {
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
                throw new UnsupportedTypeConversionException("Unsupported type: " + fieldType.getCanonicalName());
            }

            return parsed;
        } catch (ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException(value, format(
                "Failed to parse string %s into an instance of class %s",
                value,
                fieldType
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
