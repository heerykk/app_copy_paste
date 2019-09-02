package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.LayoutInflaterCompatLollipop */
class LayoutInflaterCompatLollipop {
    LayoutInflaterCompatLollipop() {
    }

    static void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflaterFactory factory = layoutInflaterFactory;
        LayoutInflater layoutInflater2 = inflater;
        LayoutInflaterFactory layoutInflaterFactory2 = factory;
        inflater.setFactory2(factory == null ? null : new FactoryWrapperHC(factory));
    }
}
