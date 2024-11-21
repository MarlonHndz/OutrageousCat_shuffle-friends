package com.outrageouscat.shufflefriends.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.outrageouscat.shufflefriends.R

@Composable
fun AddOrEditParticipantDialog(
    alertTitle: String,
    initialName: String = "",
    initialPhone: String = "",
    initialDescription: String = "",
    onConfirm: (name: String, phone: String, description: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var phone by remember { mutableStateOf(initialPhone) }
    var description by remember { mutableStateOf(initialDescription) }

    Dialog(onDismissRequest = { }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = alertTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 16.dp),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.add_edit_alert_label_name)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                )
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 16.dp),
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(stringResource(R.string.add_edit_alert_label_phone_number)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 8.dp),
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.add_edit_alert_label_description)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss
                    ) {
                        Text(stringResource(R.string.add_edit_alert_cancel_button))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (name.isNotEmpty()) onConfirm(name, phone, description)
                        },
                        enabled = name.isNotEmpty()
                    ) {
                        Text(stringResource(R.string.add_edit_alert_acept_button))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddOrEditParticipantDialog() {
    AddOrEditParticipantDialog(
        alertTitle = stringResource(R.string.add_edit_alert_title_add_participant),
        onConfirm = { name, phone, description -> },
        onDismiss = { },
    )
}
