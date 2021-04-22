package com.ilham.githubuserapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns.Companion.AVATAR_URL
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns.Companion.HTML_URL
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns.Companion.LOGIN
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns.Companion._ID
import com.ilham.githubuserapp.entity.FavoriteUser
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context) : FavoriteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }
    fun getAllFavorite() : ArrayList<FavoriteUser> {
        val arrayList = ArrayList<FavoriteUser>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
                "$_ID ASC", null)
        cursor.moveToFirst()
        var favoriteUser: FavoriteUser
        if (cursor.count > 0) {
            do {
                favoriteUser = FavoriteUser()
                favoriteUser.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                favoriteUser.login = cursor.getString(cursor.getColumnIndexOrThrow(LOGIN))
                favoriteUser.html_url = cursor.getString(cursor.getColumnIndexOrThrow(HTML_URL))
                favoriteUser.avatar_url = cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_URL))

                arrayList.add(favoriteUser)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }
    fun queryByLogin(login: String) : Cursor {
        return database.query(DATABASE_TABLE, null, "$LOGIN = ?", arrayOf(login), null, null, null, null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }
    fun deleteById(id: Int): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

}