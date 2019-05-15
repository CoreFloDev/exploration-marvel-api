package io.coreflodev.exampleapplication.details.repo

import io.reactivex.Observable

interface DetailsRepository {

    fun getPostForId(): Observable<Post>

    data class Post(
        val title: String,
        val body: String,
        val userName: String,
        val numberOfComments: String
    )
}