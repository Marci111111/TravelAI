package it.uniparthenope.marcelomirra.travelai.model

import com.google.gson.annotations.SerializedName

data class Itinerary(
    @SerializedName("itinerary")
    val itinerary: List<ItineraryDay>,
    @SerializedName("daily_budget")
    val dailyBudget: Int,
    @SerializedName("notes")
    val notes: String
)

data class ItineraryDay(
    @SerializedName("day")
    val day: Int,
    @SerializedName("activities")
    val activities: List<Activity>
)

data class Activity(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("cost")
    val cost: Int,
    @SerializedName("description")
    val description: String
)
