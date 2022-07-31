package com.example.androidel

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class SendAdapter(val onClick: () -> Unit): RecyclerView.Adapter<SendAdapter.ViewHolder>() {
    private var imageUriList: List<Uri> = listOf()
    private var imageUriOne: Uri? = null
    private lateinit var context: Context
    var count = 3
    var select = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.send_item_view, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.record.setOnClickListener {

        }

        if (imageUriOne != null && position == select) {
            holder.video.setImageBitmap(Bitmap.createScaledBitmap(
                createThumbnail(context, imageUriOne.toString())!!,
                holder.video.width,
                holder.video.height,
                false))
        }

        if(imageUriList.isNotEmpty() && position < imageUriList.size) {
            holder.video.setImageBitmap(Bitmap.createScaledBitmap(
                createThumbnail(context, imageUriList[position].toString())!!, holder.video.width, holder.video.height, false))

            if (position == count-1) {
                this.imageUriList = listOf()
            }
        }

        holder.video.setOnClickListener {
            onClick.invoke()
            select = position
            Log.e("태그", select.toString())
        }
    }

    override fun getItemCount(): Int {
        return count
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val video = itemView.findViewById<ImageButton>(R.id.btnVideo)
        val record = itemView.findViewById<Button>(R.id.btnRecord)
    }

    fun setVideoList(videoUriList: List<Uri>) {
        this.imageUriList = videoUriList
        notifyDataSetChanged()
    }

    fun setVideoOne(videoUriList: Uri?) {
        if (videoUriList != null) {
            imageUriOne = videoUriList
        }
        notifyDataSetChanged()
    }

    fun createThumbnail(activity: Context?, path: String?): Bitmap? {
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        var bitmap: Bitmap? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(activity, Uri.parse(path))
            //timeUs는 마이크로 초 이므로 1000000초 곱해줘야 초단위다.
            bitmap = mediaMetadataRetriever.getFrameAtTime(
                1000000,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }
}