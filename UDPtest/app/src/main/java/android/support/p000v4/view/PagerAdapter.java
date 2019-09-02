package android.support.p000v4.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.view.PagerAdapter */
public abstract class PagerAdapter {
    public static final int POSITION_NONE = -2;
    public static final int POSITION_UNCHANGED = -1;
    private final DataSetObservable mObservable = new DataSetObservable();
    private DataSetObserver mViewPagerObserver;

    public abstract int getCount();

    public abstract boolean isViewFromObject(View view, Object obj);

    public PagerAdapter() {
    }

    public void startUpdate(ViewGroup viewGroup) {
        ViewGroup container = viewGroup;
        ViewGroup viewGroup2 = container;
        startUpdate((View) container);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        ViewGroup container = viewGroup;
        int position = i;
        ViewGroup viewGroup2 = container;
        int i2 = position;
        return instantiateItem((View) container, position);
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ViewGroup container = viewGroup;
        int position = i;
        Object object = obj;
        ViewGroup viewGroup2 = container;
        int i2 = position;
        Object obj2 = object;
        destroyItem((View) container, position, object);
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        ViewGroup container = viewGroup;
        int position = i;
        Object object = obj;
        ViewGroup viewGroup2 = container;
        int i2 = position;
        Object obj2 = object;
        setPrimaryItem((View) container, position, object);
    }

    public void finishUpdate(ViewGroup viewGroup) {
        ViewGroup container = viewGroup;
        ViewGroup viewGroup2 = container;
        finishUpdate((View) container);
    }

    @Deprecated
    public void startUpdate(View view) {
        View view2 = view;
    }

    @Deprecated
    public Object instantiateItem(View view, int i) {
        View view2 = view;
        int i2 = i;
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    @Deprecated
    public void destroyItem(View view, int i, Object obj) {
        View view2 = view;
        int i2 = i;
        Object obj2 = obj;
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    @Deprecated
    public void setPrimaryItem(View view, int i, Object obj) {
        View view2 = view;
        int i2 = i;
        Object obj2 = obj;
    }

    @Deprecated
    public void finishUpdate(View view) {
        View view2 = view;
    }

    public Parcelable saveState() {
        return null;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        Parcelable parcelable2 = parcelable;
        ClassLoader classLoader2 = classLoader;
    }

    public int getItemPosition(Object obj) {
        Object obj2 = obj;
        return -1;
    }

    public void notifyDataSetChanged() {
        synchronized (this) {
            try {
                if (this.mViewPagerObserver != null) {
                    this.mViewPagerObserver.onChanged();
                }
            } finally {
            }
        }
        this.mObservable.notifyChanged();
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        DataSetObserver observer = dataSetObserver;
        DataSetObserver dataSetObserver2 = observer;
        this.mObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        DataSetObserver observer = dataSetObserver;
        DataSetObserver dataSetObserver2 = observer;
        this.mObservable.unregisterObserver(observer);
    }

    /* access modifiers changed from: 0000 */
    public void setViewPagerObserver(DataSetObserver dataSetObserver) {
        DataSetObserver observer = dataSetObserver;
        DataSetObserver dataSetObserver2 = observer;
        synchronized (this) {
            try {
                this.mViewPagerObserver = observer;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public CharSequence getPageTitle(int i) {
        int i2 = i;
        return null;
    }

    public float getPageWidth(int i) {
        int i2 = i;
        return 1.0f;
    }
}
