package com.ilham.consumerapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilham.consumerapp.databinding.ItemRowFavoriteBinding

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var listFavorite = ArrayList<FavoriteUser>()

    fun set(items: ArrayList<FavoriteUser>) {
        listFavorite.clear()
        listFavorite.addAll(items)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFavoriteBinding.bind(itemView)
        fun bind(favorite: FavoriteUser) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(favorite.avatar_url)
                    . apply(RequestOptions().override(55, 55))
                    .into(binding.avatarUser)
                binding.tvUsername.text = favorite.login
                binding.tvHtml.text = favorite.html_url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return this.listFavorite.size
    }
}