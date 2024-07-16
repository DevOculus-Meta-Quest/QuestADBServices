package com.purefusion.questadbservices

import android.content.Intent
import android.os.Bundle
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
import com.cgutman.adblib.AdbCrypto
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start ADB server in a coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val adbUtils = AdbUtils()
            val crypto = adbUtils.readCryptoConfig(filesDir) ?: adbUtils.writeNewCryptoConfig(filesDir)
            if (crypto != null) {
                startAdbServer(crypto)
            } else {
                runOnUiThread {
                    showErrorDialog()
                }
            }
        }

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

    private fun startAdbServer(crypto: AdbCrypto) {
        val host = "localhost"
        val port = 5555

        val intent = Intent(this, ShellService::class.java)
        startService(intent)

        val shellService = ShellService()
        shellService.startService(host, port, crypto)
        Log.d("MainActivity", "ADB server started on $host:$port")
    }

    private fun showErrorDialog() {
        // Your logic to show an error dialog if the certificate generation fails
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
