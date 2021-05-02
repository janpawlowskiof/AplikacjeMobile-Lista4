package com.junapablo.simplegallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class EditPhotoRatingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo_rating)

        val originalEntry = intent.extras?.get(EXTRA_GALLERY_ENTRY) as GalleryEntry

        val imageFull = findViewById<ImageView>(R.id.image_full)
        val bitmap = Utils.loadBitmapFromAssets(this, originalEntry.filename)
        imageFull.setImageBitmap(bitmap)

        val descriptionTextView = findViewById<TextView>(R.id.description)
        descriptionTextView.text = originalEntry.description

        val ratingBar = findViewById<RatingBar>(R.id.rating_bar)
        ratingBar.rating = originalEntry.rating

        val saveButton = findViewById<Button>(R.id.buttonSave)
        saveButton.setOnClickListener {
            val newEntry = GalleryEntry(
                    originalEntry.filename,
                    originalEntry.description,
                    ratingBar.rating
            )
            Intent().putExtra(GalleryItemFragment.EXTRA_GALLERY_OLD_ENTRY, originalEntry)
                    .putExtra(GalleryItemFragment.EXTRA_GALLERY_NEW_ENTRY, newEntry)
                .also{ intent ->
                setResult(RESULT_OK, intent)
            }
            finish()
        }

        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        cancelButton.setOnClickListener {
            finish()
        }
    }

    companion object{
        val EXTRA_GALLERY_ENTRY = "extra_gallry_entry"
    }
}