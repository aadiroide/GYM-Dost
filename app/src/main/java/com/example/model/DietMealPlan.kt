package com.example.model

data class DietMealPlan(
  val id: String,
  val name: String,
  val description: String,
  val calories: Int,
  val protein: Int,
  val iconKey: String = "meal",
  val emoji: String = "🥑",
  val iconUrl: String = ""
)

data class QuickFoodItem(
  val name: String,
  val calories: Int,
  val protein: Int,
  val description: String = "",
  val iconKey: String = "food",
  val emoji: String = "🥗",
  val iconUrl: String = ""
)
