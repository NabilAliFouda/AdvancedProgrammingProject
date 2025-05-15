/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

/**
 *
 * @author nabil
 */
public class Language {
    private String LanguageName;
    private int StudentNo;
    private Teacher teachers[];
    private Course courses[];
    public Language(int StudentNo, Teacher teachers[], Course courses[]){
        this.StudentNo = StudentNo;
        this.teachers = teachers;
        this.courses = courses;
    }
    public Language(){
        this.StudentNo = 0;
        this.teachers = new Teacher[0];
        this.courses = new Course[0];
    }
    public Teacher[] getteachers() {
        return teachers;
    }
    public Course[] getcourses() {
        return courses;
    }  
    public String getLanguageName() {
        return LanguageName;
    }
}
