package pro.johndunlap.getopt.example;

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

import pro.johndunlap.getopt.GetOpt;
import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptProperty;
import java.lang.Override;

/**
 * This is an example of how to use the GetOpt library.
 */
@GetOptHelp(
        openingText = "Supported arguments:"
)
public class Main {

    @GetOptProperty(code = 'f')
    private Boolean first;

    @GetOptProperty(code = 's')
    private Boolean second;

    @GetOptProperty(code = 't')
    private Boolean third;

    @GetOptProperty(code = 'd')
    private String description;

    public static void main(String[] args) {
        Main example = new GetOpt().run(Main.class, args);
        System.out.println(example);
    }

    @Override
    public java.lang.String toString() {
        return "Main{" +
                "\n  first=" + first +
                ",\n  second=" + second +
                ",\n  third=" + third +
                ",\n  description='" + description + '\'' +
                "\n}";
    }
}
