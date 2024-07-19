package com.purefusion.questadbservices

import android.app.Service
import android.content.ContentResolver
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import com.cgutman.adblib.AdbConnection
import com.cgutman.adblib.AdbCrypto
import com.purefusion.questadbservices.adblib.AdbUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket

class ShellService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            val crypto = AdbUtils.readCryptoConfig(filesDir) ?: AdbUtils.writeNewCryptoConfig(filesDir)
            if (crypto != null) {
                val port = readAdbPort()
                if (port != null) {
                    connectToAdb(crypto, port)
                } else {
                    Log.e("ShellService", "Failed to read ADB port")
                }
            } else {
                Log.e("ShellService", "Failed to generate or read the ADB crypto configuration")
            }
        }
        return START_STICKY
    }

    private fun readAdbPort(): Int? {
        return try {
            // Read the current ADB port from system settings
            val port = Settings.Global.getInt(contentResolver, "adb_port")
            Log.d("ShellService", "Current ADB port: $port")
            port
        } catch (e: Exception) {
            Log.e("ShellService", "Failed to read ADB port", e)
            null
        }
    }

    private fun connectToAdb(crypto: AdbCrypto, port: Int) {
        try {
            val socket = Socket("localhost", port)
            val adbConnection = AdbConnection.create(socket, crypto)
            adbConnection.connect()

            val adbStream = adbConnection.open("shell:vibrant shell")

            // Example command to verify the connection
            adbStream.write("echo 'Hello from ADB'\n")

            // Read the response
            val response = adbStream.read()
            Log.d("ShellService", "Received response: $response")

        } catch (e: Exception) {
            Log.e("ShellService", "Error starting ADB server", e)
        }
    }
}
