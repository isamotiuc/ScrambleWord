package com.isamotiuc.scrambleword.main_screen.data

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TextRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    private var wordsList: HashSet<String> = hashSetOf()

    fun getRandomWord(): Single<String> {
        return if (wordsList.isEmpty()) {
            apiInterface.getText()
                .map { textApi -> textApi.text.toLowerCase() }
                .map { text -> text.splitWords() }
                .flattenAsObservable { it }
                .filter { word -> word.length > 3 }
                .toList()
                .map { HashSet<String>(it) }
                .doOnSuccess { this.wordsList = it }
                .map { wordsList ->
                    pickAndDelete(wordsList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        } else {
            Single.just(pickAndDelete(wordsList))
        }
    }

    private fun pickAndDelete(wordsList: HashSet<String>) =
        wordsList.random().apply {
            wordsList.remove(this)
        }

    private fun String.splitWords() = this.split("\\W+".toRegex())

}