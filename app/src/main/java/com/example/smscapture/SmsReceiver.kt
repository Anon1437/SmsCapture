package com.example.smscapture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent) ?: return
        if (messages.isEmpty()) return

        val sender = messages.first().displayOriginatingAddress ?: "Unknown"
        val body = messages.joinToString(separator = "") { it.messageBody ?: "" }

        // Show in Toast (main-thread safe; broadcast receiver runs on main thread)
        val text = "📩 SMS from: $sender\n$body"
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
