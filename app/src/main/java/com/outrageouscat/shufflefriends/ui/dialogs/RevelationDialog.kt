package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.outrageouscat.shufflefriends.data.models.Participant

@Composable
fun RevelationDialog(
    participants: List<Participant>,
    results: Map<String, Participant>,
    selectedIndex: Int,
    onDismiss: () -> Unit
) {
    val giverName = participants[selectedIndex].name
    val receiverName = results[giverName]?.name.toString()
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
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "El amigo secreto",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W500
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "De: $giverName",
                    fontSize = 20.sp,
                )
                Text(
                    modifier = Modifier.padding(vertical = 0.dp),
                    text = "Es: $receiverName",
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}