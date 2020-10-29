package com.ezstudio.framework

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.ezstudio.framework.servicemanager.IActionProvider
import com.ezstudio.framework.servicemanager.IBinderProvider
import com.ezstudio.framework.servicemanager.ServiceManager

/**
 * Created by enzowei on 10/27/2020.
 */
abstract class AbstractBinderProvider : ContentProvider(), IBinderProvider {

    private lateinit var actionProvider: IActionProvider

    override fun onCreate(): Boolean {
        Log.d("---wyf---", "BinderProvider[$this].onCreate")

        ServiceManager.attach(this)
        return true
    }

    override fun attach(actionProvider: IActionProvider) {
        this.actionProvider = actionProvider
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        Log.d("---wyf---", "BinderProvider[$this].call $method $arg $extras")
        return actionProvider.invoke(method, extras)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}