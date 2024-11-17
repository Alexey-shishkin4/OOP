package org.example;

import java.util.*;

/**
 * Класс электронной зачетной книжки студента.
 * Позволяет отслеживать оценки, вычислять текущий средний балл,
 * проверять возможность перевода на бюджетную форму обучения,
 * получения красного диплома и повышенной стипендии.
 */
public class GradeBook {
    /**
     * Класс Grade представляет информацию об одной оценке студента.
     */
    public static class Grade {
        private final String subject;
        private final int semester;
        private final String type;
        private final String grade;

        /**
         * Конструктор для создания экземпляра оценки.
         *
         * @param subject  Название предмета.
         * @param semester Номер семестра.
         * @param type     Тип контроля (экзамен, зачет, дифференцированный зачет и т.д.).
         * @param grade    Оценка (например, "отлично", "хорошо", "удовлетворительно").
         */
        public Grade(String subject, int semester, String type, String grade) {
            this.subject = subject;
            this.semester = semester;
            this.type = type;
            this.grade = grade;
        }

        /**
         * Возвращает оценку.
         *
         * @return Строка, представляющая оценку.
         */
        public String getGrade() {
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
        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return String.format("Subject: %s, Semester: %d," +
                    "Type: %s, Grade: %s", subject, semester, type, grade);
        }
    }

    private final List<Grade> grades = new ArrayList<>();
    private boolean isOnPaidForm = true;

    /**
     * Добавляет новую оценку в зачетную книжку.
     *
     * @param subject  Название предмета.
     * @param semester Номер семестра.
     * @param type     Тип контроля (например, экзамен, зачет).
     * @param grade    Оценка (например, "отлично", "хорошо", "удовлетворительно").
     */
    public void addGrade(String subject, int semester, String type, String grade) {
        grades.add(new Grade(subject, semester, type, grade));
    }

    /**
     * Вычисляет текущий средний балл за все время обучения.
     *
     * @return Средний балл в виде числа с плавающей точкой.
     */
    public double calculateAverageGrade() {
        int total = 0;
        int count = 0;
        for (Grade grade : grades) {
            int numericGrade = convertGradeToNumber(grade.getGrade());
            if (numericGrade != -1) {
                total += numericGrade;
                count++;
            }
        }
        return count == 0 ? 0 : (double) total / count;
    }

    /**
     * Проверяет возможность перевода на бюджетную форму обучения.
     *
     * @return {@code true}, если перевод возможен, {@code false} в противном случае.
     */
    public boolean canTransferToBudget() {
        int currentSemester = getCurrentSemester();
        return grades.stream()
                .filter(grade -> grade.getSemester() >= currentSemester - 1
                        && grade.getType().equals("Экзамен"))
                .noneMatch(grade -> grade.getGrade().equals("удовлетворительно"));
    }

    /**
     * Проверяет возможность получения красного диплома.
     *
     * @return {@code true}, если возможность получения красного диплома есть, {@code false} иначе.
     */
    public boolean canGetRedDiploma() {
        long excellentCount = grades.stream()
                .filter(grade -> grade.getGrade().equals("отлично"))
                .count();
        long totalRelevantGrades = grades.stream()
                .filter(grade -> grade.getType().equals("Экзамен")
                        || grade.getType().equals("Дифференцированный зачет"))
                .count();

        boolean hasNoSatisfactory = grades.stream()
                .noneMatch(grade -> grade.getGrade().equals("удовлетворительно"));

        boolean thesisIsExcellent = grades.stream()
                .anyMatch(grade -> grade.getType().equals("Квалификационная работа")
                        && grade.getGrade().equals("отлично"));

        return excellentCount >= 0.75 * totalRelevantGrades && hasNoSatisfactory
                && thesisIsExcellent;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре.
     *
     * @return {@code true}, если стипендия возможна, {@code false} в противном случае.
     */
    public boolean canGetIncreasedScholarship() {
        int currentSemester = getCurrentSemester();
        return grades.stream()
                .filter(grade -> grade.getSemester() == currentSemester
                        && grade.getType().equals("Экзамен"))
                .allMatch(grade -> grade.getGrade().equals("отлично"));
    }

    /**
     * Возвращает текущий семестр на основе добавленных оценок.
     *
     * @return Номер текущего семестра.
     */
    public int getCurrentSemester() {
        return grades.stream().mapToInt(Grade::getSemester).max().orElse(0);
    }

    /**
     * Конвертирует строковую оценку в числовое представление.
     *
     * @param grade Строка, представляющая оценку (например, "отлично").
     * @return Числовое значение оценки или -1, если оценка не числовая.
     */
    public int convertGradeToNumber(String grade) {
        switch (grade) {
            case "отлично":
                return 5;
            case "хорошо":
                return 4;
            case "удовлетворительно":
                return 3;
            default:
                return -1;
        }
    }

    /**
     * Устанавливает форму обучения (платная или бюджетная).
     *
     * @param isOnPaidForm {@code true}, если студент обучается на платной основе, {@code false} иначе.
     */
    public void setPaidForm(boolean isOnPaidForm) {
        this.isOnPaidForm = isOnPaidForm;
    }

    /**
     * Возвращает текущую форму обучения.
     *
     * @return {@code true}, если студент обучается на платной основе, {@code false} иначе.
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
}