package com.example.assignmentmobileapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        btnReset = findViewById(R.id.btn_reset)
        btnReset.setOnClickListener {
            showLeaveConfirmationDialog(
                Intent(this, QuizQuestionsActivity::class.java),
                "Are you sure you want to restart the quiz?"
            )
        }

        val tvScore: TextView = findViewById(R.id.tv_score)
        val btnFinish: Button = findViewById(R.id.btn_finish)
        val btnHistory: Button = findViewById(R.id.btn_history)

        val sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        val totalQuestions = sharedPreferences.getInt(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = sharedPreferences.getInt(Constants.CORRECT_ANSWERS, 0)

        tvScore.text = "Your score is $correctAnswers out of $totalQuestions"
        val percentageScore = (correctAnswers.toDouble() / totalQuestions.toDouble()) * 100
        Toast.makeText(this, "You scored %.2f%%".format(percentageScore), Toast.LENGTH_LONG).show()

        btnHistory.setOnClickListener {
            showLeaveConfirmationDialog(
                Intent(this, HistoryActivity::class.java),
                "Are you sure you want to view your quiz history?"
            )
        }

        btnFinish.setOnClickListener {
            showLeaveConfirmationDialog(
                Intent(this, MainActivity::class.java),
                "Are you sure you want to go back to the main menu?"
            )
        }
    }

    private fun showLeaveConfirmationDialog(intent: Intent, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Navigation")
        builder.setMessage(message)

        builder.setPositiveButton("Yes") { _, _ ->
            startActivity(intent)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
