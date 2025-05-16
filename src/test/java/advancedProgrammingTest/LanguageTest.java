/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nabil
 */
package advancedProgrammingTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import asu.advancedprogrammingproject.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
public class LanguageTest {
    @Test
    void testLanguageConstructor() {
        Language language = new Language();
        assertEquals(0, language.getcourses().size());
        assertEquals(0, language.getteachers().size());
    }
    @Test
    void testLanguageConstructorWithParameters() {
        List<Teacher> teachers = new ArrayList<Teacher>();
        List<Course> courses = new ArrayList<Course>();
        Language language = new Language(5, teachers, courses);
        assertEquals(5, language.getcourses().size());
        assertEquals(0, language.getteachers().size());
    }
    @Test
    void testGetLanguageName() {
        Language language = new Language();
        assertNull(language.getLanguageName());
    }
    @Test
    void testGetTeachers() {
        Language language = new Language();
        assertEquals(0, language.getteachers().size());
    }
    @Test
    void testGetCourses() {
        Language language = new Language();
        assertEquals(0, language.getcourses().size());
    }
    @Test
    void testAddTeacher() {
        Language language = new Language();
        Teacher teacher = new Teacher("John Doe", 12345, "@32424%$@", language);
        language.addTeacher(teacher);
        assertEquals(1, language.getteachers().size());
        assertEquals(teacher, language.getteachers().get(0));
    }
    @Test
    void testCompareTo() {
        Language language1 = new Language(5, null, null);
        Language language2 = new Language(10, null, null);
        assertTrue(language1.compareTo(language2) < 0);
        assertTrue(language2.compareTo(language1) > 0);
    }
    @Test
    void testDecrementStudentNo() {
        Language language = new Language();
        language.incrementStudentNo();
        language.decrementStudentNo();
        assertEquals(0, language.getcourses().size());
    }
    @Test
    void testSorting() {
        Language language1 = new Language(5, null, null);
        Language language2 = new Language(6, null, null);
        Language[] languages = {language2, language1};
        Arrays.sort(languages);
        assertEquals(language1, languages[0]);
        assertEquals(language2, languages[1]);
        language1.incrementStudentNo();
        language2.decrementStudentNo();
        assertEquals(language2, languages[0]);
        assertEquals(language1, languages[1]);
    }
    @Test
    void testDecrementStudentNoThrowsException() {
        Language language = new Language();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            language.decrementStudentNo();
        });
        assertEquals("Student number cannot be negative", exception.getMessage());
    }
}
