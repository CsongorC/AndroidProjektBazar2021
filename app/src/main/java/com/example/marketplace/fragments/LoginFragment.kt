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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.LoginViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val editText1: EditText = view.findViewById(R.id.edittext_name_login_fragment)
        val editText2: EditText = view.findViewById(R.id.edittext_password_login_fragment)
        val clickHere: TextView = view.findViewById(R.id.textView_login_fragment_click_here)
        val registerButton: Button = view.findViewById(R.id.button_login_fragment_sign_up)
        val button: Button = view.findViewById(R.id.button_login_fragment)
        clickHere.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        button.setOnClickListener {
            loginViewModel.user.value.let {
                if (it != null) {
                    it.username = editText1.text.toString()
                }
                if (it != null) {
                    it.password = editText2.text.toString()
                }
            }
            lifecycleScope.launch {
                loginViewModel.login()

            }
            loginViewModel.token.observe(viewLifecycleOwner){
                val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
                navBar.visibility = View.VISIBLE
                findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                //findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
            }
        }
        return view
    }
}