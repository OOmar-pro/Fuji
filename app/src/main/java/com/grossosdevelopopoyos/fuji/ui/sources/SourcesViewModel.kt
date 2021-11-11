package com.grossosdevelopopoyos.fuji.ui.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SourcesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Sources Fragment"
    }
    val text: LiveData<String> = _text
}