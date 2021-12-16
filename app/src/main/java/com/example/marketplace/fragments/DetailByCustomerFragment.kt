package com.example.marketplace.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.sql.Timestamp

class DetailByCustomerFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var descriptionText: TextView
    private lateinit var sellerText: TextView
    private lateinit var creationTimeText: TextView
    private lateinit var titleText: TextView
    private lateinit var priceText: TextView
    private lateinit var currencyText: TextView
    private lateinit var ammountTypeText: TextView
    private lateinit var goBack: ImageView
    private lateinit var orderButton: Button
    private lateinit var phoneButton: Button
    private lateinit var emailButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
        val factory2 = UserViewModelFactory(Repository())
        userViewModel = ViewModelProvider(this, factory2).get(UserViewModel::class.java)
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

    @DelicateCoroutinesApi
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

        phoneButton = view.findViewById(R.id.call)
        phoneButton.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_DIAL)
            Log.d("xxx", "Detail - phone number: ${ProductDataStorage.loginUser.phone_number}")
            intent.data = Uri.parse("tel:${ProductDataStorage.loginUser.phone_number}")
            startActivity(intent)
        }

        emailButton = view.findViewById(R.id.email_button)
        emailButton.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(ProductDataStorage.loginUser.email))
            intent.putExtra(Intent.EXTRA_SUBJECT,
                ProductDataStorage.productDetail.title)
            try {
                startActivity(Intent.createChooser(intent, "Send email"))
            } catch (e: Exception) {
                Snackbar.make(view,
                    "No email app found",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadDetails(){
        GlobalScope.launch {
            suspend {
                lifecycleScope.launch {
                    userViewModel.getInfo()
                }
                while(ProductDataStorage.loginUser.email == "" || ProductDataStorage.loginUser.phone_number == ""){
                    delay(1000)
                    Log.d("xxx", "delay() ----------------${ProductDataStorage.loginUser.email}------${ProductDataStorage.loginUser.phone_number}----------------------------------------")
                }
                withContext(Dispatchers.Main) {
                    Log.d("xxx", "loadDetails(): --------------------------------------------------------------")
                }
            }.invoke()
        }
        descriptionText.text = ProductDataStorage.productDetail.description.replace("\"", "")
        sellerText.text = ProductDataStorage.productDetail.username.replace("\"", "")
        val creationTimeLong = ProductDataStorage.productDetail.creation_time
        val creationTime = Timestamp(creationTimeLong)
        creationTimeText.text = creationTime.toString().subSequence(0,10)
        titleText.text = ProductDataStorage.productDetail.title.replace("\"", "")
        val price : String = ProductDataStorage.productDetail.price_per_unit.plus(" ").plus(ProductDataStorage.productDetail.price_type).plus("/").plus(ProductDataStorage.productDetail.amount_type).replace("\"", "")
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