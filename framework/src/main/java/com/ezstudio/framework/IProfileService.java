package com.ezstudio.framework;

import com.ezstudio.framework.servicemanager.Process;
import com.ezstudio.framework.servicemanager.SupportMultiProcess;

/**
 * Created by enzowei on 10/28/2020.
 */
@SupportMultiProcess
public interface IProfileService extends IService {

    @Process(name = "other")
    String getName();

    String getPhone();

}
