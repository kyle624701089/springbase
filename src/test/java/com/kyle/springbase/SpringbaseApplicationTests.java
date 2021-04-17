package com.kyle.springbase;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbaseApplicationTests {
    public static void main(String[] args) {
        SpringbaseApplicationTests demo = new SpringbaseApplicationTests();
        System.out.println(demo.add());
    }
    private int add() {
        int a = 6;
        int b = 10;
        return a + b;
    }
}
