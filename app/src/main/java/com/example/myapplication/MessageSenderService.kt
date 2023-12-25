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
import java.util.Timer
import java.util.TimerTask


class MessageSenderService(val topic: String) : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun sendMessages() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Log.d("topic=", topic)
                try {
                    val url = URL("http://localhost:8080/messages")
                    val connection =
                        url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    val writer =
                        OutputStreamWriter(connection.outputStream)
                    writer.write("message='$topic'")
                    writer.flush()
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = connection.inputStream
                        val reader = BufferedReader(InputStreamReader(inputStream))
                        var payload: String? = ""
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            payload += line
                        }
                        reader.close()

                        Log.d(topic, "response: $payload")
                    } else {
                        Log.d(topic, "failure: $responseCode")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }, 3000, 3000)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}