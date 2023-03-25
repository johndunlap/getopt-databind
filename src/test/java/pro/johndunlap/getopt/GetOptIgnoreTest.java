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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import pro.johndunlap.getopt.annotation.GetOptIgnore;
import pro.johndunlap.getopt.exception.ParseException;

/**
 * These tests verify that fields annotated with {@link GetOptIgnore} are not
 * bound to command line arguments.
 *
 * @author John Dunlap
 */
public class GetOptIgnoreTest {

    @Test
    public void testIgnoredField() throws ParseException {
        String[] args = new String[]{"--first", "abc123", "--second", "80"};
        IgnoreTest config = new GetOpt().read(IgnoreTest.class, args);
        assertNotNull(config);
        assertEquals("abc123", config.getFirst());
        assertNull(config.getSecond());
    }

    private static class IgnoreTest {
        private String first;

        @GetOptIgnore
        private String second;

        public IgnoreTest() {
        }

        public String getFirst() {
            return first;
        }

        public IgnoreTest setFirst(String first) {
            this.first = first;
            return this;
        }

        public String getSecond() {
            return second;
        }

        public IgnoreTest setSecond(String second) {
            this.second = second;
            return this;
        }
    }
}
