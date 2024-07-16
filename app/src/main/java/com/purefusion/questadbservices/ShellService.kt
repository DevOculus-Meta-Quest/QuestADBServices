package com.purefusion.questadbservices

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class ShellService : Service() {

    private val binder = ShellServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class ShellServiceBinder : Binder() {
        fun createConnection(host: String, port: Int): DeviceConnection {
            return DeviceConnection(host, port)
        }

        fun findConnection(host: String, port: Int): DeviceConnection? {
            // Implement connection lookup logic
            return null
        }

        fun addListener(connection: DeviceConnection, listener: DeviceConnectionListener) {
            connection.addListener(listener)
        }

        fun removeListener(connection: DeviceConnection, listener: DeviceConnectionListener) {
            connection.removeListener(listener)
        }

        fun notifyDestroyingActivity(connection: DeviceConnection) {
            // Handle activity destruction
        }

        fun notifyResumingActivity(connection: DeviceConnection) {
            // Handle activity resuming
        }

        fun notifyPausingActivity(connection: DeviceConnection) {
            // Handle activity pausing
        }
    }
}
