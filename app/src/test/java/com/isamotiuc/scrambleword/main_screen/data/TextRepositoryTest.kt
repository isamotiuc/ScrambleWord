package com.isamotiuc.scrambleword.main_screen.data

import com.isamotiuc.scrambleword.BaseTestCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

class TextRepositoryTest : BaseTestCase() {
    @InjectMocks
    lateinit var textRepository: TextRepository

    @Mock
    lateinit var apiInterface: ApiInterface

    val testText = "aaa. bbBB, cc? e (ddd) \"ff\""
    val testText2 = "aaaa bbbb cccc"
    val testTextTwoWords = "aaaa bbbb"


    @Test
    fun `gets word from server`() {
        whenever(apiInterface.getText()).thenReturn(Single.just(DummyTextApi(testText)))

        val testObserver = textRepository.getRandomWord()
            .test().apply {
                awaitTerminalEvent()
            }

        testObserver
            .assertNoErrors()
            .assertValue { wordsSet ->
                wordsSet == "bbbb"
            }
    }

    @Test
    fun `on second request gets words from cache`() {
        whenever(apiInterface.getText()).thenReturn(Single.just(DummyTextApi(testText2)))

        textRepository.getRandomWord()
            .test().apply {
                awaitTerminalEvent()
            }

        textRepository.getRandomWord()
            .test().apply {
                awaitTerminalEvent()
            }

        verify(apiInterface, times(1)).getText()
    }

    @Test
    fun `on cache runs out make a new request to server`() {
        whenever(apiInterface.getText()).thenReturn(Single.just(DummyTextApi(testTextTwoWords)))

        textRepository.getRandomWord()
            .test().apply {
                awaitTerminalEvent()
            }

        textRepository.getRandomWord()
            .test().apply {
                awaitTerminalEvent()
            }

        textRepository.getRandomWord()
            .test().apply {
                awaitTerminalEvent()
            }

        verify(apiInterface, times(2)).getText()

    }
}