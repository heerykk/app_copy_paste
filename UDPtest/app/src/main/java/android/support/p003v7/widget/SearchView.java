package android.support.p003v7.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.content.res.ConfigurationHelper;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.KeyEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.CursorAdapter;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.CollapsibleActionView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* renamed from: android.support.v7.widget.SearchView */
public class SearchView extends LinearLayoutCompat implements CollapsibleActionView {
    static final boolean DBG = false;
    static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    static final String LOG_TAG = "SearchView";
    private Bundle mAppSearchData;
    private boolean mClearingFocus;
    final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private final CharSequence mDefaultQueryHint;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    private final OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final OnEditorActionListener mOnEditorActionListener;
    private final OnItemClickListener mOnItemClickListener;
    private final OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, ConstantState> mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable;
    final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final View mSearchPlate;
    final SearchAutoComplete mSearchSrcTextView;
    private Rect mSearchSrcTextViewBounds;
    private Rect mSearchSrtTextViewBoundsExpanded;
    SearchableInfo mSearchable;
    private Runnable mShowImeRunnable;
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    CursorAdapter mSuggestionsAdapter;
    private int[] mTemp;
    private int[] mTemp2;
    OnKeyListener mTextKeyListener;
    private TextWatcher mTextWatcher;
    private UpdatableTouchDelegate mTouchDelegate;
    private final Runnable mUpdateDrawableStateRunnable;
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    /* renamed from: android.support.v7.widget.SearchView$AutoCompleteTextViewReflector */
    private static class AutoCompleteTextViewReflector {
        private Method doAfterTextChanged;
        private Method doBeforeTextChanged;
        private Method ensureImeVisible;
        private Method showSoftInputUnchecked;

        AutoCompleteTextViewReflector() {
            try {
                this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.doBeforeTextChanged.setAccessible(true);
            } catch (NoSuchMethodException e) {
            }
            try {
                this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.doAfterTextChanged.setAccessible(true);
            } catch (NoSuchMethodException e2) {
            }
            Class<AutoCompleteTextView> cls = AutoCompleteTextView.class;
            String str = "ensureImeVisible";
            try {
                Class[] clsArr = new Class[1];
                clsArr[0] = Boolean.TYPE;
                this.ensureImeVisible = cls.getMethod(str, clsArr);
                this.ensureImeVisible.setAccessible(true);
            } catch (NoSuchMethodException e3) {
            }
            Class<InputMethodManager> cls2 = InputMethodManager.class;
            String str2 = "showSoftInputUnchecked";
            try {
                Class[] clsArr2 = new Class[2];
                clsArr2[0] = Integer.TYPE;
                clsArr2[1] = ResultReceiver.class;
                this.showSoftInputUnchecked = cls2.getMethod(str2, clsArr2);
                this.showSoftInputUnchecked.setAccessible(true);
            } catch (NoSuchMethodException e4) {
            }
        }

