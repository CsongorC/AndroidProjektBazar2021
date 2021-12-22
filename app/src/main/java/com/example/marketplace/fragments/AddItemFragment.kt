package com.example.marketplace.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.RequestBody

class AddItemFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var goBack: ImageView
    private lateinit var close: ImageView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var amount: EditText
    private lateinit var price: EditText
    private lateinit var email: TextView
    private lateinit var username: TextView
    private lateinit var phone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ProductViewModelFactory(Repository())
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
        val factory1 = UserViewModelFactory( Repository())
        userViewModel = ViewModelProvider(this, factory1).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        val spinner_ammount_type: Spinner = view.findViewById(R.id.amount_type_add)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.amount_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_ammount_type.adapter = adapter
        }

        val spinner_price_type: Spinner = view.findViewById(R.id.price_type_add)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.price_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_price_type.adapter = adapter
        }

        if(view != null){
            initializeView(view)
            loadDetails()
        }

        title = view.findViewById(R.id.title_add)
        description = view.findViewById(R.id.description_add)
        price = view.findViewById(R.id.price_add)
        amount = view.findViewById(R.id.amount_add)
        val switchButton : Switch = view.findViewById(R.id.switch1_add)
        var isActive = false
        if(switchButton.isActivated){
            isActive = true
        }



        val button: Button = view.findViewById(R.id.launch_fare)
        button.setOnClickListener {
            if (checkTextViews()) {
                lifecycleScope.launch {
                    productViewModel.addProduct(
                        title.text.toString(),
                        description.text.toString(),
                        price.text.toString(),
                        amount.text.toString(),
                        isActive,
                        5.0,
                        spinner_ammount_type.selectedItem.toString(),
                        spinner_price_type.selectedItem.toString()
                    )
                }
                findNavController().navigate(R.id.myMarketFragment)
                Snackbar.make(
                    requireContext(),
                    it,
                    "Item successfully added",
                    Snackbar.LENGTH_SHORT
                ).show()

                title.text?.clear()
                description.text?.clear()
                price.text?.clear()
                amount.text?.clear()
            }
        }

        return view
    }

    private fun checkTextViews(): Boolean {
        var isValid = false
        listOf<EditText>(title,
            description,
            price,
            amount,).forEach {
            if (it.text.toString().trim().isEmpty()) {
                it.error = "This field cannot be empty."
                isValid = false
            } else {
                isValid = true
            }
        }
        return isValid
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
            findNavController().navigate(R.id.myMarketFragment)
        }
        close = view.findViewById(R.id.exit)
        close.setOnClickListener {
            findNavController().navigate(R.id.myMarketFragment)
        }

    }

    @DelicateCoroutinesApi
    private fun loadDetails(){
        GlobalScope.launch {
            suspend {
                lifecycleScope.launch {
                    userViewModel.getInfo()
                }
                while(ProductDataStorage.loginUser.email == "" || ProductDataStorage.loginUser.phone_number == ""){
                    delay(1000)
                    Log.d("xxx", "delay() ----------------${ProductDataStorage.loginUser.email} ------${ProductDataStorage.loginUser.phone_number}----------------------------------------")
                }
                withContext(Dispatchers.Main) {
                    Log.d("xxx", "loadDetails(): --------------------------------------------------------------")
                    username.text = ProductDataStorage.loginUser.username
                    email.text = ProductDataStorage.loginUser.email
                    phone.text = ProductDataStorage.loginUser.phone_number
                }
            }.invoke()
        }
        ProductDataStorage.loginUser.email = ""
        ProductDataStorage.loginUser.phone_number = ""
    }

    private fun initializeView(view: View) {
        username = view.findViewById(R.id.contact_details_add_username)
        email = view.findViewById(R.id.contact_details_add_email)
        phone = view.findViewById(R.id.contact_details_add_phone)
    }
}