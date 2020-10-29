package com.ezstudio.module_a

import android.os.Process
import android.util.Log
import com.ezstudio.framework.IProfileService

/**
 * Created by enzowei on 06/17/2019.
 */
class ProfileService : IProfileService {

    override fun getName(): String {
        Log.d("ProfileService", "getName at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
        return "Enzo Wei"
    }

    override fun getPhone(): String {
        Log.d("ProfileService", "getPhone at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
        return "010-123456"
    }
}