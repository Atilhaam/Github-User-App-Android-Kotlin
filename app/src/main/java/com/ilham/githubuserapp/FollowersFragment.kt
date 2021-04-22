package com.ilham.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.githubuserapp.databinding.FragmentFollowBinding

class FollowersFragment : Fragment(R.layout.fragment_follow){

    private var bindings : FragmentFollowBinding? = null
    private val binding get() = bindings!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var viewModel2 : DetailUserViewModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        bindings = FragmentFollowBinding.bind(view)

        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }
        adapter.setOnItemClikcCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(context, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_HTML, data.html_url)
                    startActivity(it)
                }
            }
        })
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel2 = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewModel2.setUser(username)
        viewModel2.getUser().observe( viewLifecycleOwner, { userItems ->
            if (userItems.followers == 0) {
                showNotAvailable(false)
            } else {
                showNotAvailable(true)
            }
        })

        viewModel.setListFollower(username)
        viewModel.getListFollowers().observe( viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.set(userItems)
                showLoading(false )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindings = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun showNotAvailable(state: Boolean) {
        if (state) {
            binding.tvNotavail.visibility = View.GONE
        } else {
            binding.tvNotavail.visibility = View.VISIBLE
        }
    }

}