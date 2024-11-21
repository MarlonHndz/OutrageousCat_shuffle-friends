package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.outrageouscat.shufflefriends.R

@Composable
fun PreviewWhatsappMessageDialog(
    customMessage: String,
    deliveryDate: String,
    onDismiss: () -> Unit
) {
    val exampleGiverName = stringResource(R.string.message_preview_example_giver_name)
    val exampleReceiverName = stringResource(R.string.message_preview_example_receiver_name)
    val exampleReceiverDescription =
        stringResource(R.string.message_preview_example_receiver_description)

    val whatsappMessage =
        stringResource(R.string.whatsapp_message_greeting, exampleGiverName) +
                if (customMessage.isNotEmpty()) {
                    customMessage + "\n\n"
                } else {
                    ""
                } +
                stringResource(R.string.whatsapp_message_secret_friend_name_label) +
                stringResource(R.string.whatsapp_message_receiver_name, exampleReceiverName) +
                stringResource(
                    R.string.whatsapp_message_receiver_description,
                    exampleReceiverDescription
                ) +
                stringResource(R.string.whatsapp_message_date_label, deliveryDate)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.message_preview_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W400,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = colorResource(R.color.whatsapp_green))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = whatsappMessage,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W300,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.message_preview_note),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic
                )
                Row {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 0.dp),
                        text = stringResource(R.string.message_preview_example_hello),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = 0.dp),
                        text = exampleGiverName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewForPreviewWhatsappMessageDialog() {
    PreviewWhatsappMessageDialog(
        customMessage = "",
        deliveryDate = "",
        onDismiss = {}
    )
}
