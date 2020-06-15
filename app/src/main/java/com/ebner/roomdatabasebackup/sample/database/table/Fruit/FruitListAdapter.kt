package com.ebner.roomdatabasebackup.sample.database.table.Fruit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebner.roomdatabasebackup.sample.R

/**
 * Created by raphael on 14.06.2020.
 * Android Room Database Backup Created in com.ebner.roomdatabasebackup.sample.database.table.Fruit
 */
class FruitListAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<Fruit, FruitListAdapter.FruitViewHolder>(TaskDiffCallback()) {


    /*---------------------creates the ViewHolder (returns the view with all items in it)--------------------------*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        return FruitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.content_fruit_list, parent, false)
        )
    }

    /*---------------------Bind the data with the View--------------------------*/
    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    /*---------------------Transform Fruit infos to position number--------------------------*/
    fun getFruitAt(position: Int): Fruit? {
        return getItem(position)
    }


    /*---------------------Creates an onClickListener (when you press on a item, you get the ID, and can do what ever you want--------------------------*/
    interface OnItemClickListener {

        fun onItemClicked(fruit: Fruit)
    }

    /*---------------------get the item from the onBindViewHolder, and apply it to the current view row--------------------------*/
    inner class FruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Fruit, itemclickListener: OnItemClickListener) = with(itemView) {
            //Bind the data with View
            val tvFruitName: TextView = itemView.findViewById(R.id.tv_fruit_name)

            tvFruitName.text = item.name

            itemView.setOnClickListener {
                itemclickListener.onItemClicked(item)
            }
        }
    }

}

/*---------------------Makes the Animation to the recyclerview, when item is changed, added or deleted--------------------------*/
class TaskDiffCallback : DiffUtil.ItemCallback<Fruit>() {
    override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
        //Compare all items, so if there is a new field, add it with &&
        return oldItem.name == newItem.name
    }


}