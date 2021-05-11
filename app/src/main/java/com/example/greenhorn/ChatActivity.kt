package com.example.greenhorn

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class ChatActivity : AppCompatActivity() {

    var name:String?="a"
    lateinit var listView: ListView
    lateinit var editText: EditText
    lateinit var sndIv: ImageView
    var msgs: ArrayList<String> = arrayListOf("Loreum ipsum dolor sit amet, android Development",
            "android Development android Development android Development android Development lorem ipsum devlopment font greenhorn",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        val bunny = intent.getStringExtra("image")
        name = intent.getStringExtra("name")
        val sP =  getSharedPreferences("sp", Context.MODE_PRIVATE)

        var newStr = ""
        for (item in msgs){
            newStr = newStr + item + "~"
        }
        var result: List<String>? = sP.getString(name, newStr.subSequence(0, newStr.length - 1).toString())?.split("~")?.map { it.trim() }
        if (result != null) {
            msgs.clear()
            for(item in result){
                msgs.add(item)
            }
        }

        var actionBar = getSupportActionBar()
        actionBar?.setDisplayShowCustomEnabled(true);
        actionBar?.setCustomView(R.layout.cutom_action)
        val view: View? = actionBar?.customView
        val textView:TextView? = view?.findViewById(R.id.actionTv)
        val imageView: ImageView? = view?.findViewById(R.id.actionIv)
        textView?.setText(name)

        if (actionBar != null) {
            actionBar?.setDisplayHomeAsUpEnabled(true);
        }
        if (bunny == "0"){
            imageView?.setImageResource(R.drawable.bunny1);
        }else
            imageView?.setImageResource(R.drawable.bunny2);

        sndIv = findViewById(R.id.sndIv)
        editText = findViewById(R.id.et)
        listView = findViewById(R.id.msgListView)
        val myListAdapter = MyListAdapter(this, msgs)
        listView.adapter = myListAdapter
        val inflater = layoutInflater
        val header = inflater.inflate(R.layout.header, listView, false) as ViewGroup
        listView.addHeaderView(header, null, false)

        sndIv.setOnClickListener { v: View->
            var msg:String = editText.text.toString().trim()
            if(msg.isNotEmpty()){
                msgs.add(msg)
                myListAdapter.notifyDataSetChanged()
                editText.setText("")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                backed()
                return true
            }
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backed()
    }

    fun backed(){
        val sharedPreference =  getSharedPreferences("sp", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var str = ""
        for (item in msgs){
            str = str + item + "~"
        }
        str = str.substring(0, str.length - 1)
        editor.putString(name, str).apply()
        finish()
    }
}