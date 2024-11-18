package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ShuffleAgainConfirmationDialog(
    onShuffleAgain: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Volver a sortear amigos secretos?") },
        text = { Text("Al sortear nuevamente perderas los resultados del sorteo anterior.") },
        confirmButton = {
            TextButton(
                onClick = {
                    onShuffleAgain()
                    onDismiss()
                }
            ) {
                Text("Sortear")
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