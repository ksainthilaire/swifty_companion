package com.ksainthi.swifty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import com.ksainthi.swifty.R
import com.ksainthi.swifty.viewmodels.ProjectUser
import com.ksainthi.swifty.viewmodels.SkillUser
import com.ksainthi.swifty.viewmodels.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSkills.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSkills : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_skills, container, false)

        val user: User = arguments?.getParcelable<User>("user")!!
        val cursusId: Int = arguments?.getInt("cursus_id")!!

        val skills: Array<SkillUser> = user.getSkills(cursusId)

        val scrollView = rootView.findViewById<ScrollView>(R.id.fragmentSkills)
        val linearLayout = LinearLayout(context)
        linearLayout.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ))
        linearLayout.setOrientation(LinearLayout.VERTICAL)
        for (skill in skills) {


            val row = LinearLayout(context)
            row.setLayoutParams(LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
            row.setOrientation(LinearLayout.HORIZONTAL)

            val textView = TextView(context)
            textView.setText("${skill.name} (${skill.level.toString()})")
            textView.setLayoutParams(TableLayout.LayoutParams(TableLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f)))




            val skillStartLvl = skill.level.toInt()
            val skillExpCurrentLvl = ((skill.level - skillStartLvl) * 100).toInt()

            val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
            progressBar.setLayoutParams(TableLayout.LayoutParams(TableLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f)))
            progressBar.setProgress(skillExpCurrentLvl)
            progressBar.setMax(100)



            row.addView(textView)
            row.addView(progressBar)
            linearLayout.addView(row)

        }
        scrollView.addView(linearLayout)
        return rootView
    }

}