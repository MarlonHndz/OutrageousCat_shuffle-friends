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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.ui.Dialogs.AddParticipantDialog
import com.outrageouscat.shufflefriends.ui.Dialogs.EditParticipantDialog
import com.outrageouscat.shufflefriends.ui.composables.SwipeBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantsContent(
    modifier: Modifier,
    participants: List<String>,
    onAddParticipant: (String) -> Unit,
    onEditParticipant: (Int, String) -> Unit,
    onRemoveParticipant: (Int) -> Unit,
    onShuffle: () -> Unit,
    onSeeResults: () -> Unit,
    showAddParticipantDialog: Boolean,
    onShowAddDialog: () -> Unit,
    onDismissAddDialog: () -> Unit,
    onClearParticipants: () -> Unit,
    isResultsEmpty: Boolean
) {
    var editParticipantDialog by remember { mutableStateOf(false) }
    var editingName by remember { mutableStateOf("") }
    var editingIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.gift_yellow_icon),
                        contentDescription = "Shuffle friends",
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                actions = {
                    if (participants.isNotEmpty()) {
                        IconButton(
                            onClick = onClearParticipants
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Eliminar todos"
                            )
                        }
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
                    contentDescription = "Editar"
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
                    text = "Participantes (${participants.size})",
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
                                editingName = participant
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
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp, vertical = 12.dp)
                                        .align(Alignment.CenterVertically),
                                    text = participant,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W400,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row {
                                    IconButton(
                                        onClick = {
                                            editingName = participant
                                            editingIndex = index
                                            editParticipantDialog = true
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Edit,
                                            contentDescription = "Editar"
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
                        text = "Shuffle",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.shuffle_default_icon),
                        contentDescription = "Shuffle friends",
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            } else {
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    onClick = onSeeResults,
                    enabled = participants.size % 2 == 0 && participants.size > 1
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = "Ver resultados",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.shuffle_default_icon),
                        contentDescription = "Shuffle friends",
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            }

            if (showAddParticipantDialog) {
                AddParticipantDialog(
                    onAddParticipant = { name ->
                        onAddParticipant(name)
                        onDismissAddDialog()
                    },
                    onDismiss = onDismissAddDialog,
                )
            }

            if (editParticipantDialog) {
                EditParticipantDialog(
                    name = editingName,
                    onNameChange = { editingName = it },
                    onSave = {
                        onEditParticipant(editingIndex, editingName)
                        editParticipantDialog = false
                    },
                    onDismiss = { editParticipantDialog = false }
                )
            }
        }
    }
}
