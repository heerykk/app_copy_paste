package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@TargetApi(14)
@RequiresApi(14)
class ViewOverlay {
    protected OverlayViewGroup mOverlayViewGroup;

    static class OverlayViewGroup extends ViewGroup {
        static Method sInvalidateChildInParentFastMethod;
        ArrayList<Drawable> mDrawables = null;
        ViewGroup mHostView;
        View mRequestingView;
        ViewOverlay mViewOverlay;

        static class TouchInterceptor extends View {
            TouchInterceptor(Context context) {
                Context context2 = context;
                Context context3 = context2;
                super(context2);
            }
        }

        static {
            Class<ViewGroup> cls = ViewGroup.class;
            String str = "invalidateChildInParentFast";
            try {
                Class[] clsArr = new Class[3];
                clsArr[0] = Integer.TYPE;
                clsArr[1] = Integer.TYPE;
                clsArr[2] = Rect.class;
                sInvalidateChildInParentFastMethod = cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
            }
        }

        OverlayViewGroup(Context context, ViewGroup viewGroup, View view, ViewOverlay viewOverlay) {
            Context context2 = context;
            ViewGroup hostView = viewGroup;
            View requestingView = view;
            ViewOverlay viewOverlay2 = viewOverlay;
            Context context3 = context2;
            ViewGroup viewGroup2 = hostView;
            View view2 = requestingView;
            ViewOverlay viewOverlay3 = viewOverlay2;
            super(context2);
            this.mHostView = hostView;
            this.mRequestingView = requestingView;
            setRight(hostView.getWidth());
            setBottom(hostView.getHeight());
            hostView.addView(this);
            this.mViewOverlay = viewOverlay2;
        }

        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            MotionEvent motionEvent2 = motionEvent;
            return false;
        }

        public void add(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            if (this.mDrawables == null) {
                this.mDrawables = new ArrayList<>();
            }
            if (!this.mDrawables.contains(drawable2)) {
                boolean add = this.mDrawables.add(drawable2);
                invalidate(drawable2.getBounds());
                drawable2.setCallback(this);
            }
        }

        public void remove(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            if (this.mDrawables != null) {
                boolean remove = this.mDrawables.remove(drawable2);
                invalidate(drawable2.getBounds());
                drawable2.setCallback(null);
            }
        }

        /* access modifiers changed from: protected */
        public boolean verifyDrawable(Drawable drawable) {
            Drawable who = drawable;
            Drawable drawable2 = who;
            return super.verifyDrawable(who) || (this.mDrawables != null && this.mDrawables.contains(who));
        }

