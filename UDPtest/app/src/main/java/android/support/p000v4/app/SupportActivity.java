package android.support.p000v4.app;

import android.app.Activity;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.util.SimpleArrayMap;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.app.SupportActivity */
public class SupportActivity extends Activity {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap<>();

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v4.app.SupportActivity$ExtraData */
    public static class ExtraData {
        public ExtraData() {
        }
    }

    public SupportActivity() {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void putExtraData(ExtraData extraData) {
        ExtraData extraData2 = extraData;
        ExtraData extraData3 = extraData2;
        Object put = this.mExtraDataMap.put(extraData2.getClass(), extraData2);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public <T extends ExtraData> T getExtraData(Class<T> cls) {
        Class<T> extraDataClass = cls;
        Class<T> cls2 = extraDataClass;
        return (ExtraData) this.mExtraDataMap.get(extraDataClass);
    }
}
