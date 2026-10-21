package com.example.model

data class IntakeItem(
  val id: String = System.currentTimeMillis().toString(),
  val name: String,
  val calories: Int,
  val protein: Int,
  val mealTiming: String = "Snack",
  val emoji: String = "🍽️",
  val timestamp: Long = System.currentTimeMillis()
)
