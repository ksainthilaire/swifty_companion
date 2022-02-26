package com.ksainthi.swifty.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.swifty.data.api.ApiResult
import com.ksainthi.swifty.data.repository.Repository
import com.ksainthi.swifty.domain.model.Cursus
import com.ksainthi.swifty.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {



    private var userFromRep: User? = null

    val user = MutableLiveData<User>()
    val cursusNames = MutableLiveData<List<String>>()
    val selectedCursus = MutableLiveData<Int>(0)


    fun onUserProfileLoaded(result: ApiResult<User, Nothing>) {
        Log.d("TAG", "onUserProfileLoaded()")
        when (result) {
            is ApiResult.Success -> {
                userFromRep = result.data
                userFromRep!!.cursus?.let {
                    val names = it.map { cursus -> cursus.name }
                    cursusNames.postValue(names)
                }
                user.postValue(userFromRep!!)
            }
        }
    }

    fun onCursusSelected(position: Int) {
        selectedCursus.postValue(position)
    }

    fun getCurrentCursus(): Cursus? =
        user.value?.cursus?.get(selectedCursus.value!!)

    fun getCursusNames(): List<String>? =
        user.value?.cursus?.map { cursus -> cursus.name }

    fun refreshView() = userFromRep?.let { user.postValue(it) }

    suspend fun loadUser(login: String) {
        Log.d("TAG", "loadUser()")
        repository.getUser(login)
            .collect { onUserProfileLoaded(it) }
    }
}