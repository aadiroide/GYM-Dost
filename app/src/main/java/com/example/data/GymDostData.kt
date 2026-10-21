package com.example.data

import com.example.model.DietMealPlan
import com.example.model.ExerciseItem
import com.example.model.QuickFoodItem

object GymDostData {

  // Microsoft Fluent 3D Emoji URLs with fallback
  private const val FLUENT_BASE = "https://raw.githubusercontent.com/microsoft/fluentui-emoji/main/assets"
  val ICONS_3D = mapOf(
    "coffee" to "$FLUENT_BASE/Hot%20beverage/3D/hot_beverage_3d.png",
    "banana" to "$FLUENT_BASE/Banana/3D/banana_3d.png",
    "oats" to "$FLUENT_BASE/Sheaf%20of%20rice/3D/sheaf_of_rice_3d.png",
    "egg" to "$FLUENT_BASE/Egg/3D/egg_3d.png",
    "chicken" to "$FLUENT_BASE/Poultry%20leg/3D/poultry_leg_3d.png",
    "rice" to "$FLUENT_BASE/Cooked%20rice/3D/cooked_rice_3d.png",
    "salad" to "$FLUENT_BASE/Green%20salad/3D/green_salad_3d.png",
    "paneer" to "$FLUENT_BASE/Cheese%20wedge/3D/cheese_wedge_3d.png",
    "milk" to "$FLUENT_BASE/Glass%20of%20milk/3D/glass_of_milk_3d.png",
    "peanuts" to "$FLUENT_BASE/Peanuts/3D/peanuts_3d.png",
    "sweetPotato" to "$FLUENT_BASE/Roasted%20sweet%20potato/3D/roasted_sweet_potato_3d.png",
    "apple" to "$FLUENT_BASE/Red%20apple/3D/red_apple_3d.png",
    "defaultMeal" to "$FLUENT_BASE/Bento%20box/3D/bento_box_3d.png"
  )

  // 1-Click Quick Add Snacks
  val QUICK_SNACKS = listOf(
    QuickFoodItem(name = "100g Raw Peanuts", calories = 567, protein = 26, emoji = "🥜", iconUrl = ICONS_3D["peanuts"] ?: ""),
    QuickFoodItem(name = "2 Boiled Eggs", calories = 156, protein = 13, emoji = "🥚", iconUrl = ICONS_3D["egg"] ?: ""),
    QuickFoodItem(name = "1 Scoop Whey Protein", calories = 125, protein = 24, emoji = "🥛", iconUrl = ICONS_3D["milk"] ?: ""),
    QuickFoodItem(name = "100g Fresh Paneer", calories = 265, protein = 18, emoji = "🧀", iconUrl = ICONS_3D["paneer"] ?: ""),
    QuickFoodItem(name = "1 Medium Banana", calories = 105, protein = 1, emoji = "🍌", iconUrl = ICONS_3D["banana"] ?: ""),
    QuickFoodItem(name = "50g Roasted Chana", calories = 180, protein = 10, emoji = "🌰"),
    QuickFoodItem(name = "100g Chicken Breast", calories = 165, protein = 31, emoji = "🍗", iconUrl = ICONS_3D["chicken"] ?: ""),
    QuickFoodItem(name = "50g Soya Chunks", calories = 172, protein = 26, emoji = "🌱", iconUrl = ICONS_3D["salad"] ?: "")
  )

