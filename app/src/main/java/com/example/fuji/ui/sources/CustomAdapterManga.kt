package com.example.fuji.ui.sources

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.fuji.R
import com.example.fuji.api.Manga
import com.squareup.picasso.Picasso

class CustomAdapterManga(var context : Context, var source_id : String, var mangas : ArrayList<Manga> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var imageManga: ImageView
        var nameManga: TextView
        var containerManga: GridLayout

        init {
            this.imageManga = row?.findViewById(R.id.imageManga) as ImageView
            this.nameManga = row?.findViewById(R.id.nameManga) as TextView
            this.containerManga = row?.findViewById(R.id.container_manga) as GridLayout
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

        // add listener on each button item of listview
        viewHolder.containerManga.setOnClickListener{
            // create intent with MangaDetailsActivity
            val intent : Intent = Intent(context, MangaDetailsActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // pass in extra name of source of the item selected
            intent.putExtra("source_id", source_id)
            intent.putExtra("manga_slug", manga.slug)
            intent.putExtra("manga_title", manga.title)
            startActivity(context, intent, null)
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