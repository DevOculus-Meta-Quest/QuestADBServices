package com.purefusion.questadbservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.cgutman.adblib.AdbCrypto

class ShellService : Service() {
    private lateinit var deviceConnection: DeviceConnection

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun startService(host: String, port: Int, crypto: AdbCrypto) {
        deviceConnection = DeviceConnection(host, port, crypto)
        deviceConnection.start()
    }
}
