package com.ezstudio.framework;

import com.ezstudio.framework.servicemanager.Process;

/**
 * Created by enzowei on 10/28/2020.
 */
public interface ISayHelloService extends IService {
    @Process(name = "other")
    void hello();

    String helloWorld(String name);
}
