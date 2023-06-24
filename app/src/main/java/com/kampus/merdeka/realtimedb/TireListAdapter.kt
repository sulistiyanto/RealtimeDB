package com.kampus.merdeka.realtimedb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TireListAdapter(private val foodList: MutableList<User>,
                      private val onItemClickListener: (User) -> Unit
): RecyclerView.Adapter<TireListAdapter.TireAdapterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TireAdapterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tire, parent, false)
        return TireAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

    override fun onBindViewHolder(holder: TireAdapterHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)
        holder.itemView.setOnClickListener {
            onItemClickListener(food)
        }
    }
    fun updateData(newTireList: List<User>) {
        foodList.clear()
        foodList.addAll(newTireList)
        notifyDataSetChanged()
    }

    class TireAdapterHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.addressTextView)

        fun bind(food: User) {
            nameTextView.text = food.name
//            priceTextView.text = food.price
        }
    }
}