package com.kyle.springbase.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author sunkai-019
 * @title: MyEmptyObject
 * @projectName springbase
 * @description: 空对象，检验占用内存大小
 * @date 2021/4/11 14:12
 */
public class MyEmptyObject {

    /**
     * 需要引入包，查看内存占用大小
     * <!-- https://mvnrepository.com/artifact/org.openjdk.jol/jol-core -->
     * <dependency>
     *     <groupId>org.openjdk.jol</groupId>
     *     <artifactId>jol-core</artifactId>
     *     <version>0.15</version>
     *     <scope>provided</scope>
     * </dependency>
     *
     */

    public static void main(String[] args) {
        MyEmptyObject myEmptyObject = new MyEmptyObject();
        System.out.println(ClassLayout.parseInstance(myEmptyObject).toPrintable());
    }
}
