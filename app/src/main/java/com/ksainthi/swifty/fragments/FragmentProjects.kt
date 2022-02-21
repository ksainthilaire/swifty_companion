package com.ksainthi.swifty.fragments

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.ksainthi.swifty.R
import com.ksainthi.swifty.viewmodels.ProjectUser
import com.ksainthi.swifty.viewmodels.User

class FragmentProjects : Fragment() {

    private lateinit var user: User
    private var cursusId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_projects, container, false)

        user = arguments?.getParcelable("user")!!
        cursusId = arguments?.getInt("cursus_id")!!


        val scrollView = rootView.findViewById<ScrollView>(R.id.fragmentProjects)

        val projects: Array<ProjectUser> = user.getParentProjects(cursusId)
        val linearLayout = createProjectsLayout(projects)

        scrollView.addView(linearLayout)
        return rootView
    }

    private fun createProjectLayout(project: ProjectUser, isParent: Boolean = false): LinearLayout {

        val row = LinearLayout(context)
        row.layoutParams = (
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        row.orientation = (LinearLayout.HORIZONTAL)
        row.setPadding(10)


        val projectName = LinearLayout(context)
        projectName.layoutParams = (
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
                gravity = Gravity.CENTER_HORIZONTAL
            }
        )
        projectName.orientation = (LinearLayout.HORIZONTAL)

        val textView = TextView(context)
        textView.text = ("${project.project?.name}")
        textView.layoutParams = (
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )


        projectName.addView(textView)

        if (isParent) {
            val arrow = ImageView(context)
            val layoutParams =          LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(20, 15, 0, 0)
            arrow.layoutParams = (layoutParams)

            arrow.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_display
                )
            )

            projectName.addView(arrow)
        }

        val colWrapper = LinearLayout(context)
        colWrapper.layoutParams = (
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        colWrapper.orientation = (LinearLayout.HORIZONTAL)

        val wrapperImageView = LinearLayout(context)
        wrapperImageView.layoutParams = (
            TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )
        wrapperImageView.orientation = (LinearLayout.HORIZONTAL)


        val imageView = ImageView(context)
        imageView.layoutParams = (
            TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )


        var color = ContextCompat.getColor(requireContext(), R.color.green)
        var drawable = R.drawable.ic_success

        if (project.isValidated == false) {
            color = ContextCompat.getColor(requireContext(), R.color.red)
            drawable = R.drawable.ic_failure
        }

        if (project.isValidated != null)
         imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), drawable))

        val finalMark = TextView(context)
        finalMark.layoutParams = (
            TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )
        finalMark.setPadding(20, 0, 0, 0)
        finalMark.text = (project.finalMark)


        finalMark.setTextColor(color)

        wrapperImageView.addView(imageView)


        colWrapper.addView(wrapperImageView)
        colWrapper.addView(finalMark)





        row.addView(projectName)
        row.addView(colWrapper)
        return row
    }

    private fun createProjectsLayout(projects: Array<ProjectUser>): LinearLayout {
        val linearLayout = LinearLayout(context)

        linearLayout.layoutParams = (
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        linearLayout.orientation = (LinearLayout.VERTICAL)

        for (project in projects) {

            val childProjects = user.getChildProjects(cursusId, project.project!!.id)

            val isParent = project.status == "parent" || childProjects.isNotEmpty()
            val parentProject = createProjectLayout(project, isParent)

            linearLayout.addView(parentProject)

            if (childProjects.isNotEmpty()) {


                val subProject = LinearLayout(context)
                subProject.layoutParams = (
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                subProject.orientation = (LinearLayout.VERTICAL)
                subProject.visibility = (View.GONE)

                parentProject.setOnClickListener {
                   if (subProject.visibility == View.GONE) {
                       subProject.visibility = (View.VISIBLE)
                   }
                    else subProject.visibility = (View.GONE)
                }

                for (childProject in childProjects) {
                    val row = createProjectLayout(childProject)
                    row.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
                    row.setPadding(20)
                    subProject.addView(row)
                }
                linearLayout.addView(subProject)
            }


        }
        return linearLayout
    }

}