package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.C0001R;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p003v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class BottomSheetDialog extends AppCompatDialog {
    private BottomSheetBehavior<FrameLayout> mBehavior;
    private BottomSheetCallback mBottomSheetCallback;
    boolean mCancelable;
    private boolean mCanceledOnTouchOutside;
    private boolean mCanceledOnTouchOutsideSet;

    public BottomSheetDialog(@NonNull Context context, @StyleRes int i) {
        Context context2 = context;
        int theme = i;
        Context context3 = context2;
        int i2 = theme;
        super(context2, getThemeResId(context2, theme));
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mBottomSheetCallback = new BottomSheetCallback(this) {
            final /* synthetic */ BottomSheetDialog this$0;

            {
                BottomSheetDialog this$02 = r5;
                BottomSheetDialog bottomSheetDialog = this$02;
                this.this$0 = this$02;
            }

            public void onStateChanged(@NonNull View view, int i) {
                int newState = i;
                View view2 = view;
                int i2 = newState;
                if (newState == 5) {
                    this.this$0.cancel();
                }
            }

            public void onSlide(@NonNull View view, float f) {
                View view2 = view;
                float f2 = f;
            }
        };
        boolean supportRequestWindowFeature = supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean z, OnCancelListener onCancelListener) {
        Context context2 = context;
        OnCancelListener cancelListener = onCancelListener;
        Context context3 = context2;
        boolean cancelable = z;
        OnCancelListener onCancelListener2 = cancelListener;
        super(context2, cancelable, cancelListener);
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mBottomSheetCallback = new BottomSheetCallback(this) {
            final /* synthetic */ BottomSheetDialog this$0;

            {
                BottomSheetDialog this$02 = r5;
                BottomSheetDialog bottomSheetDialog = this$02;
                this.this$0 = this$02;
            }

            public void onStateChanged(@NonNull View view, int i) {
                int newState = i;
                View view2 = view;
                int i2 = newState;
                if (newState == 5) {
                    this.this$0.cancel();
                }
            }

            public void onSlide(@NonNull View view, float f) {
                View view2 = view;
                float f2 = f;
            }
        };
        boolean supportRequestWindowFeature = supportRequestWindowFeature(1);
        this.mCancelable = cancelable;
    }

    public BottomSheetDialog(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, 0);
    }

    public void setContentView(@LayoutRes int i) {
        int layoutResId = i;
        int i2 = layoutResId;
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        super.onCreate(savedInstanceState);
        getWindow().setLayout(-1, -1);
    }

    public void setContentView(View view) {
        View view2 = view;
        View view3 = view2;
        super.setContentView(wrapInBottomSheet(0, view2, null));
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams params = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams2 = params;
        super.setContentView(wrapInBottomSheet(0, view2, params));
    }

    public void setCancelable(boolean z) {
        boolean cancelable = z;
        super.setCancelable(cancelable);
        if (this.mCancelable != cancelable) {
            this.mCancelable = cancelable;
            if (this.mBehavior != null) {
                this.mBehavior.setHideable(cancelable);
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        boolean cancel = z;
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mCanceledOnTouchOutside = cancel;
        this.mCanceledOnTouchOutsideSet = true;
    }

    private View wrapInBottomSheet(int i, View view, LayoutParams layoutParams) {
        int layoutResId = i;
        View view2 = view;
        LayoutParams params = layoutParams;
        int i2 = layoutResId;
        View view3 = view2;
        LayoutParams layoutParams2 = params;
        CoordinatorLayout coordinator = (CoordinatorLayout) View.inflate(getContext(), C0001R.layout.design_bottom_sheet_dialog, null);
        if (layoutResId != 0 && view2 == null) {
            view3 = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        FrameLayout bottomSheet = (FrameLayout) coordinator.findViewById(C0001R.C0003id.design_bottom_sheet);
        this.mBehavior = BottomSheetBehavior.from(bottomSheet);
        this.mBehavior.setBottomSheetCallback(this.mBottomSheetCallback);
        this.mBehavior.setHideable(this.mCancelable);
        if (params != null) {
            bottomSheet.addView(view3, params);
        } else {
            bottomSheet.addView(view3);
        }
        View findViewById = coordinator.findViewById(C0001R.C0003id.touch_outside);
        C00281 r0 = new OnClickListener(this) {
            final /* synthetic */ BottomSheetDialog this$0;

            {
                BottomSheetDialog this$02 = r5;
                BottomSheetDialog bottomSheetDialog = this$02;
                this.this$0 = this$02;
            }

            public void onClick(View view) {
                View view2 = view;
                if (this.this$0.mCancelable && this.this$0.isShowing() && this.this$0.shouldWindowCloseOnTouchOutside()) {
                    this.this$0.cancel();
                }
            }
        };
        findViewById.setOnClickListener(r0);
        C00292 r02 = new AccessibilityDelegateCompat(this) {
            final /* synthetic */ BottomSheetDialog this$0;

            {
                BottomSheetDialog this$02 = r5;
                BottomSheetDialog bottomSheetDialog = this$02;
                this.this$0 = this$02;
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                View host = view;
                AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
                View view2 = host;
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (!this.this$0.mCancelable) {
                    info.setDismissable(false);
                    return;
                }
                info.addAction(1048576);
                info.setDismissable(true);
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                View host = view;
                int action = i;
                Bundle args = bundle;
                View view2 = host;
                int i2 = action;
                Bundle bundle2 = args;
                if (action != 1048576 || !this.this$0.mCancelable) {
                    return super.performAccessibilityAction(host, action, args);
                }
                this.this$0.cancel();
                return true;
            }
        };
        ViewCompat.setAccessibilityDelegate(bottomSheet, r02);
        return coordinator;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldWindowCloseOnTouchOutside() {
        if (!this.mCanceledOnTouchOutsideSet) {
            if (VERSION.SDK_INT >= 11) {
                TypedArray a = getContext().obtainStyledAttributes(new int[]{16843611});
                this.mCanceledOnTouchOutside = a.getBoolean(0, true);
                a.recycle();
            } else {
                this.mCanceledOnTouchOutside = true;
            }
            this.mCanceledOnTouchOutsideSet = true;
        }
        return this.mCanceledOnTouchOutside;
    }

    private static int getThemeResId(Context context, int i) {
        Context context2 = context;
        int themeId = i;
        Context context3 = context2;
        int themeId2 = themeId;
        if (themeId == 0) {
            TypedValue outValue = new TypedValue();
            if (!context2.getTheme().resolveAttribute(C0001R.attr.bottomSheetDialogTheme, outValue, true)) {
                themeId2 = C0001R.style.Theme_Design_Light_BottomSheetDialog;
            } else {
                themeId2 = outValue.resourceId;
            }
        }
        return themeId2;
    }
}
