package android.support.p003v7.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.ActionMode.Callback;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/* renamed from: android.support.v7.app.AppCompatDialog */
public class AppCompatDialog extends Dialog implements AppCompatCallback {
    private AppCompatDelegate mDelegate;

    public AppCompatDialog(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, 0);
    }

    public AppCompatDialog(Context context, int i) {
        Context context2 = context;
        int theme = i;
        Context context3 = context2;
        int i2 = theme;
        super(context2, getThemeResId(context2, theme));
        getDelegate().onCreate(null);
        boolean applyDayNight = getDelegate().applyDayNight();
    }

    protected AppCompatDialog(Context context, boolean z, OnCancelListener onCancelListener) {
        Context context2 = context;
        OnCancelListener cancelListener = onCancelListener;
        Context context3 = context2;
        OnCancelListener onCancelListener2 = cancelListener;
        super(context2, z, cancelListener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        getDelegate().installViewFactory();
        super.onCreate(savedInstanceState);
        getDelegate().onCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setContentView(@LayoutRes int i) {
        int layoutResID = i;
        int i2 = layoutResID;
        getDelegate().setContentView(layoutResID);
    }

    public void setContentView(View view) {
        View view2 = view;
        View view3 = view2;
        getDelegate().setContentView(view2);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams params = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams2 = params;
        getDelegate().setContentView(view2, params);
    }

    @Nullable
    public View findViewById(@IdRes int i) {
        int id = i;
        int i2 = id;
        return getDelegate().findViewById(id);
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        super.setTitle(title);
        getDelegate().setTitle(title);
    }

    public void setTitle(int i) {
        int titleId = i;
        int i2 = titleId;
        super.setTitle(titleId);
        getDelegate().setTitle(getContext().getString(titleId));
    }

    public void addContentView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams params = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams2 = params;
        getDelegate().addContentView(view2, params);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    public boolean supportRequestWindowFeature(int i) {
        int featureId = i;
        int i2 = featureId;
        return getDelegate().requestWindowFeature(featureId);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    public AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create((Dialog) this, (AppCompatCallback) this);
        }
        return this.mDelegate;
    }

    private static int getThemeResId(Context context, int i) {
        Context context2 = context;
        int themeId = i;
        Context context3 = context2;
        int themeId2 = themeId;
        if (themeId == 0) {
            TypedValue outValue = new TypedValue();
            boolean resolveAttribute = context2.getTheme().resolveAttribute(C0268R.attr.dialogTheme, outValue, true);
            themeId2 = outValue.resourceId;
        }
        return themeId2;
    }

    public void onSupportActionModeStarted(ActionMode actionMode) {
        ActionMode actionMode2 = actionMode;
    }

    public void onSupportActionModeFinished(ActionMode actionMode) {
        ActionMode actionMode2 = actionMode;
    }

    @Nullable
    public ActionMode onWindowStartingSupportActionMode(Callback callback) {
        Callback callback2 = callback;
        return null;
    }
}
