package com.purefusion.questadbservices

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purefusion.questadbservices.ui.theme.QuestADBServicesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable ADB Wireless if not already enabled
        enableAdbWireless()

        // Start ShellService to enable ADB over TCP
        val serviceIntent = Intent(this, ShellService::class.java)
        startService(serviceIntent)

        // Show main UI
        setContent {
            QuestADBServicesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun enableAdbWireless() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Enable ADB over Wi-Fi
                Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, 1)
                Log.d("MainActivity", "ADB Wireless Enabled")
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to enable ADB Wireless", e)
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        QuestADBServicesTheme {
            Greeting("Android")
        }
    }
}
