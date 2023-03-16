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

import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptIgnore;
import pro.johndunlap.getopt.annotation.GetOptOrdered;
import pro.johndunlap.getopt.exception.ParseException;

import java.lang.reflect.Field;
import java.util.*;

import static pro.johndunlap.getopt.Parser.NEUTRAL;

public class GetOpt {

    public GetOpt() {
    }

    public static <T> T bind(Class<T> classType, String[] args) throws ParseException {
        ParseContext<T> context = new ParseContext<>(classType, args);
        Parser state = NEUTRAL;

        // Continue executing the next state until all input has been processed
        while (state != null) {
            state = state.execute(context);
        }

        T instance = context.getInstance();

        // If the method
        if (instance instanceof EntryPoint) {
            int status = ((EntryPoint) instance).main();
            // TODO: What do we do with the status code? If we exit here, it will break the tests
        }

        return instance;
    }

    public static <T> String help(Class<T> classType) {
        GetOptHelp help = classType.getAnnotation(GetOptHelp.class);
        StringBuilder sb = new StringBuilder();
        String before = "";
        String after = "";

        // If there's a help annotation, grab the before and after text
        if (help != null) {
            before = help.openingText();
            after = help.closingText();
        } else {
            before = "The following options are accepted: ";
        }

        sb.append(before);

        Map<String, List<OptionInfo>> categorized = new HashMap<>();
        int longestLongName = 0;

        // Attempt to categorize the options
        Map<String, Boolean> categoryMap = new HashMap<>();
        for (OptionInfo optionInfo : extract(classType)) {
            String category = optionInfo.getCategory();

            if (category.equals("")) {
                category = "default";
            } else {
                categoryMap.put(category, true);
            }

            List<OptionInfo> list;

            // Create the list if it doesn't already exist
            if (!categorized.containsKey(category)) {
                list = new ArrayList<>();
                categorized.put(category, list);
            } else {
                list = categorized.get(category);
            }

            list.add(optionInfo);

            // Keep track of the longest long name so that we can pad the help message correctly
            int length = optionInfo.getFlag().length();
            if (length > longestLongName) {
                longestLongName = length;
            }
        }

        LinkedList<String> categoryList = new LinkedList<>(categoryMap.keySet());

        // Sort the list of categories
        Collections.sort(categoryList);

        categoryList.addFirst("default");

        String longestWhitespace = String.join("", Collections.nCopies(longestLongName, " "));

        for (String category : categoryList) {
            List<OptionInfo> namedOptionList = categorized.get(category);

            if (!category.equals("default")) {
                sb.append("\n\n").append(category).append(":");
            }

            for (OptionInfo nameOption : namedOptionList) {
                sb.append("\n  ");

                if (nameOption.getCode() != ' ') {
                    sb.append('-').append(nameOption.getCode());
                } else {
                    sb.append(' ');
                }

                String longName = nameOption.getFlag();

                // Pad the end of the long name
                if (longName.equals("")) {
                    sb.append(longestWhitespace);
                } else {
                    sb.append("  --").append(longName)
                            .append(longestWhitespace, 0, longestLongName - longName.length());
                }

                sb.append("  ").append(nameOption.getDescription());

                if (nameOption.isRequired()) {
                    sb.append(" (required)");
                }
            }
        }

        if (!after.equals("")) {
            sb.append("\n");
        }

        return sb.append(after).toString();
    }

    /**
     * This method is shared between the bind and help methods.
     * @param classType The class type from which metadata should be extracted
     * @param <T> The generic type of the class from which metadata is being extracted
     * @return A list of objects representing the fields which can be bound to
     */
    protected static <T> List<OptionInfo> extract(Class<T> classType) {
        List<OptionInfo> options = new ArrayList<>();
        Field[] fields = classType.getDeclaredFields();

        for (Field field : fields) {
            // Skip ordered fields and fields which have been annotated with ignore
            if (field.isAnnotationPresent(GetOptIgnore.class) || field.isAnnotationPresent(GetOptOrdered.class)) {
                continue;
            }

            options.add(new OptionInfo(field));
        }

        return options;
    }
}
