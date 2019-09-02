package android.support.p003v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.support.p000v4.view.ActionProvider;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.widget.ActivityChooserModel.OnChooseActivityListener;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

/* renamed from: android.support.v7.widget.ShareActionProvider */
public class ShareActionProvider extends ActionProvider {
    private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    final Context mContext;
    private int mMaxShownActivityCount = 4;
    private OnChooseActivityListener mOnChooseActivityListener;
    private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener(this);
    OnShareTargetSelectedListener mOnShareTargetSelectedListener;
    String mShareHistoryFileName = DEFAULT_SHARE_HISTORY_FILE_NAME;

    /* renamed from: android.support.v7.widget.ShareActionProvider$OnShareTargetSelectedListener */
    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent);
    }

    /* renamed from: android.support.v7.widget.ShareActionProvider$ShareActivityChooserModelPolicy */
    private class ShareActivityChooserModelPolicy implements OnChooseActivityListener {
        final /* synthetic */ ShareActionProvider this$0;

        ShareActivityChooserModelPolicy(ShareActionProvider shareActionProvider) {
            this.this$0 = shareActionProvider;
        }

        public boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
            Intent intent2 = intent;
            ActivityChooserModel activityChooserModel2 = activityChooserModel;
            Intent intent3 = intent2;
            if (this.this$0.mOnShareTargetSelectedListener != null) {
                boolean onShareTargetSelected = this.this$0.mOnShareTargetSelectedListener.onShareTargetSelected(this.this$0, intent2);
            }
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.ShareActionProvider$ShareMenuItemOnMenuItemClickListener */
    private class ShareMenuItemOnMenuItemClickListener implements OnMenuItemClickListener {
        final /* synthetic */ ShareActionProvider this$0;

        ShareMenuItemOnMenuItemClickListener(ShareActionProvider shareActionProvider) {
            this.this$0 = shareActionProvider;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            ActivityChooserModel dataModel = ActivityChooserModel.get(this.this$0.mContext, this.this$0.mShareHistoryFileName);
            int itemId = item.getItemId();
            int i = itemId;
            Intent chooseActivity = dataModel.chooseActivity(itemId);
            Intent launchIntent = chooseActivity;
            if (chooseActivity != null) {
                String action = launchIntent.getAction();
                if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                    this.this$0.updateIntent(launchIntent);
                }
                this.this$0.mContext.startActivity(launchIntent);
            }
            return true;
        }
    }

    public ShareActionProvider(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
        this.mContext = context2;
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener onShareTargetSelectedListener) {
        OnShareTargetSelectedListener listener = onShareTargetSelectedListener;
        OnShareTargetSelectedListener onShareTargetSelectedListener2 = listener;
        this.mOnShareTargetSelectedListener = listener;
        setActivityChooserPolicyIfNeeded();
    }

    public View onCreateActionView() {
        ActivityChooserView activityChooserView = new ActivityChooserView(this.mContext);
        ActivityChooserView activityChooserView2 = activityChooserView;
        if (!activityChooserView.isInEditMode()) {
            activityChooserView2.setActivityChooserModel(ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName));
        }
        TypedValue outTypedValue = new TypedValue();
        boolean resolveAttribute = this.mContext.getTheme().resolveAttribute(C0268R.attr.actionModeShareDrawable, outTypedValue, true);
        activityChooserView2.setExpandActivityOverflowButtonDrawable(AppCompatResources.getDrawable(this.mContext, outTypedValue.resourceId));
        activityChooserView2.setProvider(this);
        activityChooserView2.setDefaultActionButtonContentDescription(C0268R.string.abc_shareactionprovider_share_with_application);
        activityChooserView2.setExpandActivityOverflowButtonContentDescription(C0268R.string.abc_shareactionprovider_share_with);
        return activityChooserView2;
    }

    public boolean hasSubMenu() {
        return true;
    }

    public void onPrepareSubMenu(SubMenu subMenu) {
        SubMenu subMenu2 = subMenu;
        SubMenu subMenu3 = subMenu2;
        subMenu2.clear();
        ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        PackageManager packageManager = this.mContext.getPackageManager();
        int activityCount = dataModel.getActivityCount();
        int expandedActivityCount = activityCount;
        int collapsedActivityCount = Math.min(activityCount, this.mMaxShownActivityCount);
        for (int i = 0; i < collapsedActivityCount; i++) {
            ResolveInfo activity = dataModel.getActivity(i);
            MenuItem onMenuItemClickListener = subMenu2.add(0, i, i, activity.loadLabel(packageManager)).setIcon(activity.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        }
        if (collapsedActivityCount < expandedActivityCount) {
            SubMenu expandedSubMenu = subMenu2.addSubMenu(0, collapsedActivityCount, collapsedActivityCount, this.mContext.getString(C0268R.string.abc_activity_chooser_view_see_all));
            for (int i2 = 0; i2 < expandedActivityCount; i2++) {
                ResolveInfo activity2 = dataModel.getActivity(i2);
                MenuItem onMenuItemClickListener2 = expandedSubMenu.add(0, i2, i2, activity2.loadLabel(packageManager)).setIcon(activity2.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
            }
        }
    }

    public void setShareHistoryFileName(String str) {
        String shareHistoryFile = str;
        String str2 = shareHistoryFile;
        this.mShareHistoryFileName = shareHistoryFile;
        setActivityChooserPolicyIfNeeded();
    }

    public void setShareIntent(Intent intent) {
        Intent shareIntent = intent;
        Intent intent2 = shareIntent;
        if (shareIntent != null) {
            String action = shareIntent.getAction();
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                updateIntent(shareIntent);
            }
        }
        ActivityChooserModel activityChooserModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        ActivityChooserModel activityChooserModel2 = activityChooserModel;
        activityChooserModel.setIntent(shareIntent);
    }

    private void setActivityChooserPolicyIfNeeded() {
        if (this.mOnShareTargetSelectedListener != null) {
            if (this.mOnChooseActivityListener == null) {
                this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy(this);
            }
            ActivityChooserModel activityChooserModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
            ActivityChooserModel activityChooserModel2 = activityChooserModel;
            activityChooserModel.setOnChooseActivityListener(this.mOnChooseActivityListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        if (VERSION.SDK_INT < 21) {
            Intent addFlags = intent2.addFlags(524288);
        } else {
            Intent addFlags2 = intent2.addFlags(134742016);
        }
    }
}
