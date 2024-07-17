package com.purefusion.questadbservices

import com.cgutman.adblib.AdbCrypto
import java.io.File

object AdbUtils {
    fun readCryptoConfig(filesDir: File): AdbCrypto? {
        val privateKey = File(filesDir, "private.key")
        val publicKey = File(filesDir, "public.key")
        return if (privateKey.exists() && publicKey.exists()) {
            AdbCrypto.loadAdbKeyPair(AndroidBase64(), privateKey, publicKey)
        } else {
            null
        }
    }

    fun writeNewCryptoConfig(filesDir: File): AdbCrypto? {
        return try {
            val crypto = AdbCrypto.generateAdbKeyPair(AndroidBase64())
            crypto.saveAdbKeyPair(File(filesDir, "private.key"), File(filesDir, "public.key"))
            crypto
        } catch (e: Exception) {
            null
        }
    }
}
