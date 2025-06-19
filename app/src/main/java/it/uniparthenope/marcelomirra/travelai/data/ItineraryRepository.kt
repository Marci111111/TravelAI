package it.uniparthenope.marcelomirra.travelai.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import it.uniparthenope.marcelomirra.travelai.model.Activity
import it.uniparthenope.marcelomirra.travelai.utils.ActivityDeserializer
import it.uniparthenope.marcelomirra.travelai.model.Itinerary
import it.uniparthenope.marcelomirra.travelai.model.Preference
import it.uniparthenope.marcelomirra.travelai.network.OpenAIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItineraryRepository {
    suspend fun getItinerary(pref: Preference): Itinerary? = withContext(Dispatchers.IO) {
        val prompt = """
            Crea un itinerario JSON di ${pref.travelDays} giorni per:
            ${pref.destinations.joinToString(", ")}, 
            interessi: ${pref.interests.joinToString(", ")}, 
            budget giornaliero €${pref.budgetPerDay}.
        """.trimIndent()

        val rawJson = OpenAIService.chatItinerary(prompt)




        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Activity::class.java, ActivityDeserializer())
            .create()


        val cleanedJson = extractJson(rawJson)
        return@withContext try {
            gson.fromJson(cleanedJson, Itinerary::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }


    }

    private fun extractJson(str: String): String {
        val start = str.indexOf("{")
        val end = str.lastIndexOf("}")
        return if (start != -1 && end != -1 && end > start) {
            str.substring(start, end + 1)
        } else {
            str
        }
    }
}
