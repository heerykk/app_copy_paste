package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.C0001R;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.widget.Space;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.widget.AppCompatDrawableManager;
import android.support.p003v7.widget.AppCompatTextView;
import android.support.p003v7.widget.DrawableUtils;
import android.support.p003v7.widget.TintTypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextInputLayout extends LinearLayout {
    private static final int ANIMATION_DURATION = 200;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final String LOG_TAG = "TextInputLayout";
    private ValueAnimatorCompat mAnimator;
    final CollapsingTextHelper mCollapsingTextHelper;
    boolean mCounterEnabled;
    private int mCounterMaxLength;
    private int mCounterOverflowTextAppearance;
    private boolean mCounterOverflowed;
    private int mCounterTextAppearance;
    private TextView mCounterView;
    private ColorStateList mDefaultTextColor;
    EditText mEditText;
    private CharSequence mError;
    private boolean mErrorEnabled;
    private boolean mErrorShown;
    private int mErrorTextAppearance;
    TextView mErrorView;
    private ColorStateList mFocusedTextColor;
    private boolean mHasPasswordToggleTintList;
    private boolean mHasPasswordToggleTintMode;
    private boolean mHasReconstructedEditTextBackground;
    private CharSequence mHint;
    private boolean mHintAnimationEnabled;
    private boolean mHintEnabled;
    private boolean mHintExpanded;
    private boolean mInDrawableStateChanged;
    private LinearLayout mIndicatorArea;
    private int mIndicatorsAdded;
    private final FrameLayout mInputFrame;
    private Drawable mOriginalEditTextEndDrawable;
    private CharSequence mPasswordToggleContentDesc;
    private Drawable mPasswordToggleDrawable;
    private Drawable mPasswordToggleDummyDrawable;
    private boolean mPasswordToggleEnabled;
    private ColorStateList mPasswordToggleTintList;
    private Mode mPasswordToggleTintMode;
    private CheckableImageButton mPasswordToggleView;
    private boolean mPasswordToggledVisible;
    private boolean mRestoringSavedState;
    private Paint mTmpPaint;
    private final Rect mTmpRect;
    private Typeface mTypeface;

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel in = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = in;
                ClassLoader classLoader2 = loader;
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        });
        CharSequence error;

        SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel source = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = source;
            ClassLoader classLoader2 = loader;
            super(source, loader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            TextUtils.writeToParcel(this.error, dest, flags);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + "}";
        }
    }

    private class TextInputAccessibilityDelegate extends AccessibilityDelegateCompat {
        final /* synthetic */ TextInputLayout this$0;

        TextInputAccessibilityDelegate(TextInputLayout textInputLayout) {
            this.this$0 = textInputLayout;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(TextInputLayout.class.getSimpleName());
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onPopulateAccessibilityEvent(host, event);
            CharSequence text = this.this$0.mCollapsingTextHelper.getText();
            CharSequence text2 = text;
            if (!TextUtils.isEmpty(text)) {
                boolean add = event.getText().add(text2);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence text = this.this$0.mCollapsingTextHelper.getText();
            CharSequence text2 = text;
            if (!TextUtils.isEmpty(text)) {
                info.setText(text2);
            }
            if (this.this$0.mEditText != null) {
                info.setLabelFor(this.this$0.mEditText);
            }
            CharSequence error = this.this$0.mErrorView == null ? null : this.this$0.mErrorView.getText();
            if (!TextUtils.isEmpty(error)) {
                info.setContentInvalid(true);
                info.setError(error);
            }
        }
    }

    static /* synthetic */ boolean access$000(TextInputLayout textInputLayout) {
        TextInputLayout x0 = textInputLayout;
        TextInputLayout textInputLayout2 = x0;
        return x0.mRestoringSavedState;
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs);
        this.mTmpRect = new Rect();
        this.mCollapsingTextHelper = new CollapsingTextHelper(this);
        ThemeUtils.checkAppCompatTheme(context2);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        this.mInputFrame = new FrameLayout(context2);
        this.mInputFrame.setAddStatesFromChildren(true);
        addView(this.mInputFrame);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator(new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextGravity(8388659);
        this.mHintExpanded = this.mCollapsingTextHelper.getExpansionFraction() == 1.0f;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0001R.styleable.TextInputLayout, defStyleAttr, C0001R.style.Widget_Design_TextInputLayout);
        this.mHintEnabled = a.getBoolean(C0001R.styleable.TextInputLayout_hintEnabled, true);
        setHint(a.getText(C0001R.styleable.TextInputLayout_android_hint));
        this.mHintAnimationEnabled = a.getBoolean(C0001R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (a.hasValue(C0001R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList = a.getColorStateList(C0001R.styleable.TextInputLayout_android_textColorHint);
            this.mFocusedTextColor = colorStateList;
            this.mDefaultTextColor = colorStateList;
        }
        int resourceId = a.getResourceId(C0001R.styleable.TextInputLayout_hintTextAppearance, -1);
        int i3 = resourceId;
        if (resourceId != -1) {
            setHintTextAppearance(a.getResourceId(C0001R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.mErrorTextAppearance = a.getResourceId(C0001R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean errorEnabled = a.getBoolean(C0001R.styleable.TextInputLayout_errorEnabled, false);
        boolean counterEnabled = a.getBoolean(C0001R.styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(a.getInt(C0001R.styleable.TextInputLayout_counterMaxLength, -1));
        this.mCounterTextAppearance = a.getResourceId(C0001R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.mCounterOverflowTextAppearance = a.getResourceId(C0001R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.mPasswordToggleEnabled = a.getBoolean(C0001R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.mPasswordToggleDrawable = a.getDrawable(C0001R.styleable.TextInputLayout_passwordToggleDrawable);
        this.mPasswordToggleContentDesc = a.getText(C0001R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (a.hasValue(C0001R.styleable.TextInputLayout_passwordToggleTint)) {
            this.mHasPasswordToggleTintList = true;
            this.mPasswordToggleTintList = a.getColorStateList(C0001R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (a.hasValue(C0001R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.mHasPasswordToggleTintMode = true;
            this.mPasswordToggleTintMode = ViewUtils.parseTintMode(a.getInt(C0001R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
        }
        a.recycle();
        setErrorEnabled(errorEnabled);
        setCounterEnabled(counterEnabled);
        applyPasswordToggleTint();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setAccessibilityDelegate(this, new TextInputAccessibilityDelegate(this));
    }

    public TextInputLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        View child = view;
        int index = i;
        LayoutParams params = layoutParams;
        View view2 = child;
        int i2 = index;
        LayoutParams layoutParams2 = params;
        if (!(child instanceof EditText)) {
            super.addView(child, index, params);
            return;
        }
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(params);
        FrameLayout.LayoutParams flp = layoutParams3;
        layoutParams3.gravity = 16 | (flp.gravity & -113);
        this.mInputFrame.addView(child, flp);
        this.mInputFrame.setLayoutParams(params);
        updateInputLayoutMargins();
        setEditText((EditText) child);
    }

    public void setTypeface(@Nullable Typeface typeface) {
        Typeface typeface2 = typeface;
        Typeface typeface3 = typeface2;
        if (typeface2 != this.mTypeface) {
            this.mTypeface = typeface2;
            this.mCollapsingTextHelper.setTypefaces(typeface2);
            if (this.mCounterView != null) {
                this.mCounterView.setTypeface(typeface2);
            }
            if (this.mErrorView != null) {
                this.mErrorView.setTypeface(typeface2);
            }
        }
    }

    @NonNull
    public Typeface getTypeface() {
        return this.mTypeface;
    }

    private void setEditText(EditText editText) {
        EditText editText2 = editText;
        EditText editText3 = editText2;
        if (this.mEditText == null) {
            if (!(editText2 instanceof TextInputEditText)) {
                int i = Log.i(LOG_TAG, "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.mEditText = editText2;
            boolean hasPasswordTransformation = hasPasswordTransformation();
            boolean z = hasPasswordTransformation;
            if (!hasPasswordTransformation) {
                this.mCollapsingTextHelper.setTypefaces(this.mEditText.getTypeface());
            }
            this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
            int gravity = this.mEditText.getGravity();
            int i2 = gravity;
            this.mCollapsingTextHelper.setCollapsedTextGravity(48 | (gravity & -113));
            this.mCollapsingTextHelper.setExpandedTextGravity(gravity);
            this.mEditText.addTextChangedListener(new TextWatcher(this) {
                final /* synthetic */ TextInputLayout this$0;

                {
                    TextInputLayout this$02 = r5;
                    TextInputLayout textInputLayout = this$02;
                    this.this$0 = this$02;
                }

                public void afterTextChanged(Editable editable) {
                    Editable s = editable;
                    Editable editable2 = s;
                    this.this$0.updateLabelState(!TextInputLayout.access$000(this.this$0));
                    if (this.this$0.mCounterEnabled) {
                        this.this$0.updateCounter(s.length());
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    CharSequence charSequence2 = charSequence;
                    int i4 = i;
                    int i5 = i2;
                    int i6 = i3;
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    CharSequence charSequence2 = charSequence;
                    int i4 = i;
                    int i5 = i2;
                    int i6 = i3;
                }
            });
            if (this.mDefaultTextColor == null) {
                this.mDefaultTextColor = this.mEditText.getHintTextColors();
            }
            if (this.mHintEnabled && TextUtils.isEmpty(this.mHint)) {
                setHint(this.mEditText.getHint());
                this.mEditText.setHint(null);
            }
            if (this.mCounterView != null) {
                updateCounter(this.mEditText.getText().length());
            }
            if (this.mIndicatorArea != null) {
                adjustIndicatorPadding();
            }
            updatePasswordToggleView();
            updateLabelState(false);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void updateInputLayoutMargins() {
        int newTopMargin;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mInputFrame.getLayoutParams();
        if (!this.mHintEnabled) {
            newTopMargin = 0;
        } else {
            if (this.mTmpPaint == null) {
                this.mTmpPaint = new Paint();
            }
            Typeface typeface = this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getCollapsedTypeface());
            this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
            newTopMargin = (int) (-this.mTmpPaint.ascent());
        }
        if (newTopMargin != lp.topMargin) {
            lp.topMargin = newTopMargin;
            this.mInputFrame.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateLabelState(boolean z) {
        boolean animate = z;
        boolean isEnabled = isEnabled();
        boolean hasText = this.mEditText != null && !TextUtils.isEmpty(this.mEditText.getText());
        boolean isFocused = arrayContains(getDrawableState(), 16842908);
        boolean isErrorShowing = !TextUtils.isEmpty(getError());
        if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setExpandedTextColor(this.mDefaultTextColor);
        }
        if (isEnabled && this.mCounterOverflowed && this.mCounterView != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mCounterView.getTextColors());
        } else if (isEnabled && isFocused && this.mFocusedTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mFocusedTextColor);
        } else if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mDefaultTextColor);
        }
        if (!hasText && (!isEnabled() || (!isFocused && !isErrorShowing))) {
            if (!this.mHintExpanded) {
                expandHint(animate);
            }
        } else if (this.mHintExpanded) {
            collapseHint(animate);
        }
    }

    @Nullable
    public EditText getEditText() {
        return this.mEditText;
    }

    public void setHint(@Nullable CharSequence charSequence) {
        CharSequence hint = charSequence;
        CharSequence charSequence2 = hint;
        if (this.mHintEnabled) {
            setHintInternal(hint);
            sendAccessibilityEvent(2048);
        }
    }

    private void setHintInternal(CharSequence charSequence) {
        CharSequence hint = charSequence;
        CharSequence charSequence2 = hint;
        this.mHint = hint;
        this.mCollapsingTextHelper.setText(hint);
    }

    @Nullable
    public CharSequence getHint() {
        return !this.mHintEnabled ? null : this.mHint;
    }

    public void setHintEnabled(boolean z) {
        boolean enabled = z;
        if (enabled != this.mHintEnabled) {
            this.mHintEnabled = enabled;
            CharSequence editTextHint = this.mEditText.getHint();
            if (!this.mHintEnabled) {
                if (!TextUtils.isEmpty(this.mHint) && TextUtils.isEmpty(editTextHint)) {
                    this.mEditText.setHint(this.mHint);
                }
                setHintInternal(null);
            } else if (!TextUtils.isEmpty(editTextHint)) {
                if (TextUtils.isEmpty(this.mHint)) {
                    setHint(editTextHint);
                }
                this.mEditText.setHint(null);
            }
            if (this.mEditText != null) {
                updateInputLayoutMargins();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.mHintEnabled;
    }

    public void setHintTextAppearance(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        this.mCollapsingTextHelper.setCollapsedTextAppearance(resId);
        this.mFocusedTextColor = this.mCollapsingTextHelper.getCollapsedTextColor();
        if (this.mEditText != null) {
            updateLabelState(false);
            updateInputLayoutMargins();
        }
    }

    private void addIndicator(TextView textView, int i) {
        TextView indicator = textView;
        int index = i;
        TextView textView2 = indicator;
        int i2 = index;
        if (this.mIndicatorArea == null) {
            this.mIndicatorArea = new LinearLayout(getContext());
            this.mIndicatorArea.setOrientation(0);
            addView(this.mIndicatorArea, -1, -2);
            this.mIndicatorArea.addView(new Space(getContext()), new LinearLayout.LayoutParams(0, 0, 1.0f));
            if (this.mEditText != null) {
                adjustIndicatorPadding();
            }
        }
        this.mIndicatorArea.setVisibility(0);
        this.mIndicatorArea.addView(indicator, index);
        this.mIndicatorsAdded++;
    }

    private void adjustIndicatorPadding() {
        ViewCompat.setPaddingRelative(this.mIndicatorArea, ViewCompat.getPaddingStart(this.mEditText), 0, ViewCompat.getPaddingEnd(this.mEditText), this.mEditText.getPaddingBottom());
    }

    private void removeIndicator(TextView textView) {
        TextView indicator = textView;
        TextView textView2 = indicator;
        if (this.mIndicatorArea != null) {
            this.mIndicatorArea.removeView(indicator);
            int i = this.mIndicatorsAdded - 1;
            this.mIndicatorsAdded = i;
            if (i == 0) {
                this.mIndicatorArea.setVisibility(8);
            }
        }
    }

    public void setErrorEnabled(boolean z) {
        boolean enabled = z;
        if (this.mErrorEnabled != enabled) {
            if (this.mErrorView != null) {
                ViewCompat.animate(this.mErrorView).cancel();
            }
            if (!enabled) {
                this.mErrorShown = false;
                updateEditTextBackground();
                removeIndicator(this.mErrorView);
                this.mErrorView = null;
            } else {
                this.mErrorView = new AppCompatTextView(getContext());
                this.mErrorView.setId(C0001R.C0003id.textinput_error);
                if (this.mTypeface != null) {
                    this.mErrorView.setTypeface(this.mTypeface);
                }
                boolean useDefaultColor = false;
                try {
                    TextViewCompat.setTextAppearance(this.mErrorView, this.mErrorTextAppearance);
                    if (VERSION.SDK_INT >= 23) {
                        if (this.mErrorView.getTextColors().getDefaultColor() == -65281) {
                            useDefaultColor = true;
                        }
                    }
                } catch (Exception e) {
                    Exception exc = e;
                    useDefaultColor = true;
                }
                if (useDefaultColor) {
                    TextViewCompat.setTextAppearance(this.mErrorView, C0268R.style.TextAppearance_AppCompat_Caption);
                    this.mErrorView.setTextColor(ContextCompat.getColor(getContext(), C0001R.color.design_textinput_error_color_light));
                }
                this.mErrorView.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion(this.mErrorView, 1);
                addIndicator(this.mErrorView, 0);
            }
            this.mErrorEnabled = enabled;
        }
    }

    public void setErrorTextAppearance(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        this.mErrorTextAppearance = resId;
        if (this.mErrorView != null) {
            TextViewCompat.setTextAppearance(this.mErrorView, resId);
        }
    }

    public boolean isErrorEnabled() {
        return this.mErrorEnabled;
    }

    public void setError(@Nullable CharSequence charSequence) {
        CharSequence error = charSequence;
        CharSequence charSequence2 = error;
        setError(error, ViewCompat.isLaidOut(this) && isEnabled() && (this.mErrorView == null || !TextUtils.equals(this.mErrorView.getText(), error)));
    }

    private void setError(@Nullable CharSequence charSequence, boolean z) {
        CharSequence error = charSequence;
        CharSequence charSequence2 = error;
        boolean animate = z;
        this.mError = error;
        if (!this.mErrorEnabled) {
            if (!TextUtils.isEmpty(error)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        this.mErrorShown = !TextUtils.isEmpty(error);
        ViewCompat.animate(this.mErrorView).cancel();
        if (this.mErrorShown) {
            this.mErrorView.setText(error);
            this.mErrorView.setVisibility(0);
            if (!animate) {
                ViewCompat.setAlpha(this.mErrorView, 1.0f);
            } else {
                if (ViewCompat.getAlpha(this.mErrorView) == 1.0f) {
                    ViewCompat.setAlpha(this.mErrorView, 0.0f);
                }
                ViewPropertyAnimatorCompat interpolator = ViewCompat.animate(this.mErrorView).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
                C00522 r0 = new ViewPropertyAnimatorListenerAdapter(this) {
                    final /* synthetic */ TextInputLayout this$0;

                    {
                        TextInputLayout this$02 = r5;
                        TextInputLayout textInputLayout = this$02;
                        this.this$0 = this$02;
                    }

                    public void onAnimationStart(View view) {
                        View view2 = view;
                        View view3 = view2;
                        view2.setVisibility(0);
                    }
                };
                interpolator.setListener(r0).start();
            }
        } else if (this.mErrorView.getVisibility() == 0) {
            if (!animate) {
                this.mErrorView.setText(error);
                this.mErrorView.setVisibility(4);
            } else {
                ViewPropertyAnimatorCompat interpolator2 = ViewCompat.animate(this.mErrorView).alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
                final CharSequence charSequence3 = error;
                C00533 r02 = new ViewPropertyAnimatorListenerAdapter(this) {
                    final /* synthetic */ TextInputLayout this$0;

                    {
                        TextInputLayout this$02 = r6;
                        CharSequence charSequence = r7;
                        TextInputLayout textInputLayout = this$02;
                        this.this$0 = this$02;
                    }

                    public void onAnimationEnd(View view) {
                        View view2 = view;
                        View view3 = view2;
                        this.this$0.mErrorView.setText(charSequence3);
                        view2.setVisibility(4);
                    }
                };
                interpolator2.setListener(r02).start();
            }
        }
        updateEditTextBackground();
        updateLabelState(animate);
    }

    public void setCounterEnabled(boolean z) {
        boolean enabled = z;
        if (this.mCounterEnabled != enabled) {
            if (!enabled) {
                removeIndicator(this.mCounterView);
                this.mCounterView = null;
            } else {
                this.mCounterView = new AppCompatTextView(getContext());
                this.mCounterView.setId(C0001R.C0003id.textinput_counter);
                if (this.mTypeface != null) {
                    this.mCounterView.setTypeface(this.mTypeface);
                }
                this.mCounterView.setMaxLines(1);
                try {
                    TextViewCompat.setTextAppearance(this.mCounterView, this.mCounterTextAppearance);
                } catch (Exception e) {
                    Exception exc = e;
                    TextViewCompat.setTextAppearance(this.mCounterView, C0268R.style.TextAppearance_AppCompat_Caption);
                    this.mCounterView.setTextColor(ContextCompat.getColor(getContext(), C0001R.color.design_textinput_error_color_light));
                }
                addIndicator(this.mCounterView, -1);
                if (this.mEditText != null) {
                    updateCounter(this.mEditText.getText().length());
                } else {
                    updateCounter(0);
                }
            }
            this.mCounterEnabled = enabled;
        }
    }

    public boolean isCounterEnabled() {
        return this.mCounterEnabled;
    }

    public void setCounterMaxLength(int i) {
        int maxLength = i;
        int i2 = maxLength;
        if (this.mCounterMaxLength != maxLength) {
            if (maxLength <= 0) {
                this.mCounterMaxLength = -1;
            } else {
                this.mCounterMaxLength = maxLength;
            }
            if (this.mCounterEnabled) {
                updateCounter(this.mEditText != null ? this.mEditText.getText().length() : 0);
            }
        }
    }

    public void setEnabled(boolean z) {
        boolean enabled = z;
        recursiveSetEnabled(this, enabled);
        super.setEnabled(enabled);
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean z) {
        ViewGroup vg = viewGroup;
        ViewGroup viewGroup2 = vg;
        boolean enabled = z;
        int count = vg.getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = vg.getChildAt(i);
            View child = childAt;
            childAt.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) child, enabled);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.mCounterMaxLength;
    }

    /* access modifiers changed from: 0000 */
    public void updateCounter(int i) {
        int length = i;
        int i2 = length;
        boolean wasCounterOverflowed = this.mCounterOverflowed;
        if (this.mCounterMaxLength != -1) {
            this.mCounterOverflowed = length > this.mCounterMaxLength;
            if (wasCounterOverflowed != this.mCounterOverflowed) {
                TextViewCompat.setTextAppearance(this.mCounterView, !this.mCounterOverflowed ? this.mCounterTextAppearance : this.mCounterOverflowTextAppearance);
            }
            TextView textView = this.mCounterView;
            Context context = getContext();
            int i3 = C0001R.string.character_counter_pattern;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(length);
            objArr[1] = Integer.valueOf(this.mCounterMaxLength);
            textView.setText(context.getString(i3, objArr));
        } else {
            this.mCounterView.setText(String.valueOf(length));
            this.mCounterOverflowed = false;
        }
        if (this.mEditText != null && wasCounterOverflowed != this.mCounterOverflowed) {
            updateLabelState(false);
            updateEditTextBackground();
        }
    }

    private void updateEditTextBackground() {
        if (this.mEditText != null) {
            Drawable background = this.mEditText.getBackground();
            Drawable editTextBackground = background;
            if (background != null) {
                ensureBackgroundDrawableStateWorkaround();
                if (DrawableUtils.canSafelyMutateDrawable(editTextBackground)) {
                    editTextBackground = editTextBackground.mutate();
                }
                if (this.mErrorShown && this.mErrorView != null) {
                    editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.mErrorView.getCurrentTextColor(), Mode.SRC_IN));
                } else if (this.mCounterOverflowed && this.mCounterView != null) {
                    editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.mCounterView.getCurrentTextColor(), Mode.SRC_IN));
                } else {
                    DrawableCompat.clearColorFilter(editTextBackground);
                    this.mEditText.refreshDrawableState();
                }
            }
        }
    }

    private void ensureBackgroundDrawableStateWorkaround() {
        int i = VERSION.SDK_INT;
        int sdk = i;
        if (i == 21 || sdk == 22) {
            Drawable background = this.mEditText.getBackground();
            Drawable bg = background;
            if (background != null && !this.mHasReconstructedEditTextBackground) {
                Drawable newBg = bg.getConstantState().newDrawable();
                if (bg instanceof DrawableContainer) {
                    this.mHasReconstructedEditTextBackground = DrawableUtils.setContainerConstantState((DrawableContainer) bg, newBg.getConstantState());
                }
                if (!this.mHasReconstructedEditTextBackground) {
                    ViewCompat.setBackground(this.mEditText, newBg);
                    this.mHasReconstructedEditTextBackground = true;
                }
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (this.mErrorShown) {
            ss.error = getError();
        }
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            setError(ss.error);
            requestLayout();
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> container = sparseArray;
        SparseArray<Parcelable> sparseArray2 = container;
        this.mRestoringSavedState = true;
        super.dispatchRestoreInstanceState(container);
        this.mRestoringSavedState = false;
    }

    @Nullable
    public CharSequence getError() {
        return !this.mErrorEnabled ? null : this.mError;
    }

    public boolean isHintAnimationEnabled() {
        return this.mHintAnimationEnabled;
    }

    public void setHintAnimationEnabled(boolean z) {
        this.mHintAnimationEnabled = z;
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.draw(canvas2);
        if (this.mHintEnabled) {
            this.mCollapsingTextHelper.draw(canvas2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        updatePasswordToggleView();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void updatePasswordToggleView() {
        if (this.mEditText != null) {
            if (!shouldShowPasswordIcon()) {
                if (this.mPasswordToggleView != null && this.mPasswordToggleView.getVisibility() == 0) {
                    this.mPasswordToggleView.setVisibility(8);
                }
                if (this.mPasswordToggleDummyDrawable != null) {
                    Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this.mEditText);
                    Drawable[] compounds = compoundDrawablesRelative;
                    if (compoundDrawablesRelative[2] == this.mPasswordToggleDummyDrawable) {
                        TextViewCompat.setCompoundDrawablesRelative(this.mEditText, compounds[0], compounds[1], this.mOriginalEditTextEndDrawable, compounds[3]);
                        this.mPasswordToggleDummyDrawable = null;
                    }
                }
            } else {
                if (this.mPasswordToggleView == null) {
                    this.mPasswordToggleView = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(C0001R.layout.design_text_input_password_icon, this.mInputFrame, false);
                    this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
                    this.mPasswordToggleView.setContentDescription(this.mPasswordToggleContentDesc);
                    this.mInputFrame.addView(this.mPasswordToggleView);
                    this.mPasswordToggleView.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ TextInputLayout this$0;

                        {
                            TextInputLayout this$02 = r5;
                            TextInputLayout textInputLayout = this$02;
                            this.this$0 = this$02;
                        }

                        public void onClick(View view) {
                            View view2 = view;
                            this.this$0.passwordVisibilityToggleRequested();
                        }
                    });
                }
                if (this.mEditText != null && ViewCompat.getMinimumHeight(this.mEditText) <= 0) {
                    this.mEditText.setMinimumHeight(ViewCompat.getMinimumHeight(this.mPasswordToggleView));
                }
                this.mPasswordToggleView.setVisibility(0);
                this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
                if (this.mPasswordToggleDummyDrawable == null) {
                    this.mPasswordToggleDummyDrawable = new ColorDrawable();
                }
                this.mPasswordToggleDummyDrawable.setBounds(0, 0, this.mPasswordToggleView.getMeasuredWidth(), 1);
                Drawable[] compoundDrawablesRelative2 = TextViewCompat.getCompoundDrawablesRelative(this.mEditText);
                Drawable[] compounds2 = compoundDrawablesRelative2;
                if (compoundDrawablesRelative2[2] != this.mPasswordToggleDummyDrawable) {
                    this.mOriginalEditTextEndDrawable = compounds2[2];
                }
                TextViewCompat.setCompoundDrawablesRelative(this.mEditText, compounds2[0], compounds2[1], this.mPasswordToggleDummyDrawable, compounds2[3]);
                this.mPasswordToggleView.setPadding(this.mEditText.getPaddingLeft(), this.mEditText.getPaddingTop(), this.mEditText.getPaddingRight(), this.mEditText.getPaddingBottom());
            }
        }
    }

    public void setPasswordVisibilityToggleDrawable(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setPasswordVisibilityToggleDrawable(resId == 0 ? null : AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mPasswordToggleDrawable = icon;
        if (this.mPasswordToggleView != null) {
            this.mPasswordToggleView.setImageDrawable(icon);
        }
    }

    public void setPasswordVisibilityToggleContentDescription(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        setPasswordVisibilityToggleContentDescription(resId == 0 ? null : getResources().getText(resId));
    }

    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        CharSequence description = charSequence;
        CharSequence charSequence2 = description;
        this.mPasswordToggleContentDesc = description;
        if (this.mPasswordToggleView != null) {
            this.mPasswordToggleView.setContentDescription(description);
        }
    }

    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.mPasswordToggleDrawable;
    }

    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.mPasswordToggleContentDesc;
    }

    public boolean isPasswordVisibilityToggleEnabled() {
        return this.mPasswordToggleEnabled;
    }

    public void setPasswordVisibilityToggleEnabled(boolean z) {
        boolean enabled = z;
        if (this.mPasswordToggleEnabled != enabled) {
            this.mPasswordToggleEnabled = enabled;
            if (!enabled && this.mPasswordToggledVisible && this.mEditText != null) {
                this.mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.mPasswordToggledVisible = false;
            updatePasswordToggleView();
        }
    }

    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tintList = colorStateList;
        ColorStateList colorStateList2 = tintList;
        this.mPasswordToggleTintList = tintList;
        this.mHasPasswordToggleTintList = true;
        applyPasswordToggleTint();
    }

    public void setPasswordVisibilityToggleTintMode(@Nullable Mode mode) {
        Mode mode2 = mode;
        Mode mode3 = mode2;
        this.mPasswordToggleTintMode = mode2;
        this.mHasPasswordToggleTintMode = true;
        applyPasswordToggleTint();
    }

    /* access modifiers changed from: 0000 */
    public void passwordVisibilityToggleRequested() {
        if (this.mPasswordToggleEnabled) {
            int selection = this.mEditText.getSelectionEnd();
            if (!hasPasswordTransformation()) {
                this.mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.mPasswordToggledVisible = false;
            } else {
                this.mEditText.setTransformationMethod(null);
                this.mPasswordToggledVisible = true;
            }
            this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
            this.mEditText.setSelection(selection);
        }
    }

    private boolean hasPasswordTransformation() {
        return this.mEditText != null && (this.mEditText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private boolean shouldShowPasswordIcon() {
        return this.mPasswordToggleEnabled && (hasPasswordTransformation() || this.mPasswordToggledVisible);
    }

    private void applyPasswordToggleTint() {
        if (this.mPasswordToggleDrawable != null) {
            if (this.mHasPasswordToggleTintList || this.mHasPasswordToggleTintMode) {
                this.mPasswordToggleDrawable = DrawableCompat.wrap(this.mPasswordToggleDrawable).mutate();
                if (this.mHasPasswordToggleTintList) {
                    DrawableCompat.setTintList(this.mPasswordToggleDrawable, this.mPasswordToggleTintList);
                }
                if (this.mHasPasswordToggleTintMode) {
                    DrawableCompat.setTintMode(this.mPasswordToggleDrawable, this.mPasswordToggleTintMode);
                }
                if (this.mPasswordToggleView != null && this.mPasswordToggleView.getDrawable() != this.mPasswordToggleDrawable) {
                    this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        super.onLayout(z, left, top, right, bottom);
        if (this.mHintEnabled && this.mEditText != null) {
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(this, this.mEditText, rect);
            int l = rect.left + this.mEditText.getCompoundPaddingLeft();
            int compoundPaddingRight = rect.right - this.mEditText.getCompoundPaddingRight();
            int i9 = compoundPaddingRight;
            this.mCollapsingTextHelper.setExpandedBounds(l, rect.top + this.mEditText.getCompoundPaddingTop(), compoundPaddingRight, rect.bottom - this.mEditText.getCompoundPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(l, getPaddingTop(), compoundPaddingRight, (bottom - top) - getPaddingBottom());
            this.mCollapsingTextHelper.recalculate();
        }
    }

    private void collapseHint(boolean z) {
        boolean animate = z;
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (animate && this.mHintAnimationEnabled) {
            animateToExpansionFraction(1.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(1.0f);
        }
        this.mHintExpanded = false;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.mInDrawableStateChanged) {
            this.mInDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] state = getDrawableState();
            boolean changed = false;
            updateLabelState(ViewCompat.isLaidOut(this) && isEnabled());
            updateEditTextBackground();
            if (this.mCollapsingTextHelper != null) {
                changed = false | this.mCollapsingTextHelper.setState(state);
            }
            if (changed) {
                invalidate();
            }
            this.mInDrawableStateChanged = false;
        }
    }

    private void expandHint(boolean z) {
        boolean animate = z;
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (animate && this.mHintAnimationEnabled) {
            animateToExpansionFraction(0.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(0.0f);
        }
        this.mHintExpanded = true;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void animateToExpansionFraction(float f) {
        float target = f;
        float f2 = target;
        if (this.mCollapsingTextHelper.getExpansionFraction() != target) {
            if (this.mAnimator == null) {
                this.mAnimator = ViewUtils.createAnimator();
                this.mAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
                this.mAnimator.setDuration(200);
                this.mAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                    final /* synthetic */ TextInputLayout this$0;

                    {
                        TextInputLayout this$02 = r5;
                        TextInputLayout textInputLayout = this$02;
                        this.this$0 = this$02;
                    }

                    public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                        ValueAnimatorCompat animator = valueAnimatorCompat;
                        ValueAnimatorCompat valueAnimatorCompat2 = animator;
                        this.this$0.mCollapsingTextHelper.setExpansionFraction(animator.getAnimatedFloatValue());
                    }
                });
            }
            this.mAnimator.setFloatValues(this.mCollapsingTextHelper.getExpansionFraction(), target);
            this.mAnimator.start();
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final boolean isHintExpanded() {
        return this.mHintExpanded;
    }

    private static boolean arrayContains(int[] iArr, int i) {
        int[] array = iArr;
        int value = i;
        int[] iArr2 = array;
        int i2 = value;
        for (int i3 : array) {
            int i4 = i3;
            if (i3 == value) {
                return true;
            }
        }
        return false;
    }
}
