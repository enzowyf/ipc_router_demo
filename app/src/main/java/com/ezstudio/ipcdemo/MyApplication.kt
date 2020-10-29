package com.ezstudio.ipcdemo

import android.app.Application
import android.os.Process
import android.util.Log
import com.ezstudio.framework.*
import com.ezstudio.framework.servicemanager.ServiceManager
import com.ezstudio.module_a.AddressService
import com.ezstudio.module_a.ProfileService
import com.ezstudio.module_a.SayHelloService

/**
 * Created by enzowei on 06/14/2019.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "onCreate at process:${Process.myPid()}")
        ServiceManager.registerProcess("main")
        ServiceManager.registerProcess("other")
        ServiceManager.registerService(ISayHelloService::class.java, SayHelloService())
        ServiceManager.registerService(IProfileService::class.java, ProfileService())
        ServiceManager.registerService(IAddressService::class.java, AddressService())

        GlobalContext.app = this
    }
}