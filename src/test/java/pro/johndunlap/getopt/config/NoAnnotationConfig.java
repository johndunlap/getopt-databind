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

public class NoAnnotationConfig {
    private Integer integerValue;
    private String stringValue;
    private String somethingElse;
    private Boolean onOrOff;

    public NoAnnotationConfig() {
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public NoAnnotationConfig setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
        return this;
    }

    public String getStringValue() {
        return stringValue;
    }

    public NoAnnotationConfig setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public String getSomethingElse() {
        return somethingElse;
    }

    public NoAnnotationConfig setSomethingElse(String somethingElse) {
        this.somethingElse = somethingElse;
        return this;
    }

    public Boolean getOnOrOff() {
        return onOrOff;
    }

    public NoAnnotationConfig setOnOrOff(Boolean onOrOff) {
        this.onOrOff = onOrOff;
        return this;
    }
}
