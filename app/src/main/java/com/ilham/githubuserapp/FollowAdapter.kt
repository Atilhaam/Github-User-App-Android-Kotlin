package com.ilham.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilham.githubuserapp.databinding.ItemCardUserBinding

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    var listFollow = ArrayList<User>()
    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClikcCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun set(items: ArrayList<User>) {
        listFollow.clear()
        listFollow.addAll(items)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardUserBinding.bind(itemView)
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                        .load(user.avatar_url)
                        . apply(RequestOptions().override(55, 55))
                        .into(binding.avatarUser)
                binding.tvUsername.text = user.login
                binding.tvHtml.text = user.html_url
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_user, parent, false)
        return FollowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(listFollow[position])
    }

    override fun getItemCount(): Int {
        return this.listFollow.size
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }
}