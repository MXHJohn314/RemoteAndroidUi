package com.example.myapplication

import com.example.myapplication.step.login.LoginStep
import io.netty.handler.codec.http.HttpMethod

/**
 * To add a new step, start here, by defining the endpoint's suffix, and a reference to the
 * appropriate RemoteStepExecution.
 */
enum class UiServerOperation(
    val httpMethodType: HttpMethod,
    val path: String,
    val lambda: (String, Array<out Any?>) -> Any,
) {
    LOGIN(HttpMethod.PUT, "/login", LoginStep()),
    ;
}