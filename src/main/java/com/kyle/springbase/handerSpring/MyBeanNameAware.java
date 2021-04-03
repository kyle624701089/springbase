package com.kyle.springbase.handerSpring;

/**
 * @author sunkai-019
 * @title: MyBeanNameAware
 * @projectName springbase
 * @description: 类似于spring中的回调
 * @date 2021/4/3 23:32
 */
public interface MyBeanNameAware {

    void setBeanName(String beanName);
}
