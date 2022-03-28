package com.example.fuji.ui.library

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.fuji.R
import com.example.fuji.api.Babel
import com.example.fuji.api.Manga
import com.example.fuji.api.Source

import com.squareup.picasso.Picasso


class CustomAdapterLibrary(var context : Context, var mangas : ArrayList<Manga> ) : BaseAdapter() {

    // API
    private val api: Babel = Babel()

    private class ViewHolder(row : View?) {
        var title: TextView
        var image: ImageView

        init {
            this.title = row?.findViewById(R.id.text_manga_title) as TextView
            this.image = row?.findViewById(R.id.image_manga_library) as ImageView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.library_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var manga: Manga = getItem(position) as Manga

        viewHolder.title.text = manga.title
        api.getImage(viewHolder.image, this.context, manga.source_id!!, manga.slug!!)

        return view as View
    }

    override fun getCount(): Int {
        return mangas.count()
    }

    override fun getItem(position: Int): Any {
        return mangas.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}