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

import pro.johndunlap.getopt.annotation.GetOptNamed;
import pro.johndunlap.getopt.exception.ParseException;
import pro.johndunlap.getopt.exception.RethrownException;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DateParserTest {
    @Test
    public void testDateParser() throws ParseException {
        String[] args = {"--date-value", "2022-12-11"};
        DateConfig config = GetOpt.bind(DateConfig.class, args);
        assertNotNull(config);

        String result = new SimpleDateFormat(DateValueParser.DATE_FORMAT).format(config.getDateValue());

        assertEquals("2022-12-11", result);
    }

    private static class DateConfig {
        @GetOptNamed(flag = "date-value", code = 'D', parser = DateValueParser.class)
        private Date dateValue;

        public DateConfig() {
        }

        public Date getDateValue() {
            return dateValue;
        }

        public DateConfig setDateValue(Date dateValue) {
            this.dateValue = dateValue;
            return this;
        }
    }

    private static class DateValueParser implements ValueParser<Date> {
        public static final String DATE_FORMAT = "yyyy-MM-dd";

        public DateValueParser() {
        }

        @Override
        public Class<Date> getType() {
            return Date.class;
        }

        @Override
        public Date parse(String value) throws ParseException {
            try {
                return new SimpleDateFormat(DATE_FORMAT).parse(value);
            } catch (java.text.ParseException e) {
                throw new RethrownException(e);
            }
        }
    }
}
