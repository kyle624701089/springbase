package com.kyle.springbase.handerSpring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunkai-019
 * @title: MyComponent
 * @projectName springbase
 * @description: 用于告诉scan注解，这是一个需要spring管理的bean对象
 * @date 2021/4/3 17:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyComponent {
    //自定义beanName
    String value();
}
