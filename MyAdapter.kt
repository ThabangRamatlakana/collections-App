package com.example.cloudkicks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MyAdapter(private val context: android.content.Context, private val dataList: List<DataClass>):RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].dataImage).into(holder.recImage)
        holder.recTitle.text = dataList[position].dataTitle
        holder.recDesc.text = dataList[position].dataDesc
        holder.recSize.text = dataList[position].dataSize
        holder.recPrice.text = dataList[position].dataPrice
    }

}

class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    lateinit var recImage:ImageView
    lateinit var recTitle:TextView
    lateinit var recDesc:TextView
    lateinit var recSize: TextView
    lateinit var recCard: CardView
    lateinit var recPrice:TextView

    init {
        recImage = itemView.findViewById(R.id.recImage)
        recCard = itemView.findViewById(R.id.recCard)
        recDesc = itemView.findViewById(R.id.recDesc)
        recTitle = itemView.findViewById(R.id.recTitle)
        recSize = itemView.findViewById(R.id.recSize)
    }
}