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

/**
 * This allows a single line code in the normal public static void main(String[]) method to invoke a statically typed
 * main method inside the object being bound to. That method will not be invoked unless there are no validation errors.
 * However, when it is invoked the developer has all a hydrated object containing everything they need to start writing
 * code and can proceed as if it were the real main method. Getopt will automatically invoke this method when it
 * notices that the object implements it. This will work exceptionally well in multi-layered command line interfaces
 * which can have separate entry points for each of the types that they support. The correct entrypoint is automatically
 * invoked based on the arguments which were passed on the command line.
 */
public interface EntryPoint {
    /**
     * This method will be invoked with a hydrated object containing all arguments which were passed on the
     * command line. This method will not be invoked unless there are no validation errors.
     *
     * @return The exit code. Zero should be returned on success and non-zero otherwise
     */
    int main();
}
