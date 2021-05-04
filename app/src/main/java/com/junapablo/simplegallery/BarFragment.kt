package com.junapablo.simplegallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher


class BarFragment : Fragment() {

    lateinit var newEntryLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_bar, container, false)

        result.findViewById<ImageButton>(R.id.image_take_photo).setOnClickListener {
            newEntryLauncher.launch(Intent(requireContext(), CameraPreview::class.java))
        }

        return result
    }
}