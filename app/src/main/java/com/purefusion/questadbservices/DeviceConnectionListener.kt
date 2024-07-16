package com.purefusion.questadbservices

import com.cgutman.adblib.AdbCrypto
import com.purefusion.questadbservices.console.ConsoleBuffer

interface DeviceConnectionListener {
    fun canReceiveData(): Boolean
    fun isConsole(): Boolean
    fun receivedData(deviceConnection: DeviceConnection, data: ByteArray, offset: Int, length: Int)
    fun notifyConnectionEstablished(deviceConnection: DeviceConnection)
    fun notifyConnectionFailed(deviceConnection: DeviceConnection, exception: Exception)
    fun notifyStreamFailed(deviceConnection: DeviceConnection, exception: Exception)
    fun notifyStreamClosed(deviceConnection: DeviceConnection)
    fun loadAdbCrypto(deviceConnection: DeviceConnection): AdbCrypto?
    fun consoleUpdated(deviceConnection: DeviceConnection, consoleBuffer: ConsoleBuffer)
}
