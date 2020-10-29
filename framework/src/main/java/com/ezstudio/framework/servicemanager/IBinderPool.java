package com.ezstudio.framework.servicemanager;

import android.os.IBinder;
import android.support.annotation.NonNull;

import com.ezstudio.framework.IService;

import org.jetbrains.annotations.NotNull;


/**
 * Created by enzowei on 10/28/2020.
 */
public interface IBinderPool  {
    @NonNull
    IBinder getBinder(@NonNull String serviceInterfaceName);
}
