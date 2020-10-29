package com.ezstudio.framework.servicemanager

import android.net.Uri
import android.os.IBinder
import android.os.IInterface
import android.util.ArrayMap
import android.util.Log
import com.ezstudio.framework.GlobalContext
import com.ezstudio.framework.IService
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by enzowei on 10/28/2020.
 */
object ServiceManager {
    private val serviceMap = ArrayMap<String, ServiceModel>()

    private val processList = mutableListOf<String>()

    private val proxyMap = mutableMapOf<Class<out IService>, Any>()

    fun attach(binderProvider: IBinderProvider) {
        binderProvider.attach { classname ->
            Log.d("ServiceManager", "binderPool getBinder:$classname $serviceMap")

            serviceMap[classname]!!.service.binder!!
        }
    }

    fun registerProcess(process: String) {
        processList.add(process)
    }

    fun <T : IService> registerService(clazz: Class<T>, serviceImplement: T) {
        Log.d("ServiceManager", "registerService invoke, $clazz $serviceImplement ${clazz.annotations.any { it is SupportMultiProcess }}")

        serviceMap[clazz.name] =
            ServiceModel(serviceImplement, clazz.annotations.any { it is SupportMultiProcess })
    }

    fun <T : IService> getService(serviceClass: Class<T>): T {
        val serviceModel = serviceMap[serviceClass.name]!!
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
                if (processList.any { processName -> processName == binderAnnotation.name }) {
                    val binderProxy = proxyMap[clazz] ?: getProxy(
                        binderAnnotation.name,
                        clazz,
                        service
                    ).also { proxyMap[clazz] = it }

                    Log.d("ServiceManager", "binderProxy:$binderProxy")
                    val binderMethod = binderProxy!!.javaClass.getMethod(method.name, *method.parameterTypes)
                    invokeMethod(
                        binderProxy!!,
                        binderMethod,
                        args
                    )
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

    private fun getProxy(process: String, serviceClazz: Class<out IService>, service: IService): IInterface {
        val bundle = GlobalContext.app.contentResolver.call(
            Uri.parse("content://process_dispatcher_${process}"),
            "getBinder",
            serviceClazz.name,
            null
        )

        return service.getInterface(bundle?.getBinder("binder"))!!
    }

    private fun invokeMethod(obj: Any, method: Method, args: Array<Any>?): Any? {
        return if (args.isNullOrEmpty()) {
            method.invoke(obj)
        } else {
            method.invoke(obj, *args)
        }
    }
}