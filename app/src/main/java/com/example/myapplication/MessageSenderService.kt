package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * Take incoming messages from MQTT and forward them to the InstrumentedTestServer by sending
 * a web request to http://localhost:<5000-5099> (The range common for user-defined apps).
 */
class MessageSenderService(val topic: String, val port: Int = 5040) : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

     /**
      * Todo, write a method to subscribe to a topic and send unsolicited messages in response. That
      * implementation should call sendMessage to take care of evrything else on the android side
      * */

    /**
     * Forward an incoming message from MQTT, and return the response.
     */
    fun sendMessage(message: String): String {
        Log.d("topic=", "About to send another message for topic '$topic'.")
        val url = URL("http://localhost:$port/$topic/$message")
        try {
            var payload = ""
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            val writer = OutputStreamWriter(connection.outputStream)
            writer.write("message='$topic'")
            writer.flush()
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    payload += line
                }
                reader.close()

                Log.d(topic, "response: $payload")
            } else {
                Log.d(topic, "failure: $responseCode")
            }
            return payload
        } catch (e: IOException) {
            e.printStackTrace()
            return "$e"
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}