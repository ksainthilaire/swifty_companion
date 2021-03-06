package com.ksainthi.swifty.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.swifty.data.api.ApiResult
import com.ksainthi.swifty.data.repository.Repository
import com.ksainthi.swifty.presentation.model.ErrorState
import com.ksainthi.swifty.domain.model.User
import com.ksainthi.swifty.presentation.model.HomeState
import com.ksainthi.swifty.presentation.model.Step
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {



    private lateinit var homeState: HomeState
    val homeModel = MutableLiveData<HomeState>()


    private fun refreshView() {
        homeModel.postValue(homeState)
    }

    private fun onUsersLoaded(result: ApiResult<List<User>, Nothing>)  {
        homeState = when (result) {
            is ApiResult.Success -> {
                HomeState( users = result.data,  error = ErrorState(isVisible = true, text = "Introdd  uvable!"))
            }
            is ApiResult.Error -> {
                HomeState(
                    error = ErrorState(isVisible = true, text = "Introuvable!")
                )
            }
        }
        refreshView()
    }

    private fun onUserLoaded(result: ApiResult<User, Nothing>) {
        homeState = when (result) {
            is ApiResult.Success -> {
                HomeState(
                    step = Step.SWITCH_PROFILE
                )

            }
            is ApiResult.Error -> {
                HomeState(
                    error = ErrorState(isVisible = true, text = "Introuvable!")
                )

            }
        }
        refreshView()
    }

    fun loadUsers() = CoroutineScope(Dispatchers.IO).launch {
        val page: Int = (0..100).random()
        repository.getUsers(page)
            .collect { onUsersLoaded(it) }
    }

    fun loadUser(login: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.getUser(login)
            .collect { onUserLoaded(it) }
    }
}