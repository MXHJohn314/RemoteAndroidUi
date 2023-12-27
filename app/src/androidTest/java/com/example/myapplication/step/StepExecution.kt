package com.example.myapplication.step

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import java.lang.StringBuilder

/**
 * Base class representing a remote step's execution.
 */
abstract class StepExecution : (String, Array<out Any?>) -> Any {
    companion object {
        val TAG: String = StepExecution::class.java.simpleName
        /**
         * convert to or from json
         */
        val gson: Gson = Gson()
    }

    protected val mDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    var isSevere: Boolean = false
    var statusCode = HttpStatusCode.OK
    protected val payload = StringBuilder()
    abstract val description: String
    protected fun log(s: String) {
        if (s.isEmpty()) {
            Log.e(TAG, "Empty strings are coming through local log method.")
            return
        }
        payload.append("$s\n")
        Log.d(TAG, s)
    }

    override fun invoke(methodName: String, arguments: Array<out Any?>):  StepExecutionResult {
        precondition()
        execute()
        return validate()
    }

    open fun precondition() {

    }
    open fun execute() {

    }
    open fun validate(): StepExecutionResult {
        return StepExecutionResult(statusCode, "$payload",
            // Tack on the exception if things went badly.
            if (isSevere) RemoteExecutionException(errorString)
            else null
        )
    }
    fun triage(
        errorString: String, statusCode: HttpStatusCode, isSevere: Boolean,
        vararg evidence: Any
    ) {
        this.isSevere = isSevere
        this.statusCode = statusCode
        log(errorString)
        log("$evidence")
    }

    abstract val errorString: String
    abstract val resourceId: String
}