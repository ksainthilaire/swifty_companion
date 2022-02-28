package com.ksainthi.swifty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import com.ksainthi.swifty.R
import com.ksainthi.swifty.domain.model.Project
import com.ksainthi.swifty.presentation.viewmodels.ProjectViewModel




class ProjectsViewAdapter(private val projects: List<Project>) :
    RecyclerView.Adapter<BindingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_row_item, parent, false)
        return BindingViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BindingViewHolder, position: Int) {
        val project = projects.get(position)
        val viewModel = ProjectViewModel(
            title= project.name ?: "",
            finalMark=project.finalMark ?: "",
            isValidated =project.isValidated ?: false
        )
        viewHolder.binding.setVariable(BR.viewModel, viewModel)
    }

    override fun getItemCount() = projects.size

}