package com.example.assignmentmobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


private lateinit var ivPrevious: Button
private lateinit var ivNext: Button
private lateinit var exitButton: Button

class ReviewActivity : AppCompatActivity() {

    companion object {
        private var instance: ReviewActivity? = null
        private var questionsList: ArrayList<Question>? = null
        private var userAnswers: IntArray? = null

        // Singleton method to receive and handle questions list and user answers
        fun receiveData(questionsList: ArrayList<Question>?, userAnswers: IntArray?) {
            this.questionsList = questionsList
            this.userAnswers = userAnswers
            instance?.displayCurrentQuestion()
        }
    }

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Initialize instance in companion object
        instance = this

        // Handle the data if it's already available
        displayCurrentQuestion()

        ivPrevious = findViewById(R.id.btn_previous)
        ivNext = findViewById(R.id.btn_next)
        exitButton = findViewById(R.id.btn_exit)

        ivPrevious.visibility = View.VISIBLE
        ivNext.visibility = View.VISIBLE

        ivPrevious.setOnClickListener {
            showPreviousQuestion()
        }

        ivNext.setOnClickListener {
            showNextQuestion()
        }

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayCurrentQuestion() {
        if (questionsList != null && userAnswers != null) {
            val question = questionsList!![currentQuestionIndex]
            val userAnswer = userAnswers!![currentQuestionIndex]

            val tvQuestion: TextView = findViewById(R.id.tv_question)
            val ivFlag: ImageView = findViewById(R.id.iv_flag)
            val tvOptionOne: TextView = findViewById(R.id.tv_option_one)
            val tvOptionTwo: TextView = findViewById(R.id.tv_option_two)
            val tvOptionThree: TextView = findViewById(R.id.tv_option_three)
            val tvOptionFour: TextView = findViewById(R.id.tv_option_four)

            tvQuestion.text = question.question

            if (question.image != 0) {
                ivFlag.setImageResource(question.image)
                ivFlag.visibility = View.VISIBLE
            } else {
                ivFlag.visibility = View.GONE
            }

            tvOptionOne.text = question.option1
            tvOptionTwo.text = question.option2
            tvOptionThree.text = question.option3
            tvOptionFour.text = question.option4

            // Highlight user answer and the correct answer
            highlightAnswers(userAnswer, question.correctAnswer)
        }
    }

    private fun highlightAnswers(userAnswer: Int, correctAnswer: Int) {
        val tvOptionOne: TextView = findViewById(R.id.tv_option_one)
        val tvOptionTwo: TextView = findViewById(R.id.tv_option_two)
        val tvOptionThree: TextView = findViewById(R.id.tv_option_three)
        val tvOptionFour: TextView = findViewById(R.id.tv_option_four)

        resetOptionsBackground()

        // Highlight correct and wrong answers
        if (userAnswer == correctAnswer) {
            when (correctAnswer) {
                1 -> tvOptionOne.setBackgroundResource(R.drawable.style_correct_option_border_bg)
                2 -> tvOptionTwo.setBackgroundResource(R.drawable.style_correct_option_border_bg)
                3 -> tvOptionThree.setBackgroundResource(R.drawable.style_correct_option_border_bg)
                4 -> tvOptionFour.setBackgroundResource(R.drawable.style_correct_option_border_bg)
            }
        } else {
            when (userAnswer) {
                1 -> tvOptionOne.setBackgroundResource(R.drawable.style_wrong_option_border_bg)
                2 -> tvOptionTwo.setBackgroundResource(R.drawable.style_wrong_option_border_bg)
                3 -> tvOptionThree.setBackgroundResource(R.drawable.style_wrong_option_border_bg)
                4 -> tvOptionFour.setBackgroundResource(R.drawable.style_wrong_option_border_bg)
            }
            when (correctAnswer) {
                1 -> tvOptionOne.setBackgroundResource(R.drawable.style_correct_option_border_bg)
                2 -> tvOptionTwo.setBackgroundResource(R.drawable.style_correct_option_border_bg)
                3 -> tvOptionThree.setBackgroundResource(R.drawable.style_correct_option_border_bg)
                4 -> tvOptionFour.setBackgroundResource(R.drawable.style_correct_option_border_bg)
            }
        }
    }

    private fun resetOptionsBackground() {
        val tvOptionOne: TextView = findViewById(R.id.tv_option_one)
        val tvOptionTwo: TextView = findViewById(R.id.tv_option_two)
        val tvOptionThree: TextView = findViewById(R.id.tv_option_three)
        val tvOptionFour: TextView = findViewById(R.id.tv_option_four)

        tvOptionOne.setBackgroundResource(R.drawable.style_text_black_border)
        tvOptionTwo.setBackgroundResource(R.drawable.style_text_black_border)
        tvOptionThree.setBackgroundResource(R.drawable.style_text_black_border)
        tvOptionFour.setBackgroundResource(R.drawable.style_text_black_border)
    }

    // Call this method to move to the next question
    private fun showNextQuestion() {
        if (currentQuestionIndex < questionsList!!.size - 1) {
            currentQuestionIndex++
            displayCurrentQuestion()
        } else {
            Toast.makeText(this, "This is the end of the quiz.", Toast.LENGTH_SHORT).show()
        }
    }

    // Call this method to move to the previous question
    private fun showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            displayCurrentQuestion()
        }
    }

}
