package com.purefusion.questadbservices.adblib

import com.cgutman.adblib.AdbCrypto
import java.io.Closeable
import java.io.File
import java.io.IOException

object AdbUtils {
    const val PRIVATE_KEY_NAME = "private.key"
    const val PUBLIC_KEY_NAME = "public.key"

    fun readCryptoConfig(file: File): AdbCrypto? {
        val publicKeyFile = File(file, PUBLIC_KEY_NAME)
        val privateKeyFile = File(file, PRIVATE_KEY_NAME)
        return if (publicKeyFile.exists() && privateKeyFile.exists()) {
            try {
                AdbCrypto.loadAdbKeyPair(AndroidBase64(), privateKeyFile, publicKeyFile)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun writeNewCryptoConfig(file: File): AdbCrypto? {
        val publicKeyFile = File(file, PUBLIC_KEY_NAME)
        val privateKeyFile = File(file, PRIVATE_KEY_NAME)
        return try {
            val adbCrypto = AdbCrypto.generateAdbKeyPair(AndroidBase64())
            adbCrypto.saveAdbKeyPair(privateKeyFile, publicKeyFile)
            adbCrypto
        } catch (e: Exception) {
            null
        }
    }

    fun safeClose(closeable: Closeable?): Boolean {
        return try {
            closeable?.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    fun safeAsyncClose(closeable: Closeable?) {
        if (closeable == null) return
        Thread {
            try {
                closeable.close()
            } catch (e: IOException) {
            }
        }.start()
    }
}
