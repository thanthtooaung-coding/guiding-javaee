package com.oop.encapsulation;

public class EmployeeTest {
    public static void main(String[] args) {
        Employee employee = new Employee();
//        employee.displayInformation();
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Phone Number: " + employee.getPhoneNumber());

        employee.setId(100);
        System.out.println("ID: " + employee.getId()); // 100

        employee.setName("John");
        System.out.println("Name: " + employee.getName());

        employee.setPhoneNumber("0123456789");
        System.out.println("Phone Number: " + employee.getPhoneNumber());
    }
}
