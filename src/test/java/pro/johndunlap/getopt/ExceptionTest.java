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

import pro.johndunlap.getopt.annotation.GetOptNamed;
import pro.johndunlap.getopt.exception.*;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class ExceptionTest {
    @Test(expected = InaccessibleFieldException.class)
    public void testInaccessibleFieldExceptionConstructorMessageThrowableClassType() throws InaccessibleFieldException {
        throw new InaccessibleFieldException("Message", new Exception(), Integer.class);
    }

    @Test(expected = MissingDefaultConstructorException.class)
    public void testMissingDefaultConstructorExceptionConstructorMessageThrowableClassType() throws MissingDefaultConstructorException {
        throw new MissingDefaultConstructorException("Message", new Exception(), Integer.class);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionConstructorFieldValueMessage() throws ParseException {
        Field field = NamedConfig.class.getDeclaredFields()[0];
        String value = "123";
        String message = "Message";
        ParseException npe = new ParseException(field, value, message);
        assertEquals(field, npe.getField());
        assertEquals(value, npe.getValue());
        assertEquals(message, npe.getMessage());
        throw npe;
    }

    @Test(expected = RethrownException.class)
    public void testRethrownRuntimeExceptionConstructorThrowable() throws RethrownException {
        throw new RethrownException(new Exception());
    }

    @Test(expected = UnsupportedTypeConversionException.class)
    public void testUnsupportedTypeConversionExceptionConstructorMessage() throws UnsupportedTypeConversionException {
        throw new UnsupportedTypeConversionException("Message");
    }

    @Test(expected = ParseException.class)
    public void testParseExceptionConstructorMessage() throws ParseException {
        throw new ParseException("Message");
    }

    @Test(expected = ParseException.class)
    public void testParseExceptionConstructorMessageThrowable() throws ParseException {
        throw new ParseException("Message", new Exception());
    }

    @Test(expected = ParseException.class)
    public void testParseExceptionConstructorThrowable() throws ParseException {
        throw new ParseException(new Exception());
    }

    @Test(expected = DuplicateOptionException.class)
    public void testDuplicateLongName() throws ParseException {
        String[] args = {"--value", "abc123"};
        DuplicateLongNameConfig config = GetOpt.bind(DuplicateLongNameConfig.class, args);
    }

    @Test(expected = DuplicateOptionException.class)
    public void testDuplicateShortName() throws ParseException {
        String[] args = {"--value", "abc123"};
        DuplicateShortNameConfig config = GetOpt.bind(DuplicateShortNameConfig.class, args);
    }

    private static class DuplicateLongNameConfig {
        @GetOptNamed(flag = "value")
        private String firstValue;
        @GetOptNamed(flag = "value")
        private String secondValue;

        public DuplicateLongNameConfig() {
        }

        public String getFirstValue() {
            return firstValue;
        }

        public DuplicateLongNameConfig setFirstValue(String firstValue) {
            this.firstValue = firstValue;
            return this;
        }

        public String getSecondValue() {
            return secondValue;
        }

        public DuplicateLongNameConfig setSecondValue(String secondValue) {
            this.secondValue = secondValue;
            return this;
        }
    }

    private static class DuplicateShortNameConfig {
        @GetOptNamed(code = 'V')
        private String firstValue;
        @GetOptNamed(code = 'V')
        private String secondValue;

        public DuplicateShortNameConfig() {
        }

        public String getFirstValue() {
            return firstValue;
        }

        public DuplicateShortNameConfig setFirstValue(String firstValue) {
            this.firstValue = firstValue;
            return this;
        }

        public String getSecondValue() {
            return secondValue;
        }

        public DuplicateShortNameConfig setSecondValue(String secondValue) {
            this.secondValue = secondValue;
            return this;
        }
    }

    private static class NamedConfig {
        private static String message;

        public NamedConfig() {
        }

        public static String getMessage() {
            return message;
        }

        public static void setMessage(String message) {
            NamedConfig.message = message;
        }
    }
}
