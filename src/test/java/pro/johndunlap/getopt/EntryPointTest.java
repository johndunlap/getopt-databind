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
