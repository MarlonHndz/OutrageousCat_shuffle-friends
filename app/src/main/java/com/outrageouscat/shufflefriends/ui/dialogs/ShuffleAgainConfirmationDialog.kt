package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.outrageouscat.shufflefriends.R

@Composable
fun ShuffleAgainConfirmationDialog(
    onShuffleAgain: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.shuffle_again_alert_title)) },
        text = { Text(stringResource(R.string.shuffle_again_alert_message)) },
        confirmButton = {
            TextButton(
                onClick = {
                    onShuffleAgain()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.shuffle_again_alert_shuffle_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.shuffle_again_alert_cancel_button))
            }
        }
    )
}

@Preview
@Composable
fun PreviewShuffleAgainConfirmationDialog() {
    ShuffleAgainConfirmationDialog(
        onShuffleAgain = {},
        onDismiss = {}
    )
}
