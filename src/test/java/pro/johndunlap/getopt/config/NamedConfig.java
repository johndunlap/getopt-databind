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
package pro.johndunlap.getopt.config;

import pro.johndunlap.getopt.annotation.GetOptHelp;
import pro.johndunlap.getopt.annotation.GetOptIgnore;
import pro.johndunlap.getopt.annotation.GetOptNamed;

@GetOptHelp(openingText = "This is the opening description", closingText = "This is the closing description")
public class NamedConfig {
    @GetOptNamed(shortName = 'L', longName = "long-value", required = true, description = "A long value", category = "Type tests")
    private Long longValue;
    @GetOptNamed(shortName = 'I', longName = "integer-value", required = true, description = "A integer value", category = "Type tests")
    private Integer integerValue;
    @GetOptNamed(shortName = 's', longName = "short-value", required = true, description = "A long value", category = "Type tests")
    private Short shortValue;
    @GetOptNamed(shortName = 'b', longName = "byte-value", required = true, description = "A byte value", category = "Type tests")
    private Byte byteValue;
    @GetOptNamed(shortName = 'D', longName = "double-value", description = "A double value", category = "Type tests")
    private Double doubleValue;
    @GetOptNamed(shortName = 'F', longName = "float-value", description = "A float value", category = "Type tests")
    private Float floatValue;
    @GetOptNamed(shortName = 'S', longName = "string-value", description = "A string value", category = "Type tests")
    private String stringValue;
    @GetOptNamed(shortName = 'B', longName = "boolean-value", description = "A boolean value")
    private boolean booleanValue = false;
    @GetOptNamed(shortName = 'C', longName = "char-value", description = "A single character")
    private Character charValue;

    @GetOptNamed(longName = "help", shortName = 'H', description = "Displays this help message")
    private Boolean help;

    @GetOptIgnore
    private Boolean ignoredBoolean;

    public NamedConfig() {
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public NamedConfig setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public NamedConfig setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
        return this;
    }

    public Boolean getHelp() {
        return help;
    }

    public NamedConfig setHelp(Boolean help) {
        this.help = help;
        return this;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public NamedConfig setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public String getStringValue() {
        return stringValue;
    }

    public NamedConfig setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public Long getLongValue() {
        return longValue;
    }

    public NamedConfig setLongValue(Long longValue) {
        this.longValue = longValue;
        return this;
    }

    public Short getShortValue() {
        return shortValue;
    }

    public NamedConfig setShortValue(Short shortValue) {
        this.shortValue = shortValue;
        return this;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public NamedConfig setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
        return this;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public Character getCharValue() {
        return charValue;
    }

    public NamedConfig setCharValue(Character charValue) {
        this.charValue = charValue;
        return this;
    }

    public Byte getByteValue() {
        return byteValue;
    }

    public NamedConfig setByteValue(Byte byteValue) {
        this.byteValue = byteValue;
        return this;
    }

    public Boolean getIgnoredBoolean() {
        return ignoredBoolean;
    }

    public NamedConfig setIgnoredBoolean(Boolean ignoredBoolean) {
        this.ignoredBoolean = ignoredBoolean;
        return this;
    }

    @Override
    public String toString() {
        return "NamedConfig{" +
                "integerValue=" + integerValue +
                ", longValue=" + longValue +
                ", booleanValue=" + help +
                ", doubleValue=" + doubleValue +
                ", stringValue='" + stringValue +
                ", otherBoolean='" + booleanValue + '\'' +
                '}';
    }
}
