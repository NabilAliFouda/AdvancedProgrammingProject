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
    private List<Quiz> quizzes; 
    private int courseGrade;

    // Constructor with all parameters
    public Course(Language language, int courseID, String level, int Price, Teacher teacher, List<Student> students) {
        this.language = language;
        this.courseID = courseID;
        this.level = level;
        this.Price = Price;
        this.teacher = teacher;
        this.students = students != null ? students : new ArrayList<>();
        this.quizzes = new ArrayList<>(); 
    }

    // Default constructor
    public Course() {
        this.language = null;
        this.courseID = 0;
        this.level = "";
        this.Price = 0;
        this.teacher = new Teacher();
        this.students = new ArrayList<>();
        this.quizzes = new ArrayList<>(); // ✅ Initialize quiz list
    }

    public void addStudent(Student student) {
        students.add(student);
        // Increment the student number in the language object
        language.incrementStudentNo();
    }

    public void removeStudent(Student student) throws IllegalStateException {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to remove");
        }
        students.remove(student);
        // Decrement the student number in the language object
        language.decrementStudentNo();
    }

    public int getID() {
        return courseID;
    }

    public Language getLanguage() {
        return language;
    }

    public int getPrice() {
        return Price;
    }

    public String getLevel() {
        return level;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void appendCourseGrade(int num) {
        this.courseGrade += num;
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    // ✅ NEW: Get all quizzes of the course
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    @Override
    public String toString() {
        return getLanguage().getLanguageName() + " (Level: " + level + ")";
    }

    public void print() {
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
}
