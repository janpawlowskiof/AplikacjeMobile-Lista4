package com.junapablo.simplegallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GalleryEntry(
    val filename: String,
    val description: String,
    val rating: Float
) : Parcelable {
    fun createThumbnail(context: Context){
        val originalBitmap = Utils.loadBitmapFromAssets(context, filename)
        val scaledBitmap = Utils.resizeBitmap(originalBitmap, 256, 256)
        val thumbnailFilename = Utils.filenameToThumbnailName(filename)
        Utils.saveBitmapToAssets(context, scaledBitmap, thumbnailFilename)
    }

    fun getThumbnailFilename(): String{
        return Utils.filenameToThumbnailName(filename)
    }
}