package com.ksainthi.swifty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import com.ksainthi.swifty.viewmodels.ProjectUser
import java.util.ArrayList

class FragmentProjects : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_projects, container, false)


        val projects: Array<ProjectUser> =
            arguments?.getParcelableArray("projects") as Array<ProjectUser>


        val scrollView = rootView.findViewById<ScrollView>(R.id.fragmentProjects)
        val linearLayout = LinearLayout(context)
        linearLayout.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        linearLayout.setOrientation(LinearLayout.VERTICAL)
        for (projectUser in projects) {


            val row = LinearLayout(context)
            row.setLayoutParams(
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            row.setOrientation(LinearLayout.HORIZONTAL)
            row.setPadding(10)

            val textView = TextView(context)
            textView.setText("${projectUser.project?.name}")
            textView.setLayoutParams(
                TableLayout.LayoutParams(
                    TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1f
                    )
                )
            )

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

            if (projectUser.isValidated == null || projectUser.isValidated == false) {
                color = ContextCompat.getColor(requireContext(), R.color.red)
                drawable = R.drawable.ic_failure
            }


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
            finalMark.setText(projectUser.finalMark)


            finalMark.setTextColor(color)

            wrapperImageView.addView(imageView)


            colWrapper.addView(wrapperImageView)
            colWrapper.addView(finalMark)





            row.addView(textView)
            row.addView(colWrapper)
            linearLayout.addView(row)

        }
        scrollView.addView(linearLayout)
        return rootView
    }


}