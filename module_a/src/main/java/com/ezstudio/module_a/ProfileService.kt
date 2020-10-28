package com.ezstudio.module_a

import android.os.IBinder
import android.os.IInterface
import android.os.Process
import android.util.Log
import com.ezstudio.framework.IProfileInterface
import com.ezstudio.framework.IProfileService
import com.ezstudio.framework.ISayHelloInterface

/**
 * Created by enzowei on 06/17/2019.
 */
class ProfileService : IProfileService, IProfileInterface.Stub() {

    override fun getName(): String {
        Log.d("ProfileService", "getName at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
        return "Enzo Wei"
    }

    override fun getPhone(): String {
        Log.d("ProfileService", "getPhone at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
        return "010-123456"
    }

    override fun getBinder(): IBinder {
        Log.d("ProfileService", "getBinder: at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        return this
    }

    override fun getInterface(binder: IBinder?): IInterface {
        return asInterface(binder)
    }
}