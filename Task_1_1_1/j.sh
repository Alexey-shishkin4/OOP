#!/bin/bash

# Устанавливаем переменные для путей
SRC_DIR="src"
BIN_DIR="bin"
DIST_DIR="dist"
DOC_DIR="docs"
MAIN_CLASS="org.example.HeapSort"

# Создаем необходимые директории
mkdir -p "$BIN_DIR" "$DIST_DIR" "$DOC_DIR"

# Компиляция исходных файлов
echo "Компиляция исходных файлов..."
# shellcheck disable=SC2046
javac -d "$BIN_DIR" -sourcepath "$SRC_DIR" $(find "$SRC_DIR" -name "*.java")

# Генерация документации Javadoc
echo "Генерация документации Javadoc..."
# shellcheck disable=SC2046
javadoc -d "$DOC_DIR" -sourcepath "$SRC_DIR" $(find "$SRC_DIR" -name "*.java")

# Создание JAR-файла
echo "Создание JAR-файла..."
jar cvf "$DIST_DIR/app.jar" -C "$BIN_DIR" .

# Запуск приложения
echo "Запуск приложения..."
java -cp "$DIST_DIR/app.jar" "$MAIN_CLASS"
