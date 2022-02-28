package com.ksainthi.swifty.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ksainthi.swifty.domain.model.Cursus
import com.ksainthi.swifty.presentation.fragments.FragmentProjects
import com.ksainthi.swifty.presentation.fragments.FragmentSkills


class ProfileViewAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val cursus: Cursus
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int { return 2 }

    override fun createFragment(position: Int): Fragment {

        val params = Bundle()
        params.putParcelable("cursus", cursus)


        val fragment = when (position) {
            0 -> FragmentSkills()
            else -> FragmentProjects()
        }

        fragment.arguments = params
        return fragment
    }
}