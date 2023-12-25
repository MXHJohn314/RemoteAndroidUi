package com.example.myapplication

import MessageSenderService
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding


// Replace with your package name


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serviceIntent = Intent(this, MessageSenderService::class.java)
        startService(serviceIntent)


        setContentView(ActivityMainBinding.inflate(layoutInflater).root) // Assuming you have an activity_main layout
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@RunWith(AndroidJUnit4::class)
//class MainActivity : AppCompatActivity() {
//    /**
//     * Store the incoming response message in this string.
//     */
//    var callback: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        printHelp()
//        val filter =
//            IntentFilter("com.example.myapplication.UI_DEVICE_REMOTE_CALL") // Specify actions
//        val intent =
//            Intent(InstrumentationRegistry.getInstrumentation().targetContext, this::class.java)
//        intent.putExtra("message", "Hello from Kotlin!")
//
//        // Send a broadcast to ourselves
//        InstrumentationRegistry.getInstrumentation().targetContext.sendBroadcast(intent)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    companion object {
//        const val TAG = "UiDeviceAutomation"
//    }
//
//    /**
//     * convert to or from json
//     */
//    private val gson: Gson = Gson()
//
//    private val conversions = mapOf(
//        "int" to "java.lang.Integer",
//        "bool" to "java.lang.Boolean",
//        "boolean" to "java.lang.Boolean",
//        "char" to "java.lang.Character",
//        "long" to "java.lang.Long",
//        "float" to "java.lang.Float",
//        "double" to "java.lang.Double",
//        "short" to "java.lang.Short",
//        "byte" to "java.lang.Byte",
//    )
//
//    /**
//     * We only support methods that pass primitives, and throw UnsupportedOperationException
//     * if non=primitive args are passed. This may change in the future if we add support for
//     * UIWatchers, Selectors, Conditions, etc.
//     */
//    private val methodsByName = UiDevice::class.java.methods.groupBy { it.name }
//        .values.map { overloads ->
//            overloads.first().name to overloads.filter { method ->
//                method.parameterTypes.all { it.isPrimitive }
//            }
//        }.filterNot { it.second.isEmpty() }
//        .toMap()
//        .mapValues { (_, methods) ->
//            // Get a local reference to the singleton UiDevice object.
//            val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//            // For each method, store a function that will take in the args and invoke that method
//            // using the UiDevice object.
//            methods.map { method ->
//                method to { args: List<Any?> ->
//                    method.invoke(uiDevice, *args.toTypedArray())
//                }
//            }
//        }
//
//    // Do not throw, but return the exception object.
//    private fun returnError(s: String): Exception {
//        Log.e(TAG, "Error: $s")
//        return Exception(s)
//    }
//
//    /**
//     * Invoke a method call on the Android device.
//     */
//    @RequiresApi(Build.VERSION_CODES.P)
//    private fun call(
//        methodName: String,
//        vararg arguments: Any?
//    ): Any {
//        // If the method name doesn't appear in the Map, then throw an exception.
//        methodsByName[methodName] ?: return returnError("UnsupportedOperationException")
//
//        Log.i(TAG, "attempting to run method $methodName")
//
//        // Find a matching overload and use the associated lambda
//        val methodGroup = methodsByName[methodName]!!
//
//        // We can easily get a null pointer here
//        try {
//            val (_, handlerLambda) = methodGroup.first { (method, _) ->
//                // Look at each pair of parameter type and arg
//                method.parameters.map {
//                    it.parameterizedType
//                }.zip(arguments)
//
//                    // Make sure all parameters' types can be assigned from the values parsed from
//                    // the payload.
//                    .all { (paramType, arg) ->
//                        var isAssignable = false
//                        Log.d(TAG, "paramType.typeName=${paramType.typeName}")
//                        try {
//                            Class.forName(paramType.typeName).cast(arg)
//                            isAssignable = true
//                        } catch (_: Exception) {
//                        }
//                        try {
//                            Class.forName(conversions[paramType.typeName]!!).cast(arg)
//                            isAssignable = true
//                        } catch (_: Exception) {
//                        }
//                        isAssignable
//                    }
//            }
//
//            // Run the lambda
//            return handlerLambda.invoke(arguments.toList())!!
//        } catch (e: Exception) {
//            return returnError(e.message!!)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.P)
//    fun isAssignableFrom(type: Type): Boolean {
//        try {
//            Class.forName(type.typeName)
//            return true
//        } catch (_: Exception) {
//            return false
//        }
//    }
//    /*override fun onReceive(context: Context, intent: Intent) {
//        Log.i(TAG, "Got a message!")
//        // Get the action that triggered the broadcast
//        val action = intent.action
//
//        // Handle different actions based on your needs
//        when (action) {
//            "com.example.myapplication.UI_DEVICE_REMOTE_CALL" -> {
//                val name = intent.getStringExtra("methodName")!!
//                val args = Gson().fromJson(intent.getStringExtra("args")!!, Map::class.java)!!
//                Log.d(TAG, "UiDevice.$name(${args.keys})")
//                val returnValue = call(name, args)
//                Log.d(TAG, "SUCCESS! Return value: $returnValue")
//            }
//        }
//    }*/
//
//    /**
//     * Print a json Log showing all the methods in the api.
//     */
//    private fun printHelp() {
//        Log.i(
//            TAG, "Help: ${
//                "[" + methodsByName.map { (name, methodAndLambdaPair) ->
//                    "$name:${
//                        methodAndLambdaPair.map { (method, _) ->
//                            method.parameters.map { param ->
//                                "${param.name}:${param.type}"
//                            }
//                        }.joinToString { "," }
//                    }"
//                }.joinToString { "],[" } + "]"
//            }"
//        )
//    }
//}


