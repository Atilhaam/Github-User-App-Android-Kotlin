package com.ilham.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite"
        adapter = FavoriteAdapter(this)
        adapter.notifyDataSetChanged()


        binding.rvUser.adapter = adapter
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUser.adapter = adapter
        var list = getFavoriteUser()
        adapter.set(list)
    }

    private fun getFavoriteUser(): ArrayList<FavoriteUser> {
        val cursor = contentResolver.query(
            DatabaseContract.UserFavoriteColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        return MappingHelper.mapCursorToArrayList(cursor)
    }



}




