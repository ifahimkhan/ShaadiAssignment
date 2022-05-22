package com.fahim.shaadi.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.databinding.CardItemBinding
import javax.inject.Inject

class CardStackRecyclerAdapter @Inject constructor(
    val glide: RequestManager, private var profile: List<ProfileModel> = emptyList()
) : RecyclerView.Adapter<CardStackRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardStackRecyclerAdapter.ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = this.profile.get(position)
        holder.itemBinding.apply {
            glide.load(profile.picture).into(itemImage)
            itemName.text = "${profile.name} | ${profile.age} yrs"
            itemCity.text = "${profile.gender} | ${profile.city}"
        }

    }

    override fun getItemCount(): Int {
        return profile.size
    }

    fun setSpots(spots: List<ProfileModel>) {
        this.profile = spots
    }

    fun getSpots(): List<ProfileModel> {
        return profile
    }

    class ViewHolder(public val itemBinding: CardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}
