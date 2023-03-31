package pro.johndunlap.getopt.exception;

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

import java.lang.reflect.Field;
import pro.johndunlap.getopt.annotation.Arg;

/**
 * Thrown when a value cannot be parsed.
 *
 * @author John Dunlap
 */
public class ParseException extends Exception {
    public static final int DEFAULT_ERROR_EXIT_STATUS = 1;

    private Field field;
    private String value;

    /**
     * Constructs a new exception with the specified field, value, and message.
     *
     * @param field The field which could not be populated
     * @param value The value that could not be parsed
     * @param message The message to include in the exception
     */
    public ParseException(Field field, String value, String message) {
        super(message);
        this.field = field;
        this.value = value;
    }

    /**
     * Returns the exit status which should be passed back to the shell
     * for this exception. Allowing this to be different on a field by
     * field basis can allow shell programs to determine which fields
     * were invalid.
     *
     * @return The exit status which should be passed back to the shell
     */
    public int getExitStatus() {
        if (field != null && field.isAnnotationPresent(Arg.class)) {
            Arg arg = field.getAnnotation(Arg.class);
            return arg.exitStatus();
        }

        return DEFAULT_ERROR_EXIT_STATUS;
    }

    public ParseException(String value, String message) {
        super(message);
        this.value = value;
    }

    public ParseException(Field field, String message) {
        super(message);
        this.field = field;
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public Field getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
