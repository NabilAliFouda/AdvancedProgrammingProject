/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

/**
 *
 * @author nabil
 */
public class Question implements Gradeable {
    private String text;
    private boolean graded;
    private String correctAnswer;
    private String studentAnswer;

    public Question(String text, String correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.graded = false;
    }

    public void answer(String ans) {
        this.studentAnswer = ans;
    }

    public int grade() throws IllegalStateException {
        if (studentAnswer == null) {
            throw new IllegalStateException("Question not answered");
        }
        if (!graded) {
            graded = true;
            return 1;
        }
        throw new IllegalStateException("Question already graded");
    }

    public void print() {
        System.out.println("Question: " + text);
        System.out.println("Your Answer: " + studentAnswer);
        System.out.println("Correct Answer: " + correctAnswer);
    }
}
