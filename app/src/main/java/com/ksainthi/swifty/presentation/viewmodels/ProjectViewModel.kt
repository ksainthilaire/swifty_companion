package com.ksainthi.swifty.presentation.viewmodels

import androidx.lifecycle.ViewModel

data class ProjectViewModel(
    val title: String,
    val isValidated: Boolean,
    val finalMark: String
) : ViewModel()