package com.ksainthi.swifty.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentSkills : Fragment() {
/*

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_skills, container, false)

        val user: User = arguments?.getParcelable("user")!!
        val cursusId: Int = arguments?.getInt("cursus_id")!!

        val skills: Array<SkillUser> = user.getSkills(cursusId)

        val scrollView = rootView.findViewById<ScrollView>(R.id.fragmentSkills)
        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams =
            LinearLayout.LayoutParams(
                MATCH_PARENT,
              WRAP_CONTENT
            )
        linearLayout.orientation = LinearLayout.VERTICAL
        for (skill in skills) {


            val row = LinearLayout(context)
            row.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            row.orientation = LinearLayout.HORIZONTAL

            val textView = TextView(context)
            textView.text = "${skill.name} (${skill.level})"
            textView.layoutParams = (TableLayout.LayoutParams(TableLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f)))




            val skillStartLvl = skill.level.toInt()
            val skillExpCurrentLvl = ((skill.level - skillStartLvl) * 100).toInt()

            val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
            progressBar.layoutParams = (TableLayout.LayoutParams(TableLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f)))
            progressBar.progress = skillExpCurrentLvl
            progressBar.max = 100



            row.addView(textView)
            row.addView(progressBar)
            linearLayout.addView(row)

        }
        scrollView.addView(linearLayout)
        return rootView
    }
*/
}
