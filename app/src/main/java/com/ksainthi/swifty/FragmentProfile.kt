package com.ksainthi.swifty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ksainthi.swifty.databinding.FragmentProfileBinding
import com.ksainthi.swifty.viewmodels.User
import kotlinx.coroutines.*


class FragmentProfile(val user: User) : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private var tabTitle = arrayOf("Compétences", "Projets", "Corrections")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        val supportFragmentManager = (activity as MainActivity).supportFragmentManager

        binding.viewPager2.adapter = ViewAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()


        binding.user = user
        loadPicture(user)
        return binding.root
    }

    fun loadPicture(user: User) = CoroutineScope(Dispatchers.Main).launch  {

            context?.let {
                Glide.with(it)
                    .load(user.imageUrl)
                    .into(binding.avatar)
            }
                //binding.avatar.setImageBitmap(picture)


    }

}