        /* access modifiers changed from: 0000 */
        public void doBeforeTextChanged(AutoCompleteTextView autoCompleteTextView) {
            AutoCompleteTextView view = autoCompleteTextView;
            AutoCompleteTextView autoCompleteTextView2 = view;
            if (this.doBeforeTextChanged != null) {
                try {
                    Object invoke = this.doBeforeTextChanged.invoke(view, new Object[0]);
                } catch (Exception e) {
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void doAfterTextChanged(AutoCompleteTextView autoCompleteTextView) {
            AutoCompleteTextView view = autoCompleteTextView;
            AutoCompleteTextView autoCompleteTextView2 = view;
            if (this.doAfterTextChanged != null) {
                try {
                    Object invoke = this.doAfterTextChanged.invoke(view, new Object[0]);
                } catch (Exception e) {
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void ensureImeVisible(AutoCompleteTextView autoCompleteTextView, boolean z) {
            AutoCompleteTextView view = autoCompleteTextView;
            AutoCompleteTextView autoCompleteTextView2 = view;
            boolean visible = z;
            if (this.ensureImeVisible != null) {
                try {
                    Method method = this.ensureImeVisible;
                    Object[] objArr = new Object[1];
                    objArr[0] = Boolean.valueOf(visible);
                    Object invoke = method.invoke(view, objArr);
                } catch (Exception e) {
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void showSoftInputUnchecked(InputMethodManager inputMethodManager, View view, int i) {
            InputMethodManager imm = inputMethodManager;
            View view2 = view;
            int flags = i;
            InputMethodManager inputMethodManager2 = imm;
            View view3 = view2;
            int i2 = flags;
            if (this.showSoftInputUnchecked != null) {
                try {
                    Method method = this.showSoftInputUnchecked;
                    Object[] objArr = new Object[2];
                    objArr[0] = Integer.valueOf(flags);
                    objArr[1] = null;
                    Object invoke = method.invoke(imm, objArr);
                    return;
                } catch (Exception e) {
                }
            }
            boolean showSoftInput = imm.showSoftInput(view2, flags);
        }
    }

    /* renamed from: android.support.v7.widget.SearchView$OnCloseListener */
    public interface OnCloseListener {
        boolean onClose();
    }

    /* renamed from: android.support.v7.widget.SearchView$OnQueryTextListener */
    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    /* renamed from: android.support.v7.widget.SearchView$OnSuggestionListener */
    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    /* renamed from: android.support.v7.widget.SearchView$SavedState */
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
        boolean isIconified;

        SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel source = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = source;
            ClassLoader classLoader2 = loader;
            super(source, loader);
            this.isIconified = ((Boolean) source.readValue(null)).booleanValue();
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            dest.writeValue(Boolean.valueOf(this.isIconified));
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}";
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.SearchView$SearchAutoComplete */
    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {
        private SearchView mSearchView;
        private int mThreshold;

        static /* synthetic */ boolean access$000(SearchAutoComplete searchAutoComplete) {
            SearchAutoComplete x0 = searchAutoComplete;
            SearchAutoComplete searchAutoComplete2 = x0;
            return x0.isEmpty();
        }

        public SearchAutoComplete(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this(context2, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            this(context2, attrs, C0268R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            int defStyle = i;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            int i2 = defStyle;
            super(context2, attrs, defStyle);
            this.mThreshold = getThreshold();
        }

        /* access modifiers changed from: protected */
        public void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, (float) getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        /* access modifiers changed from: 0000 */
        public void setSearchView(SearchView searchView) {
            SearchView searchView2 = searchView;
            SearchView searchView3 = searchView2;
            this.mSearchView = searchView2;
        }

        public void setThreshold(int i) {
            int threshold = i;
            int i2 = threshold;
            super.setThreshold(threshold);
            this.mThreshold = threshold;
        }

        private boolean isEmpty() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        /* access modifiers changed from: protected */
        public void replaceText(CharSequence charSequence) {
            CharSequence charSequence2 = charSequence;
        }

        public void performCompletion() {
        }

        public void onWindowFocusChanged(boolean z) {
            boolean hasWindowFocus = z;
            super.onWindowFocusChanged(hasWindowFocus);
            if (hasWindowFocus && this.mSearchView.hasFocus() && getVisibility() == 0) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
                InputMethodManager inputMethodManager2 = inputMethodManager;
                boolean showSoftInput = inputMethodManager.showSoftInput(this, 0);
                if (SearchView.isLandscapeMode(getContext())) {
                    SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onFocusChanged(boolean z, int i, Rect rect) {
            int direction = i;
            Rect previouslyFocusedRect = rect;
            int i2 = direction;
            Rect rect2 = previouslyFocusedRect;
            super.onFocusChanged(z, direction, previouslyFocusedRect);
            this.mSearchView.onTextFocusChanged();
        }

        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            int keyCode = i;
            KeyEvent event = keyEvent;
            int i2 = keyCode;
            KeyEvent keyEvent2 = event;
            if (keyCode == 4) {
                if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                    DispatcherState keyDispatcherState = getKeyDispatcherState();
                    DispatcherState state = keyDispatcherState;
                    if (keyDispatcherState != null) {
                        state.startTracking(event, this);
                    }
                    return true;
                } else if (event.getAction() == 1) {
                    DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    DispatcherState state2 = keyDispatcherState2;
                    if (keyDispatcherState2 != null) {
                        state2.handleUpEvent(event);
                    }
                    if (event.isTracking() && !event.isCanceled()) {
                        this.mSearchView.clearFocus();
                        this.mSearchView.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(keyCode, event);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration config = getResources().getConfiguration();
            int widthDp = ConfigurationHelper.getScreenWidthDp(getResources());
            int heightDp = ConfigurationHelper.getScreenHeightDp(getResources());
            if (widthDp >= 960 && heightDp >= 720 && config.orientation == 2) {
                return 256;
            }
            if (widthDp < 600 && (widthDp < 640 || heightDp < 480)) {
                return 160;
            }
            return 192;
        }
    }

    /* renamed from: android.support.v7.widget.SearchView$UpdatableTouchDelegate */
    private static class UpdatableTouchDelegate extends TouchDelegate {
        private final Rect mActualBounds = new Rect();
        private boolean mDelegateTargeted;
        private final View mDelegateView;
        private final int mSlop;
        private final Rect mSlopBounds = new Rect();
        private final Rect mTargetBounds = new Rect();

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            Rect targetBounds = rect;
            Rect actualBounds = rect2;
            View delegateView = view;
            Rect rect3 = targetBounds;
            Rect rect4 = actualBounds;
            View view2 = delegateView;
            super(targetBounds, delegateView);
            this.mSlop = ViewConfiguration.get(delegateView.getContext()).getScaledTouchSlop();
            setBounds(targetBounds, actualBounds);
            this.mDelegateView = delegateView;
        }

        public void setBounds(Rect rect, Rect rect2) {
            Rect desiredBounds = rect;
            Rect actualBounds = rect2;
            Rect rect3 = desiredBounds;
            Rect rect4 = actualBounds;
            this.mTargetBounds.set(desiredBounds);
            this.mSlopBounds.set(desiredBounds);
            this.mSlopBounds.inset(-this.mSlop, -this.mSlop);
            this.mActualBounds.set(actualBounds);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            MotionEvent event = motionEvent;
            MotionEvent motionEvent2 = event;
            int x = (int) event.getX();
            int y = (int) event.getY();
            boolean sendToDelegate = false;
            boolean hit = true;
            boolean handled = false;
            switch (event.getAction()) {
                case 0:
                    if (this.mTargetBounds.contains(x, y)) {
                        this.mDelegateTargeted = true;
                        sendToDelegate = true;
                        break;
                    }
                    break;
                case 1:
                case 2:
                    boolean z = this.mDelegateTargeted;
                    sendToDelegate = z;
                    if (z && !this.mSlopBounds.contains(x, y)) {
                        hit = false;
                        break;
                    }
                case 3:
                    sendToDelegate = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                    break;
            }
            if (sendToDelegate) {
                if (hit && !this.mActualBounds.contains(x, y)) {
                    event.setLocation((float) (this.mDelegateView.getWidth() / 2), (float) (this.mDelegateView.getHeight() / 2));
                } else {
                    event.setLocation((float) (x - this.mActualBounds.left), (float) (y - this.mActualBounds.top));
                }
                handled = this.mDelegateView.dispatchTouchEvent(event);
            }
            return handled;
        }
    }

    public SearchView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mSearchSrcTextViewBounds = new Rect();
        this.mSearchSrtTextViewBoundsExpanded = new Rect();
        this.mTemp = new int[2];
        this.mTemp2 = new int[2];
        this.mShowImeRunnable = new Runnable(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) this.this$0.getContext().getSystemService("input_method");
                InputMethodManager imm = inputMethodManager;
                if (inputMethodManager != null) {
                    SearchView.HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(imm, this.this$0, 0);
                }
            }
        };
        this.mUpdateDrawableStateRunnable = new Runnable(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                this.this$0.updateFocusedState();
            }
        };
        this.mReleaseCursorRunnable = new Runnable(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                if (this.this$0.mSuggestionsAdapter != null && (this.this$0.mSuggestionsAdapter instanceof SuggestionsAdapter)) {
                    this.this$0.mSuggestionsAdapter.changeCursor(null);
                }
            }
        };
        this.mOutsideDrawablesCache = new WeakHashMap<>();
        this.mOnClickListener = new OnClickListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void onClick(View view) {
                View v = view;
                View view2 = v;
                if (v == this.this$0.mSearchButton) {
                    this.this$0.onSearchClicked();
                } else if (v == this.this$0.mCloseButton) {
                    this.this$0.onCloseClicked();
                } else if (v == this.this$0.mGoButton) {
                    this.this$0.onSubmitQuery();
                } else if (v == this.this$0.mVoiceButton) {
                    this.this$0.onVoiceClicked();
                } else if (v == this.this$0.mSearchSrcTextView) {
                    this.this$0.forceSuggestionQuery();
                }
            }
        };
        this.mTextKeyListener = new OnKeyListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                View v = view;
                int keyCode = i;
                KeyEvent event = keyEvent;
                View view2 = v;
                int i2 = keyCode;
                KeyEvent keyEvent2 = event;
                if (this.this$0.mSearchable == null) {
                    return false;
                }
                if (this.this$0.mSearchSrcTextView.isPopupShowing() && this.this$0.mSearchSrcTextView.getListSelection() != -1) {
                    return this.this$0.onSuggestionsKey(v, keyCode, event);
                }
                if (SearchAutoComplete.access$000(this.this$0.mSearchSrcTextView) || !KeyEventCompat.hasNoModifiers(event) || event.getAction() != 1 || keyCode != 66) {
                    return false;
                }
                v.cancelLongPress();
                this.this$0.launchQuerySearch(0, null, this.this$0.mSearchSrcTextView.getText().toString());
                return true;
            }
        };
        this.mOnEditorActionListener = new OnEditorActionListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                TextView textView2 = textView;
                int i2 = i;
                KeyEvent keyEvent2 = keyEvent;
                this.this$0.onSubmitQuery();
                return true;
            }
        };
        this.mOnItemClickListener = new OnItemClickListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int position = i;
                AdapterView<?> adapterView2 = adapterView;
                View view2 = view;
                int i2 = position;
                long j2 = j;
                boolean onItemClicked = this.this$0.onItemClicked(position, 0, null);
            }
        };
        this.mOnItemSelectedListener = new OnItemSelectedListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                int position = i;
                AdapterView<?> adapterView2 = adapterView;
                View view2 = view;
                int i2 = position;
                long j2 = j;
                boolean onItemSelected = this.this$0.onItemSelected(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                AdapterView<?> adapterView2 = adapterView;
            }
        };
        this.mTextWatcher = new TextWatcher(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CharSequence charSequence2 = charSequence;
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CharSequence s = charSequence;
                CharSequence charSequence2 = s;
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                this.this$0.onTextChanged(s);
            }

