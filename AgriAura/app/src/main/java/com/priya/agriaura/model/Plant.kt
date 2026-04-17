package com.priya.agriaura.model

import com.priya.agriaura.R

// 🌱 Plant Data Model
data class Plant(
    val id: Int = 0,
    val name: String,
    val category: String,
    val area: String,
    val container: String,
    val plantingDate: String,
    val lightRequirement: String,
    val careLevel: String,
    val image: Int
)

// 🌿 Master Plant List
val plantList = listOf(

    // 🌽 VEGETABLES
    Plant(1, "Tomato", "Vegetables", "Terrace", "Grow Bag", "10/10/2023", "Full Sun", "Easy", R.drawable.tomato),
    Plant(2, "Chili", "Vegetables", "Terrace", "Clay Pot", "10/10/2023", "Full Sun", "Easy", R.drawable.chili),
    Plant(3, "Brinjal", "Vegetables", "Terrace", "Grow Bag", "10/10/2023", "Full Sun", "Easy", R.drawable.brinjal),
    Plant(4, "Ladies Finger", "Vegetables", "Rooftop", "Grow Bag", "10/10/2023", "Full Sun", "Easy", R.drawable.ladies_finger),
    Plant(5, "Onion", "Vegetables", "Balcony", "Plastic Pot", "10/10/2023", "Partial Sun", "Easy", R.drawable.onion),
    Plant(6, "Bottle Gourd", "Vegetables", "Terrace", "Raised Bed", "10/10/2023", "Full Sun", "Medium", R.drawable.bottle_gourd),
    Plant(7, "Cucumber", "Vegetables", "Terrace", "Vertical Planter", "10/10/2023", "Full Sun", "Easy", R.drawable.cucumber),
    Plant(8, "Carrot", "Vegetables", "Balcony", "Deep Pot", "10/10/2023", "Full Sun", "Easy", R.drawable.carrot),
    Plant(9, "Beans", "Vegetables", "Rooftop", "Grow Bag", "10/10/2023", "Full Sun", "Easy", R.drawable.beans),
    Plant(10, "Radish", "Vegetables", "Balcony", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.radish),
    Plant(11, "Capsicum", "Vegetables", "Indoor", "Plastic Pot", "10/10/2023", "Partial Sun", "Easy", R.drawable.capsicum),
    Plant(12, "Cauliflower", "Vegetables", "Terrace", "Raised Bed", "10/10/2023", "Full Sun", "Medium", R.drawable.cauliflower),

    // 🌿 HERBS
    Plant(13, "Mint", "Herbs", "Balcony", "Hanging Pot", "10/10/2023", "Partial Sun", "Easy", R.drawable.mint),
    Plant(14, "Coriander", "Herbs", "Terrace", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.coriander),
    Plant(15, "Tulsi", "Herbs", "Balcony", "Clay Pot", "10/10/2023", "Full Sun", "Easy", R.drawable.tulsi),
    Plant(16, "Curry Leaves", "Herbs", "Terrace", "Cement Pot", "10/10/2023", "Full Sun", "Easy", R.drawable.curry_leaves),
    Plant(17, "Fenugreek", "Herbs", "Balcony", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.fenugreek),
    Plant(18, "Thyme", "Herbs", "Indoor", "Clay Pot", "10/10/2023", "Partial Sun", "Medium", R.drawable.thyme),
    Plant(19, "Oregano", "Herbs", "Balcony", "Plastic Pot", "10/10/2023", "Full Sun", "Easy", R.drawable.oregano),
    Plant(20, "Parsley", "Herbs", "Indoor", "Plastic Pot", "10/10/2023", "Partial Sun", "Easy", R.drawable.parsley),

    // 🌸 FLOWERS
    Plant(21, "Rose", "Flowers", "Terrace", "Clay Pot", "10/10/2023", "Full Sun", "Medium", R.drawable.rose),
    Plant(22, "Marigold", "Flowers", "Balcony", "Grow Bag", "10/10/2023", "Full Sun", "Easy", R.drawable.marigold),
    Plant(23, "Hibiscus", "Flowers", "Terrace", "Cement Pot", "10/10/2023", "Full Sun", "Medium", R.drawable.hibiscus),
    Plant(24, "Jasmine", "Flowers", "Balcony", "Clay Pot", "10/10/2023", "Partial Sun", "Easy", R.drawable.jasmine),
    Plant(25, "Sunflower", "Flowers", "Rooftop", "Grow Bag", "10/10/2023", "Full Sun", "Easy", R.drawable.sunflower),
    Plant(26, "Petunia", "Flowers", "Balcony", "Hanging Pot", "10/10/2023", "Full Sun", "Easy", R.drawable.petunia),

    // 🍎 FRUITS
    Plant(27, "Strawberry", "Fruits", "Balcony", "Hanging Pot", "10/10/2023", "Full Sun", "Medium", R.drawable.strawberry),
    Plant(28, "Lemon", "Fruits", "Terrace", "Cement Pot", "10/10/2023", "Full Sun", "Medium", R.drawable.lemon),
    Plant(29, "Papaya", "Fruits", "Rooftop", "Grow Bag", "10/10/2023", "Full Sun", "Medium", R.drawable.papaya),
    Plant(30, "Guava", "Fruits", "Terrace", "Grow Bag", "10/10/2023", "Full Sun", "Medium", R.drawable.guava),
    Plant(31, "Pomegranate", "Fruits", "Terrace", "Cement Pot", "10/10/2023", "Full Sun", "Medium", R.drawable.pomegranate),

    // 🥬 LEAFY GREENS
    Plant(32, "Spinach", "Leafy Greens", "Balcony", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.spinach),
    Plant(33, "Lettuce", "Leafy Greens", "Terrace", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.lettuce),
    Plant(34, "Amaranthus", "Leafy Greens", "Balcony", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.amaranthus),
    Plant(35, "Mustard Greens", "Leafy Greens", "Terrace", "Grow Bag", "10/10/2023", "Partial Sun", "Easy", R.drawable.mustard_greens),
    Plant(36, "Swiss Chard", "Leafy Greens", "Indoor", "Deep Pot", "10/10/2023", "Partial Sun", "Easy", R.drawable.swiss_chard)
)