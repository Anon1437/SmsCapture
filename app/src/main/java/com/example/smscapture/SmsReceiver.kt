package com.example.smscapture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("SmsReceiver", "========== onReceive fired ==========")
        Log.d("SmsReceiver", "Action: ${intent.action}")

        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Log.d("SmsReceiver", "Not SMS_RECEIVED_ACTION, ignoring")
            return
        }

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        if (messages.isNullOrEmpty()) {
            Log.d("SmsReceiver", "No messages in intent")
            return
        }

        val sender = messages.first().displayOriginatingAddress ?: "Unknown"
        val body = messages.joinToString(separator = "") { it.messageBody ?: "" }

        Log.d("SmsReceiver", "From: $sender")
        Log.d("SmsReceiver", "Body: $body")

        Toast.makeText(context, "📩 From: $sender\n$body", Toast.LENGTH_LONG).show()
        Log.d("SmsReceiver", "========== Toast shown ==========")
    }
}