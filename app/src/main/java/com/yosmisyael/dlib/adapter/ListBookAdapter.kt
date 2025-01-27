package com.yosmisyael.dlib.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yosmisyael.dlib.activity.DetailActivity
import com.yosmisyael.dlib.data.Book
import com.yosmisyael.dlib.databinding.ItemRowBookBinding

class ListBookAdapter(private val listBook: ArrayList<Book>) : RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listBook.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val context = holder.itemView.context
        val (title, description, photo) = listBook[position]
        holder.binding.imgItemPhoto.setImageResource(photo)
        holder.binding.tvItemTitle.text = title
        holder.binding.tvItemDescription.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listBook[holder.adapterPosition])
            val detailActivity = Intent(context, DetailActivity::class.java)
            detailActivity.putExtra(DetailActivity.EXTRA_BOOK, listBook[position])
            context.startActivity(detailActivity)
        }
    }

    class ListViewHolder(var binding: ItemRowBookBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Book)
    }
}