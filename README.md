# getopt-databind
This is a Java library which provides a flexible and customizable way to bind command line arguments to Java objects, in a similar manner to how JSON, XML, and YAML can be bound to Java objects. You can easily define Java classes that represent the data structure of your command line arguments, and use annotations to specify how those arguments should be parsed and bound to the corresponding fields in your Java object. This allows you to write command line interfaces that are more intuitive and easier to use, while also reducing the amount of boilerplate code that is needed to parse and validate command line arguments.

## Why should you use this library?
* Sophisticated command line interfaces with minimal effort
* No dependencies.
* Permissive licensing
* It's free

## Getting started
### Maven
```xml
<dependency>
    <groupId>pro.johndunlap</groupId>
    <artifactId>getopt-databind</artifactId>
    <version>0.5.6</version>
</dependency>
```
### Example

```java
import pro.johndunlap.getopt.annotation.Help;
import pro.johndunlap.getopt.annotation.Arg;

@Help(
    openingText = "This is the opening text",
    closingText = "This is the closing text."
)
public class Example {
    @Arg(code = 'f')
    private String firstName;

    @Arg(code = 'l')
    private String lastName;

    public static void main(String[] args) {
        Example example = new GetOpt().run(Example.class, args);
        System.out.printf("Hello, %s %s!\n", example.firstName, example.lastName);
    }
}
```

The following is output when **--help** is passed into the above program:
```text
This is the opening text
  -f  --first-name  Accepts a string value
  -l  --last-name   Accepts a string value
This is the closing text.
```

The following is output when **--first-name John --last-name Dunlap** is passed into the above program:
```text
Hello, John Dunlap!
```
<br/>If I have helped you in some way, please consider supporting my work by buying me a coffee!<br/><br/>
<a href="https://www.buymeacoffee.com/ixCgtN0uXb" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>
