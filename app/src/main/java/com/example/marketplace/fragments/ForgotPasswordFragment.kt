package com.example.marketplace.fragments

import LoginViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sendEmail: Button
    private lateinit var email: EditText
    private lateinit var username: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        email = view.findViewById(R.id.edittext_password_login_fragment)
        username = view.findViewById(R.id.username)
        sendEmail = view.findViewById(R.id.forgot_password_email)
        sendEmail.setOnClickListener {
            lifecycleScope.launch {
                loginViewModel.forgotPassword(username.text.toString(), email.text.toString())
            }
            if (checkTextViews()){
                Snackbar.make(
                    requireContext(),
                    it,
                    "We sent you the email!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }

    private fun checkTextViews(): Boolean {
        var isValid = false
        listOf<EditText>(email,
            username
            ).forEach {
            if (it.text.toString().trim().isEmpty()) {
                it.error = "This field can't be empty."
                isValid = false
            } else {
                isValid = true
            }
        }
        return isValid
    }
}