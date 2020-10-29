package com.ezstudio.framework;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Tencent. Author: raezlu Date: 13-10-24 Time: 上午11:48
 * <p>Utilities for process.</p>
 */
public final class ProcessUtils {

    private final static int[] EMPTY_INT_ARRAY = new int[0];
    private final static int EMPTY_ID = 0;

    private static volatile String sProcessName;
    private final static Object sNameLock = new Object();

    private static volatile Boolean sMainProcess;
    private final static Object sMainLock = new Object();

    private ProcessUtils() {
        // static usage.
    }

    /**
     * Returns the name of this process.
     *
     * @param context Application context.
     * @return The name of this process.
     */
    public static String myProcessName(Context context) {
        if (sProcessName != null) {
            return sProcessName;
        }
        synchronized (sNameLock) {
            if (sProcessName != null) {
                return sProcessName;
            }
            return sProcessName = obtainProcessName(context);
        }
    }

    /**
     * Check whether this process is the main process (it's name equals to {@link
     * Context#getPackageName()}).
     *
     * @param context Application context.
     * @return Whether this process is the main process.
     */
    public static boolean isMainProcess(Context context) {
        if (sMainProcess != null) {
            return sMainProcess;
        }
        synchronized (sMainLock) {
            if (sMainProcess != null) {
                return sMainProcess;
            }
            final String processName = myProcessName(context);
            if (processName == null) {
                return false;
            }
            sMainProcess = processName.equals(context.getApplicationInfo().processName);
            return sMainProcess;
        }
    }

    /**
     * Kill this process itself.
     */
    public void killSelf() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Kill all processes from this package.
     */
    public static void killAll(Context context) {
        killAll(context, EMPTY_INT_ARRAY);
    }

    /**
     * Kill all process from this package.
     *
     * @param excludePid exclude pid of processes.
     */
    public static void killAll(Context context, int... excludePid) {
        int myPid = android.os.Process.myPid();
        List<RunningAppProcessInfo> runningProcesses = collectSelfRunningProcessInfo(context);
        Set<Integer> excludePidSet = collectUniqueSet(excludePid);
        // kill running process exclude required exception and self.
        if (runningProcesses != null) {
            for (RunningAppProcessInfo process : runningProcesses) {
                if (excludePidSet != null && excludePidSet.contains(process.pid)) {
                    // exclude process.
                    continue;
                }
                if (myPid == process.pid) {
                    // my pid, ignore here.
                    continue;
                }
                android.os.Process.killProcess(process.pid);
            }
        }
        if (excludePidSet != null && excludePidSet.contains(myPid)) {
            // exclude self.
            return;
        }
        // kill self at last.
        android.os.Process.killProcess(myPid);
    }

    /**
     * Kill all process from this package.
     *
     * @param excludeName exclude name of processes.
     */
    public static void killAll(Context context, String... excludeName) {
        int myPid = android.os.Process.myPid();
        String myProcessName = null;
        List<RunningAppProcessInfo> runningProcesses = collectSelfRunningProcessInfo(context);
        Set<String> excludeNameSet = collectUniqueSet(excludeName);
        // kill running process exclude required exception and self.
        if (runningProcesses != null) {
            for (RunningAppProcessInfo process : runningProcesses) {
                if (excludeNameSet != null && excludeNameSet.contains(process.processName)) {
                    // exclude process.
                    continue;
                }
                if (myPid == process.pid) {
                    // my pid, ignore here.
                    myProcessName = process.processName;
                    continue;
                }
                android.os.Process.killProcess(process.pid);
            }
        }
        if (myProcessName != null && excludeNameSet != null && excludeNameSet
                .contains(myProcessName)) {
            // exclude self.
            return;
        }
        // kill self at last.
        android.os.Process.killProcess(myPid);
    }

