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
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import pro.johndunlap.getopt.exception.ParseException;

/**
 * Tests for configuration classes which implement {@link Runnable}.
 *
 * @author John Dunlap
 */
public class EntryPointTest {
    @Test
    public void testEntryPointOnSimpleObject() throws ParseException {
        String[] args = new String[]{"--first", "abc123", "--second", "80"};
        SimpleConfig config = new GetOpt().read(SimpleConfig.class, args);
        assertNotNull(config);
        assertTrue(config.getInvoked());
    }

    private static class SimpleConfig implements Runnable {
        private String first;
        private int second;

        private Boolean invoked;

        public SimpleConfig() {
        }

        public String getFirst() {
            return first;
        }

        public SimpleConfig setFirst(String first) {
            this.first = first;
            return this;
        }

        public int getSecond() {
            return second;
        }

        public SimpleConfig setSecond(int second) {
            this.second = second;
            return this;
        }

        public Boolean getInvoked() {
            return invoked;
        }

        public SimpleConfig setInvoked(Boolean invoked) {
            this.invoked = invoked;
            return this;
        }

        @Override
        public void run() {
            assertEquals("abc123", getFirst());
            assertEquals(80, getSecond());

            invoked = true;
        }
    }
}
