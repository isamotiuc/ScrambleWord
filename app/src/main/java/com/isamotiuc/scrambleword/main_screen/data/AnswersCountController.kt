package com.isamotiuc.scrambleword.main_screen.data

import javax.inject.Inject

const val ALL_ANSWERS_COUNT_KEY = "ALL_ANSWERS"
const val CORRECT_ANSWERS_COUNT_KEY = "CORRECT_ANSWERS"

class AnswersCountController @Inject constructor(val simpleRepository: SampleRepository) {
    var correctAnswers = simpleRepository.getInt(CORRECT_ANSWERS_COUNT_KEY)
    var allAnswers = simpleRepository.getInt(ALL_ANSWERS_COUNT_KEY)

    fun increaseCorrectAnswer() {
        ++correctAnswers
        simpleRepository.putInt(CORRECT_ANSWERS_COUNT_KEY, correctAnswers)
    }

    fun increaseAllAnswers() {
        ++allAnswers
        simpleRepository.putInt(ALL_ANSWERS_COUNT_KEY, allAnswers)
    }

    fun getSavedRecords(): AnswersCount? {
        return if (allAnswers != 0) {
            AnswersCount(correctAnswers, allAnswers)
        } else {
            null
        }
    }

    data class AnswersCount(val correctAnswers: Int, val allAnswers: Int)
}