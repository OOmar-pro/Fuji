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

class CustomAdapterChapter(var context : Context, var source_id : String, var mangas_slug : String, var chapters : ArrayList<ChapterDetail> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var number: TextView
        var title: TextView
        var date: TextView

        var containerChapters: ConstraintLayout

        init {
            this.number = row?.findViewById(R.id.number_chapter) as TextView
            this.title = row?.findViewById(R.id.title_chapter) as TextView
            this.date = row?.findViewById(R.id.date_chapter) as TextView

            this.containerChapters = row?.findViewById(R.id.container_chapitres) as ConstraintLayout
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_item_chapter, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var chapter: ChapterDetail = getItem(position) as ChapterDetail

        viewHolder.number.text = chapter.number
        viewHolder.title.text = chapter.title
        viewHolder.date.text = chapter.date

        // add listener on each button item of listview
//        viewHolder.containerChapters.setOnClickListener{
//            // create intent with MangaDetailsActivity
//            val intent : Intent = Intent(context, ReaderActivity::class.java)
//            // pass in extra name of source of the item selected
//            intent.putExtra("source_id", source_id)
//            intent.putExtra("manga_slug", mangas_slug)
//            intent.putExtra("chapter_number", chapter.number)
//            startActivity(context, intent, null)
//        }

        return view as View
    }

    override fun getCount(): Int {
        return chapters.count()
    }

    override fun getItem(position: Int): Any {
        return chapters.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}