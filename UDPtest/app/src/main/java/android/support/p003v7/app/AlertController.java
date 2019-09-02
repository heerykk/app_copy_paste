package android.support.p003v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.NestedScrollView;
import android.support.p000v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.p003v7.appcompat.C0268R;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* renamed from: android.support.v7.app.AlertController */
class AlertController {
    ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final OnClickListener mButtonHandler = new OnClickListener(this) {
        final /* synthetic */ AlertController this$0;

        {
            AlertController this$02 = r5;
            AlertController alertController = this$02;
            this.this$0 = this$02;
        }

        public void onClick(View view) {
            Message m;
            View v = view;
            View view2 = v;
            if (v == this.this$0.mButtonPositive && this.this$0.mButtonPositiveMessage != null) {
                m = Message.obtain(this.this$0.mButtonPositiveMessage);
            } else if (v == this.this$0.mButtonNegative && this.this$0.mButtonNegativeMessage != null) {
                m = Message.obtain(this.this$0.mButtonNegativeMessage);
            } else if (v == this.this$0.mButtonNeutral && this.this$0.mButtonNeutralMessage != null) {
                m = Message.obtain(this.this$0.mButtonNeutralMessage);
            } else {
                m = null;
            }
            if (m != null) {
                m.sendToTarget();
            }
            this.this$0.mHandler.obtainMessage(1, this.this$0.mDialog).sendToTarget();
        }
    };
    Button mButtonNegative;
    Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    Button mButtonNeutral;
    Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    Button mButtonPositive;
    Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    int mCheckedItem = -1;
    private final Context mContext;
    private View mCustomTitleView;
    final AppCompatDialog mDialog;
    Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    int mListItemLayout;
    int mListLayout;
    ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    int mMultiChoiceItemLayout;
    NestedScrollView mScrollView;
    private boolean mShowTitle;
    int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    private final Window mWindow;

