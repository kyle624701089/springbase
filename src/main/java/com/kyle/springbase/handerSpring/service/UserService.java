package com.kyle.springbase.handerSpring.service;

import com.kyle.springbase.MyBeanPostProcessor;
import com.kyle.springbase.handerSpring.MyBeanNameAware;
import com.kyle.springbase.handerSpring.MyInitializingBean;
import com.kyle.springbase.handerSpring.annotation.MyAutowire;
import com.kyle.springbase.handerSpring.annotation.MyComponent;
import com.kyle.springbase.handerSpring.annotation.MyLazy;
import com.kyle.springbase.handerSpring.annotation.MyScope;
import com.kyle.springbase.handerSpring.domain.User;
import lombok.Data;

/**
 * @author sunkai-019
 * @title: UserService
 * @projectName springbase
 * @description: TODO
 * @date 2021/4/3 17:20
 */
@MyComponent("userService")
@MyLazy
@MyScope
public class UserService implements MyBeanNameAware, MyInitializingBean , MyBeanPostProcessor {

    @MyAutowire
    private User user;

    private String beanName;

    private String username;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
//        初始化动作都可以在这里面实现
        this.username = "haha";
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化之前");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化之后");
        return bean;
    }

    public void test() {
        System.out.println(user);
        System.out.println(beanName);
        System.out.println(username);
    }
}
