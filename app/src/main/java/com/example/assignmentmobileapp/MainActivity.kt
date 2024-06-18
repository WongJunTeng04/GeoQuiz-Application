package com.example.assignmentmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        val leaveButton: Button = findViewById(R.id.leaveButton)
        val historyButton: Button = findViewById(R.id.historyButton)

        startButton.setOnClickListener {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            startActivity(intent)
            finish()
        }

        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        leaveButton.setOnClickListener {
            showLeaveConfirmationDialog()
        }
    }

    private fun showLeaveConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Exit")
        builder.setMessage("Are you sure you want to leave?")

        builder.setPositiveButton("Yes") { dialog, which ->
            Toast.makeText(applicationContext, "Thank you for your time", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
