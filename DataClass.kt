package com.example.cloudkicks

import android.util.Size

data class DataClass(
    var dataTitle: String?=null,
    var dataDesc: String?=null,
    var dataPrice: String?=null,
    var dataImage: String?=null,
    var dataSize: String?=null
) {
    // No need to explicitly add getter methods, as they are automatically generated for data classes
}

data class CollectionClass(
    var collectionName: String?=null,
    var collectionDescription: String?=null,
    var collectionGoal: String?=null,
    var imageUrlCollection: String?=null
) {
    // No need to explicitly add getter methods, as they are automatically generated for data classes
}
