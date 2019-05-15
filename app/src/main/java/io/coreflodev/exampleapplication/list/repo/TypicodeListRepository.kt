package io.coreflodev.exampleapplication.list.repo

import io.coreflodev.exampleapplication.list.injection.ListScope
import io.reactivex.Observable
import retrofit2.Retrofit
import javax.inject.Inject

@ListScope
class TypicodeListRepository @Inject constructor(
    val retrofit: Retrofit
) : ListRepository {

    override fun getList(): Observable<Any> {
        return Observable.empty<Any>()
    }

}
