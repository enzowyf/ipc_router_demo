package com.ezstudio.framework.servicemanager;

import android.support.annotation.NonNull;

/**
 * Created by enzowei on 10/28/2020.
 */
public interface IBinderProvider {
    void attach(@NonNull IBinderPool pool);
}
