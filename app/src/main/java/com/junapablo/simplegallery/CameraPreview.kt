package com.junapablo.simplegallery

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat

class CameraPreview : AppCompatActivity() {
    lateinit var viewFinder: PreviewView
    lateinit var imageCapture: ImageCapture
    lateinit var inputDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_camera_preview)
        } else {
            setContentView(R.layout.activity_camera_preview_landscape)
        }

        viewFinder = findViewById<PreviewView>(R.id.viewFinder)
        inputDescription = findViewById<TextView>(R.id.input_description)
        findViewById<Button>(R.id.camera_capture_button).setOnClickListener {
            takePhoto()
        }
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            imageCapture = ImageCapture.Builder().setTargetRotation(Surface.ROTATION_90).build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e("mDebug", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = Utils.getAssetJpgFile(this)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("mDebug", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Intent().putExtra(
                        GalleryItemFragment.EXTRA_GALLERY_NEW_ENTRY, GalleryEntry(
                            photoFile.name,
                            inputDescription.text.toString(),
                            3.0f
                        )
                    )
                        .also { intent ->
                            setResult(RESULT_OK, intent)
                        }
                    finish()

                }
            })
    }
}