package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import java.util.ArrayList;

@TargetApi(13)
@RequiresApi(13)
public class FragmentTabHost extends TabHost implements OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs = new ArrayList<>();

    static class DummyTabFactory implements TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mContext = context2;
        }

        public View createTabContent(String str) {
            String str2 = str;
            View view = new View(this.mContext);
            View v = view;
            view.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                return new SavedState(in);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        };
        String curTab;

        SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        SavedState(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            super(in);
            this.curTab = in.readString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel out = parcel;
            int flags = i;
            Parcel parcel2 = out;
            int i2 = flags;
            super.writeToParcel(out, flags);
            out.writeString(this.curTab);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
        }
    }

    static final class TabInfo {
        final Bundle args;
        final Class<?> clss;
        Fragment fragment;
        final String tag;

        TabInfo(String str, Class<?> cls, Bundle bundle) {
            String _tag = str;
            Class<?> _class = cls;
            Bundle _args = bundle;
            String str2 = _tag;
            Class<?> cls2 = _class;
            Bundle bundle2 = _args;
            this.tag = _tag;
            this.clss = _class;
            this.args = _args;
        }
    }

    public FragmentTabHost(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2, null);
        initFragmentTabHost(context2, null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        initFragmentTabHost(context2, attrs);
    }

    private void initFragmentTabHost(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        TypedArray a = context2.obtainStyledAttributes(attrs, new int[]{16842995}, 0, 0);
        this.mContainerId = a.getResourceId(0, 0);
        a.recycle();
        super.setOnTabChangedListener(this);
    }

    private void ensureHierarchy(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context2);
            LinearLayout ll = linearLayout;
            linearLayout.setOrientation(1);
            addView(ll, new LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context2);
            TabWidget tw = tabWidget;
            tabWidget.setId(16908307);
            tw.setOrientation(0);
            ll.addView(tw, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context2);
            FrameLayout fl = frameLayout;
            frameLayout.setId(16908305);
            ll.addView(fl, new LinearLayout.LayoutParams(0, 0, 0.0f));
            FrameLayout frameLayout2 = new FrameLayout(context2);
            FrameLayout fl2 = frameLayout2;
            this.mRealTabContent = frameLayout2;
            this.mRealTabContent.setId(this.mContainerId);
            ll.addView(fl2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager fragmentManager) {
        Context context2 = context;
        FragmentManager manager = fragmentManager;
        Context context3 = context2;
        FragmentManager fragmentManager2 = manager;
        ensureHierarchy(context2);
        super.setup();
        this.mContext = context2;
        this.mFragmentManager = manager;
        ensureContent();
    }

    public void setup(Context context, FragmentManager fragmentManager, int i) {
        Context context2 = context;
        FragmentManager manager = fragmentManager;
        int containerId = i;
        Context context3 = context2;
        FragmentManager fragmentManager2 = manager;
        int i2 = containerId;
        ensureHierarchy(context2);
        super.setup();
        this.mContext = context2;
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
        ensureContent();
        this.mRealTabContent.setId(containerId);
        if (getId() == -1) {
            setId(16908306);
        }
    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId);
            if (this.mRealTabContent == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }
    }

    public void setOnTabChangedListener(OnTabChangeListener onTabChangeListener) {
        OnTabChangeListener l = onTabChangeListener;
        OnTabChangeListener onTabChangeListener2 = l;
        this.mOnTabChangeListener = l;
    }

    public void addTab(TabSpec tabSpec, Class<?> cls, Bundle bundle) {
        TabSpec tabSpec2 = tabSpec;
        Class<?> clss = cls;
        Bundle args = bundle;
        TabSpec tabSpec3 = tabSpec2;
        Class<?> cls2 = clss;
        Bundle bundle2 = args;
        TabSpec content = tabSpec2.setContent(new DummyTabFactory(this.mContext));
        String tag = tabSpec2.getTag();
        TabInfo info = new TabInfo(tag, clss, args);
        if (this.mAttached) {
            info.fragment = this.mFragmentManager.findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
                FragmentTransaction ft = beginTransaction;
                FragmentTransaction detach = beginTransaction.detach(info.fragment);
                int commit = ft.commit();
            }
        }
        boolean add = this.mTabs.add(info);
        addTab(tabSpec2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTab = getCurrentTabTag();
        FragmentTransaction ft = null;
        for (int i = 0; i < this.mTabs.size(); i++) {
            TabInfo tabInfo = (TabInfo) this.mTabs.get(i);
            TabInfo tab = tabInfo;
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tab.tag);
            if (tab.fragment != null && !tab.fragment.isDetached()) {
                if (!tab.tag.equals(currentTab)) {
                    if (ft == null) {
                        ft = this.mFragmentManager.beginTransaction();
                    }
                    FragmentTransaction detach = ft.detach(tab.fragment);
                } else {
                    this.mLastTab = tab;
                }
            }
        }
        this.mAttached = true;
        FragmentTransaction doTabChanged = doTabChanged(currentTab, ft);
        FragmentTransaction ft2 = doTabChanged;
        if (doTabChanged != null) {
            int commit = ft2.commit();
            boolean executePendingTransactions = this.mFragmentManager.executePendingTransactions();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState ss = savedState;
        savedState.curTab = getCurrentTabTag();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            setCurrentTabByTag(ss.curTab);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void onTabChanged(String str) {
        String tabId = str;
        String str2 = tabId;
        if (this.mAttached) {
            FragmentTransaction doTabChanged = doTabChanged(tabId, null);
            FragmentTransaction ft = doTabChanged;
            if (doTabChanged != null) {
                int commit = ft.commit();
            }
        }
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(tabId);
        }
    }

    private FragmentTransaction doTabChanged(String str, FragmentTransaction fragmentTransaction) {
        String tabId = str;
        FragmentTransaction ft = fragmentTransaction;
        String str2 = tabId;
        FragmentTransaction ft2 = ft;
        TabInfo newTab = null;
        for (int i = 0; i < this.mTabs.size(); i++) {
            TabInfo tabInfo = (TabInfo) this.mTabs.get(i);
            TabInfo tab = tabInfo;
            if (tabInfo.tag.equals(tabId)) {
                newTab = tab;
            }
        }
        if (newTab != null) {
            if (this.mLastTab != newTab) {
                if (ft == null) {
                    ft2 = this.mFragmentManager.beginTransaction();
                }
                if (!(this.mLastTab == null || this.mLastTab.fragment == null)) {
                    FragmentTransaction detach = ft2.detach(this.mLastTab.fragment);
                }
                if (newTab != null) {
                    if (newTab.fragment != null) {
                        FragmentTransaction attach = ft2.attach(newTab.fragment);
                    } else {
                        newTab.fragment = Fragment.instantiate(this.mContext, newTab.clss.getName(), newTab.args);
                        FragmentTransaction add = ft2.add(this.mContainerId, newTab.fragment, newTab.tag);
                    }
                }
                this.mLastTab = newTab;
            }
            return ft2;
        }
        throw new IllegalStateException("No tab known for tag " + tabId);
    }
}
