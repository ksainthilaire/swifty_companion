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
    var cursus: CursusUser
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {



    override fun getItemCount(): Int {
        return 3
    }

    fun updateCursus(newCursus: CursusUser, currentItem: Int) {
        cursus = newCursus
        notifyItemChanged(currentItem)
    }


    override fun createFragment(position: Int): Fragment {

        cursus ?: run {
            return FragmentNoCursus()
        }

        return when (position) {
            0 -> {

                val fragment = FragmentSkills()
                val params = Bundle()
                val skills: Array<SkillUser> = user.getSkills(cursus.cursusId)

                params.putParcelableArray("skills", skills)
                fragment.setArguments(params)
                return fragment
            }
            1 -> {

                val fragment = FragmentProjects()
                val params = Bundle()
                val projects: Array<ProjectUser> = user.getProjects(cursus.cursusId)
                params.putParcelableArray("projects", projects)

                fragment.setArguments(params)
                return fragment
            }
            else -> {

                return FragmentCorrections()
            }

        }


    }
}