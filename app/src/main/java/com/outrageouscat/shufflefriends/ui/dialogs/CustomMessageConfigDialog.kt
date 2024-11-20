package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.outrageouscat.shufflefriends.R

@Composable
fun CustomMessageConfigDialog(
    initialCustomMessage: String,
    onConfirm: (customMessage: String) -> Unit,
    onDismiss: () -> Unit
) {
    var customMessage by remember { mutableStateOf(initialCustomMessage) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.custom_message_alert_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W300,
                )
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = customMessage,
                    onValueChange = { customMessage = it },
                    label = { Text(stringResource(R.string.custom_message_alert_label)) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.custom_message_alert_placeholder),
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W300,
                            fontStyle = FontStyle.Italic
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Save message and date
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onConfirm(customMessage)
                    }
                ) {
                    Text(stringResource(R.string.custom_message_alert_save_button))
                }
            }
        }
    }
}
