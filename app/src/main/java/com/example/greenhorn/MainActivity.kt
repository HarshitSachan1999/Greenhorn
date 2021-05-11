package com.example.greenhorn

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Filter
import android.widget.ListView
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){

    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: RecyclerViewAdapter
    lateinit var searchView: SearchView

    val names : ArrayList<Array<String>> = arrayListOf(arrayOf("Harshit Sachan", "1", "2"),
            arrayOf("Chavi Jain", "1", "2"),
            arrayOf("Prateek Verma", "0", "1"),
            arrayOf("Anjali Singh", "1", "3"),
            arrayOf("Ankita Gupta", "0", "1"),
            arrayOf("Naina Singh", "0", "0"),
            arrayOf("Saloni Mittal", "1", "0"))
    var filteredArrayList: ArrayList<Array<String>> = arrayListOf()
    val lastItem:ArrayList<String?> = arrayListOf("Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin",
            "Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreference =  getSharedPreferences("sp", Context.MODE_PRIVATE)  //to clear saved messages of previous instance
        val editor = sharedPreference.edit()
        editor.clear().apply()

        filteredArrayList = names
        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RecyclerViewAdapter(this, filteredArrayList, recyclerView, lastItem)
        recyclerView.adapter = adapter

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecoration)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                val resultList: ArrayList<Array<String>> = arrayListOf()
                filteredArrayList = if (newText.isNotEmpty()) {

                    for (row in names) {
                        if (row[0].toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }else{
                    names
                }
                adapter = RecyclerViewAdapter(this@MainActivity, filteredArrayList, recyclerView, lastItem)
                recyclerView.adapter = adapter

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

    }

    override fun onResume() {
        super.onResume()
        searchView.setQuery("", false);
        val sP =  getSharedPreferences("sp", Context.MODE_PRIVATE)
        recyclerView.requestFocus()

        for ((count, item) in names.withIndex()){
            val msgString = sP.getString(item[0], "~Hey there, I'd Like to know lorem ipsum, Dollar sit amet, android, java, kotlin")
            lastItem[count] = msgString?.substring(msgString.lastIndexOf("~")+1, msgString.length)
        }
        adapter.notifyDataSetChanged()
    }

}