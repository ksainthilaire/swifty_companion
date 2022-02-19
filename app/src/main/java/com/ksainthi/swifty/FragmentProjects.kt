package com.ksainthi.swifty

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.ksainthi.swifty.viewmodels.Cursus
import com.ksainthi.swifty.viewmodels.CursusUser
import com.ksainthi.swifty.viewmodels.ProjectUser
import com.ksainthi.swifty.viewmodels.User
import java.util.ArrayList

class FragmentProjects : Fragment() {

    private lateinit var user: User
    private var cursusId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_projects, container, false)

        user = arguments?.getParcelable<User>("user")!!
        cursusId = arguments?.getInt("cursus_id")!!


        val scrollView = rootView.findViewById<ScrollView>(R.id.fragmentProjects)

        val projects: Array<ProjectUser> = user.getParentProjects(cursusId)
        val linearLayout = createProjectsLayout(projects)

        scrollView.addView(linearLayout)
        return rootView
    }

    fun createProjectLayout(project: ProjectUser, isParent: Boolean = false): LinearLayout {

        val row = LinearLayout(context)
        row.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        row.setOrientation(LinearLayout.HORIZONTAL)
        row.setPadding(10)


        val projectName = LinearLayout(context)
        projectName.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
                gravity = Gravity.CENTER_HORIZONTAL
            }
        )
        projectName.setOrientation(LinearLayout.HORIZONTAL)

        val textView = TextView(context)
        textView.setText("${project.project?.name}")
        textView.setLayoutParams(
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
            arrow.setLayoutParams(layoutParams)

            arrow.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_display
                )
            )

            projectName.addView(arrow)
        }

        val colWrapper = LinearLayout(context)
        colWrapper.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        colWrapper.setOrientation(LinearLayout.HORIZONTAL)

        val wrapperImageView = LinearLayout(context)
        wrapperImageView.setLayoutParams(
            TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )
        wrapperImageView.setOrientation(LinearLayout.HORIZONTAL)


        val imageView = ImageView(context)
        imageView.setLayoutParams(
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
        finalMark.setLayoutParams(
            TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )
        finalMark.setPadding(20, 0, 0, 0)
        finalMark.setText(project.finalMark)


        finalMark.setTextColor(color)

        wrapperImageView.addView(imageView)


        colWrapper.addView(wrapperImageView)
        colWrapper.addView(finalMark)





        row.addView(projectName)
        row.addView(colWrapper)
        return row
    }

    fun createProjectsLayout(projects: Array<ProjectUser>): LinearLayout {
        val linearLayout = LinearLayout(context)

        linearLayout.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        linearLayout.setOrientation(LinearLayout.VERTICAL)

        for (project in projects) {

            val childProjects = user.getChildProjects(cursusId, project.project!!.id)

            val isParent = project.status == "parent" || childProjects.isNotEmpty()
            val parentProject = createProjectLayout(project, isParent)

            linearLayout.addView(parentProject)

            if (childProjects.isNotEmpty()) {


                val subProject = LinearLayout(context)
                subProject.setLayoutParams(
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                subProject.setOrientation(LinearLayout.VERTICAL)
                subProject.setVisibility(View.GONE)

                parentProject.setOnClickListener {
                   if (subProject.getVisibility() == View.GONE) {
                       subProject.setVisibility(View.VISIBLE)
                   }
                    else subProject.setVisibility(View.GONE)
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