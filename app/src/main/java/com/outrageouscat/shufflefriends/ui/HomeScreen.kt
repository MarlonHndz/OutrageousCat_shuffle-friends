package com.outrageouscat.shufflefriends.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.outrageouscat.shufflefriends.data.ParticipantsList
import com.outrageouscat.shufflefriends.data.participantsListDataStore
import com.outrageouscat.shufflefriends.ui.Dialogs.ClearParticipantsConfirmationDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    modifier: Modifier,
    navController: NavHostController
) {
    val dataStore = context.participantsListDataStore
    val participantsState = remember { mutableStateOf<List<String>>(emptyList()) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Load participants from DataStore
    LaunchedEffect(Unit) {
        dataStore.data.collect { participantsList ->
            participantsState.value = participantsList.participants
        }
    }

    // Save changes to DataStore
    suspend fun saveParticipants(participants: List<String>) {
        dataStore.updateData { ParticipantsList(participants) }
    }

    // Function to clear all participants
    var showClearConfirmationDialog by remember { mutableStateOf(false) }
    val onClearParticipants: () -> Unit = {
        participantsState.value = emptyList()  // Update local state to empty list
        scope.launch { saveParticipants(emptyList()) }  // Update DataStore with empty list
    }

    var participants by remember { participantsState }
    var showAddParticipantDialog by remember { mutableStateOf(false) }
    var results by remember { mutableStateOf<Map<String, String>?>(null) }
    var errorMessage by remember { mutableStateOf<String>("") }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) {

        ParticipantsContent(
            modifier = Modifier.fillMaxSize(),
            participants = participants,
            onAddParticipant = { name ->
                if (participants.contains(name)) {
                    errorMessage = "El participante '$name' ya existe."
                    scope.launch { snackbarHostState.showSnackbar(errorMessage) }
                } else {
                    participants = participants + name
                    scope.launch { saveParticipants(participants) }
                    errorMessage = ""
                }
            },
            onEditParticipant = { index, newName ->
                if (participants.contains(newName)) {
                    errorMessage = "El participante '$newName' ya existe."
                    scope.launch { snackbarHostState.showSnackbar(errorMessage) }
                } else {
                    participants = participants.toMutableList().apply { this[index] = newName }
                    scope.launch { saveParticipants(participants) }
                }
            },
            onRemoveParticipant = { index ->
                participants = participants.toMutableList().apply { removeAt(index) }
                scope.launch { saveParticipants(participants) }
            },
            onSeeResults = {
                results?.let {
                    val resultsJson = Gson().toJson(it)
                    navController.navigate("Results/$resultsJson")
                }
            },
            onShuffle = {
                results = shuffleParticipants(participants)
                results?.let {
                    val resultsJson = Gson().toJson(it)
                    navController.navigate("Results/$resultsJson")

                }
            },
            showAddParticipantDialog = showAddParticipantDialog,
            onShowAddDialog = { showAddParticipantDialog = true },
            onDismissAddDialog = { showAddParticipantDialog = false },
            onClearParticipants = { showClearConfirmationDialog = true },
            isResultsEmpty = true
        )

        if (showClearConfirmationDialog) {
            ClearParticipantsConfirmationDialog(
                onClearParticipants = onClearParticipants,
                onDismiss = { showClearConfirmationDialog = false }
            )
        }
    }
}

fun shuffleParticipants(participants: List<String>): Map<String, String> {
    if (participants.size < 2) {
        return emptyMap()
    }

    var shuffledParticipants: List<String>
    do {
        shuffledParticipants = participants.shuffled()
    } while (participants.indices.any { participants[it] == shuffledParticipants[it] })
    // Repeat shuffle as long as someone is assigned to self

    return participants.zip(shuffledParticipants).toMap()
}
