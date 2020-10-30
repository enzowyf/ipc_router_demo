package com.ezstudio.module_a


import android.os.Process
import android.util.Log
import com.ezstudio.framework.ISayHelloService

/**
 * Created by enzowei on 06/14/2019.
 */
class SayHelloService : ISayHelloService {
    override fun hello(name: String): String {
        Log.d("SayHelloService", "hello at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        Log.d("SayHelloService", "Hello $name, I am module a.")
        return "Hello $name, I am module a."
    }
    override fun helloWorld(name: String): String {
        Log.d("SayHelloService", "helloWorld at process:${Process.myPid()} at Thread:${Thread.currentThread()}")

        return "Hello world, I am $name."
    }
}