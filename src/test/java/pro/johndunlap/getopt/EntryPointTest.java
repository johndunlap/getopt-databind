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

import org.junit.Test;
import pro.johndunlap.getopt.exception.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntryPointTest {
    @Test
    public void testEntryPointOnSimpleObject() throws ParseException {
        SimpleConfig config = GetOpt.bind(SimpleConfig.class, new String[]{"-a", "abc123", "-b", "80"});
        assertTrue(config.getInvoked());
    }

    private static class SimpleConfig implements EntryPoint {
        private String a;
        private int b;

        private Boolean invoked;

        public SimpleConfig() {
        }

        public String getA() {
            return a;
        }

        public SimpleConfig setA(String a) {
            this.a = a;
            return this;
        }

        public int getB() {
            return b;
        }

        public SimpleConfig setB(int b) {
            this.b = b;
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
        public int main() {
            assertEquals("abc123", getA());
            assertEquals(80, getB());

            invoked = true;
            return 0;
        }
    }
}
