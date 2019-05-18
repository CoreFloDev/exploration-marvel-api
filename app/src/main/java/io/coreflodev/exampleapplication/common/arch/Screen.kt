package io.coreflodev.exampleapplication.common.arch

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class Screen<I : ScreenInput, O : ScreenOutput> {

    private val disposables = CompositeDisposable()

    protected val input: Subject<I> = PublishSubject.create()

    protected abstract fun output(): Observable<O>

    fun attach(view: ScreenView<I, O>) {
        disposables.addAll(
            output().observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render),

            view.inputs()
                .subscribe(input::onNext)
        )
    }

    fun detach() {
        disposables.clear()
    }
}
