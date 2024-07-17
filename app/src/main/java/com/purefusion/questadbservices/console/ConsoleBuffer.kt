package com.purefusion.questadbservices.console

import android.widget.TextView

class ConsoleBuffer(private val size: Int) {
    private val buffer = CharArray(size)
    private var length = 0

    @Synchronized
    fun append(data: ByteArray, offset: Int, len: Int) {
        val appendLen = if (len > size - length) size - length else len
        for (i in 0 until appendLen) {
            buffer[length + i] = data[offset + i].toInt().toChar()
        }
        length += appendLen
    }

    @Synchronized
    fun clear() {
        length = 0
    }

    @Synchronized
    fun writeTo(textView: TextView) {
        textView.text = String(buffer, 0, length)
    }
}
