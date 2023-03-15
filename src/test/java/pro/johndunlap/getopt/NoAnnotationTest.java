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

import pro.johndunlap.getopt.config.NoAnnotationConfig;
import pro.johndunlap.getopt.exception.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoAnnotationTest {
    @Test
    public void testNoAnnotationBindLongNames() throws ParseException {
        String[] args = {"--integer-value", "8080", "--string-value", "abc123"};
        NoAnnotationConfig config = GetOpt.bind(NoAnnotationConfig.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getIntegerValue().intValue());
        assertEquals("abc123", config.getStringValue());
    }

    @Test
    public void testNoAnnotationBindLowerCaseShortNames() throws ParseException {
        String[] args = {"-i", "8080", "-s", "abc123"};
        NoAnnotationConfig config = GetOpt.bind(NoAnnotationConfig.class, args);
        assertNotNull(config);
        assertEquals(8080, config.getIntegerValue().intValue());
        assertEquals("abc123", config.getStringValue());
    }

    @Test
    public void testNoAnnotationBindWithWrongShortName() throws ParseException {
        String[] args = {"-S", "abc123"};
        NoAnnotationConfig config = GetOpt.bind(NoAnnotationConfig.class, args);
        assertNotNull(config);
        assertNull(config.getStringValue());
    }

    @Test
    public void testNoAnnotationHelp() {
        String expected = "The following options are accepted: \n" +
                "     --integer-value   Accepts a number\n" +
                "     --string-value    Accepts a string value\n" +
                "     --something-else  Accepts a string value\n" +
                "     --on-or-off       Boolean flag which requires no argument";
        String actual = GetOpt.help(NoAnnotationConfig.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}