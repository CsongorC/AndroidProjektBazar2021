package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ProductDataStorage
import com.example.marketplace.viewmodels.UserViewModel
import com.example.marketplace.viewmodels.UserViewModelFactory
import kotlinx.coroutines.*

class ProfileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var goBack: ImageView
    private lateinit var username : EditText
    private lateinit var email : EditText
    private lateinit var phone : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory1 = UserViewModelFactory( Repository())
        userViewModel = ViewModelProvider(this, factory1).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

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
                    username.hint = ProductDataStorage.loginUser.username
                    email.hint = ProductDataStorage.loginUser.email
                    phone.hint = ProductDataStorage.loginUser.phone_number
                }
            }.invoke()
        }
        ProductDataStorage.loginUser.email = ""
        ProductDataStorage.loginUser.phone_number = ""
    }

    private fun initializeView(view: View) {
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        phone = view.findViewById(R.id.phone_number)
    }
}