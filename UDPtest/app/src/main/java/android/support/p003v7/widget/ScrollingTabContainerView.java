package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListener;
import android.support.p003v7.app.ActionBar.Tab;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.ActionBarPolicy;
import android.support.p003v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ScrollingTabContainerView */
public class ScrollingTabContainerView extends HorizontalScrollView implements OnItemSelectedListener {
    private static final int FADE_DURATION = 200;
    private static final String TAG = "ScrollingTabContainerView";
    private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private TabClickListener mTabClickListener;
    LinearLayoutCompat mTabLayout;
    Runnable mTabSelector;
    private Spinner mTabSpinner;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener(this);
    protected ViewPropertyAnimatorCompat mVisibilityAnim;

    /* renamed from: android.support.v7.widget.ScrollingTabContainerView$TabAdapter */
    private class TabAdapter extends BaseAdapter {
        final /* synthetic */ ScrollingTabContainerView this$0;

        TabAdapter(ScrollingTabContainerView scrollingTabContainerView) {
            this.this$0 = scrollingTabContainerView;
        }

        public int getCount() {
            return this.this$0.mTabLayout.getChildCount();
        }

        public Object getItem(int i) {
            int position = i;
            int i2 = position;
            return ((TabView) this.this$0.mTabLayout.getChildAt(position)).getTab();
        }

        public long getItemId(int i) {
            int position = i;
            int i2 = position;
            return (long) position;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int position = i;
            View convertView = view;
            int i2 = position;
            View convertView2 = convertView;
            ViewGroup viewGroup2 = viewGroup;
            if (convertView != null) {
                ((TabView) convertView).bindTab((Tab) getItem(position));
            } else {
                convertView2 = this.this$0.createTabView((Tab) getItem(position), true);
            }
            return convertView2;
        }
    }

    /* renamed from: android.support.v7.widget.ScrollingTabContainerView$TabClickListener */
    private class TabClickListener implements OnClickListener {
        final /* synthetic */ ScrollingTabContainerView this$0;

        TabClickListener(ScrollingTabContainerView scrollingTabContainerView) {
            this.this$0 = scrollingTabContainerView;
        }

        public void onClick(View view) {
            View view2 = view;
            View view3 = view2;
            TabView tabView = (TabView) view2;
            TabView tabView2 = tabView;
            tabView.getTab().select();
            int tabCount = this.this$0.mTabLayout.getChildCount();
            for (int i = 0; i < tabCount; i++) {
                View childAt = this.this$0.mTabLayout.getChildAt(i);
                childAt.setSelected(childAt == view2);
            }
        }
    }

    /* renamed from: android.support.v7.widget.ScrollingTabContainerView$TabView */
    private class TabView extends LinearLayoutCompat implements OnLongClickListener {
        private final int[] BG_ATTRS = {16842964};
        private View mCustomView;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;
        final /* synthetic */ ScrollingTabContainerView this$0;

        public TabView(ScrollingTabContainerView scrollingTabContainerView, Context context, Tab tab, boolean z) {
            Context context2 = context;
            Tab tab2 = tab;
            Context context3 = context2;
            Tab tab3 = tab2;
            boolean forList = z;
            this.this$0 = scrollingTabContainerView;
            super(context2, null, C0268R.attr.actionBarTabStyle);
            this.mTab = tab2;
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, null, this.BG_ATTRS, C0268R.attr.actionBarTabStyle, 0);
            TintTypedArray a = obtainStyledAttributes;
            if (obtainStyledAttributes.hasValue(0)) {
                setBackgroundDrawable(a.getDrawable(0));
            }
            a.recycle();
            if (forList) {
                setGravity(8388627);
            }
            update();
        }

        public void bindTab(Tab tab) {
            Tab tab2 = tab;
            Tab tab3 = tab2;
            this.mTab = tab2;
            update();
        }

