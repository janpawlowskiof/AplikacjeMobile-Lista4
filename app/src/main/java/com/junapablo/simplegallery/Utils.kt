package com.junapablo.simplegallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

object Utils {
    fun loadBitmapFromAssets(context: Context, assetName: String): Bitmap {
        return BitmapFactory.decodeStream(File(context.filesDir, assetName).inputStream())
    }

    fun checkIfAssetExists(context: Context, assetName: String) : Boolean {
        return File(context.filesDir, assetName).isFile
    }

    fun saveBitmapToAssets(context: Context, bitmap: Bitmap, assetName: String) {
        val outFile = File(context.filesDir, assetName)
        outFile.parentFile.mkdirs()
        outFile.createNewFile()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outFile.outputStream())
    }

    fun filenameToThumbnailName(filename: String): String{
        return "thumbnail/${filename}"
    }

    fun resizeBitmap(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        return if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
        } else {
            image
        }
    }
}