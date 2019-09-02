package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.view.LayoutInflaterCompatHC */
class LayoutInflaterCompatHC {
    private static final String TAG = "LayoutInflaterCompatHC";
    private static boolean sCheckedField;
    private static Field sLayoutInflaterFactory2Field;

    /* renamed from: android.support.v4.view.LayoutInflaterCompatHC$FactoryWrapperHC */
    static class FactoryWrapperHC extends FactoryWrapper implements Factory2 {
        FactoryWrapperHC(LayoutInflaterFactory layoutInflaterFactory) {
            LayoutInflaterFactory delegateFactory = layoutInflaterFactory;
            LayoutInflaterFactory layoutInflaterFactory2 = delegateFactory;
            super(delegateFactory);
        }

        public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            View parent = view;
            String name = str;
            Context context2 = context;
            AttributeSet attributeSet2 = attributeSet;
            View view2 = parent;
            String str2 = name;
            Context context3 = context2;
            AttributeSet attributeSet3 = attributeSet2;
            return this.mDelegateFactory.onCreateView(parent, name, context2, attributeSet2);
        }
    }

    LayoutInflaterCompatHC() {
    }

    static void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflaterFactory factory = layoutInflaterFactory;
        LayoutInflater layoutInflater2 = inflater;
        LayoutInflaterFactory layoutInflaterFactory2 = factory;
        FactoryWrapperHC factoryWrapperHC = factory == null ? null : new FactoryWrapperHC(factory);
        inflater.setFactory2(factoryWrapperHC);
        Factory factory2 = inflater.getFactory();
        Factory f = factory2;
        if (!(factory2 instanceof Factory2)) {
            forceSetFactory2(inflater, factoryWrapperHC);
        } else {
            forceSetFactory2(inflater, (Factory2) f);
        }
    }

    static void forceSetFactory2(LayoutInflater layoutInflater, Factory2 factory2) {
        LayoutInflater inflater = layoutInflater;
        Factory2 factory = factory2;
        LayoutInflater layoutInflater2 = inflater;
        Factory2 factory22 = factory;
        if (!sCheckedField) {
            try {
                sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
                sLayoutInflaterFactory2Field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                int e2 = Log.e(TAG, "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", e);
            }
            sCheckedField = true;
        }
        if (sLayoutInflaterFactory2Field != null) {
            try {
                sLayoutInflaterFactory2Field.set(inflater, factory);
            } catch (IllegalAccessException e3) {
                IllegalAccessException illegalAccessException = e3;
                int e4 = Log.e(TAG, "forceSetFactory2 could not set the Factory2 on LayoutInflater " + inflater + "; inflation may have unexpected results.", e3);
            }
        }
    }
}
