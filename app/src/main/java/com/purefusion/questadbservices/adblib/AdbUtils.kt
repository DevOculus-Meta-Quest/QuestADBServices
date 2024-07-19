package com.purefusion.questadbservices.adblib

import com.cgutman.adblib.AdbCrypto
import java.io.File

object AdbUtils {

    fun readCryptoConfig(dir: File): AdbCrypto? {
        return try {
            val keyFile = File(dir, "adbkey")
            val pubFile = File(dir, "adbkey.pub")
            if (keyFile.exists() && pubFile.exists()) {
                AdbCrypto.load(AndroidBase64(), keyFile, pubFile)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun writeNewCryptoConfig(dir: File): AdbCrypto? {
        return try {
            val keyFile = File(dir, "adbkey")
            val pubFile = File(dir, "adbkey.pub")
            val crypto = AdbCrypto.generateAdbKeyPair(AndroidBase64())
            crypto.saveAdbKeyPair(keyFile, pubFile)
            crypto
        } catch (e: Exception) {
            null
        }
    }
}
