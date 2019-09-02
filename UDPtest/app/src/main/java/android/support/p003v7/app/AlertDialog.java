package android.support.p003v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.p003v7.app.AlertController.AlertParams;
import android.support.p003v7.appcompat.C0268R;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/* renamed from: android.support.v7.app.AlertDialog */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    static final int LAYOUT_HINT_NONE = 0;
    static final int LAYOUT_HINT_SIDE = 1;
    final AlertController mAlert;

    /* renamed from: android.support.v7.app.AlertDialog$Builder */
    public static class Builder {

        /* renamed from: P */
        private final AlertParams f16P;
        private final int mTheme;

        public Builder(@NonNull Context context) {
            Context context2 = context;
            Context context3 = context2;
            this(context2, AlertDialog.resolveDialogTheme(context2, 0));
        }

        public Builder(@NonNull Context context, @StyleRes int i) {
            Context context2 = context;
            int themeResId = i;
            Context context3 = context2;
            int i2 = themeResId;
            this.f16P = new AlertParams(new ContextThemeWrapper(context2, AlertDialog.resolveDialogTheme(context2, themeResId)));
            this.mTheme = themeResId;
        }

        @NonNull
        public Context getContext() {
            return this.f16P.mContext;
        }

        public Builder setTitle(@StringRes int i) {
            int titleId = i;
            int i2 = titleId;
            this.f16P.mTitle = this.f16P.mContext.getText(titleId);
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.f16P.mTitle = title;
            return this;
        }

        public Builder setCustomTitle(@Nullable View view) {
            View customTitleView = view;
            View view2 = customTitleView;
            this.f16P.mCustomTitleView = customTitleView;
            return this;
        }

        public Builder setMessage(@StringRes int i) {
            int messageId = i;
            int i2 = messageId;
            this.f16P.mMessage = this.f16P.mContext.getText(messageId);
            return this;
        }

        public Builder setMessage(@Nullable CharSequence charSequence) {
            CharSequence message = charSequence;
            CharSequence charSequence2 = message;
            this.f16P.mMessage = message;
            return this;
        }

        public Builder setIcon(@DrawableRes int i) {
            int iconId = i;
            int i2 = iconId;
            this.f16P.mIconId = iconId;
            return this;
        }

        public Builder setIcon(@Nullable Drawable drawable) {
            Drawable icon = drawable;
            Drawable drawable2 = icon;
            this.f16P.mIcon = icon;
            return this;
        }

        public Builder setIconAttribute(@AttrRes int i) {
            int attrId = i;
            int i2 = attrId;
            TypedValue out = new TypedValue();
            boolean resolveAttribute = this.f16P.mContext.getTheme().resolveAttribute(attrId, out, true);
            this.f16P.mIconId = out.resourceId;
            return this;
        }

        public Builder setPositiveButton(@StringRes int i, OnClickListener onClickListener) {
            int textId = i;
            OnClickListener listener = onClickListener;
            int i2 = textId;
            OnClickListener onClickListener2 = listener;
            this.f16P.mPositiveButtonText = this.f16P.mContext.getText(textId);
            this.f16P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, OnClickListener onClickListener) {
            CharSequence text = charSequence;
            OnClickListener listener = onClickListener;
            CharSequence charSequence2 = text;
            OnClickListener onClickListener2 = listener;
            this.f16P.mPositiveButtonText = text;
            this.f16P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int i, OnClickListener onClickListener) {
            int textId = i;
            OnClickListener listener = onClickListener;
            int i2 = textId;
            OnClickListener onClickListener2 = listener;
            this.f16P.mNegativeButtonText = this.f16P.mContext.getText(textId);
            this.f16P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, OnClickListener onClickListener) {
            CharSequence text = charSequence;
            OnClickListener listener = onClickListener;
            CharSequence charSequence2 = text;
            OnClickListener onClickListener2 = listener;
            this.f16P.mNegativeButtonText = text;
            this.f16P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(@StringRes int i, OnClickListener onClickListener) {
            int textId = i;
            OnClickListener listener = onClickListener;
            int i2 = textId;
            OnClickListener onClickListener2 = listener;
            this.f16P.mNeutralButtonText = this.f16P.mContext.getText(textId);
            this.f16P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, OnClickListener onClickListener) {
            CharSequence text = charSequence;
            OnClickListener listener = onClickListener;
            CharSequence charSequence2 = text;
            OnClickListener onClickListener2 = listener;
            this.f16P.mNeutralButtonText = text;
            this.f16P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.f16P.mCancelable = z;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            OnCancelListener onCancelListener2 = onCancelListener;
            OnCancelListener onCancelListener3 = onCancelListener2;
            this.f16P.mOnCancelListener = onCancelListener2;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            OnDismissListener onDismissListener2 = onDismissListener;
            OnDismissListener onDismissListener3 = onDismissListener2;
            this.f16P.mOnDismissListener = onDismissListener2;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            OnKeyListener onKeyListener2 = onKeyListener;
            OnKeyListener onKeyListener3 = onKeyListener2;
            this.f16P.mOnKeyListener = onKeyListener2;
            return this;
        }

        public Builder setItems(@ArrayRes int i, OnClickListener onClickListener) {
            int itemsId = i;
            OnClickListener listener = onClickListener;
            int i2 = itemsId;
            OnClickListener onClickListener2 = listener;
            this.f16P.mItems = this.f16P.mContext.getResources().getTextArray(itemsId);
            this.f16P.mOnClickListener = listener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, OnClickListener onClickListener) {
            CharSequence[] items = charSequenceArr;
            OnClickListener listener = onClickListener;
            CharSequence[] charSequenceArr2 = items;
            OnClickListener onClickListener2 = listener;
            this.f16P.mItems = items;
            this.f16P.mOnClickListener = listener;
            return this;
        }

        public Builder setAdapter(ListAdapter listAdapter, OnClickListener onClickListener) {
            ListAdapter adapter = listAdapter;
            OnClickListener listener = onClickListener;
            ListAdapter listAdapter2 = adapter;
            OnClickListener onClickListener2 = listener;
            this.f16P.mAdapter = adapter;
            this.f16P.mOnClickListener = listener;
            return this;
        }

        public Builder setCursor(Cursor cursor, OnClickListener onClickListener, String str) {
            Cursor cursor2 = cursor;
            OnClickListener listener = onClickListener;
            String labelColumn = str;
            Cursor cursor3 = cursor2;
            OnClickListener onClickListener2 = listener;
            String str2 = labelColumn;
            this.f16P.mCursor = cursor2;
            this.f16P.mLabelColumn = labelColumn;
            this.f16P.mOnClickListener = listener;
            return this;
        }

        public Builder setMultiChoiceItems(@ArrayRes int i, boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            int itemsId = i;
            boolean[] checkedItems = zArr;
            OnMultiChoiceClickListener listener = onMultiChoiceClickListener;
            int i2 = itemsId;
            boolean[] zArr2 = checkedItems;
            OnMultiChoiceClickListener onMultiChoiceClickListener2 = listener;
            this.f16P.mItems = this.f16P.mContext.getResources().getTextArray(itemsId);
            this.f16P.mOnCheckboxClickListener = listener;
            this.f16P.mCheckedItems = checkedItems;
            this.f16P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            CharSequence[] items = charSequenceArr;
            boolean[] checkedItems = zArr;
            OnMultiChoiceClickListener listener = onMultiChoiceClickListener;
            CharSequence[] charSequenceArr2 = items;
            boolean[] zArr2 = checkedItems;
            OnMultiChoiceClickListener onMultiChoiceClickListener2 = listener;
            this.f16P.mItems = items;
            this.f16P.mOnCheckboxClickListener = listener;
            this.f16P.mCheckedItems = checkedItems;
            this.f16P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String str, String str2, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            Cursor cursor2 = cursor;
            String isCheckedColumn = str;
            String labelColumn = str2;
            OnMultiChoiceClickListener listener = onMultiChoiceClickListener;
            Cursor cursor3 = cursor2;
            String str3 = isCheckedColumn;
            String str4 = labelColumn;
            OnMultiChoiceClickListener onMultiChoiceClickListener2 = listener;
            this.f16P.mCursor = cursor2;
            this.f16P.mOnCheckboxClickListener = listener;
            this.f16P.mIsCheckedColumn = isCheckedColumn;
            this.f16P.mLabelColumn = labelColumn;
            this.f16P.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(@ArrayRes int i, int i2, OnClickListener onClickListener) {
            int itemsId = i;
            int checkedItem = i2;
            OnClickListener listener = onClickListener;
            int i3 = itemsId;
            int i4 = checkedItem;
            OnClickListener onClickListener2 = listener;
            this.f16P.mItems = this.f16P.mContext.getResources().getTextArray(itemsId);
            this.f16P.mOnClickListener = listener;
            this.f16P.mCheckedItem = checkedItem;
            this.f16P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int i, String str, OnClickListener onClickListener) {
            Cursor cursor2 = cursor;
            int checkedItem = i;
            String labelColumn = str;
            OnClickListener listener = onClickListener;
            Cursor cursor3 = cursor2;
            int i2 = checkedItem;
            String str2 = labelColumn;
            OnClickListener onClickListener2 = listener;
            this.f16P.mCursor = cursor2;
            this.f16P.mOnClickListener = listener;
            this.f16P.mCheckedItem = checkedItem;
            this.f16P.mLabelColumn = labelColumn;
            this.f16P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArr, int i, OnClickListener onClickListener) {
            CharSequence[] items = charSequenceArr;
            int checkedItem = i;
            OnClickListener listener = onClickListener;
            CharSequence[] charSequenceArr2 = items;
            int i2 = checkedItem;
            OnClickListener onClickListener2 = listener;
            this.f16P.mItems = items;
            this.f16P.mOnClickListener = listener;
            this.f16P.mCheckedItem = checkedItem;
            this.f16P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int i, OnClickListener onClickListener) {
            ListAdapter adapter = listAdapter;
            int checkedItem = i;
            OnClickListener listener = onClickListener;
            ListAdapter listAdapter2 = adapter;
            int i2 = checkedItem;
            OnClickListener onClickListener2 = listener;
            this.f16P.mAdapter = adapter;
            this.f16P.mOnClickListener = listener;
            this.f16P.mCheckedItem = checkedItem;
            this.f16P.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
            OnItemSelectedListener listener = onItemSelectedListener;
            OnItemSelectedListener onItemSelectedListener2 = listener;
            this.f16P.mOnItemSelectedListener = listener;
            return this;
        }

        public Builder setView(int i) {
            int layoutResId = i;
            int i2 = layoutResId;
            this.f16P.mView = null;
            this.f16P.mViewLayoutResId = layoutResId;
            this.f16P.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view) {
            View view2 = view;
            View view3 = view2;
            this.f16P.mView = view2;
            this.f16P.mViewLayoutResId = 0;
            this.f16P.mViewSpacingSpecified = false;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Deprecated
        public Builder setView(View view, int i, int i2, int i3, int i4) {
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
            this.f16P.mView = view2;
            this.f16P.mViewLayoutResId = 0;
            this.f16P.mViewSpacingSpecified = true;
            this.f16P.mViewSpacingLeft = viewSpacingLeft;
            this.f16P.mViewSpacingTop = viewSpacingTop;
            this.f16P.mViewSpacingRight = viewSpacingRight;
            this.f16P.mViewSpacingBottom = viewSpacingBottom;
            return this;
        }

        @Deprecated
        public Builder setInverseBackgroundForced(boolean z) {
            this.f16P.mForceInverseBackground = z;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder setRecycleOnMeasureEnabled(boolean z) {
            this.f16P.mRecycleOnMeasure = z;
            return this;
        }

        public AlertDialog create() {
            AlertDialog dialog = new AlertDialog(this.f16P.mContext, this.mTheme);
            this.f16P.apply(dialog.mAlert);
            dialog.setCancelable(this.f16P.mCancelable);
            if (this.f16P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(this.f16P.mOnCancelListener);
            dialog.setOnDismissListener(this.f16P.mOnDismissListener);
            if (this.f16P.mOnKeyListener != null) {
                dialog.setOnKeyListener(this.f16P.mOnKeyListener);
            }
            return dialog;
        }

        public AlertDialog show() {
            AlertDialog create = create();
            AlertDialog dialog = create;
            create.show();
            return dialog;
        }
    }

    protected AlertDialog(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, 0);
    }

    protected AlertDialog(@NonNull Context context, @StyleRes int i) {
        Context context2 = context;
        int themeResId = i;
        Context context3 = context2;
        int i2 = themeResId;
        super(context2, resolveDialogTheme(context2, themeResId));
        this.mAlert = new AlertController(getContext(), this, getWindow());
    }

    protected AlertDialog(@NonNull Context context, boolean z, @Nullable OnCancelListener onCancelListener) {
        Context context2 = context;
        OnCancelListener cancelListener = onCancelListener;
        Context context3 = context2;
        boolean cancelable = z;
        OnCancelListener onCancelListener2 = cancelListener;
        this(context2, 0);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    static int resolveDialogTheme(@NonNull Context context, @StyleRes int i) {
        Context context2 = context;
        int resid = i;
        Context context3 = context2;
        int i2 = resid;
        if (resid >= 16777216) {
            return resid;
        }
        TypedValue outValue = new TypedValue();
        boolean resolveAttribute = context2.getTheme().resolveAttribute(C0268R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    public Button getButton(int i) {
        int whichButton = i;
        int i2 = whichButton;
        return this.mAlert.getButton(whichButton);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        super.setTitle(title);
        this.mAlert.setTitle(title);
    }

    public void setCustomTitle(View view) {
        View customTitleView = view;
        View view2 = customTitleView;
        this.mAlert.setCustomTitle(customTitleView);
    }

    public void setMessage(CharSequence charSequence) {
        CharSequence message = charSequence;
        CharSequence charSequence2 = message;
        this.mAlert.setMessage(message);
    }

    public void setView(View view) {
        View view2 = view;
        View view3 = view2;
        this.mAlert.setView(view2);
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
        this.mAlert.setView(view2, viewSpacingLeft, viewSpacingTop, viewSpacingRight, viewSpacingBottom);
    }

    /* access modifiers changed from: 0000 */
    public void setButtonPanelLayoutHint(int i) {
        int layoutHint = i;
        int i2 = layoutHint;
        this.mAlert.setButtonPanelLayoutHint(layoutHint);
    }

    public void setButton(int i, CharSequence charSequence, Message message) {
        int whichButton = i;
        CharSequence text = charSequence;
        Message msg = message;
        int i2 = whichButton;
        CharSequence charSequence2 = text;
        Message message2 = msg;
        this.mAlert.setButton(whichButton, text, null, msg);
    }

    public void setButton(int i, CharSequence charSequence, OnClickListener onClickListener) {
        int whichButton = i;
        CharSequence text = charSequence;
        OnClickListener listener = onClickListener;
        int i2 = whichButton;
        CharSequence charSequence2 = text;
        OnClickListener onClickListener2 = listener;
        this.mAlert.setButton(whichButton, text, listener, null);
    }

    public void setIcon(int i) {
        int resId = i;
        int i2 = resId;
        this.mAlert.setIcon(resId);
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mAlert.setIcon(icon);
    }

    public void setIconAttribute(int i) {
        int attrId = i;
        int i2 = attrId;
        TypedValue out = new TypedValue();
        boolean resolveAttribute = getContext().getTheme().resolveAttribute(attrId, out, true);
        this.mAlert.setIcon(out.resourceId);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        super.onCreate(savedInstanceState);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (!this.mAlert.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (!this.mAlert.onKeyUp(keyCode, event)) {
            return super.onKeyUp(keyCode, event);
        }
        return true;
    }
}
