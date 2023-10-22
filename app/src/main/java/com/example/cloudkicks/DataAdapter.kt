package com.example.cloudkicks
/*
“Save Data in Firebase Realtime Database in Android Studio Using Kotlin.” Www.youtube.com, www.youtube.com/watch?v=pLnhnHwLkYo&feature=youtu.be. Accessed 7 June 2023.
“Java – How to Use EditText User Input, Increment and Decrement Buttons Values in Android – ITecNote.” Itecnote.com, itecnote.com/tecnote/java-how-to-use-edittext-user-input-increment-and-decrement-buttons-values-in-android/. Accessed 7 June 2023.
"Horton, J., 2019. Android Programming with Beginers. 1 ed. Birmingham: Packt Publishing Ltd."
"Agency, T., 2022. How to Save Image to Firebase Storage in Android Studio using Kotlin | Kotlin tutorial 2022 in hindi. [Online]
Available at: https://www.youtube.com/watch?v=mHoBIwR2__0&t=298s
[Accessed 01 June 2023].
Used ChatGPT for debugging and Fixing Alignment
*/

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(private val dataList: ArrayList<DataClass>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare and initialize the views in your grid item layout (data_item.xml)
        val dataImage: ImageView = itemView.findViewById(R.id.recImage)
        val dataSize: TextView = itemView.findViewById(R.id.recSize)
        val dataDesc: TextView = itemView.findViewById(R.id.recDesc)
        val dataTitle: TextView = itemView.findViewById(R.id.recTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        // Bind the data to the views in your grid item layout
        holder.dataImage.setImageResource(R.drawable.uploadimagevector) // Set your desired placeholder image
        holder.dataSize.text = data.dataSize
        holder.dataDesc.text = data.dataDesc
        holder.dataTitle.text = data.dataTitle
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
