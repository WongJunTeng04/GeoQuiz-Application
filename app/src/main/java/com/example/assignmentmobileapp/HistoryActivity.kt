package com.example.assignmentmobileapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val exitButton: Button = findViewById(R.id.btn_finish)
        val tvTotalQuetions1: TextView = findViewById(R.id.tv_totalQuestions1)
        val tvTotalQuestionsAnswered: TextView = findViewById(R.id.tv_totalQuestionAnswered)
        val tvTotalScore: TextView = findViewById(R.id.tv_totalScore)
        val tvCheatAttempts: TextView = findViewById(R.id.tv_totalCheatsUsed)
        val reviewButton: Button = findViewById(R.id.btn_review)

        val sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        val totalQuestions = sharedPreferences.getInt(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = sharedPreferences.getInt(Constants.CORRECT_ANSWERS, 0)
        val cheatAttempts = sharedPreferences.getInt(Constants.CHEATS_USED, 0)

        tvTotalQuetions1.text = "$totalQuestions"
        tvTotalQuestionsAnswered.text= "$totalQuestions questions"
        tvTotalScore.text = "$correctAnswers out of $totalQuestions"
        tvCheatAttempts.text = "Used $cheatAttempts cheat(s)"


        reviewButton.setOnClickListener{
            val intent = Intent(this, ReviewActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
