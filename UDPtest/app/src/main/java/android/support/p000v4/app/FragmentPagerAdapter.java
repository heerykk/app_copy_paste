package android.support.p000v4.app;

import android.os.Parcelable;
import android.support.p000v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.app.FragmentPagerAdapter */
public abstract class FragmentPagerAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentPagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;

    public abstract Fragment getItem(int i);

    public FragmentPagerAdapter(FragmentManager fragmentManager) {
        FragmentManager fm = fragmentManager;
        FragmentManager fragmentManager2 = fm;
        this.mFragmentManager = fm;
    }

    public void startUpdate(ViewGroup viewGroup) {
        ViewGroup container = viewGroup;
        ViewGroup viewGroup2 = container;
        if (container.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        ViewGroup container = viewGroup;
        int position = i;
        ViewGroup viewGroup2 = container;
        int i2 = position;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long itemId = getItemId(position);
        long j = itemId;
        Fragment findFragmentByTag = this.mFragmentManager.findFragmentByTag(makeFragmentName(container.getId(), itemId));
        Fragment fragment = findFragmentByTag;
        if (findFragmentByTag == null) {
            fragment = getItem(position);
            FragmentTransaction add = this.mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), itemId));
        } else {
            FragmentTransaction attach = this.mCurTransaction.attach(fragment);
        }
        if (fragment != this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        Object object = obj;
        ViewGroup viewGroup2 = viewGroup;
        int i2 = i;
        Object obj2 = object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        FragmentTransaction detach = this.mCurTransaction.detach((Fragment) object);
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Object object = obj;
        ViewGroup viewGroup2 = viewGroup;
        int i2 = i;
        Object obj2 = object;
        Fragment fragment = (Fragment) object;
        Fragment fragment2 = fragment;
        if (fragment != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment2 != null) {
                fragment2.setMenuVisibility(true);
                fragment2.setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = fragment2;
        }
    }

    public void finishUpdate(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }
    }

    public boolean isViewFromObject(View view, Object obj) {
        View view2 = view;
        Object object = obj;
        View view3 = view2;
        Object obj2 = object;
        return ((Fragment) object).getView() == view2;
    }

    public Parcelable saveState() {
        return null;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        Parcelable parcelable2 = parcelable;
        ClassLoader classLoader2 = classLoader;
    }

    public long getItemId(int i) {
        int position = i;
        int i2 = position;
        return (long) position;
    }

    private static String makeFragmentName(int i, long j) {
        int viewId = i;
        long id = j;
        int i2 = viewId;
        long j2 = id;
        return "android:switcher:" + viewId + ":" + id;
    }
}
