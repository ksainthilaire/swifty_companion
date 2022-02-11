package com.ksainthi.swifty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.displayHome()
    }

    fun displayHome() {
        val fragment = HomeFragment()
        displayFragment(fragment)
    }

    fun searchUser(login: String) {
        launch {
            Api42.getToken()
            val user : Api42.User = Api42.getUser(login)
            println(user)
        }
    }

    fun displayProfile(profileId: Int) {
        val fragment = ProfileFragment()

        val args = Bundle()
        args.putInt("profileId", profileId);

        fragment.setArguments(args)
        displayFragment(fragment)
    }

    private fun displayFragment(fragment: Fragment) {
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }
}