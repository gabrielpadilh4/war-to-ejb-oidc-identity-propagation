package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

@InterceptorBinding
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditControl {

    @Nonbinding
    String application() default "";

    @Nonbinding
    String module() default "";

    @Nonbinding
    String variableType() default "queryType";
}
