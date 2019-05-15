package io.coreflodev.exampleapplication.list.repo

import io.reactivex.Observable

interface ListRepository {

    fun getList() : Observable<Any>
}
