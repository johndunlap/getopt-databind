# Hello
This example demonstrates using the getopt-databind library to say hello.

# Usage
Build the example with this command:
```bash
mvn clean install
```

Run the example with commands like these:
```bash
java -jar target/hello.jar --first-name YOUR -l NAME
java -jar target/hello.jar -f YOUR --last-name NAME
java -jar target/hello.jar --first-name YOUR --last-name NAME
java -jar target/hello.jar --help
java -jar target/hello.jar -h
```

The output will look similar to this:
```bash
Hello, YOUR NAME!
```