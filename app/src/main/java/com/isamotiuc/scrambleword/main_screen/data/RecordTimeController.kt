package com.isamotiuc.scrambleword.main_screen.data

import java.util.*
import javax.inject.Inject

const val RECORD_KEY_TIME = "RECORD_TIME"
const val RECORD_KEY_WORD = "RECORD_WORD"

class RecordTimeController @Inject constructor(val simpleRepository: SampleRepository) {

    private var startTimer: Long = 0
    private var bestTime: Long = 0

    init {
        bestTime = simpleRepository.getLong(RECORD_KEY_TIME)
    }

    fun getSavedRecord(): Record? {
        simpleRepository.run {
            return if (getString(RECORD_KEY_WORD).isNotBlank()) {
                Record(getString(RECORD_KEY_WORD), getLong(RECORD_KEY_TIME))
            } else null
        }
    }

    fun resetTime() {
        startTimer = Calendar.getInstance().time.time
    }

    fun checkRecord(recordWord: String): Long? {
        val currentTimeForAnswer = Calendar.getInstance().time.time - startTimer
        return if (bestTime == 0L || currentTimeForAnswer < bestTime) {
            bestTime = currentTimeForAnswer
            simpleRepository.putLong(RECORD_KEY_TIME, bestTime)
            simpleRepository.putString(RECORD_KEY_WORD, recordWord)
            bestTime
        } else {
            null
        }
    }

    data class Record(val word: String, val time: Long)
}