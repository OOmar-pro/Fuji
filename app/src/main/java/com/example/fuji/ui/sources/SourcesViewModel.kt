package com.example.fuji.ui.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SourcesViewModel : ViewModel() {

    /* API */
    private val url = "https://localhost:8000/list"

    private val _text = MutableLiveData<String>().apply {
        value = "This is sources Fragment"
    }
    val text: LiveData<String> = _text
}