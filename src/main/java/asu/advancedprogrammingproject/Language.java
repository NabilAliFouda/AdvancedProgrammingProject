/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author nabil
 */
public class Language implements Comparable<Language> {
    private String LanguageName;
    private int StudentNo;
    private List<Teacher> teachers;
    private Course courses[];
    
    public Language(){}
    public Language(int StudentNo, List<Teacher> teachers, Course courses[]){
        this.StudentNo = StudentNo;
        this.teachers = teachers;
        this.courses = courses;
    }
    public Language(String name){
        this.LanguageName = name;
        this.StudentNo = 0;
        this.teachers = new ArrayList<Teacher>();
        this.courses = new Course[0];
    }
    public List<Teacher> getteachers() {
        return teachers;
    }
    public Course[] getcourses() {
        return courses;
    }  
    public String getLanguageName() {
        return LanguageName;
    }
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
    @Override
    public int compareTo(Language other) {
        return this.StudentNo - other.StudentNo;
    }
    @Override
    public String toString() {
        return LanguageName; // or getLanguageName()
    }

}
