package io.coreflodev.exampleapplication.common.arch

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Screen<I : ScreenInput, O : ScreenOutput> {

    private val disposables = CompositeDisposable()

    protected abstract fun onAttach(view: ScreenView<I, O>)

    fun attach(view: ScreenView<I, O>) {
        onAttach(view)
    }

    fun add(vararg disposable: Disposable) {
        disposables.addAll(*disposable)
    }

    fun detach() {
        disposables.clear()
    }
}