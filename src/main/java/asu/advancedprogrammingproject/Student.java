/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

/**
 *
 * @author nabil
 */
public class Student extends User {
    public Student(String name, int ID, String password) {
        super(name, ID, password);
    }

    @Override
    public void getRole() {
        System.out.println("Role: Student");
    }
}


