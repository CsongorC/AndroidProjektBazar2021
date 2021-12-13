package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.DataAdapter
import com.example.marketplace.adapters.OrderAdapter
import com.example.marketplace.model.Order
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory
import com.example.marketplace.viewmodels.OrderViewModel
import com.example.marketplace.viewmodels.OrderViewModelFactory
import kotlinx.android.synthetic.main.fragment_timeline.*

class MyFaresFragment : Fragment(), OrderAdapter.OnItemClickListener, OrderAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    private lateinit var adapter: OrderAdapter
    private lateinit var recycler_view: RecyclerView
    lateinit var orderViewModel: OrderViewModel
    private lateinit var search_layout: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_fares, container, false)

        recycler_view = view.findViewById(R.id.recycler_view)
        search_layout = view.findViewById(R.id.search)
        search_layout.setOnQueryTextListener(this)
        setupRecyclerView()
        orderViewModel.orders.observe(viewLifecycleOwner){
            adapter.setData(orderViewModel.orders.value as ArrayList<Order>)
            adapter.notifyDataSetChanged()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_toolbar.setOnMenuItemClickListener {
                menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.profile -> findNavController().navigate(R.id.profileFragment2)
                else -> false
            }
            true
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("xxx", "MyFaresFragment - BackPressed")
                }
            })
    }

    private fun setupRecyclerView(){
        adapter = OrderAdapter(ArrayList<Order>(), ArrayList<Order>(),ArrayList<Order>(), this.requireContext(),this,this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }
}