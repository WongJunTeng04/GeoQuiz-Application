package com.example.assignmentmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        val leaveButton : Button = findViewById(R.id.leaveButton)

        startButton.setOnClickListener {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            startActivity(intent)
            finish()
        }

        leaveButton.setOnClickListener {
            Toast.makeText(this, "Thank you for your time", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}