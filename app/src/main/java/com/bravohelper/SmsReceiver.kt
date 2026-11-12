package com.bravohelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (m in msgs) {
            val match = Regex("\\b(\\d{4,6})\\b").find(m.messageBody)
            if (match != null) {
                State.otp = match.groupValues[1]
            }
        }
    }
}
