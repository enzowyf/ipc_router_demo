package com.ezstudio.framework;

import android.os.IBinder;
import android.os.IInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by enzowei on 10/28/2020.
 */
public interface IService {

    @Nullable
    IBinder getBinder();

    @Nullable
    IInterface getInterface(IBinder binder);
}
