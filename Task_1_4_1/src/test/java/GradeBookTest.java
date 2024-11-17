import org.example.GradeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GradeBookTest {

    private GradeBook gradeBook;

    @BeforeEach
    void setUp() {
        gradeBook = new GradeBook();
        gradeBook.addGrade("Math", 1, "Exam",
                "отлично");
        gradeBook.addGrade("Physics", 1, "Exam",
                "хорошо");
        gradeBook.addGrade("Programming", 2, "Дифференцированный зачет",
                "отлично");
        gradeBook.addGrade("English", 2, "Exam",
                "удовлетворительно");
        gradeBook.addGrade("Thesis", 8, "Квалификационная работа",
                "отлично");
    }

    @Test
    void testCalculateAverageGrade() {
        double average = gradeBook.calculateAverageGrade();
        assertEquals(4.4, average, 0.01,
                "Средний балл рассчитывается неправильно");
    }

    @Test
    void testCanTransferToBudgetTrue() {
        gradeBook.addGrade("History", 3, "Exam",
                "хорошо");
        gradeBook.addGrade("Economics", 4, "Exam",
                "отлично");

        assertTrue(gradeBook.canTransferToBudget(),
                "Студент должен иметь возможность перевода на бюджет");
    }

    @Test
    void testCanGetRedDiplomaFalseDueToSatisfactory() {
        assertFalse(gradeBook.canGetRedDiploma(),
                "Студент не должен иметь возможность" +
                        "получения красного диплома из-за оценки 'удовлетворительно'");
    }

    @Test
    void testCanGetRedDiplomaFalseDueToThesis() {
        gradeBook.addGrade("Thesis", 8,
                "Квалификационная работа", "хорошо");
        assertFalse(gradeBook.canGetRedDiploma(),
                "Студент не должен иметь возможность" +
                        "получения красного диплома без оценки 'отлично' за дипломную работу");
    }

    @Test
    void testCanGetIncreasedScholarshipTrue() {
        gradeBook.addGrade("History", 2, "Exam", "отлично");
        gradeBook.addGrade("Economics", 2, "Exam", "отлично");

        assertTrue(gradeBook.canGetIncreasedScholarship(),
                "Студент должен иметь возможность получения повышенной стипендии");
    }

    @Test
    void testSetAndGetPaidForm() {
        gradeBook.setPaidForm(false);
        assertFalse(gradeBook.isOnPaidForm(),
                "Студент должен быть на бюджетной форме обучения");

        gradeBook.setPaidForm(true);
        assertTrue(gradeBook.isOnPaidForm(),
                "Студент должен быть на платной форме обучения");
    }

    @Test
    void testGetCurrentSemester() {
        assertEquals(8, gradeBook.getCurrentSemester(),
                "Текущий семестр определяется неверно");
    }

    @Test
    void testConvertGradeToNumber() {
        assertEquals(5, gradeBook.convertGradeToNumber("отлично"));
        assertEquals(4, gradeBook.convertGradeToNumber("хорошо"));
        assertEquals(3, gradeBook.convertGradeToNumber("удовлетворительно"));
        assertEquals(-1, gradeBook.convertGradeToNumber("неизвестно"),
                "Неверный перевод для неизвестной оценки");
    }

    @Test
    void testPrintGrades() {
        assertDoesNotThrow(() -> gradeBook.printGrades(),
                "Метод printGrades не должен выбрасывать исключения");
    }
}
