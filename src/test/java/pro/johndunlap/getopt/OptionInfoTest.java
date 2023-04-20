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

import java.lang.reflect.Field;
import org.junit.Test;
import pro.johndunlap.getopt.annotation.Ignore;

/**
 * Tests for the {@link OptionInfo} class.
 */
public class OptionInfoTest {
    @Test(expected = RuntimeException.class)
    public void testConstructorWithIgnoredField() throws NoSuchFieldException {
        Field field = ConfigWithIgnoredField.class.getDeclaredField("two");
        new OptionInfo(field);
    }

    @Test
    public void testConstructorWithDoublePrimitiveField() throws NoSuchFieldException {
        Field field = ConfigWithPrimitives.class.getDeclaredField("eight");
        OptionInfo info = new OptionInfo(field);
        assertEquals("eight", info.getFlag());
    }

    @Test
    public void testConstructorWithFloatPrimitiveField() throws NoSuchFieldException {
        Field field = ConfigWithPrimitives.class.getDeclaredField("seven");
        OptionInfo info = new OptionInfo(field);
        assertEquals("seven", info.getFlag());
    }

    @Test
    public void testConstructorWithCharPrimitiveField() throws NoSuchFieldException {
        Field field = ConfigWithPrimitives.class.getDeclaredField("three");
        OptionInfo info = new OptionInfo(field);
        assertEquals("three", info.getFlag());
    }

    private static class ConfigWithIgnoredField {
        private String one;

        @Ignore
        private String two;
    }

    private static class ConfigWithPrimitives {
        private boolean one;
        private byte two;
        private char three;
        private short four;
        private int five;
        private long six;
        private float seven;
        private double eight;
    }
}
