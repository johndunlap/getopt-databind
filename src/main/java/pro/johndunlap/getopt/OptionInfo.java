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

import java.lang.reflect.Field;
import pro.johndunlap.getopt.annotation.Arg;
import pro.johndunlap.getopt.annotation.Ignore;

/**
 * A class which contains information about a single option.
 *
 * @author John Dunlap
 */
public class OptionInfo {
    private String flag = "";
    private char code = ' ';
    private String description = "";
    private String category = "";
    private boolean required = false;

    /**
     * Creates a new OptionInfo object from the given field.
     *
     * @param field The field to create the OptionInfo object from.
     */
    public OptionInfo(Field field) {
        if (field.isAnnotationPresent(Ignore.class)) {
            throw new RuntimeException("Cannot process ignored field");
        }

        Arg property = field.getAnnotation(Arg.class);

        flag = Parser.camelCaseToHyphenCase(field.getName());

        if (field.getType().equals(Boolean.class)
                || field.getType().equals(boolean.class)) {
            description = "Boolean flag which requires no argument";
        } else {
            description = "Accepts a ";

            if (field.getType().equals(String.class)) {
                description += "string value";
            } else if (
                    field.getType().equals(Double.class)
                            || field.getType().equals(double.class)
                            || field.getType().equals(Float.class)
                            || field.getType().equals(float.class)
            ) {
                description += "floating point number";
            } else if (field.getType().equals(Character.class)
                    || field.getType().equals(char.class)) {
                description += "single character";
            } else {
                description += "number";
            }
        }

        if (property != null) {

            if (!property.flag().equals("")) {
                flag = property.flag();
            }

            if (property.code() != ' ') {
                code = property.code();
            }

            if (!property.description().equals("")) {
                description = property.description();
            }

            if (!property.category().equals("")) {
                category = property.category();
            }

            required = property.required();
        }
    }

    public String getFlag() {
        return flag;
    }

    public char getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isRequired() {
        return required;
    }
}
