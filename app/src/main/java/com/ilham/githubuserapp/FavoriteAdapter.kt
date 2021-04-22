package com.ilham.githubuserapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilham.githubuserapp.databinding.ItemCardUserBinding
import com.ilham.githubuserapp.entity.FavoriteUser

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var listFavorite = ArrayList<FavoriteUser>()

    fun set(items: ArrayList<FavoriteUser>) {
        listFavorite.clear()
        listFavorite.addAll(items)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardUserBinding.bind(itemView)
        fun bind(favorite: FavoriteUser) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(favorite.avatar_url)
                    . apply(RequestOptions().override(55, 55))
                    .into(binding.avatarUser)
                binding.tvUsername.text = favorite.login
                binding.tvHtml.text = favorite.html_url
                binding.cvItemUser.setOnClickListener(CustomOnItemClickListener(favorite, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, favorite: FavoriteUser) {
                        val intent = Intent(activity, DetailUserActivity::class.java)
                        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favorite.login)
                        intent.putExtra(DetailUserActivity.EXTRA_HTML,favorite.html_url)
                        intent.putExtra(DetailUserActivity.EXTRA_ID, favorite.id)
                        intent.putExtra(DetailUserActivity.EXTRA_AVATAR, favorite.avatar_url)
                        activity.startActivity(intent)
                    }
                }))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return this.listFavorite.size
    }
}