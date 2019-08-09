package com.isamotiuc.scrambleword.main_screen.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
    @GET("api")
    fun getText(): Single<DummyTextApi>
}

data class DummyTextApi(@SerializedName("text_out") val text: String)
