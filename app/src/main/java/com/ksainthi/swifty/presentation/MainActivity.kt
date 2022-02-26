package com.ksainthi.swifty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ksainthi.swifty.R
import com.ksainthi.swifty.data.api.Api42
import com.ksainthi.swifty.data.model.TokenResponse
import com.ksainthi.swifty.domain.model.User
import com.ksainthi.swifty.presentation.dialogs.LoadingDialog
import com.ksainthi.swifty.presentation.fragments.FragmentHome
import com.ksainthi.swifty.presentation.fragments.FragmentProfile
import kotlinx.coroutines.*
import com.ksainthi.swifty.presentation.viewmodels.*
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}