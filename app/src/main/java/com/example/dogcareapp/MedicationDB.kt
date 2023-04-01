package com.example.dogcareapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MedicationDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var db: SQLiteDatabase? = null

    companion object{
        private const val DATABASE_NAME = "medication.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "medication"
        private const val ID = "id"
        private const val TASK = "task"
        private const val STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK TEXT, $STATUS INTEGER)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun openDatabase(){
        db = this.writableDatabase
    }

    fun getAllTasks(): ArrayList<Medication>{
        val taskList: ArrayList<Medication> = ArrayList()
        var cursor: Cursor? = null
        var query = "SELECT * FROM $TABLE_NAME"

        db = this.readableDatabase

        if(db != null){
            cursor = db!!.rawQuery(query, null)
            while(cursor.moveToNext()){
                val task = Medication(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))
                taskList.add(task)
            }
        }
        return taskList
    }

    fun addTask(task: Medication){
        openDatabase()
        val cv = ContentValues()
        cv.put(TASK, task.medicine)
        cv.put(STATUS, 0)
        db!!.insert(TABLE_NAME, null, cv)
    }

    fun updateStatus(id: Int, status: Int){
        openDatabase()
        val cv = ContentValues()
        cv.put(STATUS, status)
        db!!.update(TABLE_NAME, cv, "id=?", arrayOf(id.toString()))
    }

    fun updateTask(id: Int, task: String){
        openDatabase()
        val cv = ContentValues()
        cv.put(TASK, task)
        db!!.update(TABLE_NAME, cv, "id=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int){
        openDatabase()
        db!!.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
    }
}