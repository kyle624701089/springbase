package com.kyle.springbase.handerSpring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunkai-019
 * @title: MyComponent
 * @projectName springbase
 * @description: scope，是原型prototype还是singleton
 * @date 2021/4/3 17:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyScope {
    //自定义beanName
    String value() default "singleton";
}
