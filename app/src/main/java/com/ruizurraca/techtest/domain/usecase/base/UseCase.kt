package com.ruizurraca.techtest.domain.usecase.base

import android.util.Log
import com.ruizurraca.techtest.domain.exception.ApiErrorHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class UseCase<Type, in Params>(private val apiErrorHandle: ApiErrorHandle) where Type : Any {

    abstract suspend fun run(params: Params? = null): Type

    @ExperimentalCoroutinesApi
    fun invoke(
        params: Params?,
        onResult: (UseCaseResponse<Type>)?
    ) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = run(params)
                onResult?.onSuccess(result)
                Log.d(TAG, "Response: $result")
            } catch (e: CancellationException) {
                Log.d(TAG, "Error: $e")
                onResult?.onError(apiErrorHandle.traceErrorException(e))
            } catch (e: Exception) {
                Log.d(TAG, "Error: $e cause: ${e.cause}")
                onResult?.onError(apiErrorHandle.traceErrorException(e))
            }
        }
    }

    companion object {
        private val TAG = UseCase::class.java.name
    }

}