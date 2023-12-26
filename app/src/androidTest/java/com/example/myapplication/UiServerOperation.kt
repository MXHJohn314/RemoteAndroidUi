package com.example.myapplication

import com.example.myapplication.step.login.LoginStep
import com.example.myapplication.step.startworkflow.StartWorkflow
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
    START_WORKFLOW(HttpMethod.POST, "/start-workflow", StartWorkflow()),
    LOGIN(HttpMethod.PUT, "/login", LoginStep()),
    ;
}