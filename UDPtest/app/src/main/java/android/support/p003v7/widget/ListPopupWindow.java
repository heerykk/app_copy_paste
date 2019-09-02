package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p000v4.view.PointerIconCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.PopupWindowCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.ShowableListMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import java.lang.reflect.Method;

/* renamed from: android.support.v7.widget.ListPopupWindow */
public class ListPopupWindow implements ShowableListMenu {
    private static final boolean DEBUG = false;
    static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private static Method sClipToWindowEnabledMethod;
    private static Method sGetMaxAvailableHeightMethod;
    private static Method sSetEpicenterBoundsMethod;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch;
    final Handler mHandler;
    private final ListSelectorHider mHideSelector;
    private boolean mIsAnimatedFromAnchor;
    private OnItemClickListener mItemClickListener;
    private OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum;
    private boolean mModal;
    private DataSetObserver mObserver;
    PopupWindow mPopup;
    private int mPromptPosition;
    private View mPromptView;
    final ResizePopupRunnable mResizePopupRunnable;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    /* renamed from: android.support.v7.widget.ListPopupWindow$ListSelectorHider */
    private class ListSelectorHider implements Runnable {
        final /* synthetic */ ListPopupWindow this$0;

        ListSelectorHider(ListPopupWindow listPopupWindow) {
            this.this$0 = listPopupWindow;
        }

        public void run() {
            this.this$0.clearListSelection();
        }
    }

    /* renamed from: android.support.v7.widget.ListPopupWindow$PopupDataSetObserver */
    private class PopupDataSetObserver extends DataSetObserver {
        final /* synthetic */ ListPopupWindow this$0;

        PopupDataSetObserver(ListPopupWindow listPopupWindow) {
            this.this$0 = listPopupWindow;
        }

        public void onChanged() {
            if (this.this$0.isShowing()) {
                this.this$0.show();
            }
        }

        public void onInvalidated() {
            this.this$0.dismiss();
        }
    }

    /* renamed from: android.support.v7.widget.ListPopupWindow$PopupScrollListener */
    private class PopupScrollListener implements OnScrollListener {
        final /* synthetic */ ListPopupWindow this$0;

        PopupScrollListener(ListPopupWindow listPopupWindow) {
            this.this$0 = listPopupWindow;
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            AbsListView absListView2 = absListView;
            int i4 = i;
            int i5 = i2;
            int i6 = i3;
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            int scrollState = i;
            AbsListView absListView2 = absListView;
            int i2 = scrollState;
            if (scrollState == 1 && !this.this$0.isInputMethodNotNeeded() && this.this$0.mPopup.getContentView() != null) {
                this.this$0.mHandler.removeCallbacks(this.this$0.mResizePopupRunnable);
                this.this$0.mResizePopupRunnable.run();
            }
        }
    }

    /* renamed from: android.support.v7.widget.ListPopupWindow$PopupTouchInterceptor */
    private class PopupTouchInterceptor implements OnTouchListener {
        final /* synthetic */ ListPopupWindow this$0;

