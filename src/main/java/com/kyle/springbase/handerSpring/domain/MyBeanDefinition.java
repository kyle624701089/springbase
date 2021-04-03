package com.kyle.springbase.handerSpring.domain;

import lombok.Data;

/**
 * @author sunkai-019
 * @title: MyBeanDefinition
 * @projectName springbase
 * @description: TODO
 * @date 2021/4/3 22:25
 */
@Data
public class MyBeanDefinition {

    private Class clz;

    /**
     * 原型 prototype  还是 单例 singleton
     */
    private String scope;

    /**
     * 是否懒加载
     */
    private Boolean lazy;
}
