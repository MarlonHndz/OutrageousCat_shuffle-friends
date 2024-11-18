package com.outrageouscat.shufflefriends.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.datastore.resultsDataStore
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
    val resultsDataStore = context.resultsDataStore
    val resultsListLocal by resultsDataStore.data.collectAsState(initial = ResultsList.getDefaultInstance())
    val results = resultsListLocal.resultsMap
    val participants = results.keys.toList()

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
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(250.dp)
                                .padding(horizontal = 32.dp),

                            ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(backgroundColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = participant,
                                    textAlign = TextAlign.Center,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.W400,
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
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        sendWhatsappMessage(
                            context = context,
                            numberPersonReceivingMsg = "",
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
    numberPersonReceivingMsg: String,
    participants: List<String>,
    results: Map<String, String>,
    selectedIndex: Int,
) {
    val whatsappNumber = "57311*******"
    val whatsappMessage =
        "Hola *${participants[selectedIndex]}*, \n Se te ha asignado un *AMIGO SECRETO* \n\n" +
                "No compartas esta información con nadie o *vidas podrían correr peligro.* \n\n" +
                "El nombre de tu amigo secreto es: \n\n" +
                "*${results[participants[selectedIndex]]}*"

    val whatsappIntent = Intent(Intent.ACTION_SEND)
    whatsappIntent.setType("text/plain")
    whatsappIntent.setPackage("com.whatsapp")
    whatsappIntent.putExtra(Intent.EXTRA_TEXT, whatsappMessage)

    whatsappIntent.putExtra("jid", "$whatsappNumber@s.whatsapp.net")

    try {
        context.startActivity(whatsappIntent)
    } catch (error: ActivityNotFoundException) {
        error.printStackTrace()
        Toast.makeText(context, "¡¡¡NO TIENES WHATSAPP SUBNORMAL!!!", Toast.LENGTH_SHORT).show()
    }
}
