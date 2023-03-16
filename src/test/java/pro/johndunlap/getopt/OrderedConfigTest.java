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

import org.junit.Test;
import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptOrdered;
import pro.johndunlap.getopt.exception.ParseException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderedConfigTest {
    @Test
    public void testOrderedConfigWithFourElements() throws ParseException {
        OrderedConfig orderedConfig = GetOpt.bind(OrderedConfig.class, new String[]{"zero", "one", "two", "three"});
        assertEquals("zero", orderedConfig.getStringValue());
        assertEquals("one", orderedConfig.getSomething());
        assertEquals("two", orderedConfig.getList().get(0));
        assertEquals("three", orderedConfig.getArray()[0]);
        assertEquals(1, orderedConfig.getArray().length);
    }

    @GetOptHelp(openingText = "This is the opening description", closingText = "This is the closing description")
    private static class OrderedConfig {
        @GetOptOrdered(order = 1, required = true, collectionType = String.class)
        private String something;
        @GetOptOrdered(order = 2, collectionType = String.class)
        private List<String> list;
        @GetOptOrdered(order = 0, collectionType = String.class)
        private String stringValue;
        @GetOptOrdered(order = 3, collectionType = String.class)
        private String[] array;

        public OrderedConfig() {
        }

        public String getSomething() {
            return something;
        }

        public OrderedConfig setSomething(String something) {
            this.something = something;
            return this;
        }

        public List<String> getList() {
            return list;
        }

        public OrderedConfig setList(List<String> list) {
            this.list = list;
            return this;
        }

        public String getStringValue() {
            return stringValue;
        }

        public OrderedConfig setStringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        public String[] getArray() {
            return array;
        }

        public OrderedConfig setArray(String[] array) {
            this.array = array;
            return this;
        }

        @Override
        public String toString() {
            return "OrderedConfig{" +
                    "longValue='" + something + '\'' +
                    ", list=" + list +
                    ", stringValue='" + stringValue + '\'' +
                    '}';
        }
    }
}
