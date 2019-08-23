package com.defu.permission.annotation;

import java.lang.annotation.*;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/22
 * time: 11:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    String value() default "";

}
