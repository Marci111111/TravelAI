package it.uniparthenope.marcelomirra.travelai.network

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import it.uniparthenope.marcelomirra.travelai.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OpenAIService {
    private val openAI by lazy { OpenAI(BuildConfig.OPENAI_API_KEY) }
    @OptIn(com.aallam.openai.api.BetaOpenAI::class)
    suspend fun chatItinerary(prompt: String): String = withContext(Dispatchers.IO) {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-4o"),
            messages = listOf(
                ChatMessage(role = ChatRole.System, content = "Sei un travel-assistant esperto, Rispondi sempre in italiano e con le seguenti data class data class Itinerary(\n" +
                        "    @SerializedName(\"itinerary\")\n" +
                        "    val itinerary: List<ItineraryDay>,\n" +
                        "    @SerializedName(\"daily_budget\")\n" +
                        "    val dailyBudget: Int,\n" +
                        "    @SerializedName(\"notes\")\n" +
                        "    val notes: String\n" +
                        ")\n" +
                        "\n" +
                        "data class ItineraryDay(\n" +
                        "    @SerializedName(\"day\")\n" +
                        "    val day: Int,\n" +
                        "    @SerializedName(\"activities\")\n" +
                        "    val activities: List<Activity>\n" +
                        ")\n" +
                        "\n" +
                        "data class Activity(\n" +
                        "    @SerializedName(\"name\")\n" +
                        "    val name: String,\n" +
                        "    @SerializedName(\"type\")\n" +
                        "    val type: String,\n" +
                        "    @SerializedName(\"cost\")\n" +
                        "    val cost: Int,\n" +
                        "    @SerializedName(\"description\")\n" +
                        "    val description: String\n" +
                        ")\n. Rispondi solo con un oggetto JSON ben formato, senza del testo aggiuntivo."),
                ChatMessage(role = ChatRole.User, content = prompt)
            )
        )


        val response: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
        response.choices.firstOrNull()?.message?.content ?: "Errore: Nessuna risposta disponibile"
    }
}
