package com.ilham.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(favoriteCursor: Cursor?) : ArrayList<FavoriteUser> {
        val favoriteList = ArrayList<FavoriteUser>()

        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.LOGIN))
                val html = getString(getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.HTML_URL))
                val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.UserFavoriteColumns.AVATAR_URL))
                favoriteList.add(FavoriteUser(id,username,html,avatarUrl))
            }
        }
        return favoriteList
    }
}