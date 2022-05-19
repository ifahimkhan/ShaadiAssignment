package com.fahim.shaadi.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.databinding.ProfileItemBinding
import javax.inject.Inject

class DeckRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : ListAdapter<ProfileModel, DeckRecyclerAdapter.DeckViewHolder>(object :
    DiffUtil.ItemCallback<ProfileModel>() {
    override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
        return oldItem == newItem
    }

}) {
/*
    private val diffutil = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {

            return oldItem.id == newItem.id
        }

    }
*/

    class DeckViewHolder(public val itemBinding: ProfileItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val itemBinding = ProfileItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DeckViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val profile = getItem(position)
        holder.itemBinding.apply {
            glide.load(profile.picture).into(image)
            name.text = profile.name
            age.text = "${profile.age} yrs,"
            gender.text = profile.gender
            city.text = profile.city
        }

    }


}