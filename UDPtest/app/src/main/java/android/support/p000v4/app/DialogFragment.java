package android.support.p000v4.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;

/* renamed from: android.support.v4.app.DialogFragment */
public class DialogFragment extends Fragment implements OnCancelListener, OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    int mBackStackId = -1;
    boolean mCancelable = true;
    Dialog mDialog;
    boolean mDismissed;
    boolean mShownByMe;
    boolean mShowsDialog = true;
    int mStyle = 0;
    int mTheme = 0;
    boolean mViewDestroyed;

    public DialogFragment() {
    }

    public void setStyle(int i, @StyleRes int i2) {
        int style = i;
        int theme = i2;
        int i3 = style;
        int i4 = theme;
        this.mStyle = style;
        if (this.mStyle == 2 || this.mStyle == 3) {
            this.mTheme = 16973913;
        }
        if (theme != 0) {
            this.mTheme = theme;
        }
    }

    public void show(FragmentManager fragmentManager, String str) {
        FragmentManager manager = fragmentManager;
        String tag = str;
        FragmentManager fragmentManager2 = manager;
        String str2 = tag;
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction beginTransaction = manager.beginTransaction();
        FragmentTransaction ft = beginTransaction;
        FragmentTransaction add = beginTransaction.add((Fragment) this, tag);
        int commit = ft.commit();
    }

    public int show(FragmentTransaction fragmentTransaction, String str) {
        FragmentTransaction transaction = fragmentTransaction;
        String tag = str;
        FragmentTransaction fragmentTransaction2 = transaction;
        String str2 = tag;
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction add = transaction.add((Fragment) this, tag);
        this.mViewDestroyed = false;
        this.mBackStackId = transaction.commit();
        return this.mBackStackId;
    }

    public void dismiss() {
        dismissInternal(false);
    }

    public void dismissAllowingStateLoss() {
        dismissInternal(true);
    }

    /* access modifiers changed from: 0000 */
    public void dismissInternal(boolean z) {
        boolean allowStateLoss = z;
        if (!this.mDismissed) {
            this.mDismissed = true;
            this.mShownByMe = false;
            if (this.mDialog != null) {
                this.mDialog.dismiss();
                this.mDialog = null;
            }
            this.mViewDestroyed = true;
            if (this.mBackStackId < 0) {
                FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
                FragmentTransaction ft = beginTransaction;
                FragmentTransaction remove = beginTransaction.remove(this);
                if (!allowStateLoss) {
                    int commit = ft.commit();
                } else {
                    int commitAllowingStateLoss = ft.commitAllowingStateLoss();
                }
            } else {
                getFragmentManager().popBackStack(this.mBackStackId, 1);
                this.mBackStackId = -1;
            }
        }
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    @StyleRes
    public int getTheme() {
        return this.mTheme;
    }

    public void setCancelable(boolean z) {
        boolean cancelable = z;
        this.mCancelable = cancelable;
        if (this.mDialog != null) {
            this.mDialog.setCancelable(cancelable);
        }
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    public void setShowsDialog(boolean z) {
        this.mShowsDialog = z;
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    public void onAttach(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super.onAttach(context2);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    public void onDetach() {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        super.onCreate(savedInstanceState);
        this.mShowsDialog = this.mContainerId == 0;
        if (savedInstanceState != null) {
            this.mStyle = savedInstanceState.getInt(SAVED_STYLE, 0);
            this.mTheme = savedInstanceState.getInt(SAVED_THEME, 0);
            this.mCancelable = savedInstanceState.getBoolean(SAVED_CANCELABLE, true);
            this.mShowsDialog = savedInstanceState.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
            this.mBackStackId = savedInstanceState.getInt(SAVED_BACK_STACK_ID, -1);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public LayoutInflater getLayoutInflater(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        if (!this.mShowsDialog) {
            return super.getLayoutInflater(savedInstanceState);
        }
        this.mDialog = onCreateDialog(savedInstanceState);
        if (this.mDialog == null) {
            return (LayoutInflater) this.mHost.getContext().getSystemService("layout_inflater");
        }
        setupDialog(this.mDialog, this.mStyle);
        return (LayoutInflater) this.mDialog.getContext().getSystemService("layout_inflater");
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setupDialog(Dialog dialog, int i) {
        Dialog dialog2 = dialog;
        int style = i;
        Dialog dialog3 = dialog2;
        int i2 = style;
        switch (style) {
            case 1:
            case 2:
                break;
            case 3:
                dialog2.getWindow().addFlags(24);
                break;
            default:
                return;
        }
        boolean requestWindowFeature = dialog2.requestWindowFeature(1);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        Bundle bundle2 = bundle;
        return new Dialog(getActivity(), getTheme());
    }

    public void onCancel(DialogInterface dialogInterface) {
        DialogInterface dialogInterface2 = dialogInterface;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        DialogInterface dialogInterface2 = dialogInterface;
        if (!this.mViewDestroyed) {
            dismissInternal(true);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        super.onActivityCreated(savedInstanceState);
        if (this.mShowsDialog) {
            View view = getView();
            View view2 = view;
            if (view != null) {
                if (view2.getParent() == null) {
                    this.mDialog.setContentView(view2);
                } else {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                }
            }
            FragmentActivity activity = getActivity();
            FragmentActivity fragmentActivity = activity;
            if (activity != null) {
                this.mDialog.setOwnerActivity(fragmentActivity);
            }
            this.mDialog.setCancelable(this.mCancelable);
            this.mDialog.setOnCancelListener(this);
            this.mDialog.setOnDismissListener(this);
            if (savedInstanceState != null) {
                Bundle bundle3 = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG);
                Bundle dialogState = bundle3;
                if (bundle3 != null) {
                    this.mDialog.onRestoreInstanceState(dialogState);
                }
            }
        }
    }

    public void onStart() {
        super.onStart();
        if (this.mDialog != null) {
            this.mViewDestroyed = false;
            this.mDialog.show();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        super.onSaveInstanceState(outState);
        if (this.mDialog != null) {
            Bundle onSaveInstanceState = this.mDialog.onSaveInstanceState();
            Bundle dialogState = onSaveInstanceState;
            if (onSaveInstanceState != null) {
                outState.putBundle(SAVED_DIALOG_STATE_TAG, dialogState);
            }
        }
        if (this.mStyle != 0) {
            outState.putInt(SAVED_STYLE, this.mStyle);
        }
        if (this.mTheme != 0) {
            outState.putInt(SAVED_THEME, this.mTheme);
        }
        if (!this.mCancelable) {
            outState.putBoolean(SAVED_CANCELABLE, this.mCancelable);
        }
        if (!this.mShowsDialog) {
            outState.putBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
        }
        if (this.mBackStackId != -1) {
            outState.putInt(SAVED_BACK_STACK_ID, this.mBackStackId);
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mDialog != null) {
            this.mDialog.hide();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mDialog != null) {
            this.mViewDestroyed = true;
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }
}
