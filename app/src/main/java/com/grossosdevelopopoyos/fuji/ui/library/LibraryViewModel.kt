package com.grossosdevelopopoyos.fuji.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Votre bibliothèque est vide"
    }
    val text: LiveData<String> = _text
}