package com.test;

public class MyClassTest {
    public static void main(String[] args) {
        MyClassTest.myStaticMethod();

        // DataType variableName = new DataType();
        MyClassTest myClassTest = new MyClassTest();
        myClassTest.myNormalMethod();
    }

    public static void myStaticMethod() {
        System.out.println("Hello");
    }

    public void myNormalMethod() {
        System.out.println("Hi");
    }
}
