package com.example.handyhubke.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.data.model.Review

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "HandyHub.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_JOB_REQUESTS = "JobRequests"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SERVICE_TYPE = "serviceType"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_BUDGET = "budget"
        private const val COLUMN_STATUS = "status"

        private const val TABLE_REVIEWS = "Reviews"
        private const val COLUMN_REVIEW_ID = "review_id"
        private const val COLUMN_WORKER_NAME = "worker_name"
        private const val COLUMN_RATING = "rating"
        private const val COLUMN_COMMENT = "comment"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createJobTable = ("CREATE TABLE $TABLE_JOB_REQUESTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SERVICE_TYPE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_BUDGET REAL, " +
                "$COLUMN_STATUS TEXT)")
        db.execSQL(createJobTable)

        val createReviewTable = ("CREATE TABLE $TABLE_REVIEWS (" +
                "$COLUMN_REVIEW_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_WORKER_NAME TEXT, " +
                "$COLUMN_RATING REAL, " +
                "$COLUMN_COMMENT TEXT, " +
                "$COLUMN_DATE TEXT)")
        db.execSQL(createReviewTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            val createReviewTable = ("CREATE TABLE $TABLE_REVIEWS (" +
                    "$COLUMN_REVIEW_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_WORKER_NAME TEXT, " +
                    "$COLUMN_RATING REAL, " +
                    "$COLUMN_COMMENT TEXT, " +
                    "$COLUMN_DATE TEXT)")
            db.execSQL(createReviewTable)
        }
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

    // SEARCH: Find job requests by service type or description
    fun searchJobRequests(query: String): List<JobRequest> {
        val jobList = mutableListOf<JobRequest>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_JOB_REQUESTS WHERE $COLUMN_SERVICE_TYPE LIKE ? OR $COLUMN_DESCRIPTION LIKE ?",
            arrayOf("%$query%", "%$query%")
        )

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

    // --- REVIEW CRUD OPERATIONS ---

    fun insertReview(review: Review): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_WORKER_NAME, review.workerName)
            put(COLUMN_RATING, review.rating)
            put(COLUMN_COMMENT, review.comment)
            put(COLUMN_DATE, review.date)
        }
        val result = db.insert(TABLE_REVIEWS, null, values)
        db.close()
        return result
    }

    fun getAllReviews(): List<Review> {
        val reviewList = mutableListOf<Review>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_REVIEWS", null)

        if (cursor.moveToFirst()) {
            do {
                val review = Review(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID)),
                    workerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKER_NAME)),
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                )
                reviewList.add(review)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reviewList
    }

    fun updateReview(review: Review): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_WORKER_NAME, review.workerName)
            put(COLUMN_RATING, review.rating)
            put(COLUMN_COMMENT, review.comment)
            put(COLUMN_DATE, review.date)
        }
        val result = db.update(TABLE_REVIEWS, values, "$COLUMN_REVIEW_ID = ?", arrayOf(review.id.toString()))
        db.close()
        return result
    }

    fun deleteReview(reviewId: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_REVIEWS, "$COLUMN_REVIEW_ID = ?", arrayOf(reviewId.toString()))
        db.close()
        return result
    }

    fun searchReviews(query: String): List<Review> {
        val reviewList = mutableListOf<Review>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_REVIEWS WHERE $COLUMN_WORKER_NAME LIKE ? OR $COLUMN_COMMENT LIKE ?",
            arrayOf("%$query%", "%$query%")
        )

        if (cursor.moveToFirst()) {
            do {
                val review = Review(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID)),
                    workerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKER_NAME)),
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                )
                reviewList.add(review)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reviewList
    }
}
