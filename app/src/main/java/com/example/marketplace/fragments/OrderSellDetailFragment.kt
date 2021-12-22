package com.example.marketplace.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.sql.Timestamp

class OrderSellDetailFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var title: TextView
    private lateinit var amount: TextView
    private lateinit var price: TextView
    private lateinit var status: TextView
    private lateinit var description: TextView
    private lateinit var time: TextView
    private lateinit var buyer: TextView
    private lateinit var accept: Button
    private lateinit var decline: Button
    private lateinit var goBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_sell_detail, container, false)

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
            findNavController().navigate(R.id.myFaresFragment)
        }

        accept.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Accept")
            builder.setMessage("Are you sure you want to accept this order?")
            builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                status.text = "ACCEPTED"
                accept.visibility = View.GONE
                decline.visibility = View.GONE
                lifecycleScope.launch {
                    orderViewModel.upadteOrder(ProductDataStorage.orderDetail.order_id,ProductDataStorage.orderDetail.price_per_unit,"ACCEPTED", ProductDataStorage.orderDetail.title)
                }
            }
            builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }

        decline.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Accept")
            builder.setMessage("Are you sure you want to decline this order?")
            builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                status.text = "DECLINED"
                accept.visibility = View.GONE
                decline.visibility = View.GONE
                lifecycleScope.launch {
                    orderViewModel.upadteOrder(ProductDataStorage.orderDetail.order_id,ProductDataStorage.orderDetail.price_per_unit,"DECLINED", ProductDataStorage.orderDetail.title)
                }
            }
            builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()

        }
    }

    private fun loadDetails(){
        description.text = ProductDataStorage.orderDetail.description.replace("\"", "").replace("\\", "")
        buyer.text = ProductDataStorage.orderDetail.username.replace("\"", "")
        val creationTimeLong = ProductDataStorage.orderDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        time.text = creationTime.toString().subSequence(0,10)
        title.text = ProductDataStorage.orderDetail.title.replace("\"", "")
        price.text = ProductDataStorage.orderDetail.price_per_unit.replace("\"", "").replace("\\", "")
        status.text = ProductDataStorage.orderDetail.status.replace("\"", "")
        amount.text = ProductDataStorage.orderDetail.units.replace("\"", "").replace("\\", "")
        if(ProductDataStorage.orderDetail.status == "DECLINED" || ProductDataStorage.orderDetail.status == "ACCEPTED"){
            accept.visibility = View.GONE
            decline.visibility = View.GONE
        }
    }

    private fun initializeView(view: View) {
        description = view.findViewById(R.id.description)
        buyer = view.findViewById(R.id.buyer)
        time = view.findViewById(R.id.creationTime_orderSell)
        title= view.findViewById(R.id.title)
        price = view.findViewById(R.id.price)
        status = view.findViewById(R.id.status_orderSell)
        amount = view.findViewById(R.id.amount_orderSell)
        accept = view.findViewById(R.id.accept_orderSell)
        decline = view.findViewById(R.id.decline_orderSell)
    }
}