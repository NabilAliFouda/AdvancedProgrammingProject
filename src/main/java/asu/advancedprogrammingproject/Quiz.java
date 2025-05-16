/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

/**
 *
 * @author nabil
 */
public class Quiz implements Gradeable {
    private Question[] questions;
    private int totalGrade;
    private int grade;
    private boolean graded;
    private Course course;


    public Quiz(Question[] questions, Course course) {
        this.questions = questions;
        this.totalGrade = questions.length;
        this.graded = false;
        this.grade = 0;
        this.course = course;
    }

    public int grade() throws IllegalStateException {
        int grade = 0;
        if (!graded) {
            for (Question q : questions) {
                if (q.grade()==1) {
                    grade++;
                }
            }
            graded = true;
            course.appendCourseGrade(grade);
            return grade;
        }
        throw new IllegalStateException("Quiz already graded");
    }

    public void print() {
        System.out.println("quiz");
        for (Question q : questions) {
            q.print();
            System.out.println();
        }
        System.out.println("Total Grade: " + grade + "/" + totalGrade);
    }
    public int getTotalGrade() {
        return totalGrade;
    }
    public Course getCourse() {
        return course;
    }
    public Question[] getQuestions() {
        return questions;
}

}
