package com.example.greenhorn

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyListAdapter(private val context: Activity, private val msgs: ArrayList<String>) : ArrayAdapter<String>(context, R.layout.custom_msg_list, msgs) {

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "ViewHolder", "InflateParams")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm a")
        val currentDate = sdf.format(Date())
        var time = currentDate.subSequence(currentDate.indexOf(" ")+1, currentDate.length).toString().toUpperCase()

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_msg_list, null, true)
        val msgsCv = rowView.findViewById<CardView>(R.id.msgsCV)
        val msgTv = rowView.findViewById<TextView>(R.id.msgsTv)
        val msgTimeTv = rowView.findViewById<TextView>(R.id.msgTimeTv)
        msgTv.text = msgs[position]
        if (position<3){
            msgTimeTv.text = "12:08 PM"
            msgTv.setBackgroundColor(Color.parseColor("#d8d8d8"))
            msgTimeTv.setBackgroundColor(Color.parseColor("#d8d8d8"))
        }else{
            msgTimeTv.text = time
            val params = msgsCv.layoutParams as ConstraintLayout.LayoutParams
            params.apply {
                horizontalBias = 1.0f
            }
            msgTv.setBackgroundColor(Color.parseColor("#4086F7"))
            msgTimeTv.setBackgroundColor(Color.parseColor("#4086F7"))
            msgTimeTv.setTextColor(Color.parseColor("#FFFFFF"))
            msgTv.setTextColor(Color.parseColor("#FFFFFF"))
        }

        return rowView
    }
}