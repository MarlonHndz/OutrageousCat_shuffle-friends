package com.outrageouscat.shufflefriends.ui.screens.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.ui.dialogs.DateIsMissingAlertDialog
import com.outrageouscat.shufflefriends.ui.dialogs.RevelationDialog
import com.outrageouscat.shufflefriends.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    navController: NavHostController,
    viewModel: ResultsViewModel = koinViewModel()
) {
    val participants by viewModel.participants.collectAsState()
    val results by viewModel.results.collectAsState()
    val selectedIndex by viewModel.selectedIndex.collectAsState()
    val alertMessage by viewModel.alertMessage.collectAsState()

    val listState = rememberLazyListState()
    var showResultDialog by remember { mutableStateOf(false) }

    // Update selectedIndex whenever the user scrolls
    LaunchedEffect(selectedIndex) {
        listState.animateScrollToItem(selectedIndex)
    }

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
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_back_icon)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.results_screen_title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W300
                )

                // Participant Slider
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    userScrollEnabled = false
                ) {
                    items(participants.size) { index ->
                        val participant = participants[index]
                        val backgroundColor = Color(
                            Random.nextInt(100, 255),
                            Random.nextInt(100, 255),
                            Random.nextInt(100, 255)
                        )
                        ElevatedCard(
                            elevation = CardDefaults.elevatedCardElevation(16.dp),
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(180.dp)
                                .padding(horizontal = 32.dp),

                            ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(backgroundColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = participant.name,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.W300,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Reveal Button
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    onClick = { showResultDialog = true }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = stringResource(R.string.results_reveal_secret_button),
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.gift_box_open_icon),
                        contentDescription = stringResource(R.string.content_description_gift_icon),
                        modifier = Modifier
                            .size(22.dp)
                    )
                }

                // WhatsApp Button
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        viewModel.sendMessage()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = stringResource(R.string.results_send_via_whatsapp_button),
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.whatsapp_icon),
                        contentDescription = stringResource(R.string.content_description_whatsapp_icon),
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.results_top_secret_alert_message),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray
                )

                if (showResultDialog && selectedIndex in participants.indices) {
                    RevelationDialog(
                        participants = participants,
                        results = results,
                        selectedIndex = selectedIndex,
                        onDismiss = { showResultDialog = false }
                    )
                }

                alertMessage?.let {
                    DateIsMissingAlertDialog(
                        alertTitle = it,
                        onConfirm = {
                            viewModel.dismissAlert()
                            navController.navigate(Screen.Settings.route)
                        },
                        onDismiss = { viewModel.dismissAlert() })
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous Button
                Button(
                    onClick = { viewModel.moveToPreviousParticipant() },
                    enabled = viewModel.canMoveToPrevious.collectAsState(initial = false).value
                ) {
                    Text(stringResource(R.string.results_previous_button))
                }

                // Next Button
                Button(
                    onClick = { viewModel.moveToNextParticipant() },
                    enabled = viewModel.canMoveToNext.collectAsState(initial = false).value
                ) {
                    Text(stringResource(R.string.results_next_button))
                }
            }
        }
    }
}
