package com.kyle.springbase;

/**
 * @author sunkai-019
 * @title: MyBeanPostProcessor
 * @projectName springbase
 * @description: Bean的后置处理器，在Bean对象初始化前后，对Bean对象做自定义处理   类似于aop的功能
 * @date 2021/4/4 0:08
 */
public interface MyBeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);
}
