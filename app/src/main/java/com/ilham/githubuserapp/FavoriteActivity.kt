package com.ilham.githubuserapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.githubuserapp.databinding.ActivityFavoriteBinding
import com.ilham.githubuserapp.db.FavoriteHelper
import com.ilham.githubuserapp.entity.FavoriteUser

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteHelper: FavoriteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = FavoriteAdapter(this)
        adapter.notifyDataSetChanged()

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        binding.rvUser.adapter = adapter
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvUser.adapter = adapter
        val list = getFavoriteUser()
        adapter.set(list)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun getFavoriteUser(): ArrayList<FavoriteUser> {
        return favoriteHelper.getAllFavorite()
    }

}