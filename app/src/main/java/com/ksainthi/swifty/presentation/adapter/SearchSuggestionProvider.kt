package com.ksainthi.swifty.presentation.adapter

import android.content.SearchRecentSuggestionsProvider

class SearchSuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.ksainthi.swifty.presentation.adapter.SearchSuggestionProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }
}