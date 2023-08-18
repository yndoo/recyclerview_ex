몇년 전에 안드로이드 공부를 처음 할 때 네이버 블로그에 ListView 사용법만 기록했었다. (RecyclerView는 너무 복잡하다고 생각했었음) 그래서 그 이후로 리스트 화면(?)을 제작할 일이 생길 때마다 내 블로그 글을 보고 따라하느라 ListView만 사용하게 됐다. 오늘 RecyclerView에 대해 기록하면, 앞으로 RecyclerView를 사용하겠지!

# 구조
큰 구조는 ListView와 비슷하다.
![](https://velog.velcdn.com/images/kuronuma_daisy/post/7ebdddf8-23db-4ba2-b628-db454a823018/image.png)
1. MainActivity에서 ListViewModel로 데이터 덩어리를 Adapter로 등록
2. Adapter에서 값을 하나씩 item과 맵핑해줌
3. item을 recyclerview에서 보여줌

# 코드
### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### rv_item.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="80dp">
    <TextView
        android:id="@+id/rv_item"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="rvitem"
        android:textSize="20sp"
        android:layout_margin="20dp"/>
</LinearLayout>
```
### RVAdapter.kt
* ListView의 Adapter와 비슷하지만 ViewHolder와 클릭이벤트 처리를 위한 몇몇 코드가 추가됨
```kotlin
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
```
### MainActivity.kt
```kotlin
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
```
# 예시 화면
클릭 시 클릭한 item의 데이터를 토스트 메시지로 보여준다.
![](https://velog.velcdn.com/images/kuronuma_daisy/post/b70fd46a-0069-4bc5-9d72-240e3ba04e5f/image.png)
