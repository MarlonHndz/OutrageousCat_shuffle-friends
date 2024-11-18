package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ClearParticipantsConfirmationDialog(
    onClearParticipants: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Borrar todos los participantes?") },
        text = { Text("Al eliminar todos los participantes no podrás recuperarlos.") },
        confirmButton = {
            TextButton(
                onClick = {
                    onClearParticipants()
                    onDismiss()
                }
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}
