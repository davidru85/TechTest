package com.ruizurraca.techtest.domain.usecase.base

import com.ruizurraca.techtest.domain.model.ErrorModel

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}
