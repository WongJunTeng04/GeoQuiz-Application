package com.example.assignmentmobileapp

object Constants {

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
            "Malaysia",
            "Denmark",
            4
        )
        questionsList.add(question1)

        val question2 = Question(
            2,
            "What is the name of the largest country in the world?",
            "The largest cat species is found here too: Siberian Tiger",
            R.drawable.iv_worldmap,
            "Argentina",
            "Russia",
            "Malaysia",
            "Denmark",
            2
        )
        questionsList.add(question2)

        val question3 = Question(
            3,
            "Which country does this flag belong to?",
            "Messi won the world cup in 2022 in this country.",
            R.drawable.iv_argentina_flag,
            "Argentina",
            "Australia",
            "Malaysia",
            "Denmark",
            1
        )
        questionsList.add(question3)

        val question4 = Question(
            4,
            "This country has the highest population in the world:",
            "Country famous for the Great Wall",
            R.drawable.iv_denmark_flag,
            "Bahamas",
            "Belgium",
            "Barbados",
            "China",
            4
        )
        questionsList.add(question4)

        val question5 = Question(
            5,
            "Pho, is a traditional dish in which asian country?",
            "Also famous for their exotic street food consisting of insects.",
            R.drawable.iv_denmark_flag,
            "Vietnam",
            "China",
            "Malaysia",
            "Thailand",
            1
        )
        questionsList.add(question5)

        val question6 = Question(
            6,
            "Which country has the largest Muslim population in the world? ",
            "Home to the world's largest flower: Rafflesia Arnoldii",
            R.drawable.iv_denmark_flag,
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
            R.drawable.iv_denmark_flag,
            "Malaysia",
            "Indonesia",
            "Kuwait",
            "Lebanon",
            3
        )
        questionsList.add(question7)

        val question8 = Question(
            8,
            "Which country is often called the Land of the Rising Sun?",
            "This country is famous for it's own spin on cartoons called Anime",
            R.drawable.iv_denmark_flag,
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
            R.drawable.iv_denmark_flag,
            "Egypt",
            "Singapore",
            "Germany",
            "Poland",
            1
        )
        questionsList.add(question9)

        val question10 = Question(
            10,
            "Which country does this this flag belong to?",
            "One of the world's largest exporters of natural gas",
            R.drawable.iv_denmark_flag,
            "Japan",
            "China",
            "Qatar",
            "Thailand",
            3
        )
        questionsList.add(question10)

        questionsList.shuffle()
        return questionsList
    }
}
