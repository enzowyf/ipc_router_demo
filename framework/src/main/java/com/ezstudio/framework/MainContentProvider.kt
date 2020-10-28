package com.ezstudio.framework

import android.os.Bundle
import android.util.Log

/**
 * Created by enzowei on 10/27/2020.
 */
class MainContentProvider : AbstractBinderProvider() {

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        Log.d("---wyf---", "MyContentProvider.call:$method $arg $extras")

        val binder = getBinder(IProfileService::class.java)

        val bundle = Bundle()
        bundle.putBinder("binder", binder)

        return bundle
    }
}