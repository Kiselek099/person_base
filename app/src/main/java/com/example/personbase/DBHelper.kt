package com.example.personbase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.ViewModelProvider

class DBHelper(context: Context,factory: CursorFactory?) :
SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION){
    companion object {
        private val DATABASE_NAME="PERSON_DATABASE"
        private val DATABASE_VERSION=2
        val TABLE_NAME="person_table"
        val KEY_ID="id"
        val KEY_NAME="name"
        val KEY_POS="position"
        val KEY_PHONE="phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_POS + " TEXT, " +
                KEY_PHONE + " TEXT" + ")")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addName(name:String,position:String,phone:String){
        val values=ContentValues()
        values.put(KEY_NAME,name)
        values.put(KEY_POS,position)
        values.put(KEY_PHONE,phone)
        val db=this.writableDatabase
        db.insert(TABLE_NAME,null,values)
    }
    fun getInfo():Cursor?{
        val db=this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME",null)
    }
    fun removeAll(){
        val db=this.writableDatabase
        db.delete(TABLE_NAME,null,null)
    }
}