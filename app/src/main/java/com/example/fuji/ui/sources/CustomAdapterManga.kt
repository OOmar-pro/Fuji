package com.example.fuji.ui.sources

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fuji.R
import com.example.fuji.api.Manga
import com.squareup.picasso.Picasso

class CustomAdapterManga(var context : Context, var mangas : ArrayList<Manga> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var imageManga: ImageView
        var nameManga: TextView

        init {
            this.imageManga = row?.findViewById(R.id.imageManga) as ImageView
            this.nameManga = row?.findViewById(R.id.nameManga) as TextView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_item_manga_source, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var manga: Manga = getItem(position) as Manga

        viewHolder.nameManga.text = manga.title
        if (manga.img.isEmpty()) {
            Picasso.get().load("https://semantic-ui.com/images/wireframe/white-image.png").into(viewHolder.imageManga)
        }
        else {
            Picasso.get().load(manga.img).into(viewHolder.imageManga)
        }

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