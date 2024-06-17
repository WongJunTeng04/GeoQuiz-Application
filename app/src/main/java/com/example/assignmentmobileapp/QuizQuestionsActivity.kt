package com.example.assignmentmobileapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mIsCheater: Boolean = false // Need to show in results
    private var cheatTokens: Int = 3 // Need to show in results
    private var initialCheatTokens: Int = cheatTokens // Track initial cheat tokens
    private var userAnswers: ArrayList<Int> = ArrayList() // Store user answers
    private var gradedQuestions: HashSet<Int> = HashSet() // Keep track of graded questions

    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button
    private lateinit var btnHint: Button
    private lateinit var hintCard: CardView
    private lateinit var tvHint: TextView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btnSubmit: Button
    private lateinit var btnCheat: Button
    private lateinit var tvCheatTokens: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        tvCheatTokens = findViewById(R.id.tv_cheat_tokens)
        updateCheatTokens()

        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean("mIsCheater")
            cheatTokens = savedInstanceState.getInt("cheatTokens")
            initialCheatTokens = savedInstanceState.getInt("initialCheatTokens")
        }

        // Initialize views
        btnPrevious = findViewById(R.id.btn_previous)
        btnNext = findViewById(R.id.btn_next)
        btnCheat = findViewById(R.id.btn_cheat)
        btnHint = findViewById(R.id.hint)
        hintCard = findViewById(R.id.hint_card)
        tvHint = findViewById(R.id.tv_hint)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        mQuestionsList = Constants.getQuestions()
        for (i in mQuestionsList!!.indices) {
            userAnswers.add(0) // Initialize with 0 indicating no answer selected
        }
        setQuestion()

        // Set click listeners
        btnPrevious.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        btnCheat.setOnClickListener(this)
        btnHint.setOnClickListener(this)
        hintCard.setOnClickListener(this)
        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()

        btnPrevious.visibility = if (mCurrentPosition > 1) View.VISIBLE else View.GONE

        if (mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit.text = getString(R.string.finish_text_QQA)
        } else {
            btnSubmit.text = getString(R.string.submit_text_QQA)
        }

        // Find other views by ID
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val tvProgress: TextView = findViewById(R.id.tv_progress)
        val tvQuestion: TextView = findViewById(R.id.tv_question)
        val ivFlag: ImageView = findViewById(R.id.iv_flag)

        // Set data to views
        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition/${progressBar.max}"

        question.let {
            tvQuestion.text = it.question
            ivFlag.setImageResource(it.image)
            tvOptionOne.text = it.option1
            tvOptionTwo.text = it.option2
            tvOptionThree.text = it.option3
            tvOptionFour.text = it.option4
            tvHint.text = it.hint
        }

        // Restore selected answer if any
        if (userAnswers[mCurrentPosition - 1] != 0) {
            when (userAnswers[mCurrentPosition - 1]) {
                1 -> selectedOptionView(tvOptionOne, 1, false)
                2 -> selectedOptionView(tvOptionTwo, 2, false)
                3 -> selectedOptionView(tvOptionThree, 3, false)
                4 -> selectedOptionView(tvOptionFour, 4, false)
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(tvOptionOne)
        options.add(tvOptionTwo)
        options.add(tvOptionThree)
        options.add(tvOptionFour)

        for (option in options) {
            option.setBackgroundResource(R.drawable.style_default_option_border_bg)
            option.setTextColor(Color.parseColor("#7A8089"))
            option.setTypeface(null, Typeface.NORMAL)
            option.background =
                ContextCompat.getDrawable(this, R.drawable.style_default_option_border_bg)
            option.isClickable = true // Make options clickable by default
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_previous -> {
                mCurrentPosition--
                setQuestion()
            }

            R.id.btn_next -> {
                if (mCurrentPosition < mQuestionsList!!.size) {
                    mCurrentPosition++
                    setQuestion()
                }
            }

            R.id.btn_cheat -> {
                if (cheatTokens > 0) {
                    cheatTokens--
                    updateCheatTokens()
                    highlightCorrectAnswer()
                }
            }

            R.id.hint -> {
                hintCard.visibility = if (hintCard.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            R.id.tv_option_one -> {
                selectedOptionView(tvOptionOne, 1)
            }

            R.id.tv_option_two -> {
                selectedOptionView(tvOptionTwo, 2)
            }

            R.id.tv_option_three -> {
                selectedOptionView(tvOptionThree, 3)
            }

            R.id.tv_option_four -> {
                selectedOptionView(tvOptionFour, 4)
            }

            R.id.hint_card -> {
                hintCard.visibility = View.GONE
            }

            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    // Show a Toast indicating that the user must select an option
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                } else {
                    // Store user's answer
                    userAnswers[mCurrentPosition - 1] = mSelectedOptionPosition

                    // Update correct answers count if not already graded
                    if (!gradedQuestions.contains(mCurrentPosition - 1)) {
                        val question = mQuestionsList?.get(mCurrentPosition - 1)
                        if (question?.correctAnswer == mSelectedOptionPosition) {
                            mCorrectAnswers++
                        }
                        gradedQuestions.add(mCurrentPosition - 1)
                    }

                    // Disable option buttons
                    disableOptions()

                    // Reset selected option position
                    mSelectedOptionPosition = 0

                    // Move to the next question or finish the quiz
                    if (mCurrentPosition < mQuestionsList!!.size) {
                        mCurrentPosition++
                        setQuestion()
                    } else {
                        // Check if all questions have been answered
                        if (userAnswers.contains(0)) {
                            Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
                        } else {
                            // Save results to SharedPreferences
                            val sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putInt(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            editor.putInt(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            editor.putInt(Constants.CHEATS_USED, initialCheatTokens - cheatTokens)
                            editor.apply()

                            // Start ResultActivity
                            val intent = Intent(this, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }

            2 -> {
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }

            3 -> {
                tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }

            4 -> {
                tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int, isEnabled: Boolean = true) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.style_selected_option_border_bg)
        if (!isEnabled) {
            disableOptions()
        }
    }

    private fun disableOptions() {
        tvOptionOne.isClickable = false
        tvOptionTwo.isClickable = false
        tvOptionThree.isClickable = false
        tvOptionFour.isClickable = false
    }

    private fun highlightCorrectAnswer() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        answerView(question.correctAnswer, R.drawable.style_selected_option_border_bg)
    }

    //Track whether the user has used any cheats during the test.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("mIsCheater", mIsCheater)
        outState.putInt("cheatTokens", cheatTokens)
        outState.putInt("initialCheatTokens", initialCheatTokens)
    }

    private fun updateCheatTokens() {
        tvCheatTokens.text = "Cheat Tokens: $cheatTokens"
        if (cheatTokens <= 0) {
            btnCheat.isEnabled = false
        }
    }
}

