package com.example.marketplace.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.model.Order
import com.example.marketplace.viewmodels.*
import java.sql.Timestamp

class SalesAdapter(
    private var list: ArrayList<Order>,
    private var listOfMyProducts: ArrayList<Order>,
    var listFiltered: ArrayList<Order>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<SalesAdapter.DataViewHolder>(), Filterable {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_title: TextView = itemView.findViewById(R.id.textView_name_item_layout)
        val textView_buyer: TextView = itemView.findViewById(R.id.textView_buyer_name)
        val textView_price: TextView = itemView.findViewById(R.id.textView_price_item_layout)
        val textView_amount: TextView = itemView.findViewById(R.id.amount_text)
        val time: TextView = itemView.findViewById(R.id.time_text)
        //val imageView: ImageView = itemView.findViewById(R.id.imageView_item_layout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
            return
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            if (p0 != null) {
                ProductDataStorage.orderDetail = listFiltered[currentPosition]
                p0.findNavController().navigate(R.id.action_listFragment_to_detailByCustomerFragment)
            }
            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sell_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = listFiltered[position]
        holder.textView_title.text = currentItem.title
        holder.textView_buyer.text = currentItem.username
        holder.textView_price.text = currentItem.price_per_unit
        holder.textView_amount.text = currentItem.units
        val creationTimeLong = currentItem.creation_time
        val creationTime = Timestamp(creationTimeLong)
        holder.time.text = creationTime.toString().subSequence(0,10)
    }

    override fun getItemCount() = listFiltered.size

    // Update the list
    fun setData(newlist: ArrayList<Order>) {
        list = newlist.filter{ s -> s.owner_username == ProductDataStorage.username} as ArrayList<Order>
        listFiltered = list
        notifyDataSetChanged()
    }

    fun sortAscending(){
        listFiltered.sortBy {
            it.creation_time
        }
        notifyDataSetChanged()
    }

    fun sortDescending(){
        listFiltered.sortByDescending {
            it.creation_time
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listFiltered = list
                else {
                    val filteredList = ArrayList<Order>()
                    list
                        .filter {
                            (it.title.contains(constraint!!)) or (it.description.contains(constraint))
                        }
                        .forEach { filteredList.add(it) }
                    listFiltered = filteredList

                }
                return FilterResults().apply { values = listFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFiltered = (if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Order>).also { listFiltered = it }
                notifyDataSetChanged()
            }

        }
    }
}

