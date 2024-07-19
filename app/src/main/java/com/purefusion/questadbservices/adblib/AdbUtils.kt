package com.purefusion.questadbservices.adblib

import com.cgutman.adblib.AdbCrypto
import java.io.File

object AdbUtils {
    private const val ADB_KEY_PATH = "adbkey"
    private const val ADB_KEY_PUB_PATH = "adbkey.pub"

    fun readCryptoConfig(filesDir: File): AdbCrypto? {
        val privateKeyFile = File(filesDir, ADB_KEY_PATH)
        val publicKeyFile = File(filesDir, ADB_KEY_PUB_PATH)
        return if (privateKeyFile.exists() && publicKeyFile.exists()) {
            try {
                AdbCrypto.loadAdbKeyPair(AndroidBase64(), privateKeyFile, publicKeyFile)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }

    fun writeNewCryptoConfig(filesDir: File): AdbCrypto? {
        val privateKeyFile = File(filesDir, ADB_KEY_PATH)
        val publicKeyFile = File(filesDir, ADB_KEY_PUB_PATH)
        return try {
            val crypto = AdbCrypto.generateAdbKeyPair(AndroidBase64())
            crypto.saveAdbKeyPair(privateKeyFile, publicKeyFile)
            crypto
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
