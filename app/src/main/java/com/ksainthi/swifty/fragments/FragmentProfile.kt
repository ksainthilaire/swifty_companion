package com.ksainthi.swifty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ksainthi.swifty.MainActivity
import com.ksainthi.swifty.R
import com.ksainthi.swifty.UserViewAdapter
import com.ksainthi.swifty.databinding.FragmentProfileBinding
import com.ksainthi.swifty.viewmodels.CursusUser
import com.ksainthi.swifty.viewmodels.User
import kotlinx.coroutines.*


class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var user: User
    lateinit var cursus: CursusUser


    private var tabTitle = arrayOf("Compétences", "Projets")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        user = arguments?.getParcelable("user")!!
        cursus = user.getCursusByName("42cursus")



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        val supportFragmentManager = (activity as MainActivity).supportFragmentManager


        this.updateUserExp()




        binding.viewPager2.adapter =
            UserViewAdapter(supportFragmentManager, lifecycle, user, cursus.cursusId)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        val spinner = Spinner(context)
        spinner.layoutParams = LinearLayout.LayoutParams(
            200,
            80,
            1f
        )
        spinner.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.user_tag_bg)


        val cursusNames = user.getCursusNames()
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cursusNames)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val cursusName = (view as TextView).text.toString()
                cursus = user.getCursusByName(cursusName)
                updateUserExp()
                binding.cursus = cursus
                binding.viewPager2.adapter =
                    UserViewAdapter(supportFragmentManager, lifecycle, user, cursus.cursusId)
            }
        }

        binding.cursusSpinnerWrapper.addView(spinner, 0)


        loadPicture(user)
        binding.user = user
        binding.cursus = cursus
        return binding.root
    }

    private fun loadPicture(user: User) = CoroutineScope(Dispatchers.Main).launch {

        context?.let {
            Glide.with(it)
                .load(user.imageUrl)
                .into(binding.avatar)
        }
    }

    fun updateUserExp() {
        val skillStartLvl = cursus.level.toInt()
        val skillExpCurrentLvl = ((cursus.level - skillStartLvl) * 100).toInt()

        binding.experience.progress = skillExpCurrentLvl
        binding.experience.max = 100
    }
}