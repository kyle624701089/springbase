package com.kyle.springbase.handerSpring;

import com.kyle.springbase.handerSpring.service.UserService;

/**
 * @author sunkai-019
 * @title: StartApp
 * @projectName springbase
 * @description: 模拟spring的启动类 Application
 * @date 2021/4/3 17:19
 */
public class StartApp {

    public static void main(String[] args) {
        MyApplicationContext context = new MyApplicationContext(MyAppConfig.class);

        //通过context拿到bean信息  UserService是单例的，所以打印出来是同一个Bean对象
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));

        UserService userService = (UserService) context.getBean("userService");
        userService.test();
    }
}