        PopupTouchInterceptor(ListPopupWindow listPopupWindow) {
            this.this$0 = listPopupWindow;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            MotionEvent event = motionEvent;
            View view2 = view;
            MotionEvent motionEvent2 = event;
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (action == 0 && this.this$0.mPopup != null && this.this$0.mPopup.isShowing() && x >= 0 && x < this.this$0.mPopup.getWidth() && y >= 0 && y < this.this$0.mPopup.getHeight()) {
                boolean postDelayed = this.this$0.mHandler.postDelayed(this.this$0.mResizePopupRunnable, 250);
            } else if (action == 1) {
                this.this$0.mHandler.removeCallbacks(this.this$0.mResizePopupRunnable);
            }
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.ListPopupWindow$ResizePopupRunnable */
    private class ResizePopupRunnable implements Runnable {
        final /* synthetic */ ListPopupWindow this$0;

        ResizePopupRunnable(ListPopupWindow listPopupWindow) {
            this.this$0 = listPopupWindow;
        }

        public void run() {
            if (this.this$0.mDropDownList != null && ViewCompat.isAttachedToWindow(this.this$0.mDropDownList) && this.this$0.mDropDownList.getCount() > this.this$0.mDropDownList.getChildCount() && this.this$0.mDropDownList.getChildCount() <= this.this$0.mListItemExpandMaximum) {
                this.this$0.mPopup.setInputMethodMode(2);
                this.this$0.show();
            }
        }
    }

    static {
        Class<PopupWindow> cls = PopupWindow.class;
        String str = "setClipToScreenEnabled";
        try {
            Class[] clsArr = new Class[1];
            clsArr[0] = Boolean.TYPE;
            sClipToWindowEnabledMethod = cls.getDeclaredMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            NoSuchMethodException noSuchMethodException = e;
            int i = Log.i(TAG, "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        Class<PopupWindow> cls2 = PopupWindow.class;
        String str2 = "getMaxAvailableHeight";
        try {
            Class[] clsArr2 = new Class[3];
            clsArr2[0] = View.class;
            clsArr2[1] = Integer.TYPE;
            clsArr2[2] = Boolean.TYPE;
            sGetMaxAvailableHeightMethod = cls2.getDeclaredMethod(str2, clsArr2);
        } catch (NoSuchMethodException e2) {
            int i2 = Log.i(TAG, "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        Class<PopupWindow> cls3 = PopupWindow.class;
        String str3 = "setEpicenterBounds";
        try {
            Class[] clsArr3 = new Class[1];
            clsArr3[0] = Rect.class;
            sSetEpicenterBoundsMethod = cls3.getDeclaredMethod(str3, clsArr3);
        } catch (NoSuchMethodException e3) {
            int i3 = Log.i(TAG, "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = PointerIconCompat.TYPE_HAND;
        this.mIsAnimatedFromAnchor = true;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.mListItemExpandMaximum = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mPromptPosition = 0;
        this.mResizePopupRunnable = new ResizePopupRunnable(this);
        this.mTouchInterceptor = new PopupTouchInterceptor(this);
        this.mScrollListener = new PopupScrollListener(this);
        this.mHideSelector = new ListSelectorHider(this);
        this.mTempRect = new Rect();
        this.mContext = context2;
        this.mHandler = new Handler(context2.getMainLooper());
        TypedArray a = context2.obtainStyledAttributes(attrs, C0268R.styleable.ListPopupWindow, defStyleAttr, defStyleRes);
        this.mDropDownHorizontalOffset = a.getDimensionPixelOffset(C0268R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.mDropDownVerticalOffset = a.getDimensionPixelOffset(C0268R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        a.recycle();
        if (VERSION.SDK_INT < 11) {
            this.mPopup = new AppCompatPopupWindow(context2, attrs, defStyleAttr);
        } else {
            this.mPopup = new AppCompatPopupWindow(context2, attrs, defStyleAttr, defStyleRes);
        }
        this.mPopup.setInputMethodMode(1);
    }

    public ListPopupWindow(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null, C0268R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        this(context2, attrs, defStyleAttr, 0);
    }

    public void setAdapter(@Nullable ListAdapter listAdapter) {
        ListAdapter adapter = listAdapter;
        ListAdapter listAdapter2 = adapter;
        if (this.mObserver == null) {
            this.mObserver = new PopupDataSetObserver(this);
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = adapter;
        if (this.mAdapter != null) {
            adapter.registerDataSetObserver(this.mObserver);
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
        }
    }

    public void setPromptPosition(int i) {
        int position = i;
        int i2 = position;
        this.mPromptPosition = position;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public void setModal(boolean z) {
        boolean modal = z;
        this.mModal = modal;
        this.mPopup.setFocusable(modal);
    }

    public boolean isModal() {
        return this.mModal;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setForceIgnoreOutsideTouch(boolean z) {
        this.mForceIgnoreOutsideTouch = z;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setDropDownAlwaysVisible(boolean z) {
        this.mDropDownAlwaysVisible = z;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public void setSoftInputMode(int i) {
        int mode = i;
        int i2 = mode;
        this.mPopup.setSoftInputMode(mode);
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public void setListSelector(Drawable drawable) {
        Drawable selector = drawable;
        Drawable drawable2 = selector;
        this.mDropDownListHighlight = selector;
    }

    @Nullable
    public Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mPopup.setBackgroundDrawable(d);
    }

    public void setAnimationStyle(@StyleRes int i) {
        int animationStyle = i;
        int i2 = animationStyle;
        this.mPopup.setAnimationStyle(animationStyle);
    }

    @StyleRes
    public int getAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    @Nullable
    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public void setAnchorView(@Nullable View view) {
        View anchor = view;
        View view2 = anchor;
        this.mDropDownAnchorView = anchor;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int i) {
        int offset = i;
        int i2 = offset;
        this.mDropDownHorizontalOffset = offset;
    }

    public int getVerticalOffset() {
        if (this.mDropDownVerticalOffsetSet) {
            return this.mDropDownVerticalOffset;
        }
        return 0;
    }

    public void setVerticalOffset(int i) {
        int offset = i;
        int i2 = offset;
        this.mDropDownVerticalOffset = offset;
        this.mDropDownVerticalOffsetSet = true;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setEpicenterBounds(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        this.mEpicenterBounds = bounds;
    }

    public void setDropDownGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mDropDownGravity = gravity;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public void setWidth(int i) {
        int width = i;
        int i2 = width;
        this.mDropDownWidth = width;
    }

    public void setContentWidth(int i) {
        int width = i;
        int i2 = width;
        Drawable background = this.mPopup.getBackground();
        Drawable popupBackground = background;
        if (background == null) {
            setWidth(width);
            return;
        }
        boolean padding = popupBackground.getPadding(this.mTempRect);
        this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + width;
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public void setHeight(int i) {
        int height = i;
        int i2 = height;
        this.mDropDownHeight = height;
    }

    public void setWindowLayoutType(int i) {
        int layoutType = i;
        int i2 = layoutType;
        this.mDropDownWindowLayoutType = layoutType;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        OnItemClickListener clickListener = onItemClickListener;
        OnItemClickListener onItemClickListener2 = clickListener;
        this.mItemClickListener = clickListener;
    }

    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener onItemSelectedListener) {
        OnItemSelectedListener selectedListener = onItemSelectedListener;
        OnItemSelectedListener onItemSelectedListener2 = selectedListener;
        this.mItemSelectedListener = selectedListener;
    }

    public void setPromptView(@Nullable View view) {
        View prompt = view;
        View view2 = prompt;
        boolean isShowing = isShowing();
        boolean showing = isShowing;
        if (isShowing) {
            removePromptView();
        }
        this.mPromptView = prompt;
        if (showing) {
            show();
        }
    }

    public void postShow() {
        boolean post = this.mHandler.post(this.mShowDropDownRunnable);
    }

    public void show() {
        int widthSpec;
        int heightSpec;
        int widthSpec2;
        int heightSpec2;
        int height = buildDropDown();
        boolean noInputMethod = isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.mPopup, this.mDropDownWindowLayoutType);
        if (!this.mPopup.isShowing()) {
            if (this.mDropDownWidth == -1) {
                widthSpec2 = -1;
            } else if (this.mDropDownWidth != -2) {
                widthSpec2 = this.mDropDownWidth;
            } else {
                widthSpec2 = getAnchorView().getWidth();
            }
            if (this.mDropDownHeight == -1) {
                heightSpec2 = -1;
            } else if (this.mDropDownHeight != -2) {
                heightSpec2 = this.mDropDownHeight;
            } else {
                heightSpec2 = height;
            }
            this.mPopup.setWidth(widthSpec2);
            this.mPopup.setHeight(heightSpec2);
            setPopupClipToScreenEnabled(true);
            this.mPopup.setOutsideTouchable(!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible);
            this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
            if (sSetEpicenterBoundsMethod != null) {
                try {
                    Method method = sSetEpicenterBoundsMethod;
                    PopupWindow popupWindow = this.mPopup;
                    Object[] objArr = new Object[1];
                    objArr[0] = this.mEpicenterBounds;
                    Object invoke = method.invoke(popupWindow, objArr);
                } catch (Exception e) {
                    Exception exc = e;
                    int e2 = Log.e(TAG, "Could not invoke setEpicenterBounds on PopupWindow", e);
                }
            }
            PopupWindowCompat.showAsDropDown(this.mPopup, getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
            this.mDropDownList.setSelection(-1);
            if (!this.mModal || this.mDropDownList.isInTouchMode()) {
                clearListSelection();
            }
            if (!this.mModal) {
                boolean post = this.mHandler.post(this.mHideSelector);
                return;
            }
            return;
        }
        if (this.mDropDownWidth == -1) {
            widthSpec = -1;
        } else if (this.mDropDownWidth != -2) {
            widthSpec = this.mDropDownWidth;
        } else {
            widthSpec = getAnchorView().getWidth();
        }
        if (this.mDropDownHeight != -1) {
            heightSpec = this.mDropDownHeight != -2 ? this.mDropDownHeight : height;
        } else {
            heightSpec = !noInputMethod ? -1 : height;
            if (!noInputMethod) {
                this.mPopup.setWidth(this.mDropDownWidth != -1 ? 0 : -1);
                this.mPopup.setHeight(-1);
            } else {
                this.mPopup.setWidth(this.mDropDownWidth != -1 ? 0 : -1);
                this.mPopup.setHeight(0);
            }
        }
        this.mPopup.setOutsideTouchable(!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible);
        this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, widthSpec >= 0 ? widthSpec : -1, heightSpec >= 0 ? heightSpec : -1);
    }

    public void dismiss() {
        this.mPopup.dismiss();
        removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mPopup.setOnDismissListener(listener);
    }

    private void removePromptView() {
        if (this.mPromptView != null) {
            ViewParent parent = this.mPromptView.getParent();
            ViewParent parent2 = parent;
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent2;
                ViewGroup viewGroup2 = viewGroup;
                viewGroup.removeView(this.mPromptView);
            }
        }
    }

    public void setInputMethodMode(int i) {
        int mode = i;
        int i2 = mode;
        this.mPopup.setInputMethodMode(mode);
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    public void setSelection(int i) {
        int position = i;
        int i2 = position;
        DropDownListView list = this.mDropDownList;
        if (isShowing() && list != null) {
            list.setListSelectionHidden(false);
            list.setSelection(position);
            if (VERSION.SDK_INT >= 11 && list.getChoiceMode() != 0) {
                list.setItemChecked(position, true);
            }
        }
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.mDropDownList;
        DropDownListView list = dropDownListView;
        if (dropDownListView != null) {
            list.setListSelectionHidden(true);
            list.requestLayout();
        }
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int i) {
        int position = i;
        int i2 = position;
        if (!isShowing()) {
            return false;
        }
        if (this.mItemClickListener != null) {
            DropDownListView dropDownListView = this.mDropDownList;
            DropDownListView list = dropDownListView;
            View child = dropDownListView.getChildAt(position - list.getFirstVisiblePosition());
            ListAdapter adapter = list.getAdapter();
            this.mItemClickListener.onItemClick(list, child, position, adapter.getItemId(position));
        }
        return true;
    }

    @Nullable
    public Object getSelectedItem() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedItem();
        }
        return null;
    }

    public int getSelectedItemPosition() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedItemPosition();
        }
        return -1;
    }

    public long getSelectedItemId() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedItemId();
        }
        return Long.MIN_VALUE;
    }

    @Nullable
    public View getSelectedView() {
        if (isShowing()) {
            return this.mDropDownList.getSelectedView();
        }
        return null;
    }

    @Nullable
    public ListView getListView() {
        return this.mDropDownList;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public DropDownListView createDropDownListView(Context context, boolean z) {
        Context context2 = context;
        Context context3 = context2;
        return new DropDownListView(context2, z);
    }

    /* access modifiers changed from: 0000 */
    public void setListItemExpandMax(int i) {
        int max = i;
        int i2 = max;
        this.mListItemExpandMaximum = max;
    }

    public boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        int i2;
        int count;
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i3 = keyCode;
        KeyEvent keyEvent2 = event;
        if (isShowing() && keyCode != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !isConfirmKey(keyCode))) {
            int curIndex = this.mDropDownList.getSelectedItemPosition();
            boolean below = !this.mPopup.isAboveAnchor();
            ListAdapter listAdapter = this.mAdapter;
            ListAdapter adapter = listAdapter;
            int firstItem = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int lastItem = Integer.MIN_VALUE;
            if (listAdapter != null) {
                boolean areAllItemsEnabled = adapter.areAllItemsEnabled();
                boolean allEnabled = areAllItemsEnabled;
                if (!areAllItemsEnabled) {
                    i2 = this.mDropDownList.lookForSelectablePosition(0, true);
                } else {
                    i2 = 0;
                }
                firstItem = i2;
                if (!allEnabled) {
                    count = this.mDropDownList.lookForSelectablePosition(adapter.getCount() - 1, false);
                } else {
                    count = adapter.getCount() - 1;
                }
                lastItem = count;
            }
            if ((below && keyCode == 19 && curIndex <= firstItem) || (!below && keyCode == 20 && curIndex >= lastItem)) {
                clearListSelection();
                this.mPopup.setInputMethodMode(1);
                show();
                return true;
            }
            this.mDropDownList.setListSelectionHidden(false);
            boolean onKeyDown = this.mDropDownList.onKeyDown(keyCode, event);
            boolean z = onKeyDown;
            if (onKeyDown) {
                this.mPopup.setInputMethodMode(2);
                boolean requestFocusFromTouch = this.mDropDownList.requestFocusFromTouch();
                show();
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 23:
                    case 66:
                        return true;
                }
            } else if (below && keyCode == 20) {
                if (curIndex == lastItem) {
                    return true;
                }
            } else if (!below && keyCode == 19 && curIndex == firstItem) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int i, @NonNull KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (!isShowing() || this.mDropDownList.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean onKeyUp = this.mDropDownList.onKeyUp(keyCode, event);
        boolean consumed = onKeyUp;
        if (onKeyUp && isConfirmKey(keyCode)) {
            dismiss();
        }
        return consumed;
    }

    public boolean onKeyPreIme(int i, @NonNull KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (keyCode == 4 && isShowing()) {
            View anchorView = this.mDropDownAnchorView;
            if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                DispatcherState keyDispatcherState = anchorView.getKeyDispatcherState();
                DispatcherState state = keyDispatcherState;
                if (keyDispatcherState != null) {
                    state.startTracking(event, this);
                }
                return true;
            } else if (event.getAction() == 1) {
                DispatcherState keyDispatcherState2 = anchorView.getKeyDispatcherState();
                DispatcherState state2 = keyDispatcherState2;
                if (keyDispatcherState2 != null) {
                    state2.handleUpEvent(event);
                }
                if (event.isTracking() && !event.isCanceled()) {
                    dismiss();
                    return true;
                }
            }
        }
        return false;
    }

    public OnTouchListener createDragToOpenListener(View view) {
        View src = view;
        View view2 = src;
        return new ForwardingListener(this, src) {
            final /* synthetic */ ListPopupWindow this$0;

            {
                ListPopupWindow this$02 = r8;
                View src = r9;
                ListPopupWindow listPopupWindow = this$02;
                View view = src;
                this.this$0 = this$02;
            }

            public ListPopupWindow getPopup() {
                return this.this$0;
            }
        };
    }

    /* JADX WARNING: type inference failed for: r9v12, types: [android.support.v7.widget.DropDownListView] */
    /* JADX WARNING: type inference failed for: r28v0 */
    /* JADX WARNING: type inference failed for: r31v0 */
    /* JADX WARNING: type inference failed for: r37v0 */
    /* JADX WARNING: type inference failed for: r0v15, types: [android.widget.LinearLayout] */
    /* JADX WARNING: type inference failed for: r1v2, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r0v16, types: [android.widget.LinearLayout] */
    /* JADX WARNING: type inference failed for: r0v17, types: [android.widget.LinearLayout] */
    /* JADX WARNING: type inference failed for: r41v0 */
    /* JADX WARNING: type inference failed for: r0v18, types: [android.widget.LinearLayout] */
    /* JADX WARNING: type inference failed for: r1v5, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r28v1 */
    /* JADX WARNING: type inference failed for: r28v2 */
    /* JADX WARNING: type inference failed for: r51v0 */
    /* JADX WARNING: type inference failed for: r0v31, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 13 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int buildDropDown() {
        /*
            r65 = this;
            r6 = r65
            r7 = r6
            r8 = 0
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            r10 = 0
            if (r9 == r10) goto L_0x0068
            android.widget.PopupWindow r9 = r6.mPopup
            android.view.View r9 = r9.getContentView()
            android.view.ViewGroup r9 = (android.view.ViewGroup) r9
            r28 = r9
            android.view.View r9 = r6.mPromptView
            r11 = r9
            r10 = 0
            if (r9 != r10) goto L_0x01dc
        L_0x0019:
            android.widget.PopupWindow r9 = r6.mPopup
            android.graphics.drawable.Drawable r9 = r9.getBackground()
            r29 = r9
            r10 = 0
            if (r9 != r10) goto L_0x01fc
            android.graphics.Rect r9 = r6.mTempRect
            r9.setEmpty()
            r54 = 0
        L_0x002b:
            android.widget.PopupWindow r9 = r6.mPopup
            int r36 = r9.getInputMethodMode()
            r16 = 2
            r0 = r36
            r1 = r16
            if (r0 == r1) goto L_0x0239
            r36 = 0
        L_0x003b:
            r56 = r36
            android.view.View r12 = r6.getAnchorView()
            int r0 = r6.mDropDownVerticalOffset
            r33 = r0
            r57 = r12
            r58 = r56
            r0 = r57
            r1 = r33
            r2 = r58
            int r36 = r6.getMaxAvailableHeight(r0, r1, r2)
            r59 = r36
            boolean r0 = r6.mDropDownAlwaysVisible
            r60 = r0
            r36 = r60
            r16 = 0
            r0 = r36
            r1 = r16
            if (r0 == r1) goto L_0x023d
        L_0x0063:
            int r36 = r59 + r54
            r61 = r36
            return r61
        L_0x0068:
            android.content.Context r9 = r6.mContext
            r11 = r9
            android.support.v7.widget.ListPopupWindow$2 r12 = new android.support.v7.widget.ListPopupWindow$2
            r13 = r6
            r12.<init>(r13)
            r6.mShowDropDownRunnable = r12
            boolean r14 = r6.mModal
            r15 = r14
            r16 = 0
            r0 = r16
            if (r15 == r0) goto L_0x00f0
            r15 = 0
        L_0x007d:
            r17 = r11
            r18 = r15
            r0 = r17
            r1 = r18
            android.support.v7.widget.DropDownListView r12 = r6.createDropDownListView(r0, r1)
            r6.mDropDownList = r12
            android.graphics.drawable.Drawable r9 = r6.mDropDownListHighlight
            r10 = 0
            if (r9 != r10) goto L_0x00f2
        L_0x0090:
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            android.widget.ListAdapter r12 = r6.mAdapter
            r20 = r12
            r0 = r20
            r9.setAdapter(r0)
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            android.widget.AdapterView$OnItemClickListener r12 = r6.mItemClickListener
            r21 = r12
            r0 = r21
            r9.setOnItemClickListener(r0)
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            r22 = 1
            r0 = r22
            r9.setFocusable(r0)
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            r23 = 1
            r0 = r23
            r9.setFocusableInTouchMode(r0)
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            android.support.v7.widget.ListPopupWindow$3 r12 = new android.support.v7.widget.ListPopupWindow$3
            r24 = r6
            r0 = r24
            r12.<init>(r0)
            r25 = r12
            r0 = r25
            r9.setOnItemSelectedListener(r0)
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            android.support.v7.widget.ListPopupWindow$PopupScrollListener r12 = r6.mScrollListener
            r26 = r12
            r0 = r26
            r9.setOnScrollListener(r0)
            android.widget.AdapterView$OnItemSelectedListener r9 = r6.mItemSelectedListener
            r10 = 0
            if (r9 != r10) goto L_0x00fe
        L_0x00da:
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            r28 = r9
            android.view.View r9 = r6.mPromptView
            r29 = r9
            r10 = 0
            if (r9 != r10) goto L_0x010a
        L_0x00e5:
            android.widget.PopupWindow r9 = r6.mPopup
            r51 = r28
            r0 = r51
            r9.setContentView(r0)
            goto L_0x0019
        L_0x00f0:
            r15 = 1
            goto L_0x007d
        L_0x00f2:
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            android.graphics.drawable.Drawable r12 = r6.mDropDownListHighlight
            r19 = r12
            r0 = r19
            r9.setSelector(r0)
            goto L_0x0090
        L_0x00fe:
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            android.widget.AdapterView$OnItemSelectedListener r12 = r6.mItemSelectedListener
            r27 = r12
            r0 = r27
            r9.setOnItemSelectedListener(r0)
            goto L_0x00da
        L_0x010a:
            android.widget.LinearLayout r9 = new android.widget.LinearLayout
            r30 = r11
            r0 = r30
            r9.<init>(r0)
            r31 = r9
            r32 = 1
            r0 = r32
            r9.setOrientation(r0)
            android.widget.LinearLayout$LayoutParams r9 = new android.widget.LinearLayout$LayoutParams
            r33 = -1
            r15 = 0
            r34 = 1065353216(0x3f800000, float:1.0)
            r0 = r33
            r1 = r34
            r9.<init>(r0, r15, r1)
            r35 = r9
            int r0 = r6.mPromptPosition
            r36 = r0
            switch(r36) {
                case 0: goto L_0x01a5;
                case 1: goto L_0x01bc;
                default: goto L_0x0133;
            }
        L_0x0133:
            java.lang.String r9 = "ListPopupWindow"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r43 = "Invalid hint position "
            r44 = r43
            r0 = r44
            java.lang.StringBuilder r12 = r12.append(r0)
            int r0 = r6.mPromptPosition
            r33 = r0
            r0 = r33
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r12 = r12.toString()
            r45 = r9
            r46 = r12
            int r36 = android.util.Log.e(r45, r46)
        L_0x015c:
            int r0 = r6.mDropDownWidth
            r36 = r0
            r16 = 0
            r0 = r36
            r1 = r16
            if (r0 >= r1) goto L_0x01d3
            r47 = 0
            r48 = 0
        L_0x016c:
            r0 = r48
            r1 = r47
            int r36 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
            r49 = r36
            r50 = 0
            r33 = 0
            r0 = r29
            r1 = r49
            r2 = r33
            r0.measure(r1, r2)
            android.view.ViewGroup$LayoutParams r9 = r29.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r9 = (android.widget.LinearLayout.LayoutParams) r9
            r35 = r9
            int r36 = r29.getMeasuredHeight()
            r0 = r35
            int r0 = r0.topMargin
            r32 = r0
            int r36 = r36 + r32
            r0 = r35
            int r0 = r0.bottomMargin
            r32 = r0
            int r36 = r36 + r32
            r8 = r36
            r28 = r31
            goto L_0x00e5
        L_0x01a5:
            r40 = r29
            r0 = r31
            r1 = r40
            r0.addView(r1)
            r41 = r28
            r42 = r35
            r0 = r31
            r1 = r41
            r2 = r42
            r0.addView(r1, r2)
            goto L_0x015c
        L_0x01bc:
            r37 = r28
            r38 = r35
            r0 = r31
            r1 = r37
            r2 = r38
            r0.addView(r1, r2)
            r39 = r29
            r0 = r31
            r1 = r39
            r0.addView(r1)
            goto L_0x015c
        L_0x01d3:
            r47 = -2147483648(0xffffffff80000000, float:-0.0)
            int r0 = r6.mDropDownWidth
            r36 = r0
            r48 = r36
            goto L_0x016c
        L_0x01dc:
            android.view.ViewGroup$LayoutParams r9 = r11.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r9 = (android.widget.LinearLayout.LayoutParams) r9
            r29 = r9
            int r36 = r11.getMeasuredHeight()
            r0 = r29
            int r0 = r0.topMargin
            r32 = r0
            int r36 = r36 + r32
            r0 = r29
            int r0 = r0.bottomMargin
            r32 = r0
            int r36 = r36 + r32
            r8 = r36
            goto L_0x0019
        L_0x01fc:
            android.graphics.Rect r12 = r6.mTempRect
            r52 = r12
            r0 = r29
            r1 = r52
            boolean r53 = r0.getPadding(r1)
            android.graphics.Rect r9 = r6.mTempRect
            int r0 = r9.top
            r36 = r0
            android.graphics.Rect r12 = r6.mTempRect
            int r0 = r12.bottom
            r32 = r0
            int r36 = r36 + r32
            r54 = r36
            boolean r0 = r6.mDropDownVerticalOffsetSet
            r55 = r0
            r36 = r55
            r16 = 0
            r0 = r36
            r1 = r16
            if (r0 == r1) goto L_0x0228
            goto L_0x002b
        L_0x0228:
            android.graphics.Rect r12 = r6.mTempRect
            int r0 = r12.top
            r32 = r0
            r0 = r32
            int r0 = -r0
            r32 = r0
            r0 = r32
            r6.mDropDownVerticalOffset = r0
            goto L_0x002b
        L_0x0239:
            r36 = 1
            goto L_0x003b
        L_0x023d:
            int r0 = r6.mDropDownHeight
            r36 = r0
            r16 = -1
            r0 = r36
            r1 = r16
            if (r0 == r1) goto L_0x0063
            int r0 = r6.mDropDownWidth
            r36 = r0
            switch(r36) {
                case -2: goto L_0x0286;
                case -1: goto L_0x02b5;
                default: goto L_0x0250;
            }
        L_0x0250:
            int r0 = r6.mDropDownWidth
            r36 = r0
            r32 = 1073741824(0x40000000, float:2.0)
            r0 = r36
            r1 = r32
            int r36 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
            r48 = r36
        L_0x0260:
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            r33 = 0
            r15 = -1
            int r62 = r59 - r8
            r63 = -1
            r0 = r9
            r1 = r48
            r2 = r33
            r3 = r15
            r4 = r62
            r5 = r63
            int r36 = r0.measureHeightOfChildrenCompat(r1, r2, r3, r4, r5)
            r47 = r36
            r16 = 0
            r0 = r36
            r1 = r16
            if (r0 > r1) goto L_0x02e5
        L_0x0281:
            int r36 = r47 + r8
            r64 = r36
            return r64
        L_0x0286:
            android.content.Context r9 = r6.mContext
            android.content.res.Resources r9 = r9.getResources()
            android.util.DisplayMetrics r9 = r9.getDisplayMetrics()
            int r0 = r9.widthPixels
            r36 = r0
            android.graphics.Rect r12 = r6.mTempRect
            int r0 = r12.left
            r32 = r0
            android.graphics.Rect r0 = r6.mTempRect
            r43 = r0
            r0 = r43
            int r0 = r0.right
            r33 = r0
            int r32 = r32 + r33
            int r36 = r36 - r32
            r32 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = r36
            r1 = r32
            int r36 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
            r48 = r36
            goto L_0x0260
        L_0x02b5:
            android.content.Context r9 = r6.mContext
            android.content.res.Resources r9 = r9.getResources()
            android.util.DisplayMetrics r9 = r9.getDisplayMetrics()
            int r0 = r9.widthPixels
            r36 = r0
            android.graphics.Rect r12 = r6.mTempRect
            int r0 = r12.left
            r32 = r0
            android.graphics.Rect r0 = r6.mTempRect
            r43 = r0
            r0 = r43
            int r0 = r0.right
            r33 = r0
            int r32 = r32 + r33
            int r36 = r36 - r32
            r32 = 1073741824(0x40000000, float:2.0)
            r0 = r36
            r1 = r32
            int r36 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
            r48 = r36
            goto L_0x0260
        L_0x02e5:
            android.support.v7.widget.DropDownListView r9 = r6.mDropDownList
            int r36 = r9.getPaddingTop()
            android.support.v7.widget.DropDownListView r12 = r6.mDropDownList
            int r32 = r12.getPaddingBottom()
            int r36 = r36 + r32
            r49 = r36
            int r32 = r54 + r49
            int r36 = r8 + r32
            r8 = r36
            goto L_0x0281
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.ListPopupWindow.buildDropDown():int");
    }

    private static boolean isConfirmKey(int i) {
        int keyCode = i;
        int i2 = keyCode;
        return keyCode == 66 || keyCode == 23;
    }

    private void setPopupClipToScreenEnabled(boolean z) {
        boolean clip = z;
        if (sClipToWindowEnabledMethod != null) {
            try {
                Method method = sClipToWindowEnabledMethod;
                PopupWindow popupWindow = this.mPopup;
                Object[] objArr = new Object[1];
                objArr[0] = Boolean.valueOf(clip);
                Object invoke = method.invoke(popupWindow, objArr);
            } catch (Exception e) {
                Exception exc = e;
                int i = Log.i(TAG, "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    private int getMaxAvailableHeight(View view, int i, boolean z) {
        View anchor = view;
        int yOffset = i;
        View view2 = anchor;
        int i2 = yOffset;
        boolean ignoreBottomDecorations = z;
        if (sGetMaxAvailableHeightMethod != null) {
            try {
                Method method = sGetMaxAvailableHeightMethod;
                PopupWindow popupWindow = this.mPopup;
                Object[] objArr = new Object[3];
                objArr[0] = anchor;
                objArr[1] = Integer.valueOf(yOffset);
                objArr[2] = Boolean.valueOf(ignoreBottomDecorations);
                return ((Integer) method.invoke(popupWindow, objArr)).intValue();
            } catch (Exception e) {
                Exception exc = e;
                int i3 = Log.i(TAG, "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.mPopup.getMaxAvailableHeight(anchor, yOffset);
    }
}
