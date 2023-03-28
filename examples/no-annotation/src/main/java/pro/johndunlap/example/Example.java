package pro.johndunlap.example;

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

import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptProperty;

/**
 * This is an example of how to use the GetOpt library.
 */
@GetOptHelp(
        openingText = "This is the opening text",
        closingText = "This is the closing text."
)
public class Example {
    @GetOptProperty(code = 'f', description = "Your first name")
    private String firstName;

    @GetOptProperty(code = 'l', description = "Your last name")
    private String lastName;

    // TODO: @GetOptHelp should allow setting a non-zero exit status for the help message
    // TODO: No error message for properties which do not exist
    // TODO: The help message is not inferring flags from field names
    public static void main(String[] args) {
        Example example = new GetOpt().run(Example.class, args);
        System.out.printf("Hello, %s %s!\n", example.firstName, example.lastName);
    }
}
