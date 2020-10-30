package com.ezstudio.framework.servicemanager

import android.database.AbstractCursor
import android.database.Cursor
import android.util.Log

/**
 * Created by enzowei on 10/30/2020.
 */
class IPCCursor(private var stringResult: String? = null) : AbstractCursor() {

    override fun getLong(column: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getColumnIndex(columnName: String?): Int {
        return 1
    }

    override fun getColumnNames(): Array<String> {
        return arrayOf("1")
    }

    override fun getShort(column: Int): Short {
        return 0
    }

    override fun getFloat(column: Int): Float {
        return 0f
    }

    override fun getDouble(column: Int): Double {
        return 0.0
    }

    override fun isNull(column: Int): Boolean {
        return column >= count
    }

    override fun getInt(column: Int): Int {
        return 0
    }

    override fun onMove(oldPosition: Int, newPosition: Int): Boolean {
        return true
    }

    override fun getString(column: Int): String? {
        Log.d("---wyf---", "IPCCursor[$this].getString $column $stringResult")
        return stringResult
    }

}