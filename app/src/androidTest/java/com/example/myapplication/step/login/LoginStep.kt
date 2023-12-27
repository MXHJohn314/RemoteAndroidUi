package com.example.myapplication.step.login

import androidx.test.uiautomator.UiObject2
import com.example.myapplication.Hierarchy
import com.example.myapplication.step.StepExecutionResult
import com.example.myapplication.step.RemoteExecutionException
import com.example.myapplication.step.StepExecution
import io.ktor.http.HttpStatusCode


/**
 * Regular login step execution.
 */
class LoginStep : StepExecution() {
    val uiNode = Hierarchy.LoginScreen.USERNAME_TEXT_FIELD
    override val resourceId: String get() = uiNode.accessibilityId
    override val description: String get() = uiNode.description
    override val errorString: String get() = uiNode.failureToFindString
    private var usernameTextView: UiObject2? = null

    override fun precondition() {
        // One precondition is to check that the target app (which must be specified in the
        // arguments array) is installed on the device
        if (!mDevice.isScreenOn) {
            mDevice.wakeUp()
        }

        // Another precondition is to make sure we can find the objects we defined in the UiNode
        usernameTextView = uiNode.findUiObjectWithDevice(mDevice)
        if(usernameTextView == null) {
            triage(errorString, HttpStatusCode.PreconditionFailed, true)
        }
    }

    override fun execute() {
        if(isSevere) return
        log("here we go....")
        // If already logged in, check to see if isStrictLogin is true. If so, logout. Otherwise,
        // we're done.
        // Todo, what are the UI elements for logging in?
    }

    override fun validate(): StepExecutionResult {
        // Todo add logic to check for any views we should see upon completion
        return StepExecutionResult(statusCode, "$payload",
            // Tack on the exception if things went badly.
            if (isSevere) RemoteExecutionException(errorString)
            else null
        )
    }
}
