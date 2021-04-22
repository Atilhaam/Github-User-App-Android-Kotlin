package com.ilham.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilham.githubuserapp.databinding.ItemRowUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val mData = ArrayList<User>()
    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClikcCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind((itemView))
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                        . load(user.avatar_url)
                        . apply(RequestOptions().override(55, 55))
                        . into(binding.avatarUser)
                binding.tvUsername.text = user.login
                binding.tvHtml.text = user.html_url
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(userViewholder: UserViewHolder, position: Int) {
        userViewholder.bind(mData[position])

    }

    override fun getItemCount(): Int {
        return mData.size
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }
}