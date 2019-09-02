package android.support.p000v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.widget.EdgeEffectCompat */
public final class EdgeEffectCompat {
    private static final EdgeEffectImpl IMPL;
    private Object mEdgeEffect;

    /* renamed from: android.support.v4.widget.EdgeEffectCompat$BaseEdgeEffectImpl */
    static class BaseEdgeEffectImpl implements EdgeEffectImpl {
        BaseEdgeEffectImpl() {
        }

        public Object newEdgeEffect(Context context) {
            Context context2 = context;
            return null;
        }

        public void setSize(Object obj, int i, int i2) {
            Object obj2 = obj;
            int i3 = i;
            int i4 = i2;
        }

        public boolean isFinished(Object obj) {
            Object obj2 = obj;
            return true;
        }

        public void finish(Object obj) {
            Object obj2 = obj;
        }

        public boolean onPull(Object obj, float f) {
            Object obj2 = obj;
            float f2 = f;
            return false;
        }

        public boolean onRelease(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean onAbsorb(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
            return false;
        }

        public boolean draw(Object obj, Canvas canvas) {
            Object obj2 = obj;
            Canvas canvas2 = canvas;
            return false;
        }

        public boolean onPull(Object obj, float f, float f2) {
            Object obj2 = obj;
            float f3 = f;
            float f4 = f2;
            return false;
        }
    }

    /* renamed from: android.support.v4.widget.EdgeEffectCompat$EdgeEffectIcsImpl */
    static class EdgeEffectIcsImpl implements EdgeEffectImpl {
        EdgeEffectIcsImpl() {
        }

        public Object newEdgeEffect(Context context) {
            Context context2 = context;
            Context context3 = context2;
            return EdgeEffectCompatIcs.newEdgeEffect(context2);
        }

        public void setSize(Object obj, int i, int i2) {
            Object edgeEffect = obj;
            int width = i;
            int height = i2;
            Object obj2 = edgeEffect;
            int i3 = width;
            int i4 = height;
            EdgeEffectCompatIcs.setSize(edgeEffect, width, height);
        }

        public boolean isFinished(Object obj) {
            Object edgeEffect = obj;
            Object obj2 = edgeEffect;
            return EdgeEffectCompatIcs.isFinished(edgeEffect);
        }

        public void finish(Object obj) {
            Object edgeEffect = obj;
            Object obj2 = edgeEffect;
            EdgeEffectCompatIcs.finish(edgeEffect);
        }

        public boolean onPull(Object obj, float f) {
            Object edgeEffect = obj;
            float deltaDistance = f;
            Object obj2 = edgeEffect;
            float f2 = deltaDistance;
            return EdgeEffectCompatIcs.onPull(edgeEffect, deltaDistance);
        }

        public boolean onRelease(Object obj) {
            Object edgeEffect = obj;
            Object obj2 = edgeEffect;
            return EdgeEffectCompatIcs.onRelease(edgeEffect);
        }

        public boolean onAbsorb(Object obj, int i) {
            Object edgeEffect = obj;
            int velocity = i;
            Object obj2 = edgeEffect;
            int i2 = velocity;
            return EdgeEffectCompatIcs.onAbsorb(edgeEffect, velocity);
        }

        public boolean draw(Object obj, Canvas canvas) {
            Object edgeEffect = obj;
            Canvas canvas2 = canvas;
            Object obj2 = edgeEffect;
            Canvas canvas3 = canvas2;
            return EdgeEffectCompatIcs.draw(edgeEffect, canvas2);
        }

        public boolean onPull(Object obj, float f, float f2) {
            Object edgeEffect = obj;
            float deltaDistance = f;
            Object obj2 = edgeEffect;
            float f3 = deltaDistance;
            float f4 = f2;
            return EdgeEffectCompatIcs.onPull(edgeEffect, deltaDistance);
        }
    }

    /* renamed from: android.support.v4.widget.EdgeEffectCompat$EdgeEffectImpl */
    interface EdgeEffectImpl {
        boolean draw(Object obj, Canvas canvas);

        void finish(Object obj);

        boolean isFinished(Object obj);

        Object newEdgeEffect(Context context);

        boolean onAbsorb(Object obj, int i);

        boolean onPull(Object obj, float f);

        boolean onPull(Object obj, float f, float f2);

        boolean onRelease(Object obj);

        void setSize(Object obj, int i, int i2);
    }

    /* renamed from: android.support.v4.widget.EdgeEffectCompat$EdgeEffectLollipopImpl */
    static class EdgeEffectLollipopImpl extends EdgeEffectIcsImpl {
        EdgeEffectLollipopImpl() {
        }

        public boolean onPull(Object obj, float f, float f2) {
            Object edgeEffect = obj;
            float deltaDistance = f;
            float displacement = f2;
            Object obj2 = edgeEffect;
            float f3 = deltaDistance;
            float f4 = displacement;
            return EdgeEffectCompatLollipop.onPull(edgeEffect, deltaDistance, displacement);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new EdgeEffectLollipopImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new BaseEdgeEffectImpl();
        } else {
            IMPL = new EdgeEffectIcsImpl();
        }
    }

    public EdgeEffectCompat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mEdgeEffect = IMPL.newEdgeEffect(context2);
    }

    public void setSize(int i, int i2) {
        int width = i;
        int height = i2;
        int i3 = width;
        int i4 = height;
        IMPL.setSize(this.mEdgeEffect, width, height);
    }

    public boolean isFinished() {
        return IMPL.isFinished(this.mEdgeEffect);
    }

    public void finish() {
        IMPL.finish(this.mEdgeEffect);
    }

    @Deprecated
    public boolean onPull(float f) {
        float deltaDistance = f;
        float f2 = deltaDistance;
        return IMPL.onPull(this.mEdgeEffect, deltaDistance);
    }

    public boolean onPull(float f, float f2) {
        float deltaDistance = f;
        float displacement = f2;
        float f3 = deltaDistance;
        float f4 = displacement;
        return IMPL.onPull(this.mEdgeEffect, deltaDistance, displacement);
    }

    public boolean onRelease() {
        return IMPL.onRelease(this.mEdgeEffect);
    }

    public boolean onAbsorb(int i) {
        int velocity = i;
        int i2 = velocity;
        return IMPL.onAbsorb(this.mEdgeEffect, velocity);
    }

    public boolean draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        return IMPL.draw(this.mEdgeEffect, canvas2);
    }
}
