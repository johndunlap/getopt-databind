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

import static pro.johndunlap.getopt.Parser.NEUTRAL;
import static pro.johndunlap.getopt.Parser.camelCaseToHyphenCase;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import pro.johndunlap.getopt.annotation.GetOptOrdered;
import pro.johndunlap.getopt.annotation.Help;
import pro.johndunlap.getopt.annotation.Ignore;
import pro.johndunlap.getopt.exception.ParseException;

/**
 * The main entry point for the getopt-databind library.
 *
 * @author John Dunlap
 */
public class GetOpt {

    /**
     * This allows unit tests to override the exit mechanism.
     */
    private ExitMechanism exitMechanism = System::exit;

    /**
     * This allows unit tests to override the output stream.
     */
    private PrintStream out = System.out;

    /**
     * This allows unit tests to override the error stream.
     */
    private PrintStream err = System.err;

    private final Map<Class<?>, TypeConverter<?>> typeConverters = new HashMap<>();

    public GetOpt() {
    }

    public GetOpt register(Class<?> type, TypeConverter<?> typeConverter) {
        typeConverters.put(type, typeConverter);
        return this;
    }

    public GetOpt register(Map<Class<?>, TypeConverter<?>> typeConverters) {
        this.typeConverters.putAll(typeConverters);
        return this;
    }

    /**
     * Binds the given arguments to the given class type.
     *
     * @param classType The class type to bind the arguments to
     * @param args The arguments to bind to the class type
     * @param <T> The type of the class to bind the arguments to
     * @return An instance of the class type with the arguments bound to it
     * @throws ParseException If the arguments could not be bound to the class type
     */
    public <T> T read(Class<T> classType, String[] args) throws ParseException {
        return readContext(classType, args).getInstance();
    }

    /**
     * Same as {@link #read(Class, String[])} except that it returns a {@link ParseContext} instead
     * of the instance.
     *
     * @param classType The class type to bind the arguments to
     * @param args The arguments to bind to the class type
     * @param <T> The type of the class to bind the arguments to
     *
     * @return A {@link ParseContext} containing the instance of the class type with the arguments
     * @throws ParseException If the arguments could not be bound to the class type
     */
    public <T> ParseContext<T> readContext(Class<T> classType, String[] args) throws ParseException {
        ParseContext<T> context = new ParseContext<>(classType, args, typeConverters);

        Parser state = NEUTRAL;

        // Continue executing the next state until all input has been processed
        while (state != null) {
            state = state.execute(context);
        }

        T instance = context.getInstance();

        // Don't throw errors if the help message was requested
        if (!context.isHelpRequested()) {
            // Verify that required fields are set
            for (Field field : context.getRequiredFields()) {
                try {
                    Object value = ReflectionUtil.getFieldValue(field, instance);

                    if (value == null) {
                        // TODO: This does not take annotations into account
                        throw new ParseException(field, "Required argument --"
                                + camelCaseToHyphenCase(field.getName())
                                + " is not set");
                    }
                } catch (IllegalAccessException e) {
                    throw new ParseException("Could not access field " + field.getName(), e);
                }
            }
        }

        return context;
    }

    protected <T> void showHelp(Class<T> classType) {
        // Print the help message to stdout
        out.println(help(classType));

        // Exit normally
        exitMechanism.exit(0);
    }

    /**
     * This method automates the mechanics behind binding, error handling, and displaying
     * the help message. Incoming arguments are bound to the specified class type and the run method
     * is invoked after binding has been completed. If binding fails, validation messages will be
     * printed to stderr and an appropriate exit status will be set. If the help message is
     * requested, it will be printed to stdout prior to exit.
     *
     * @param classType The class type to bind the arguments to
     * @param args The arguments to bind to the class type
     * @param <T> The type of the class to bind the arguments to
     */
    public <T> T run(Class<T> classType, String[] args) {
        T instance = null;

        try {
            // Bind the arguments to the class type
            ParseContext<T> context = readContext(classType, args);

            if (context.isHelpRequested()) {
                showHelp(classType);
            } else {
                // Get the instance from the parse context
                instance = context.getInstance();
            }

            // We don't need to worry about setting the exit status to 0 because that is
            // the default behavior for the JVM
            return instance;
        } catch (ParseException e) {
            // Print the error message to stderr
            getErr().println(e.getMessage());

            exitMechanism.exit(e.getExitStatus());

            // This should only happen when the exit mechanism is overridden. Realistically, it probably won't execute
            // even then because the tests will use the exit mechanism to throw an exception.
            return instance;
        }
    }

    /**
     * Generates a help message for the given class type.
     *
     * @param classType The class type to generate a help message for
     * @param <T> The type of the class for which a help message should be generated
     * @return A help message for the given class type
     */
    public <T> String help(Class<T> classType) {
        Help help = classType.getAnnotation(Help.class);
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
     *
     * @param classType The class type from which metadata should be extracted
     * @param <T> The generic type of the class from which metadata is being extracted
     * @return A list of objects representing the fields which can be bound to
     */
    protected <T> List<OptionInfo> extract(Class<T> classType) {
        List<OptionInfo> options = new ArrayList<>();
        Field[] fields = classType.getDeclaredFields();

        for (Field field : fields) {
            // Skip ordered fields and fields which have been annotated with ignore
            if (field.isAnnotationPresent(Ignore.class) || field.isAnnotationPresent(GetOptOrdered.class)) {
                continue;
            }

            options.add(new OptionInfo(field));
        }

        return options;
    }


    public GetOpt setOut(PrintStream out) {
        this.out = out;
        return this;
    }

    public GetOpt setErr(PrintStream err) {
        this.err = err;
        return this;
    }

    public GetOpt setExitMechanism(ExitMechanism exitMechanism) {
        this.exitMechanism = exitMechanism;
        return this;
    }

    public PrintStream getOut() {
        return out;
    }

    public PrintStream getErr() {
        return err;
    }
}
