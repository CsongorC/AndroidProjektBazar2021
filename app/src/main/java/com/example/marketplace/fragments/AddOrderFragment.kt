package com.example.marketplace.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.sql.Timestamp

class AddOrderFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var goBack: ImageView
    private lateinit var exitThis: ImageView
    private lateinit var cancel : Button
    private lateinit var sendOrder : Button
    private lateinit var title: TextView
    private lateinit var seller: TextView
    private lateinit var time: TextView
    private lateinit var price: TextView
    private lateinit var active: TextView
    private lateinit var amount: EditText
    private lateinit var comment: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_add_order, container, false)

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

        goBack = view.findViewById(R.id.back_order)
        goBack.setOnClickListener {
            findNavController().navigate(R.id.listFragment)
        }

        exitThis = view.findViewById(R.id.back)
        exitThis.setOnClickListener {
            findNavController().navigate(R.id.detailByCustomerFragment)
        }

        cancel = view.findViewById(R.id.cancel_order)
        cancel.setOnClickListener {
            findNavController().navigate(R.id.detailByCustomerFragment)
            amount.text.clear()
            comment.text.clear()
        }

        sendOrder = view.findViewById(R.id.send_order)
        sendOrder.setOnClickListener {
            if (checkTextViews()) {
                lifecycleScope.launch {
                    orderViewModel.addOrder(
                        title.text.toString(),
                        ProductDataStorage.productDetail.description,
                        ProductDataStorage.productDetail.price_per_unit,
                        ProductDataStorage.productDetail.units,
                        ProductDataStorage.productDetail.is_active.toString(),
                        ProductDataStorage.productDetail.username,
                        comment.text.toString()
                    )
                }
                findNavController().navigate(R.id.listFragment)
                Snackbar.make(
                    requireContext(),
                    it,
                    "Successful order",
                    Snackbar.LENGTH_SHORT
                ).show()

                amount.text.clear()
                comment.text.clear()
            }
        }

    }

    private fun loadDetails(){
        seller.text = ProductDataStorage.productDetail.username.replace("\"", "")
        val creationTimeLong = ProductDataStorage.productDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        time.text = creationTime.toString().subSequence(0,10)
        title.text = ProductDataStorage.productDetail.title.replace("\"", "")
        val priceText : String = ProductDataStorage.productDetail.price_per_unit.plus(" ").plus(ProductDataStorage.productDetail.price_type).plus("/").plus(ProductDataStorage.productDetail.amount_type)
        price.text = priceText.replace("\"", "")

    }

    private fun initializeView(view: View) {
        seller = view.findViewById(R.id.seller_add_order)
        time = view.findViewById(R.id.time_add_order)
        title = view.findViewById(R.id.title_add_order)
        price = view.findViewById(R.id.price_add_order)
        amount = view.findViewById(R.id.amount_order)
        comment = view.findViewById(R.id.comments)
    }

    private fun checkTextViews(): Boolean {
        var isValid = false
        listOf<EditText>(amount,
            comment).forEach {
            if (it.text.toString().trim().isEmpty()) {
                it.error = "This field cannot be empty."
                isValid = false
            } else {
                isValid = true
            }
        }
        return isValid
    }
}