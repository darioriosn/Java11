package com.example.petmanagement.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface AgeRange {
    int min() default 0;
    int max() default 30; // Most pets don't live beyond 30 years
}