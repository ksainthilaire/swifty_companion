package com.ksainthi.swifty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ksainthi.swifty.databinding.FragmentProfileBinding
import com.ksainthi.swifty.viewmodels.User
import kotlinx.coroutines.*


class FragmentProfile(val user: User) : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

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