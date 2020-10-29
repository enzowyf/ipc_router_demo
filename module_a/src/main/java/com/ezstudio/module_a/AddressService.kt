package com.ezstudio.module_a

import android.os.IBinder
import android.os.IInterface
import android.os.Process
import android.util.Log
import com.ezstudio.framework.IAddressService

/**
 * Created by enzowei on 10/29/2020.
 */
class AddressService : IAddressService {
    override fun getAddress(): String {
        Log.d("AddressService", "getAddress at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        return "shenzhen"
    }

    override fun getInterface(binder: IBinder?): IInterface? {
        return null
    }

    override fun getBinder(): IBinder? {
        return null
    }
}