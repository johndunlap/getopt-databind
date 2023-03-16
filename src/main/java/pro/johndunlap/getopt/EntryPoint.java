package pro.johndunlap.getopt;

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
     * @return The exit code. Zero should be returned on success and non-zero otherwise
     */
    int main();
}
