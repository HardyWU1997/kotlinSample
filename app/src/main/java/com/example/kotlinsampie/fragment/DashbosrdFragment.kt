package com.example.kotlinsampie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinsampie.R



/**
 * A simple [Fragment] subclass.
 * Use the [DashbosrdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashbosrdFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashbosrd, container, false)
    }


}