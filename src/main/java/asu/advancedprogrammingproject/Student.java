package asu.advancedprogrammingproject;

import java.util.ArrayList;

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
        courseGrades.add(0); // Default grade 0
        c.addStudent(this);
        System.out.println(name + " enrolled in course: " + c.getID());
    }

    // Student takes quiz: grade quiz, record it, and update courseGrades
    public void takeQuiz(Quiz q) {
        if (quizzesTaken.contains(q)) {
            System.out.println("Quiz already taken.");
            return;
        }

        try {
            int score = q.grade();
            quizzesTaken.add(q);

            // Update grade for the related course
            Course course = q.getCourse();
            int index = enrolledCourses.indexOf(course);
            if (index != -1) {
                courseGrades.set(index, score);
            } else {
                System.out.println("Course for this quiz not found in enrolled courses.");
            }

            System.out.println("Quiz submitted successfully. Score: " + score);
        } catch (IllegalStateException e) {
            System.out.println("Quiz already graded or not answerable: " + e.getMessage());
        }
    }

    // Get grades from courseGrades array matching enrolledCourses
    public String getGrades() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);
            int grade = courseGrades.get(i);

            // Check if student took a quiz for this course
            boolean taken = quizzesTaken.stream().anyMatch(q -> q.getCourse().equals(course));

            if (taken) {
                // Assuming quiz total grade is accessible via course quizzes
                int total = 0;
                if (!course.getQuizzes().isEmpty()) {
                    total = course.getQuizzes().get(0).getTotalGrade();
                }

                sb.append("Course: ").append(course.getID())
                  .append(" | Grade: ").append(grade).append("/").append(total).append("\n");
            } else {
                sb.append("Course: ").append(course.getID())
                  .append(" | Grade: Not available\n");
            }
        }
        return sb.toString();
    }

    // Set grade manually (used if needed)
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
    public Integer getGrade(Course c) {
    int index = enrolledCourses.indexOf(c);
    if (index != -1) {
        return courseGrades.get(index);
    }
    return null;
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
