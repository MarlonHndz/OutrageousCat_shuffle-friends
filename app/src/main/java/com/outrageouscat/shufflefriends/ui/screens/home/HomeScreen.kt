package com.outrageouscat.shufflefriends.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.ui.dialogs.ClearParticipantsConfirmationDialog
import com.outrageouscat.shufflefriends.ui.dialogs.ShuffleAgainConfirmationDialog
import com.outrageouscat.shufflefriends.ui.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    modifier: Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {

    val participantsList by viewModel.participantsList.collectAsState()
    val isResultsEmpty by viewModel.isResultsEmpty.collectAsState()

    var showShuffleConfirmationDialog by remember { mutableStateOf(false) }
    var showClearConfirmationDialog by remember { mutableStateOf(false) }
    var showAddParticipantDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) {

        ParticipantsContent(
            modifier = Modifier.fillMaxSize(),
            participants = participantsList,
            onAddParticipant = { name, phoneNumber, description ->
                if (participantsList.any { it.name == name }) {
                    errorMessage =
                        context.getString(R.string.error_message_participant_already_exist, name)
                    scope.launch { snackbarHostState.showSnackbar(errorMessage) }
                } else {
                   viewModel.saveParticipants(participantsList + Participant(name, phoneNumber, description))
                    errorMessage = ""
                }
            },
            onEditParticipant = { index, newName, newPhoneNumber, newDescription ->
                if (participantsList.any { it.name == newName && it != participantsList[index] }) {
                    errorMessage =
                        context.getString(R.string.error_message_participant_already_exist, newName)
                    scope.launch { snackbarHostState.showSnackbar(errorMessage) }
                } else {
                    val updatedParticipants = participantsList.toMutableList().apply {
                        this[index] = Participant(newName, newPhoneNumber, newDescription)
                    }
                    viewModel.saveParticipants(updatedParticipants)
                }
            },
            onRemoveParticipant = { index ->
                viewModel.saveParticipants(participantsList.toMutableList().apply { removeAt(index) })
            },
            onSeeResults = {
                navController.navigate(Screen.Results.route)
            },
            onSettings = {
                navController.navigate(Screen.Settings.route)
            },
            onShuffle = {
                viewModel.shuffleParticipants(participantsList)
                navController.navigate(Screen.Results.route)
            },
            showAddParticipantDialog = showAddParticipantDialog,
            onShowAddDialog = { showAddParticipantDialog = true },
            onDismissAddDialog = { showAddParticipantDialog = false },
            onClearParticipants = { showClearConfirmationDialog = true },
            onShuffleAgain = { showShuffleConfirmationDialog = true },
            isResultsEmpty = isResultsEmpty
        )

        if (showClearConfirmationDialog) {
            ClearParticipantsConfirmationDialog(
                onClearParticipants = {
                    viewModel.saveParticipants(emptyList())
                    viewModel.clearResults()
                },
                onDismiss = { showClearConfirmationDialog = false }
            )
        }

        if (showShuffleConfirmationDialog) {
            ShuffleAgainConfirmationDialog(
                onShuffleAgain = {
                    viewModel.shuffleParticipants(participantsList)
                    navController.navigate(Screen.Results.route)
                },
                onDismiss = { showShuffleConfirmationDialog = false }
            )
        }
    }
}
