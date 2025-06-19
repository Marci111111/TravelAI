package it.uniparthenope.marcelomirra.travelai.utils
import com.google.gson.*
import it.uniparthenope.marcelomirra.travelai.model.Activity
import java.lang.reflect.Type

class ActivityDeserializer : JsonDeserializer<Activity> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Activity {
        val jsonObject = json?.asJsonObject ?: JsonObject()

        val name = if (jsonObject.has("name")) jsonObject.get("name").asString else ""
        val type = if (jsonObject.has("type")) jsonObject.get("type").asString else ""
        val cost = if (jsonObject.has("cost")) jsonObject.get("cost").asInt else 0

        // Se l'API usa "description" oppure "desc" per la descrizione
        val description = when {
            jsonObject.has("description") -> jsonObject.get("description").asString
            jsonObject.has("desc") -> jsonObject.get("desc").asString
            else -> ""
        }

        return Activity(name, type, cost, description)
    }
}