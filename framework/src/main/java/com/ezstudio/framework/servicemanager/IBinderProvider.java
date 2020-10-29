package com.ezstudio.framework.servicemanager;

import android.support.annotation.NonNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * Created by enzowei on 10/28/2020.
 */
public interface IBinderProvider {
    void attach(@NonNull IActionProvider actionProvider);
}
