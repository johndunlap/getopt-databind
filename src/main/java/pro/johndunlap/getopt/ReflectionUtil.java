package pro.johndunlap.getopt;

/*-
 * #%L
 * getopt-databind
 * %%
 * Copyright (C) 2023 John Dunlap
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import pro.johndunlap.getopt.exception.ParseException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import static java.lang.String.format;

public class ReflectionUtil {

    private ReflectionUtil() {
        throw new RuntimeException("This class cannot be instantiated");
    }

    /**
     * This method attempts to get the value of a field without violating its declared access modifiers. However, if it
     * is unable to do so, it will override the access modifiers and try again. If available, getter methods will be
     * used. Direct field access will only be used as a last resort. However, if there is a public field with a private
     * getter (unlikely), then public field will be used. If both the field and the getter are private, the getter will
     * be used.
     *
     * @param field The field from which the value should be taken
     * @param instance The instance from which the value should be taken
     * @return The value of the field
     * @throws IllegalAccessException Thrown if the field or method is inaccessible
     */
    public static Object getFieldValue(Field field, Object instance) throws IllegalAccessException {
        Method getterMethod = findGetterMethod(field);

        try {
            // Try public getter method first
            return getterMethod.invoke(instance);
        } catch (NullPointerException| InvocationTargetException |IllegalAccessException e1) {
            try {
                // Next, try public field
                return field.get(instance);
            } catch (IllegalAccessException e2) {
                try {
                    // Next try private getter method
                    getterMethod.setAccessible(true);
                    return getterMethod.invoke(instance);
                } catch (NullPointerException|InvocationTargetException|IllegalAccessException e3) {
                    // Finally, try private field
                    field.setAccessible(true);
                    return field.get(instance);
                }
            }
        }
    }

    /**
     * This method attempts to set the value of a field without violating its declared access modifiers. However, if it
     * is unable to do so, it will override the access modifiers and try again. If available, setter methods will be
     * used. Direct field access will only be used as a last resort. However, if there is a public field with a private
     * setter (unlikely), then public field will be used. If both the field and the setter are private, the setter will
     * be used.
     *
     * @param field The field to which the value should be set
     * @param instance The instance to which the value should be set
     * @param value The value to set
     * @throws IllegalAccessException Thrown if the field or method is inaccessible
     */
    public static void setFieldValue(Field field, Object instance, Object value) throws IllegalAccessException {
        Method setterMethod = findSetterMethod(field);

        try {
            // First, try public setter method
            setterMethod.invoke(instance, value);
        } catch (NullPointerException|IllegalAccessException|InvocationTargetException e1) {
            try {
                // Next, try public field
                field.set(instance, value);
            }

            catch (IllegalAccessException e2) {
                try {
                    // Next, try private setter method
                    setterMethod.setAccessible(true);
                    setterMethod.invoke(instance, value);
                } catch (NullPointerException|IllegalAccessException|InvocationTargetException e3) {
                    // Finally, try private field
                    field.setAccessible(true);
                    field.set(instance, value);
                }
            }
        }
    }

    public static Method findSetterMethod(Field field) {
        String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Method setterMethod;

        // Attempt to find a setter method for the given field
        try {
            Class<?> declaringClass = field.getDeclaringClass();
            Class<?> type = field.getType();
            setterMethod = declaringClass.getDeclaredMethod(setterName, type);
        } catch (NoSuchMethodException e) {
            setterMethod = null;
        }

        return setterMethod;
    }

    public static Method findGetterMethod(Field field) {
        String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Method getterMethod;

        // Attempt to find a setter method for the given field
        try {
            Class<?> declaringClass = field.getDeclaringClass();
            getterMethod = declaringClass.getDeclaredMethod(getterName);
        } catch (NoSuchMethodException e) {
            getterMethod = null;
        }

        return getterMethod;
    }

    public static Constructor<?> getNoArgConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> noArgConstructor = null;

        // Attempt to find the no-arg constructor
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                noArgConstructor = constructor;
                break;
            }
        }

        return noArgConstructor;
    }

    public static boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    public boolean isArray(Class<?> clazz) {
        return clazz.isArray();
    }

    public static Object instantiateCollection(Class<Collection<?>> collectionClass) {
        throw new RuntimeException("IMPLEMENT ME");
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(Class<T> fieldType, String value) throws ParseException {
        if (fieldType == null) {
            throw new ParseException("Field type cannot be null");
        }

        if (fieldType.equals(String.class)) {
            return (T) value;
        } else if (isInteger(fieldType)) {
            return (T) Integer.valueOf(Integer.parseInt(value));
        } else if (isShort(fieldType)) {
            return (T) Short.valueOf(Short.parseShort(value));
        } else if (isLong(fieldType)) {
            return (T) Long.valueOf(Long.parseLong(value));
        } else if (isFloat(fieldType)) {
            return (T) Float.valueOf(Float.parseFloat(value));
        } else if (isDouble(fieldType)) {
            return (T) Double.valueOf(Double.parseDouble(value));
        } else if (isByte(fieldType)) {
            return (T) Byte.valueOf(Byte.parseByte(value));
        } else if (fieldType.equals(BigInteger.class)) {
            return (T) new BigInteger(value);
        } else if (fieldType.equals(BigDecimal.class)) {
            return (T) new BigDecimal(value);
        } else if (isCharacter(fieldType)) {
            // Throw an exception if the wrong number of characters are passed
            if (value == null || value.length() != 1) {
                throw new ParseException(value, format("Value \"%s\" must be exactly one character in length", value));
            }

            return (T) Character.valueOf(value.charAt(0));
        } else if (isBoolean(fieldType)) {
            if (value == null) {
                return (T) Boolean.valueOf(false);
            } else {
                return (T) Boolean.valueOf(Boolean.parseBoolean(value));
            }
        }

        throw new ParseException("Unable to parse value " + value + " into type " + fieldType);
    }

    public static boolean isBoolean(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Boolean.class) || type.equals(boolean.class);
    }

    public static boolean isInteger(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Integer.class) || type.equals(int.class);
    }

    public static boolean isShort(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Short.class) || type.equals(short.class);
    }

    public static boolean isLong(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Long.class) || type.equals(long.class);
    }

    public static boolean isFloat(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Float.class) || type.equals(float.class);
    }

    public static boolean isDouble(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Double.class) || type.equals(double.class);
    }

    public static boolean isByte(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Byte.class) || type.equals(byte.class);
    }

    public static boolean isCharacter(Class<?> type) {
        if (type == null) {
            return false;
        }

        return type.equals(Character.class) || type.equals(char.class);
    }
}
