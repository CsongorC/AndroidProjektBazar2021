package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Switch

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.DataAdapter
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimelineFragment : Fragment() , DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {
    lateinit var listViewModel: ListViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var search_layout: SearchView
    private lateinit var adapter: DataAdapter
    private lateinit var switchButton : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_timeline, container, false)
        recycler_view = view.findViewById(R.id.recycler_view)
        search_layout = view.findViewById(R.id.search)
        search_layout.setOnQueryTextListener(this)
        setupRecyclerView()
        listViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(listViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
        switchButton = view.findViewById(R.id.switch1)

        switchButton.setOnClickListener {
            if (switch1.isChecked){
                adapter.sortDescending()
            }
            else{
                adapter.sortAscending()
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.timeline_action_bar, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search something cool!"
    }

    private fun setupRecyclerView(){
        adapter = DataAdapter(ArrayList<Product>(), ArrayList<Product>(),ArrayList<Product>(), this.requireContext(), this, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_toolbar.title = "Timeline"
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
                    Log.d("xxx", "TimelineFragment - BackPressed")
                }
            })
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