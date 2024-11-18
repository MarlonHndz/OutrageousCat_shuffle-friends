package com.outrageouscat.shufflefriends.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.datastore.participantsListDataStore
import com.outrageouscat.shufflefriends.data.datastore.resultsDataStore
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList
import com.outrageouscat.shufflefriends.ui.dialogs.RevelationDialog
import kotlinx.coroutines.launch
import kotlin.String
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    context: Context,
    modifier: Modifier,
    onBack: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val participantsListDataStore = context.participantsListDataStore
    val participantsListLocal by participantsListDataStore.data.collectAsState(
        initial = ParticipantsList(
            emptyList()
        )
    )
    val participants = participantsListLocal.participants

    val resultsDataStore = context.resultsDataStore
    val resultsListLocal by resultsDataStore.data.collectAsState(initial = ResultsList.getDefaultInstance())

    // Convert resultsMap to format Map<String, Participant>
    val results = resultsListLocal.resultsMap
        .map { (key, value) ->
            val giver = key.toString()
            val receiver = Participant(
                name = value.name,
                phoneNumber = value.phoneNumber,
                description = value.description
            )
            giver to receiver
        }.toMap()

    var showResultDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()


    // Update selectedIndex whenever the user scrolls
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index -> selectedIndex = index }
    }

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
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Volver"
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
                    text = "Resultados",
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
                        text = "Revelar amigo secreto",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.gift_box_open_icon),
                        contentDescription = "Shuffle friends",
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
                        sendWhatsappMessage(
                            context = context,
                            participants = participants,
                            results = results,
                            selectedIndex = selectedIndex,
                        )
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = "Enviar via WhatsApp",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.whatsapp_icon),
                        contentDescription = "Shuffle friends",
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "*Recuerda que al ser el host y enviar el mensaje de WhatsApp tendrás acceso a información confidencial 😎.",
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
                    onClick = {
                        if (selectedIndex > 0) {
                            selectedIndex -= 1
                            scope.launch { listState.animateScrollToItem(selectedIndex) }
                        }
                    },
                    enabled = selectedIndex > 0
                ) {
                    Text("Anterior")
                }

                // Next Button
                Button(
                    onClick = {
                        if (selectedIndex < participants.size - 1) {
                            selectedIndex += 1
                            scope.launch { listState.animateScrollToItem(selectedIndex) }
                        }
                    },
                    enabled = selectedIndex < participants.size - 1
                ) {
                    Text("Siguiente")
                }
            }
        }
    }
}

fun sendWhatsappMessage(
    context: Context,
    participants: List<Participant>,
    results: Map<String, Participant>,
    selectedIndex: Int,
) {
    val giverName = participants[selectedIndex].name
    val giverPhone = "57" + participants[selectedIndex].phoneNumber

    val receiverName = results[giverName]?.name.toString()
    val receiverDescription = results[giverName]?.description.toString()

    val whatsappMessage =
        "¡Hola *$giverName*!\n" +
                "Se te ha asignado un 🤫 *AMIGO SECRETO* 🤫\n\n" +
                "No compartas esta información con nadie o 😒🔪 *vidas podrían correr peligro 🔪🩸.*\n\n" +
                "🥁 El nombre de tu amigo secreto es: 🥁\n\n" +
                "🌟⭐ *$receiverName* ⭐🌟\n\n" +
                "Quien dijo respecto a sus gustos: \n\n" +
                "*$receiverDescription*\n\n" +
                "Recuerda que la entrega de regalos 🎁 se realizará el *24 de Diciembre a la media noche* 🎁"

    val whatsappIntent = Intent(Intent.ACTION_SEND)
    whatsappIntent.setType("text/plain")
    whatsappIntent.setPackage("com.whatsapp")
    whatsappIntent.putExtra(Intent.EXTRA_TEXT, whatsappMessage)

    whatsappIntent.putExtra("jid", "$giverPhone@s.whatsapp.net")

    try {
        context.startActivity(whatsappIntent)
    } catch (error: ActivityNotFoundException) {
        error.printStackTrace()
        Toast.makeText(context, "¡¡¡NO TIENES WHATSAPP SUBNORMAL!!!", Toast.LENGTH_SHORT).show()
    }
}
