package com.test.fininfocom.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.fininfocom.databinding.ItemViewDetailsBinding
import com.test.fininfocom.details.model.Person

class ViewDetailsAdapter(
    private var personsList: MutableList<Person>,
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<ViewDetailsAdapter.ViewHolder>() {

    private lateinit var context: Context

    interface OnItemClickListener {
        fun onCardDetailsClick(position: Int)
    }

    class ViewHolder(val binding: ItemViewDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        //binding
        val binding = ItemViewDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            personsList[position].apply {

                //serial no
                val slNo = position + 1
                holder.binding.tvId.text = slNo.toString()

                //name
                holder.binding.tvName.text = name.toString()

                //age
                holder.binding.tvAge.text = String.format("${age.toString()} Years")

                //city
                holder.binding.tvCity.text = city.toString()

                //Profession
                holder.binding.tvProfession.text = profession.toString()

                //item click
                holder.binding.detilsCard.setOnClickListener {
                    onItemClickListener.onCardDetailsClick(position)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return personsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
