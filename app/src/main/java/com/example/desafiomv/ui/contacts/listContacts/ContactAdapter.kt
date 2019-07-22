package com.example.desafiomv.ui.contacts.listContacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiomv.R
import com.example.desafiomv.databinding.ItemRvBinding
import com.example.desafiomv.model.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var items = mutableListOf<Contact>()
    private lateinit var callBackClick: CallBackClick

    fun setData(data: List<Contact>, callBackClick: CallBackClick) {
        this.items.addAll(data)
        notifyDataSetChanged()
        this.callBackClick = callBackClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemRvBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv, parent, false)
        return ContactViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.setContact(items[position])
        holder.binding.imageTrash.setOnClickListener {
            callBackClick.onClickDelete(items[position])
            items.removeAt(position)
        }

        holder.binding.layout.setOnClickListener {
            callBackClick.onClickUpdate(items[position])
        }
    }

    override fun getItemCount(): Int {
        return if (!items.isNullOrEmpty()) items.size else 0
    }

    class ContactViewHolder(var binding: ItemRvBinding): RecyclerView.ViewHolder(binding.root) {

        fun setContact(contact: Contact) {
            binding.contact = contact

            binding.imageTrash.setOnClickListener{

            }
        }
    }
}