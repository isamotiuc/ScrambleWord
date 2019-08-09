package com.isamotiuc.scrambleword.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<P : BasePresenter<T>, T : LifecycleOwner> : AppCompatActivity() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getViewResourceId())
        presenter.takeTarget(this as T)
    }

    @LayoutRes
    protected abstract fun getViewResourceId(): Int
}