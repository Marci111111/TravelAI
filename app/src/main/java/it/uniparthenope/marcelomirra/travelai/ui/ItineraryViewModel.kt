
package it.uniparthenope.marcelomirra.travelai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.uniparthenope.marcelomirra.travelai.data.ItineraryRepository
import it.uniparthenope.marcelomirra.travelai.model.Itinerary
import it.uniparthenope.marcelomirra.travelai.model.Preference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val itinerary: Itinerary) : UiState()
    data class Error(val message: String) : UiState()
}
class ItineraryViewModel : ViewModel() {
    private val repo = ItineraryRepository()
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun generate(pref: Preference) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = repo.getItinerary(pref)
                if (result != null) {
                    _uiState.value = UiState.Success(result)
                } else {
                    _uiState.value = UiState.Error("Itinerario nullo")
                }
            } catch (t: Throwable) {
                val cause = t.cause?.toString() ?: t.localizedMessage ?: "Errore sconosciuto"
                _uiState.value = UiState.Error("⚠️ Errore: $cause")
            }
        }
    }
}
