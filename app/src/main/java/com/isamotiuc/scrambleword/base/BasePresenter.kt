package com.isamotiuc.scrambleword.base

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T> : LifecycleObserver where T : LifecycleOwner {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected var target: T? = null

    protected fun addSubscription(subscription: Disposable): Disposable =
            subscription.apply { compositeDisposable.add(subscription) }

    private fun dropTarget() {
        this.target?.lifecycle?.removeObserver(this)
        this.target = null
        compositeDisposable.dispose()
    }

    @CallSuper
    fun takeTarget(targetIn: T) {
        this.target = targetIn
        this.target?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        dropTarget()
    }
}