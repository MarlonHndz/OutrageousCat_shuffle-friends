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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.outrageouscat.shufflefriends.data.datastore.participantsListDataStore
import com.outrageouscat.shufflefriends.data.datastore.resultsDataStore
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import com.outrageouscat.shufflefriends.datastore.ResultsProto
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ParticipantLocal
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList
import com.outrageouscat.shufflefriends.ui.dialogs.ClearParticipantsConfirmationDialog
import com.outrageouscat.shufflefriends.ui.dialogs.ShuffleAgainConfirmationDialog
import com.outrageouscat.shufflefriends.ui.navigation.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    modifier: Modifier,
    navController: NavHostController
) {
    val participantsListDataStore = context.participantsListDataStore
    val participantsState = remember { mutableStateOf<List<Participant>>(emptyList()) }
    var participants by remember { participantsState }

    val resultsDataStore = context.resultsDataStore
    val resultsListLocal by resultsDataStore.data.collectAsState(initial = ResultsList.getDefaultInstance())
    var isLocalResultsEmpty = resultsListLocal.results.isEmpty()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showShuffleConfirmationDialog by remember { mutableStateOf(false) }

    // Load participants from DataStore
    LaunchedEffect(Unit) {
        participantsListDataStore.data.collect { participantsList ->
            participantsState.value = participantsList.participants
        }
    }

    // Save changes to DataStore
    suspend fun saveParticipants(participants: List<Participant>) {
        participantsListDataStore.updateData { ParticipantsList(participants) }
        resultsDataStore.updateData { ResultsList.getDefaultInstance() }
    }

    suspend fun saveResults(results: Map<Participant, Participant>) {
        resultsDataStore.updateData { currentResults ->
            val updatedResults = results.mapKeys { it.key.name }
                .mapValues { entry ->
                    val participantBuilder = ParticipantLocal.newBuilder()
                    participantBuilder.name = entry.value.name
                    participantBuilder.phoneNumber = entry.value.phoneNumber
                    participantBuilder.description = entry.value.description
                    participantBuilder.build()
                }

            currentResults.toBuilder().putAllResults(updatedResults).build()
        }
        isLocalResultsEmpty = results.isEmpty()
    }

    // Clear all participants
    var showClearConfirmationDialog by remember { mutableStateOf(false) }
    val onClearParticipants: () -> Unit = {
        participantsState.value = emptyList()  // Update local state to empty list
        scope.launch { saveParticipants(emptyList()) }  // Update DataStore with empty list
    }

    // Clear results
    val onClearResults: () -> Unit = {
        scope.launch { resultsDataStore.updateData { ResultsList.getDefaultInstance() } }
    }

    var showAddParticipantDialog by remember { mutableStateOf(false) }
    var results by remember { mutableStateOf<Map<Participant, Participant>?>(null) }
    var errorMessage by remember { mutableStateOf<String>("") }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) {

        ParticipantsContent(
            modifier = Modifier.fillMaxSize(),
            participants = participants,
            onAddParticipant = { name, phoneNumber, description ->
                if (participants.any { it.name == name }) {
                    errorMessage = "El participante '$name' ya existe."
                    scope.launch { snackbarHostState.showSnackbar(errorMessage) }
                } else {
                    participants = participants + Participant(name, phoneNumber, description)
                    scope.launch { saveParticipants(participants) }
                    errorMessage = ""
                }
            },
            onEditParticipant = { index, newName, newPhoneNumber, newDescription ->
                if (participants.any { it.name == newName && it != participants[index] }) {
                    errorMessage = "El participante '$newName' ya existe."
                    scope.launch { snackbarHostState.showSnackbar(errorMessage) }
                } else {
                    participants = participants.toMutableList().apply {
                        this[index] = Participant(newName, newPhoneNumber, newDescription)
                    }
                    scope.launch { saveParticipants(participants) }
                }
            },
            onRemoveParticipant = { index ->
                participants = participants.toMutableList().apply { removeAt(index) }
                scope.launch { saveParticipants(participants) }
            },
            onSeeResults = {
                navController.navigate(Screen.Results.route)
            },
            onShuffle = {
                results = shuffleParticipants(participants)
                results?.let {
                    scope.launch { saveResults(it) }
                    navController.navigate(Screen.Results.route)
                }
            },
            showAddParticipantDialog = showAddParticipantDialog,
            onShowAddDialog = { showAddParticipantDialog = true },
            onDismissAddDialog = { showAddParticipantDialog = false },
            onClearParticipants = { showClearConfirmationDialog = true },
            onShuffleAgain = { showShuffleConfirmationDialog = true },
            isResultsEmpty = isLocalResultsEmpty
        )

        if (showClearConfirmationDialog) {
            ClearParticipantsConfirmationDialog(
                onClearParticipants = {
                    onClearParticipants()
                    onClearResults()
                },
                onDismiss = { showClearConfirmationDialog = false }
            )
        }

        if (showShuffleConfirmationDialog) {
            ShuffleAgainConfirmationDialog(
                onShuffleAgain = {
                    results = shuffleParticipants(participants)
                    results?.let {
                        scope.launch { saveResults(it) }
                        navController.navigate(Screen.Results.route)
                    }
                },
                onDismiss = { showShuffleConfirmationDialog = false }
            )
        }
    }
}

fun shuffleParticipants(participants: List<Participant>): Map<Participant, Participant> {
    if (participants.size < 2) {
        return emptyMap()
    }

    var shuffledParticipants: List<Participant>
    do {
        shuffledParticipants = participants.shuffled()
    } while (participants.indices.any { participants[it] == shuffledParticipants[it] })
    // Repeat shuffle as long as someone is assigned to self

    return participants.zip(shuffledParticipants).toMap()
}
