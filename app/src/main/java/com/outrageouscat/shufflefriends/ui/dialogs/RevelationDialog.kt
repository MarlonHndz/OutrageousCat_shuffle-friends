package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val receiverDescription = results[giverName]?.description.toString()
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = giverName,
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W300
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.reveal_alert_subtitle),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W300
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "🤫 $receiverName 🤫",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W300
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.reveal_alert_who_said_message),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W300
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\"$receiverDescription\"",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
