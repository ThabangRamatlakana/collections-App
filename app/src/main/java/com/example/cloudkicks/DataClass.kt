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

import android.util.Size

data class DataClass(
    //creating attributes of the table for the shoes
    var dataTitle: String?=null,
    var dataDesc: String?=null,
    var dataImage: String?=null,
    var dataSize: String?=null
) {

}

data class CollectionClass(
    //creating attributes of the table for the collections
    var collectionName: String?=null,
    var collectionDescription: String?=null,
    var collectionGoal: String?=null,
    var imageUrlCollection: String?=null
) {

}
