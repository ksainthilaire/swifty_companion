package com.ksainthi.swifty.presentation.model

import com.ksainthi.swifty.domain.model.User

enum class Step {
    SEARCH,
    SWITCH_PROFILE
}

data class HomeState(val users: List<User>? = null,
                     val error: ErrorState? = null,
                     val step: Step = Step.SEARCH)