# getopt-databind
This is a Java library which provides a flexible and customizable way to bind command line arguments to Java objects, in a similar manner to how JSON, XML, and YAML can be bound to Java objects. You can easily define Java classes that represent the data structure of your command line arguments, and use annotations to specify how those arguments should be parsed and bound to the corresponding fields in your Java object. This allows you to write command line interfaces that are more intuitive and easier to use, while also reducing the amount of boilerplate code that is needed to parse and validate command line arguments.

## Why should you use this library?
* Sophisticated command line interfaces with minimal effort
* 100% line and branch coverage in unit tests
* No dependencies.
* Permissive licensing
* It's free

## Getting started
* TODO: Jitpack instructions
* TODO: Simple demonstration
* TODO: Reference example code

## Features
* Automatically generated help messages
* Named parameters
   * Single-letter flag with a single hyphen
   * Multi-letter flag with two hyphens.
   * Multiple boolean single letter flags can be combined into a multi-letter flag with a single hyphen. The last letter in this kind of flag does not need to have a boolean type.
   * Automatic conversion to camel-case from hyphen-case to support binding to Java fields which are not annotated
* Required parameters
* Automatic validation and type conversion for: int, long, short, byte, float, double, Integer, Long, Short, Byte, Float, Double, boolean, Boolean
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

<a href="https://www.buymeacoffee.com/ixCgtN0uXb" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>
