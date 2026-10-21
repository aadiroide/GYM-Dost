package com.example.model

enum class FitnessGoal(val title: String, val description: String, val calorieAdjustment: Int) {
  BULK("Weight Gain / Muscle Bulk", "+350 to +450 kcal surplus", 350),
  CUT("Weight Loss / Fat Cutting", "-350 to -500 kcal deficit", -450),
  MAINTAIN("Stay Fit & Lean (Maintenance)", "Balanced recomposition", 0)
}

enum class TargetTimeline(val title: String, val subtitle: String) {
  THREE_MONTHS("3 Months", "Fast Track / Aggressive"),
  SIX_MONTHS("6 Months", "Balanced & Sustainable (Recommended)"),
  ONE_YEAR("1 Year", "Long-term Lifestyle Transformation")
}

enum class ActivityLevel(val multiplier: Float, val title: String) {
  SEDENTARY(1.2f, "Sedentary (Little or no workout)"),
  LIGHT(1.375f, "Lightly Active (Workout 1-3 days/wk)"),
  MODERATE(1.55f, "Moderately Active (Workout 4-5 days/wk)"),
  VERY_ACTIVE(1.725f, "Very Active (Hard training 6 days/wk)")
}

data class UserProfile(
  val name: String = "Rahul Sharma",
  val gender: String = "male", // male, female, other
  val age: Int = 24,
  val weightKg: Float = 70f,
  val heightCm: Float = 175f,
  val goal: FitnessGoal = FitnessGoal.BULK,
  val timeline: TargetTimeline = TargetTimeline.SIX_MONTHS,
  val activityLevel: ActivityLevel = ActivityLevel.MODERATE,
  val isConfigured: Boolean = false
) {
  // BMR via Mifflin-St Jeor Formula
  val bmr: Float
    get() {
      val base = (10f * weightKg) + (6.25f * heightCm) - (5f * age)
      return if (gender.lowercase() == "female") {
        base - 161f
      } else {
        base + 5f
      }
    }

  // TDEE
  val tdee: Float
    get() = bmr * activityLevel.multiplier

  // Timeline-weighted calorie adjustment
  val calculatedCalories: Int
    get() {
      val timelineMultiplier = when (timeline) {
        TargetTimeline.THREE_MONTHS -> 1.25f
        TargetTimeline.SIX_MONTHS -> 1.0f
        TargetTimeline.ONE_YEAR -> 0.75f
      }
      val adjustment = (goal.calorieAdjustment * timelineMultiplier).toInt()
      return (tdee + adjustment).toInt().coerceAtLeast(1200)
    }

  // Protein calculation: 1.8g to 2.2g per kg bodyweight
  val calculatedProtein: Int
    get() {
      val proteinPerKg = when (goal) {
        FitnessGoal.BULK -> 2.1f
        FitnessGoal.CUT -> 2.2f
        FitnessGoal.MAINTAIN -> 1.8f
      }
      return (weightKg * proteinPerKg).toInt()
    }

  // Water target in liters (~45ml per kg)
  val calculatedWaterLiters: Float
    get() = (weightKg * 0.045f).coerceIn(2.5f, 5.0f)

  // BMI
  val bmi: Float
    get() {
      val hM = heightCm / 100f
      return if (hM > 0) weightKg / (hM * hM) else 22.0f
    }

  val bmiCategory: String
    get() = when {
      bmi < 18.5f -> "Underweight"
      bmi < 25f -> "Healthy Weight"
      bmi < 30f -> "Overweight"
      else -> "Obese"
    }
}
