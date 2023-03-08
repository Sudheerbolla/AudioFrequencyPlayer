package com.audiofrequencyplayer.utils

const val MIN_FREQUENCY = 1
const val MAX_FREQUENCY = 24000

internal fun getFrequencyValue(frequency: Int): Int {
    return when {
        frequency < MIN_FREQUENCY -> {
            MIN_FREQUENCY
        }
        frequency > MAX_FREQUENCY -> {
            MAX_FREQUENCY
        }
        else -> frequency
    }
}