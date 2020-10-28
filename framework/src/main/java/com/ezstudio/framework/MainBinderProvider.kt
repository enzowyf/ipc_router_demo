package com.ezstudio.framework

import android.os.Bundle
import android.util.Log

/**
 * Created by enzowei on 10/27/2020.
 */
class MainBinderProvider : AbstractBinderProvider() {

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {

        val binder = getBinder(IProfileService::class.java)

        return Bundle().apply {
            putBinder("binder", binder)
        }
    }
}