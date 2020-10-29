package com.ezstudio.framework.servicemanager

import android.net.Uri
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import com.ezstudio.framework.GlobalContext
import com.ezstudio.framework.IProfileService
import com.ezstudio.framework.ISayHelloService
import com.ezstudio.framework.IService
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by enzowei on 10/28/2020.
 */
object ServiceManager {
    private val serviceMap = ArrayMap<Class<out IService>, ServiceModel>()

    private val ipcMethodMap = mutableMapOf<String, IPCAction>()

    init {
        ipcMethodMap["ISayHelloService.hello"] = IPCAction {
            Log.d("ServiceManager", "invokeIPCMethod, ISayHelloService.hello $it")

            val arg = it.getString("arg")
            (serviceMap[ISayHelloService::class.java]!!.service as ISayHelloService).hello(arg)

            null
        }
        ipcMethodMap["IProfileService.getName"] = IPCAction {
            Log.d("ServiceManager", "invokeIPCMethod, IProfileService.getName $it ")

            val result = (serviceMap[IProfileService::class.java]!!.service as IProfileService).name

            Log.d("ServiceManager", "invokeIPCMethod, IProfileService.getName $it $result")

            Bundle().apply {
                putString("result", result)
            }
        }
    }

    fun attach(binderProvider: IBinderProvider) {
        binderProvider.attach { method, extras ->
            Log.d("ServiceManager", "ActionProvider invoke, $method $extras")

            ipcMethodMap[method]?.invoke(extras)
        }
    }

    fun <T : IService> registerService(clazz: Class<T>, serviceImplement: T) {
        Log.d("ServiceManager", "registerService invoke, $clazz $serviceImplement ${clazz.annotations.any { it is SupportMultiProcess }}")

        serviceMap[clazz] =
            ServiceModel(serviceImplement, clazz.annotations.any { it is SupportMultiProcess })
    }

    fun <T : IService> getService(serviceClass: Class<T>): T {
        val serviceModel = serviceMap[serviceClass]!!
        return if (serviceModel.isSupportMultiProcess) {
            generateProxy(
                serviceClass,
                serviceModel.service as T
            )
        } else {
            serviceModel.service as T
        }

    }

    private fun <T : IService> generateProxy(clazz: Class<T>, service: T): T {
        return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz)) { proxy, method, args ->
            val binderAnnotation = method.getAnnotation(Process::class.java)
            if (binderAnnotation != null) {
                if (GlobalContext.process.getProcessName() != binderAnnotation.name) {
                    invokeIPCMethod(clazz, method, args, binderAnnotation.name)
                } else {
                    invokeMethod(
                        service,
                        method,
                        args
                    )
                }
            } else {
                invokeMethod(
                    service,
                    method,
                    args
                )
            }
        } as T
    }

    private fun invokeIPCMethod(
        clazz: Class<out IService>,
        method: Method,
        args: Array<Any>?,
        process: String
    ): Any? {
        Log.d("ServiceManager", "invokeIPCMethod")
        val argBundle = Bundle()
        if (args != null) {
            argBundle.putString("args", args[0] as? String)
        }

        val resultBundle = GlobalContext.app.contentResolver.call(
            Uri.parse("content://process_dispatcher_${process}"),
            "${clazz.simpleName}.${method.name}",
            null,
            null
        )

        return resultBundle.getString("result")
    }


    private fun invokeMethod(obj: Any, method: Method, args: Array<Any>?): Any? {
        return if (args.isNullOrEmpty()) {
            method.invoke(obj)
        } else {
            method.invoke(obj, *args)
        }
    }
}