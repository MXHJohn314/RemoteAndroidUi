package com.example.myapplication


import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.util.MalformedJsonException
import androidx.annotation.RequiresApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * The recursive object that exists on the Android side of our remote procedure call
 * framework. It accepts a request equivalent to each of the public methods it exposes
 * for interaction with the UI element that it represents. In order to bridge the
 * fidelity gap of calling methods remotely on live objects, these objects are stored
 * in a Map when they are found by some locator method recursively, or from the find
 * methods of RemoteUiDevice.
 */
class AndroidUiObject {

}



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * Create a receiver to call the methods.
 */
@RunWith(AndroidJUnit4::class)
class UiDeviceAutomation {
    companion object {
        const val TAG = "UiDeviceAutomation"
    }
    val uiObjects: Map<String, UiObject2>? = mapOf()

}

class ARTestIoTAndroidAutomation {

    companion object {
        const val TAG = "ARTestIoTAndroidAutomation"
    }

    private val server = embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        install(CallLogging)

        routing {
            get("/") {
                call.respondText("Listening for messages...")
            }

            post("/messages") {
                val payload = call.receiveText()
                Log.i(TAG, "incoming message: $payload")
                try {
                    val remoteCall = Gson().fromJson(payload, RemoteCallDTO::class.java)
                    Log.d(TAG, "UiDevice.${remoteCall}")
                    val returnValue = call(remoteCall.methodName, *remoteCall.arguments)
                    Log.d(TAG, "SUCCESS! Return value: $returnValue")
                    val response = call(remoteCall.methodName, remoteCall.arguments)
                    val json = gson.toJson(response)
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
        }
    }


    /**
     * Invoke a method call on the Android device.
     */
    private fun call(methodName: String, vararg arguments: Any?): Any {
        // If the method name doesn't appear in the Map, then throw an exception.
        methodsByName[methodName] ?: return returnError("UnsupportedOperationException")

        Log.i(TAG, "attempting to run method $methodName")

        // Find a matching overload and use the associated lambda
        val methodGroup = methodsByName[methodName]!!

        // We can easily get a null pointer here
        for ((_, uiDeviceMethodAunner) in methodGroup) {
            try {
                val transformedArgs = arguments.map {
                    if (it is Number) it.toInt() else it
                }.toList()
                return uiDeviceMethodAunner.invoke(transformedArgs)!!
            } catch (e: Exception) {
                Log.e(TAG, "$e")
            }
        }
        return Exception("")
    }

    /**
     * convert to or from json
     */
    private val gson: Gson = Gson()

    private val conversions = mapOf(
        "int" to "java.lang.Integer",
        "bool" to "java.lang.Boolean",
        "boolean" to "java.lang.Boolean",
        "char" to "java.lang.Character",
        "long" to "java.lang.Long",
        "float" to "java.lang.Float",
        "double" to "java.lang.Double",
        "short" to "java.lang.Short",
        "byte" to "java.lang.Byte",
    )

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


    fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Got a message!")
        // Get the action that triggered the broadcast
        val action = intent.action

        // Handle different actions based on your needs
        when (action) {
            "com.example.myapplication.UI_DEVICE_REMOTE_CALL" -> {
                val name = intent.getStringExtra("methodName")!!
                val args = Gson().fromJson(intent.getStringExtra("args")!!, Map::class.java)!!
                Log.d(TAG, "UiDevice.$name(${args.keys})")
                val returnValue = call(name, args)
                Log.d(TAG, "SUCCESS! Return value: $returnValue")
            }
        }
    }

    @Test
    fun serveForever() {
        printHelp()
        Blah(server).start()
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

data class RemoteCallDTO(
    val methodName: String,
    val arguments: Array<out Any?>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteCallDTO

        if (methodName != other.methodName) return false
        if (!arguments.contentEquals(other.arguments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = methodName.hashCode()
        result = 31 * result + arguments.contentHashCode()
        return result
    }
}
