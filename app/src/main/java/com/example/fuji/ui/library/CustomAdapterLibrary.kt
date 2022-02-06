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
import com.example.fuji.api.Manga
import com.example.fuji.api.Source

import com.squareup.picasso.Picasso


class CustomAdapterLibrary(var context : Context, var mangas : ArrayList<Manga> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var slug: TextView
        var title: TextView
        var source_id: TextView

        init {
            this.slug = row?.findViewById(R.id.text_manga_slug) as TextView
            this.title = row?.findViewById(R.id.text_manga_title) as TextView
            this.source_id = row?.findViewById(R.id.text_source_id) as TextView
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

        viewHolder.slug.text = manga.slug
        viewHolder.title.text = manga.title
        viewHolder.source_id.text = manga.url

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