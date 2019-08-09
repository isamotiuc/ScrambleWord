package com.isamotiuc.scrambleword.main_screen.view

import androidx.lifecycle.LifecycleOwner

interface MainTarget : LifecycleOwner {
    fun setWord(guessingWord: String)
    fun setAnswersCount(allAnswers: Int, correctAnswer: Int)
    fun setCorrectAnswer(correctAnswer: String)
    fun eraseEditText()
    fun setRecordTime(guessingWord: String, timeInSeconds: Int)
    fun showInternetErrorPopUp()
}
