package com.example.androidel.send

import android.app.AlertDialog
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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.androidel.R

class SendAdapter(val onClick: () -> Unit): RecyclerView.Adapter<SendAdapter.ViewHolder>() {
    private var imageUriList: Array<Uri?> = arrayOf()
    private var imageUriOne: Uri? = null
    private lateinit var context: Context
    var count = 3
    var select = 0

    var parts = Array(count) { ArrayList<String?>(3) }
    var sets = Array<String?>(count) { null }
    var reps = Array<String?>(count) { null }
    var degree = Array<String?>(count) { null }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.send_item_view, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.record.setOnClickListener {
            lateinit var btnPart : ArrayList<Button>
            var choiceButton = ArrayList<String?>()
            var choiceMaxSize = 3

            lateinit var btnStrong : ArrayList<Button>
            var choiceStrength = ArrayList<String>()
            var choiceMaxStrength = 1

            var dialogView = View.inflate(context, R.layout.send_exercise_dialog, null)
            var dlg = AlertDialog.Builder(context).create()
            dlg.setView(dialogView)
            dlg.show()

            var body = dialogView.findViewById<Button>(R.id.body)
            var arm = dialogView.findViewById<Button>(R.id.arm)
            var abs = dialogView.findViewById<Button>(R.id.abs)
            var lower = dialogView.findViewById<Button>(R.id.lower)
            var back = dialogView.findViewById<Button>(R.id.back)
            var shoulder = dialogView.findViewById<Button>(R.id.shoulder)
            var chest = dialogView.findViewById<Button>(R.id.chest)
            var waist = dialogView.findViewById<Button>(R.id.waist)
            var hip = dialogView.findViewById<Button>(R.id.hip)

            btnPart = arrayListOf(body, arm, abs, lower, back, shoulder, chest, waist, hip)

            for (i in btnPart.indices) {
                btnPart[i].setOnClickListener {
                    if (choiceButton.size < choiceMaxSize) {
                        if (!btnPart[i].isSelected) {
                            btnPart[i].isSelected = true
                            choiceButton.add(btnPart[i].text.toString())
                        } else {
                            btnPart[i].isSelected = false
                            choiceButton.removeAt(choiceButton.indexOf(btnPart[i].text.toString()))
                        }
                    } else {
                        if (btnPart[i].isSelected) {
                            btnPart[i].isSelected = false
                            choiceButton.removeAt(choiceButton.indexOf(btnPart[i].text.toString()))
                        }
                    }
                }
            }

            var set = dialogView.findViewById<EditText>(R.id.set)
            var number = dialogView.findViewById<EditText>(R.id.number)

            var hard = dialogView.findViewById<Button>(R.id.hard)
            var normal = dialogView.findViewById<Button>(R.id.normal)
            var easy = dialogView.findViewById<Button>(R.id.easy)

            btnStrong = arrayListOf(hard, normal, easy)

            for (i in btnStrong.indices) {
                btnStrong[i].setOnClickListener {
                    if (choiceStrength.size < choiceMaxStrength) {
                        if (!btnStrong[i].isSelected) {
                            btnStrong[i].isSelected = true
                            choiceStrength.add(btnStrong[i].text.toString())
                        } else {
                            btnStrong[i].isSelected = false
                            choiceStrength.removeAt(choiceStrength.indexOf(btnStrong[i].text.toString()))
                        }
                    } else {
                        if (btnStrong[i].isSelected) {
                            btnStrong[i].isSelected = false
                            choiceStrength.removeAt(choiceStrength.indexOf(btnStrong[i].text.toString()))
                        }
                    }
                }
            }

            var btnSave = dialogView.findViewById<Button>(R.id.btnSave)

            btnSave.setOnClickListener {
                holder.itemPart.text = choiceButton.joinToString(", ")
                holder.itemSet.text = set.text
                holder.itemNumber.text = number.text
                holder.itemStrength.text = choiceStrength.joinToString()

                parts[position] = choiceButton
                sets[position] = set.text.toString()
                reps[position] = number.text.toString()
                degree[position] = choiceStrength.joinToString()

                dlg.dismiss()
            }
        }

        if (imageUriOne != null && position == select) {
            holder.video.setImageBitmap(Bitmap.createScaledBitmap(
                createThumbnail(context, imageUriOne.toString())!!,
                holder.video.width,
                holder.video.height,
                false))
            holder.text.visibility = View.GONE
        }

        if(imageUriList.isNotEmpty() && position < imageUriList.size) {
            holder.video.setImageBitmap(Bitmap.createScaledBitmap(
                createThumbnail(context, imageUriList[position].toString())!!, holder.video.width, holder.video.height, false))
            holder.text.visibility = View.GONE
            if (position == count - 1) {
                this.imageUriList = arrayOf()
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
        val text = itemView.findViewById<TextView>(R.id.text)
        val record = itemView.findViewById<ImageButton>(R.id.btnRecord)

        val itemPart = itemView.findViewById<TextView>(R.id.itemPart)
        val itemSet = itemView.findViewById<TextView>(R.id.itemSet)
        val itemNumber = itemView.findViewById<TextView>(R.id.itemNumber)
        val itemStrength = itemView.findViewById<TextView>(R.id.itemStrength)
    }

    fun setVideoList(videoUriList: Array<Uri?>) {
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