package com.example.myapplication.step

import io.ktor.http.HttpStatusCode

/**
 * The object that will be converted into a json string and sent as the message's payload.
 */
class StepExecutionResult(
    val statusCode: HttpStatusCode,
    val payload: String,
    val exception: Exception? = null,
)