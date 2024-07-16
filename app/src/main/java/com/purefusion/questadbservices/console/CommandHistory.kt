package com.purefusion.questadbservices.console

import android.content.Context
import android.content.SharedPreferences
import android.view.ContextMenu
import java.util.LinkedList

class CommandHistory(private val historyLimit: Int) {
    private lateinit var prefs: SharedPreferences
    private val previousCommands = LinkedList<String>()

    companion object {
        fun loadCommandHistoryFromPrefs(limit: Int, context: Context, prefsFile: String): CommandHistory {
            val commandHistory = CommandHistory(limit)
            val sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE)
            commandHistory.prefs = sharedPreferences
            val size = sharedPreferences.getInt("Size", 0)
            for (i in 0 until size) {
                val command = sharedPreferences.getString(i.toString(), null)
                command?.let { commandHistory.add(it) }
            }
            return commandHistory
        }
    }

    fun add(command: String) {
        if (previousCommands.size > historyLimit) {
            previousCommands.removeFirst()
        }
        previousCommands.add(command)
    }

    fun populateMenu(contextMenu: ContextMenu) {
        for (command in previousCommands.asReversed()) {
            contextMenu.add(0, 0, 0, command)
        }
    }

    fun save() {
        val editor = prefs.edit()
        for (i in previousCommands.indices) {
            editor.putString(i.toString(), previousCommands[i])
        }
        editor.putInt("Size", previousCommands.size)
        editor.apply()
    }
}
