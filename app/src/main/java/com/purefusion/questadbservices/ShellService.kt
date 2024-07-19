package com.purefusion.questadbservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.cgutman.adblib.AdbCrypto
import com.purefusion.questadbservices.adblib.AdbUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShellService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            val crypto = AdbUtils.readCryptoConfig(filesDir) ?: AdbUtils.writeNewCryptoConfig(filesDir)
            if (crypto != null) {
                startAdbService(crypto)
            }
        }
        return START_STICKY
    }

    private fun startAdbService(crypto: AdbCrypto) {
        // Implement your logic to start ADB service with the given crypto
    }
}
