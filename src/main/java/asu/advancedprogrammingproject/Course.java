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
public class Course {
    private Language language;
    private int courseID;
    private String level;
    private int Price;
    private Teacher teacher;
    private List<Student> students;
    private int courseGrade;
    public Course(Language language, int courseID, String level, int Price, Teacher teacher, List<Student> students){
        this.language = language;
        this.courseID = courseID;
        this.level = level;
        this.Price = Price;
        this.teacher = teacher;
        this.students = students;
    }
    public Course(){
        this.language = new Language();
        this.courseID = 0;
        this.level = "";
        this.Price = 0;
        this.teacher = new Teacher();
        this.students = new ArrayList<Student>();
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public void removeStudent(Student student) throws IllegalStateException {
        // Check if the list is empty before trying to remove a student
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to remove");
        }
        students.remove(student);
    }

    public void print(){
        System.out.println("Language: " + language.getLanguageName());
        System.out.println("Course ID: " + courseID);
        System.out.println("Level: " + level);
        System.out.println("Price: " + Price);
        System.out.println("Teacher: " + teacher.toString());
        System.out.print("Students: ");
       for (Student student : students) {
           System.out.print(student.toString() + " ");
       }
        System.out.println();
    }
    public void appendCourseGrade(int courseGrade) {
        this.courseGrade = courseGrade;
    }

    
}
