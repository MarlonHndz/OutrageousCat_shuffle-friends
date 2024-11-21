package com.outrageouscat.shufflefriends.domain.useCases

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.ui.util.WhatsappMessageHelper

class WhatsappMessageUseCase(
    private val context: Context,
) {

    fun sendMessage(
        selectedIndex: Int,
        participants: List<Participant>,
        results: Map<String, Participant>,
    ) {
        val giverName = participants[selectedIndex].name
        val giverPhone = "57" + participants[selectedIndex].phoneNumber

        val receiverName = results[giverName]?.name.toString()
        val receiverDescription = results[giverName]?.description.toString()

        val whatsappMessage = WhatsappMessageHelper.createMessage(
            context = context,
            giverName = giverName,
            receiverName = receiverName,
            receiverDescription = receiverDescription,
            customMessage = "customMessage",
            deliveryDate = "deliveryDate"
        )

        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "text/plain"
        whatsappIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        whatsappIntent.setPackage("com.whatsapp")
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, whatsappMessage)
        whatsappIntent.putExtra("jid", "$giverPhone@s.whatsapp.net")

        try {
            context.startActivity(whatsappIntent)
        } catch (error: ActivityNotFoundException) {
            error.printStackTrace()
            Toast.makeText(
                context,
                context.getString(R.string.whatsapp_message_no_app_installed),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}