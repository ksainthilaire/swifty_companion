package com.ksainthi.swifty.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ksainthi.swifty.R
import com.ksainthi.swifty.databinding.FragmentHomeBinding
import com.ksainthi.swifty.presentation.model.ErrorState
import com.ksainthi.swifty.domain.model.User
import com.ksainthi.swifty.presentation.model.Step
import com.ksainthi.swifty.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchView()
        initViewModel()
        if (savedInstanceState == null) {
            viewModel.loadUsers()
        }
    }

    private fun updatePictures(users: List<User>) {
        val pictures = users.filter { user -> user.picture != getDefaultPictureUrl() }
            .map { user -> user.picture }

        for (index in 0..5) {
            val currentUrl = pictures[index]
            val children: ImageView = binding.avatars.getChildAt(index) as ImageView
            Glide.with(requireContext())
                .load(currentUrl)
                .into(children)
        }
    }


    private fun initSearchView() {
        binding.inputSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.loadUser(getSubmittedLogin())
                return false
            }
        })
    }

    private fun updateError(error: ErrorState) {
        binding.errorText.text = error.text
        binding.errorWrapper.visibility = if (error.isVisible) View.VISIBLE else View.GONE
    }


    private fun handleStep(step: Step) {
        when (step) {

            Step.SEARCH -> {

            }
            Step.SWITCH_PROFILE -> {
                val action =
                    FragmentHomeDirections.actionFragmentHomeToFragmentProfile(getSubmittedLogin())
                navController.navigate(action)
            }
        }
    }


    private fun initViewModel() {
        viewModel.homeModel.observe(viewLifecycleOwner) {
            it.users?.let { users -> updatePictures(users) }
            it.error?.let { errors -> updateError(errors) }
            handleStep(it.step)
        }

    }

    private fun getSubmittedLogin(): String = binding.inputSearch.query.toString()
    private fun getDefaultPictureUrl(): String = resources.getString(R.string.default_picture_url)
}
