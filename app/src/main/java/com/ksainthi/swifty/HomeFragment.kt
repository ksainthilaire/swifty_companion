package com.ksainthi.swifty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val signInWith42 = rootView.findViewById<Button>(R.id.signInWith42)
        signInWith42.setOnClickListener {

                val activity = activity as MainActivity
                activity.searchUser("kesaint-")


        }

        //    val nextPageButton = rootView.findViewById<ImageButton>(R.id.next_page_button)
        //  nextPageButton.setOnClickListener { }

        //val previousPageButton = rootView.findViewById<ImageButton>(R.id.previous_page_button)
        //previousPageButton.setOnClickListener { }

        //  val currentPage = rootView.findViewById<TextView>(R.id.current_page)

        // val results = rootView.findViewById(R.id.results)

        return rootView
    }


}