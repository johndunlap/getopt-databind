package pro.johndunlap.getopt.annotation;

import pro.johndunlap.getopt.DefaultValueParser;
import pro.johndunlap.getopt.ValueParser;

public @interface GetOpt {
    int order() default -1;
    char shortCode() default ' ';
    String longCode() default "";
    boolean required() default false;
    String category() default "";
    String description() default "";
    Class<?> collectionType() default Object.class;
    Class<? extends ValueParser<?>> parser() default DefaultValueParser.class;
}