  // Quick Food Library for Search Bar
  val FOOD_LIBRARY = listOf(
    QuickFoodItem("100g Raw Peanuts", 567, 26, "Healthy high-calorie fats & clean protein", "peanuts", "🥜", ICONS_3D["peanuts"] ?: ""),
    QuickFoodItem("100g Paneer", 265, 18, "Fresh vegetarian cottage cheese", "paneer", "🧀", ICONS_3D["paneer"] ?: ""),
    QuickFoodItem("100g Chicken Breast", 165, 31, "Boiled or grilled lean protein", "chicken", "🍗", ICONS_3D["chicken"] ?: ""),
    QuickFoodItem("2 Boiled Eggs", 156, 13, "2 whole eggs with essential fats", "egg", "🥚", ICONS_3D["egg"] ?: ""),
    QuickFoodItem("50g Soya Chunks", 172, 26, "High-protein vegetarian staple", "salad", "🌱", ICONS_3D["salad"] ?: ""),
    QuickFoodItem("1 Scoop Whey Protein", 125, 24, "33g serving with water or milk", "milk", "🥛", ICONS_3D["milk"] ?: ""),
    QuickFoodItem("50g Rolled Oats", 195, 8, "Complex carbs with high fiber", "oats", "🌾", ICONS_3D["oats"] ?: ""),
    QuickFoodItem("1 Medium Banana", 105, 1, "Pre-workout instant energy", "banana", "🍌", ICONS_3D["banana"] ?: ""),
    QuickFoodItem("2 Tbsp Peanut Butter (32g)", 190, 8, "Healthy calorie-dense fats", "peanuts", "🥜", ICONS_3D["peanuts"] ?: ""),
    QuickFoodItem("150g Boiled Brown Rice", 165, 4, "1 cup cooked whole grain rice", "rice", "🍚", ICONS_3D["rice"] ?: ""),
    QuickFoodItem("150g Sweet Potato", 130, 2, "Boiled low GI fitness carb", "sweetPotato", "🍠", ICONS_3D["sweetPotato"] ?: ""),
    QuickFoodItem("1 Glass Milk (250ml)", 125, 8, "Low-fat milk with casein", "milk", "🥛", ICONS_3D["milk"] ?: "")
  )

  // Default Diet Plan
  val DEFAULT_DIET_PLAN = listOf(
    DietMealPlan("d1", "Pre-Workout: Coffee + Banana + Soaked Almonds", "1 cup black coffee, 1 ripe banana, 6 soaked almonds", 160, 4, "banana", "🍌", ICONS_3D["banana"] ?: ""),
    DietMealPlan("d2", "Breakfast: Protein Oats + Whey + Chia Seeds", "60g rolled oats, 1 scoop whey, 10g chia seeds, milk", 460, 34, "oats", "🌾", ICONS_3D["oats"] ?: ""),
    DietMealPlan("d3", "Mid-Morning Snack: 100g Raw Peanuts / Boiled Eggs", "100g raw peanuts or 3 boiled eggs for clean protein & fats", 560, 25, "peanuts", "🥜", ICONS_3D["peanuts"] ?: ""),
    DietMealPlan("d4", "Lunch: Chicken Breast / Soya Chunks + Brown Rice", "150g grilled chicken/soya, 1.5 cup brown rice, cucumber salad", 620, 46, "chicken", "🍗", ICONS_3D["chicken"] ?: ""),
    DietMealPlan("d5", "Evening Snack: Roasted Chana + Green Tea", "50g roasted chana (gram), 1 cup green tea, 1 apple", 260, 12, "apple", "🍎", ICONS_3D["apple"] ?: ""),
    DietMealPlan("d6", "Dinner: Fresh Paneer Tikka / Fish + Steamed Veggies", "150g paneer or fish fillet, broccoli, 1 multigrain roti", 540, 36, "paneer", "🧀", ICONS_3D["paneer"] ?: ""),
    DietMealPlan("d7", "Bedtime: Haldi Milk (Turmeric Milk)", "200ml warm milk with turmeric for muscle recovery & sound sleep", 130, 7, "milk", "🥛", ICONS_3D["milk"] ?: "")
  )

