package com.example.androidel

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SendAdapter(val kind: ArrayList<String>): RecyclerView.Adapter<SendAdapter.ViewHolder>() {
    private var imageUriList: List<Uri> = listOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.send_item_view, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKind.text = kind[position]
        if (imageUriList.isNotEmpty()) {
            holder.Video.setImageBitmap(
                Bitmap.createScaledBitmap(createThumbnail(context,
                    imageUriList[position].toString())!!,
                    holder.Video.width, holder.Video.height, false))
        }
    }

    override fun getItemCount(): Int {
        return kind.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvKind = itemView.findViewById<TextView>(R.id.tvKind)
        val Video = itemView.findViewById<ImageButton>(R.id.btnVideo)
    }

    fun setVideoList(videoUriList: List<Uri>) {
        this.imageUriList = videoUriList
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