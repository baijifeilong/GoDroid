package io.github.baijifeilong.godroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout
import java.util.*
import kotlin.math.max

class MainActivity : AppCompatActivity(), AnkoLogger, GoDroidApp.IListener {
    override fun onNotify(title: String, content: String) {
        list.add("$title: $content")
        recyclerView.smoothScrollToPosition(max(recyclerView.size - 1, 0))
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    val list = ArrayList<String>()
    lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as GoDroidApp).listener = this

        verticalLayout {
            recyclerView {
                recyclerView = this
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                        return object : RecyclerView.ViewHolder(
                            LayoutInflater.from(this@MainActivity).inflate(
                                android.R.layout.simple_list_item_1,
                                parent,
                                false
                            )
                        ) {}
                    }

                    override fun getItemCount(): Int {
                        return list.size
                    }

                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        holder.itemView.findViewById<TextView>(android.R.id.text1).text = list[position]
                    }
                }
            }
        }

        Timer().scheduleAtFixedRate(object : TimerTask() {
            var count = 0
            override fun run() {
                ++count
                runOnUiThread {
                    "OK $count".apply {
                        println(this)
                        info { this }
                    }
                }
            }
        }, 0, 1000)
    }
}
