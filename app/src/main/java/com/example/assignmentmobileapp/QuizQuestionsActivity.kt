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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = ArrayList() //CHANGED HERE.
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mIsCheater: Boolean = false
    private var cheatTokens: Int = 3 // Need to show in results
    private var initialCheatTokens: Int = cheatTokens // Track initial cheat tokens
    private lateinit var userAnswers: IntArray // Store user answers
    private val gradedQuestions = HashSet<Int>() // Keep track of graded questions
    private val cheatedQuestions = HashSet<Int>() // Keep track of cheated questions

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
    private lateinit var btnReset: Button // Add reset button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean("mIsCheater")
            cheatTokens = savedInstanceState.getInt("cheatTokens")
            initialCheatTokens = savedInstanceState.getInt("initialCheatTokens")
            mCurrentPosition = savedInstanceState.getInt("mCurrentPosition")
            mSelectedOptionPosition = savedInstanceState.getInt("mSelectedOptionPosition")
            mCorrectAnswers = savedInstanceState.getInt("mCorrectAnswers")
            userAnswers = savedInstanceState.getIntArray("userAnswers") ?: IntArray(mQuestionsList!!.size) { 0 }
            gradedQuestions.clear()
            gradedQuestions.addAll(savedInstanceState.getIntegerArrayList("gradedQuestions")!!.toSet())
            cheatedQuestions.clear()
            cheatedQuestions.addAll(savedInstanceState.getIntegerArrayList("cheatedQuestions")!!.toSet())
        }

        initializeViews()
        mQuestionsList = Constants.getQuestions()
        if (!::userAnswers.isInitialized) {
            userAnswers = IntArray(mQuestionsList!!.size) { 0 }
        }
        setQuestion()
        setClickListeners()
    }

    private fun initializeViews() {

        btnPrevious = findViewById(R.id.btn_previous)
        btnNext = findViewById(R.id.btn_next)
        btnHint = findViewById(R.id.hint)
        hintCard = findViewById(R.id.hint_card)
        tvHint = findViewById(R.id.tv_hint)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        btnCheat = findViewById(R.id.btn_cheat)
        tvCheatTokens = findViewById(R.id.tv_cheat_tokens)
        btnReset = findViewById(R.id.btn_reset) // Initialize reset button
        updateCheatTokens()
    }

    private fun setClickListeners() {
        val options = listOf(tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour)
        options.forEach { it.setOnClickListener(this) }
        btnPrevious.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        btnCheat.setOnClickListener(this)
        btnHint.setOnClickListener(this)
        hintCard.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        btnReset.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()

        //Visibility of buttons
        btnPrevious.visibility = if (mCurrentPosition > 1) View.VISIBLE else View.GONE
        btnNext.visibility = if (mCurrentPosition > 0) View.VISIBLE else View.GONE
        btnReset.visibility = if (mCurrentPosition > 1) View.VISIBLE else View.GONE
        btnSubmit.text = if (mCurrentPosition == mQuestionsList!!.size) getString(R.string.finish_text_QQA) else getString(R.string.submit_text_QQA)

        //Initialise other views
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val tvProgress: TextView = findViewById(R.id.tv_progress)
        val tvQuestion: TextView = findViewById(R.id.tv_question)
        val ivFlag: ImageView = findViewById(R.id.iv_flag)

        //Progress Bar
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

        //Lock options when users have submitted an answer for a question
        userAnswers[mCurrentPosition - 1].takeIf { it != 0 }?.let {
            when (it) {
                1 -> selectedOptionView(tvOptionOne, 1, false)
                2 -> selectedOptionView(tvOptionTwo, 2, false)
                3 -> selectedOptionView(tvOptionThree, 3, false)
                4 -> selectedOptionView(tvOptionFour, 4, false)
            }
        }
    }

    private fun defaultOptionsView() {
        val options = listOf(tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour)
        options.forEach { option ->
            option.setBackgroundResource(R.drawable.style_default_option_border_bg)
            option.setTextColor(Color.parseColor("#7A8089"))
            option.setTypeface(null, Typeface.NORMAL)
            option.background = ContextCompat.getDrawable(this, R.drawable.style_default_option_border_bg)
            option.isClickable = true
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
                if (cheatTokens > 0 && !cheatedQuestions.contains(mCurrentPosition - 1)) {
                    cheatTokens--
                    cheatedQuestions.add(mCurrentPosition - 1)
                    updateCheatTokens()
                    highlightCorrectAnswer()
                } else if (cheatedQuestions.contains(mCurrentPosition - 1)) {
                    Toast.makeText(this, "You have already used a cheat for this question", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.hint -> {
                hintCard.visibility = if (hintCard.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            //Others
            R.id.tv_option_one -> selectedOptionView(tvOptionOne, 1)
            R.id.tv_option_two -> selectedOptionView(tvOptionTwo, 2)
            R.id.tv_option_three -> selectedOptionView(tvOptionThree, 3)
            R.id.tv_option_four -> selectedOptionView(tvOptionFour, 4)
            R.id.hint_card -> hintCard.visibility = View.GONE
            R.id.btn_submit -> handleSubmit()
            R.id.btn_reset -> resetQuiz() // Handle reset button click
        }
    }

    private fun handleSubmit() {
        if (mSelectedOptionPosition == 0) {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        } else {
            userAnswers[mCurrentPosition - 1] = mSelectedOptionPosition
            if (!gradedQuestions.contains(mCurrentPosition - 1)) {
                val question = mQuestionsList?.get(mCurrentPosition - 1)
                if (question?.correctAnswer == mSelectedOptionPosition) {
                    mCorrectAnswers++
                }
                gradedQuestions.add(mCurrentPosition - 1)
            }
            disableOptions()
            mSelectedOptionPosition = 0
            if (mCurrentPosition < mQuestionsList!!.size) {
                mCurrentPosition++
                setQuestion()
            } else {
                if (userAnswers.contains(0)) {
                    Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
                } else {
                    saveResultsAndFinish()
                }
            }
        }
    }

    private fun saveResultsAndFinish() {
        // Save results to SharedPreferences or any other storage mechanism
        val sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(Constants.CORRECT_ANSWERS, mCorrectAnswers)
            putInt(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
            putInt(Constants.CHEATS_USED, initialCheatTokens - cheatTokens)
            apply()
        }
        // Intent for ResultActivity
        val resultIntent = Intent(this, ResultActivity::class.java)
        startActivity(resultIntent)

        // Pass both mQuestionsList and userAnswers to ReviewActivity using singleton method
        ReviewActivity.receiveData(mQuestionsList, userAnswers)
        // Finish current activity if needed
        finish()
    }


    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
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
        answerView(question.correctAnswer, R.drawable.style_correct_option_border_bg)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("mIsCheater", mIsCheater)
        outState.putInt("cheatTokens", cheatTokens)
        outState.putInt("initialCheatTokens", initialCheatTokens)
        outState.putInt("mCurrentPosition", mCurrentPosition)
        outState.putInt("mSelectedOptionPosition", mSelectedOptionPosition)
        outState.putInt("mCorrectAnswers", mCorrectAnswers)
        outState.putIntArray("userAnswers", userAnswers)
        outState.putIntegerArrayList("gradedQuestions", ArrayList(gradedQuestions))
        outState.putIntegerArrayList("cheatedQuestions", ArrayList(cheatedQuestions))
    }

    private fun updateCheatTokens() {
        tvCheatTokens.text = "Cheat Tokens: $cheatTokens"
        btnCheat.isEnabled = cheatTokens > 0
    }

    private fun resetQuiz() {
        // Show confirmation dialog before resetting
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Reset")
        builder.setMessage("Are you sure you want to reset the quiz? Your progress will be lost.")

        builder.setPositiveButton("Yes") { _, _ ->
            // Reset quiz
            mQuestionsList?.shuffle()
            mCurrentPosition = 1
            mSelectedOptionPosition = 0
            mCorrectAnswers = 0
            mIsCheater = false
            cheatTokens = initialCheatTokens
            userAnswers.fill(0)
            gradedQuestions.clear()
            cheatedQuestions.clear()
            updateCheatTokens()
            setQuestion()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            // Dismiss dialog if user cancels
            dialog.dismiss()
        }

        // Create and show the dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
