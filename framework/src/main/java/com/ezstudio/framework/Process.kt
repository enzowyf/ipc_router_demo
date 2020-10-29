package com.ezstudio.framework

import android.support.annotation.Keep

/**
 * Created by enzowei on 08/08/2019.
 */
@Keep
class Process {

    private val MAIN_PROCESS = ""
    private val OTHER_PROCESS = ":other"


    @Volatile
    private var processName = ""

    private fun init() {
        processName = ProcessUtils.myProcessName(GlobalContext.app)
    }

    private fun checkProcess(name: String): Boolean {
        if (processName.isEmpty()) {
            init()
        }
        return when (name) {
            MAIN_PROCESS -> processName == GlobalContext.app.packageName
            else -> processName.contains(name)
        }
    }

    fun getProcessName(): String {
        if (processName.isEmpty()) {
            init()
        }

        if (processName == GlobalContext.app.packageName) {
            return "main"
        } else {
            return processName.subSequence(processName.indexOf(":") + 1, processName.length).toString()
        }
    }

    fun isMainProcess(): Boolean {
        return checkProcess(MAIN_PROCESS)
    }

    fun isOhterProcess(): Boolean {
        return checkProcess(OTHER_PROCESS)
    }
}