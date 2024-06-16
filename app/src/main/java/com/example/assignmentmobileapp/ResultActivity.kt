package com.example.assignmentmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvScore: TextView = findViewById(R.id.tv_score)
        val btnFinish: Button = findViewById(R.id.btn_finish)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        tvScore.text = "Your score is $correctAnswers out of $totalQuestions"
        val percentageScore = (correctAnswers.toDouble() / totalQuestions.toDouble()) * 100
        Toast.makeText(this, "You scored %.2f%%".format(percentageScore), Toast.LENGTH_LONG).show()

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
