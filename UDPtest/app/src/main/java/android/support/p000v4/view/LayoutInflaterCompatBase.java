package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.view.LayoutInflaterCompatBase */
class LayoutInflaterCompatBase {

    /* renamed from: android.support.v4.view.LayoutInflaterCompatBase$FactoryWrapper */
    static class FactoryWrapper implements Factory {
        final LayoutInflaterFactory mDelegateFactory;

        FactoryWrapper(LayoutInflaterFactory layoutInflaterFactory) {
            LayoutInflaterFactory delegateFactory = layoutInflaterFactory;
            LayoutInflaterFactory layoutInflaterFactory2 = delegateFactory;
            this.mDelegateFactory = delegateFactory;
        }

        public View onCreateView(String str, Context context, AttributeSet attributeSet) {
            String name = str;
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            String str2 = name;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            return this.mDelegateFactory.onCreateView(null, name, context2, attrs);
        }

        public String toString() {
            return getClass().getName() + "{" + this.mDelegateFactory + "}";
        }
    }

    LayoutInflaterCompatBase() {
    }

    static void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflaterFactory factory = layoutInflaterFactory;
        LayoutInflater layoutInflater2 = inflater;
        LayoutInflaterFactory layoutInflaterFactory2 = factory;
        inflater.setFactory(factory == null ? null : new FactoryWrapper(factory));
    }

    static LayoutInflaterFactory getFactory(LayoutInflater layoutInflater) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflater layoutInflater2 = inflater;
        Factory factory = inflater.getFactory();
        Factory factory2 = factory;
        if (!(factory instanceof FactoryWrapper)) {
            return null;
        }
        return ((FactoryWrapper) factory2).mDelegateFactory;
    }
}
