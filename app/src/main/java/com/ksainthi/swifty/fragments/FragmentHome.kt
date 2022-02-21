package com.ksainthi.swifty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ksainthi.swifty.Constants
import com.ksainthi.swifty.MainActivity
import com.ksainthi.swifty.R
import com.ksainthi.swifty.databinding.FragmentHomeBinding
import com.ksainthi.swifty.viewmodels.SearchError
import com.ksainthi.swifty.viewmodels.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var searchError: SearchError
    private lateinit var parentActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        parentActivity = (activity as MainActivity)
        searchError = ViewModelProvider(this).get(SearchError::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.searchError = searchError

        searchError.text.observe(viewLifecycleOwner) { text ->
            binding.errorText.text = text
        }

        binding.inputSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                onEnterSearchUser()
                return false
            }
        })

        loadUsersPictures()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun loadUsersPictures() {
        displayActivityLoading()
        val randomPage = (10..100).random()


        parentActivity.api42Client.accessToken?.let {

            parentActivity.api42Client
                .getService()
                .getUsers(it, randomPage)
                .enqueue(object : Callback<Array<User>> {

                    override fun onResponse(
                        call: Call<Array<User>>,
                        response: Response<Array<User>>
                    ) {

                        if (response.code() == 200) {
                            val users: Array<User>? = response.body()
                            users?.let {
                                users.filter { user -> user.imageUrl != Constants.DEFAULT_PHOTO_URL }

                                for (i in 0..5) {
                                    val children: ImageView = binding.avatars.getChildAt(i) as ImageView
                                    Glide.with(requireContext())
                                        .load(users[i].imageUrl)
                                        .into(children)
                                }
                                dismissActivityLoading()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Array<User>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    /*
    private fun loadUsersPictures() = CoroutineScope(Dispatchers.Main).launch {
        val page = (10..100).random()
        val users = Api42.getUsersByCursus("42cursus", page)
            .filter { user -> user.imageUrl != "https://cdn.intra.42.fr/users/default.png" }

        if (context != null && users.size > 5) {

            for (i in 0..5) {
                Log.d("TAG", "i->${i}")
                val children: ImageView = binding.avatars.getChildAt(i) as ImageView
                Glide.with(requireContext())
                    .load(users[i].imageUrl)
                    .into(children)
            }
        }
        dismissActivityLoading()
    }*/

    /*
        private fun searchUser(login: String) = CoroutineScope(Dispatchers.Main).launch {
            displayActivityLoading()
            try {
                val user: User = withContext(Dispatchers.IO) {
                    val user = Api42.getUser(login)
                    user
                }
                parentActivity.displayProfile(user)
            } catch (e: ClientRequestException) {
                displayError("L'utilisateur $login n'existe pas!")
            }
            dismissActivityLoading()
        }
*/
    fun searchUser(login: String) {
        parentActivity.api42Client.accessToken?.let {
            parentActivity.api42Client.getService().getUser(it, login)
                .enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {

                        when (response.code()) {

                            404 -> {
                                displayError("L'étudiant \"$login\" n'existe pas!")
                            }
                            200 -> {
                                val user: User? = response.body()
                                user?.let {
                                    parentActivity.displayProfile(user)
                                }
                            }
                        }

                    }

                    override fun onFailure(call: Call<User>, throwable: Throwable?) {

                    }

                })
        }
    }

    fun onEnterSearchUser() {
        val username = binding.inputSearch.query.toString()
        searchUser(username)
    }

    private fun displayError(text: String) {
        searchError.createError(text)
    }

    private fun displayActivityLoading() {
        parentActivity.loadingDialog.start()
    }

    private fun dismissActivityLoading() {
        parentActivity.loadingDialog.dismiss()
    }
}
