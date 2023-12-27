package com.example.myapplication


import android.os.Build
import android.util.Log
import android.util.MalformedJsonException
import androidx.annotation.RequiresApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.myapplication.step.RemoteExecutionException
import com.example.myapplication.step.StepExecutionRequest
import com.example.myapplication.step.StepExecutionResult
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * Create a receiver to call the methods.
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTestServer
    : BaseUiTestServer<StepExecutionRequest, StepExecutionResult>() {
    companion object {
        const val TAG = "InstrumentedTestServer"
        private val gson = Gson()
    }
    init {
        val c = Class.forName("android.os.SystemProperties")
        val get = c.getMethod("get", String::class.java)
        Log.d(TAG, "this device's serialNumber is: '${get.invoke(c, " sys . serialnumber ", " unknown ") as String}'")
    }

    /**
     * This server setup allows us to get messages from the mqtt receiver, as well as applications
     * under test.
     */
    private val server = embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        install(CallLogging)

        /**
         * Add each method step here, and make sure the associated UiServerOperation and
         * StepExecution have been implemented.
         */
        routing {
            post(UiServerOperation.LOGIN.path) { execute(UiServerOperation.LOGIN) }
            post(UiServerOperation.START_WORKFLOW.path) { execute(UiServerOperation.START_WORKFLOW) }
        }
    }

    /**
     * This extension function processes a UiServerOperation.
     */
    private suspend fun PipelineContext<Unit, ApplicationCall>.execute(
        operation: UiServerOperation
    ) {
        try {
            val remoteCall = Gson().fromJson(call.receiveText(), StepExecutionRequest::class.java)
            Log.d(TAG, "UiDevice.${remoteCall}")
            val result = operation.lambda.invoke(remoteCall.methodName, remoteCall.arguments)
                as StepExecutionResult
            val json = gson.toJson(result)
            call.respondText(
                "Delivering response message: $json",
                status = HttpStatusCode.OK
            )
        } catch (e: Exception) {
            call.respondText {
                gson.toJson(MalformedJsonException("Error: $e"))
            }
        }
    }

    /**
     * @experimental Invoke a UiDevice method call directly by passing its name and arguments.
     * Methods that have complex parameters are not supported.
     */
    private fun call(methodName: String, vararg arguments: Any?): Any {
        // If the method name doesn't appear in the Map, then throw an exception.
        methodsByName[methodName] ?: return returnError("UnsupportedOperationException")

        Log.i(TAG, "attempting to run method $methodName")

        // Find a matching overload and use the associated lambda
        val methodGroup = methodsByName[methodName]!!

        for ((_, uiDeviceMethodRunningLambda) in methodGroup) {
            try {
                val transformedArgs = arguments.map { arg ->
                    // Sometimes Integers become Doubles during transmission of requests,
                    //  and need to be re-casted to integers. There are no doubles in our
                    //  api.
                    if (arg is Number) arg.toInt() else arg
                }.toList()
                return uiDeviceMethodRunningLambda.invoke(transformedArgs)!!
            } catch (e: Exception) {
                Log.e(TAG, "$e")
            }
        }
        return RemoteExecutionException("No method $methodName could be invoked with the given" +
                " args: $arguments")
    }


    /**
     * We only support methods that pass primitives, and throw UnsupportedOperationException
     * otherwise.
     */
    private val methodsByName = UiDevice::class.java.methods.groupBy { it.name }
        .values.map { overloads ->
            overloads.first().name to overloads.filter { method ->
                method.parameterTypes.all { it.isPrimitive }
            }
        }.filterNot { it.second.isEmpty() }
        .toMap()
        .mapValues { (_, methods) ->
            // Get a local reference to the singleton UiDevice object.
            val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            // For each method, store a function that will take in the args and invoke that method
            // using the UiDevice object.
            methods.map { method ->
                method to { args: List<Any?> ->
                    method.invoke(uiDevice, *args.toTypedArray())
                }
            }
        }

    // Do not throw, but return the exception object.
    private fun returnError(s: String): Exception {
        Log.e(TAG, "Error: $s")
        return Exception(s)
    }

    @Test
    fun serveForever() {
        printHelp()
        AndroidCommandSever(server).start()
    }

    /**
     * Print a json Log showing all the methods in the api.
     */
    private fun printHelp() {
        Log.i(
            TAG,
            "{\"help\": ${
                gson.toJson(methodsByName.entries.map { (name, methodAndLambdaPair) ->
                    mapOf("$name:" to methodAndLambdaPair.flatMap { (method, _) ->
                        method.parameters.map { param ->
                            "${param.type}"
                        }
                    })
                })
            }}".replace(" ", "")
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun isAssignableFrom(type: String, arg: Any?): Any {
        return try {
            Class.forName(type).cast(arg)
            true
        } catch (_: Exception) {
        }
    }
}