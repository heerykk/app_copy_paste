package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.TintableBackgroundView;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.ContextThemeWrapper;
import android.support.p003v7.view.menu.ShowableListMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

/* renamed from: android.support.v7.widget.AppCompatSpinner */
public class AppCompatSpinner extends Spinner implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE;
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    DropdownPopup mPopup;
    private Context mPopupContext;
    private boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    final Rect mTempRect;

    /* renamed from: android.support.v7.widget.AppCompatSpinner$DropDownAdapter */
    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Theme theme) {
            SpinnerAdapter adapter = spinnerAdapter;
            Theme dropDownTheme = theme;
            SpinnerAdapter spinnerAdapter2 = adapter;
            Theme theme2 = dropDownTheme;
            this.mAdapter = adapter;
            if (adapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) adapter;
            }
            if (dropDownTheme != null) {
                if (VERSION.SDK_INT >= 23 && (adapter instanceof ThemedSpinnerAdapter)) {
                    ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) adapter;
                    ThemedSpinnerAdapter themedAdapter = themedSpinnerAdapter;
                    if (themedSpinnerAdapter.getDropDownViewTheme() != dropDownTheme) {
                        themedAdapter.setDropDownViewTheme(dropDownTheme);
                    }
                } else if (adapter instanceof ThemedSpinnerAdapter) {
                    ThemedSpinnerAdapter themedSpinnerAdapter2 = (ThemedSpinnerAdapter) adapter;
                    ThemedSpinnerAdapter themedAdapter2 = themedSpinnerAdapter2;
                    if (themedSpinnerAdapter2.getDropDownViewTheme() == null) {
                        themedAdapter2.setDropDownViewTheme(dropDownTheme);
                    }
                }
            }
        }

        public int getCount() {
            return this.mAdapter != null ? this.mAdapter.getCount() : 0;
        }

        public Object getItem(int i) {
            int position = i;
            int i2 = position;
            if (this.mAdapter != null) {
                return this.mAdapter.getItem(position);
            }
            return null;
        }

        public long getItemId(int i) {
            int position = i;
            int i2 = position;
            if (this.mAdapter != null) {
                return this.mAdapter.getItemId(position);
            }
            return -1;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int position = i;
            View convertView = view;
            ViewGroup parent = viewGroup;
            int i2 = position;
            View view2 = convertView;
            ViewGroup viewGroup2 = parent;
            return getDropDownView(position, convertView, parent);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            View view2;
            int position = i;
            View convertView = view;
            ViewGroup parent = viewGroup;
            int i2 = position;
            View view3 = convertView;
            ViewGroup viewGroup2 = parent;
            if (this.mAdapter != null) {
                view2 = this.mAdapter.getDropDownView(position, convertView, parent);
            } else {
                view2 = null;
            }
            return view2;
        }

        public boolean hasStableIds() {
            return this.mAdapter != null && this.mAdapter.hasStableIds();
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            DataSetObserver observer = dataSetObserver;
            DataSetObserver dataSetObserver2 = observer;
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(observer);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            DataSetObserver observer = dataSetObserver;
            DataSetObserver dataSetObserver2 = observer;
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(observer);
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            ListAdapter adapter = listAdapter;
            if (listAdapter == null) {
                return true;
            }
            return adapter.areAllItemsEnabled();
        }

        public boolean isEnabled(int i) {
            int position = i;
            int i2 = position;
            ListAdapter listAdapter = this.mListAdapter;
            ListAdapter adapter = listAdapter;
            if (listAdapter == null) {
                return true;
            }
            return adapter.isEnabled(position);
        }

        public int getItemViewType(int i) {
            int i2 = i;
            return 0;
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }
    }

    /* renamed from: android.support.v7.widget.AppCompatSpinner$DropdownPopup */
    private class DropdownPopup extends ListPopupWindow {
        ListAdapter mAdapter;
        private CharSequence mHintText;
        private final Rect mVisibleRect = new Rect();
        final /* synthetic */ AppCompatSpinner this$0;

        static /* synthetic */ void access$001(DropdownPopup dropdownPopup) {
            DropdownPopup x0 = dropdownPopup;
            DropdownPopup dropdownPopup2 = x0;
            super.show();
        }

        public DropdownPopup(AppCompatSpinner appCompatSpinner, Context context, AttributeSet attributeSet, int i) {
            AppCompatSpinner appCompatSpinner2 = appCompatSpinner;
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            int defStyleAttr = i;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            int i2 = defStyleAttr;
            this.this$0 = appCompatSpinner2;
            super(context2, attrs, defStyleAttr);
            setAnchorView(appCompatSpinner2);
            setModal(true);
            setPromptPosition(0);
            final AppCompatSpinner appCompatSpinner3 = appCompatSpinner2;
            setOnItemClickListener(new OnItemClickListener(this) {
                final /* synthetic */ DropdownPopup this$1;

                {
                    DropdownPopup this$12 = r6;
                    AppCompatSpinner appCompatSpinner = r7;
                    DropdownPopup dropdownPopup = this$12;
                    this.this$1 = this$12;
                }

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    View v = view;
                    int position = i;
                    AdapterView<?> adapterView2 = adapterView;
                    View view2 = v;
                    int i2 = position;
                    long j2 = j;
                    this.this$1.this$0.setSelection(position);
                    if (this.this$1.this$0.getOnItemClickListener() != null) {
                        boolean performItemClick = this.this$1.this$0.performItemClick(v, position, this.this$1.mAdapter.getItemId(position));
                    }
                    this.this$1.dismiss();
                }
            });
        }

        public void setAdapter(ListAdapter listAdapter) {
            ListAdapter adapter = listAdapter;
            ListAdapter listAdapter2 = adapter;
            super.setAdapter(adapter);
            this.mAdapter = adapter;
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        public void setPromptText(CharSequence charSequence) {
            CharSequence hintText = charSequence;
            CharSequence charSequence2 = hintText;
            this.mHintText = hintText;
        }

        /* access modifiers changed from: 0000 */
        public void computeContentWidth() {
            int hOffset;
            Drawable background = getBackground();
            Drawable background2 = background;
            int hOffset2 = 0;
            if (background == null) {
                Rect rect = this.this$0.mTempRect;
                this.this$0.mTempRect.right = 0;
                rect.left = 0;
            } else {
                boolean padding = background2.getPadding(this.this$0.mTempRect);
                hOffset2 = !ViewUtils.isLayoutRtl(this.this$0) ? -this.this$0.mTempRect.left : this.this$0.mTempRect.right;
            }
            int spinnerPaddingLeft = this.this$0.getPaddingLeft();
            int spinnerPaddingRight = this.this$0.getPaddingRight();
            int spinnerWidth = this.this$0.getWidth();
            if (this.this$0.mDropDownWidth == -2) {
                int contentWidth = this.this$0.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, getBackground());
                int contentWidthLimit = (this.this$0.getContext().getResources().getDisplayMetrics().widthPixels - this.this$0.mTempRect.left) - this.this$0.mTempRect.right;
                if (contentWidth > contentWidthLimit) {
                    contentWidth = contentWidthLimit;
                }
                setContentWidth(Math.max(contentWidth, (spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight));
            } else if (this.this$0.mDropDownWidth != -1) {
                setContentWidth(this.this$0.mDropDownWidth);
            } else {
                setContentWidth((spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight);
            }
            if (!ViewUtils.isLayoutRtl(this.this$0)) {
                hOffset = hOffset2 + spinnerPaddingLeft;
            } else {
                hOffset = hOffset2 + ((spinnerWidth - spinnerPaddingRight) - getWidth());
            }
            setHorizontalOffset(hOffset);
        }

        public void show() {
            boolean isShowing = isShowing();
            boolean z = isShowing;
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            ListView listView = getListView();
            ListView listView2 = listView;
            listView.setChoiceMode(1);
            setSelection(this.this$0.getSelectedItemPosition());
            if (!isShowing) {
                ViewTreeObserver viewTreeObserver = this.this$0.getViewTreeObserver();
                ViewTreeObserver vto = viewTreeObserver;
                if (viewTreeObserver != null) {
                    C03022 r16 = new OnGlobalLayoutListener(this) {
                        final /* synthetic */ DropdownPopup this$1;

                        {
                            DropdownPopup this$12 = r5;
                            DropdownPopup dropdownPopup = this$12;
                            this.this$1 = this$12;
                        }

                        public void onGlobalLayout() {
                            if (this.this$1.isVisibleToUser(this.this$1.this$0)) {
                                this.this$1.computeContentWidth();
                                DropdownPopup.access$001(this.this$1);
                                return;
                            }
                            this.this$1.dismiss();
                        }
                    };
                    vto.addOnGlobalLayoutListener(r16);
                    final C03022 r1 = r16;
                    setOnDismissListener(new OnDismissListener(this) {
                        final /* synthetic */ DropdownPopup this$1;

                        {
                            DropdownPopup this$12 = r6;
                            OnGlobalLayoutListener onGlobalLayoutListener = r7;
                            DropdownPopup dropdownPopup = this$12;
                            this.this$1 = this$12;
                        }

                        public void onDismiss() {
                            ViewTreeObserver viewTreeObserver = this.this$1.this$0.getViewTreeObserver();
                            ViewTreeObserver vto = viewTreeObserver;
                            if (viewTreeObserver != null) {
                                vto.removeGlobalOnLayoutListener(r1);
                            }
                        }
                    });
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean isVisibleToUser(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompat.isAttachedToWindow(view2) && view2.getGlobalVisibleRect(this.mVisibleRect);
        }
    }

    public /* bridge */ /* synthetic */ void setAdapter(Adapter adapter) {
        setAdapter((SpinnerAdapter) adapter);
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843505;
        ATTRS_ANDROID_SPINNERMODE = iArr;
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i, int i2, Theme theme) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int mode = i2;
        Theme popupTheme = theme;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int mode2 = mode;
        Theme theme2 = popupTheme;
        super(context2, attrs, defStyleAttr);
        this.mTempRect = new Rect();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0268R.styleable.Spinner, defStyleAttr, 0);
        AppCompatBackgroundHelper appCompatBackgroundHelper = new AppCompatBackgroundHelper(this);
        this.mBackgroundTintHelper = appCompatBackgroundHelper;
        if (popupTheme == null) {
            int resourceId = a.getResourceId(C0268R.styleable.Spinner_popupTheme, 0);
            int popupThemeResId = resourceId;
            if (resourceId == 0) {
                this.mPopupContext = VERSION.SDK_INT >= 23 ? null : context2;
            } else {
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context2, popupThemeResId);
                this.mPopupContext = contextThemeWrapper;
            }
        } else {
            ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context2, popupTheme);
            this.mPopupContext = contextThemeWrapper2;
        }
        if (this.mPopupContext != null) {
            if (mode == -1) {
                if (VERSION.SDK_INT < 11) {
                    mode2 = 1;
                } else {
                    TypedArray aa = null;
                    try {
                        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, ATTRS_ANDROID_SPINNERMODE, defStyleAttr, 0);
                        TypedArray aa2 = obtainStyledAttributes;
                        if (obtainStyledAttributes.hasValue(0)) {
                            mode2 = aa2.getInt(0, 0);
                        }
                        if (aa2 != null) {
                            aa2.recycle();
                        }
                    } catch (Exception e) {
                        Exception exc = e;
                        int i4 = Log.i(TAG, "Could not read android:spinnerMode", e);
                        if (aa != null) {
                            aa.recycle();
                        }
                    } catch (Throwable th) {
                        if (aa != null) {
                            aa.recycle();
                        }
                        throw th;
                    }
                }
            }
            if (mode2 == 1) {
                DropdownPopup dropdownPopup = new DropdownPopup(this, this.mPopupContext, attrs, defStyleAttr);
                DropdownPopup popup = dropdownPopup;
                TintTypedArray pa = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attrs, C0268R.styleable.Spinner, defStyleAttr, 0);
                this.mDropDownWidth = pa.getLayoutDimension(C0268R.styleable.Spinner_android_dropDownWidth, -2);
                popup.setBackgroundDrawable(pa.getDrawable(C0268R.styleable.Spinner_android_popupBackground));
                popup.setPromptText(a.getString(C0268R.styleable.Spinner_android_prompt));
                pa.recycle();
                this.mPopup = popup;
                C03001 r0 = new ForwardingListener(this, this, popup) {
                    final /* synthetic */ AppCompatSpinner this$0;
                    final /* synthetic */ DropdownPopup val$popup;

                    {
                        AppCompatSpinner this$02 = r9;
                        View src = r10;
                        DropdownPopup dropdownPopup = r11;
                        AppCompatSpinner appCompatSpinner = this$02;
                        View view = src;
                        this.this$0 = this$02;
                        this.val$popup = dropdownPopup;
                    }

                    public ShowableListMenu getPopup() {
                        return this.val$popup;
                    }

                    public boolean onForwardingStarted() {
                        if (!this.this$0.mPopup.isShowing()) {
                            this.this$0.mPopup.show();
                        }
                        return true;
                    }
                };
                this.mForwardingListener = r0;
            }
        }
        CharSequence[] textArray = a.getTextArray(C0268R.styleable.Spinner_android_entries);
        CharSequence[] entries = textArray;
        if (textArray != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(context2, 17367048, entries);
            ArrayAdapter arrayAdapter2 = arrayAdapter;
            arrayAdapter.setDropDownViewResource(C0268R.layout.support_simple_spinner_dropdown_item);
            setAdapter((SpinnerAdapter) arrayAdapter2);
        }
        a.recycle();
        this.mPopupSet = true;
        if (this.mTempAdapter != null) {
            setAdapter(this.mTempAdapter);
            this.mTempAdapter = null;
        }
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public AppCompatSpinner(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, (AttributeSet) null);
    }

    public AppCompatSpinner(Context context, int i) {
        Context context2 = context;
        int mode = i;
        Context context3 = context2;
        int i2 = mode;
        this(context2, null, C0268R.attr.spinnerStyle, mode);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        this(context2, attrs, defStyleAttr, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i, int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int mode = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = mode;
        this(context2, attrs, defStyleAttr, mode, null);
    }

    public Context getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        if (VERSION.SDK_INT < 23) {
            return null;
        }
        return super.getPopupContext();
    }

    public void setPopupBackgroundDrawable(Drawable drawable) {
        Drawable background = drawable;
        Drawable drawable2 = background;
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable(background);
        } else if (VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(background);
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), resId));
    }

    public Drawable getPopupBackground() {
        if (this.mPopup != null) {
            return this.mPopup.getBackground();
        }
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        return super.getPopupBackground();
    }

    public void setDropDownVerticalOffset(int i) {
        int pixels = i;
        int i2 = pixels;
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset(pixels);
        } else if (VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(pixels);
        }
    }

    public int getDropDownVerticalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset();
        }
        if (VERSION.SDK_INT < 16) {
            return 0;
        }
        return super.getDropDownVerticalOffset();
    }

    public void setDropDownHorizontalOffset(int i) {
        int pixels = i;
        int i2 = pixels;
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset(pixels);
        } else if (VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(pixels);
        }
    }

    public int getDropDownHorizontalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset();
        }
        if (VERSION.SDK_INT < 16) {
            return 0;
        }
        return super.getDropDownHorizontalOffset();
    }

    public void setDropDownWidth(int i) {
        int pixels = i;
        int i2 = pixels;
        if (this.mPopup != null) {
            this.mDropDownWidth = pixels;
        } else if (VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(pixels);
        }
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (VERSION.SDK_INT < 16) {
            return 0;
        }
        return super.getDropDownWidth();
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        SpinnerAdapter adapter = spinnerAdapter;
        SpinnerAdapter spinnerAdapter2 = adapter;
        if (this.mPopupSet) {
            super.setAdapter(adapter);
            if (this.mPopup != null) {
                this.mPopup.setAdapter(new DropDownAdapter(adapter, (this.mPopupContext != null ? this.mPopupContext : getContext()).getTheme()));
            }
            return;
        }
        this.mTempAdapter = adapter;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        if (this.mForwardingListener != null && this.mForwardingListener.onTouch(this, event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mPopup != null && MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            int measuredWidth = getMeasuredWidth();
            int i5 = measuredWidth;
            setMeasuredDimension(Math.min(Math.max(measuredWidth, compatMeasureContentWidth(getAdapter(), getBackground())), MeasureSpec.getSize(widthMeasureSpec)), getMeasuredHeight());
        }
    }

    public boolean performClick() {
        if (this.mPopup == null) {
            return super.performClick();
        }
        if (!this.mPopup.isShowing()) {
            this.mPopup.show();
        }
        return true;
    }

    public void setPrompt(CharSequence charSequence) {
        CharSequence prompt = charSequence;
        CharSequence charSequence2 = prompt;
        if (this.mPopup == null) {
            super.setPrompt(prompt);
        } else {
            this.mPopup.setPromptText(prompt);
        }
    }

    public CharSequence getPrompt() {
        return this.mPopup == null ? super.getPrompt() : this.mPopup.getHintText();
    }

    public void setBackgroundResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable background = drawable;
        Drawable drawable2 = background;
        super.setBackgroundDrawable(background);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(background);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(tint);
        }
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public ColorStateList getSupportBackgroundTintList() {
        return this.mBackgroundTintHelper == null ? null : this.mBackgroundTintHelper.getSupportBackgroundTintList();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setSupportBackgroundTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public Mode getSupportBackgroundTintMode() {
        return this.mBackgroundTintHelper == null ? null : this.mBackgroundTintHelper.getSupportBackgroundTintMode();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }

    /* access modifiers changed from: 0000 */
    public int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        SpinnerAdapter adapter = spinnerAdapter;
        Drawable background = drawable;
        SpinnerAdapter spinnerAdapter2 = adapter;
        Drawable drawable2 = background;
        if (adapter == null) {
            return 0;
        }
        int width = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int start = Math.max(0, getSelectedItemPosition());
        int min = Math.min(adapter.getCount(), start + 15);
        int end = min;
        int max = Math.max(0, start - (15 - (min - start)));
        int start2 = max;
        for (int i = max; i < end; i++) {
            int itemViewType = adapter.getItemViewType(i);
            int positionType = itemViewType;
            if (itemViewType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            View view = adapter.getView(i, itemView, this);
            itemView = view;
            if (view.getLayoutParams() == null) {
                itemView.setLayoutParams(new LayoutParams(-2, -2));
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        if (background != null) {
            boolean padding = background.getPadding(this.mTempRect);
            width += this.mTempRect.left + this.mTempRect.right;
        }
        return width;
    }
}
