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

    private var sayHelloServer: ISayHelloService? = null
    private var profileServer: IProfileService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_b)
        sayHelloServer = ServiceManager.getService(ISayHelloService::class.java)
        profileServer = ServiceManager.getService(IProfileService::class.java)

        call_module_a.setOnClickListener {
            Log.d("---wyf---", "call hello at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
            sayHelloServer?.hello()

            Log.d("---wyf---", "call helloWorld at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
            sayHelloServer?.helloWorld("enzo")?.let {
                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
            }

            Log.d("---wyf---", "call getName at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
            profileServer?.name?.let {
                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
            }

            Log.d("---wyf---", "call getPhone at process:${Process.myPid()} at Thread:${Thread.currentThread()}")
            profileServer?.phone?.let {
                Toast.makeText(this@ModuleBActivity, it, Toast.LENGTH_LONG).show()
            }
        }

    }
}
