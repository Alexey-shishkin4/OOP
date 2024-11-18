import org.example.GradeBook;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GradeBookTest {

    private GradeBook gradeBook;

    @BeforeEach
    void setUp() {
        gradeBook = new GradeBook();
        gradeBook.addGrade("Math", 1,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.EXCELLENT);
        gradeBook.addGrade("Physics", 1,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.GOOD);
        gradeBook.addGrade("Programming", 2,
                GradeBook.ControlType.DIFFERENTIATED_CREDIT, GradeBook.GradeValue.EXCELLENT);
        gradeBook.addGrade("English", 2,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.SATISFACTORY);
        gradeBook.addGrade("Thesis", 8,
                GradeBook.ControlType.THESIS, GradeBook.GradeValue.EXCELLENT);
    }

    @Test
    void testCalculateAverageGrade() {
        double average = gradeBook.calculateAverageGrade();
        assertEquals(4.4, average, 0.01,
                "Средний балл рассчитывается неправильно");
    }

    @Test
    void testCanTransferToBudgetTrue() {
        gradeBook.addGrade("History", 3,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.GOOD);
        gradeBook.addGrade("Economics", 4,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.EXCELLENT);

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
                GradeBook.ControlType.THESIS, GradeBook.GradeValue.GOOD);
        assertFalse(gradeBook.canGetRedDiploma(),
                "Студент не должен иметь" +
                        "возможность получения красного" +
                        "диплома без оценки 'отлично' за дипломную работу");
    }

    @Test
    void testCanGetIncreasedScholarshipTrue() {
        gradeBook.addGrade("History", 2,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.EXCELLENT);
        gradeBook.addGrade("Economics", 2,
                GradeBook.ControlType.EXAM, GradeBook.GradeValue.EXCELLENT);

        assertTrue(gradeBook.canGetIncreasedScholarship(),
                "Студент должен иметь возможность" +
                        "получения повышенной стипендии");
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
    void testPrintGrades() {
        assertDoesNotThrow(() -> gradeBook.printGrades(),
                "Метод printGrades не должен выбрасывать исключения");
    }
}