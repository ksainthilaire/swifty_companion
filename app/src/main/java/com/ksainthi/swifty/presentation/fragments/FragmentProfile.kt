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
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ksainthi.swifty.R
import com.ksainthi.swifty.databinding.FragmentProfileBinding
import com.ksainthi.swifty.domain.model.User
import com.ksainthi.swifty.presentation.MainActivity
import com.ksainthi.swifty.presentation.adapter.ProfileViewAdapter
import com.ksainthi.swifty.presentation.model.ProfileState
import com.ksainthi.swifty.presentation.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var cursusAdapter: ArrayAdapter<String>
    private val args: FragmentProfileArgs by navArgs()
    //  private val supportFragmentManager = (activity as MainActivity).supportFragmentManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val cursus = viewModel.getCurrentCursus()
        Log.d("TAG", "updateProfile(${cursus?.name}")

        val cursusNames: List<String> = viewModel.getCursusNames()!!

        with(binding) {
            //  viewPager2.adapter = ProfileViewAdapter(supportFragmentManager, lifecycle, cursus)
            fullName.text = user.fullName
            login.text = user.login
            wallet.text = user.wallet.toString().plus(" ₳")
            cursusLevel.text = "Niv. ".plus(cursus?.level)
            correctionPoints.text = user.correctionPoints.toString()
            if (cursus != null) {
                levelPercent.progress = user.getLevelPercent(cursus.id)!!
            }
            viewPager2.adapter = cursus?.let {
                ProfileViewAdapter(
                    (activity as MainActivity).supportFragmentManager,
                    lifecycle,
                    it
                )
            }
        }

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
            Log.d("TAG", "viewModel")
            Log.d("TAG", "Hell" + "o World")
            viewModel.onCursusSelected(p2)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }

    private fun initViewModel() {
        Log.d("TAG", "Encore???")

        viewModel.user.observe(viewLifecycleOwner, Observer {
            updateProfile(it)
        })


        viewModel.cursusNames.observe(viewLifecycleOwner, Observer {
            with(binding) {
                cursusSpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            }
            binding.cursusSpinner.setOnItemSelectedListener(null)
            binding.cursusSpinner.setOnItemSelectedListener(onCursusSelectedListener())
        })

        viewModel.selectedCursus.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "Test")
            viewModel.refreshView()
        })


    }


}
