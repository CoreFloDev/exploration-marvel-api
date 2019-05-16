package io.coreflodev.exampleapplication.details.use_cases

import io.coreflodev.exampleapplication.details.DetailsViewModel

sealed class Result {

    data class Display(val data: DetailsViewModel) : Result()

    object Error : Result()

    object Loading : Result()
}
