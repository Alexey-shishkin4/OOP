#!/bin/bash

# Устанавливаем переменные для путей
SRC_DIR="src/main/java"
BIN_DIR="build/classes/java/main"
JAVADOC_DIR="build/docs"
SRC_FILE="org/example/HeapSort.java"


mkdir -p "$BIN_DIR"
mkdir -p "$JAVADOC_DIR"


javac -d "$BIN_DIR" "$SRC_DIR/$SRC_FILE"
javadoc -d "$JAVADOC_DIR" "$SRC_DIR/$SRC_FILE"
java -cp "$BIN_DIR" org.example.HeapSort