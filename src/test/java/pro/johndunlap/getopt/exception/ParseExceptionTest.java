package pro.johndunlap.getopt.exception;

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
import pro.johndunlap.getopt.annotation.Arg;

/**
 * Tests for the {@link ParseException} class.
 */
public class ParseExceptionTest {
    @Test
    public void testGetExitStatusMethodWithDefaultExitStatus() throws NoSuchFieldException {
        Field field = ConfigTest.class.getDeclaredField("one");
        ParseException exception = new ParseException(field, "a", "This didn't work as expected");
        assertEquals(1, exception.getExitStatus());
    }

    @Test
    public void testGetExitStatusMethodWithConfiguredExitStatus() throws NoSuchFieldException {
        Field field = ConfigTest.class.getDeclaredField("two");
        ParseException exception = new ParseException(field, "a", "This didn't work as expected");
        assertEquals(7, exception.getExitStatus());
    }

    private static class ConfigTest {
        private String one;

        @Arg(exitStatus = 7)
        private String two;
    }
}