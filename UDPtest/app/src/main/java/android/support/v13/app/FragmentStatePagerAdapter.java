package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.Fragment.SavedState;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Set;

@TargetApi(13)
@RequiresApi(13)
public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<SavedState> mSavedState = new ArrayList<>();

    public abstract Fragment getItem(int i);

    public FragmentStatePagerAdapter(FragmentManager fragmentManager) {
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
        if (this.mFragments.size() > position) {
            Fragment fragment = (Fragment) this.mFragments.get(position);
            Fragment f = fragment;
            if (fragment != null) {
                return f;
            }
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Fragment fragment2 = getItem(position);
        if (this.mSavedState.size() > position) {
            SavedState savedState = (SavedState) this.mSavedState.get(position);
            SavedState fss = savedState;
            if (savedState != null) {
                fragment2.setInitialSavedState(fss);
            }
        }
        while (this.mFragments.size() <= position) {
            boolean add = this.mFragments.add(null);
        }
        FragmentCompat.setMenuVisibility(fragment2, false);
        FragmentCompat.setUserVisibleHint(fragment2, false);
        Object obj = this.mFragments.set(position, fragment2);
        FragmentTransaction add2 = this.mCurTransaction.add(container.getId(), fragment2);
        return fragment2;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        int position = i;
        Object object = obj;
        ViewGroup viewGroup2 = viewGroup;
        int i2 = position;
        Object obj2 = object;
        Fragment fragment = (Fragment) object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= position) {
            boolean add = this.mSavedState.add(null);
        }
        Object obj3 = this.mSavedState.set(position, !fragment.isAdded() ? null : this.mFragmentManager.saveFragmentInstanceState(fragment));
        Object obj4 = this.mFragments.set(position, null);
        FragmentTransaction remove = this.mCurTransaction.remove(fragment);
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
                FragmentCompat.setMenuVisibility(this.mCurrentPrimaryItem, false);
                FragmentCompat.setUserVisibleHint(this.mCurrentPrimaryItem, false);
            }
            if (fragment2 != null) {
                FragmentCompat.setMenuVisibility(fragment2, true);
                FragmentCompat.setUserVisibleHint(fragment2, true);
            }
            this.mCurrentPrimaryItem = fragment2;
        }
    }

    public void finishUpdate(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
        if (this.mCurTransaction != null) {
            int commitAllowingStateLoss = this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            boolean executePendingTransactions = this.mFragmentManager.executePendingTransactions();
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
        Bundle state = null;
        if (this.mSavedState.size() > 0) {
            state = new Bundle();
            SavedState[] fss = new SavedState[this.mSavedState.size()];
            Object[] array = this.mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < this.mFragments.size(); i++) {
            Fragment fragment = (Fragment) this.mFragments.get(i);
            Fragment f = fragment;
            if (fragment != null && f.isAdded()) {
                if (state == null) {
                    state = new Bundle();
                }
                this.mFragmentManager.putFragment(state, "f" + i, f);
            }
        }
        return state;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        Parcelable state = parcelable;
        ClassLoader loader = classLoader;
        Parcelable parcelable2 = state;
        ClassLoader classLoader2 = loader;
        if (state != null) {
            Bundle bundle = (Bundle) state;
            Bundle bundle2 = bundle;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle2.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (fss != null) {
                for (int i = 0; i < fss.length; i++) {
                    boolean add = this.mSavedState.add((SavedState) fss[i]);
                }
            }
            Set<String> keySet = bundle2.keySet();
            Set set = keySet;
            for (String str : keySet) {
                String key = str;
                if (str.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment fragment = this.mFragmentManager.getFragment(bundle2, key);
                    Fragment f = fragment;
                    if (fragment == null) {
                        int w = Log.w(TAG, "Bad fragment at key " + key);
                    } else {
                        while (this.mFragments.size() <= index) {
                            boolean add2 = this.mFragments.add(null);
                        }
                        FragmentCompat.setMenuVisibility(f, false);
                        Object obj = this.mFragments.set(index, f);
                    }
                }
            }
        }
    }
}
