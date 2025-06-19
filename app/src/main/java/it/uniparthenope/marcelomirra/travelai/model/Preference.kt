package it.uniparthenope.marcelomirra.travelai.model

data class Preference(
    val destinations: List<String>,
    val interests: List<String>,
    val budgetPerDay: Int,
    val travelDays: Int
)
