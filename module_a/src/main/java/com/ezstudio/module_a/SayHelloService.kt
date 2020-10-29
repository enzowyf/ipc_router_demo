package com.ezstudio.module_a


import android.os.IBinder
import android.os.IInterface
import android.os.Process
import android.util.Log
import com.ezstudio.framework.IProfileInterface
import com.ezstudio.framework.ISayHelloInterface
import com.ezstudio.framework.ISayHelloService

/**
 * Created by enzowei on 06/14/2019.
 */
class SayHelloService : ISayHelloService, ISayHelloInterface.Stub() {
    override fun hello(name: String) {
        Log.d("SayHelloService", "hello at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        println("Hello $name, I am module a.")
    }
    override fun helloWorld(name: String): String {
        Log.d("SayHelloService", "helloWorld at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        return "Hello world, I am $name."
    }

    override fun getInterface(binder: IBinder?): IInterface {
        return asInterface(binder)
    }

    override fun getBinder(): IBinder {
        Log.d("SayHelloService", "getBinder: at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        return this
    }
}