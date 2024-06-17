package com.example.assignmentmobileapp

object Constants {

    const val PREF_NAME = "quizAppPrefs"
    const val TOTAL_QUESTIONS: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val CHEATS_USED : String = "cheats_used"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val question1 = Question(
            1,
            "Which country does this flag belong to?",
            "LEGO originated from this country.",
            R.drawable.iv_denmark_flag,
            "Argentina",
            "Australia",
            "Norway",
            "Denmark",
            4
        )
        questionsList.add(question1)

        val question2 = Question(
            2,
            "What is the name of the largest country in the world?",
            "The largest cat species is found here too: Siberian Tiger",
            R.drawable.iv_worldmap,
            "Pakistan",
            "Russia",
            "Mexico",
            "Brazil",
            2
        )
        questionsList.add(question2)

        val question3 = Question(
            3,
            "Which country does this flag belong to?",
            "Messi won the world cup in 2022 in this country.",
            R.drawable.iv_argentina_flag,
            "Argentina",
            "Cyprus",
            "Brunei",
            "Uruguay",
            1
        )
        questionsList.add(question3)

        val question4 = Question(
            4,
            "This country has the highest population in the world:",
            "Home to the Taj Mahal",
            R.drawable.iv_india,
            "Bangladesh",
            "Nigeria",
            "India",
            "China",
            3
        )
        questionsList.add(question4)

        val question5 = Question(
            5,
            "Pho, is a traditional dish in which asian country?",
            "Also famous for their exotic street food consisting of insects.",
            R.drawable.iv_vietnam,
            "Turkey",
            "Vietnam",
            "El Salvador",
            "Thailand",
            2
        )
        questionsList.add(question5)

        val question6 = Question(
            6,
            "Which country has the largest Muslim population in the world? ",
            "Home to the world's largest flower: Rafflesia Arnoldii",
            R.drawable.iv_indonesia,
            "Iraq",
            "Indonesia",
            "Kuwait",
            "Lebanon",
            2
        )
        questionsList.add(question6)

        val question7 = Question(
            7,
            "Which country does this flag belong to?",
            "Nestled between Iraq and Saudi Arabia",
            R.drawable.iv_kuwait_flag,
            "Malaysia",
            "Croatia",
            "Kuwait",
            "Samoa",
            3
        )
        questionsList.add(question7)

        val question8 = Question(
            8,
            "Which country is often called the Land of the Rising Sun?",
            "This country is famous for it's own spin on cartoons called Anime",
            R.drawable.iv_japan,
            "Japan",
            "China",
            "Malaysia",
            "Thailand",
            1
        )
        questionsList.add(question8)

        val question9 = Question(
            9,
            "Which country does this flag belong to?",
            "Home to the Pyramids of Giza",
            R.drawable.iv_egypt_flag,
            "Egypt",
            "Singapore",
            "Germany",
            "Poland",
            1
        )
        questionsList.add(question9)

        val question10 = Question(
            10,
            "Which country does this flag belong to?",
            "The capital of this country is Athens",
            R.drawable.iv_greece_flag,
            "Guinea",
            "Rwanda",
            "Qatar",
            "Greece",
            4
        )
        questionsList.add(question10)

        questionsList.shuffle()
        return questionsList
    }
}
