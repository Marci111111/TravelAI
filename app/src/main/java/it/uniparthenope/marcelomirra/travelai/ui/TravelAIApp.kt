package it.uniparthenope.marcelomirra.travelai.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.uniparthenope.marcelomirra.travelai.model.Preference
import it.uniparthenope.marcelomirra.travelai.ui.ItineraryViewModel

@Composable
fun TravelAIApp(vm: ItineraryViewModel = viewModel()) {
    val uiState by vm.uiState.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Genera Itinerario", "Itinerario")

    var destText by remember { mutableStateOf("") }
    var intsText by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("TravelAI") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()) {
                        OutlinedTextField(
                            value = destText,
                            onValueChange = { destText = it },
                            label = { Text("Destinazione") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = intsText,
                            onValueChange = { intsText = it },
                            label = { Text("Interessi (separati da virgola)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = budget,
                            onValueChange = { budget = it },
                            label = { Text("Budget giornaliero (€)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = days,
                            onValueChange = { days = it },
                            label = { Text("Giorni di viaggio") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                vm.generate(
                                    Preference(
                                        destinations = destText.split(",").map { it.trim() },
                                        interests = intsText.split(",").map { it.trim() },
                                        budgetPerDay = budget.toIntOrNull() ?: 0,
                                        travelDays = days.toIntOrNull() ?: 1
                                    )
                                )
                                selectedTabIndex = 1
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Genera Itinerario")
                        }
                    }
                }
                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        when (uiState) {
                            is UiState.Loading -> {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                            is UiState.Success -> {
                                val itineraryData = (uiState as UiState.Success).itinerary
                                Column(modifier = Modifier.padding(16.dp)) {
                                    LazyColumn {
                                        items(itineraryData.itinerary) { day ->
                                            Text(
                                                text = "Giorno ${day.day}:",
                                                style = MaterialTheme.typography.subtitle1
                                            )
                                            day.activities.forEach { act ->

                                                Text(
                                                    text = "• ${act.name} (${act.type})\n${act.description}\nCosto: ${act.cost}€",
                                                    modifier = Modifier.padding(start = 8.dp)
                                                )

                                            }
                                            Spacer(modifier = Modifier.height(12.dp))
                                        }
                                    }
                                }
                            }

                            is UiState.Error -> {
                                Text(
                                    text = (uiState as UiState.Error).message,
                                    color = MaterialTheme.colors.error
                                )
                            }
                            else -> {

                                Text(text = "Nessun itinerario generato.", style = MaterialTheme.typography.body1)
                            }
                        }
                    }
                }
            }
        }
    }
}
