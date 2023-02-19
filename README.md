# getopt-databind
Inspired by GNU GetOpt, getopt-databind considers command line arguments to be a data stream which can be bound to Java objects in a similar manner to JSON, XML, and YAML. Annotations and the Java type system are used to simplify the creation of sophisticated command line interfaces.

## Features
* Named parameters which can have both a single-letter flag with a single hyphen or a multi-letter with two hyphens.
* Multiple boolean single letter flags can be combined into a multi-letter flag with a single hyphen. The last letter in this kind of flag does not need to have a boolean type.
* Automatic type conversion for:
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
  * File
* Custom type conversion
