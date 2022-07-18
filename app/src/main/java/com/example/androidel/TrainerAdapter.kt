package com.example.androidel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TrainerAdapter(val context: Context, val trainerList: ArrayList<TrainerDataClass>): RecyclerView.Adapter<TrainerAdapter.ViewHolder>() {
    var intent: Intent? = null
    var choiceTrainer: MutableList<TrainerDataClass> = arrayListOf()
    var choiceNumber = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trainer_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            image.setImageResource(trainerList[position].image)
            name.text = trainerList[position].name
            day.text = trainerList[position].day
            time.text = trainerList[position].time

            intent = Intent(context, TrainerAgainActivity::class.java)
            chkBox.setOnClickListener {
                if (trainerList[position].isChecked) {
                    trainerList[position].isChecked = false
                    if (choiceTrainer.size == 1) {
                        choiceTrainer.clear()
                    }
                    else {
                        choiceTrainer.removeAt(choiceNumber)
                    }
                }
                else if (!trainerList[position].isChecked) {
                    trainerList[position].isChecked = true
                    choiceTrainer.add(trainerList[position])
                    choiceNumber = choiceTrainer.size-1
                }
                intent!!.putExtra("name", ArrayList(choiceTrainer))
            }
        }
    }

    override fun getItemCount(): Int {
        return trainerList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val name = itemView.findViewById<TextView>(R.id.name)
        val day = itemView.findViewById<TextView>(R.id.day)
        val time = itemView.findViewById<TextView>(R.id.time)
        val chkBox = itemView.findViewById<CheckBox>(R.id.chkBox)
    }
}