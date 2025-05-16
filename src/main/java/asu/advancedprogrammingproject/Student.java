/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

import java.util.ArrayList;

/**
 *
 * @author nabil
 */
public class Student extends User {
    private ArrayList<Course> enrolledCourses;
    private ArrayList<Quiz> quizzesTaken;
    private ArrayList<Integer> courseGrades; // Parallel to enrolledCourses

    public Student(String name, int ID, String password) {
        super(name, ID, password);
        this.enrolledCourses = new ArrayList<>();
        this.quizzesTaken = new ArrayList<>();
        this.courseGrades = new ArrayList<>();
    }

    @Override
    public void getRole() {
        System.out.println("Role: Student");
    }

    public void enroll(Course c) {
        if (enrolledCourses.contains(c)) {
            System.out.println(name + " is already enrolled in " + c.getID());
            return;
        }
        enrolledCourses.add(c);
        courseGrades.add(0); // Default grade
        c.addStudent(this);  // Ensure Course has addStudent(Student s)
        System.out.println(name + " enrolled in course: " + c.getID());
    }

    public void takeQuiz(Quiz q) {
        try {
            int score = q.grade();
            quizzesTaken.add(q);
            System.out.println("Quiz submitted successfully. Score: " + score);
        } catch (IllegalStateException e) {
            System.out.println("Quiz already graded or not answerable: " + e.getMessage());
        }
    }

    public String getGrades() {
        StringBuilder sb = new StringBuilder();
        for (Quiz quiz : quizzesTaken) {
            Course course = quiz.getCourse();
            int total = quiz.getTotalGrade();
            try {
                int score = quiz.grade(); // returns score (already graded if taken)
                sb.append("Course: ").append(course.getID())
                  .append(" | Grade: ").append(score).append("/").append(total).append("\n");
            } catch (IllegalStateException e) {
                sb.append("Course: ").append(course.getID())
                  .append(" | Grade: Not available (").append(e.getMessage()).append(")\n");
            }
        }
        return sb.toString();
    }
    

    public void setGrade(Course c, int grade) {
        int index = enrolledCourses.indexOf(c);
        if (index != -1) {
            courseGrades.set(index, grade);
        } else {
            System.out.println("Course not found for this student.");
        }
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public ArrayList<Quiz> getQuizzesTaken() {
        return quizzesTaken;
    }

    // Prints basic student info
    public void printInfo() {
        System.out.println("Student Info:");
        System.out.println("- Name: " + name);
        System.out.println("- ID: " + ID);
        System.out.println("- Logged In: " + loggedIn);
        System.out.println("- Enrolled Courses: " + enrolledCourses.size());
        System.out.println("- Quizzes Taken: " + quizzesTaken.size());
        System.out.println("- Grades:");
        System.out.print(getGrades());
    }
}
