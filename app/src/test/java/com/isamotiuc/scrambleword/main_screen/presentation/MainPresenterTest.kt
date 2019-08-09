package com.isamotiuc.scrambleword.main_screen.presentation

import com.isamotiuc.scrambleword.PresenterTestCase
import com.isamotiuc.scrambleword.main_screen.data.AnswersCountController
import com.isamotiuc.scrambleword.main_screen.data.RecordTimeController
import com.isamotiuc.scrambleword.main_screen.data.TextRepository
import com.isamotiuc.scrambleword.main_screen.view.MainTarget
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.concurrent.TimeUnit

class MainPresenterTest : PresenterTestCase<MainPresenter, MainTarget>() {
    override lateinit var targetMock: MainTarget
    override val targetClazz: Class<MainTarget> = MainTarget::class.java

    @InjectMocks
    override lateinit var presenter: MainPresenter

    @Mock
    lateinit var textRepository: TextRepository

    @Mock
    lateinit var recordTimeController: RecordTimeController

    @Mock
    lateinit var answersCountController: AnswersCountController

    val testWord = "test"

    override fun setUp() {
        super.setUp()
        whenever(textRepository.getRandomWord()).thenReturn(Single.just(testWord))
    }

    @Test
    fun `restores saved record on create`() {
        val record = RecordTimeController.Record(testWord, 5)

        whenever(recordTimeController.getSavedRecord()).thenReturn(record)

        presenter.onCreate()

        verify(targetMock).setRecordTime(testWord, TimeUnit.MILLISECONDS.toSeconds(5).toInt())
    }

    @Test
    fun `does not show saved record if it is empty on create`() {
        whenever(recordTimeController.getSavedRecord()).thenReturn(null)

        presenter.onCreate()

        verify(targetMock, never()).setRecordTime(any(), any())
    }

    @Test
    fun `resets time on word set up`() {
        presenter.onCreate()

        verify(recordTimeController).resetTime()
    }

    @Test
    fun `sets word on create`() {
        presenter.onCreate()

        verify(targetMock).setWord(any())
    }

    @Test
    fun `shows error message on internet connection error`() {
        whenever(textRepository.getRandomWord()).thenReturn(Single.error(Throwable()))

        presenter.onCreate()

        verify(targetMock, never()).setWord(any())
        verify(targetMock).showInternetErrorPopUp()
    }

    @Test
    fun `does not show word on internet connection error`() {
        whenever(textRepository.getRandomWord()).thenReturn(Single.error(Throwable()))

        presenter.onCreate()

        verify(targetMock, never()).setWord(any())
    }

    @Test
    fun `loads word on retry request clicked`() {
        presenter.onRetryRequestClicked()

        verify(textRepository).getRandomWord()
    }

    @Test
    fun `does not check word when word is not initialized`() {
        presenter.onCheckButtonClicked(testWord)

        verifyZeroInteractions(targetMock)
        verifyZeroInteractions(recordTimeController)
        verifyZeroInteractions(textRepository)
    }

    @Test
    fun `increases all answers count on check button click`() {
        whenever(answersCountController.allAnswers).thenReturn(1)

        presenter.onCreate()
        presenter.onCheckButtonClicked(testWord)

        inOrder(targetMock, answersCountController) {
            verify(answersCountController).increaseAllAnswers()
            verify(targetMock).setAnswersCount(eq(1), any())
        }

    }

    @Test
    fun `increases correct answers count on check button click`() {
        whenever(answersCountController.correctAnswers).thenReturn(1)

        presenter.onCreate()
        presenter.onCheckButtonClicked(testWord)

        inOrder(targetMock, answersCountController) {
            verify(answersCountController).increaseCorrectAnswer()
            verify(targetMock).setAnswersCount(any(), eq(1))
        }
    }

    @Test
    fun `does not increase count on wrong answer on check button click`() {

        presenter.onCreate()
        presenter.onCheckButtonClicked("different")

        verify(answersCountController, never()).increaseCorrectAnswer()
    }

    @Test
    fun `erase edit text on check button click`() {
        presenter.onCreate()

        presenter.onCheckButtonClicked(testWord)

        verify(targetMock).eraseEditText()
    }

    @Test
    fun `checks record on check button click`() {
        whenever(recordTimeController.checkRecord(testWord)).thenReturn(500000)

        presenter.onCreate()
        presenter.onCheckButtonClicked(testWord)

        verify(targetMock).setRecordTime(testWord, TimeUnit.MILLISECONDS.toSeconds(500000).toInt())
    }

    @Test
    fun `does not show record if its empty`() {
        whenever(recordTimeController.checkRecord(testWord)).thenReturn(null)

        presenter.onCreate()
        presenter.onCheckButtonClicked(testWord)

        verify(targetMock, never()).setRecordTime(any(), any())
    }
}