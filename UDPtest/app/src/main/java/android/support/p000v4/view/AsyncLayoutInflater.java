package android.support.p000v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.p000v4.util.Pools.SynchronizedPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ArrayBlockingQueue;

/* renamed from: android.support.v4.view.AsyncLayoutInflater */
public final class AsyncLayoutInflater {
    private static final String TAG = "AsyncLayoutInflater";
    Handler mHandler;
    private Callback mHandlerCallback = new Callback(this) {
        final /* synthetic */ AsyncLayoutInflater this$0;

        {
            AsyncLayoutInflater this$02 = r5;
            AsyncLayoutInflater asyncLayoutInflater = this$02;
            this.this$0 = this$02;
        }

        public boolean handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            InflateRequest inflateRequest = (InflateRequest) msg.obj;
            InflateRequest request = inflateRequest;
            if (inflateRequest.view == null) {
                request.view = this.this$0.mInflater.inflate(request.resid, request.parent, false);
            }
            request.callback.onInflateFinished(request.view, request.resid, request.parent);
            this.this$0.mInflateThread.releaseRequest(request);
            return true;
        }
    };
    InflateThread mInflateThread;
    LayoutInflater mInflater;

    /* renamed from: android.support.v4.view.AsyncLayoutInflater$BasicInflater */
    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList;

        static {
            String[] strArr = new String[3];
            strArr[0] = "android.widget.";
            strArr[1] = "android.webkit.";
            strArr[2] = "android.app.";
            sClassPrefixList = strArr;
        }

        BasicInflater(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(context2);
        }

        public LayoutInflater cloneInContext(Context context) {
            Context newContext = context;
            Context context2 = newContext;
            return new BasicInflater(newContext);
        }

        /* access modifiers changed from: protected */
        public View onCreateView(String str, AttributeSet attributeSet) throws ClassNotFoundException {
            String name = str;
            AttributeSet attrs = attributeSet;
            String str2 = name;
            AttributeSet attributeSet2 = attrs;
            String[] strArr = sClassPrefixList;
            String[] strArr2 = strArr;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                try {
                    View createView = createView(name, strArr2[i], attrs);
                    View view = createView;
                    if (createView != null) {
                        return view;
                    }
                    i++;
                } catch (ClassNotFoundException e) {
                    ClassNotFoundException classNotFoundException = e;
                }
            }
            return super.onCreateView(name, attrs);
        }
    }

    /* renamed from: android.support.v4.view.AsyncLayoutInflater$InflateRequest */
    private static class InflateRequest {
        OnInflateFinishedListener callback;
        AsyncLayoutInflater inflater;
        ViewGroup parent;
        int resid;
        View view;

        InflateRequest() {
        }
    }

    /* renamed from: android.support.v4.view.AsyncLayoutInflater$InflateThread */
    private static class InflateThread extends Thread {
        private static final InflateThread sInstance = new InflateThread();
        private ArrayBlockingQueue<InflateRequest> mQueue = new ArrayBlockingQueue<>(10);
        private SynchronizedPool<InflateRequest> mRequestPool = new SynchronizedPool<>(10);

        private InflateThread() {
        }

        static {
            sInstance.start();
        }

        public static InflateThread getInstance() {
            return sInstance;
        }

        public void run() {
            while (true) {
                try {
                    InflateRequest inflateRequest = (InflateRequest) this.mQueue.take();
                    InflateRequest request = inflateRequest;
                    try {
                        inflateRequest.view = request.inflater.mInflater.inflate(request.resid, request.parent, false);
                    } catch (RuntimeException e) {
                        RuntimeException runtimeException = e;
                        int w = Log.w(AsyncLayoutInflater.TAG, "Failed to inflate resource in the background! Retrying on the UI thread", e);
                    }
                    Message.obtain(request.inflater.mHandler, 0, request).sendToTarget();
                } catch (InterruptedException e2) {
                    InterruptedException interruptedException = e2;
                    int w2 = Log.w(AsyncLayoutInflater.TAG, e2);
                }
            }
        }

        public InflateRequest obtainRequest() {
            InflateRequest inflateRequest = (InflateRequest) this.mRequestPool.acquire();
            InflateRequest obj = inflateRequest;
            if (inflateRequest == null) {
                obj = new InflateRequest();
            }
            return obj;
        }

        public void releaseRequest(InflateRequest inflateRequest) {
            InflateRequest obj = inflateRequest;
            InflateRequest inflateRequest2 = obj;
            obj.callback = null;
            obj.inflater = null;
            obj.parent = null;
            obj.resid = 0;
            obj.view = null;
            boolean release = this.mRequestPool.release(obj);
        }

        public void enqueue(InflateRequest inflateRequest) {
            InflateRequest request = inflateRequest;
            InflateRequest inflateRequest2 = request;
            try {
                this.mQueue.put(request);
            } catch (InterruptedException e) {
                InterruptedException interruptedException = e;
                throw new RuntimeException("Failed to enqueue async inflate request", e);
            }
        }
    }

    /* renamed from: android.support.v4.view.AsyncLayoutInflater$OnInflateFinishedListener */
    public interface OnInflateFinishedListener {
        void onInflateFinished(View view, int i, ViewGroup viewGroup);
    }

    public AsyncLayoutInflater(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mInflater = new BasicInflater(context2);
        this.mHandler = new Handler(this.mHandlerCallback);
        this.mInflateThread = InflateThread.getInstance();
    }

    @UiThread
    public void inflate(@LayoutRes int i, @Nullable ViewGroup viewGroup, @NonNull OnInflateFinishedListener onInflateFinishedListener) {
        int resid = i;
        ViewGroup parent = viewGroup;
        OnInflateFinishedListener callback = onInflateFinishedListener;
        int i2 = resid;
        ViewGroup viewGroup2 = parent;
        OnInflateFinishedListener onInflateFinishedListener2 = callback;
        if (callback != null) {
            InflateRequest obtainRequest = this.mInflateThread.obtainRequest();
            InflateRequest request = obtainRequest;
            obtainRequest.inflater = this;
            request.resid = resid;
            request.parent = parent;
            request.callback = callback;
            this.mInflateThread.enqueue(request);
            return;
        }
        throw new NullPointerException("callback argument may not be null!");
    }
}
