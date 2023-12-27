package com.example.myapplication

import android.content.ComponentName
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


// Replace with your package name


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startInstrumentation(
            ComponentName(
                "com.example.myapplication.test", // Replace with your test package
                "androidx.test.runner.AndroidJUnitRunner" // Use the updated runner
            ),
            null,
            null
        )
    }
}