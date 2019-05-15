package io.coreflodev.exampleapplication.details.repo

import io.reactivex.Observable

class TypicodeDetailsRepository : DetailsRepository {

    override fun getPostForId(): Observable<DetailsRepository.Post> {
        return Observable.empty()
    }

}