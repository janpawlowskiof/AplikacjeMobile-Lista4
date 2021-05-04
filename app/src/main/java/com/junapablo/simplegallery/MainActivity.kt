package com.junapablo.simplegallery

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main_landscape)
        }
    }

    override fun onResume() {
        super.onResume()

        val fragmentGallery = supportFragmentManager.findFragmentById(R.id.fragment_gallery) as GalleryItemFragment
        val fragmentBar = supportFragmentManager.findFragmentById(R.id.fragment_bar) as BarFragment

        fragmentBar.newEntryLauncher = fragmentGallery.addNewEntryResult

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentGallery.setColumns(4)
        } else {
            fragmentGallery.setColumns(8)
        }

        if(fragmentGallery.isEmpty()){
            setupMockDataset()
        }
    }

    private fun setupMockDataset(){
        val fragmentGallery = supportFragmentManager.findFragmentById(R.id.fragment_gallery) as GalleryItemFragment

        fragmentGallery.addEntry(GalleryEntry("supercow.png", "Supercow !!!", 5.5f))
        fragmentGallery.addEntry(GalleryEntry("who_dat_boy.png", "Who him is?", 5.0f))

        fragmentGallery.addEntry(GalleryEntry("1.jpeg", "Zdjęcie nr 1", 1.0f))
        fragmentGallery.addEntry(GalleryEntry("2.jpeg", "Zdjęcie nr 2", 2.0f))
        fragmentGallery.addEntry(GalleryEntry("3.jpg", "Zdjęcie nr 3", 2.5f))
        fragmentGallery.addEntry(GalleryEntry("4.jpg", "Zdjęcie nr 4", 3.5f))
        fragmentGallery.addEntry(GalleryEntry("5.jpeg", "Zdjęcie nr 5", 4.0f))
        fragmentGallery.addEntry(GalleryEntry("6.jpg", "Zdjęcie nr 6", 5.0f))
        fragmentGallery.addEntry(GalleryEntry("7.jpeg", "Zdjęcie nr 7", 6.0f))
    }
}