    /* renamed from: android.support.v7.app.AlertController$AlertParams */
    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean mCancelable;
        public int mCheckedItem = -1;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public int mIconAttrId = 0;
        public int mIconId = 0;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public OnCancelListener mOnCancelListener;
        public OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public OnDismissListener mOnDismissListener;
        public OnItemSelectedListener mOnItemSelectedListener;
        public OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public boolean mRecycleOnMeasure = true;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        /* renamed from: android.support.v7.app.AlertController$AlertParams$OnPrepareListViewListener */
        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mContext = context2;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater) context2.getSystemService("layout_inflater");
        }

        public void apply(AlertController alertController) {
            AlertController dialog = alertController;
            AlertController alertController2 = dialog;
            if (this.mCustomTitleView == null) {
                if (this.mTitle != null) {
                    dialog.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    dialog.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    dialog.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    dialog.setIcon(dialog.getIconAttributeResId(this.mIconAttrId));
                }
            } else {
                dialog.setCustomTitle(this.mCustomTitleView);
            }
            if (this.mMessage != null) {
                dialog.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                dialog.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null) {
                dialog.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null) {
                dialog.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null);
            }
            if (!(this.mItems == null && this.mCursor == null && this.mAdapter == null)) {
                createListView(dialog);
            }
            if (this.mView == null) {
                if (this.mViewLayoutResId != 0) {
                    dialog.setView(this.mViewLayoutResId);
                }
            } else if (!this.mViewSpacingSpecified) {
                dialog.setView(this.mView);
            } else {
                dialog.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
        }

        /* JADX WARNING: type inference failed for: r28v0 */
        /* JADX WARNING: type inference failed for: r28v1 */
        /* JADX WARNING: type inference failed for: r28v2 */
        /* JADX WARNING: type inference failed for: r0v10, types: [android.widget.ListAdapter] */
        /* JADX WARNING: type inference failed for: r28v3 */
        /* JADX WARNING: type inference failed for: r11v14, types: [android.widget.ListAdapter] */
        /* JADX WARNING: type inference failed for: r28v4 */
        /* JADX WARNING: type inference failed for: r11v15, types: [android.support.v7.app.AlertController$CheckedItemAdapter] */
        /* JADX WARNING: type inference failed for: r28v5 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 4 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void createListView(android.support.p003v7.app.AlertController r65) {
            /*
                r64 = this;
                r7 = r64
                r8 = r65
                r9 = r7
                r10 = r8
                android.view.LayoutInflater r11 = r7.mInflater
                int r12 = r8.mListLayout
                r13 = 0
                r15 = 0
                r14 = r15
                android.view.View r11 = r11.inflate(r12, r14)
                android.support.v7.app.AlertController$RecycleListView r11 = (android.support.p003v7.app.AlertController.RecycleListView) r11
                r16 = r11
                boolean r0 = r7.mIsMultiChoice
                r17 = r0
                r18 = r17
                r19 = 0
                r0 = r18
                r1 = r19
                if (r0 != r1) goto L_0x009c
                boolean r0 = r7.mIsSingleChoice
                r36 = r0
                r18 = r36
                r19 = 0
                r0 = r18
                r1 = r19
                if (r0 != r1) goto L_0x00f7
                int r0 = r8.mListItemLayout
                r18 = r0
                r37 = r18
            L_0x0037:
                android.database.Cursor r11 = r7.mCursor
                r15 = 0
                if (r11 != r15) goto L_0x00ff
                android.widget.ListAdapter r11 = r7.mAdapter
                r15 = 0
                if (r11 != r15) goto L_0x0146
                android.support.v7.app.AlertController$CheckedItemAdapter r11 = new android.support.v7.app.AlertController$CheckedItemAdapter
                android.content.Context r13 = r7.mContext
                r38 = r37
                r21 = 16908308(0x1020014, float:2.3877285E-38)
                java.lang.CharSequence[] r0 = r7.mItems
                r39 = r0
                r50 = r13
                r51 = r39
                r0 = r50
                r1 = r38
                r2 = r21
                r3 = r51
                r11.<init>(r0, r1, r2, r3)
                r28 = r11
            L_0x005f:
                android.support.v7.app.AlertController$AlertParams$OnPrepareListViewListener r11 = r7.mOnPrepareListViewListener
                r15 = 0
                if (r11 != r15) goto L_0x014c
            L_0x0064:
                r0 = r28
                r8.mAdapter = r0
                int r12 = r7.mCheckedItem
                r8.mCheckedItem = r12
                android.content.DialogInterface$OnClickListener r11 = r7.mOnClickListener
                r15 = 0
                if (r11 != r15) goto L_0x0157
                android.content.DialogInterface$OnMultiChoiceClickListener r11 = r7.mOnCheckboxClickListener
                r15 = 0
                if (r11 != r15) goto L_0x016b
            L_0x0076:
                android.widget.AdapterView$OnItemSelectedListener r11 = r7.mOnItemSelectedListener
                r15 = 0
                if (r11 != r15) goto L_0x0189
            L_0x007b:
                boolean r0 = r7.mIsSingleChoice
                r62 = r0
                r18 = r62
                r19 = 0
                r0 = r18
                r1 = r19
                if (r0 != r1) goto L_0x0198
                boolean r0 = r7.mIsMultiChoice
                r63 = r0
                r18 = r63
                r19 = 0
                r0 = r18
                r1 = r19
                if (r0 != r1) goto L_0x01a0
            L_0x0097:
                r0 = r16
                r8.mListView = r0
                return
            L_0x009c:
                android.database.Cursor r11 = r7.mCursor
                r15 = 0
                if (r11 == r15) goto L_0x00ca
                android.support.v7.app.AlertController$AlertParams$2 r11 = new android.support.v7.app.AlertController$AlertParams$2
                android.content.Context r0 = r7.mContext
                r20 = r0
                android.database.Cursor r0 = r7.mCursor
                r29 = r0
                r30 = r7
                r31 = r20
                r32 = r29
                r33 = 0
                r34 = r16
                r35 = r8
                r0 = r11
                r1 = r30
                r2 = r31
                r3 = r32
                r4 = r33
                r5 = r34
                r6 = r35
                r0.<init>(r1, r2, r3, r4, r5, r6)
                r28 = r11
                goto L_0x005f
            L_0x00ca:
                android.support.v7.app.AlertController$AlertParams$1 r11 = new android.support.v7.app.AlertController$AlertParams$1
                android.content.Context r0 = r7.mContext
                r20 = r0
                int r0 = r8.mMultiChoiceItemLayout
                r21 = r0
                r22 = 16908308(0x1020014, float:2.3877285E-38)
                java.lang.CharSequence[] r0 = r7.mItems
                r23 = r0
                r24 = r7
                r25 = r20
                r26 = r23
                r27 = r16
                r0 = r11
                r1 = r24
                r2 = r25
                r3 = r21
                r4 = r22
                r5 = r26
                r6 = r27
                r0.<init>(r1, r2, r3, r4, r5, r6)
                r28 = r11
                goto L_0x005f
            L_0x00f7:
                int r0 = r8.mSingleChoiceItemLayout
                r18 = r0
                r37 = r18
                goto L_0x0037
            L_0x00ff:
                android.widget.SimpleCursorAdapter r11 = new android.widget.SimpleCursorAdapter
                android.content.Context r13 = r7.mContext
                r38 = r37
                android.database.Cursor r0 = r7.mCursor
                r29 = r0
                r22 = 1
                r0 = r22
                java.lang.String[] r0 = new java.lang.String[r0]
                r39 = r0
                r40 = 0
                java.lang.String r0 = r7.mLabelColumn
                r41 = r0
                r42 = r39
                r42[r40] = r41
                r43 = 1
                r0 = r43
                int[] r0 = new int[r0]
                r23 = r0
                r44 = 0
                r45 = r23
                r19 = 16908308(0x1020014, float:2.3877285E-38)
                r45[r44] = r19
                r46 = r13
                r47 = r29
                r48 = r39
                r49 = r23
                r0 = r11
                r1 = r46
                r2 = r38
                r3 = r47
                r4 = r48
                r5 = r49
                r0.<init>(r1, r2, r3, r4, r5)
                r28 = r11
                goto L_0x005f
            L_0x0146:
                android.widget.ListAdapter r11 = r7.mAdapter
                r28 = r11
                goto L_0x005f
            L_0x014c:
                android.support.v7.app.AlertController$AlertParams$OnPrepareListViewListener r11 = r7.mOnPrepareListViewListener
                r52 = r16
                r0 = r52
                r11.onPrepareListView(r0)
                goto L_0x0064
            L_0x0157:
                android.support.v7.app.AlertController$AlertParams$3 r53 = new android.support.v7.app.AlertController$AlertParams$3
                r54 = r7
                r55 = r8
                r53.<init>(r54, r55)
                r56 = r53
                r0 = r16
                r1 = r56
                r0.setOnItemClickListener(r1)
                goto L_0x0076
            L_0x016b:
                android.support.v7.app.AlertController$AlertParams$4 r53 = new android.support.v7.app.AlertController$AlertParams$4
                r57 = r7
                r58 = r16
                r59 = r8
                r0 = r53
                r1 = r57
                r2 = r58
                r3 = r59
                r0.<init>(r1, r2, r3)
                r60 = r53
                r0 = r16
                r1 = r60
                r0.setOnItemClickListener(r1)
                goto L_0x0076
            L_0x0189:
                android.widget.AdapterView$OnItemSelectedListener r0 = r7.mOnItemSelectedListener
                r53 = r0
                r61 = r53
                r0 = r16
                r1 = r61
                r0.setOnItemSelectedListener(r1)
                goto L_0x007b
            L_0x0198:
                r12 = 1
                r0 = r16
                r0.setChoiceMode(r12)
                goto L_0x0097
            L_0x01a0:
                r12 = 2
                r0 = r16
                r0.setChoiceMode(r12)
                goto L_0x0097
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.app.AlertController.AlertParams.createListView(android.support.v7.app.AlertController):void");
        }
    }

    /* renamed from: android.support.v7.app.AlertController$ButtonHandler */
    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            DialogInterface dialog = dialogInterface;
            DialogInterface dialogInterface2 = dialog;
            this.mDialog = new WeakReference<>(dialog);
        }

        public void handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            switch (msg.what) {
                case -3:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) msg.obj).onClick((DialogInterface) this.mDialog.get(), msg.what);
                    return;
                case 1:
                    ((DialogInterface) msg.obj).dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: android.support.v7.app.AlertController$CheckedItemAdapter */
    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            Context context2 = context;
            int resource = i;
            int textViewResourceId = i2;
            CharSequence[] objects = charSequenceArr;
            Context context3 = context2;
            int i3 = resource;
            int i4 = textViewResourceId;
            CharSequence[] charSequenceArr2 = objects;
            super(context2, resource, textViewResourceId, objects);
        }

        public boolean hasStableIds() {
            return true;
        }

        public long getItemId(int i) {
            int position = i;
            int i2 = position;
            return (long) position;
        }
    }

    /* renamed from: android.support.v7.app.AlertController$RecycleListView */
    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this(context2, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TypedArray ta = context2.obtainStyledAttributes(attrs, C0268R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = ta.getDimensionPixelOffset(C0268R.styleable.RecycleListView_paddingBottomNoButtons, -1);
            this.mPaddingTopNoTitle = ta.getDimensionPixelOffset(C0268R.styleable.RecycleListView_paddingTopNoTitle, -1);
        }

        public void setHasDecor(boolean z, boolean z2) {
            boolean hasTitle = z;
            boolean hasButtons = z2;
            if (!hasButtons || !hasTitle) {
                setPadding(getPaddingLeft(), !hasTitle ? this.mPaddingTopNoTitle : getPaddingTop(), getPaddingRight(), !hasButtons ? this.mPaddingBottomNoButtons : getPaddingBottom());
            }
        }
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        Context context2 = context;
        AppCompatDialog di = appCompatDialog;
        Window window2 = window;
        Context context3 = context2;
        AppCompatDialog appCompatDialog2 = di;
        Window window3 = window2;
        this.mContext = context2;
        this.mDialog = di;
        this.mWindow = window2;
        this.mHandler = new ButtonHandler(di);
        TypedArray a = context2.obtainStyledAttributes(null, C0268R.styleable.AlertDialog, C0268R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = a.getResourceId(C0268R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = a.getResourceId(C0268R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.mListLayout = a.getResourceId(C0268R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = a.getResourceId(C0268R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = a.getResourceId(C0268R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = a.getResourceId(C0268R.styleable.AlertDialog_listItemLayout, 0);
        this.mShowTitle = a.getBoolean(C0268R.styleable.AlertDialog_showTitle, true);
        a.recycle();
        boolean supportRequestWindowFeature = di.supportRequestWindowFeature(1);
    }

    private static boolean shouldCenterSingleButton(Context context) {
        Context context2 = context;
        Context context3 = context2;
        TypedValue outValue = new TypedValue();
        boolean resolveAttribute = context2.getTheme().resolveAttribute(C0268R.attr.alertDialogCenterButtons, outValue, true);
        return outValue.data != 0;
    }

    static boolean canTextInput(View view) {
        View v = view;
        View view2 = v;
        if (v.onCheckIsTextEditor()) {
            return true;
        }
        if (!(v instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) v;
        ViewGroup vg = viewGroup;
        int i = viewGroup.getChildCount();
        while (i > 0) {
            i--;
            View childAt = vg.getChildAt(i);
            View v2 = childAt;
            if (canTextInput(childAt)) {
                return true;
            }
        }
        return false;
    }

    public void installContent() {
        int selectContentView = selectContentView();
        int i = selectContentView;
        this.mDialog.setContentView(selectContentView);
        setupView();
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint != 1) {
            return this.mAlertDialogLayout;
        }
        return this.mButtonPanelSideLayout;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        if (this.mTitleView != null) {
            this.mTitleView.setText(title);
        }
    }

    public void setCustomTitle(View view) {
        View customTitleView = view;
        View view2 = customTitleView;
        this.mCustomTitleView = customTitleView;
    }

    public void setMessage(CharSequence charSequence) {
        CharSequence message = charSequence;
        CharSequence charSequence2 = message;
        this.mMessage = message;
        if (this.mMessageView != null) {
            this.mMessageView.setText(message);
        }
    }

    public void setView(int i) {
        int layoutResId = i;
        int i2 = layoutResId;
        this.mView = null;
        this.mViewLayoutResId = layoutResId;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        View view2 = view;
        View view3 = view2;
        this.mView = view2;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        View view2 = view;
        int viewSpacingLeft = i;
        int viewSpacingTop = i2;
        int viewSpacingRight = i3;
        int viewSpacingBottom = i4;
        View view3 = view2;
        int i5 = viewSpacingLeft;
        int i6 = viewSpacingTop;
        int i7 = viewSpacingRight;
        int i8 = viewSpacingBottom;
        this.mView = view2;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = viewSpacingLeft;
        this.mViewSpacingTop = viewSpacingTop;
        this.mViewSpacingRight = viewSpacingRight;
        this.mViewSpacingBottom = viewSpacingBottom;
    }

    public void setButtonPanelLayoutHint(int i) {
        int layoutHint = i;
        int i2 = layoutHint;
        this.mButtonPanelLayoutHint = layoutHint;
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        int whichButton = i;
        CharSequence text = charSequence;
        DialogInterface.OnClickListener listener = onClickListener;
        Message msg = message;
        int i2 = whichButton;
        CharSequence charSequence2 = text;
        DialogInterface.OnClickListener onClickListener2 = listener;
        Message msg2 = msg;
        if (msg == null && listener != null) {
            msg2 = this.mHandler.obtainMessage(whichButton, listener);
        }
        switch (whichButton) {
            case -3:
                this.mButtonNeutralText = text;
                this.mButtonNeutralMessage = msg2;
                return;
            case -2:
                this.mButtonNegativeText = text;
                this.mButtonNegativeMessage = msg2;
                return;
            case -1:
                this.mButtonPositiveText = text;
                this.mButtonPositiveMessage = msg2;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setIcon(int i) {
        int resId = i;
        int i2 = resId;
        this.mIcon = null;
        this.mIconId = resId;
        if (this.mIconView != null) {
            if (resId == 0) {
                this.mIconView.setVisibility(8);
                return;
            }
            this.mIconView.setVisibility(0);
            this.mIconView.setImageResource(this.mIconId);
        }
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mIcon = icon;
        this.mIconId = 0;
        if (this.mIconView != null) {
            if (icon == null) {
                this.mIconView.setVisibility(8);
                return;
            }
            this.mIconView.setVisibility(0);
            this.mIconView.setImageDrawable(icon);
        }
    }

    public int getIconAttributeResId(int i) {
        int attrId = i;
        int i2 = attrId;
        TypedValue out = new TypedValue();
        boolean resolveAttribute = this.mContext.getTheme().resolveAttribute(attrId, out, true);
        return out.resourceId;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public Button getButton(int i) {
        int whichButton = i;
        int i2 = whichButton;
        switch (whichButton) {
            case -3:
                return this.mButtonNeutral;
            case -2:
                return this.mButtonNegative;
            case -1:
                return this.mButtonPositive;
            default:
                return null;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        int i2 = i;
        KeyEvent keyEvent2 = event;
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(event);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        int i2 = i;
        KeyEvent keyEvent2 = event;
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(event);
    }

    @Nullable
    private ViewGroup resolvePanel(@Nullable View view, @Nullable View view2) {
        View customPanel = view;
        View defaultPanel = view2;
        View customPanel2 = customPanel;
        View defaultPanel2 = defaultPanel;
        if (customPanel != null) {
            if (defaultPanel != null) {
                ViewParent parent = defaultPanel.getParent();
                ViewParent parent2 = parent;
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(defaultPanel);
                }
            }
            if (customPanel instanceof ViewStub) {
                customPanel2 = ((ViewStub) customPanel).inflate();
            }
            return (ViewGroup) customPanel2;
        }
        if (defaultPanel instanceof ViewStub) {
            defaultPanel2 = ((ViewStub) defaultPanel).inflate();
        }
        return (ViewGroup) defaultPanel2;
    }

    private void setupView() {
        View findViewById = this.mWindow.findViewById(C0268R.C0270id.parentPanel);
        View parentPanel = findViewById;
        View defaultTopPanel = findViewById.findViewById(C0268R.C0270id.topPanel);
        View defaultContentPanel = parentPanel.findViewById(C0268R.C0270id.contentPanel);
        View defaultButtonPanel = parentPanel.findViewById(C0268R.C0270id.buttonPanel);
        ViewGroup customPanel = (ViewGroup) parentPanel.findViewById(C0268R.C0270id.customPanel);
        setupCustomContent(customPanel);
        View customTopPanel = customPanel.findViewById(C0268R.C0270id.topPanel);
        View customContentPanel = customPanel.findViewById(C0268R.C0270id.contentPanel);
        View customButtonPanel = customPanel.findViewById(C0268R.C0270id.buttonPanel);
        ViewGroup topPanel = resolvePanel(customTopPanel, defaultTopPanel);
        ViewGroup contentPanel = resolvePanel(customContentPanel, defaultContentPanel);
        ViewGroup buttonPanel = resolvePanel(customButtonPanel, defaultButtonPanel);
        setupContent(contentPanel);
        setupButtons(buttonPanel);
        setupTitle(topPanel);
        boolean hasCustomPanel = (customPanel == null || customPanel.getVisibility() == 8) ? false : true;
        boolean hasTopPanel = (topPanel == null || topPanel.getVisibility() == 8) ? false : true;
        boolean hasButtonPanel = (buttonPanel == null || buttonPanel.getVisibility() == 8) ? false : true;
        if (!hasButtonPanel && contentPanel != null) {
            View findViewById2 = contentPanel.findViewById(C0268R.C0270id.textSpacerNoButtons);
            View spacer = findViewById2;
            if (findViewById2 != null) {
                spacer.setVisibility(0);
            }
        }
        if (hasTopPanel) {
            if (this.mScrollView != null) {
                this.mScrollView.setClipToPadding(true);
            }
            View divider = null;
            if (!(this.mMessage == null && this.mListView == null && !hasCustomPanel) && !hasCustomPanel) {
                divider = topPanel.findViewById(C0268R.C0270id.titleDividerNoCustom);
            }
            if (divider != null) {
                divider.setVisibility(0);
            }
        } else if (contentPanel != null) {
            View findViewById3 = contentPanel.findViewById(C0268R.C0270id.textSpacerNoTitle);
            View spacer2 = findViewById3;
            if (findViewById3 != null) {
                spacer2.setVisibility(0);
            }
        }
        if (this.mListView instanceof RecycleListView) {
            ((RecycleListView) this.mListView).setHasDecor(hasTopPanel, hasButtonPanel);
        }
        if (!hasCustomPanel) {
            View content = this.mListView == null ? this.mScrollView : this.mListView;
            if (content != null) {
                int i = (!hasTopPanel ? 0 : 1) | (!hasButtonPanel ? 0 : 2);
                int i2 = i;
                setScrollIndicators(contentPanel, content, i, 3);
            }
        }
        ListView listView = this.mListView;
        ListView listView2 = listView;
        if (listView != null && this.mAdapter != null) {
            listView2.setAdapter(this.mAdapter);
            int i3 = this.mCheckedItem;
            int checkedItem = i3;
            if (i3 > -1) {
                listView2.setItemChecked(checkedItem, true);
                listView2.setSelection(checkedItem);
            }
        }
    }

    private void setScrollIndicators(ViewGroup viewGroup, View view, int i, int i2) {
        ViewGroup contentPanel = viewGroup;
        View content = view;
        int indicators = i;
        int mask = i2;
        ViewGroup viewGroup2 = contentPanel;
        View view2 = content;
        int i3 = indicators;
        int i4 = mask;
        View indicatorUp = this.mWindow.findViewById(C0268R.C0270id.scrollIndicatorUp);
        View indicatorDown = this.mWindow.findViewById(C0268R.C0270id.scrollIndicatorDown);
        if (VERSION.SDK_INT < 23) {
            if (indicatorUp != null && (indicators & 1) == 0) {
                contentPanel.removeView(indicatorUp);
                indicatorUp = null;
            }
            if (indicatorDown != null && (indicators & 2) == 0) {
                contentPanel.removeView(indicatorDown);
                indicatorDown = null;
            }
            if (indicatorUp != null || indicatorDown != null) {
                View top = indicatorUp;
                View bottom = indicatorDown;
                if (this.mMessage != null) {
                    final View view3 = top;
                    final View view4 = bottom;
                    this.mScrollView.setOnScrollChangeListener(new OnScrollChangeListener(this) {
                        final /* synthetic */ AlertController this$0;

                        {
                            AlertController this$02 = r7;
                            View view = r8;
                            View view2 = r9;
                            AlertController alertController = this$02;
                            this.this$0 = this$02;
                        }

                        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                            NestedScrollView v = nestedScrollView;
                            NestedScrollView nestedScrollView2 = v;
                            int i5 = i;
                            int i6 = i2;
                            int i7 = i3;
                            int i8 = i4;
                            AlertController.manageScrollIndicators(v, view3, view4);
                        }
                    });
                    NestedScrollView nestedScrollView = this.mScrollView;
                    final View view5 = top;
                    final View view6 = bottom;
                    C02443 r0 = new Runnable(this) {
                        final /* synthetic */ AlertController this$0;

                        {
                            AlertController this$02 = r7;
                            View view = r8;
                            View view2 = r9;
                            AlertController alertController = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            AlertController.manageScrollIndicators(this.this$0.mScrollView, view5, view6);
                        }
                    };
                    boolean post = nestedScrollView.post(r0);
                } else if (this.mListView == null) {
                    if (top != null) {
                        contentPanel.removeView(top);
                    }
                    if (bottom != null) {
                        contentPanel.removeView(bottom);
                    }
                } else {
                    ListView listView = this.mListView;
                    final View view7 = top;
                    final View view8 = bottom;
                    C02454 r02 = new OnScrollListener(this) {
                        final /* synthetic */ AlertController this$0;

                        {
                            AlertController this$02 = r7;
                            View view = r8;
                            View view2 = r9;
                            AlertController alertController = this$02;
                            this.this$0 = this$02;
                        }

                        public void onScrollStateChanged(AbsListView absListView, int i) {
                            AbsListView absListView2 = absListView;
                            int i2 = i;
                        }

                        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                            AbsListView v = absListView;
                            AbsListView absListView2 = v;
                            int i4 = i;
                            int i5 = i2;
                            int i6 = i3;
                            AlertController.manageScrollIndicators(v, view7, view8);
                        }
                    };
                    listView.setOnScrollListener(r02);
                    ListView listView2 = this.mListView;
                    final View view9 = top;
                    final View view10 = bottom;
                    C02465 r03 = new Runnable(this) {
                        final /* synthetic */ AlertController this$0;

                        {
                            AlertController this$02 = r7;
                            View view = r8;
                            View view2 = r9;
                            AlertController alertController = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            AlertController.manageScrollIndicators(this.this$0.mListView, view9, view10);
                        }
                    };
                    boolean post2 = listView2.post(r03);
                }
            }
        } else {
            ViewCompat.setScrollIndicators(content, indicators, mask);
            if (indicatorUp != null) {
                contentPanel.removeView(indicatorUp);
            }
            if (indicatorDown != null) {
                contentPanel.removeView(indicatorDown);
            }
        }
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        View customView;
        ViewGroup customPanel = viewGroup;
        ViewGroup viewGroup2 = customPanel;
        if (this.mView != null) {
            customView = this.mView;
        } else if (this.mViewLayoutResId == 0) {
            customView = null;
        } else {
            LayoutInflater from = LayoutInflater.from(this.mContext);
            LayoutInflater layoutInflater = from;
            customView = from.inflate(this.mViewLayoutResId, customPanel, false);
        }
        boolean hasCustomView = customView != null;
        if (!hasCustomView || !canTextInput(customView)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (!hasCustomView) {
            customPanel.setVisibility(8);
            return;
        }
        FrameLayout frameLayout = (FrameLayout) this.mWindow.findViewById(C0268R.C0270id.custom);
        FrameLayout custom = frameLayout;
        frameLayout.addView(customView, new LayoutParams(-1, -1));
        if (this.mViewSpacingSpecified) {
            custom.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        }
        if (this.mListView != null) {
            ((LinearLayout.LayoutParams) customPanel.getLayoutParams()).weight = 0.0f;
        }
    }

    private void setupTitle(ViewGroup viewGroup) {
        ViewGroup topPanel = viewGroup;
        ViewGroup viewGroup2 = topPanel;
        if (this.mCustomTitleView == null) {
            this.mIconView = (ImageView) this.mWindow.findViewById(16908294);
            if ((!TextUtils.isEmpty(this.mTitle)) && this.mShowTitle) {
                this.mTitleView = (TextView) this.mWindow.findViewById(C0268R.C0270id.alertTitle);
                this.mTitleView.setText(this.mTitle);
                if (this.mIconId != 0) {
                    this.mIconView.setImageResource(this.mIconId);
                } else if (this.mIcon == null) {
                    this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                    this.mIconView.setVisibility(8);
                } else {
                    this.mIconView.setImageDrawable(this.mIcon);
                }
            } else {
                View findViewById = this.mWindow.findViewById(C0268R.C0270id.title_template);
                View view = findViewById;
                findViewById.setVisibility(8);
                this.mIconView.setVisibility(8);
                topPanel.setVisibility(8);
            }
        } else {
            topPanel.addView(this.mCustomTitleView, 0, new LayoutParams(-1, -2));
            View findViewById2 = this.mWindow.findViewById(C0268R.C0270id.title_template);
            View view2 = findViewById2;
            findViewById2.setVisibility(8);
        }
    }

    private void setupContent(ViewGroup viewGroup) {
        ViewGroup contentPanel = viewGroup;
        ViewGroup viewGroup2 = contentPanel;
        this.mScrollView = (NestedScrollView) this.mWindow.findViewById(C0268R.C0270id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        this.mMessageView = (TextView) contentPanel.findViewById(16908299);
        if (this.mMessageView != null) {
            if (this.mMessage == null) {
                this.mMessageView.setVisibility(8);
                this.mScrollView.removeView(this.mMessageView);
                if (this.mListView == null) {
                    contentPanel.setVisibility(8);
                } else {
                    ViewGroup viewGroup3 = (ViewGroup) this.mScrollView.getParent();
                    ViewGroup scrollParent = viewGroup3;
                    int indexOfChild = viewGroup3.indexOfChild(this.mScrollView);
                    int i = indexOfChild;
                    scrollParent.removeViewAt(indexOfChild);
                    scrollParent.addView(this.mListView, indexOfChild, new LayoutParams(-1, -1));
                }
            } else {
                this.mMessageView.setText(this.mMessage);
            }
        }
    }

    static void manageScrollIndicators(View view, View view2, View view3) {
        View v = view;
        View upIndicator = view2;
        View downIndicator = view3;
        View view4 = v;
        View view5 = upIndicator;
        View view6 = downIndicator;
        if (upIndicator != null) {
            upIndicator.setVisibility(!ViewCompat.canScrollVertically(v, -1) ? 4 : 0);
        }
        if (downIndicator != null) {
            downIndicator.setVisibility(!ViewCompat.canScrollVertically(v, 1) ? 4 : 0);
        }
    }

    private void setupButtons(ViewGroup viewGroup) {
        ViewGroup buttonPanel = viewGroup;
        ViewGroup viewGroup2 = buttonPanel;
        int whichButtons = 0;
        this.mButtonPositive = (Button) buttonPanel.findViewById(16908313);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        if (!TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            whichButtons = true;
        } else {
            this.mButtonPositive.setVisibility(8);
        }
        this.mButtonNegative = (Button) buttonPanel.findViewById(16908314);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (!TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            whichButtons |= 2;
        } else {
            this.mButtonNegative.setVisibility(8);
        }
        this.mButtonNeutral = (Button) buttonPanel.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (!TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            whichButtons |= 4;
        } else {
            this.mButtonNeutral.setVisibility(8);
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (whichButtons == 1) {
                centerButton(this.mButtonPositive);
            } else if (whichButtons == 2) {
                centerButton(this.mButtonNegative);
            } else if (whichButtons == 4) {
                centerButton(this.mButtonNeutral);
            }
        }
        if (!(whichButtons != 0)) {
            buttonPanel.setVisibility(8);
        }
    }

    private void centerButton(Button button) {
        Button button2 = button;
        Button button3 = button2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button2.getLayoutParams();
        LinearLayout.LayoutParams params = layoutParams;
        layoutParams.gravity = 1;
        params.weight = 0.5f;
        button2.setLayoutParams(params);
    }
}
