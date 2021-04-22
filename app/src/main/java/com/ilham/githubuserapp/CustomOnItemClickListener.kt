package com.ilham.githubuserapp

import android.view.View
import com.ilham.githubuserapp.entity.FavoriteUser

class CustomOnItemClickListener(private val data: FavoriteUser, private val onItemClickCallback: OnItemClickCallback) : View.OnClickListener {
    override fun onClick(view: View) {
        onItemClickCallback.onItemClicked(view, data)
    }
    interface OnItemClickCallback {
        fun onItemClicked(view: View, data: FavoriteUser)
    }
}