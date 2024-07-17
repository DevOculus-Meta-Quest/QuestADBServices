package com.purefusion.questadbservices.adblib

import com.cgutman.adblib.AdbCrypto
import java.io.File

class AdbUtils {
    fun readCryptoConfig(filesDir: File): AdbCrypto? {
        val keyFile = File(filesDir, "adbkey")
        val pubFile = File(filesDir, "adbkey.pub")
        return if (keyFile.exists() && pubFile.exists()) {
            AdbCrypto.loadAdbKeyPair(AndroidBase64(), keyFile, pubFile)
        } else {
            null
        }
    }

    fun writeNewCryptoConfig(filesDir: File): AdbCrypto? {
        val keyFile = File(filesDir, "adbkey")
        val pubFile = File(filesDir, "adbkey.pub")
        val crypto = AdbCrypto.generateAdbKeyPair(AndroidBase64())
        crypto.saveAdbKeyPair(keyFile, pubFile)
        return crypto
    }
}
