package com.ilham.githubuserapp

import android.content.ContentValues
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilham.githubuserapp.databinding.ActivityDetailUserBinding
import com.ilham.githubuserapp.db.DatabaseContract
import com.ilham.githubuserapp.db.FavoriteHelper
import com.ilham.githubuserapp.entity.FavoriteUser

class DetailUserActivity : AppCompatActivity()  {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_HTML = "extra_html"
        const val EXTRA_AVATAR = "extra_avatar"

    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailUserViewModel
    private lateinit var favoriteHelper: FavoriteHelper
    private var statusFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User"

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        val username = intent.getStringExtra(EXTRA_USERNAME)!!
        val _id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)!!
        val html = intent.getStringExtra(EXTRA_HTML)!!

        val checked = checkFavorite(username)
        if (checked != null) {
            if (checked.count > 0) {
                statusFavorite = true
            } else {
                statusFavorite = false
            }
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        setStatusFavorite(statusFavorite)
        binding.floatButton.setOnClickListener {
            statusFavorite = !statusFavorite
            if (statusFavorite) {
                addToFavorite(_id,username, html, avatar)
            } else {
                removeFromFavorite(_id)
            }
            setStatusFavorite(statusFavorite)
        }

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        detailViewModel.setUser(username)
        detailViewModel.getUser().observe( this, { userItems ->
            if (userItems != null) {
                binding.apply {
                    if (userItems.name == "null") {
                        tvName.text = "Not Available"
                    } else {
                        tvName.text = userItems.name
                    }
                    tvUsername.text = userItems.login
                    tvFollowers.text = "${userItems.followers} Followers"
                    tvFollowing.text = "${userItems.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(userItems.avatar_url)
                        .apply(RequestOptions().override(55, 55))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(avatarProfile)
                }

            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setStatusFavorite(statusFavorite : Boolean) {
        if (statusFavorite) {
            binding.floatButton.setColorFilter(Color.RED)
        } else {
            binding.floatButton.setColorFilter(Color.WHITE)
        }
    }

    private fun addToFavorite(id: Int, username: String, html: String, avatar: String) {
        var user = FavoriteUser(
                id,
                username,
                html,
                avatar
        )
        val values = ContentValues()
        values.put(DatabaseContract.UserFavoriteColumns._ID, user.id)
        values.put(DatabaseContract.UserFavoriteColumns.LOGIN, user.login)
        values.put(DatabaseContract.UserFavoriteColumns.HTML_URL, user.html_url)
        values.put(DatabaseContract.UserFavoriteColumns.AVATAR_URL, user.avatar_url)
        favoriteHelper.insert(values)
    }
    private fun removeFromFavorite(id: Int) {
        favoriteHelper.deleteById(id)
    }
    private fun checkFavorite(username: String) : Cursor {
        return favoriteHelper.queryByLogin(username)
    }



}