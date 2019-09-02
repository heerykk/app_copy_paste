package android.support.p000v4.p002os;

import android.os.Build.VERSION;

/* renamed from: android.support.v4.os.CancellationSignal */
public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    /* renamed from: android.support.v4.os.CancellationSignal$OnCancelListener */
    public interface OnCancelListener {
        void onCancel();
    }

    public CancellationSignal() {
    }

    public boolean isCanceled() {
        synchronized (this) {
            try {
                this = this.mIsCanceled;
            } finally {
            }
        }
        return this;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (r8 != null) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0.mCancelInProgress = false;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0027, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0028, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r7.onCancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0034, code lost:
        android.support.p000v4.p002os.CancellationSignalCompatJellybean.cancel(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0038, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0039, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x003b, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x003c, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x003e, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r0.mCancelInProgress = false;
        notifyAll();
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0047, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0048, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0049, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x004b, code lost:
        throw r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0019, code lost:
        if (r7 != null) goto L_0x0030;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r14 = this;
            r0 = r14
            r1 = r0
            r2 = r0
            monitor-enter(r2)
            boolean r3 = r0.mIsCanceled     // Catch:{ all -> 0x002c }
            r4 = r3
            r5 = 0
            if (r4 != r5) goto L_0x0029
            r6 = 1
            r0.mIsCanceled = r6     // Catch:{ all -> 0x002c }
            r6 = 1
            r0.mCancelInProgress = r6     // Catch:{ all -> 0x002c }
            android.support.v4.os.CancellationSignal$OnCancelListener r2 = r0.mOnCancelListener     // Catch:{ all -> 0x002c }
            r7 = r2
            java.lang.Object r2 = r0.mCancellationSignalObj     // Catch:{ all -> 0x002c }
            r8 = r2
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x002c }
            r10 = 0
            if (r7 != r10) goto L_0x0030
        L_0x001b:
            r10 = 0
            if (r8 != r10) goto L_0x0034
        L_0x001e:
            r2 = r0
            monitor-enter(r2)
            r6 = 0
            r0.mCancelInProgress = r6     // Catch:{ all -> 0x0038 }
            r0.notifyAll()     // Catch:{ all -> 0x0038 }
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            return
        L_0x0029:
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x002c }
            return
        L_0x002c:
            r9 = move-exception
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x002c }
            throw r9
        L_0x0030:
            r7.onCancel()     // Catch:{ all -> 0x003c }
            goto L_0x001b
        L_0x0034:
            android.support.p000v4.p002os.CancellationSignalCompatJellybean.cancel(r8)     // Catch:{ all -> 0x003c }
            goto L_0x001e
        L_0x0038:
            r11 = move-exception
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            throw r11
        L_0x003c:
            r12 = move-exception
            r2 = r0
            monitor-enter(r2)
            r6 = 0
            r0.mCancelInProgress = r6     // Catch:{ all -> 0x0048 }
            r0.notifyAll()     // Catch:{ all -> 0x0048 }
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x0048 }
            throw r12
        L_0x0048:
            r13 = move-exception
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x0048 }
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.p002os.CancellationSignal.cancel():void");
    }

    /* JADX INFO: finally extract failed */
    public void setOnCancelListener(OnCancelListener onCancelListener) {
        OnCancelListener listener = onCancelListener;
        OnCancelListener onCancelListener2 = listener;
        synchronized (this) {
            try {
                waitForCancelFinishedLocked();
                if (this.mOnCancelListener != listener) {
                    this.mOnCancelListener = listener;
                    if (this.mIsCanceled && listener != null) {
                        listener.onCancel();
                    }
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
    }

    public Object getCancellationSignalObject() {
        Object obj;
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        synchronized (this) {
            try {
                if (this.mCancellationSignalObj == null) {
                    this.mCancellationSignalObj = CancellationSignalCompatJellybean.create();
                    if (this.mIsCanceled) {
                        CancellationSignalCompatJellybean.cancel(this.mCancellationSignalObj);
                    }
                }
                obj = this.mCancellationSignalObj;
            } finally {
            }
        }
        return obj;
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}
