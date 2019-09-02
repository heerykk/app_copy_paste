package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.design.C0001R;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.BaseTransientBottomBar.ContentViewCallback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class Snackbar extends BaseTransientBottomBar<Snackbar> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    @Nullable
    private BaseCallback<Snackbar> mCallback;

    public static class Callback extends BaseCallback<Snackbar> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        public Callback() {
        }

        public /* bridge */ /* synthetic */ void onDismissed(Object obj, int i) {
            onDismissed((Snackbar) obj, i);
        }

        public /* bridge */ /* synthetic */ void onShown(Object obj) {
            onShown((Snackbar) obj);
        }

        public void onShown(Snackbar snackbar) {
            Snackbar snackbar2 = snackbar;
        }

        public void onDismissed(Snackbar snackbar, int i) {
            Snackbar snackbar2 = snackbar;
            int i2 = i;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final class SnackbarLayout extends SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(context2);
        }

        public SnackbarLayout(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
        }
    }

    private Snackbar(ViewGroup viewGroup, View view, ContentViewCallback contentViewCallback) {
        ViewGroup parent = viewGroup;
        View content = view;
        ContentViewCallback contentViewCallback2 = contentViewCallback;
        ViewGroup viewGroup2 = parent;
        View view2 = content;
        ContentViewCallback contentViewCallback3 = contentViewCallback2;
        super(parent, content, contentViewCallback2);
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @NonNull CharSequence charSequence, int i) {
        View view2 = view;
        CharSequence text = charSequence;
        int duration = i;
        View view3 = view2;
        CharSequence charSequence2 = text;
        int i2 = duration;
        ViewGroup findSuitableParent = findSuitableParent(view2);
        ViewGroup parent = findSuitableParent;
        LayoutInflater from = LayoutInflater.from(findSuitableParent.getContext());
        LayoutInflater layoutInflater = from;
        SnackbarContentLayout content = (SnackbarContentLayout) from.inflate(C0001R.layout.design_layout_snackbar_include, parent, false);
        Snackbar snackbar = new Snackbar(parent, content, content);
        Snackbar snackbar2 = snackbar;
        Snackbar text2 = snackbar.setText(text);
        BaseTransientBottomBar duration2 = snackbar2.setDuration(duration);
        return snackbar2;
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @StringRes int i, int i2) {
        View view2 = view;
        int resId = i;
        int duration = i2;
        View view3 = view2;
        int i3 = resId;
        int i4 = duration;
        return make(view2, view2.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        View view2 = view;
        ViewGroup fallback = null;
        while (!(view2 instanceof CoordinatorLayout)) {
            if (view2 instanceof FrameLayout) {
                if (view2.getId() == 16908290) {
                    return (ViewGroup) view2;
                }
                fallback = (ViewGroup) view2;
            }
            if (view2 != null) {
                ViewParent parent = view2.getParent();
                view2 = !(parent instanceof View) ? null : (View) parent;
            }
            if (view2 == null) {
                return fallback;
            }
        }
        return (ViewGroup) view2;
    }

    @NonNull
    public Snackbar setText(@NonNull CharSequence charSequence) {
        CharSequence message = charSequence;
        CharSequence charSequence2 = message;
        SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) this.mView.getChildAt(0);
        SnackbarContentLayout snackbarContentLayout2 = snackbarContentLayout;
        TextView messageView = snackbarContentLayout.getMessageView();
        TextView textView = messageView;
        messageView.setText(message);
        return this;
    }

    @NonNull
    public Snackbar setText(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        return setText(getContext().getText(resId));
    }

    @NonNull
    public Snackbar setAction(@StringRes int i, OnClickListener onClickListener) {
        int resId = i;
        OnClickListener listener = onClickListener;
        int i2 = resId;
        OnClickListener onClickListener2 = listener;
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public Snackbar setAction(CharSequence charSequence, OnClickListener onClickListener) {
        CharSequence text = charSequence;
        OnClickListener listener = onClickListener;
        CharSequence charSequence2 = text;
        OnClickListener onClickListener2 = listener;
        SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) this.mView.getChildAt(0);
        SnackbarContentLayout snackbarContentLayout2 = snackbarContentLayout;
        Button actionView = snackbarContentLayout.getActionView();
        if (!TextUtils.isEmpty(text) && listener != null) {
            actionView.setVisibility(0);
            actionView.setText(text);
            final OnClickListener onClickListener3 = listener;
            C00441 r0 = new OnClickListener(this) {
                final /* synthetic */ Snackbar this$0;

                {
                    Snackbar this$02 = r6;
                    OnClickListener onClickListener = r7;
                    Snackbar snackbar = this$02;
                    this.this$0 = this$02;
                }

                public void onClick(View view) {
                    View view2 = view;
                    View view3 = view2;
                    onClickListener3.onClick(view2);
                    this.this$0.dispatchDismiss(1);
                }
            };
            actionView.setOnClickListener(r0);
        } else {
            actionView.setVisibility(8);
            actionView.setOnClickListener(null);
        }
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(ColorStateList colorStateList) {
        ColorStateList colors = colorStateList;
        ColorStateList colorStateList2 = colors;
        SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) this.mView.getChildAt(0);
        SnackbarContentLayout snackbarContentLayout2 = snackbarContentLayout;
        Button actionView = snackbarContentLayout.getActionView();
        Button button = actionView;
        actionView.setTextColor(colors);
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) this.mView.getChildAt(0);
        SnackbarContentLayout snackbarContentLayout2 = snackbarContentLayout;
        Button actionView = snackbarContentLayout.getActionView();
        Button button = actionView;
        actionView.setTextColor(color);
        return this;
    }

    @Deprecated
    @NonNull
    public Snackbar setCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        if (this.mCallback != null) {
            BaseTransientBottomBar removeCallback = removeCallback(this.mCallback);
        }
        if (callback2 != null) {
            BaseTransientBottomBar addCallback = addCallback(callback2);
        }
        this.mCallback = callback2;
        return this;
    }
}