  // Full Weekly Workout Split (Mon to Sat active, SUNDAY OFF)
  val INITIAL_ROUTINES: Map<String, List<ExerciseItem>> = mapOf(
    "mon" to listOf(
      ExerciseItem("m1", "Barbell Flat Bench Press", "Chest & Triceps", "4 Sets × 8-10 Reps", "mon"),
      ExerciseItem("m2", "Incline Dumbbell Press", "Upper Chest", "3 Sets × 10-12 Reps", "mon"),
      ExerciseItem("m3", "Overhead Dumbbell Shoulder Press", "Anterior & Lateral Delts", "3 Sets × 10 Reps", "mon"),
      ExerciseItem("m4", "Dumbbell Lateral Raises", "Side Delts", "4 Sets × 15 Reps", "mon"),
      ExerciseItem("m5", "Cable Tricep Pushdowns", "Triceps Lateral Head", "3 Sets × 12 Reps", "mon"),
      ExerciseItem("m6", "Overhead Tricep Rope Extension", "Triceps Long Head", "3 Sets × 12 Reps", "mon")
    ),
    "tue" to listOf(
      ExerciseItem("t1", "Barbell Bent-Over Rows", "Mid Back & Lats", "4 Sets × 8-10 Reps", "tue"),
      ExerciseItem("t2", "Wide Grip Lat Pulldown", "Lats & Teres Major", "3 Sets × 10-12 Reps", "tue"),
      ExerciseItem("t3", "Seated Cable Row", "Rhomboids & Mid Traps", "3 Sets × 12 Reps", "tue"),
      ExerciseItem("t4", "Face Pulls with Rope", "Rear Delts & Rotators", "4 Sets × 15 Reps", "tue"),
      ExerciseItem("t5", "Incline Dumbbell Bicep Curls", "Biceps Long Head", "3 Sets × 12 Reps", "tue"),
      ExerciseItem("t6", "Dumbbell Hammer Curls", "Brachialis & Forearms", "3 Sets × 12 Reps", "tue")
    ),
    "wed" to listOf(
      ExerciseItem("w1", "Barbell Back Squats", "Quads & Glutes", "4 Sets × 8-10 Reps", "wed"),
      ExerciseItem("w2", "Romanian Deadlifts (RDL)", "Hamstrings & Glutes", "3 Sets × 10 Reps", "wed"),
      ExerciseItem("w3", "Leg Press Machine", "Quad Hypertrophy", "3 Sets × 12-15 Reps", "wed"),
      ExerciseItem("w4", "Lying Hamstring Curls", "Hamstring Isolation", "3 Sets × 12 Reps", "wed"),
      ExerciseItem("w5", "Standing Calf Raises", "Calves (Gastrocnemius)", "4 Sets × 15-20 Reps", "wed"),
      ExerciseItem("w6", "Hanging Leg Raises & Plank", "Core & Lower Abs", "3 Sets × 15 Reps", "wed")
    ),
    "thu" to listOf(
      ExerciseItem("th1", "Dumbbell Flat Bench Press", "Chest Volume", "4 Sets × 10-12 Reps", "thu"),
      ExerciseItem("th2", "Incline Cable Flyes", "Chest Inner Squeeze", "3 Sets × 12-15 Reps", "thu"),
      ExerciseItem("th3", "Arnold Dumbbell Press", "Full Shoulder Rotation", "3 Sets × 10 Reps", "thu"),
      ExerciseItem("th4", "Cable Lateral Raises", "Side Delt Hypertrophy", "4 Sets × 15 Reps", "thu"),
      ExerciseItem("th5", "Skull Crushers (EZ Bar)", "Triceps Mass", "3 Sets × 10 Reps", "thu")
    ),
    "fri" to listOf(
      ExerciseItem("f1", "Conventional Deadlifts", "Posterior Chain & Traps", "3 Sets × 6-8 Reps", "fri"),
      ExerciseItem("f2", "T-Bar Supported Rows", "Back Thickness", "4 Sets × 10 Reps", "fri"),
      ExerciseItem("f3", "Close-Grip Lat Pulldown", "Lower Lat Width", "3 Sets × 12 Reps", "fri"),
      ExerciseItem("f4", "Barbell EZ-Bar Bicep Curls", "Biceps Peak", "3 Sets × 10 Reps", "fri"),
      ExerciseItem("f5", "Reverse Grip Forearm Curls", "Forearm Grip Strength", "3 Sets × 15 Reps", "fri")
    ),
    "sat" to listOf(
      ExerciseItem("s1", "Front Squats or Goblet Squats", "Quad Dominance", "4 Sets × 10 Reps", "sat"),
      ExerciseItem("s2", "Walking Dumbbell Lunges", "Glutes & Stability", "3 Sets × 12 Steps/leg", "sat"),
      ExerciseItem("s3", "Leg Extension Machine", "Quad Teardrop Peak", "3 Sets × 15 Reps", "sat"),
      ExerciseItem("s4", "Seated Calf Raises", "Soleus (Lower Calf)", "4 Sets × 20 Reps", "sat"),
      ExerciseItem("s5", "Ab Wheel Rollouts / Cable Crunches", "Abdominal Wall", "4 Sets × 15 Reps", "sat")
    ),
    "sun" to listOf(
      ExerciseItem("su1", "Sunday Off: Full Muscle Recovery", "Rest Day (No Heavy Lifting)", "Rest & Repair", "sun", isCompleted = true),
      ExerciseItem("su2", "Full Body Mobility & Light Stretching", "Joint Flexibility & Soreness Relief", "15-20 Mins", "sun"),
      ExerciseItem("su3", "Hydration Target: 3.5L Fresh Water", "Flush Toxins & Nutrient Delivery", "Daily Goal", "sun")
    )
  )
}
