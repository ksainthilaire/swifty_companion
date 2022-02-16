package com.ksainthi.swifty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class FragmentTopbar : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_topbar, container, false)

        rootView.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            Log.d("TAG", "onBackPressed()")
            (activity as MainActivity).onBackPressed()
        }

        return rootView
    }

}