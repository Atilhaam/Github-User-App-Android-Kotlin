package com.ilham.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.ilham.githubuserapp"
    const val SCHEME = "content"

    internal class UserFavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "userfav"
            const val _ID =  "id"
            const val LOGIN = "login"
            const val HTML_URL = "html_url"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}