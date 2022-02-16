package com.ksainthi.swifty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import com.ksainthi.swifty.viewmodels.*

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var currentFragment: Fragment
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }





    fun displayProfile(user: User) {

        currentFragment = FragmentProfile(user)

        displayFragment(R.id.topbar, FragmentTopbar())
        displayFragment(R.id.fragment, currentFragment)
    }

    private fun displayHome() {
        currentFragment = FragmentHome()


        displayFragment(R.id.fragment, currentFragment)
    }

    private fun displayFragment(containerViewId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}