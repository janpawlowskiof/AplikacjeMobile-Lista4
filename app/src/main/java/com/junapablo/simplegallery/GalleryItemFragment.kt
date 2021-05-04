package com.junapablo.simplegallery

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts


class GalleryItemFragment : Fragment() {

    lateinit var galleryAdapter: MyGalleryItemRecyclerViewAdapter

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(BUNDLE_GALLERY_ENTRIES, galleryAdapter.values.toTypedArray())
    }


    fun setColumns(columns: Int){
        if(view is RecyclerView){
            (view as RecyclerView).layoutManager = GridLayoutManager(context, columns)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            galleryAdapter = MyGalleryItemRecyclerViewAdapter(this)
            view.adapter = galleryAdapter
        }

        val galleryEntries = savedInstanceState?.getParcelableArray(BUNDLE_GALLERY_ENTRIES)
        if(galleryEntries != null){
            for(entry in galleryEntries.filterIsInstance<GalleryEntry>()){
                addEntry(entry)
            }
        }

        return view
    }

    fun addEntry(entry: GalleryEntry){
        entry.createThumbnail(requireContext())
        galleryAdapter.values.add(entry)
        galleryAdapter.values.sortByDescending { it.rating }
        galleryAdapter.notifyDataSetChanged()
    }

    fun isEmpty(): Boolean{
        return galleryAdapter.values.isEmpty()
    }

    fun replaceEntry(oldEntry: GalleryEntry, newEntry: GalleryEntry){
        galleryAdapter.values.remove(oldEntry)
        galleryAdapter.values.add(newEntry)
        galleryAdapter.values.sortByDescending { it.rating }
        galleryAdapter.notifyDataSetChanged()
    }

    val editRatingResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val oldEntry = result.data?.extras?.get(EXTRA_GALLERY_OLD_ENTRY)
            val newEntry = result.data?.extras?.get(EXTRA_GALLERY_NEW_ENTRY)
            if (oldEntry !is GalleryEntry)
                return@registerForActivityResult
            if (newEntry !is GalleryEntry)
                return@registerForActivityResult

            replaceEntry(oldEntry, newEntry)
        }
    }

    val addNewEntryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newEntry = result.data?.extras?.get(EXTRA_GALLERY_NEW_ENTRY)
            if (newEntry !is GalleryEntry)
                return@registerForActivityResult

            addEntry(newEntry)
        }
    }


    companion object {
        const val EXTRA_GALLERY_OLD_ENTRY = "extra_gallery_old_entry"
        const val EXTRA_GALLERY_NEW_ENTRY = "extra_gallery_new_entry"

        const val BUNDLE_GALLERY_ENTRIES = "bundle_gallery_entries"
    }
}