package com.ezstudio.framework

import android.os.Bundle
import android.util.Log

/**
 * Created by enzowei on 10/27/2020.
 */
class OtherBinderProvider : AbstractBinderProvider() {

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        Log.d("---wyf---", "OtherContentProvider.call:$method $arg $extras")

        val binder = getBinder(ISayHelloService::class.java)

        return Bundle().apply {
            putBinder("binder", binder)
        }
    }
}