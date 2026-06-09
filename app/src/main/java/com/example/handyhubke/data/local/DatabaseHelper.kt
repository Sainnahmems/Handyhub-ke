package com.example.handyhubke.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.handyhubke.data.model.JobRequest

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "HandyHub.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_JOB_REQUESTS = "JobRequests"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SERVICE_TYPE = "serviceType"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_BUDGET = "budget"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_JOB_REQUESTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SERVICE_TYPE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_BUDGET REAL, " +
                "$COLUMN_STATUS TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_JOB_REQUESTS")
        onCreate(db)
    }

    // CREATE: Insert a new job request
    fun insertJobRequest(job: JobRequest): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SERVICE_TYPE, job.serviceType)
            put(COLUMN_DESCRIPTION, job.description)
            put(COLUMN_BUDGET, job.budget)
            put(COLUMN_STATUS, job.status)
        }
        val result = db.insert(TABLE_JOB_REQUESTS, null, values)
        db.close()
        return result
    }

    // READ: Fetch all saved job requests
    fun getAllJobRequests(): List<JobRequest> {
        val jobList = mutableListOf<JobRequest>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_JOB_REQUESTS", null)

        if (cursor.moveToFirst()) {
            do {
                val job = JobRequest(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    serviceType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_TYPE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    budget = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET)),
                    status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                )
                jobList.add(job)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return jobList
    }

    // UPDATE: Modify job details or status
    fun updateJobRequest(job: JobRequest): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SERVICE_TYPE, job.serviceType)
            put(COLUMN_DESCRIPTION, job.description)
            put(COLUMN_BUDGET, job.budget)
            put(COLUMN_STATUS, job.status)
        }
        val result = db.update(TABLE_JOB_REQUESTS, values, "$COLUMN_ID = ?", arrayOf(job.id.toString()))
        db.close()
        return result
    }

    // DELETE: Remove a job request
    fun deleteJobRequest(jobId: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_JOB_REQUESTS, "$COLUMN_ID = ?", arrayOf(jobId.toString()))
        db.close()
        return result
    }
}
