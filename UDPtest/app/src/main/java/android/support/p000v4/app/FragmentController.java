package android.support.p000v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.p000v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentController */
public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    public static final FragmentController createController(FragmentHostCallback<?> fragmentHostCallback) {
        FragmentHostCallback<?> callbacks = fragmentHostCallback;
        FragmentHostCallback<?> fragmentHostCallback2 = callbacks;
        return new FragmentController(callbacks);
    }

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        FragmentHostCallback<?> callbacks = fragmentHostCallback;
        FragmentHostCallback<?> fragmentHostCallback2 = callbacks;
        this.mHost = callbacks;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mHost.getFragmentManagerImpl();
    }

    public LoaderManager getSupportLoaderManager() {
        return this.mHost.getLoaderManagerImpl();
    }

    @Nullable
    public Fragment findFragmentByWho(String str) {
        String who = str;
        String str2 = who;
        return this.mHost.mFragmentManager.findFragmentByWho(who);
    }

    public int getActiveFragmentsCount() {
        ArrayList<Fragment> arrayList = this.mHost.mFragmentManager.mActive;
        return arrayList != null ? arrayList.size() : 0;
    }

    public List<Fragment> getActiveFragments(List<Fragment> list) {
        List<Fragment> actives = list;
        List<Fragment> actives2 = actives;
        if (this.mHost.mFragmentManager.mActive == null) {
            return null;
        }
        if (actives == null) {
            actives2 = new ArrayList<>(getActiveFragmentsCount());
        }
        boolean addAll = actives2.addAll(this.mHost.mFragmentManager.mActive);
        return actives2;
    }

    public void attachHost(Fragment fragment) {
        Fragment parent = fragment;
        Fragment fragment2 = parent;
        this.mHost.mFragmentManager.attachController(this.mHost, this.mHost, parent);
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View parent = view;
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = parent;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        return this.mHost.mFragmentManager.onCreateView(parent, name, context2, attrs);
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public Parcelable saveAllState() {
        return this.mHost.mFragmentManager.saveAllState();
    }

    @Deprecated
    public void restoreAllState(Parcelable parcelable, List<Fragment> list) {
        Parcelable state = parcelable;
        List<Fragment> nonConfigList = list;
        Parcelable parcelable2 = state;
        List<Fragment> list2 = nonConfigList;
        this.mHost.mFragmentManager.restoreAllState(state, new FragmentManagerNonConfig(nonConfigList, null));
    }

    public void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        Parcelable state = parcelable;
        FragmentManagerNonConfig nonConfig = fragmentManagerNonConfig;
        Parcelable parcelable2 = state;
        FragmentManagerNonConfig fragmentManagerNonConfig2 = nonConfig;
        this.mHost.mFragmentManager.restoreAllState(state, nonConfig);
    }

    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig retainNonConfig = this.mHost.mFragmentManager.retainNonConfig();
        return retainNonConfig == null ? null : retainNonConfig.getFragments();
    }

    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    public void dispatchReallyStop() {
        this.mHost.mFragmentManager.dispatchReallyStop();
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(z);
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(z);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        this.mHost.mFragmentManager.dispatchConfigurationChanged(newConfig);
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Menu menu2 = menu;
        MenuInflater inflater = menuInflater;
        Menu menu3 = menu2;
        MenuInflater menuInflater2 = inflater;
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu2, inflater);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu2);
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(item);
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        return this.mHost.mFragmentManager.dispatchContextItemSelected(item);
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu2);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    public void doLoaderStart() {
        this.mHost.doLoaderStart();
    }

    public void doLoaderStop(boolean z) {
        this.mHost.doLoaderStop(z);
    }

    public void doLoaderRetain() {
        this.mHost.doLoaderRetain();
    }

    public void doLoaderDestroy() {
        this.mHost.doLoaderDestroy();
    }

    public void reportLoaderStart() {
        this.mHost.reportLoaderStart();
    }

    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return this.mHost.retainLoaderNonConfig();
    }

    public void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
        SimpleArrayMap<String, LoaderManager> loaderManagers = simpleArrayMap;
        SimpleArrayMap<String, LoaderManager> simpleArrayMap2 = loaderManagers;
        this.mHost.restoreLoaderNonConfig(loaderManagers);
    }

    public void dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String prefix = str;
        FileDescriptor fd = fileDescriptor;
        PrintWriter writer = printWriter;
        String[] args = strArr;
        String str2 = prefix;
        FileDescriptor fileDescriptor2 = fd;
        PrintWriter printWriter2 = writer;
        String[] strArr2 = args;
        this.mHost.dumpLoaders(prefix, fd, writer, args);
    }
}
