package android.support.p000v4.app;

import java.util.List;

/* renamed from: android.support.v4.app.FragmentManagerNonConfig */
public class FragmentManagerNonConfig {
    private final List<FragmentManagerNonConfig> mChildNonConfigs;
    private final List<Fragment> mFragments;

    FragmentManagerNonConfig(List<Fragment> list, List<FragmentManagerNonConfig> list2) {
        List<Fragment> fragments = list;
        List<FragmentManagerNonConfig> childNonConfigs = list2;
        List<Fragment> list3 = fragments;
        List<FragmentManagerNonConfig> list4 = childNonConfigs;
        this.mFragments = fragments;
        this.mChildNonConfigs = childNonConfigs;
    }

    /* access modifiers changed from: 0000 */
    public List<Fragment> getFragments() {
        return this.mFragments;
    }

    /* access modifiers changed from: 0000 */
    public List<FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }
}
