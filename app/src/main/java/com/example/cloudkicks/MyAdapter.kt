package com.example.cloudkicks
/*
| Thabang Agnes Ramatlakana ST10088708 |
| Makgalotso Maketela ST10089280
| Itumeleng Tshabane ST10088688 |
| Juan Romeo Springbok ST10089499 |
| Daniel Fernandes Lourenço ST10088840 |

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
        // Use Glide library to load the image from the dataList into the ImageView of the holder
         Glide.with(context).load(dataList[position].dataImage).into(holder.recImage)
        holder.recTitle.text = dataList[position].dataTitle
        holder.recDesc.text = dataList[position].dataDesc
        holder.recSize.text = dataList[position].dataSize
    }

}

class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    lateinit var recImage:ImageView
    lateinit var recTitle:TextView
    lateinit var recDesc:TextView
    lateinit var recSize: TextView
    lateinit var recCard: CardView

    init {
        recImage = itemView.findViewById(R.id.recImage)
        recCard = itemView.findViewById(R.id.recCard)
        recDesc = itemView.findViewById(R.id.recDesc)
        recTitle = itemView.findViewById(R.id.recTitle)
        recSize = itemView.findViewById(R.id.recSize)
    }
}