package com.ilham.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUserViewModel: ViewModel() {
    private var detailUser = MutableLiveData<DetailUser>()

    fun setUser(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${username}"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val username = responseObject.getString("login")
                    val name = responseObject.getString("name")
                    val avatar = responseObject.getString("avatar_url")
                    val follower = responseObject.getInt("followers")
                    val following = responseObject.getInt("following")
                    val followersUrl = responseObject.getString("followers_url")
                    val followingUrl = responseObject.getString("following_url")
                    val detail = DetailUser()
                    detail.name = name
                    detail.login = username
                    detail.avatar_url = avatar
                    detail.followers = follower
                    detail.following = following
                    detail.followers_url = followersUrl
                    detail.following_url = followingUrl
                    detailUser.postValue(detail)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUser() : LiveData<DetailUser> {
        return detailUser
    }
}