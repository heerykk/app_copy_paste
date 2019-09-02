package android.support.p000v4.app;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;

/* renamed from: android.support.v4.app.OneShotPreDrawListener */
class OneShotPreDrawListener implements OnPreDrawListener, OnAttachStateChangeListener {
    private final Runnable mRunnable;
    private final View mView;
    private ViewTreeObserver mViewTreeObserver;

    private OneShotPreDrawListener(View view, Runnable runnable) {
        View view2 = view;
        Runnable runnable2 = runnable;
        View view3 = view2;
        Runnable runnable3 = runnable2;
        this.mView = view2;
        this.mViewTreeObserver = view2.getViewTreeObserver();
        this.mRunnable = runnable2;
    }

    public static OneShotPreDrawListener add(View view, Runnable runnable) {
        View view2 = view;
        Runnable runnable2 = runnable;
        View view3 = view2;
        Runnable runnable3 = runnable2;
        OneShotPreDrawListener listener = new OneShotPreDrawListener(view2, runnable2);
        view2.getViewTreeObserver().addOnPreDrawListener(listener);
        view2.addOnAttachStateChangeListener(listener);
        return listener;
    }

    public boolean onPreDraw() {
        removeListener();
        this.mRunnable.run();
        return true;
    }

    public void removeListener() {
        if (!this.mViewTreeObserver.isAlive()) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
        } else {
            this.mViewTreeObserver.removeOnPreDrawListener(this);
        }
        this.mView.removeOnAttachStateChangeListener(this);
    }

    public void onViewAttachedToWindow(View view) {
        View v = view;
        View view2 = v;
        this.mViewTreeObserver = v.getViewTreeObserver();
    }

    public void onViewDetachedFromWindow(View view) {
        View view2 = view;
        removeListener();
    }
}
