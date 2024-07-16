package com.purefusion.questadbservices

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
import com.purefusion.questadbservices.adblib.AndroidBase64
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
                startAdbServer()
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

    private fun startAdbServer() {
        // Your logic to start the ADB server
        // This is a placeholder and should be replaced with actual code to start ADB server
        val host = "localhost"
        val port = 5555
        val deviceConnection = DeviceConnection(host, port, AdbUtils.readCryptoConfig(filesDir)!!)
        deviceConnection.start()
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
