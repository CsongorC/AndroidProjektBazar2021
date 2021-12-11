package com.example.marketplace.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import kotlinx.coroutines.launch
import java.sql.Timestamp

class DetailByOwnerFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var listViewModel: ListViewModel
    private lateinit var descriptionText: TextView
    private lateinit var sellerText: TextView
    private lateinit var creationTimeText: TextView
    private lateinit var titleText: TextView
    private lateinit var priceText: TextView
    private lateinit var currencyText: TextView
    private lateinit var ammountTypeText: TextView
    private lateinit var goBack: ImageView
    private lateinit var deleteItem: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ProductViewModelFactory(Repository())
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_by_owner, container, false)

        if(view != null){
            initializeView(view)
            loadDetails()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })

        deleteItem = view.findViewById(R.id.delete)
        deleteItem.setOnClickListener {
            lifecycleScope.launch {
                productViewModel.deleteProduct()
            }
            findNavController().navigate(R.id.action_detailByOwnerFragment_to_myMarketFragment)
        }

        goBack = view.findViewById(R.id.back)
        goBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailByOwnerFragment_to_myMarketFragment)
        }
    }

    private fun loadDetails(){
        descriptionText.text = ProductDataStorage.productDetail.description
        sellerText.text = ProductDataStorage.productDetail.username
        val creationTimeLong = ProductDataStorage.productDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        creationTimeText.text = creationTime.toString().subSequence(0,10)
        titleText.text = ProductDataStorage.productDetail.title
        priceText.text = ProductDataStorage.productDetail.price_per_unit
        currencyText.text = ProductDataStorage.productDetail.price_type
        ammountTypeText.text = ProductDataStorage.productDetail.amount_type
    }

    private fun initializeView(view: View) {
        descriptionText = view.findViewById(R.id.description)
        sellerText = view.findViewById(R.id.seller)
        creationTimeText = view.findViewById(R.id.creationTime)
        titleText = view.findViewById(R.id.title)
        priceText = view.findViewById(R.id.price)
        currencyText = view.findViewById(R.id.currency)
        ammountTypeText = view.findViewById(R.id.amount_type)
    }

}