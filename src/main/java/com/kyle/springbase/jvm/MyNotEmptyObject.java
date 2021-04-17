package com.kyle.springbase.jvm;

import org.openjdk.jol.info.ClassLayout;
/**
 * @author sunkai-019
 * @title: MyNotEmptyObject
 * @projectName springbase
 * @description: 有属性的对象
 * @date 2021/4/11 14:28
 */
public class MyNotEmptyObject {
    int a =1;
    int b = 2;
    double c = 3.0d;
    public static void main(String[] args) {
        MyNotEmptyObject myNotEmptyObject = new MyNotEmptyObject();
        System.out.println(ClassLayout.parseInstance(myNotEmptyObject).toPrintable());
    }
}
