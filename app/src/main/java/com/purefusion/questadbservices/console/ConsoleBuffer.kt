package com.purefusion.questadbservices.console

import android.widget.TextView

class ConsoleBuffer(private val bufferSize: Int) {
    private var amountPopulated = 0
    private val buffer = CharArray(bufferSize)

    @Synchronized
    fun append(data: ByteArray, offset: Int, length: Int) {
        if (amountPopulated + length > buffer.size) {
            System.arraycopy(buffer, length, buffer, 0, amountPopulated - length)
            amountPopulated -= length
        }
        for (i in 0 until length) {
            buffer[amountPopulated++] = data[offset + i].toChar()
        }
    }

    @Synchronized
    fun updateTextView(textView: TextView) {
        textView.text = String(buffer, 0, amountPopulated)
    }
}
