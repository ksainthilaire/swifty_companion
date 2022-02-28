package com.ksainthi.swifty.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ksainthi.swifty.R
import com.ksainthi.swifty.databinding.FragmentProfileBinding
import com.ksainthi.swifty.domain.model.User
import com.ksainthi.swifty.presentation.MainActivity
import com.ksainthi.swifty.presentation.adapter.ProfileViewAdapter
import com.ksainthi.swifty.presentation.adapter.ProjectsViewAdapter
import com.ksainthi.swifty.presentation.adapter.SkillsViewAdapter
import com.ksainthi.swifty.presentation.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    private val args: FragmentProfileArgs by navArgs()
    //  private val supportFragmentManager = (activity as MainActivity).supportFragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initViewModel()



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("TAG", "Rappelé plusieurs fois")
                viewModel.loadUser(args.login)

            }
        }
    }


    private fun updateProfile(user: User) {
        val cursus = viewModel.getCurrentCursus()!!

        with(binding) {

            fullName.text = user.fullName
            login.text = user.login
            wallet.text = user.wallet.toString().plus(" ₳")
            cursusLevel.text = resources.getString(R.string.profil_cursus_level, cursus.level.toString())
            correctionPoints.text = user.correctionPoints.toString()

                viewPager2.adapter = ProfileViewAdapter(parentFragmentManager, lifecycle, cursus)
                levelPercent.progress = user.getLevelPercent(cursus.id)

            viewPager2.adapter =
                ProfileViewAdapter(
                    (activity as MainActivity).supportFragmentManager,
                    lifecycle,
                    cursus
                )

        }

        val profileTabs = getProfileTabs()

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = profileTabs[position]
        }.attach()

        loadPicture(user.picture)
    }


    private fun loadPicture(url: String) = viewLifecycleOwner.lifecycleScope.launch {

        context?.let {
            Glide.with(it)
                .load(url)
                .into(binding.avatar)
        }
    }

    private fun onCursusSelectedListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            viewModel.onCursusSelected(p2)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }

    private fun initViewModel() {

        viewModel.user.observe(viewLifecycleOwner) {
            updateProfile(it)
        }


        viewModel.cursusNames.observe(viewLifecycleOwner) {
            with(binding) {
                cursusSpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            }
            binding.cursusSpinner.onItemSelectedListener = null
            binding.cursusSpinner.onItemSelectedListener = onCursusSelectedListener()
        }

        viewModel.selectedCursus.observe(viewLifecycleOwner) {
            viewModel.refreshView()
        }


    }
    private fun getProfileTabs(): Array<String> = resources.getStringArray(R.array.profile_tabs)

}
