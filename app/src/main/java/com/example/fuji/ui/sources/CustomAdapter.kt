package com.example.fuji.ui.sources

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fuji.R
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class CustomAdapter(var context : Context, var sources : ArrayList<Source> ) : BaseAdapter() {

    private class ViewHolder(row : View?) {
        var logoSource: ImageView
        var nameSource: TextView
        var subtitleSource: TextView

        init {
            this.logoSource = row?.findViewById(R.id.logoSource) as ImageView
            this.nameSource = row?.findViewById(R.id.nameSource) as TextView
            this.subtitleSource = row?.findViewById(R.id.subtitleSource) as TextView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.row_items_sources, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var source: Source = getItem(position) as Source

        viewHolder.nameSource.text = source.name
        viewHolder.subtitleSource.text = source.version
        DownLoadImageTask(viewHolder.logoSource).execute(source.img)

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

    private class DownLoadImageTask(imageView: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {
        var imageView: ImageView

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        override fun doInBackground(vararg params: String?): Bitmap? {
            val urlOfImage = params[0]
            var logo: Bitmap? = null
            try {
                val `is`: InputStream = URL(urlOfImage).openStream()
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */logo = BitmapFactory.decodeStream(`is`)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
            }
            return logo
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }

        init {
            this.imageView = imageView
        }
    }
}