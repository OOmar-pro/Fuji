package com.example.fuji.others

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class DownloadImage(imageView: ImageView): AsyncTask<String?, Void?, Bitmap?>() {
    var imageView: ImageView

    /* doInBackground(Params... params)
         Override this method to perform a computation on a background thread. */
    override fun doInBackground(vararg params: String?): Bitmap? {
        val urlOfImage = params[0]
        var logo: Bitmap? = null
        try {
            val `is`: InputStream = URL(urlOfImage).openStream()
            /* decodeStream(InputStream is)
                 Decode an input stream into a bitmap. */
            logo = BitmapFactory.decodeStream(`is`)
        } catch (e: Exception) { // Catch the download exception
            e.printStackTrace()
        }
        return logo
    }

    /* onPostExecute(Result result)
         Runs on the UI thread after doInBackground(Params...). */
    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }

    init {
        this.imageView = imageView
    }
}