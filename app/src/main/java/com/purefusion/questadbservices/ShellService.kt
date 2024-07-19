package com.purefusion.questadbservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.cgutman.adblib.AdbCrypto
import java.io.File

class ShellService : Service() {

    private lateinit var deviceConnection: DeviceConnection

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            val crypto = AdbUtils.readCryptoConfig(filesDir) ?: AdbUtils.writeNewCryptoConfig(filesDir)
            if (crypto != null) {
                val port = getAdbWirelessPort()
                startDeviceConnection("localhost", port, crypto)
            } else {
                Log.e("ShellService", "Failed to generate/read RSA keys")
            }
        }
        return START_STICKY
    }

    private fun startDeviceConnection(host: String, port: Int, crypto: AdbCrypto) {
        deviceConnection = DeviceConnection(host, port, crypto)
        deviceConnection.start()
    }

    private fun getAdbWirelessPort(): Int {
        val getPortCommand = "getprop service.adb.tcp.port"
        val output = executeShellCommand(getPortCommand)
        return output.trim().toIntOrNull() ?: 5555 // Default to 5555 if port retrieval fails
    }

    private fun executeShellCommand(command: String): String {
        val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuilder()
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            output.append(line).append("\n")
        }

        process.waitFor()
        return output.toString()
    }
}
