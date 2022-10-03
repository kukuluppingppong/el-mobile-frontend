package com.example.androidel.trainer

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidel.R
import com.example.androidel.trainer.model.TrainerItemModel

class TrainerAdapter(val context: Context, private val trainerList: MutableList<TrainerItemModel>?): RecyclerView.Adapter<TrainerAdapter.ViewHolder>() {
    private var selectBox: CheckBox? = null
    var trainerId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trainer_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            Glide.with(context)
                .asBitmap()
                .load("https://el-trainer.s3.ap-northeast-2.amazonaws.com/" + trainerList?.get(position)?.image)
                .into(image)

            name.text = trainerList?.get(position)?.name ?: ""
            gender.text = trainerList?.get(position)?.gender ?: ""
            time.text = trainerList?.get(position)?.award ?: ""

            checkBox.setOnClickListener {
                if (selectBox != null) {
                    selectBox!!.isChecked = false
                }
                selectBox = checkBox
                if (checkBox.isChecked) {
                   trainerId = trainerList?.get(position)?.trainerId
                } else {
                    trainerId = null
                }
            }

            detail.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://naver.com"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return trainerList!!.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val name = itemView.findViewById<TextView>(R.id.name)
        val gender = itemView.findViewById<TextView>(R.id.gender)
        val time = itemView.findViewById<TextView>(R.id.time)
        val checkBox = itemView.findViewById<CheckBox>(R.id.chkBox)
        val detail = itemView.findViewById<ImageView>(R.id.detail)
    }
}