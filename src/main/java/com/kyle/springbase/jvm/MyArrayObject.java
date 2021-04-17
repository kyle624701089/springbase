package com.kyle.springbase.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author sunkai-019
 * @title: MyArrayObject
 * @projectName springbase
 * @description: 数组对象和普通对象的内存占用
 * @date 2021/4/11 14:41
 */
public class MyArrayObject {
    int a = 1;
    int b = 2;
    static int[] c = {0, 1, 2};
    public static void main(String[] args) {
        MyArrayObject myArrayObject = new MyArrayObject();
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        System.out.println(ClassLayout.parseInstance(myArrayObject).toPrintable());
    }
}
