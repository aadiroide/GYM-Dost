package com.example.model

data class ExerciseItem(
  val id: String,
  val name: String,
  val targetMuscle: String,
  val setsAndReps: String,
  val dayKey: String, // "mon", "tue", "wed", "thu", "fri", "sat", "sun"
  val isCompleted: Boolean = false,
  val isCustom: Boolean = false
)
