package com.example.fuji.ui.sources

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
import com.example.fuji.api.Source

import com.squareup.picasso.Picasso


class CustomAdapterSource(var context : Context, var sources : ArrayList<Source> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var logoSource: ImageView
        var idSource: TextView
        var nameSource: TextView
        var subtitleSource: TextView
        var buttonLatests: Button

        init {
            this.logoSource = row?.findViewById(R.id.logoSource) as ImageView
            this.idSource = row?.findViewById(R.id.idSource) as TextView
            this.nameSource = row?.findViewById(R.id.nameSource) as TextView
            this.subtitleSource = row?.findViewById(R.id.subtitleSource) as TextView
            this.buttonLatests = row?.findViewById(R.id.latestSource) as Button
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_items_sources, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var source: Source = getItem(position) as Source

        viewHolder.idSource.text = source.id
        viewHolder.nameSource.text = source.name
        viewHolder.subtitleSource.text = source.version

        if (source.img.isEmpty()) {
            Picasso.get().load("https://semantic-ui.com/images/wireframe/white-image.png").into(viewHolder.logoSource)
        }
        else {
            Picasso.get().load(source.img).into(viewHolder.logoSource)
        }

        // add listener on each button item of listview
        viewHolder.buttonLatests.setOnClickListener{
            // create intent with SourceListMangaActivity
            val intent : Intent =  Intent(context, SourceListMangaActivity::class.java)
            // pass in extra name of source of the item selected
            intent.putExtra("id", source.id)
            intent.putExtra("name", source.name)
            startActivity(context, intent, null)
        }

        return view as View
    }

    override fun getCount(): Int {
        return sources.count()
    }

    override fun getItem(position: Int): Any {
        return sources.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}