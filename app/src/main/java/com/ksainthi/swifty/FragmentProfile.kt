package com.ksainthi.swifty

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ksainthi.swifty.databinding.FragmentProfileBinding
import com.ksainthi.swifty.viewmodels.CursusUser
import com.ksainthi.swifty.viewmodels.User
import kotlinx.coroutines.*


class FragmentProfile() : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var user: User
    var cursus: CursusUser? = null


    private var tabTitle = arrayOf("Compétences", "Projets", "Corrections")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        user = arguments?.getParcelable<User>("user")!!
        cursus = user.getCursusByName("42cursus")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        val supportFragmentManager = (activity as MainActivity).supportFragmentManager

        binding.viewPager2.adapter =
            UserViewAdapter(supportFragmentManager, lifecycle, user, cursus!!)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        val spinner = Spinner(context)
        spinner.layoutParams = LinearLayout.LayoutParams(
            200,
            80,
            1f
        )
        spinner.setBackground(
            ContextCompat.getDrawable(requireContext(), R.drawable.user_tag_bg)
        )

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

                val currentItem = binding.viewPager2.getCurrentItem()
                val cursusName = (view as TextView).getText().toString()
                cursus = user.getCursusByName(cursusName)
                binding.viewPager2.adapter = UserViewAdapter(supportFragmentManager, lifecycle, user, cursus!!)
                //binding.viewPager2.adapter?.notifyItemChanged(currentItem)
            }
        }

        binding.cursusSpinnerWrapper.addView(spinner)
        binding.cursusSpinnerWrapper


        loadPicture(user)
        binding.user = user
        return binding.root
    }

    fun loadPicture(user: User) = CoroutineScope(Dispatchers.Main).launch {

        context?.let {
            Glide.with(it)
                .load(user.imageUrl)
                .into(binding.avatar)
        }
    }

}