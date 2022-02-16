package com.ksainthi.swifty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ksainthi.swifty.databinding.FragmentHomeBinding
import com.ksainthi.swifty.viewmodels.SearchError
import com.ksainthi.swifty.viewmodels.User
import io.ktor.client.features.*
import kotlinx.coroutines.*

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var searchError: SearchError
    private lateinit var parentActivity: MainActivity

    fun searchUser(login: String) = CoroutineScope(Dispatchers.Main).launch {

        Log.d("RELANCER", "searchUser()")

       try {
           val user: User = withContext(Dispatchers.IO) {
               val user = Api42.getUser(login)
               user
           }
           parentActivity.displayProfile(user)
       } catch (e: ClientRequestException) {
           displayError("L'utilisateur $login n'existe pas!")
       }

    }


    fun onEnterSearchUser() {
        val username = binding.inputSearch.query.toString()
        searchUser(username)
    }


    fun displayError(text: String) {
        searchError.createError(text)
    }


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
        binding.lifecycleOwner = viewLifecycleOwner
        /*
        n case you might be experiencing this issue with LiveData, you might've forgotten to set lifecycle owner of your binding; binding.setLifecycleOwner(lifecycleOwner)
         */
        return binding.root
    }


}