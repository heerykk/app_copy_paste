package android.support.p000v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.util.SparseArray;

/* renamed from: android.support.v4.content.WakefulBroadcastReceiver */
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
    private static final SparseArray<WakeLock> mActiveWakeLocks = new SparseArray<>();
    private static int mNextId = 1;

    public WakefulBroadcastReceiver() {
    }

    public static ComponentName startWakefulService(Context context, Intent intent) {
        Context context2 = context;
        Intent intent2 = intent;
        Context context3 = context2;
        Intent intent3 = intent2;
        SparseArray<WakeLock> sparseArray = mActiveWakeLocks;
        ComponentName componentName = sparseArray;
        synchronized (sparseArray) {
            try {
                int id = mNextId;
                mNextId++;
                if (mNextId <= 0) {
                    mNextId = 1;
                }
                Intent putExtra = intent2.putExtra(EXTRA_WAKE_LOCK_ID, id);
                ComponentName startService = context2.startService(intent2);
                ComponentName comp = startService;
                if (startService != null) {
                    PowerManager powerManager = (PowerManager) context2.getSystemService("power");
                    PowerManager powerManager2 = powerManager;
                    WakeLock newWakeLock = powerManager.newWakeLock(1, "wake:" + comp.flattenToShortString());
                    WakeLock wl = newWakeLock;
                    newWakeLock.setReferenceCounted(false);
                    wl.acquire(60000);
                    mActiveWakeLocks.put(id, wl);
                    return comp;
                }
                return null;
            } finally {
                ComponentName componentName2 = componentName;
            }
        }
    }

    public static boolean completeWakefulIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        int intExtra = intent2.getIntExtra(EXTRA_WAKE_LOCK_ID, 0);
        int id = intExtra;
        if (intExtra == 0) {
            return false;
        }
        SparseArray<WakeLock> sparseArray = mActiveWakeLocks;
        SparseArray<WakeLock> sparseArray2 = sparseArray;
        synchronized (sparseArray) {
            try {
                WakeLock wakeLock = (WakeLock) mActiveWakeLocks.get(id);
                WakeLock wl = wakeLock;
                if (wakeLock == null) {
                    int w = Log.w("WakefulBroadcastReceiver", "No active wake lock id #" + id);
                    return true;
                }
                wl.release();
                mActiveWakeLocks.remove(id);
                return true;
            } finally {
                SparseArray<WakeLock> sparseArray3 = sparseArray2;
            }
        }
    }
}
