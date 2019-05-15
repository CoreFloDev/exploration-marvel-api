package io.coreflodev.exampleapplication.common.arch

import io.reactivex.Observable

interface ScreenView<I : ScreenInput, O : ScreenOutput> {

    fun inputs() : Observable<I>

    fun render(output: O)
}