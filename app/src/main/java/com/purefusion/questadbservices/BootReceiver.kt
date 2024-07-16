package com.purefusion.questadbservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val adbServiceIntent = Intent(context, ShellService::class.java)
            context?.startService(adbServiceIntent)
        }
    }
}
