package com.example.assignmentmobileapp

object Constants {
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val question1 = Question(
            1,
            "Which country does this flag belong to?",
            R.drawable.denmark_flag,
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
            R.drawable.world_map,
            "Argentina",
            "Russia",
            "Malaysia",
            "Denmark",
            2
        )
        questionsList.add(question2)

        val question3 = Question(
            3,
            "What country does this flag belong to?",
            R.drawable.denmark_flag,
            "Argentina",
            "Australia",
            "Malaysia",
            "Denmark",
            4
        )
        questionsList.add(question3)

        val question4 = Question(
            4,
            "What country does this flag belong to?",
            R.drawable.denmark_flag,
            "Bahamas",
            "Belgium",
            "Barbados",
            "China",
            4
        )
        questionsList.add(question4)

        val question5 = Question(
            5,
            "Pho, which contains beef, herbs, rice noodles, and broth is a traditional dish in which asian country",
            R.drawable.denmark_flag,
            "Vietnam",
            "China",
            "Malaysia",
            "Thailand",
            1
        )
        questionsList.add(question5)


        return questionsList
    }
}