            public void afterTextChanged(Editable editable) {
                Editable editable2 = editable;
            }
        };
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0268R.styleable.SearchView, defStyleAttr, 0);
        LayoutInflater inflater = LayoutInflater.from(context2);
        int resourceId = a.getResourceId(C0268R.styleable.SearchView_layout, C0268R.layout.abc_search_view);
        int i3 = resourceId;
        View inflate = inflater.inflate(resourceId, this, true);
        this.mSearchSrcTextView = (SearchAutoComplete) findViewById(C0268R.C0270id.search_src_text);
        this.mSearchSrcTextView.setSearchView(this);
        this.mSearchEditFrame = findViewById(C0268R.C0270id.search_edit_frame);
        this.mSearchPlate = findViewById(C0268R.C0270id.search_plate);
        this.mSubmitArea = findViewById(C0268R.C0270id.submit_area);
        this.mSearchButton = (ImageView) findViewById(C0268R.C0270id.search_button);
        this.mGoButton = (ImageView) findViewById(C0268R.C0270id.search_go_btn);
        this.mCloseButton = (ImageView) findViewById(C0268R.C0270id.search_close_btn);
        this.mVoiceButton = (ImageView) findViewById(C0268R.C0270id.search_voice_btn);
        this.mCollapsedIcon = (ImageView) findViewById(C0268R.C0270id.search_mag_icon);
        ViewCompat.setBackground(this.mSearchPlate, a.getDrawable(C0268R.styleable.SearchView_queryBackground));
        ViewCompat.setBackground(this.mSubmitArea, a.getDrawable(C0268R.styleable.SearchView_submitBackground));
        this.mSearchButton.setImageDrawable(a.getDrawable(C0268R.styleable.SearchView_searchIcon));
        this.mGoButton.setImageDrawable(a.getDrawable(C0268R.styleable.SearchView_goIcon));
        this.mCloseButton.setImageDrawable(a.getDrawable(C0268R.styleable.SearchView_closeIcon));
        this.mVoiceButton.setImageDrawable(a.getDrawable(C0268R.styleable.SearchView_voiceIcon));
        this.mCollapsedIcon.setImageDrawable(a.getDrawable(C0268R.styleable.SearchView_searchIcon));
        this.mSearchHintIcon = a.getDrawable(C0268R.styleable.SearchView_searchHintIcon);
        this.mSuggestionRowLayout = a.getResourceId(C0268R.styleable.SearchView_suggestionRowLayout, C0268R.layout.abc_search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = a.getResourceId(C0268R.styleable.SearchView_commitIcon, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void onFocusChange(View view, boolean z) {
                View view2 = view;
                boolean hasFocus = z;
                if (this.this$0.mOnQueryTextFocusChangeListener != null) {
                    this.this$0.mOnQueryTextFocusChangeListener.onFocusChange(this.this$0, hasFocus);
                }
            }
        });
        setIconifiedByDefault(a.getBoolean(C0268R.styleable.SearchView_iconifiedByDefault, true));
        int dimensionPixelSize = a.getDimensionPixelSize(C0268R.styleable.SearchView_android_maxWidth, -1);
        int maxWidth = dimensionPixelSize;
        if (dimensionPixelSize != -1) {
            setMaxWidth(maxWidth);
        }
        this.mDefaultQueryHint = a.getText(C0268R.styleable.SearchView_defaultQueryHint);
        this.mQueryHint = a.getText(C0268R.styleable.SearchView_queryHint);
        int i4 = a.getInt(C0268R.styleable.SearchView_android_imeOptions, -1);
        int imeOptions = i4;
        if (i4 != -1) {
            setImeOptions(imeOptions);
        }
        int i5 = a.getInt(C0268R.styleable.SearchView_android_inputType, -1);
        int inputType = i5;
        if (i5 != -1) {
            setInputType(inputType);
        }
        boolean z = a.getBoolean(C0268R.styleable.SearchView_android_focusable, true);
        boolean focusable = z;
        setFocusable(z);
        a.recycle();
        this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
        Intent addFlags = this.mVoiceWebSearchIntent.addFlags(268435456);
        Intent putExtra = this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        Intent addFlags2 = this.mVoiceAppSearchIntent.addFlags(268435456);
        this.mDropDownAnchor = findViewById(this.mSearchSrcTextView.getDropDownAnchor());
        if (this.mDropDownAnchor != null) {
            if (VERSION.SDK_INT < 11) {
                addOnLayoutChangeListenerToDropDownAnchorBase();
            } else {
                addOnLayoutChangeListenerToDropDownAnchorSDK11();
            }
        }
        updateViewsVisibility(this.mIconifiedByDefault);
        updateQueryHint();
    }

    public SearchView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.searchViewStyle);
    }

    @TargetApi(11)
    private void addOnLayoutChangeListenerToDropDownAnchorSDK11() {
        this.mDropDownAnchor.addOnLayoutChangeListener(new OnLayoutChangeListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                View view2 = view;
                int i9 = i;
                int i10 = i2;
                int i11 = i3;
                int i12 = i4;
                int i13 = i5;
                int i14 = i6;
                int i15 = i7;
                int i16 = i8;
                this.this$0.adjustDropDownSizeAndPosition();
            }
        });
    }

    private void addOnLayoutChangeListenerToDropDownAnchorBase() {
        this.mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ SearchView this$0;

            {
                SearchView this$02 = r5;
                SearchView searchView = this$02;
                this.this$0 = this$02;
            }

            public void onGlobalLayout() {
                this.this$0.adjustDropDownSizeAndPosition();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    /* access modifiers changed from: 0000 */
    public int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        SearchableInfo searchable = searchableInfo;
        SearchableInfo searchableInfo2 = searchable;
        this.mSearchable = searchable;
        if (this.mSearchable != null) {
            updateSearchAutoComplete();
            updateQueryHint();
        }
        this.mVoiceButtonEnabled = hasVoiceSearch();
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        updateViewsVisibility(isIconified());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setAppSearchData(Bundle bundle) {
        Bundle appSearchData = bundle;
        Bundle bundle2 = appSearchData;
        this.mAppSearchData = appSearchData;
    }

    public void setImeOptions(int i) {
        int imeOptions = i;
        int i2 = imeOptions;
        this.mSearchSrcTextView.setImeOptions(imeOptions);
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public void setInputType(int i) {
        int inputType = i;
        int i2 = inputType;
        this.mSearchSrcTextView.setInputType(inputType);
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean requestFocus(int i, Rect rect) {
        int direction = i;
        Rect previouslyFocusedRect = rect;
        int i2 = direction;
        Rect rect2 = previouslyFocusedRect;
        if (this.mClearingFocus) {
            return false;
        }
        if (!isFocusable()) {
            return false;
        }
        if (isIconified()) {
            return super.requestFocus(direction, previouslyFocusedRect);
        }
        boolean requestFocus = this.mSearchSrcTextView.requestFocus(direction, previouslyFocusedRect);
        boolean result = requestFocus;
        if (requestFocus) {
            updateViewsVisibility(false);
        }
        return result;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void clearFocus() {
        this.mClearingFocus = true;
        setImeVisibility(false);
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mClearingFocus = false;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        OnQueryTextListener listener = onQueryTextListener;
        OnQueryTextListener onQueryTextListener2 = listener;
        this.mOnQueryChangeListener = listener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        OnCloseListener listener = onCloseListener;
        OnCloseListener onCloseListener2 = listener;
        this.mOnCloseListener = listener;
    }

    public void setOnQueryTextFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        OnFocusChangeListener listener = onFocusChangeListener;
        OnFocusChangeListener onFocusChangeListener2 = listener;
        this.mOnQueryTextFocusChangeListener = listener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        OnSuggestionListener listener = onSuggestionListener;
        OnSuggestionListener onSuggestionListener2 = listener;
        this.mOnSuggestionListener = listener;
    }

    public void setOnSearchClickListener(OnClickListener onClickListener) {
        OnClickListener listener = onClickListener;
        OnClickListener onClickListener2 = listener;
        this.mOnSearchClickListener = listener;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public void setQuery(CharSequence charSequence, boolean z) {
        CharSequence query = charSequence;
        CharSequence charSequence2 = query;
        boolean submit = z;
        this.mSearchSrcTextView.setText(query);
        if (query != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
            this.mUserQuery = query;
        }
        if (submit && !TextUtils.isEmpty(query)) {
            onSubmitQuery();
        }
    }

    public void setQueryHint(@Nullable CharSequence charSequence) {
        CharSequence hint = charSequence;
        CharSequence charSequence2 = hint;
        this.mQueryHint = hint;
        updateQueryHint();
    }

    @Nullable
    public CharSequence getQueryHint() {
        CharSequence hint;
        if (this.mQueryHint != null) {
            hint = this.mQueryHint;
        } else if (this.mSearchable == null || this.mSearchable.getHintId() == 0) {
            hint = this.mDefaultQueryHint;
        } else {
            hint = getContext().getText(this.mSearchable.getHintId());
        }
        return hint;
    }

    public void setIconifiedByDefault(boolean z) {
        boolean iconified = z;
        if (this.mIconifiedByDefault != iconified) {
            this.mIconifiedByDefault = iconified;
            updateViewsVisibility(iconified);
            updateQueryHint();
        }
    }

    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public void setIconified(boolean z) {
        if (!z) {
            onSearchClicked();
        } else {
            onCloseClicked();
        }
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public void setSubmitButtonEnabled(boolean z) {
        this.mSubmitButtonEnabled = z;
        updateViewsVisibility(isIconified());
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    public void setQueryRefinementEnabled(boolean z) {
        boolean enable = z;
        this.mQueryRefinement = enable;
        if (this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(!enable ? 1 : 2);
        }
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        CursorAdapter adapter = cursorAdapter;
        CursorAdapter cursorAdapter2 = adapter;
        this.mSuggestionsAdapter = adapter;
        this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public void setMaxWidth(int i) {
        int maxpixels = i;
        int i2 = maxpixels;
        this.mMaxWidth = maxpixels;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i4 = widthMeasureSpec;
        int i5 = heightMeasureSpec;
        if (!isIconified()) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec);
            switch (widthMode) {
                case Integer.MIN_VALUE:
                    if (this.mMaxWidth > 0) {
                        width = Math.min(this.mMaxWidth, width);
                        break;
                    } else {
                        width = Math.min(getPreferredWidth(), width);
                        break;
                    }
                case 0:
                    if (this.mMaxWidth <= 0) {
                        i3 = getPreferredWidth();
                    } else {
                        i3 = this.mMaxWidth;
                    }
                    width = i3;
                    break;
                case 1073741824:
                    if (this.mMaxWidth > 0) {
                        width = Math.min(this.mMaxWidth, width);
                        break;
                    }
                    break;
            }
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            switch (heightMode) {
                case Integer.MIN_VALUE:
                    height = Math.min(getPreferredHeight(), height);
                    break;
                case 0:
                    height = getPreferredHeight();
                    break;
            }
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(height, 1073741824));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        boolean changed = z;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getChildBoundsWithinSearchView(this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
            this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, bottom - top);
            if (this.mTouchDelegate != null) {
                this.mTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
                return;
            }
            UpdatableTouchDelegate updatableTouchDelegate = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView);
            this.mTouchDelegate = updatableTouchDelegate;
            setTouchDelegate(this.mTouchDelegate);
        }
    }

    private void getChildBoundsWithinSearchView(View view, Rect rect) {
        View view2 = view;
        Rect rect2 = rect;
        View view3 = view2;
        Rect rect3 = rect2;
        view2.getLocationInWindow(this.mTemp);
        getLocationInWindow(this.mTemp2);
        int top = this.mTemp[1] - this.mTemp2[1];
        int i = this.mTemp[0] - this.mTemp2[0];
        int i2 = i;
        rect2.set(i, top, i + view2.getWidth(), top + view2.getHeight());
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(C0268R.dimen.abc_search_view_preferred_width);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(C0268R.dimen.abc_search_view_preferred_height);
    }

    private void updateViewsVisibility(boolean z) {
        int iconVisibility;
        boolean collapsed = z;
        this.mIconified = collapsed;
        boolean hasText = !TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(!collapsed ? 8 : 0);
        updateSubmitButton(hasText);
        this.mSearchEditFrame.setVisibility(!collapsed ? 0 : 8);
        if (this.mCollapsedIcon.getDrawable() != null && !this.mIconifiedByDefault) {
            iconVisibility = 0;
        } else {
            iconVisibility = 8;
        }
        this.mCollapsedIcon.setVisibility(iconVisibility);
        updateCloseButton();
        updateVoiceButton(!hasText);
        updateSubmitArea();
    }

    private boolean hasVoiceSearch() {
        if (this.mSearchable != null && this.mSearchable.getVoiceSearchEnabled()) {
            Intent testIntent = null;
            if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
                testIntent = this.mVoiceWebSearchIntent;
            } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
                testIntent = this.mVoiceAppSearchIntent;
            }
            if (testIntent != null) {
                ResolveInfo resolveActivity = getContext().getPackageManager().resolveActivity(testIntent, 65536);
                ResolveInfo resolveInfo = resolveActivity;
                return resolveActivity != null;
            }
        }
        return false;
    }

    private boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
    }

    private void updateSubmitButton(boolean z) {
        boolean hasText = z;
        int visibility = 8;
        if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (hasText || !this.mVoiceButtonEnabled)) {
            visibility = 0;
        }
        this.mGoButton.setVisibility(visibility);
    }

    private void updateSubmitArea() {
        int visibility = 8;
        if (isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
            visibility = 0;
        }
        this.mSubmitArea.setVisibility(visibility);
    }

    private void updateCloseButton() {
        boolean hasText = !TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mCloseButton.setVisibility(!(hasText || (this.mIconifiedByDefault && !this.mExpandedInActionView)) ? 8 : 0);
        Drawable drawable = this.mCloseButton.getDrawable();
        Drawable closeButtonImg = drawable;
        if (drawable != null) {
            boolean state = closeButtonImg.setState(!hasText ? EMPTY_STATE_SET : ENABLED_STATE_SET);
        }
    }

    private void postUpdateFocusedState() {
        boolean post = post(this.mUpdateDrawableStateRunnable);
    }

    /* access modifiers changed from: 0000 */
    public void updateFocusedState() {
        boolean hasFocus = this.mSearchSrcTextView.hasFocus();
        boolean z = hasFocus;
        int[] stateSet = !hasFocus ? EMPTY_STATE_SET : FOCUSED_STATE_SET;
        Drawable background = this.mSearchPlate.getBackground();
        Drawable searchPlateBg = background;
        if (background != null) {
            boolean state = searchPlateBg.setState(stateSet);
        }
        Drawable background2 = this.mSubmitArea.getBackground();
        Drawable submitAreaBg = background2;
        if (background2 != null) {
            boolean state2 = submitAreaBg.setState(stateSet);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        boolean removeCallbacks = removeCallbacks(this.mUpdateDrawableStateRunnable);
        boolean post = post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    public void setImeVisibility(boolean z) {
        if (!z) {
            boolean removeCallbacks = removeCallbacks(this.mShowImeRunnable);
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            InputMethodManager imm = inputMethodManager;
            if (inputMethodManager != null) {
                boolean hideSoftInputFromWindow = imm.hideSoftInputFromWindow(getWindowToken(), 0);
                return;
            }
            return;
        }
        boolean post = post(this.mShowImeRunnable);
    }

    /* access modifiers changed from: 0000 */
    public void onQueryRefine(CharSequence charSequence) {
        CharSequence queryText = charSequence;
        CharSequence charSequence2 = queryText;
        setQuery(queryText);
    }

    /* access modifiers changed from: 0000 */
    public boolean onSuggestionsKey(View view, int i, KeyEvent keyEvent) {
        int i2;
        int keyCode = i;
        KeyEvent event = keyEvent;
        View view2 = view;
        int i3 = keyCode;
        KeyEvent keyEvent2 = event;
        if (this.mSearchable == null) {
            return false;
        }
        if (this.mSuggestionsAdapter == null) {
            return false;
        }
        if (event.getAction() == 0 && KeyEventCompat.hasNoModifiers(event)) {
            if (keyCode == 66 || keyCode == 84 || keyCode == 61) {
                int listSelection = this.mSearchSrcTextView.getListSelection();
                int i4 = listSelection;
                return onItemClicked(listSelection, 0, null);
            } else if (keyCode == 21 || keyCode == 22) {
                if (keyCode != 21) {
                    i2 = this.mSearchSrcTextView.length();
                } else {
                    i2 = 0;
                }
                this.mSearchSrcTextView.setSelection(i2);
                this.mSearchSrcTextView.setListSelection(0);
                this.mSearchSrcTextView.clearListSelection();
                HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mSearchSrcTextView, true);
                return true;
            } else if (keyCode == 19 && 0 == this.mSearchSrcTextView.getListSelection()) {
                return false;
            }
        }
        return false;
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        CharSequence hintText = charSequence;
        CharSequence charSequence2 = hintText;
        if (!this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return hintText;
        }
        int textSize = (int) (((double) this.mSearchSrcTextView.getTextSize()) * 1.25d);
        int i = textSize;
        this.mSearchHintIcon.setBounds(0, 0, textSize, textSize);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        SpannableStringBuilder ssb = spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
        ImageSpan imageSpan = new ImageSpan(this.mSearchHintIcon);
        spannableStringBuilder2.setSpan(imageSpan, 1, 2, 33);
        SpannableStringBuilder append = ssb.append(hintText);
        return ssb;
    }

    private void updateQueryHint() {
        CharSequence hint = getQueryHint();
        this.mSearchSrcTextView.setHint(getDecoratedHint(hint != null ? hint : ""));
    }

    private void updateSearchAutoComplete() {
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int inputType = this.mSearchable.getInputType();
        int inputType2 = inputType;
        if ((inputType & 15) == 1) {
            int i = inputType2 & -65537;
            inputType2 = i;
            if (this.mSearchable.getSuggestAuthority() != null) {
                int i2 = i | 65536;
                int inputType3 = i2;
                inputType2 = i2 | 524288;
            }
        }
        this.mSearchSrcTextView.setInputType(inputType2);
        if (this.mSuggestionsAdapter != null) {
            this.mSuggestionsAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(!this.mQueryRefinement ? 1 : 2);
        }
    }

    private void updateVoiceButton(boolean z) {
        boolean empty = z;
        int visibility = 8;
        if (this.mVoiceButtonEnabled && !isIconified() && empty) {
            visibility = 0;
            this.mGoButton.setVisibility(8);
        }
        this.mVoiceButton.setVisibility(visibility);
    }

    /* access modifiers changed from: 0000 */
    public void onTextChanged(CharSequence charSequence) {
        CharSequence newText = charSequence;
        CharSequence charSequence2 = newText;
        Editable text = this.mSearchSrcTextView.getText();
        this.mUserQuery = text;
        boolean hasText = !TextUtils.isEmpty(text);
        updateSubmitButton(hasText);
        updateVoiceButton(!hasText);
        updateCloseButton();
        updateSubmitArea();
        if (this.mOnQueryChangeListener != null && !TextUtils.equals(newText, this.mOldQueryText)) {
            boolean onQueryTextChange = this.mOnQueryChangeListener.onQueryTextChange(newText.toString());
        }
        this.mOldQueryText = newText.toString();
    }

    /* access modifiers changed from: 0000 */
    public void onSubmitQuery() {
        Editable text = this.mSearchSrcTextView.getText();
        Editable editable = text;
        if (text == null || TextUtils.getTrimmedLength(editable) <= 0) {
            return;
        }
        if (this.mOnQueryChangeListener == null || !this.mOnQueryChangeListener.onQueryTextSubmit(editable.toString())) {
            if (this.mSearchable != null) {
                launchQuerySearch(0, null, editable.toString());
            }
            setImeVisibility(false);
            dismissSuggestions();
        }
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    /* access modifiers changed from: 0000 */
    public void onCloseClicked() {
        Editable text = this.mSearchSrcTextView.getText();
        Editable editable = text;
        if (!TextUtils.isEmpty(text)) {
            this.mSearchSrcTextView.setText("");
            boolean requestFocus = this.mSearchSrcTextView.requestFocus();
            setImeVisibility(true);
        } else if (this.mIconifiedByDefault) {
            if (this.mOnCloseListener == null || !this.mOnCloseListener.onClose()) {
                clearFocus();
                updateViewsVisibility(true);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onSearchClicked() {
        updateViewsVisibility(false);
        boolean requestFocus = this.mSearchSrcTextView.requestFocus();
        setImeVisibility(true);
        if (this.mOnSearchClickListener != null) {
            this.mOnSearchClickListener.onClick(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onVoiceClicked() {
        if (this.mSearchable != null) {
            SearchableInfo searchableInfo = this.mSearchable;
            SearchableInfo searchable = searchableInfo;
            try {
                if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    getContext().startActivity(createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchable));
                } else if (searchable.getVoiceSearchLaunchRecognizer()) {
                    getContext().startActivity(createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchable));
                }
            } catch (ActivityNotFoundException e) {
                ActivityNotFoundException activityNotFoundException = e;
                int w = Log.w(LOG_TAG, "Could not find voice search activity");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onTextFocusChanged() {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        postUpdateFocusedState();
    }

    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    public void onActionViewExpanded() {
        if (!this.mExpandedInActionView) {
            this.mExpandedInActionView = true;
            this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
            this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 33554432);
            this.mSearchSrcTextView.setText("");
            setIconified(false);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState ss = savedState;
        savedState.isIconified = isIconified();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            updateViewsVisibility(ss.isIconified);
            requestLayout();
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: 0000 */
    public void adjustDropDownSizeAndPosition() {
        int offset;
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources res = getContext().getResources();
            int anchorPadding = this.mSearchPlate.getPaddingLeft();
            Rect dropDownPadding = new Rect();
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            int iconOffset = !this.mIconifiedByDefault ? 0 : res.getDimensionPixelSize(C0268R.dimen.abc_dropdownitem_icon_width) + res.getDimensionPixelSize(C0268R.dimen.abc_dropdownitem_text_padding_left);
            boolean padding = this.mSearchSrcTextView.getDropDownBackground().getPadding(dropDownPadding);
            if (!isLayoutRtl) {
                offset = anchorPadding - (dropDownPadding.left + iconOffset);
            } else {
                offset = -dropDownPadding.left;
            }
            this.mSearchSrcTextView.setDropDownHorizontalOffset(offset);
            int width = (((this.mDropDownAnchor.getWidth() + dropDownPadding.left) + dropDownPadding.right) + iconOffset) - anchorPadding;
            int i = width;
            this.mSearchSrcTextView.setDropDownWidth(width);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean onItemClicked(int i, int i2, String str) {
        int position = i;
        int i3 = position;
        int i4 = i2;
        String str2 = str;
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionClick(position)) {
            return false;
        }
        boolean launchSuggestion = launchSuggestion(position, 0, null);
        setImeVisibility(false);
        dismissSuggestions();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean onItemSelected(int i) {
        int position = i;
        int i2 = position;
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionSelect(position)) {
            return false;
        }
        rewriteQueryFromSuggestion(position);
        return true;
    }

    private void rewriteQueryFromSuggestion(int i) {
        int position = i;
        int i2 = position;
        Editable text = this.mSearchSrcTextView.getText();
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        Cursor c = cursor;
        if (cursor != null) {
            if (!c.moveToPosition(position)) {
                setQuery(text);
            } else {
                CharSequence convertToString = this.mSuggestionsAdapter.convertToString(c);
                CharSequence newQuery = convertToString;
                if (convertToString == null) {
                    setQuery(text);
                } else {
                    setQuery(newQuery);
                }
            }
        }
    }

    private boolean launchSuggestion(int i, int i2, String str) {
        int position = i;
        int actionKey = i2;
        String actionMsg = str;
        int i3 = position;
        int i4 = actionKey;
        String str2 = actionMsg;
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        Cursor c = cursor;
        if (cursor == null || !c.moveToPosition(position)) {
            return false;
        }
        launchIntent(createIntentFromSuggestion(c, actionKey, actionMsg));
        return true;
    }

    private void launchIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        if (intent2 != null) {
            try {
                getContext().startActivity(intent2);
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                int e2 = Log.e(LOG_TAG, "Failed launch activity: " + intent2, e);
            }
        }
    }

    private void setQuery(CharSequence charSequence) {
        CharSequence query = charSequence;
        CharSequence charSequence2 = query;
        this.mSearchSrcTextView.setText(query);
        this.mSearchSrcTextView.setSelection(!TextUtils.isEmpty(query) ? query.length() : 0);
    }

    /* access modifiers changed from: 0000 */
    public void launchQuerySearch(int i, String str, String str2) {
        int actionKey = i;
        String actionMsg = str;
        String query = str2;
        int i2 = actionKey;
        String str3 = actionMsg;
        String str4 = query;
        getContext().startActivity(createIntent("android.intent.action.SEARCH", null, null, query, actionKey, actionMsg));
    }

    private Intent createIntent(String str, Uri uri, String str2, String str3, int i, String str4) {
        String action = str;
        Uri data = uri;
        String extraData = str2;
        String query = str3;
        int actionKey = i;
        String actionMsg = str4;
        String str5 = action;
        Uri uri2 = data;
        String str6 = extraData;
        String str7 = query;
        int i2 = actionKey;
        String str8 = actionMsg;
        Intent intent = new Intent(action);
        Intent intent2 = intent;
        Intent addFlags = intent.addFlags(268435456);
        if (data != null) {
            Intent data2 = intent2.setData(data);
        }
        Intent putExtra = intent2.putExtra("user_query", this.mUserQuery);
        if (query != null) {
            Intent putExtra2 = intent2.putExtra("query", query);
        }
        if (extraData != null) {
            Intent putExtra3 = intent2.putExtra("intent_extra_data_key", extraData);
        }
        if (this.mAppSearchData != null) {
            Intent putExtra4 = intent2.putExtra("app_data", this.mAppSearchData);
        }
        if (actionKey != 0) {
            Intent putExtra5 = intent2.putExtra("action_key", actionKey);
            Intent putExtra6 = intent2.putExtra("action_msg", actionMsg);
        }
        Intent component = intent2.setComponent(this.mSearchable.getSearchActivity());
        return intent2;
    }

    private Intent createVoiceWebSearchIntent(Intent intent, SearchableInfo searchableInfo) {
        String str;
        Intent baseIntent = intent;
        SearchableInfo searchable = searchableInfo;
        Intent intent2 = baseIntent;
        SearchableInfo searchableInfo2 = searchable;
        Intent voiceIntent = new Intent(baseIntent);
        ComponentName searchActivity = searchable.getSearchActivity();
        String str2 = "calling_package";
        if (searchActivity != null) {
            str = searchActivity.flattenToShortString();
        } else {
            str = null;
        }
        Intent putExtra = voiceIntent.putExtra(str2, str);
        return voiceIntent;
    }

    private Intent createVoiceAppSearchIntent(Intent intent, SearchableInfo searchableInfo) {
        String str;
        Intent baseIntent = intent;
        SearchableInfo searchable = searchableInfo;
        Intent intent2 = baseIntent;
        SearchableInfo searchableInfo2 = searchable;
        ComponentName searchActivity = searchable.getSearchActivity();
        Intent intent3 = new Intent("android.intent.action.SEARCH");
        Intent queryIntent = intent3;
        Intent component = intent3.setComponent(searchActivity);
        PendingIntent pending = PendingIntent.getActivity(getContext(), 0, queryIntent, 1073741824);
        Bundle queryExtras = new Bundle();
        if (this.mAppSearchData != null) {
            queryExtras.putParcelable("app_data", this.mAppSearchData);
        }
        Intent voiceIntent = new Intent(baseIntent);
        String languageModel = "free_form";
        String prompt = null;
        String language = null;
        int maxResults = 1;
        Resources resources = getResources();
        if (searchable.getVoiceLanguageModeId() != 0) {
            languageModel = resources.getString(searchable.getVoiceLanguageModeId());
        }
        if (searchable.getVoicePromptTextId() != 0) {
            prompt = resources.getString(searchable.getVoicePromptTextId());
        }
        if (searchable.getVoiceLanguageId() != 0) {
            language = resources.getString(searchable.getVoiceLanguageId());
        }
        if (searchable.getVoiceMaxResults() != 0) {
            maxResults = searchable.getVoiceMaxResults();
        }
        Intent putExtra = voiceIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", languageModel);
        Intent putExtra2 = voiceIntent.putExtra("android.speech.extra.PROMPT", prompt);
        Intent putExtra3 = voiceIntent.putExtra("android.speech.extra.LANGUAGE", language);
        Intent putExtra4 = voiceIntent.putExtra("android.speech.extra.MAX_RESULTS", maxResults);
        String str2 = "calling_package";
        if (searchActivity != null) {
            str = searchActivity.flattenToShortString();
        } else {
            str = null;
        }
        Intent putExtra5 = voiceIntent.putExtra(str2, str);
        Intent putExtra6 = voiceIntent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", pending);
        Intent putExtra7 = voiceIntent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", queryExtras);
        return voiceIntent;
    }

    private Intent createIntentFromSuggestion(Cursor cursor, int i, String str) {
        int rowNum;
        Cursor c = cursor;
        int actionKey = i;
        String actionMsg = str;
        Cursor cursor2 = c;
        int i2 = actionKey;
        String str2 = actionMsg;
        try {
            String columnString = SuggestionsAdapter.getColumnString(c, "suggest_intent_action");
            String action = columnString;
            if (columnString == null) {
                action = this.mSearchable.getSuggestIntentAction();
            }
            if (action == null) {
                action = "android.intent.action.SEARCH";
            }
            String columnString2 = SuggestionsAdapter.getColumnString(c, "suggest_intent_data");
            String data = columnString2;
            if (columnString2 == null) {
                data = this.mSearchable.getSuggestIntentData();
            }
            if (data != null) {
                String columnString3 = SuggestionsAdapter.getColumnString(c, "suggest_intent_data_id");
                String id = columnString3;
                if (columnString3 != null) {
                    data = data + "/" + Uri.encode(id);
                }
            }
            return createIntent(action, data != null ? Uri.parse(data) : null, SuggestionsAdapter.getColumnString(c, "suggest_intent_extra_data"), SuggestionsAdapter.getColumnString(c, "suggest_intent_query"), actionKey, actionMsg);
        } catch (RuntimeException e) {
            RuntimeException runtimeException = e;
            try {
                rowNum = c.getPosition();
            } catch (RuntimeException e2) {
                RuntimeException runtimeException2 = e2;
                rowNum = -1;
            }
            int w = Log.w(LOG_TAG, "Search suggestions cursor at row " + rowNum + " returned exception.", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void forceSuggestionQuery() {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView);
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView);
    }

    static boolean isLandscapeMode(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getResources().getConfiguration().orientation == 2;
    }
}
