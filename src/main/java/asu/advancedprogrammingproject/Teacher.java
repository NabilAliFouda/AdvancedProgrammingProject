public class Teacher extends User {

    private Language language; // The language this teacher teaches
    private List<Quiz> createdQuizzes = new ArrayList<>(); // Quizzes created by this teacher

    public Teacher(String name, int ID, String password, Language language) {
        super(name, ID, password);
        this.language = language;
    }

    public Teacher() {
        this(" ", 0, " ", new Language());
    }

    @Override
    public void getRole() {
        System.out.println("Teacher");
    }

    // Creates a quiz for a course using a list of questions
    public Quiz createQuiz(Course course, List<Question> questions) {
        Question[] questionArray = questions.toArray(new Question[0]);
        Quiz quiz = new Quiz(questionArray, course);
        createdQuizzes.add(quiz);
        System.out.println("Quiz created with " + questionArray.length + " questions for course: " + course.getID());
        return quiz;
    }

    public List<Quiz> getCreatedQuizzes() {
        return createdQuizzes;
    }

    // Grades a quiz and prints the score
    public void gradeQuiz(Quiz quiz) {
        try {
            int score = quiz.grade();
            System.out.println("Quiz graded successfully.");
            System.out.println("Score: " + score + "/" + quiz.getTotalGrade());
        } catch (IllegalStateException e) {
            System.out.println("Grading failed: " + e.getMessage());
        }
    }

    // Prints basic info about the teacher
    public void printInfo() {
        System.out.println("Teacher Info:");
        System.out.println("- Name: " + name);
        System.out.println("- ID: " + ID);
        System.out.println("- Logged In: " + loggedIn);
        System.out.println("- Language: " + language.getLanguageName());
        System.out.println("- Quizzes Created: " + createdQuizzes.size());
    }
}