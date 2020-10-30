package com.ezstudio.module_b

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_module_b.*
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.ezstudio.framework.*
import com.ezstudio.framework.servicemanager.ServiceManager


class ModuleBActivity : AppCompatActivity() {

    private lateinit var sayHelloServer: ISayHelloService
    private lateinit var profileServer: IProfileService
    private lateinit var addressService: IAddressService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_b)
        sayHelloServer = ServiceManager.getService(ISayHelloService::class.java)
        profileServer = ServiceManager.getService(IProfileService::class.java)
        addressService = ServiceManager.getService(IAddressService::class.java)
        Log.d("---wyf---", "ModuleBActivity onCreate:${sayHelloServer.javaClass} ${profileServer.javaClass} ${addressService.javaClass}")


        call_module_a.setOnClickListener {
            Log.d("---wyf---", "[start] call hello at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
            sayHelloServer.hello("enzo")?.let {
                Log.d("---wyf---", "[end] call hello at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
            }

//            Log.d("---wyf---", "[start] call helloWorld at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//            sayHelloServer.helloWorld("enzo")?.let {
//                Log.d("---wyf---", "[end] call helloWorld at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
//            }
//
//            Log.d("---wyf---", "[start] call getName at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//            profileServer.name?.let {
//                Log.d("---wyf---", "[end] call getName at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//
//                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
//            }
//
//            Log.d("---wyf---", "[start] call getPhone at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//            profileServer.phone?.let {
//                Log.d("---wyf---", "[end] call getPhone at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//
//                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
//            }
//
//            Log.d("---wyf---", "[start] call getAddress at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//            addressService.address?.let {
//                Log.d("---wyf---", "[end] call getAddress at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
//
//                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
//            }
        }

    }
}
