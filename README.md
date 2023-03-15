# getopt-databind
Inspired by GNU GetOpt, getopt-databind considers command line arguments to be a data stream which can be bound to Java objects in a similar manner to JSON, XML, or YAML. Annotations and the Java type system are used to simplify the creation of sophisticated command line interfaces.

## Features
* Automatically generated help messages
* Named parameters
   * Single-letter flag with a single hyphen
   * Multi-letter with two hyphens.
   * Multiple boolean single letter flags can be combined into a multi-letter flag with a single hyphen. The last letter in this kind of flag does not need to have a boolean type.
   * Automatic conversion to and from hyphen-case for Java fields which are not annotated
* Required parameters
* Automatic validation and type conversion for:
  * int
  * long
  * short
  * byte
  * float
  * double
  * Integer
  * Long
  * Short
  * Byte
  * Float
  * Double
  * boolean
  * Boolean
* Custom type conversion

## Example

```java
import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptIgnore;
import pro.johndunlap.getopt.annotation.GetOptNamed;
import pro.johndunlap.getopt.exception.ParseException;

public class Main {
  public static void main(String[] args) throws ParseException {
    Config config = GetOpt.bind(Config.class, args);

    if (config.getHelp()) {
      System.out.println(GetOpt.help(Config.class));
      System.exit(0);
    }

    System.out.println(config);
  }

  @GetOptHelp(openingText = "This is the opening description", closingText = "This is the closing description")
  public static class Config {
    @GetOptNamed(shortCode = 'L', longCode = "long-value", required = true, description = "A long value", category = "Type tests")
    private Long longValue;
    @GetOptNamed(shortCode = 'I', longCode = "integer-value", required = true, description = "A integer value", category = "Type tests")
    private Integer integerValue;
    @GetOptNamed(shortCode = 's', longCode = "short-value", required = true, description = "A short value", category = "Type tests")
    private Short shortValue;
    @GetOptNamed(shortCode = 'b', longCode = "byte-value", required = true, description = "A byte value", category = "Type tests")
    private Byte byteValue;
    @GetOptNamed(shortCode = 'D', longCode = "double-value", description = "A double value", category = "Type tests")
    private Double doubleValue;
    @GetOptNamed(shortCode = 'F', longCode = "float-value", description = "A float value", category = "Type tests")
    private Float floatValue;
    @GetOptNamed(shortCode = 'S', longCode = "string-value", description = "A string value", category = "Type tests")
    private String stringValue;
    @GetOptNamed(shortCode = 'B', longCode = "boolean-value", description = "A boolean value")
    private boolean booleanValue = false;
    @GetOptNamed(shortCode = 'C', longCode = "char-value", description = "A single character")
    private Character charValue;

    @GetOptNamed(longCode = "help", shortCode = 'H', description = "Displays this help message")
    private Boolean help;

    @GetOptIgnore
    private Boolean ignoredBoolean;

    public Config() {
    }

    public Boolean getHelp() {
      return help;
    }

    @Override
    public String toString() {
      return "Config{" +
              "integerValue=" + integerValue +
              ", longValue=" + longValue +
              ", booleanValue=" + help +
              ", doubleValue=" + doubleValue +
              ", stringValue='" + stringValue +
              ", otherBoolean='" + booleanValue + '\'' +
              '}';
    }
  }
}
```

The following is output when **--help** is passed into the above program:
```text
This is the opening description
  -B  --boolean-value  A boolean value
  -C  --char-value     A single character
  -H  --help           Displays this help message

Type tests:
  -L  --long-value     A long value (required)
  -I  --integer-value  A integer value (required)
  -s  --short-value    A long value (required)
  -b  --byte-value     A byte value (required)
  -D  --double-value   A double value
  -F  --float-value    A float value
  -S  --string-value   A string value
This is the closing description
```

The following is output when **--long-value 345 --string-value "This is a test"** is passed into the above program:
```text
Config{integerValue=null, longValue=345, booleanValue=false, doubleValue=null, stringValue='This is a test, otherBoolean='false'}
```
