package com.example.androidel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidel.model.TrainerItemModel


class TrainerAdapter(private val trainerList: MutableList<TrainerItemModel>?): RecyclerView.Adapter<TrainerAdapter.ViewHolder>() {
    private var selectBox: CheckBox? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trainer_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            name.text = trainerList?.get(position)?.name ?: ""
            gender.text = trainerList?.get(position)?.gender ?: ""
            time.text = trainerList?.get(position)?.award ?: ""

            checkBox.setOnClickListener {
                if (selectBox != null) {
                    selectBox!!.isChecked = false
                }
                selectBox = checkBox
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
    }
}