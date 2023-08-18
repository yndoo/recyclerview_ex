package com.yndoo.rv_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = mutableListOf<String>()

        items.add("a")
        items.add("b")
        items.add("c")

        val rv = findViewById<RecyclerView>(R.id.rv)
        val rvAdapter = RVAdapter(items) //어댑터에 데이터덩어리 보냄

        rv.adapter = rvAdapter          //어댑터 연결
        rv.layoutManager = LinearLayoutManager(this)

        //클릭 이벤트 처리
        rvAdapter.itemClick = object : RVAdapter.ItemClick{
            override fun OnClick(view: View, position: Int) {
                Toast.makeText(baseContext, items[position], Toast.LENGTH_SHORT).show()
            }
        }

    }
}