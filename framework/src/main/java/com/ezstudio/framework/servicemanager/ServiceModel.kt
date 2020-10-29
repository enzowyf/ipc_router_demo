package com.ezstudio.framework.servicemanager

import com.ezstudio.framework.IService

/**
 * Created by enzowei on 10/29/2020.
 */
data class ServiceModel(val service: IService, val isSupportMultiProcess: Boolean = false)