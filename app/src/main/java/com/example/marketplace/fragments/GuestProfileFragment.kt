package com.example.marketplace.fragments

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
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ProductDataStorage
import com.example.marketplace.viewmodels.UserViewModel
import com.example.marketplace.viewmodels.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class GuestProfileFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel

    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var fullname: TextView
    private lateinit var goBack: ImageView
    private lateinit var sendEmail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserViewModelFactory( Repository())
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_profile, container, false)

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
            findNavController().navigate(R.id.action_guestProfileFragment_to_listFragment)
        }

        sendEmail = view.findViewById(R.id.send_email)
        sendEmail.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(ProductDataStorage.loginUser.email))
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Marketplace")
            try {
                startActivity(Intent.createChooser(intent, "Send email"))
            } catch (e: Exception) {
                Snackbar.make(view,
                    "No email app found",
                    Snackbar.LENGTH_SHORT).show()
            }
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
                    phoneNumber.text = ProductDataStorage.loginUser.phone_number
                }
            }.invoke()
        }
        ProductDataStorage.loginUser.email = ""
        ProductDataStorage.loginUser.phone_number = ""
    }

    private fun initializeView(view: View) {
        username = view.findViewById(R.id.username_text)
        email = view.findViewById(R.id.email)
        phoneNumber = view.findViewById(R.id.phone_number)
    }
}