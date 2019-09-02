package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

class SnackbarManager {
    private static final int LONG_DURATION_MS = 2750;
    static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static SnackbarManager sSnackbarManager;
    private SnackbarRecord mCurrentSnackbar;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new android.os.Handler.Callback(this) {
        final /* synthetic */ SnackbarManager this$0;

        {
            SnackbarManager this$02 = r5;
            SnackbarManager snackbarManager = this$02;
            this.this$0 = this$02;
        }

        public boolean handleMessage(Message message) {
            Message message2 = message;
            Message message3 = message2;
            switch (message2.what) {
                case 0:
                    this.this$0.handleTimeout((SnackbarRecord) message2.obj);
                    return true;
                default:
                    return false;
            }
        }
    });
    private final Object mLock = new Object();
    private SnackbarRecord mNextSnackbar;

    interface Callback {
        void dismiss(int i);

        void show();
    }

    private static class SnackbarRecord {
        final WeakReference<Callback> callback;
        int duration;

        SnackbarRecord(int i, Callback callback2) {
            int duration2 = i;
            Callback callback3 = callback2;
            int i2 = duration2;
            Callback callback4 = callback3;
            this.callback = new WeakReference<>(callback3);
            this.duration = duration2;
        }

        /* access modifiers changed from: 0000 */
        public boolean isSnackbar(Callback callback2) {
            Callback callback3 = callback2;
            Callback callback4 = callback3;
            return callback3 != null && this.callback.get() == callback3;
        }
    }

    static SnackbarManager getInstance() {
        if (sSnackbarManager == null) {
            sSnackbarManager = new SnackbarManager();
        }
        return sSnackbarManager;
    }

    private SnackbarManager() {
    }

    /* JADX INFO: finally extract failed */
    public void show(int i, Callback callback) {
        int duration = i;
        Callback callback2 = callback;
        int i2 = duration;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (!isCurrentSnackbarLocked(callback2)) {
                    if (!isNextSnackbarLocked(callback2)) {
                        this.mNextSnackbar = new SnackbarRecord(duration, callback2);
                    } else {
                        this.mNextSnackbar.duration = duration;
                    }
                    if (this.mCurrentSnackbar != null) {
                        if (cancelSnackbarLocked(this.mCurrentSnackbar, 4)) {
                            return;
                        }
                    }
                    this.mCurrentSnackbar = null;
                    showNextSnackbarLocked();
                    return;
                }
                this.mCurrentSnackbar.duration = duration;
                this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
                scheduleTimeoutLocked(this.mCurrentSnackbar);
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void dismiss(Callback callback, int i) {
        Callback callback2 = callback;
        int event = i;
        Callback callback3 = callback2;
        int i2 = event;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (isCurrentSnackbarLocked(callback2)) {
                    boolean cancelSnackbarLocked = cancelSnackbarLocked(this.mCurrentSnackbar, event);
                } else if (isNextSnackbarLocked(callback2)) {
                    boolean cancelSnackbarLocked2 = cancelSnackbarLocked(this.mNextSnackbar, event);
                }
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    public void onDismissed(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (isCurrentSnackbarLocked(callback2)) {
                    this.mCurrentSnackbar = null;
                    if (this.mNextSnackbar != null) {
                        showNextSnackbarLocked();
                    }
                }
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public void onShown(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (isCurrentSnackbarLocked(callback2)) {
                    scheduleTimeoutLocked(this.mCurrentSnackbar);
                }
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public void cancelTimeout(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (isCurrentSnackbarLocked(callback2)) {
                    this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
                }
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public void restoreTimeout(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (isCurrentSnackbarLocked(callback2)) {
                    scheduleTimeoutLocked(this.mCurrentSnackbar);
                }
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public boolean isCurrent(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        boolean z = obj;
        synchronized (obj) {
            try {
                z = isCurrentSnackbarLocked(callback2);
                return z;
            } finally {
                boolean z2 = z;
            }
        }
    }

    public boolean isCurrentOrNext(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                boolean z = isCurrentSnackbarLocked(callback2) || isNextSnackbarLocked(callback2);
                return z;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    private void showNextSnackbarLocked() {
        if (this.mNextSnackbar != null) {
            this.mCurrentSnackbar = this.mNextSnackbar;
            this.mNextSnackbar = null;
            Callback callback = (Callback) this.mCurrentSnackbar.callback.get();
            Callback callback2 = callback;
            if (callback == null) {
                this.mCurrentSnackbar = null;
            } else {
                callback2.show();
            }
        }
    }

    private boolean cancelSnackbarLocked(SnackbarRecord snackbarRecord, int i) {
        SnackbarRecord record = snackbarRecord;
        int event = i;
        SnackbarRecord snackbarRecord2 = record;
        int i2 = event;
        Callback callback = (Callback) record.callback.get();
        Callback callback2 = callback;
        if (callback == null) {
            return false;
        }
        this.mHandler.removeCallbacksAndMessages(record);
        callback2.dismiss(event);
        return true;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return this.mCurrentSnackbar != null && this.mCurrentSnackbar.isSnackbar(callback2);
    }

    private boolean isNextSnackbarLocked(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return this.mNextSnackbar != null && this.mNextSnackbar.isSnackbar(callback2);
    }

    private void scheduleTimeoutLocked(SnackbarRecord snackbarRecord) {
        SnackbarRecord r = snackbarRecord;
        SnackbarRecord snackbarRecord2 = r;
        if (r.duration != -2) {
            int durationMs = LONG_DURATION_MS;
            if (r.duration > 0) {
                durationMs = r.duration;
            } else if (r.duration == -1) {
                durationMs = SHORT_DURATION_MS;
            }
            this.mHandler.removeCallbacksAndMessages(r);
            boolean sendMessageDelayed = this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 0, r), (long) durationMs);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public void handleTimeout(SnackbarRecord snackbarRecord) {
        SnackbarRecord record = snackbarRecord;
        SnackbarRecord snackbarRecord2 = record;
        Object obj = this.mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (this.mCurrentSnackbar == record || this.mNextSnackbar == record) {
                    boolean cancelSnackbarLocked = cancelSnackbarLocked(record, 2);
                }
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }
}
