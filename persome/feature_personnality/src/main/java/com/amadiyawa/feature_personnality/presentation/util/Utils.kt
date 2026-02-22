package com.amadiyawa.feature_personnality.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Formate un timestamp en date lisible → "21/02/2026"
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

// Formate un timestamp en heure lisible → "14:35"
fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}