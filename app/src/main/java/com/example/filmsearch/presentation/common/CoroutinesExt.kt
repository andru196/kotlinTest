package com.example.filmsearch.presentation.common

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchWithErrorHandler(
    onError: (Throwable) -> Unit = {},
    block: suspend () -> Unit
) {
    launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("TopFilmsViewModel", throwable.message, throwable)
        onError(throwable)
    }) {
        block()
    }

}