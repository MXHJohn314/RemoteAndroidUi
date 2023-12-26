package com.example.myapplication.step

/**
 * The exception that we turn into json and send with the payload if any exception occurs throughout
 * the step.
 */
data class RemoteExecutionException(val errorString: String) : Exception(errorString)