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

import java.util.List;
import org.junit.Test;
import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptIgnore;
import pro.johndunlap.getopt.annotation.GetOptNamed;
import pro.johndunlap.getopt.exception.ParseException;

/**
 * Tests for the {@link GetOptNamed} annotation.
 *
 * @author John Dunlap
 */
public class GetOptNamedTest {
    @Test
    public void testBindMethodWithNoArguments() throws ParseException {
        String[] args = {};
        StringConfigNotRequired config = new GetOpt().read(StringConfigNotRequired.class, args);
        assertNotNull(config);
        assertNull(config.getStringValue());
    }

    @Test
    public void testBindMethodWithLongValueLongName() throws ParseException {
        String[] args = {"--long-value", "8080"};
        LongConfigNotRequired config = new GetOpt().read(LongConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(8080L, config.getLongValue().longValue());
    }

    @Test
    public void testBindMethodWithLongValueShortName() throws ParseException {
        String[] args = {"-L", "8080"};
        LongConfigNotRequired config = new GetOpt().read(LongConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(8080L, config.getLongValue().longValue());
    }

    @Test
    public void testBindMethodWithIntegerValueLongName() throws ParseException {
        String[] args = {"--integer-value", "8080"};
        IntegerConfigNotRequired config = new GetOpt().read(IntegerConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getIntegerValue().intValue());
    }

    @Test
    public void testBindMethodWithIntegerValueShortName() throws ParseException {
        String[] args = {"-I", "8080"};
        IntegerConfigNotRequired config = new GetOpt().read(IntegerConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getIntegerValue().intValue());
    }

    @Test
    public void testBindMethodWithShortValueLongName() throws ParseException {
        String[] args = {"--short-value", "8080"};
        ShortConfigNotRequired config = new GetOpt().read(ShortConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getShortValue().shortValue());
    }

    @Test
    public void testBindMethodWithShortValueShortName() throws ParseException {
        String[] args = {"-s", "8080"};
        ShortConfigNotRequired config = new GetOpt().read(ShortConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getShortValue().shortValue());
    }

    @Test
    public void testBindMethodWithByteValueLongName() throws ParseException {
        String[] args = {"--byte-value", "64"};
        ByteConfigNotRequired config = new GetOpt().read(ByteConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(64, config.getByteValue().byteValue());
    }

    @Test
    public void testBindMethodWithByteValueShortName() throws ParseException {
        String[] args = {"-b", "64"};
        ByteConfigNotRequired config = new GetOpt().read(ByteConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(64, config.getByteValue().byteValue());
    }

    @Test
    public void testBindMethodWithDoubleValueLongName() throws ParseException {
        String[] args = {"--double-value", "3.12345"};
        DoubleConfigNotRequired config = new GetOpt().read(DoubleConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(Double.valueOf(3.12345), config.getDoubleValue());
    }

    @Test
    public void testBindMethodWithDoubleValueShortName() throws ParseException {
        String[] args = {"-D", "3.12345"};
        DoubleConfigNotRequired config = new GetOpt().read(DoubleConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(Double.valueOf(3.12345), config.getDoubleValue());
    }

    @Test
    public void testBindMethodWithFloatValueLongName() throws ParseException {
        String[] args = {"--float-value", "3.12345"};
        FloatConfigNotRequired config = new GetOpt().read(FloatConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(Float.valueOf((float) 3.12345), config.getFloatValue());
    }

    @Test
    public void testBindMethodWithFloatValueShortName() throws ParseException {
        String[] args = {"-F", "3.12345"};
        FloatConfigNotRequired config = new GetOpt().read(FloatConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(Float.valueOf((float) 3.12345), config.getFloatValue());
    }

    @Test
    public void testBindMethodWithStringValueLongName() throws ParseException {
        String[] args = {"--string-value", "abc123"};
        StringConfigNotRequired config = new GetOpt().read(StringConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals("abc123", config.getStringValue());
    }

    @Test
    public void testBindMethodWithStringValueShortName() throws ParseException {
        String[] args = {"-S", "abc123"};
        StringConfigNotRequired config = new GetOpt().read(StringConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals("abc123", config.getStringValue());
    }

    @Test
    public void testBindMethodWithBooleanValueLongName() throws ParseException {
        String[] args = {"--boolean-value"};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertTrue(config.getBooleanValue());
    }

    @Test
    public void testBindMethodWithBooleanValueShortName() throws ParseException {
        String[] args = {"-B"};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertTrue(config.getBooleanValue());
    }

    @Test
    public void testBindMethodWithCharacterValueLongName() throws ParseException {
        String[] args = {"--character-value", "A"};
        CharacterConfigNotRequired config = new GetOpt().read(CharacterConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(Character.valueOf('A'), config.getCharacterValue());
    }

    @Test
    public void testBindMethodWithCharacterValueShortName() throws ParseException {
        String[] args = {"-C", "A"};
        CharacterConfigNotRequired config = new GetOpt().read(CharacterConfigNotRequired.class, args);
        assertNotNull(config);
        assertEquals(Character.valueOf('A'), config.getCharacterValue());
    }

    @Test
    public void testBindMethodWithLongHelpFlag() throws ParseException {
        String[] args = {"--help"};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
    }

    @Test
    public void testBindMethodWithShortHelpFlag() throws ParseException {
        String[] args = {"-H"};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
    }

    @Test
    public void testMultipleShortNameBooleans() throws ParseException {
        String[] args = {"-HB"};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
        assertTrue(config.getBooleanValue());
    }

    @Test
    public void testMultipleBooleanShortNamesWithTrailingValue() throws ParseException {
        String[] args = {"-HBD", "3.14159265"};
        TwoBooleanAndDouble config = new GetOpt().read(TwoBooleanAndDouble.class, args);
        assertNotNull(config);
        assertTrue(config.getHelp());
        assertTrue(config.getBooleanValue());
        assertEquals(Double.valueOf(3.14159265), config.getDoubleValue());
    }

    @Test
    public void testMultipleBooleanShortNamesAtEndOfArray() throws ParseException {
        String[] args = {"--double-value", "3.14159265", "-HB"};
        TwoBooleanAndDouble config = new GetOpt().read(TwoBooleanAndDouble.class, args);
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
        HelpConfig config = new GetOpt().read(HelpConfig.class, args);
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
        TwoBooleanAndShort config = new GetOpt().read(TwoBooleanAndShort.class, args);
        assertNotNull(config);

        assertEquals(Short.valueOf((short) 1234), config.getShortValue());
        assertTrue(config.getBooleanValue());
        assertTrue(config.getHelp());
    }

    @Test
    public void testBooleanDefaultFalse() throws ParseException {
        String[] args = {};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertFalse(config.getBooleanValue());
        assertFalse(config.getHelp());
    }

    @Test
    public void testHelpMethod() {
        String expectedOutput = "This is the opening description\n"
                + "  -B  --boolean-value  A boolean value\n"
                + "  -C  --char-value     A single character\n"
                + "  -H  --help           Displays this help message\n"
                + "\n"
                + "Type tests:\n"
                + "  -L  --long-value     A long value (required)\n"
                + "  -I  --integer-value  A integer value (required)\n"
                + "  -s  --short-value    A long value (required)\n"
                + "  -b  --byte-value     A byte value (required)\n"
                + "  -D  --double-value   A double value\n"
                + "  -F  --float-value    A float value\n"
                + "  -S  --string-value   A string value\n"
                + "  -l  --string-list    A list of strings\n"
                + "This is the closing description";

        String actual = new GetOpt().help(HelpConfig.class);
        assertEquals(expectedOutput, actual);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithLongField() throws ParseException {
        String[] args = {"--long-value", "abc"};
        new GetOpt().read(HelpConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithIntegerField() throws ParseException {
        String[] args = {"--integer-value", "abc"};
        new GetOpt().read(HelpConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithShortField() throws ParseException {
        String[] args = {"--short-value", "abc"};
        new GetOpt().read(HelpConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithByteField() throws ParseException {
        String[] args = {"--byte-value", "abc"};
        new GetOpt().read(HelpConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithDoubleField() throws ParseException {
        String[] args = {"--double-value", "abc"};
        new GetOpt().read(HelpConfig.class, args);
    }

    @Test(expected = ParseException.class)
    public void testNumericParseExceptionWithFloatField() throws ParseException {
        String[] args = {"--float-value", "abc"};
        new GetOpt().read(HelpConfig.class, args);
    }

    @Test
    public void testVerifyIgnoreAnnotatedBooleanNotDefaultedToFalse() throws ParseException {
        String[] args = {};
        BooleanConfigNotRequired config = new GetOpt().read(BooleanConfigNotRequired.class, args);
        assertNotNull(config);
        assertFalse(config.getBooleanValue());
        assertFalse(config.getHelp());
        assertNull(config.getIgnoredBoolean());
    }

    @Test
    public void testAddNamedOptionToList() throws ParseException {
        String[] args = {"-l", "one", "-l", "two", "-l", "three"};
        StringListNotRequired config = new GetOpt().read(StringListNotRequired.class, args);
        assertNotNull(config);
        assertEquals(3, config.getStringList().size());
        assertEquals("one", config.getStringList().get(0));
        assertEquals("two", config.getStringList().get(1));
        assertEquals("three", config.getStringList().get(2));
    }

    @GetOptHelp(
            openingText = "This is the opening description",
            closingText = "This is the closing description"
    )
    private static class HelpConfig {
        @GetOptNamed(
                code = 'L',
                flag = "long-value",
                required = true,
                description = "A long value",
                category = "Type tests"
        )
        private Long longValue;
        @GetOptNamed(
                code = 'I',
                flag = "integer-value",
                required = true,
                description = "A integer value",
                category = "Type tests"
        )
        private Integer integerValue;
        @GetOptNamed(
                code = 's',
                flag = "short-value",
                required = true,
                description = "A long value",
                category = "Type tests"
        )
        private Short shortValue;
        @GetOptNamed(
                code = 'b',
                flag = "byte-value",
                required = true,
                description = "A byte value",
                category = "Type tests"
        )
        private Byte byteValue;
        @GetOptNamed(
                code = 'D',
                flag = "double-value",
                description = "A double value",
                category = "Type tests"
        )
        private Double doubleValue;
        @GetOptNamed(
                code = 'F',
                flag = "float-value",
                description = "A float value",
                category = "Type tests"
        )
        private Float floatValue;
        @GetOptNamed(
                code = 'S',
                flag = "string-value",
                description = "A string value",
                category = "Type tests"
        )
        private String stringValue;
        @GetOptNamed(
                code = 'B',
                flag = "boolean-value",
                description = "A boolean value"
        )
        private boolean booleanValue = false;
        @GetOptNamed(
                code = 'C',
                flag = "char-value",
                description = "A single character"
        )
        private Character charValue;

        @GetOptNamed(
                flag = "help",
                code = 'H',
                description = "Displays this help message"
        )
        private Boolean help;

        @GetOptNamed(
                flag = "string-list",
                code = 'l',
                description = "A list of strings",
                category = "Type tests",
                collectionType = String.class
        )
        private List<String> stringList;

        @GetOptIgnore
        private Boolean ignoredBoolean;

        public HelpConfig() {
        }

        public boolean getBooleanValue() {
            return booleanValue;
        }

        public HelpConfig setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
            return this;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }

        public HelpConfig setIntegerValue(Integer integerValue) {
            this.integerValue = integerValue;
            return this;
        }

        public Boolean getHelp() {
            return help;
        }

        public HelpConfig setHelp(Boolean help) {
            this.help = help;
            return this;
        }

        public Double getDoubleValue() {
            return doubleValue;
        }

        public HelpConfig setDoubleValue(Double doubleValue) {
            this.doubleValue = doubleValue;
            return this;
        }

        public String getStringValue() {
            return stringValue;
        }

        public HelpConfig setStringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        public Long getLongValue() {
            return longValue;
        }

        public HelpConfig setLongValue(Long longValue) {
            this.longValue = longValue;
            return this;
        }

        public Short getShortValue() {
            return shortValue;
        }

        public HelpConfig setShortValue(Short shortValue) {
            this.shortValue = shortValue;
            return this;
        }

        public Float getFloatValue() {
            return floatValue;
        }

        public HelpConfig setFloatValue(Float floatValue) {
            this.floatValue = floatValue;
            return this;
        }

        public boolean isBooleanValue() {
            return booleanValue;
        }

        public Character getCharValue() {
            return charValue;
        }

        public HelpConfig setCharValue(Character charValue) {
            this.charValue = charValue;
            return this;
        }

        public Byte getByteValue() {
            return byteValue;
        }

        public HelpConfig setByteValue(Byte byteValue) {
            this.byteValue = byteValue;
            return this;
        }

        public Boolean getIgnoredBoolean() {
            return ignoredBoolean;
        }

        public HelpConfig setIgnoredBoolean(Boolean ignoredBoolean) {
            this.ignoredBoolean = ignoredBoolean;
            return this;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public HelpConfig setStringList(List<String> stringList) {
            this.stringList = stringList;
            return this;
        }

        @Override
        public String toString() {
            return "NamedConfig{"
                    + "integerValue=" + integerValue
                    + ", longValue=" + longValue
                    + ", booleanValue=" + help
                    + ", doubleValue=" + doubleValue
                    + ", stringValue='" + stringValue
                    + ", otherBoolean='" + booleanValue + '\''
                    + '}';
        }
    }

    private static class StringConfigNotRequired {
        @GetOptNamed(code = 'S')
        private String stringValue;

        public StringConfigNotRequired() {
        }

        public String getStringValue() {
            return stringValue;
        }
    }

    private static class IntegerConfigNotRequired {
        @GetOptNamed(code = 'I')
        private Integer integerValue;

        public IntegerConfigNotRequired() {
        }

        public Integer getIntegerValue() {
            return integerValue;
        }
    }

    private static class LongConfigNotRequired {
        @GetOptNamed(code = 'L')
        private Long longValue;

        public LongConfigNotRequired() {
        }

        public Long getLongValue() {
            return longValue;
        }
    }

    private static class ByteConfigNotRequired {
        @GetOptNamed(code = 'b')
        private Byte byteValue;

        public ByteConfigNotRequired() {
        }

        public Byte getByteValue() {
            return byteValue;
        }
    }

    private static class CharacterConfigNotRequired {

        @GetOptNamed(code = 'C')
        private Character characterValue;

        public CharacterConfigNotRequired() {
        }

        public Character getCharacterValue() {
            return characterValue;
        }
    }

    private static class BooleanConfigNotRequired {
        @GetOptNamed(code = 'B')
        private Boolean booleanValue;

        @GetOptNamed(code = 'H')
        private Boolean help;

        @GetOptIgnore
        private Boolean ignoredBoolean;

        public BooleanConfigNotRequired() {
        }

        public Boolean getBooleanValue() {
            return booleanValue;
        }

        public Boolean getHelp() {
            return help;
        }

        public Boolean getIgnoredBoolean() {
            return ignoredBoolean;
        }
    }

    private static class TwoBooleanAndDouble {

        @GetOptNamed(code = 'D')
        private Double doubleValue;

        @GetOptNamed(code = 'B')
        private Boolean booleanValue;

        @GetOptNamed(code = 'H')
        private Boolean help;

        public TwoBooleanAndDouble() {
        }

        public Boolean getBooleanValue() {
            return booleanValue;
        }

        public Boolean getHelp() {
            return help;
        }

        public Double getDoubleValue() {
            return doubleValue;
        }
    }

    private static class TwoBooleanAndShort {

        @GetOptNamed(code = 's')
        private Short shortValue;

        @GetOptNamed(code = 'B')
        private Boolean booleanValue;

        @GetOptNamed(code = 'H')
        private Boolean help;

        public TwoBooleanAndShort() {
        }

        public Boolean getBooleanValue() {
            return booleanValue;
        }

        public Boolean getHelp() {
            return help;
        }

        public Short getShortValue() {
            return shortValue;
        }
    }

    private static class FloatConfigNotRequired {

        @GetOptNamed(code = 'F')
        private Float floatValue;

        public FloatConfigNotRequired() {
        }

        public Float getFloatValue() {
            return floatValue;
        }
    }

    private static class DoubleConfigNotRequired {

        @GetOptNamed(code = 'D')
        private Double doubleValue;

        public DoubleConfigNotRequired() {
        }

        public Double getDoubleValue() {
            return doubleValue;
        }
    }

    private static class ShortConfigNotRequired {

        @GetOptNamed(code = 's')
        private Short shortValue;

        public ShortConfigNotRequired() {
        }

        public Short getShortValue() {
            return shortValue;
        }
    }

    private static class StringListNotRequired {
        @GetOptNamed(code = 'l', collectionType = String.class)
        private List<String> stringList;

        public StringListNotRequired() {
        }

        public List<String> getStringList() {
            return stringList;
        }
    }
}
