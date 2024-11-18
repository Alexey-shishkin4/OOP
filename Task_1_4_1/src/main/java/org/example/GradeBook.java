package org.example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс электронной зачетной книжки студента.
 * Позволяет отслеживать оценки, вычислять текущий средний балл,
 * проверять возможность перевода на бюджетную форму обучения,
 * получения красного диплома и повышенной стипендии.
 */
public class GradeBook {
    private final List<Grade> grades = new ArrayList<>();
    private boolean isOnPaidForm = true;

    /**
     * Перечисление для типов контроля.
     */
    public enum ControlType {
        EXAM("Экзамен"),
        DIFFERENTIATED_CREDIT("Дифференцированный зачет"),
        COURSEWORK("Курсовая работа"),
        THESIS("Квалификационная работа");

        private final String description;

        ControlType(String description) {
            this.description = description;
        }

        /**
         * Возвращает описание.
         *
         * @return Описание.
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Перечисление для оценок.
     */
    public enum GradeValue {
        EXCELLENT("отлично", 5),
        GOOD("хорошо", 4),
        SATISFACTORY("удовлетворительно", 3);

        private final String description;
        private final int numericValue;

        GradeValue(String description, int numericValue) {
            this.description = description;
            this.numericValue = numericValue;
        }

        /**
         * Возвращает описание.
         *
         * @return Описание.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Возвращает численное значение.
         *
         * @return int.
         */
        public int getNumericValue() {
            return numericValue;
        }
    }

    /**
     * Класс Grade представляет информацию об одной оценке студента.
     */
    public static class Grade {
        private final String subject;
        private final int semester;
        private final ControlType type;
        private final GradeValue grade;

        /**
         * Конструктор для создания экземпляра оценки.
         *
         * @param subject  Название предмета.
         * @param semester Номер семестра.
         * @param type     Тип контроля (экзамен, зачет, дифференцированный зачет и т.д.).
         * @param grade    Оценка (например, "отлично", "хорошо", "удовлетворительно").
         */
        public Grade(String subject, int semester, ControlType type, GradeValue grade) {
            this.subject = subject;
            this.semester = semester;
            this.type = type;
            this.grade = grade;
        }

        /**
         * Возвращает объект.
         *
         * @return Объект.
         */
        public String getSubject() {
            return subject;
        }

        /**
         * Возвращает оценку.
         *
         * @return Строка, представляющая оценку.
         */
        public GradeValue getGrade() {
            return grade;
        }

        /**
         * Возвращает номер семестра.
         *
         * @return Номер семестра.
         */
        public int getSemester() {
            return semester;
        }

        /**
         * Возвращает тип.
         *
         * @return Тип.
         */
        public ControlType getType() {
            return type;
        }

        @Override
        public String toString() {
            return String.format("Subject: %s, Semester: %d, Type: %s, Grade: %s",
                    subject, semester, type.getDescription(), grade.getDescription());
        }
    }

    /**
     * Добавляет новую оценку в зачетную книжку.
     */
    public void addGrade(String subject, int semester, ControlType type, GradeValue grade) {
        grades.add(new Grade(subject, semester, type, grade));
    }

    /**
     * Вычисляет текущий средний балл за все время обучения.
     */
    public double calculateAverageGrade() {
        int total = grades.stream().mapToInt(grade -> grade.getGrade().getNumericValue()).sum();
        return grades.isEmpty() ? 0 : (double) total / grades.size();
    }

    /**
     * Проверяет возможность перевода на бюджетную форму обучения.
     */
    public boolean canTransferToBudget() {
        if (!isOnPaidForm) return false;

        int currentSemester = getCurrentSemester();
        return grades.stream()
                .filter(grade -> grade.getSemester() >= currentSemester - 1
                        && grade.getType() == ControlType.EXAM)
                .noneMatch(grade -> grade.getGrade() == GradeValue.SATISFACTORY);
    }

    /**
     * Проверяет возможность получения красного диплома.
     */
    public boolean canGetRedDiploma() {
        // Учитываем только последнюю оценку за курс
        Map<String, Grade> latestGrades = getLatestGrades();

        long excellentCount = latestGrades.values().stream()
                .filter(grade -> grade.getGrade() == GradeValue.EXCELLENT)
                .count();

        long totalRelevantGrades = latestGrades.values().stream()
                .filter(grade -> grade.getType() == ControlType.EXAM
                        || grade.getType() == ControlType.DIFFERENTIATED_CREDIT)
                .count();

        boolean hasNoSatisfactory = latestGrades.values().stream()
                .noneMatch(grade -> grade.getGrade() == GradeValue.SATISFACTORY);

        boolean thesisIsExcellent = latestGrades.values().stream()
                .anyMatch(grade -> grade.getType() == ControlType.THESIS
                        && grade.getGrade() == GradeValue.EXCELLENT);

        return totalRelevantGrades > 0
                && excellentCount >= 0.75 * totalRelevantGrades
                && hasNoSatisfactory
                && thesisIsExcellent;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре.
     */
    public boolean canGetIncreasedScholarship() {
        int currentSemester = getCurrentSemester();
        return grades.stream()
                .filter(grade -> grade.getSemester() == currentSemester
                        && grade.getType() == ControlType.EXAM)
                .allMatch(grade -> grade.getGrade() == GradeValue.EXCELLENT);
    }

    /**
     * Возвращает текущий семестр на основе добавленных оценок.
     */
    public int getCurrentSemester() {
        return grades.stream().mapToInt(Grade::getSemester).max().orElse(0);
    }

    /**
     * Устанавливает форму обучения (платная или бюджетная).
     */
    public void setPaidForm(boolean isOnPaidForm) {
        this.isOnPaidForm = isOnPaidForm;
    }

    /**
     * Возвращает текущую форму обучения.
     */
    public boolean isOnPaidForm() {
        return isOnPaidForm;
    }

    /**
     * Печатает список всех добавленных оценок.
     */
    public void printGrades() {
        grades.forEach(System.out::println);
    }

    /**
     * Возвращает мапу с последними оценками за каждый предмет.
     */
    private Map<String, Grade> getLatestGrades() {
        return grades.stream()
                .collect(Collectors.toMap(
                        Grade::getSubject,
                        grade -> grade,
                        (existing, replacement) -> replacement // Берем последнюю оценку
                ));
    }
}
