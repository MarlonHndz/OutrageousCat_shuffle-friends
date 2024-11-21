package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.outrageouscat.shufflefriends.R

@Composable
fun DateIsMissingAlertDialog(
    alertTitle: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = alertTitle) },
        text = { Text(stringResource(R.string.whatsapp_message_date_missing_alert_text)) },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = stringResource(R.string.whatsapp_message_date_missing_alert_confirm_button))
            }
        }
    )
}

@Preview
@Composable
fun PreviewDateIsMissingAlertDialog() {
    DateIsMissingAlertDialog(
        alertTitle = stringResource(R.string.add_edit_alert_title_add_participant),
        onConfirm = {},
        onDismiss = {}
    )
}
