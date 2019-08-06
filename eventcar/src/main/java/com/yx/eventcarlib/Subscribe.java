package com.yx.eventcarlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author by YX, Date on 2019/8/6.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    ThreadMode threadMode() default ThreadMode.POSTING;
}
