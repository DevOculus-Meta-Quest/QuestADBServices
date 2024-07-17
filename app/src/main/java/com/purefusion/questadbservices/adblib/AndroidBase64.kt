// AndroidBase64.kt
package com.purefusion.questadbservices.adblib

import android.util.Base64
import com.cgutman.adblib.AdbBase64

class AndroidBase64 : AdbBase64 {
    override fun encodeToString(data: ByteArray): String {
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }
}
