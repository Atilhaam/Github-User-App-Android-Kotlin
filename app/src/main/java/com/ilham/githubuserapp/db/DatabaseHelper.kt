package com.ilham.githubuserapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns
import com.ilham.githubuserapp.db.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "userfavorite"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${UserFavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${UserFavoriteColumns.LOGIN} TEXT NOT NULL," +
                " ${UserFavoriteColumns.HTML_URL} TEXT NOT NULL," +
                " ${UserFavoriteColumns.AVATAR_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}