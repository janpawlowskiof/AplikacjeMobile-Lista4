package com.junapablo.simplegallery

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast


class MyGalleryItemRecyclerViewAdapter(
    private val galleryItemFragment: GalleryItemFragment
) : RecyclerView.Adapter<MyGalleryItemRecyclerViewAdapter.ViewHolder>() {

    val values = mutableListOf<GalleryEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val ctx = galleryItemFragment.requireContext()
        holder.previewView.setImageBitmap(Utils.loadBitmapFromAssets(ctx, item.getThumbnailFilename()))
        holder.itemView.setOnClickListener {
            Intent(ctx, EditPhotoRatingActivity::class.java).apply {
                putExtra(EditPhotoRatingActivity.EXTRA_GALLERY_ENTRY, item)
            }.also{ intent ->
                galleryItemFragment.editRatingResult.launch(intent)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val previewView: ImageView = view.findViewById(R.id.image_preview)
    }
}