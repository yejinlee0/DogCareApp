package com.example.dogcareapp

data class Allergy(
    var no: Int,
    var name: String,
    var rate: String,
    var imagePath: String = "none",
){}