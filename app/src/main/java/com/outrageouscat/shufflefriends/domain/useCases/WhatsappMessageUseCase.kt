package com.outrageouscat.shufflefriends.domain.useCases

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.domain.respositories.SettingsRepository
import com.outrageouscat.shufflefriends.ui.util.WhatsappMessageHelper
import kotlinx.coroutines.flow.first

class WhatsappMessageUseCase(
    private val context: Context,
    private val settingsRepository: SettingsRepository
) {

    suspend fun sendMessage(
        selectedIndex: Int,
        participants: List<Participant>,
        results: Map<String, Participant>,
    ): Result<Unit> {

        val settings = settingsRepository.settings.first()

        // Validate
        if (settings.deliveryDate.isBlank()) {
            return Result.failure(Exception(context.getString(R.string.whatsapp_message_date_missing_alert_title)))
        }

        val giverPhone = "57" + participants[selectedIndex].phoneNumber

        launchWhatsappIntent(
            whatsappMessage = buildMessage(
                selectedIndex = selectedIndex,
                participants = participants,
                results = results,
                customMessage = settings.customMessage,
                deliveryDate = settings.deliveryDate
            ),
            giverPhone = giverPhone
        )
        return Result.success(Unit)
    }

    private fun buildMessage(
        selectedIndex: Int,
        participants: List<Participant>,
        results: Map<String, Participant>,
        customMessage: String,
        deliveryDate: String
    ): String {
        val giverName = participants[selectedIndex].name
        val receiverName = results[giverName]?.name.toString()
        val receiverDescription = results[giverName]?.description.toString()

        return WhatsappMessageHelper.createMessage(
            context = context,
            giverName = giverName,
            receiverName = receiverName,
            receiverDescription = receiverDescription,
            customMessage = customMessage,
            deliveryDate = deliveryDate
        )
    }

    private fun launchWhatsappIntent(whatsappMessage: String, giverPhone: String) {

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
