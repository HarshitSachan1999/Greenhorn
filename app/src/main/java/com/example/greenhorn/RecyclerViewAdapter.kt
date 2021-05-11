package com.example.greenhorn

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(private val context: Context, private val names: ArrayList<Array<String>>, private val rv: RecyclerView, private val lastItem:ArrayList<String?>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aa")
    private val currentDate = sdf.format(Date())

    class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val name = rowView.findViewById<TextView>(R.id.name)
        val msg = rowView.findViewById<TextView>(R.id.message)
        val timeTv = rowView.findViewById<TextView>(R.id.timeTv)
        val msgCountTv = rowView.findViewById<TextView>(R.id.msgCountTv)
        val msgCountCv = rowView.findViewById<CardView>(R.id.msgCountCv)
        val personDp = rowView.findViewById<ImageView>(R.id.personDp)
        val cl = rowView.findViewById<ConstraintLayout>(R.id.cl)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_person, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {

        if (names[position][1] == "0") {
            holder.personDp.setImageResource(R.drawable.bunny1)
        } else {
            holder.personDp.setImageResource(R.drawable.bunny2)
        }

        holder.name.text = names[position][0]
        holder.msg.text = lastItem[position];
        val bool = lastItem[position].equals("Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin")
        if (!bool){
            holder.msgCountCv.visibility = View.INVISIBLE
        }

        if (!names[position][2].equals("0")) {
            if (!bool) {
                holder.timeTv.text =
                        currentDate.subSequence(currentDate.indexOf(" ", 0, true), currentDate.length)
            }else{
                holder.timeTv.text = "12:08 pm"
            }
            holder.msgCountTv.text = names[position][2]
        } else {
            if (!bool) {
                holder.timeTv.text =
                        currentDate.subSequence(currentDate.indexOf(" ", 0, true), currentDate.length)
            }else{
                holder.timeTv.text = "11/05/21"
            }
            holder.msgCountCv.visibility = View.INVISIBLE
        }

        holder.cl.setOnClickListener{v:View->
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", names[position][0])
            intent.putExtra("image", names[position][1])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }

}