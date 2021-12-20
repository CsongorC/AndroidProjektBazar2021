package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ProductDataStorage
import com.example.marketplace.viewmodels.ProductViewModel
import com.example.marketplace.viewmodels.ProductViewModelFactory
import kotlinx.coroutines.*
import java.sql.Timestamp

class EditItemFragment : Fragment() {

    private lateinit var goBack: ImageView
    private lateinit var titleText: EditText
    private lateinit var descriptionText: EditText
    private lateinit var priceText: EditText
    private lateinit var priceTextSuffix: TextView
    private lateinit var sellerText: TextView
    private lateinit var creationTimeText: TextView
    private lateinit var updateProduct: Button
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ProductViewModelFactory(Repository())
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_item, container, false)

        if(view != null){
            initializeView(view)
            loadDetails()
        }

        goBack = view.findViewById(R.id.back)
        goBack.setOnClickListener {
            findNavController().navigate(R.id.detailByOwnerFragment)
        }

        updateProduct = view.findViewById(R.id.save_changes)
        updateProduct.setOnClickListener {
            var title = ""
            var description = ""
            var price = ""
            if(titleText.text.toString() == ""){
                title = ProductDataStorage.productDetail.title
            }else{
                title = titleText.text.toString()
            }

            if(descriptionText.text.toString() == ""){
                description = ProductDataStorage.productDetail.description
            }else{
                description = descriptionText.text.toString()
            }
            if(priceText.text.toString() == ""){
                price = ProductDataStorage.productDetail.price_per_unit
            }else{
                price = priceText.text.toString()
            }

            lifecycleScope.launch {
                productViewModel.updateProduct(
                    ProductDataStorage.productDetail.product_id,
                    title,
                    price,
                    description
                )
            }
            titleText.setText("")
            priceText.setText("")
            descriptionText.setText("")
            findNavController().navigate(R.id.action_editItemFragment_to_myMarketFragment)
        }
        return view
    }

    private fun loadDetails(){
        descriptionText.hint = ProductDataStorage.productDetail.description.replace("\"", "")
        sellerText.hint = ProductDataStorage.productDetail.username.replace("\"", "")
        val creationTimeLong = ProductDataStorage.productDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        creationTimeText.text = creationTime.toString().subSequence(0,10)
        titleText.hint = ProductDataStorage.productDetail.title.replace("\"", "")
        priceText.hint = ProductDataStorage.productDetail.price_per_unit.replace("\"", "")
        priceTextSuffix.text = ProductDataStorage.productDetail.price_type.plus("/").plus(ProductDataStorage.productDetail.amount_type).replace("\"", "")
    }

    private fun initializeView(view: View) {
        descriptionText = view.findViewById(R.id.description_edit)
        sellerText = view.findViewById(R.id.seller_edit)
        creationTimeText = view.findViewById(R.id.creationTime_edit)
        titleText = view.findViewById(R.id.title_edit)
        priceText = view.findViewById(R.id.price_edit)
        priceTextSuffix = view.findViewById(R.id.price_suffix)
    }
}