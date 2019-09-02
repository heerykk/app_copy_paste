package android.support.p003v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ActionProvider;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.ShowableListMenu;
import android.support.p003v7.widget.ActivityChooserModel.ActivityChooserModelClient;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ActivityChooserView */
public class ActivityChooserView extends ViewGroup implements ActivityChooserModelClient {
    private static final String LOG_TAG = "ActivityChooserView";
    private final LinearLayoutCompat mActivityChooserContent;
    private final Drawable mActivityChooserContentBackground;
    final ActivityChooserViewAdapter mAdapter;
    private final Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    final FrameLayout mDefaultActivityButton;
    private final ImageView mDefaultActivityButtonImage;
    final FrameLayout mExpandActivityOverflowButton;
    private final ImageView mExpandActivityOverflowButtonImage;
    int mInitialActivityCount;
    private boolean mIsAttachedToWindow;
    boolean mIsSelectingDefaultActivity;
    private final int mListPopupMaxWidth;
    private ListPopupWindow mListPopupWindow;
    final DataSetObserver mModelDataSetObserver;
    OnDismissListener mOnDismissListener;
    private final OnGlobalLayoutListener mOnGlobalLayoutListener;
    ActionProvider mProvider;

    /* renamed from: android.support.v7.widget.ActivityChooserView$ActivityChooserViewAdapter */
    private class ActivityChooserViewAdapter extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
        private static final int ITEM_VIEW_TYPE_COUNT = 3;
        private static final int ITEM_VIEW_TYPE_FOOTER = 1;
        public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
        public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
        private ActivityChooserModel mDataModel;
        private boolean mHighlightDefaultActivity;
        private int mMaxActivityCount = 4;
        private boolean mShowDefaultActivity;
        private boolean mShowFooterView;
        final /* synthetic */ ActivityChooserView this$0;

        ActivityChooserViewAdapter(ActivityChooserView activityChooserView) {
            this.this$0 = activityChooserView;
        }

        public void setDataModel(ActivityChooserModel activityChooserModel) {
            ActivityChooserModel dataModel = activityChooserModel;
            ActivityChooserModel activityChooserModel2 = dataModel;
            ActivityChooserModel dataModel2 = this.this$0.mAdapter.getDataModel();
            ActivityChooserModel oldDataModel = dataModel2;
            if (dataModel2 != null && this.this$0.isShown()) {
                oldDataModel.unregisterObserver(this.this$0.mModelDataSetObserver);
            }
            this.mDataModel = dataModel;
            if (dataModel != null && this.this$0.isShown()) {
                dataModel.registerObserver(this.this$0.mModelDataSetObserver);
            }
            notifyDataSetChanged();
        }

        public int getItemViewType(int i) {
            int position = i;
            int i2 = position;
            if (this.mShowFooterView && position == getCount() - 1) {
                return 1;
            }
            return 0;
        }

        public int getViewTypeCount() {
            return 3;
        }

