package com.isamotiuc.scrambleword.main_screen.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.google.android.material.snackbar.Snackbar
import com.isamotiuc.scrambleword.R
import com.isamotiuc.scrambleword.base.BaseActivity
import com.isamotiuc.scrambleword.main_screen.presentation.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter, MainTarget>(), MainTarget {

    override fun getViewResourceId() = R.layout.activity_main

    override fun setWord(guessingWord: String) {
        guessing_word_text_view.text = guessingWord
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        check_button.setOnClickListener {
            presenter.onCheckButtonClicked(word_edit_text.text.toString())
        }
        word_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.onCheckButtonClicked(word_edit_text.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun setAnswersCount(allAnswers: Int, correctAnswer: Int) {
        correct_answers_text_view.text = getString(R.string.correct_answer, correctAnswer, allAnswers)
    }

    override fun setCorrectAnswer(correctAnswer: String) {
        correct_answer.text = correctAnswer
    }

    override fun eraseEditText() {
        word_edit_text.setText("")
    }

    override fun setRecordTime(guessingWord: String, timeInSeconds: Int) {
        record_text_view.text = resources.getQuantityString(R.plurals.record,
                timeInSeconds, guessingWord, timeInSeconds)
    }

    override fun showInternetErrorPopUp() {
        Snackbar.make(parent_view, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { presenter.onRetryRequestClicked() }
                .show()
    }

}
