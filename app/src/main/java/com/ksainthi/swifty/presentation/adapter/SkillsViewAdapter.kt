package com.ksainthi.swifty.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import com.ksainthi.swifty.R
import com.ksainthi.swifty.domain.model.Skill
import com.ksainthi.swifty.presentation.viewmodels.SkillViewModel


class SkillsViewAdapter(private val skills: List<Skill>) :
    RecyclerView.Adapter<BindingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.skill_row_item, parent, false)
        return BindingViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BindingViewHolder, position: Int) {
        Log.d("TAG", "Affichage en position ${position}")
        val skill = skills.get(position)


        val skillLevel = skill.level.toInt()
        val progress = ((skill.level - skillLevel) * 100).toInt()

        val viewModel = SkillViewModel(
            name="${skill.name} (${skill.level})",
            progress=progress
        )
        viewHolder.binding.setVariable(BR.viewModel, viewModel)
    }

    override fun getItemCount() = skills.size

}