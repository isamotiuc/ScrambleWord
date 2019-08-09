package com.isamotiuc.scrambleword

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import com.isamotiuc.scrambleword.base.BasePresenter
import com.nhaarman.mockito_kotlin.reset
import org.mockito.Mockito

abstract class PresenterTestCase<P : BasePresenter<T>, T> : BaseTestCase()
        where T : LifecycleOwner {

    abstract var presenter: P
    abstract var targetMock: T

    abstract val targetClazz: Class<T>

    @CallSuper
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        targetMock = Mockito.mock(targetClazz)
        presenter.takeTarget(targetMock)
        reset(targetMock)
    }

}
