package com.example.marketplace.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.OrderAdapter
import com.example.marketplace.adapters.SalesAdapter
import com.example.marketplace.model.Order
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.OrderViewModel
import com.example.marketplace.viewmodels.OrderViewModelFactory

class MyFaresOrdersFragment : Fragment(),  OrderAdapter.OnItemClickListener, OrderAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    lateinit var orderViewModel: OrderViewModel
    private lateinit var ongoing_sales: TextView
    private lateinit var recycler_view: RecyclerView
    private lateinit var search_layout: SearchView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_fares_orders, container, false)

        recycler_view = view.findViewById(R.id.recycler_view)
        search_layout = view.findViewById(R.id.search)
        search_layout.setOnQueryTextListener(this)
        setupRecyclerView_forOrders()
        orderViewModel.orders.observe(viewLifecycleOwner){
            orderAdapter.setData(orderViewModel.orders.value as ArrayList<Order>)
            orderAdapter.notifyDataSetChanged()
        }

        ongoing_sales = view.findViewById(R.id.textview_ongoing_sales)
        ongoing_sales.setOnClickListener{
            findNavController().navigate(R.id.myFaresFragment)
        }

        return view
    }

    private fun setupRecyclerView_forOrders(){
        orderAdapter = OrderAdapter(ArrayList<Order>(), ArrayList<Order>(),ArrayList<Order>(), this.requireContext(),this,this)
        recycler_view.adapter =orderAdapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        orderAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        orderAdapter.filter.filter(newText)
        return false
    }
}