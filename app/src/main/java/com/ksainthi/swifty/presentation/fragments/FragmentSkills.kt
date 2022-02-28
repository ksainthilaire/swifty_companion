package com.ksainthi.swifty.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksainthi.swifty.R
import com.ksainthi.swifty.domain.model.Cursus
import com.ksainthi.swifty.presentation.adapter.SkillsViewAdapter

class FragmentSkills : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_skills, container, false)


        val cursus: Cursus? = arguments?.getParcelable("cursus")

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = cursus?.skills?.let { SkillsViewAdapter(it) }

        return rootView
    }




}