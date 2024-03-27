package ru.moevm.sportfinder.common

import android.content.Context
import android.content.Intent

object Utils {
    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        try {
            context.startActivity(Intent.createChooser(intent, null))
        } catch (e: Exception) {
            // ignore
        }
    }
}