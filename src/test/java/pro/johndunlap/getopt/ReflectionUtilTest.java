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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pro.johndunlap.getopt.ReflectionUtil.isBoolean;
import static pro.johndunlap.getopt.ReflectionUtil.isByte;
import static pro.johndunlap.getopt.ReflectionUtil.isCharacter;
import static pro.johndunlap.getopt.ReflectionUtil.isDouble;
import static pro.johndunlap.getopt.ReflectionUtil.isFloat;
import static pro.johndunlap.getopt.ReflectionUtil.isInteger;
import static pro.johndunlap.getopt.ReflectionUtil.isLong;
import static pro.johndunlap.getopt.ReflectionUtil.isShort;
import static pro.johndunlap.getopt.ReflectionUtil.parse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pro.johndunlap.getopt.exception.ParseException;

/**
 * Tests for reflective utility methods.
 *
 * @author John Dunlap
 */
public class ReflectionUtilTest {

    @Test
    public void testGetNoArgConstructorMethodForListInterface() {
        assertNull(ReflectionUtil.getNoArgConstructor(List.class));
    }

    @Test
    public void testGetNoArgConstructorMethodForArrayListClass()
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = ReflectionUtil.getNoArgConstructor(ArrayList.class);
        assertNotNull(constructor);
        Object instance = constructor.newInstance();
        assertNotNull(instance);
        assertEquals(ArrayList.class, instance.getClass());
    }

    @Test
    public void testGetNoArgConstructorMethodForConcreteIntegerWhichHasNoArgsConstructor() {
        Constructor<?> constructor = ReflectionUtil.getNoArgConstructor(Integer.class);
        assertNull(constructor);
    }

    @Test
    public void testIsCollectionWithListInterface() {
        assertTrue(ReflectionUtil.isCollection(List.class));
    }

    @Test
    public void testIsCollectionWithArrayListImplementation() {
        assertTrue(ReflectionUtil.isCollection(ArrayList.class));
    }

    @Test
    public void testIsBooleanClassMethod() {
        assertTrue(isBoolean(Boolean.class));
        assertTrue(isBoolean(boolean.class));
        assertFalse(isBoolean(List.class));
        assertFalse(isBoolean(null));
    }

    @Test
    public void testIsIntegerClassMethod() {
        assertTrue(isInteger(Integer.class));
        assertTrue(isInteger(int.class));
        assertFalse(isInteger(List.class));
        assertFalse(isInteger(null));
    }

    @Test
    public void testIsShortClassMethod() {
        assertTrue(isShort(Short.class));
        assertTrue(isShort(short.class));
        assertFalse(isShort(List.class));
        assertFalse(isShort(null));
    }

    @Test
    public void testIsLongClassMethod() {
        assertTrue(isLong(Long.class));
        assertTrue(isLong(long.class));
        assertFalse(isLong(List.class));
        assertFalse(isLong(null));
    }

    @Test
    public void testIsFloatClassMethod() {
        assertTrue(isFloat(Float.class));
        assertTrue(isFloat(float.class));
        assertFalse(isFloat(List.class));
        assertFalse(isFloat(null));
    }

    @Test
    public void testIsDoubleClassMethod() {
        assertTrue(isDouble(Double.class));
        assertTrue(isDouble(double.class));
        assertFalse(isDouble(List.class));
        assertFalse(isDouble(null));
    }

    @Test
    public void testIsByteClassMethod() {
        assertTrue(isByte(Byte.class));
        assertTrue(isByte(byte.class));
        assertFalse(isByte(List.class));
        assertFalse(isByte(null));
    }

    @Test
    public void testIsCharacterClassMethod() {
        assertTrue(isCharacter(Character.class));
        assertTrue(isCharacter(char.class));
        assertFalse(isCharacter(List.class));
        assertFalse(isCharacter(null));
    }

    @Test(expected = ParseException.class)
    public void testParseMethodWithNullTypeAndNullValue() throws ParseException {
        parse(null, null);
    }

    @Test(expected = ParseException.class)
    public void testParseMethodWithNullTypeAndNonNullValue() throws ParseException {
        parse(null, "1");
    }

    @Test
    public void testParseMethodWithBooleanClassAndNullValue() throws ParseException {
        assertEquals(Boolean.FALSE, parse(Boolean.class, null));
    }

    @Test
    public void testParseMethodWithBooleanPrimitiveClassAndNullValue() throws ParseException {
        assertEquals(Boolean.FALSE, parse(boolean.class, null));
    }

    @Test
    public void testParseMethodWithBooleanClassAndNonNullValue() throws ParseException {
        assertEquals(Boolean.TRUE, parse(Boolean.class, "true"));
        assertEquals(Boolean.FALSE, parse(Boolean.class, "false"));
        assertEquals(Boolean.TRUE, parse(boolean.class, "true"));
        assertEquals(Boolean.FALSE, parse(boolean.class, "false"));
    }
}
