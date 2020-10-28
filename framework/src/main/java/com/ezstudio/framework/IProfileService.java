package com.ezstudio.framework;

import com.ezstudio.framework.servicemanager.Process;

/**
 * Created by enzowei on 10/28/2020.
 */
public interface IProfileService extends IService {

    @Process(name = "main")
    String getName();

    String getPhone();

}