        public void setSelected(boolean z) {
            boolean selected = z;
            boolean changed = isSelected() != selected;
            super.setSelected(selected);
            if (changed && selected) {
                sendAccessibilityEvent(4);
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            AccessibilityNodeInfo info = accessibilityNodeInfo;
            AccessibilityNodeInfo accessibilityNodeInfo2 = info;
            super.onInitializeAccessibilityNodeInfo(info);
            if (VERSION.SDK_INT >= 14) {
                info.setClassName(Tab.class.getName());
            }
        }

        public void onMeasure(int i, int i2) {
            int widthMeasureSpec = i;
            int heightMeasureSpec = i2;
            int i3 = widthMeasureSpec;
            int i4 = heightMeasureSpec;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.this$0.mMaxTabWidth > 0 && getMeasuredWidth() > this.this$0.mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(this.this$0.mMaxTabWidth, 1073741824), heightMeasureSpec);
            }
        }

        public void update() {
            Tab tab = this.mTab;
            Tab tab2 = tab;
            View customView = tab.getCustomView();
            View custom = customView;
            if (customView == null) {
                if (this.mCustomView != null) {
                    removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                Drawable icon = tab2.getIcon();
                CharSequence text = tab2.getText();
                if (icon != null) {
                    if (this.mIconView == null) {
                        AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
                        LayoutParams layoutParams = new LayoutParams(-2, -2);
                        LayoutParams lp = layoutParams;
                        layoutParams.gravity = 16;
                        appCompatImageView.setLayoutParams(lp);
                        addView(appCompatImageView, 0);
                        this.mIconView = appCompatImageView;
                    }
                    this.mIconView.setImageDrawable(icon);
                    this.mIconView.setVisibility(0);
                } else if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                boolean hasText = !TextUtils.isEmpty(text);
                if (hasText) {
                    if (this.mTextView == null) {
                        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), null, C0268R.attr.actionBarTabTextStyle);
                        AppCompatTextView appCompatTextView2 = appCompatTextView;
                        appCompatTextView.setEllipsize(TruncateAt.END);
                        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
                        LayoutParams lp2 = layoutParams2;
                        layoutParams2.gravity = 16;
                        appCompatTextView2.setLayoutParams(lp2);
                        addView(appCompatTextView2);
                        this.mTextView = appCompatTextView2;
                    }
                    this.mTextView.setText(text);
                    this.mTextView.setVisibility(0);
                } else if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                    this.mTextView.setText(null);
                }
                if (this.mIconView != null) {
                    this.mIconView.setContentDescription(tab2.getContentDescription());
                }
                if (!hasText && !TextUtils.isEmpty(tab2.getContentDescription())) {
                    setOnLongClickListener(this);
                    return;
                }
                setOnLongClickListener(null);
                setLongClickable(false);
                return;
            }
            ViewParent parent = custom.getParent();
            ViewParent customParent = parent;
            if (parent != this) {
                if (customParent != null) {
                    ((ViewGroup) customParent).removeView(custom);
                }
                addView(custom);
            }
            this.mCustomView = custom;
            if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
            }
            if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
            }
        }

        public boolean onLongClick(View view) {
            View view2 = view;
            int[] screenPos = new int[2];
            getLocationOnScreen(screenPos);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int i = context.getResources().getDisplayMetrics().widthPixels;
            int i2 = i;
            Toast makeText = Toast.makeText(context, this.mTab.getContentDescription(), 0);
            Toast cheatSheet = makeText;
            makeText.setGravity(49, (screenPos[0] + (width / 2)) - (i / 2), height);
            cheatSheet.show();
            return true;
        }

        public Tab getTab() {
            return this.mTab;
        }
    }

    /* renamed from: android.support.v7.widget.ScrollingTabContainerView$VisibilityAnimListener */
    protected class VisibilityAnimListener implements ViewPropertyAnimatorListener {
        private boolean mCanceled = false;
        private int mFinalVisibility;
        final /* synthetic */ ScrollingTabContainerView this$0;

        protected VisibilityAnimListener(ScrollingTabContainerView scrollingTabContainerView) {
            ScrollingTabContainerView this$02 = scrollingTabContainerView;
            ScrollingTabContainerView scrollingTabContainerView2 = this$02;
            this.this$0 = this$02;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, int i) {
            ViewPropertyAnimatorCompat animation = viewPropertyAnimatorCompat;
            int visibility = i;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = animation;
            int i2 = visibility;
            this.mFinalVisibility = visibility;
            this.this$0.mVisibilityAnim = animation;
            return this;
        }

        public void onAnimationStart(View view) {
            View view2 = view;
            this.this$0.setVisibility(0);
            this.mCanceled = false;
        }

        public void onAnimationEnd(View view) {
            View view2 = view;
            if (!this.mCanceled) {
                this.this$0.mVisibilityAnim = null;
                this.this$0.setVisibility(this.mFinalVisibility);
            }
        }

        public void onAnimationCancel(View view) {
            View view2 = view;
            this.mCanceled = true;
        }
    }

    public ScrollingTabContainerView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
        setHorizontalScrollBarEnabled(false);
        ActionBarPolicy abp = ActionBarPolicy.get(context2);
        setContentHeight(abp.getTabContainerHeight());
        this.mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
        this.mTabLayout = createTabLayout();
        addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int i3 = widthMeasureSpec;
        int i4 = i2;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMode = mode;
        boolean lockedExpanded = mode == 1073741824;
        setFillViewport(lockedExpanded);
        int childCount = this.mTabLayout.getChildCount();
        int childCount2 = childCount;
        if (childCount > 1 && (widthMode == 1073741824 || widthMode == Integer.MIN_VALUE)) {
            if (childCount2 <= 2) {
                this.mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            } else {
                this.mMaxTabWidth = (int) (((float) MeasureSpec.getSize(widthMeasureSpec)) * 0.4f);
            }
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        } else {
            this.mMaxTabWidth = -1;
        }
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
        if (!(!lockedExpanded && this.mAllowCollapse)) {
            boolean performExpand = performExpand();
        } else {
            this.mTabLayout.measure(0, heightMeasureSpec);
            if (this.mTabLayout.getMeasuredWidth() <= MeasureSpec.getSize(widthMeasureSpec)) {
                boolean performExpand2 = performExpand();
            } else {
                performCollapse();
            }
        }
        int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newWidth = getMeasuredWidth();
        if (lockedExpanded && oldWidth != newWidth) {
            setTabSelected(this.mSelectedTabIndex);
        }
    }

    private boolean isCollapsed() {
        return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
    }

    public void setAllowCollapse(boolean z) {
        this.mAllowCollapse = z;
    }

    private void performCollapse() {
        if (!isCollapsed()) {
            if (this.mTabSpinner == null) {
                this.mTabSpinner = createSpinner();
            }
            removeView(this.mTabLayout);
            addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
            if (this.mTabSpinner.getAdapter() == null) {
                this.mTabSpinner.setAdapter(new TabAdapter(this));
            }
            if (this.mTabSelector != null) {
                boolean removeCallbacks = removeCallbacks(this.mTabSelector);
                this.mTabSelector = null;
            }
            this.mTabSpinner.setSelection(this.mSelectedTabIndex);
        }
    }

    private boolean performExpand() {
        if (!isCollapsed()) {
            return false;
        }
        removeView(this.mTabSpinner);
        addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void setTabSelected(int i) {
        int position = i;
        int i2 = position;
        this.mSelectedTabIndex = position;
        int tabCount = this.mTabLayout.getChildCount();
        int i3 = 0;
        while (i3 < tabCount) {
            View childAt = this.mTabLayout.getChildAt(i3);
            View view = childAt;
            boolean isSelected = i3 == position;
            childAt.setSelected(isSelected);
            if (isSelected) {
                animateToTab(position);
            }
            i3++;
        }
        if (this.mTabSpinner != null && position >= 0) {
            this.mTabSpinner.setSelection(position);
        }
    }

    public void setContentHeight(int i) {
        int contentHeight = i;
        int i2 = contentHeight;
        this.mContentHeight = contentHeight;
        requestLayout();
    }

    private LinearLayoutCompat createTabLayout() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext(), null, C0268R.attr.actionBarTabBarStyle);
        LinearLayoutCompat tabLayout = linearLayoutCompat;
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        tabLayout.setGravity(17);
        tabLayout.setLayoutParams(new LayoutParams(-2, -1));
        return tabLayout;
    }

    private Spinner createSpinner() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(getContext(), null, C0268R.attr.actionDropDownStyle);
        AppCompatSpinner appCompatSpinner2 = appCompatSpinner;
        appCompatSpinner.setLayoutParams(new LayoutParams(-2, -1));
        appCompatSpinner2.setOnItemSelectedListener(this);
        return appCompatSpinner2;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        ActionBarPolicy abp = ActionBarPolicy.get(getContext());
        setContentHeight(abp.getTabContainerHeight());
        this.mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
    }

    public void animateToVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (visibility != 0) {
            ViewPropertyAnimatorCompat alpha = ViewCompat.animate(this).alpha(0.0f);
            ViewPropertyAnimatorCompat anim = alpha;
            ViewPropertyAnimatorCompat duration = alpha.setDuration(200);
            ViewPropertyAnimatorCompat interpolator = anim.setInterpolator(sAlphaInterpolator);
            ViewPropertyAnimatorCompat listener = anim.setListener(this.mVisAnimListener.withFinalVisibility(anim, visibility));
            anim.start();
            return;
        }
        if (getVisibility() != 0) {
            ViewCompat.setAlpha(this, 0.0f);
        }
        ViewPropertyAnimatorCompat alpha2 = ViewCompat.animate(this).alpha(1.0f);
        ViewPropertyAnimatorCompat anim2 = alpha2;
        ViewPropertyAnimatorCompat duration2 = alpha2.setDuration(200);
        ViewPropertyAnimatorCompat interpolator2 = anim2.setInterpolator(sAlphaInterpolator);
        ViewPropertyAnimatorCompat listener2 = anim2.setListener(this.mVisAnimListener.withFinalVisibility(anim2, visibility));
        anim2.start();
    }

    public void animateToTab(int i) {
        int position = i;
        int i2 = position;
        View tabView = this.mTabLayout.getChildAt(position);
        if (this.mTabSelector != null) {
            boolean removeCallbacks = removeCallbacks(this.mTabSelector);
        }
        final View view = tabView;
        this.mTabSelector = new Runnable(this) {
            final /* synthetic */ ScrollingTabContainerView this$0;

            {
                ScrollingTabContainerView this$02 = r6;
                View view = r7;
                ScrollingTabContainerView scrollingTabContainerView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                int left = view.getLeft() - ((this.this$0.getWidth() - view.getWidth()) / 2);
                int i = left;
                this.this$0.smoothScrollTo(left, 0);
                this.this$0.mTabSelector = null;
            }
        };
        boolean post = post(this.mTabSelector);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            boolean post = post(this.mTabSelector);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            boolean removeCallbacks = removeCallbacks(this.mTabSelector);
        }
    }

    /* access modifiers changed from: 0000 */
    public TabView createTabView(Tab tab, boolean z) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        boolean forAdapter = z;
        TabView tabView = new TabView(this, getContext(), tab2, forAdapter);
        TabView tabView2 = tabView;
        if (!forAdapter) {
            tabView.setFocusable(true);
            if (this.mTabClickListener == null) {
                TabClickListener tabClickListener = new TabClickListener(this);
                this.mTabClickListener = tabClickListener;
            }
            tabView2.setOnClickListener(this.mTabClickListener);
        } else {
            tabView.setBackgroundDrawable(null);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(-1, this.mContentHeight);
            tabView2.setLayoutParams(layoutParams);
        }
        return tabView2;
    }

    public void addTab(Tab tab, boolean z) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        boolean setSelected = z;
        TabView tabView = createTabView(tab2, false);
        this.mTabLayout.addView(tabView, new LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (setSelected) {
            tabView.setSelected(true);
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void addTab(Tab tab, int i, boolean z) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        boolean setSelected = z;
        TabView tabView = createTabView(tab2, false);
        this.mTabLayout.addView(tabView, position, new LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (setSelected) {
            tabView.setSelected(true);
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void updateTab(int i) {
        int position = i;
        int i2 = position;
        ((TabView) this.mTabLayout.getChildAt(position)).update();
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeTabAt(int i) {
        int position = i;
        int i2 = position;
        this.mTabLayout.removeViewAt(position);
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeAllTabs() {
        this.mTabLayout.removeAllViews();
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        View view2 = view;
        AdapterView<?> adapterView2 = adapterView;
        View view3 = view2;
        int i2 = i;
        long j2 = j;
        TabView tabView = (TabView) view2;
        TabView tabView2 = tabView;
        tabView.getTab().select();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        AdapterView<?> adapterView2 = adapterView;
    }
}
