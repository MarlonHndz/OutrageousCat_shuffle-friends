package com.outrageouscat.shufflefriends.ui.util

import android.content.Context
import com.outrageouscat.shufflefriends.R

object WhatsappMessageHelper {
    fun createMessage(
        context: Context,
        giverName: String,
        receiverName: String,
        receiverDescription: String,
        customMessage: String,
        deliveryDate: String
    ): String {
        return context.getString(R.string.whatsapp_message_greeting, giverName) +
                if (customMessage.isNotEmpty()) {
                    customMessage + "\n\n"
                } else {
                    ""
                } +
                context.getString(R.string.whatsapp_message_secret_friend_name_label) +
                context.getString(R.string.whatsapp_message_receiver_name, receiverName) +
                context.getString(
                    R.string.whatsapp_message_receiver_description,
                    receiverDescription
                ) +
                context.getString(R.string.whatsapp_message_date_label, deliveryDate)

    }
}
