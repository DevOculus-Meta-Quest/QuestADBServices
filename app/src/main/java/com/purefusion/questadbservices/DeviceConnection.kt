package com.purefusion.questadbservices

import com.purefusion.questadbservices.console.ConsoleBuffer
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class DeviceConnection(private val host: String, private val port: Int) {

    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private val listeners = mutableListOf<DeviceConnectionListener>()
    val consoleBuffer = ConsoleBuffer(1024) // Adjust buffer size as needed

    fun startConnect() {
        // Implement connection logic
        Thread {
            try {
                socket = Socket(host, port)
                inputStream = socket!!.getInputStream()
                outputStream = socket!!.getOutputStream()
                listeners.forEach { it.notifyConnectionEstablished(this) }
                readData()
            } catch (e: Exception) {
                listeners.forEach { it.notifyConnectionFailed(this, e) }
            }
        }.start()
    }

    private fun readData() {
        val buffer = ByteArray(1024)
        try {
            while (true) {
                val length = inputStream!!.read(buffer)
                if (length == -1) break
                consoleBuffer.append(buffer, 0, length)
                listeners.forEach { it.consoleUpdated(this, consoleBuffer) }
            }
        } catch (e: Exception) {
            listeners.forEach { it.notifyStreamFailed(this, e) }
        } finally {
            listeners.forEach { it.notifyStreamClosed(this) }
        }
    }

    fun queueCommand(command: String) {
        outputStream?.write(command.toByteArray())
    }

    fun queueBytes(bytes: ByteArray) {
        outputStream?.write(bytes)
    }

    fun addListener(listener: DeviceConnectionListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: DeviceConnectionListener) {
        listeners.remove(listener)
    }
}
