package com.example.marketplace.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
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
import com.example.marketplace.viewmodels.OrderViewModel
import com.example.marketplace.viewmodels.OrderViewModelFactory
import com.example.marketplace.viewmodels.ProductDataStorage
import kotlinx.coroutines.launch
import java.sql.Timestamp

class OrderBuyDetailFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var title: TextView
    private lateinit var amount: TextView
    private lateinit var price: TextView
    private lateinit var status: TextView
    private lateinit var description: TextView
    private lateinit var time: TextView
    private lateinit var buyer: TextView
    private lateinit var goBack: ImageView
    private lateinit var withdraw: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_buy_detail, container, false)

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
            findNavController().navigate(R.id.myFaresOrdersFragment)
        }

        withdraw.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Withdraw")
            builder.setMessage("Are you sure you want to withdraw this order?")
            builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                Toast.makeText(context, "Successful withdrawal. ", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    orderViewModel.removeOrder(ProductDataStorage.orderDetail.order_id)
                }
                findNavController().navigate(R.id.myFaresOrdersFragment)
            }
            builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }

    private fun loadDetails(){
        description.text = ProductDataStorage.orderDetail.description.replace("\"", "").replace("\\", "")
        buyer.text = ProductDataStorage.orderDetail.owner_username.replace("\"", "")
        val creationTimeLong = ProductDataStorage.orderDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        time.text = creationTime.toString().subSequence(0,10)
        title.text = ProductDataStorage.orderDetail.title.replace("\"", "")
        price.text = ProductDataStorage.orderDetail.price_per_unit.replace("\"", "").replace("\\", "")
        status.text = ProductDataStorage.orderDetail.status.replace("\"", "")
        amount.text = ProductDataStorage.orderDetail.units.replace("\"", "").replace("\\", "")
        if(ProductDataStorage.orderDetail.status != "OPEN"){
            withdraw.visibility = View.GONE
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
        withdraw = view.findViewById(R.id.remove_orderBuy)
    }
}