    /**
     * Whether a process from this package is running foreground ui or service.
     *
     * @param context Application context.
     * @return true if foreground.
     */
    public static boolean isForeground(Context context) {
        // collecting running process info may cause binder overflow and should be avoided.
        // but after LOLLIPOP_MR1, only our own running processes will be returned.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                && isRunningTopTask(context)) {
            return true;
        }
        List<RunningAppProcessInfo> runningProcesses = collectSelfRunningProcessInfo(context);
        if (runningProcesses != null) {
            for (RunningAppProcessInfo process : runningProcesses) {
                if (process.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        || process.importance == RunningAppProcessInfo.IMPORTANCE_TOP_SLEEPING
                        || process.importance
                        == RunningAppProcessInfo.IMPORTANCE_FOREGROUND_SERVICE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Whether a process from this package is running foreground ui.
     *
     * @param context Application context.
     * @return true if foreground.
     */
    public static boolean isUIForeground(Context context) {
        // collecting running process info may cause binder overflow and should be avoided.
        // but after LOLLIPOP_MR1, only our own running processes will be returned.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return isRunningTopTask(context);
        } else {
            List<RunningAppProcessInfo> runningProcesses = collectSelfRunningProcessInfo(context);
            if (runningProcesses != null) {
                for (RunningAppProcessInfo process : runningProcesses) {
                    if (process.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            || process.importance
                            == RunningAppProcessInfo.IMPORTANCE_TOP_SLEEPING) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Whether a process from this package is running foreground service.
     *
     * @param context Application context.
     * @return true if foreground.
     */
    public static boolean isServiceForeground(Context context) {
        List<RunningAppProcessInfo> runningProcesses = collectSelfRunningProcessInfo(context);
        if (runningProcesses != null) {
            for (RunningAppProcessInfo process : runningProcesses) {
                if (process.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND_SERVICE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Collect running processes from this package itself.
     */
    private static List<RunningAppProcessInfo> collectSelfRunningProcessInfo(Context context) {
        return collectRunningProcessInfo(context, android.os.Process.myUid(), EMPTY_ID);
    }

    /**
     * Collect all running processes match the corresponding terms. Notice, after {@link
     * Build.VERSION_CODES#LOLLIPOP_MR1}, this will return your own process infos.
     */
    private static List<RunningAppProcessInfo> collectRunningProcessInfo(Context context, int uid,
            int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        // filter processes according to uid.
        if (runningProcesses != null
                // avoid unnecessary iteration.
                && (uid != EMPTY_ID || pid != EMPTY_ID)) {

            Iterator<RunningAppProcessInfo> iterator = runningProcesses.iterator();
            while (iterator.hasNext()) {
                RunningAppProcessInfo process = iterator.next();
                // operation order: complexity decrease to minimize cost.
                if (uid != EMPTY_ID && process.uid != uid) {
                    iterator.remove();

                } else if (pid != EMPTY_ID && process.pid != pid) {
                    iterator.remove();
                }
            }
        }
        return runningProcesses;
    }

    private static boolean isRunningTopTask(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            throw new RuntimeException("Call this before LOLLIPOP.");
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo top =
                (runningTasks != null && !runningTasks.isEmpty()) ? runningTasks.get(0) : null;
        return top != null && context.getPackageName().equals(top.baseActivity.getPackageName());
    }

    private static <V> Set<V> collectUniqueSet(V[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Set<V> set = new HashSet<V>(values.length);
        Collections.addAll(set, values);
        return set;
    }

    private static Set<Integer> collectUniqueSet(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Set<Integer> set = new HashSet<Integer>(values.length);
        for (int value : values) {
            set.add(value);
        }
        return set;
    }

    private static String obtainProcessName(Context context) {
        final int pid = android.os.Process.myPid();
        List<RunningAppProcessInfo> listTaskInfo = collectSelfRunningProcessInfo(context);
        if (listTaskInfo != null) {
            for (RunningAppProcessInfo proc : listTaskInfo) {
                if (proc != null && proc.pid == pid) {
                    return proc.processName;
                }
            }
        }
        return null;
    }
}
