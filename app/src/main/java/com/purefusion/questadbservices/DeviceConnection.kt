package com.purefusion.questadbservices

import android.util.Log
import com.cgutman.adblib.AdbBase64
import com.cgutman.adblib.AdbCrypto
import com.cgutman.adblib.AdbStream
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

class DeviceConnection(
    private val host: String,
    private val port: Int,
    private val crypto: AdbCrypto
) {

    fun start() {
        try {
            val address = InetSocketAddress(host, port)
            val channel = SocketChannel.open(address)

            val stream = AdbStream(channel, crypto, AdbBase64.Default)
            stream.open("shell:vibrant shell")

            // Example command to verify the connection
            stream.write("echo 'Hello from ADB'\n")

            // Read the response
            val response = stream.read()
            Log.d("DeviceConnection", "Received response: $response")

        } catch (e: Exception) {
            Log.e("DeviceConnection", "Error starting ADB server", e)
        }
    }
}
