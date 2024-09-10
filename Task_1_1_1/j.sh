#!/bin/bash

# Устанавливаем переменные для путей
SRC=src/main/java/org.example/HeapSort.java

javac -d gradleless $SRC
javadoc -d gradleless $SRC
java -classpath gradleless org.example.HeapSort