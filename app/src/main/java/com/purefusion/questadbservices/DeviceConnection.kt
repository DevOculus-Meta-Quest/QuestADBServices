package com.purefusion.questadbservices

import android.util.Log
import com.cgutman.adblib.AdbConnection
import com.cgutman.adblib.AdbCrypto
import java.net.Socket

class DeviceConnection(
    private val host: String,
    private val port: Int,
    private val crypto: AdbCrypto
) {
    fun start() {
        try {
            val socket = Socket(host, port)
            val adbConnection = AdbConnection.create(socket, crypto)
            adbConnection.connect()

            val adbStream = adbConnection.open("shell:vibrant shell")
            adbStream.write("echo 'Hello from ADB'\n")

            val response = adbStream.read()
            Log.d("DeviceConnection", "Received response: $response")
        } catch (e: Exception) {
            Log.e("DeviceConnection", "Error starting ADB server", e)
        }
    }
}
