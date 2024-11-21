package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.outrageouscat.shufflefriends.R

@Composable
fun ClearParticipantsConfirmationDialog(
    onClearParticipants: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.clear_alert_title)) },
        text = { Text(stringResource(R.string.clear_alert_message)) },
        confirmButton = {
            TextButton(
                onClick = {
                    onClearParticipants()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.clear_alert_delete_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.clear_alert_cancel_button))
            }
        }
    )
}

@Preview
@Composable
fun PreviewClearParticipantsConfirmationDialog() {
    ClearParticipantsConfirmationDialog(
        onClearParticipants = {},
        onDismiss = {}
    )
}
