package com.ezstudio.framework.servicemanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by enzowei on 10/29/2020.
 */
public interface IActionProvider {
    @Nullable String invoke(@NonNull String method, @Nullable String extras);
}
