package com.example.marketplace.fragments

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import kotlinx.coroutines.launch
import java.sql.Timestamp

class DetailByCustomerFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var descriptionText: TextView
    private lateinit var sellerText: TextView
    private lateinit var creationTimeText: TextView
    private lateinit var titleText: TextView
    private lateinit var priceText: TextView
    private lateinit var currencyText: TextView
    private lateinit var ammountTypeText: TextView
    private lateinit var goBack: ImageView
    private lateinit var orderButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_by_customer, container, false)

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

        goBack = view.findViewById(R.id.back)
        goBack.setOnClickListener {
            findNavController().navigate(R.id.listFragment)
        }

        orderButton.setOnClickListener {
            findNavController().navigate(R.id.addOrderFragment)
        }

    }

    private fun loadDetails(){
        descriptionText.text = ProductDataStorage.productDetail.description
        sellerText.text = ProductDataStorage.productDetail.username
        val creationTimeLong = ProductDataStorage.productDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        creationTimeText.text = creationTime.toString().subSequence(0,10)
        titleText.text = ProductDataStorage.productDetail.title
        val price : String = ProductDataStorage.productDetail.price_per_unit.plus(" ").plus(ProductDataStorage.productDetail.price_type).plus("/").plus(ProductDataStorage.productDetail.amount_type)
        priceText.text = price

    }

    private fun initializeView(view: View) {
        descriptionText = view.findViewById(R.id.description)
        sellerText = view.findViewById(R.id.seller)
        creationTimeText = view.findViewById(R.id.creationTime)
        titleText = view.findViewById(R.id.title)
        priceText = view.findViewById(R.id.price)
        orderButton = view.findViewById(R.id.order)
    }

}