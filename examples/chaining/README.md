# Chaining
This example demonstrates chaining of multiple single letter codes using the getopt-databind library. 

# Usage
Build the main with this command:
```bash
mvn clean install
```

Run the main with commands like these:
```bash
java -jar target/chaining.jar -fstd "This is the description"
java -jar target/chaining.jar -ftd "This is the description"
```

The output will look similar to this:
```bash
Main{
  first=true,
  second=false,
  third=true,
  description='This is the description'
}
```