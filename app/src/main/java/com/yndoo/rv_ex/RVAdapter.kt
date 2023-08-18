package com.yndoo.rv_ex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class RVAdapter(val items : MutableList<String>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)

        return ViewHolder(view)
    }
    //리사이클러뷰의 아이템 클릭은 직접 선언해서 만들어줘야함
    interface ItemClick {
        fun OnClick(view: View, position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        if (itemClick!=null){
            holder.itemView.setOnClickListener{ v ->
                itemClick?.OnClick(v, position)
            }
        }

        //데이터들을 리사이클러뷰의 아이템으로 뷰바인딩해주는것
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        //전체 리사이클러뷰의 개수
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item : String){ //데이터들 맵핑(?)
            val rvitem = itemView.findViewById<TextView>(R.id.rv_item)
            rvitem.text = item
        }
    }

}