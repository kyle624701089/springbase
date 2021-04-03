package com.kyle.springbase.handerSpring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunkai-019
 * @title: MyComponentScan
 * @projectName springbase
 * @description: 用来扫描component注解
 * @date 2021/4/3 17:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)//该注解只能用于类上
public @interface MyComponentScan {
    //扫描路径之类的
    String value();
}
