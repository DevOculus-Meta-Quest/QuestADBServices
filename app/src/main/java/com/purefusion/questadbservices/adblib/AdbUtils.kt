package com.purefusion.questadbservices

import com.cgutman.adblib.AdbCrypto
import com.purefusion.questadbservices.adblib.AndroidBase64
import java.io.File

class AdbUtils {
    companion object {
        const val PRIVATE_KEY_NAME = "private.key"
        const val PUBLIC_KEY_NAME = "public.key"
    }

    fun readCryptoConfig(filesDir: File): AdbCrypto? {
        val publicKey = File(filesDir, PUBLIC_KEY_NAME)
        val privateKey = File(filesDir, PRIVATE_KEY_NAME)
        return if (publicKey.exists() && privateKey.exists()) {
            try {
                AdbCrypto.loadAdbKeyPair(AndroidBase64(), privateKey, publicKey)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun writeNewCryptoConfig(filesDir: File): AdbCrypto? {
        val publicKey = File(filesDir, PUBLIC_KEY_NAME)
        val privateKey = File(filesDir, PRIVATE_KEY_NAME)
        return try {
            val crypto = AdbCrypto.generateAdbKeyPair(AndroidBase64())
            crypto.saveAdbKeyPair(privateKey, publicKey)
            crypto
        } catch (e: Exception) {
            null
        }
    }
}
