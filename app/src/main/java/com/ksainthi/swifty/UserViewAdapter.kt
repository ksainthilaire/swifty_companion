package com.ksainthi.swifty

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ksainthi.swifty.fragments.FragmentProjects
import com.ksainthi.swifty.fragments.FragmentSkills
import com.ksainthi.swifty.viewmodels.User

class UserViewAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val user: User,
    var cursusId: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        val args = Bundle()
        args.putParcelable("user", user)
        args.putInt("cursus_id", cursusId)

        val fragment = when (position) {
            0 -> FragmentSkills()
            else -> FragmentProjects()
        }

        fragment.arguments = args
        return fragment
    }
}