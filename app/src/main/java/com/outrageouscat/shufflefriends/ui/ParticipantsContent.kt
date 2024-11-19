package com.outrageouscat.shufflefriends.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.ui.composables.SwipeBox
import com.outrageouscat.shufflefriends.ui.dialogs.AddOrEditParticipantDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantsContent(
    modifier: Modifier,
    participants: List<Participant>,
    onAddParticipant: (String, String, String) -> Unit,
    onEditParticipant: (Int, String, String, String) -> Unit,
    onRemoveParticipant: (Int) -> Unit,
    onShuffle: () -> Unit,
    onSeeResults: () -> Unit,
    onSettings: () -> Unit,
    showAddParticipantDialog: Boolean,
    onShowAddDialog: () -> Unit,
    onDismissAddDialog: () -> Unit,
    onClearParticipants: () -> Unit,
    onShuffleAgain: () -> Unit,
    isResultsEmpty: Boolean
) {
    var editParticipantDialog by remember { mutableStateOf(false) }
    var editingParticipant by remember { mutableStateOf(Participant("", "", "")) }
    var editingName by remember { mutableStateOf("") }
    var editingIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.shuffle_friends_gift_icon),
                        contentDescription = stringResource(R.string.content_description_app_logo),
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.FillBounds
                    )
                },
                actions = {
                    if (participants.isNotEmpty()) {
                        IconButton(
                            onClick = onClearParticipants
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(R.string.content_description_delete_icon_button)
                            )
                        }
                    }

                    if (!isResultsEmpty) {
                        IconButton(
                            onClick = onShuffleAgain
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.shuffle_default_icon),
                                contentDescription = stringResource(R.string.content_description_shuffle_icon_button),
                                modifier = Modifier
                                    .size(22.dp),
                                colorFilter = ColorFilter.tint(color = Color.Black)
                            )
                        }
                    }

                    IconButton(
                        onClick = onSettings
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = stringResource(R.string.content_description_settings_icon)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onShowAddDialog,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surfaceDim
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.content_description_add_participant_floating_button)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(
                        R.string.home_screen_title,
                        participants.size
                    ),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W300,
                )

                // Participant List
                LazyColumn {
                    itemsIndexed(participants) { index, participant ->

                        SwipeBox(
                            onDelete = {
                                onRemoveParticipant(index)
                            },
                            onEdit = {
                                editingParticipant = participant
                                editingIndex = index
                                editParticipantDialog = true
                            },
                            modifier = Modifier.animateItem()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = participant.name,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.W500,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = participant.phoneNumber,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W400,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = participant.description,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        fontStyle = FontStyle.Italic

                                    )
                                }

                                Row(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    IconButton(
                                        onClick = {
                                            editingParticipant = participant
                                            editingIndex = index
                                            editParticipantDialog = true
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Edit,
                                            contentDescription = stringResource(R.string.content_description_edit_button)
                                        )
                                    }
//                                    IconButton(onClick = { onRemoveParticipant(index) }) {
//                                        Icon(
//                                            imageVector = Icons.Default.Delete,
//                                            contentDescription = "Eliminar"
//                                        )
//                                    }
                                }
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            thickness = 1.dp
                        )
                    }
                }
            }

            // Shuffle Button
            if (isResultsEmpty) {
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    onClick = onShuffle,
                    enabled = participants.size % 2 == 0 && participants.size > 1
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = stringResource(R.string.home_shuffle_button),
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.shuffle_default_icon),
                        contentDescription = stringResource(R.string.content_description_shuffle_icon),
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            } else {
                // See Results Button
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    onClick = onSeeResults,
                    enabled = participants.size % 2 == 0 && participants.size > 1
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = stringResource(R.string.home_see_results_button),
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.playing_cards_icon),
                        contentDescription = stringResource(R.string.content_description_results_icon),
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            }

            if (showAddParticipantDialog) {
                AddOrEditParticipantDialog(
                    alertTitle = stringResource(R.string.add_edit_alert_title_add_participant),
                    onConfirm = { name, phone, description ->
                        onAddParticipant(name, phone, description)
                        onDismissAddDialog()
                    },
                    onDismiss = onDismissAddDialog,
                )
            }

            if (editParticipantDialog) {
                AddOrEditParticipantDialog(
                    alertTitle = stringResource(R.string.add_edit_alert_title_edit_participant),
                    initialName = editingParticipant.name,
                    initialPhone = editingParticipant.phoneNumber,
                    initialDescription = editingParticipant.description,
                    onConfirm = { newName, newPhoneNumber, newDescription ->
                        onEditParticipant(
                            editingIndex,
                            newName,
                            newPhoneNumber,
                            newDescription,
                        )
                        editParticipantDialog = false
                    },
                    onDismiss = { editParticipantDialog = false }
                )
            }
        }
    }
}
