package com.ksainthi.swifty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.ksainthi.swifty.api.Api42
import com.ksainthi.swifty.dialogs.LoadingDialog
import com.ksainthi.swifty.fragments.FragmentHome
import com.ksainthi.swifty.fragments.FragmentProfile
import kotlinx.coroutines.*
import com.ksainthi.swifty.viewmodels.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var currentFragment: Fragment

    val api42Client: Api42 = Api42()

    private val job = Job()


    val loadingDialog = LoadingDialog(this@MainActivity)
    override val coroutineContext = job + Dispatchers.Main


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadApiToken()
    }

    fun loadApiToken() {
        Log.d("TAG","Requête en cours")
        api42Client.getService().login(Constants.API_42_SECRET, Constants.API_42_UID)
            .enqueue(object :
                Callback<Token> {

                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.code() == 200) {
                        val token = response.body()
                        Log.d("TAG", "Le token est ${token}")
                        token?.let {
                            api42Client.accessToken = it.accessToken
                            displayHome()
                        }
                    }
                }

                override fun onFailure(call: Call<Token>, throwable: Throwable?) {
                    println("Impossible de charger le token")
                }

            })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


    fun displayProfile(user: User) {
        currentFragment = FragmentProfile()

        val params = Bundle()
        params.putParcelable("user", user)

        currentFragment.arguments = params
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