package com.example.assignmentmobileapp

data class Question(
    val id: Int,
    val question: String,
    val hint: String,
    val image: Int,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctAnswer: Int,
    var userAnswer: Int = 0
)
