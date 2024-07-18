package com.purefusion.questadbservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

class ShellService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        enableAdbOverTcp()
        return START_STICKY
    }

    private fun enableAdbOverTcp() {
        try {
            val process = Runtime.getRuntime().exec("setprop service.adb.tcp.port 5555 && stop adbd && start adbd")
            process.waitFor()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                Log.d("ShellService", line ?: "")
            }
        } catch (e: Exception) {
            Log.e("ShellService", "Error enabling ADB over TCP/IP", e)
        }
    }
}
