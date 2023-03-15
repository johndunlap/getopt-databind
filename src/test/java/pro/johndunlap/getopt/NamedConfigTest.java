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

import pro.johndunlap.getopt.config.NamedConfig;
import pro.johndunlap.getopt.exception.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamedConfigTest {
    @Test
    public void testBindMethodWithNoArguments() throws ParseException {
        String[] args = {};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
    }

    @Test
    public void testBindMethodWithLongValueLongName() throws ParseException {
        String[] args = {"--long-value", "8080"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(8080L, config.getLongValue().longValue());
    }

    @Test
    public void testBindMethodWithLongValueShortName() throws ParseException {
        String[] args = {"-L", "8080"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(8080L, config.getLongValue().longValue());
    }

    @Test
    public void testBindMethodWithIntegerValueLongName() throws ParseException {
        String[] args = {"--integer-value", "8080"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getIntegerValue().intValue());
    }

    @Test
    public void testBindMethodWithIntegerValueShortName() throws ParseException {
        String[] args = {"-I", "8080"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getIntegerValue().intValue());
    }

    @Test
    public void testBindMethodWithShortValueLongName() throws ParseException {
        String[] args = {"--short-value", "8080"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getShortValue().shortValue());
    }

    @Test
    public void testBindMethodWithShortValueShortName() throws ParseException {
        String[] args = {"-s", "8080"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getShortValue().shortValue());
    }

    @Test
    public void testBindMethodWithByteValueLongName() throws ParseException {
        String[] args = {"--byte-value", "64"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(64, config.getByteValue().byteValue());
    }

    @Test
    public void testBindMethodWithByteValueShortName() throws ParseException {
        String[] args = {"-b", "64"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(64, config.getByteValue().byteValue());
    }

    @Test
    public void testBindMethodWithDoubleValueLongName() throws ParseException {
        String[] args = {"--double-value", "3.12345"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(Double.valueOf(3.12345), config.getDoubleValue());
    }

    @Test
    public void testBindMethodWithDoubleValueShortName() throws ParseException {
        String[] args = {"-D", "3.12345"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(Double.valueOf(3.12345), config.getDoubleValue());
    }

    @Test
    public void testBindMethodWithFloatValueLongName() throws ParseException {
        String[] args = {"--float-value", "3.12345"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(Float.valueOf((float)3.12345), config.getFloatValue());
    }

    @Test
    public void testBindMethodWithFloatValueShortName() throws ParseException {
        String[] args = {"-F", "3.12345"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(Float.valueOf((float)3.12345), config.getFloatValue());
    }

    @Test
    public void testBindMethodWithStringValueLongName() throws ParseException {
        String[] args = {"--string-value", "abc123"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals("abc123", config.getStringValue());
    }

    @Test
    public void testBindMethodWithStringValueShortName() throws ParseException {
        String[] args = {"-S", "abc123"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals("abc123", config.getStringValue());
    }

    @Test
    public void testBindMethodWithBooleanValueLongName() throws ParseException {
        String[] args = {"--boolean-value"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getBooleanValue());
    }

    @Test
    public void testBindMethodWithBooleanValueShortName() throws ParseException {
        String[] args = {"-B"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getBooleanValue());
    }

    @Test
    public void testBindMethodWithCharacterValueLongName() throws ParseException {
        String[] args = {"--char-value", "A"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(Character.valueOf('A'), config.getCharValue());
    }

    @Test
    public void testBindMethodWithCharacterValueShortName() throws ParseException {
        String[] args = {"-C", "A"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(Character.valueOf('A'), config.getCharValue());
    }

    @Test
    public void testBindMethodWithLongHelpFlag() throws ParseException {
        String[] args = {"--help"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
    }

    @Test
    public void testBindMethodWithShortHelpFlag() throws ParseException {
        String[] args = {"-H"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
    }

    @Test
    public void testMultipleShortNameBooleans() throws ParseException {
        String[] args = {"-HB"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
        assertTrue(config.getBooleanValue());
    }

    @Test
    public void testMultipleBooleanShortNamesWithTrailingValue() throws ParseException {
        String[] args = {"-HBD", "3.14159265"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
        assertTrue(config.getBooleanValue());
        assertEquals(Double.valueOf(3.14159265), config.getDoubleValue());
    }

    @Test
    public void testMultipleBooleanShortNamesAtEndOfArray() throws ParseException {
        String[] args = {"--double-value", "3.14159265", "-HB"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
        assertTrue(config.getBooleanValue());
        assertEquals(Double.valueOf(3.14159265), config.getDoubleValue());
    }

    @Test
    public void testSuccessfullyParseNumericOptionsWithMaximumValues() throws ParseException {
        String[] args = {
                "--long-value", "9223372036854775807",
                "--integer-value", "2147483647",
                "--short-value", "32767",
                "--byte-value", "127",
                "--double-value", "1.7976931348623157E308",
                "--float-value", "3.4028235E38"
        };
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);

        assertEquals(Long.valueOf(Long.MAX_VALUE), config.getLongValue());
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), config.getIntegerValue());
        assertEquals(Short.valueOf(Short.MAX_VALUE), config.getShortValue());
        assertEquals(Byte.valueOf(Byte.MAX_VALUE), config.getByteValue());
        assertEquals(Double.valueOf(Double.MAX_VALUE), config.getDoubleValue());
        assertEquals(Float.valueOf(Float.MAX_VALUE), config.getFloatValue());
    }

    @Test
    public void testParseMultipleCombinedShortNames() throws ParseException {
        String[] args = {"-HBs", "1234"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);

        assertEquals(Short.valueOf((short) 1234), config.getShortValue());
        assertTrue(config.getBooleanValue());
        assertTrue(config.getHelp());
    }

    @Test
    public void testBooleanDefaultFalse() throws ParseException {
        String[] args = {};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertFalse(config.getBooleanValue());
        assertFalse(config.getHelp());
    }

    @Test
    public void testHelpMethod() {
        String expectedOutput = "This is the opening description\n" +
                "  -B  --boolean-value  A boolean value\n" +
                "  -C  --char-value     A single character\n" +
                "  -H  --help           Displays this help message\n" +
                "\n" +
                "Type tests:\n" +
                "  -L  --long-value     A long value (required)\n" +
                "  -I  --integer-value  A integer value (required)\n" +
                "  -s  --short-value    A long value (required)\n" +
                "  -b  --byte-value     A byte value (required)\n" +
                "  -D  --double-value   A double value\n" +
                "  -F  --float-value    A float value\n" +
                "  -S  --string-value   A string value\n" +
                "  -l  --string-list    A list of strings\n" +
                "This is the closing description";

        String actual = GetOpt.help(NamedConfig.class);
        assertEquals(expectedOutput, actual);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithLongField() throws ParseException {
        String[] args = {"--long-value", "abc"};
        GetOpt.bind(NamedConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithIntegerField() throws ParseException {
        String[] args = {"--integer-value", "abc"};
        GetOpt.bind(NamedConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithShortField() throws ParseException {
        String[] args = {"--short-value", "abc"};
        GetOpt.bind(NamedConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithByteField() throws ParseException {
        String[] args = {"--byte-value", "abc"};
        GetOpt.bind(NamedConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithDoubleField() throws ParseException {
        String[] args = {"--double-value", "abc"};
        GetOpt.bind(NamedConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithFloatField() throws ParseException {
        String[] args = {"--float-value", "abc"};
        GetOpt.bind(NamedConfig.class, args);
    }

    @Test
    public void testVerifyIgnoreAnnotatedBooleanNotDefaultedToFalse() throws ParseException {
        String[] args = {};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertFalse(config.getBooleanValue());
        assertFalse(config.getHelp());
        assertNull(config.getIgnoredBoolean());
    }

    @Test
    public void testAddNamedOptionToList() throws ParseException {
        String[] args = {"-l", "one", "-l", "two", "-l", "three"};
        NamedConfig config = GetOpt.bind(NamedConfig.class, args);
        assertNotNull(config);
        assertEquals(3, config.getStringList().size());
        assertEquals("one", config.getStringList().get(0));
        assertEquals("two", config.getStringList().get(1));
        assertEquals("three", config.getStringList().get(2));
    }
}