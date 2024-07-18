package com.purefusion.questadbservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class ShellService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ShellService", "Service started")
        CoroutineScope(Dispatchers.IO).launch {
            if (!isAdbWirelessEnabled()) {
                enableAdbWireless()
            }
            val port = readAdbPort()
            if (port != null) {
                connectToAdb(port)
            } else {
                Log.e("ShellService", "Failed to read ADB port")
            }
        }
        return START_STICKY
    }

    private fun isAdbWirelessEnabled(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", "getprop service.adb.tcp.port"))
            val inputStream = BufferedReader(InputStreamReader(process.inputStream))
            val port = inputStream.readLine().trim()
            port.isNotEmpty()
        } catch (e: Exception) {
            Log.e("ShellService", "Error checking ADB wireless status", e)
            false
        }
    }

    private fun enableAdbWireless() {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", "setprop service.adb.tcp.port 5555 && stop adbd && start adbd"))
            process.waitFor()
            Log.d("ShellService", "ADB wireless enabled")
        } catch (e: Exception) {
            Log.e("ShellService", "Error enabling ADB wireless", e)
        }
    }

    private fun readAdbPort(): Int? {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", "getprop service.adb.tcp.port"))
            val inputStream = BufferedReader(InputStreamReader(process.inputStream))
            val port = inputStream.readLine().trim()
            port.toIntOrNull()
        } catch (e: Exception) {
            Log.e("ShellService", "Error reading ADB port", e)
            null
        }
    }

    private fun connectToAdb(port: Int) {
        // Your logic to connect to ADB using the read port and self-signed RSA keys
        Log.d("ShellService", "Connecting to ADB on port: $port")
        // Implement the connection logic here
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
