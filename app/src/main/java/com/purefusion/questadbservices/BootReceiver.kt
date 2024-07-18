package com.purefusion.questadbservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device rebooted, starting ShellService to set ADB port")
            val serviceIntent = Intent(context, ShellService::class.java)
            context.startService(serviceIntent)
        }
    }
}
