package com.ksainthi.swifty

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ksainthi.swifty.viewmodels.CursusUser
import com.ksainthi.swifty.viewmodels.ProjectUser
import com.ksainthi.swifty.viewmodels.SkillUser
import com.ksainthi.swifty.viewmodels.User
import java.util.ArrayList

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

    fun updateCursus(newCursusId: Int, currentItem: Int) {
        cursusId = newCursusId
        notifyItemChanged(currentItem)
    }

    override fun createFragment(position: Int): Fragment {

        val args = Bundle()
        args.putParcelable("user", user)
        args.putInt("cursus_id", cursusId)

        val fragment = when (position) {
            0 -> FragmentSkills()
            else -> FragmentProjects()
        }

        fragment.setArguments(args)
        return fragment
    }
}