package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.SwipeDismissBehavior.OnDismissListener;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    static final int MSG_DISMISS = 1;
    static final int MSG_SHOW = 0;
    static final Handler sHandler = new Handler(Looper.getMainLooper(), new Callback() {
        public boolean handleMessage(Message message) {
            Message message2 = message;
            Message message3 = message2;
            switch (message2.what) {
                case 0:
                    ((BaseTransientBottomBar) message2.obj).showView();
                    return true;
                case 1:
                    ((BaseTransientBottomBar) message2.obj).hideView(message2.arg1);
                    return true;
                default:
                    return false;
            }
        }
    });
    private final AccessibilityManager mAccessibilityManager;
    private List<BaseCallback<B>> mCallbacks;
    private final ContentViewCallback mContentViewCallback;
    private final Context mContext;
    private int mDuration;
    final Callback mManagerCallback = new Callback(this) {
        final /* synthetic */ BaseTransientBottomBar this$0;

        {
            BaseTransientBottomBar this$02 = r5;
            BaseTransientBottomBar baseTransientBottomBar = this$02;
            this.this$0 = this$02;
        }

        public void show() {
            boolean sendMessage = BaseTransientBottomBar.sHandler.sendMessage(BaseTransientBottomBar.sHandler.obtainMessage(0, this.this$0));
        }

        public void dismiss(int i) {
            int event = i;
            int i2 = event;
            boolean sendMessage = BaseTransientBottomBar.sHandler.sendMessage(BaseTransientBottomBar.sHandler.obtainMessage(1, event, 0, this.this$0));
        }
    };
    private final ViewGroup mTargetParent;
    final SnackbarBaseLayout mView;

    public static abstract class BaseCallback<B> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        public BaseCallback() {
        }

        public void onDismissed(B b, int i) {
            B b2 = b;
            int i2 = i;
        }

        public void onShown(B b) {
            B b2 = b;
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarBaseLayout> {
        final /* synthetic */ BaseTransientBottomBar this$0;

        Behavior(BaseTransientBottomBar baseTransientBottomBar) {
            BaseTransientBottomBar this$02 = baseTransientBottomBar;
            BaseTransientBottomBar baseTransientBottomBar2 = this$02;
            this.this$0 = this$02;
        }

        public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return onInterceptTouchEvent(coordinatorLayout, (SnackbarBaseLayout) view, motionEvent);
        }

        public boolean canSwipeDismissView(View view) {
            View child = view;
            View view2 = child;
            return child instanceof SnackbarBaseLayout;
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, SnackbarBaseLayout snackbarBaseLayout, MotionEvent motionEvent) {
            CoordinatorLayout parent = coordinatorLayout;
            SnackbarBaseLayout child = snackbarBaseLayout;
            MotionEvent event = motionEvent;
            CoordinatorLayout coordinatorLayout2 = parent;
            SnackbarBaseLayout snackbarBaseLayout2 = child;
            MotionEvent motionEvent2 = event;
            if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                switch (event.getActionMasked()) {
                    case 0:
                        SnackbarManager.getInstance().cancelTimeout(this.this$0.mManagerCallback);
                        break;
                    case 1:
                    case 3:
                        SnackbarManager.getInstance().restoreTimeout(this.this$0.mManagerCallback);
                        break;
                }
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    public interface ContentViewCallback {
        void animateContentIn(int i, int i2);

        void animateContentOut(int i, int i2);
    }

    @IntRange(from = 1)
    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    static class SnackbarBaseLayout extends FrameLayout {
        private OnAttachStateChangeListener mOnAttachStateChangeListener;
        private OnLayoutChangeListener mOnLayoutChangeListener;

        SnackbarBaseLayout(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this(context2, null);
        }

        SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, C0001R.styleable.SnackbarLayout);
            TypedArray a = obtainStyledAttributes;
            if (obtainStyledAttributes.hasValue(C0001R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(C0001R.styleable.SnackbarLayout_elevation, 0));
            }
            a.recycle();
            setClickable(true);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int l = i;
            int t = i2;
            int r = i3;
            int b = i4;
            int i5 = l;
            int i6 = t;
            int i7 = r;
            int i8 = b;
            super.onLayout(z, l, t, r, b);
            if (this.mOnLayoutChangeListener != null) {
                this.mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        /* access modifiers changed from: protected */
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.mOnAttachStateChangeListener != null) {
                this.mOnAttachStateChangeListener.onViewAttachedToWindow(this);
            }
            ViewCompat.requestApplyInsets(this);
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.mOnAttachStateChangeListener != null) {
                this.mOnAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            OnLayoutChangeListener onLayoutChangeListener2 = onLayoutChangeListener;
            OnLayoutChangeListener onLayoutChangeListener3 = onLayoutChangeListener2;
            this.mOnLayoutChangeListener = onLayoutChangeListener2;
        }

        /* access modifiers changed from: 0000 */
        public void setOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
            OnAttachStateChangeListener listener = onAttachStateChangeListener;
            OnAttachStateChangeListener onAttachStateChangeListener2 = listener;
            this.mOnAttachStateChangeListener = listener;
        }
    }

    static /* synthetic */ ContentViewCallback access$000(BaseTransientBottomBar baseTransientBottomBar) {
        BaseTransientBottomBar x0 = baseTransientBottomBar;
        BaseTransientBottomBar baseTransientBottomBar2 = x0;
        return x0.mContentViewCallback;
    }

    protected BaseTransientBottomBar(@NonNull ViewGroup viewGroup, @NonNull View view, @NonNull ContentViewCallback contentViewCallback) {
        ViewGroup parent = viewGroup;
        View content = view;
        ContentViewCallback contentViewCallback2 = contentViewCallback;
        ViewGroup viewGroup2 = parent;
        View view2 = content;
        ContentViewCallback contentViewCallback3 = contentViewCallback2;
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        } else if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        } else if (contentViewCallback2 != null) {
            this.mTargetParent = parent;
            this.mContentViewCallback = contentViewCallback2;
            this.mContext = parent.getContext();
            ThemeUtils.checkAppCompatTheme(this.mContext);
            this.mView = (SnackbarBaseLayout) LayoutInflater.from(this.mContext).inflate(C0001R.layout.design_layout_snackbar, this.mTargetParent, false);
            this.mView.addView(content);
            ViewCompat.setAccessibilityLiveRegion(this.mView, 1);
            ViewCompat.setImportantForAccessibility(this.mView, 1);
            ViewCompat.setFitsSystemWindows(this.mView, true);
            ViewCompat.setOnApplyWindowInsetsListener(this.mView, new OnApplyWindowInsetsListener(this) {
                final /* synthetic */ BaseTransientBottomBar this$0;

                {
                    BaseTransientBottomBar this$02 = r5;
                    BaseTransientBottomBar baseTransientBottomBar = this$02;
                    this.this$0 = this$02;
                }

                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    View v = view;
                    WindowInsetsCompat insets = windowInsetsCompat;
                    View view2 = v;
                    WindowInsetsCompat windowInsetsCompat2 = insets;
                    v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), insets.getSystemWindowInsetBottom());
                    return insets;
                }
            });
            this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        } else {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
    }

    @NonNull
    public B setDuration(int i) {
        int duration = i;
        int i2 = duration;
        this.mDuration = duration;
        return this;
    }

    public int getDuration() {
        return this.mDuration;
    }

    @NonNull
    public Context getContext() {
        return this.mContext;
    }

    @NonNull
    public View getView() {
        return this.mView;
    }

    public void show() {
        SnackbarManager.getInstance().show(this.mDuration, this.mManagerCallback);
    }

    public void dismiss() {
        dispatchDismiss(3);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchDismiss(int i) {
        int event = i;
        int i2 = event;
        SnackbarManager.getInstance().dismiss(this.mManagerCallback, event);
    }

    @NonNull
    public B addCallback(@NonNull BaseCallback<B> baseCallback) {
        BaseCallback<B> callback = baseCallback;
        BaseCallback<B> baseCallback2 = callback;
        if (callback == null) {
            return this;
        }
        if (this.mCallbacks == null) {
            this.mCallbacks = new ArrayList();
        }
        boolean add = this.mCallbacks.add(callback);
        return this;
    }

    @NonNull
    public B removeCallback(@NonNull BaseCallback<B> baseCallback) {
        BaseCallback<B> callback = baseCallback;
        BaseCallback<B> baseCallback2 = callback;
        if (callback == null) {
            return this;
        }
        if (this.mCallbacks == null) {
            return this;
        }
        boolean remove = this.mCallbacks.remove(callback);
        return this;
    }

    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(this.mManagerCallback);
    }

    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(this.mManagerCallback);
    }

    /* access modifiers changed from: 0000 */
    public final void showView() {
        if (this.mView.getParent() == null) {
            LayoutParams layoutParams = this.mView.getLayoutParams();
            LayoutParams lp = layoutParams;
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;
                Behavior behavior = new Behavior(this);
                Behavior behavior2 = behavior;
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior2.setEndAlphaSwipeDistance(0.6f);
                behavior2.setSwipeDirection(0);
                behavior2.setListener(new OnDismissListener(this) {
                    final /* synthetic */ BaseTransientBottomBar this$0;

                    {
                        BaseTransientBottomBar this$02 = r5;
                        BaseTransientBottomBar baseTransientBottomBar = this$02;
                        this.this$0 = this$02;
                    }

                    public void onDismiss(View view) {
                        View view2 = view;
                        View view3 = view2;
                        view2.setVisibility(8);
                        this.this$0.dispatchDismiss(0);
                    }

                    public void onDragStateChanged(int i) {
                        int state = i;
                        int i2 = state;
                        switch (state) {
                            case 0:
                                SnackbarManager.getInstance().restoreTimeout(this.this$0.mManagerCallback);
                                return;
                            case 1:
                            case 2:
                                SnackbarManager.getInstance().cancelTimeout(this.this$0.mManagerCallback);
                                return;
                            default:
                                return;
                        }
                    }
                });
                clp.setBehavior(behavior2);
                clp.insetEdge = 80;
            }
            this.mTargetParent.addView(this.mView);
        }
        this.mView.setOnAttachStateChangeListener(new OnAttachStateChangeListener(this) {
            final /* synthetic */ BaseTransientBottomBar this$0;

            {
                BaseTransientBottomBar this$02 = r5;
                BaseTransientBottomBar baseTransientBottomBar = this$02;
                this.this$0 = this$02;
            }

            public void onViewAttachedToWindow(View view) {
                View view2 = view;
            }

            public void onViewDetachedFromWindow(View view) {
                View view2 = view;
                if (this.this$0.isShownOrQueued()) {
                    boolean post = BaseTransientBottomBar.sHandler.post(new Runnable(this) {
                        final /* synthetic */ C00185 this$1;

                        {
                            C00185 this$12 = r5;
                            C00185 r3 = this$12;
                            this.this$1 = this$12;
                        }

                        public void run() {
                            this.this$1.this$0.onViewHidden(3);
                        }
                    });
                }
            }
        });
        if (!ViewCompat.isLaidOut(this.mView)) {
            this.mView.setOnLayoutChangeListener(new OnLayoutChangeListener(this) {
                final /* synthetic */ BaseTransientBottomBar this$0;

                {
                    BaseTransientBottomBar this$02 = r5;
                    BaseTransientBottomBar baseTransientBottomBar = this$02;
                    this.this$0 = this$02;
                }

                public void onLayoutChange(View view, int i, int i2, int i3, int i4) {
                    View view2 = view;
                    int i5 = i;
                    int i6 = i2;
                    int i7 = i3;
                    int i8 = i4;
                    this.this$0.mView.setOnLayoutChangeListener(null);
                    if (!this.this$0.shouldAnimate()) {
                        this.this$0.onViewShown();
                    } else {
                        this.this$0.animateViewIn();
                    }
                }
            });
        } else if (!shouldAnimate()) {
            onViewShown();
        } else {
            animateViewIn();
        }
    }

    /* access modifiers changed from: 0000 */
    public void animateViewIn() {
        if (VERSION.SDK_INT < 14) {
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mView.getContext(), C0001R.anim.design_snackbar_in);
            Animation anim = loadAnimation;
            loadAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(250);
            anim.setAnimationListener(new AnimationListener(this) {
                final /* synthetic */ BaseTransientBottomBar this$0;

                {
                    BaseTransientBottomBar this$02 = r5;
                    BaseTransientBottomBar baseTransientBottomBar = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationEnd(Animation animation) {
                    Animation animation2 = animation;
                    this.this$0.onViewShown();
                }

                public void onAnimationStart(Animation animation) {
                    Animation animation2 = animation;
                }

                public void onAnimationRepeat(Animation animation) {
                    Animation animation2 = animation;
                }
            });
            this.mView.startAnimation(anim);
            return;
        }
        ViewCompat.setTranslationY(this.mView, (float) this.mView.getHeight());
        ViewCompat.animate(this.mView).translationY(0.0f).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250).setListener(new ViewPropertyAnimatorListenerAdapter(this) {
            final /* synthetic */ BaseTransientBottomBar this$0;

            {
                BaseTransientBottomBar this$02 = r5;
                BaseTransientBottomBar baseTransientBottomBar = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                BaseTransientBottomBar.access$000(this.this$0).animateContentIn(70, BaseTransientBottomBar.ANIMATION_FADE_DURATION);
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                this.this$0.onViewShown();
            }
        }).start();
    }

    private void animateViewOut(int i) {
        final int event = i;
        int i2 = event;
        if (VERSION.SDK_INT < 14) {
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mView.getContext(), C0001R.anim.design_snackbar_out);
            Animation anim = loadAnimation;
            loadAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(250);
            anim.setAnimationListener(new AnimationListener(this) {
                final /* synthetic */ BaseTransientBottomBar this$0;

                {
                    BaseTransientBottomBar this$02 = r6;
                    int i = r7;
                    BaseTransientBottomBar baseTransientBottomBar = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationEnd(Animation animation) {
                    Animation animation2 = animation;
                    this.this$0.onViewHidden(event);
                }

                public void onAnimationStart(Animation animation) {
                    Animation animation2 = animation;
                }

                public void onAnimationRepeat(Animation animation) {
                    Animation animation2 = animation;
                }
            });
            this.mView.startAnimation(anim);
            return;
        }
        ViewCompat.animate(this.mView).translationY((float) this.mView.getHeight()).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250).setListener(new ViewPropertyAnimatorListenerAdapter(this) {
            final /* synthetic */ BaseTransientBottomBar this$0;

            {
                BaseTransientBottomBar this$02 = r6;
                int i = r7;
                BaseTransientBottomBar baseTransientBottomBar = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                BaseTransientBottomBar.access$000(this.this$0).animateContentOut(0, BaseTransientBottomBar.ANIMATION_FADE_DURATION);
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                this.this$0.onViewHidden(event);
            }
        }).start();
    }

    /* access modifiers changed from: 0000 */
    public final void hideView(int i) {
        int event = i;
        int i2 = event;
        if (shouldAnimate() && this.mView.getVisibility() == 0) {
            animateViewOut(event);
        } else {
            onViewHidden(event);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onViewShown() {
        SnackbarManager.getInstance().onShown(this.mManagerCallback);
        if (this.mCallbacks != null) {
            int size = this.mCallbacks.size();
            int i = size;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                ((BaseCallback) this.mCallbacks.get(i2)).onShown(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onViewHidden(int i) {
        int event = i;
        int i2 = event;
        SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
        if (this.mCallbacks != null) {
            int size = this.mCallbacks.size();
            int i3 = size;
            for (int i4 = size - 1; i4 >= 0; i4--) {
                ((BaseCallback) this.mCallbacks.get(i4)).onDismissed(this, event);
            }
        }
        if (VERSION.SDK_INT < 11) {
            this.mView.setVisibility(8);
        }
        ViewParent parent = this.mView.getParent();
        ViewParent parent2 = parent;
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent2).removeView(this.mView);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldAnimate() {
        return !this.mAccessibilityManager.isEnabled();
    }
}
