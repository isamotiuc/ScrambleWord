package com.isamotiuc.scrambleword.main_screen.presentation

import com.isamotiuc.scrambleword.BuildConfig
import com.isamotiuc.scrambleword.base.BasePresenter
import com.isamotiuc.scrambleword.main_screen.data.AnswersCountController
import com.isamotiuc.scrambleword.main_screen.data.RecordTimeController
import com.isamotiuc.scrambleword.main_screen.data.TextRepository
import com.isamotiuc.scrambleword.main_screen.view.MainTarget
import com.isamotiuc.scrambleword.utils.shuffleString
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(
    val textRepository: TextRepository,
    val recordTimeController: RecordTimeController,
    val answersCountController: AnswersCountController
) : BasePresenter<MainTarget>() {

    private var guessingWord: String = ""

    override fun onCreate() {
        setGuessingWord()

        recordTimeController.getSavedRecord()?.let { it ->
            target?.setRecordTime(it.word, TimeUnit.MILLISECONDS.toSeconds(it.time).toInt())
        }
        answersCountController.getSavedRecords()?.let {
            target?.setAnswersCount(it.allAnswers, it.correctAnswers)
        }
    }

    fun onCheckButtonClicked(word: String) {
        if (guessingWord.isEmpty()) {
            return
        }

        if (word == guessingWord) {
            answersCountController.increaseCorrectAnswer()
            correctAnswer()
        }

        answersCountController.increaseAllAnswers()

        target?.eraseEditText()
        target?.setAnswersCount(answersCountController.allAnswers, answersCountController.correctAnswers)
    }

    private fun correctAnswer() {
        recordTimeController.checkRecord(guessingWord)?.let {
            target?.setRecordTime(guessingWord, TimeUnit.MILLISECONDS.toSeconds(it).toInt())
        }

        setGuessingWord()
    }

    private fun setGuessingWord() {
        addSubscription(
            textRepository.getRandomWord()
                .subscribe({
                    guessingWord = it
                    if (BuildConfig.DEBUG) {
                        target?.setCorrectAnswer(guessingWord)
                    }
                    target?.setWord(guessingWord.shuffleString())
                    recordTimeController.resetTime()

                }, { target?.showInternetErrorPopUp() })
        )
    }

    fun onRetryRequestClicked() {
        setGuessingWord()
    }
}