        public int getCount() {
            int activityCount = this.mDataModel.getActivityCount();
            if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                activityCount--;
            }
            int count = Math.min(activityCount, this.mMaxActivityCount);
            if (this.mShowFooterView) {
                count++;
            }
            return count;
        }

        public Object getItem(int i) {
            int position = i;
            int position2 = position;
            int itemViewType = getItemViewType(position);
            int i2 = itemViewType;
            switch (itemViewType) {
                case 0:
                    if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                        position2 = position + 1;
                    }
                    return this.mDataModel.getActivity(position2);
                case 1:
                    return null;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public long getItemId(int i) {
            int position = i;
            int i2 = position;
            return (long) position;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int position = i;
            View convertView = view;
            ViewGroup parent = viewGroup;
            int i2 = position;
            View convertView2 = convertView;
            ViewGroup viewGroup2 = parent;
            int itemViewType = getItemViewType(position);
            int i3 = itemViewType;
            switch (itemViewType) {
                case 0:
                    if (convertView == null || convertView.getId() != C0268R.C0270id.list_item) {
                        convertView2 = LayoutInflater.from(this.this$0.getContext()).inflate(C0268R.layout.abc_activity_chooser_view_list_item, parent, false);
                    }
                    PackageManager packageManager = this.this$0.getContext().getPackageManager();
                    ResolveInfo activity = (ResolveInfo) getItem(position);
                    ((ImageView) convertView2.findViewById(C0268R.C0270id.icon)).setImageDrawable(activity.loadIcon(packageManager));
                    TextView textView = (TextView) convertView2.findViewById(C0268R.C0270id.title);
                    TextView textView2 = textView;
                    textView.setText(activity.loadLabel(packageManager));
                    if (this.mShowDefaultActivity && position == 0 && this.mHighlightDefaultActivity) {
                        ViewCompat.setActivated(convertView2, true);
                    } else {
                        ViewCompat.setActivated(convertView2, false);
                    }
                    return convertView2;
                case 1:
                    if (convertView == null || convertView.getId() != 1) {
                        View inflate = LayoutInflater.from(this.this$0.getContext()).inflate(C0268R.layout.abc_activity_chooser_view_list_item, parent, false);
                        convertView2 = inflate;
                        inflate.setId(1);
                        TextView textView3 = (TextView) convertView2.findViewById(C0268R.C0270id.title);
                        TextView textView4 = textView3;
                        textView3.setText(this.this$0.getContext().getString(C0268R.string.abc_activity_chooser_view_see_all));
                    }
                    return convertView2;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public int measureContentWidth() {
            int oldMaxActivityCount = this.mMaxActivityCount;
            this.mMaxActivityCount = MAX_ACTIVITY_COUNT_UNLIMITED;
            int contentWidth = 0;
            View itemView = null;
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            for (int i = 0; i < count; i++) {
                View view = getView(i, itemView, null);
                itemView = view;
                view.measure(widthMeasureSpec, heightMeasureSpec);
                contentWidth = Math.max(contentWidth, itemView.getMeasuredWidth());
            }
            this.mMaxActivityCount = oldMaxActivityCount;
            return contentWidth;
        }

        public void setMaxActivityCount(int i) {
            int maxActivityCount = i;
            int i2 = maxActivityCount;
            if (this.mMaxActivityCount != maxActivityCount) {
                this.mMaxActivityCount = maxActivityCount;
                notifyDataSetChanged();
            }
        }

        public ResolveInfo getDefaultActivity() {
            return this.mDataModel.getDefaultActivity();
        }

        public void setShowFooterView(boolean z) {
            boolean showFooterView = z;
            if (this.mShowFooterView != showFooterView) {
                this.mShowFooterView = showFooterView;
                notifyDataSetChanged();
            }
        }

        public int getActivityCount() {
            return this.mDataModel.getActivityCount();
        }

        public int getHistorySize() {
            return this.mDataModel.getHistorySize();
        }

        public ActivityChooserModel getDataModel() {
            return this.mDataModel;
        }

        public void setShowDefaultActivity(boolean z, boolean z2) {
            boolean showDefaultActivity = z;
            boolean highlightDefaultActivity = z2;
            if (this.mShowDefaultActivity != showDefaultActivity || this.mHighlightDefaultActivity != highlightDefaultActivity) {
                this.mShowDefaultActivity = showDefaultActivity;
                this.mHighlightDefaultActivity = highlightDefaultActivity;
                notifyDataSetChanged();
            }
        }

        public boolean getShowDefaultActivity() {
            return this.mShowDefaultActivity;
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserView$Callbacks */
    private class Callbacks implements OnItemClickListener, OnClickListener, OnLongClickListener, OnDismissListener {
        final /* synthetic */ ActivityChooserView this$0;

        Callbacks(ActivityChooserView activityChooserView) {
            this.this$0 = activityChooserView;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            AdapterView<?> parent = adapterView;
            int position = i;
            AdapterView<?> adapterView2 = parent;
            View view2 = view;
            int i2 = position;
            long j2 = j;
            ActivityChooserViewAdapter activityChooserViewAdapter = (ActivityChooserViewAdapter) parent.getAdapter();
            ActivityChooserViewAdapter activityChooserViewAdapter2 = activityChooserViewAdapter;
            int itemViewType = activityChooserViewAdapter.getItemViewType(position);
            int i3 = itemViewType;
            switch (itemViewType) {
                case 0:
                    boolean dismissPopup = this.this$0.dismissPopup();
                    if (!this.this$0.mIsSelectingDefaultActivity) {
                        Intent chooseActivity = this.this$0.mAdapter.getDataModel().chooseActivity(!this.this$0.mAdapter.getShowDefaultActivity() ? position + 1 : position);
                        Intent launchIntent = chooseActivity;
                        if (chooseActivity != null) {
                            Intent addFlags = launchIntent.addFlags(524288);
                            this.this$0.getContext().startActivity(launchIntent);
                            return;
                        }
                        return;
                    } else if (position > 0) {
                        this.this$0.mAdapter.getDataModel().setDefaultActivity(position);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    this.this$0.showPopupUnchecked(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void onClick(View view) {
            View view2 = view;
            View view3 = view2;
            if (view2 == this.this$0.mDefaultActivityButton) {
                boolean dismissPopup = this.this$0.dismissPopup();
                int activityIndex = this.this$0.mAdapter.getDataModel().getActivityIndex(this.this$0.mAdapter.getDefaultActivity());
                int i = activityIndex;
                Intent chooseActivity = this.this$0.mAdapter.getDataModel().chooseActivity(activityIndex);
                Intent launchIntent = chooseActivity;
                if (chooseActivity != null) {
                    Intent addFlags = launchIntent.addFlags(524288);
                    this.this$0.getContext().startActivity(launchIntent);
                }
            } else if (view2 != this.this$0.mExpandActivityOverflowButton) {
                throw new IllegalArgumentException();
            } else {
                this.this$0.mIsSelectingDefaultActivity = false;
                this.this$0.showPopupUnchecked(this.this$0.mInitialActivityCount);
            }
        }

        public boolean onLongClick(View view) {
            View view2 = view;
            View view3 = view2;
            if (view2 != this.this$0.mDefaultActivityButton) {
                throw new IllegalArgumentException();
            }
            if (this.this$0.mAdapter.getCount() > 0) {
                this.this$0.mIsSelectingDefaultActivity = true;
                this.this$0.showPopupUnchecked(this.this$0.mInitialActivityCount);
            }
            return true;
        }

        public void onDismiss() {
            notifyOnDismissListener();
            if (this.this$0.mProvider != null) {
                this.this$0.mProvider.subUiVisibilityChanged(false);
            }
        }

        private void notifyOnDismissListener() {
            if (this.this$0.mOnDismissListener != null) {
                this.this$0.mOnDismissListener.onDismiss();
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.ActivityChooserView$InnerLayout */
    public static class InnerLayout extends LinearLayoutCompat {
        private static final int[] TINT_ATTRS;

        static {
            int[] iArr = new int[1];
            iArr[0] = 16842964;
            TINT_ATTRS = iArr;
        }

        public InnerLayout(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, TINT_ATTRS);
            setBackgroundDrawable(a.getDrawable(0));
            a.recycle();
        }
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mModelDataSetObserver = new DataSetObserver(this) {
            final /* synthetic */ ActivityChooserView this$0;

            {
                ActivityChooserView this$02 = r5;
                ActivityChooserView activityChooserView = this$02;
                this.this$0 = this$02;
            }

            public void onChanged() {
                super.onChanged();
                this.this$0.mAdapter.notifyDataSetChanged();
            }

            public void onInvalidated() {
                super.onInvalidated();
                this.this$0.mAdapter.notifyDataSetInvalidated();
            }
        };
        this.mOnGlobalLayoutListener = new OnGlobalLayoutListener(this) {
            final /* synthetic */ ActivityChooserView this$0;

            {
                ActivityChooserView this$02 = r5;
                ActivityChooserView activityChooserView = this$02;
                this.this$0 = this$02;
            }

            public void onGlobalLayout() {
                if (this.this$0.isShowingPopup()) {
                    if (this.this$0.isShown()) {
                        this.this$0.getListPopupWindow().show();
                        if (this.this$0.mProvider != null) {
                            this.this$0.mProvider.subUiVisibilityChanged(true);
                            return;
                        }
                        return;
                    }
                    this.this$0.getListPopupWindow().dismiss();
                }
            }
        };
        this.mInitialActivityCount = 4;
        TypedArray attributesArray = context2.obtainStyledAttributes(attrs, C0268R.styleable.ActivityChooserView, defStyle, 0);
        this.mInitialActivityCount = attributesArray.getInt(C0268R.styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable expandActivityOverflowButtonDrawable = attributesArray.getDrawable(C0268R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        attributesArray.recycle();
        LayoutInflater from = LayoutInflater.from(getContext());
        LayoutInflater layoutInflater = from;
        View inflate = from.inflate(C0268R.layout.abc_activity_chooser_view, this, true);
        this.mCallbacks = new Callbacks(this);
        this.mActivityChooserContent = (LinearLayoutCompat) findViewById(C0268R.C0270id.activity_chooser_view_content);
        this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
        this.mDefaultActivityButton = (FrameLayout) findViewById(C0268R.C0270id.default_activity_button);
        this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
        this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
        this.mDefaultActivityButtonImage = (ImageView) this.mDefaultActivityButton.findViewById(C0268R.C0270id.image);
        FrameLayout frameLayout = (FrameLayout) findViewById(C0268R.C0270id.expand_activities_button);
        FrameLayout expandButton = frameLayout;
        frameLayout.setOnClickListener(this.mCallbacks);
        expandButton.setOnTouchListener(new ForwardingListener(this, expandButton) {
            final /* synthetic */ ActivityChooserView this$0;

            {
                ActivityChooserView this$02 = r8;
                View src = r9;
                ActivityChooserView activityChooserView = this$02;
                View view = src;
                this.this$0 = this$02;
            }

            public ShowableListMenu getPopup() {
                return this.this$0.getListPopupWindow();
            }

            /* access modifiers changed from: protected */
            public boolean onForwardingStarted() {
                boolean showPopup = this.this$0.showPopup();
                return true;
            }

            /* access modifiers changed from: protected */
            public boolean onForwardingStopped() {
                boolean dismissPopup = this.this$0.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = expandButton;
        this.mExpandActivityOverflowButtonImage = (ImageView) expandButton.findViewById(C0268R.C0270id.image);
        this.mExpandActivityOverflowButtonImage.setImageDrawable(expandActivityOverflowButtonDrawable);
        this.mAdapter = new ActivityChooserViewAdapter(this);
        this.mAdapter.registerDataSetObserver(new DataSetObserver(this) {
            final /* synthetic */ ActivityChooserView this$0;

            {
                ActivityChooserView this$02 = r5;
                ActivityChooserView activityChooserView = this$02;
                this.this$0 = this$02;
            }

            public void onChanged() {
                super.onChanged();
                this.this$0.updateAppearance();
            }
        });
        Resources resources = context2.getResources();
        this.mListPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C0268R.dimen.abc_config_prefDialogWidth));
    }

    public ActivityChooserView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setActivityChooserModel(ActivityChooserModel activityChooserModel) {
        ActivityChooserModel dataModel = activityChooserModel;
        ActivityChooserModel activityChooserModel2 = dataModel;
        this.mAdapter.setDataModel(dataModel);
        if (isShowingPopup()) {
            boolean dismissPopup = dismissPopup();
            boolean showPopup = showPopup();
        }
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable2);
    }

    public void setExpandActivityOverflowButtonContentDescription(int i) {
        int resourceId = i;
        int i2 = resourceId;
        this.mExpandActivityOverflowButtonImage.setContentDescription(getContext().getString(resourceId));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setProvider(ActionProvider actionProvider) {
        ActionProvider provider = actionProvider;
        ActionProvider actionProvider2 = provider;
        this.mProvider = provider;
    }

    public boolean showPopup() {
        if (isShowingPopup() || !this.mIsAttachedToWindow) {
            return false;
        }
        this.mIsSelectingDefaultActivity = false;
        showPopupUnchecked(this.mInitialActivityCount);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void showPopupUnchecked(int i) {
        int maxActivityCount = i;
        int i2 = maxActivityCount;
        if (this.mAdapter.getDataModel() != null) {
            getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
            boolean defaultActivityButtonShown = this.mDefaultActivityButton.getVisibility() == 0;
            int activityCount = this.mAdapter.getActivityCount();
            int maxActivityCountOffset = !defaultActivityButtonShown ? 0 : 1;
            if (maxActivityCount != Integer.MAX_VALUE && activityCount > maxActivityCount + maxActivityCountOffset) {
                this.mAdapter.setShowFooterView(true);
                this.mAdapter.setMaxActivityCount(maxActivityCount - 1);
            } else {
                this.mAdapter.setShowFooterView(false);
                this.mAdapter.setMaxActivityCount(maxActivityCount);
            }
            ListPopupWindow listPopupWindow = getListPopupWindow();
            ListPopupWindow popupWindow = listPopupWindow;
            if (!listPopupWindow.isShowing()) {
                if (!this.mIsSelectingDefaultActivity && defaultActivityButtonShown) {
                    this.mAdapter.setShowDefaultActivity(false, false);
                } else {
                    this.mAdapter.setShowDefaultActivity(true, defaultActivityButtonShown);
                }
                int min = Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth);
                int i3 = min;
                popupWindow.setContentWidth(min);
                popupWindow.show();
                if (this.mProvider != null) {
                    this.mProvider.subUiVisibilityChanged(true);
                }
                popupWindow.getListView().setContentDescription(getContext().getString(C0268R.string.abc_activitychooserview_choose_application));
                return;
            }
            return;
        }
        throw new IllegalStateException("No data model. Did you call #setDataModel?");
    }

    public boolean dismissPopup() {
        if (isShowingPopup()) {
            getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            ViewTreeObserver viewTreeObserver2 = viewTreeObserver;
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver2.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
            }
        }
        return true;
    }

    public boolean isShowingPopup() {
        return getListPopupWindow().isShowing();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel dataModel = this.mAdapter.getDataModel();
        ActivityChooserModel dataModel2 = dataModel;
        if (dataModel != null) {
            dataModel2.registerObserver(this.mModelDataSetObserver);
        }
        this.mIsAttachedToWindow = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityChooserModel dataModel = this.mAdapter.getDataModel();
        ActivityChooserModel dataModel2 = dataModel;
        if (dataModel != null) {
            dataModel2.unregisterObserver(this.mModelDataSetObserver);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        ViewTreeObserver viewTreeObserver2 = viewTreeObserver;
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver2.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (isShowingPopup()) {
            boolean dismissPopup = dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int heightMeasureSpec2 = heightMeasureSpec;
        LinearLayoutCompat linearLayoutCompat = this.mActivityChooserContent;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            heightMeasureSpec2 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), 1073741824);
        }
        measureChild(linearLayoutCompat, widthMeasureSpec, heightMeasureSpec2);
        setMeasuredDimension(linearLayoutCompat.getMeasuredWidth(), linearLayoutCompat.getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        boolean z2 = z;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        this.mActivityChooserContent.layout(0, 0, right - left, bottom - top);
        if (!isShowingPopup()) {
            boolean dismissPopup = dismissPopup();
        }
    }

    public ActivityChooserModel getDataModel() {
        return this.mAdapter.getDataModel();
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mOnDismissListener = listener;
    }

    public void setInitialActivityCount(int i) {
        int itemCount = i;
        int i2 = itemCount;
        this.mInitialActivityCount = itemCount;
    }

    public void setDefaultActionButtonContentDescription(int i) {
        int resourceId = i;
        int i2 = resourceId;
        this.mDefaultActionButtonContentDescription = resourceId;
    }

    /* access modifiers changed from: 0000 */
    public ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            this.mListPopupWindow = new ListPopupWindow(getContext());
            this.mListPopupWindow.setAdapter(this.mAdapter);
            this.mListPopupWindow.setAnchorView(this);
            this.mListPopupWindow.setModal(true);
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        }
        return this.mListPopupWindow;
    }

    /* access modifiers changed from: 0000 */
    public void updateAppearance() {
        if (this.mAdapter.getCount() <= 0) {
            this.mExpandActivityOverflowButton.setEnabled(false);
        } else {
            this.mExpandActivityOverflowButton.setEnabled(true);
        }
        int activityCount = this.mAdapter.getActivityCount();
        int historySize = this.mAdapter.getHistorySize();
        if (activityCount != 1 && (activityCount <= 1 || historySize <= 0)) {
            this.mDefaultActivityButton.setVisibility(8);
        } else {
            this.mDefaultActivityButton.setVisibility(0);
            ResolveInfo activity = this.mAdapter.getDefaultActivity();
            PackageManager packageManager = getContext().getPackageManager();
            this.mDefaultActivityButtonImage.setImageDrawable(activity.loadIcon(packageManager));
            if (this.mDefaultActionButtonContentDescription != 0) {
                CharSequence label = activity.loadLabel(packageManager);
                this.mDefaultActivityButton.setContentDescription(getContext().getString(this.mDefaultActionButtonContentDescription, new Object[]{label}));
            }
        }
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            this.mActivityChooserContent.setBackgroundDrawable(null);
        } else {
            this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
        }
    }
}
