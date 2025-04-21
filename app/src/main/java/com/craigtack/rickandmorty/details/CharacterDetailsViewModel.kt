package com.craigtack.rickandmorty.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor() : ViewModel() {

    private val inDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    private val outDateFormat = SimpleDateFormat("EEE, MMM d, ''yy", Locale.US)

    fun formatDate(created: String): String {
        val parsedDate = inDateFormat.parse(created) ?: Date()
        return outDateFormat.format(parsedDate)
    }
}