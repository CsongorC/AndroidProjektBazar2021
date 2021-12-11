package com.example.marketplace.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.marketplace.model.Image
import com.example.marketplace.model.Product
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository

object ProductDataStorage {

    var productDetail = Product(5.0,"","","","",false,"", "",
        "","", listOf<Image>(), 5);

    var loginUser = User("","", "", "")

    var my_product_id : String = ""

    var username : String = ""

}