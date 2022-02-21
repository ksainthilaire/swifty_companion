package com.ksainthi.swifty

import android.content.SearchRecentSuggestionsProvider

class SearchSuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.ksainthi.swifty.SearchSuggestionProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }
}