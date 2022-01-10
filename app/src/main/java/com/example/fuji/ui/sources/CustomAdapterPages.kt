package com.example.fuji.ui.sources

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.fuji.R
import com.example.fuji.api.Manga
import com.example.fuji.api.models.ChapterDetail
import com.squareup.picasso.Picasso

class CustomAdapterPages(var context : Context, var source_id : String, var mangas_slug : String, var pages : ArrayList<String> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var page: ImageView

        var containerPages: LinearLayout

        init {
            this.page = row?.findViewById(R.id.page_reader) as ImageView

            this.containerPages = row?.findViewById(R.id.container_page) as LinearLayout
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_item_page, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var page_url: String = getItem(position) as String
        Picasso.get().load(page_url).error(R.drawable.white_image).placeholder( R.drawable.progress_animation ).into(viewHolder.page)

        return view as View
    }

    override fun getCount(): Int {
        return pages.count()
    }

    override fun getItem(position: Int): Any {
        return pages.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}