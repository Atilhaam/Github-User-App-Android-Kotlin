package com.ilham.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${username}/following"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)
                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val id = item.getInt("id")
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val html = item.getString("html_url")
                        val user = User()
                        user.id = id
                        user.login = username
                        user.avatar_url = avatar
                        user.html_url = html
                        listItems.add(user)
                    }
                    listFollowing.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}