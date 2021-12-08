package com.example.marketplace.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.marketplace.model.Image
import com.example.marketplace.model.Product

object ProductDataStorage {

    var productDetail = Product(5.0,"","","","",false,"", "",
        "","", listOf<Image>(), 5);

}