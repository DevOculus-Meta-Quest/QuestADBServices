package com.purefusion.questadbservices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purefusion.questadbservices.ui.theme.QuestADBServicesTheme
import com.cgutman.adblib.AdbCrypto
import com.purefusion.questadbservices.adblib.AndroidBase64
import com.purefusion.questadbservices.adblib.AdbUtils
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuestADBServicesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            // Check for existing key and prompt user if not found
            if (AdbUtils.readCryptoConfig(filesDir) == null) {
                ShowKeyGenerationDialog()
            }
        }
    }
}

@Composable
fun ShowKeyGenerationDialog() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Generate Self-Signed Key") },
            text = { Text("No existing RSA key pair found. A self-signed key will be generated. This will only be done once.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        // Generate key pair
                        AdbUtils.writeNewCryptoConfig(File("/path/to/filesDir")) // Adjust path as necessary
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
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
