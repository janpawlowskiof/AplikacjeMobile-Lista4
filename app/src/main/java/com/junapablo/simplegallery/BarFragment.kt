package com.junapablo.simplegallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_NAME = "arg_name"


class BarFragment : Fragment() {
    private var test_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            test_name = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bar, container, false)
    }

    companion object {
        fun newInstance(name: String) =
            BarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
    }
}