package pro.johndunlap.getopt.annotation;

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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pro.johndunlap.getopt.DefaultValueParser;
import pro.johndunlap.getopt.ValueParser;

/**
 * This annotation is used to mark a field as a named option(code/flag).
 *
 * @author John Dunlap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GetOptNamed {
    /**
     * The single character code for the option. This is expected to be a single character. For example, 'o'.
     *
     * @return The single character code for the option.
     */
    char code() default ' ';

    /**
     * The name of the option. This is expected to be two or more characters and hyphen cased. For example, "my-option".
     *
     * @return The name of the option.
     */
    String flag() default "";

    /**
     * True if the option requires a value. If true and if a value is not available, an exception will be thrown.
     *
     * @return True if the option requires a value.
     */
    boolean required() default false;

    /**
     * The category in which the option should appear in the help message. This is used in the help message.
     *
     * @return The category of the option.
     */
    String category() default "";

    /**
     * The description of the option. This is used in the help message.
     *
     * @return The description of the option.
     */
    String description() default "";

    /**
     * This is only necessary if the field is a collection. In that case, this is the type of the objects which the
     * collection will contain. This is necessary because of Java type erasure. Generics are not available at runtime.
     *
     * @return The type of the objects which the collection will contain.
     */
    Class<?> collectionType() default Object.class;

    /**
     * This is only necessary if the field is a type that is not supported by default. In that case, this type is
     * instantiated and used to parse the value.
     *
     * @return The class of the ValueParser to use.
     */
    Class<? extends ValueParser<?>> parser() default DefaultValueParser.class;
}