        public void add(View view) {
            View child = view;
            View view2 = child;
            if (child.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) child.getParent();
                ViewGroup parent = viewGroup;
                if (!(viewGroup == this.mHostView || parent.getParent() == null)) {
                    int[] parentLocation = new int[2];
                    int[] hostViewLocation = new int[2];
                    parent.getLocationOnScreen(parentLocation);
                    this.mHostView.getLocationOnScreen(hostViewLocation);
                    ViewCompat.offsetLeftAndRight(child, parentLocation[0] - hostViewLocation[0]);
                    ViewCompat.offsetTopAndBottom(child, parentLocation[1] - hostViewLocation[1]);
                }
                parent.removeView(child);
                if (child.getParent() != null) {
                    parent.removeView(child);
                }
            }
            super.addView(child, getChildCount() - 1);
        }

        public void remove(View view) {
            View view2 = view;
            View view3 = view2;
            super.removeView(view2);
            if (isEmpty()) {
                this.mHostView.removeView(this);
            }
        }

        public void clear() {
            removeAllViews();
            if (this.mDrawables != null) {
                this.mDrawables.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean isEmpty() {
            if (getChildCount() == 0 && (this.mDrawables == null || this.mDrawables.size() == 0)) {
                return true;
            }
            return false;
        }

        public void invalidateDrawable(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            invalidate(drawable2.getBounds());
        }

        /* access modifiers changed from: protected */
        public void dispatchDraw(Canvas canvas) {
            Canvas canvas2 = canvas;
            Canvas canvas3 = canvas2;
            int[] contentViewLocation = new int[2];
            int[] hostViewLocation = new int[2];
            ViewGroup viewGroup = (ViewGroup) getParent();
            this.mHostView.getLocationOnScreen(contentViewLocation);
            this.mRequestingView.getLocationOnScreen(hostViewLocation);
            canvas2.translate((float) (hostViewLocation[0] - contentViewLocation[0]), (float) (hostViewLocation[1] - contentViewLocation[1]));
            Rect rect = new Rect(0, 0, this.mRequestingView.getWidth(), this.mRequestingView.getHeight());
            boolean clipRect = canvas2.clipRect(rect);
            super.dispatchDraw(canvas2);
            int numDrawables = this.mDrawables != null ? this.mDrawables.size() : 0;
            for (int i = 0; i < numDrawables; i++) {
                ((Drawable) this.mDrawables.get(i)).draw(canvas2);
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            boolean z2 = z;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
        }

        private void getOffset(int[] iArr) {
            int[] offset = iArr;
            int[] iArr2 = offset;
            int[] contentViewLocation = new int[2];
            int[] hostViewLocation = new int[2];
            ViewGroup viewGroup = (ViewGroup) getParent();
            this.mHostView.getLocationOnScreen(contentViewLocation);
            this.mRequestingView.getLocationOnScreen(hostViewLocation);
            offset[0] = hostViewLocation[0] - contentViewLocation[0];
            offset[1] = hostViewLocation[1] - contentViewLocation[1];
        }

        public void invalidateChildFast(View view, Rect rect) {
            View child = view;
            Rect dirty = rect;
            View view2 = child;
            Rect rect2 = dirty;
            if (this.mHostView != null) {
                int left = child.getLeft();
                int top = child.getTop();
                int[] offset = new int[2];
                getOffset(offset);
                dirty.offset(left + offset[0], top + offset[1]);
                this.mHostView.invalidate(dirty);
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public ViewParent invalidateChildInParentFast(int i, int i2, Rect rect) {
            int left = i;
            int top = i2;
            Rect dirty = rect;
            int i3 = left;
            int i4 = top;
            Rect rect2 = dirty;
            if ((this.mHostView instanceof ViewGroup) && sInvalidateChildInParentFastMethod != null) {
                try {
                    getOffset(new int[2]);
                    Method method = sInvalidateChildInParentFastMethod;
                    ViewGroup viewGroup = this.mHostView;
                    Object[] objArr = new Object[3];
                    objArr[0] = Integer.valueOf(left);
                    objArr[1] = Integer.valueOf(top);
                    objArr[2] = dirty;
                    Object invoke = method.invoke(viewGroup, objArr);
                } catch (IllegalAccessException e) {
                    IllegalAccessException illegalAccessException = e;
                    e.printStackTrace();
                } catch (InvocationTargetException e2) {
                    InvocationTargetException invocationTargetException = e2;
                    e2.printStackTrace();
                }
            }
            return null;
        }

        public ViewParent invalidateChildInParent(int[] iArr, Rect rect) {
            int[] location = iArr;
            Rect dirty = rect;
            int[] iArr2 = location;
            Rect rect2 = dirty;
            if (this.mHostView != null) {
                dirty.offset(location[0], location[1]);
                if (!(this.mHostView instanceof ViewGroup)) {
                    invalidate(dirty);
                } else {
                    location[0] = 0;
                    location[1] = 0;
                    int[] offset = new int[2];
                    getOffset(offset);
                    dirty.offset(offset[0], offset[1]);
                    return super.invalidateChildInParent(location, dirty);
                }
            }
            return null;
        }
    }

    ViewOverlay(Context context, ViewGroup viewGroup, View view) {
        Context context2 = context;
        ViewGroup hostView = viewGroup;
        View requestingView = view;
        Context context3 = context2;
        ViewGroup viewGroup2 = hostView;
        View view2 = requestingView;
        this.mOverlayViewGroup = new OverlayViewGroup(context2, hostView, requestingView, this);
    }

    static ViewGroup getContentView(View view) {
        View view2 = view;
        View view3 = view2;
        View parent = view2;
        while (parent != null) {
            if (parent.getId() == 16908290 && (parent instanceof ViewGroup)) {
                return (ViewGroup) parent;
            }
            if (parent.getParent() instanceof ViewGroup) {
                parent = (ViewGroup) parent.getParent();
            }
        }
        return null;
    }

    public static ViewOverlay createFrom(View view) {
        View view2 = view;
        View view3 = view2;
        ViewGroup contentView = getContentView(view2);
        ViewGroup contentView2 = contentView;
        if (contentView == null) {
            return null;
        }
        int numChildren = contentView2.getChildCount();
        for (int i = 0; i < numChildren; i++) {
            View childAt = contentView2.getChildAt(i);
            View child = childAt;
            if (childAt instanceof OverlayViewGroup) {
                return ((OverlayViewGroup) child).mViewOverlay;
            }
        }
        return new ViewGroupOverlay(contentView2.getContext(), contentView2, view2);
    }

    /* access modifiers changed from: 0000 */
    public ViewGroup getOverlayView() {
        return this.mOverlayViewGroup;
    }

    public void add(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        this.mOverlayViewGroup.add(drawable2);
    }

    public void remove(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        this.mOverlayViewGroup.remove(drawable2);
    }

    public void clear() {
        this.mOverlayViewGroup.clear();
    }

    /* access modifiers changed from: 0000 */
    public boolean isEmpty() {
        return this.mOverlayViewGroup.isEmpty();
    }
}
