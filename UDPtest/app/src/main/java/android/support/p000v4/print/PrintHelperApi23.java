package android.support.p000v4.print;

import android.annotation.TargetApi;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.print.PrintHelperApi23 */
class PrintHelperApi23 extends PrintHelperApi20 {
    /* access modifiers changed from: protected */
    public Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes other = printAttributes;
        PrintAttributes printAttributes2 = other;
        Builder b = super.copyAttributes(other);
        if (other.getDuplexMode() != 0) {
            Builder duplexMode = b.setDuplexMode(other.getDuplexMode());
        }
        return b;
    }

    PrintHelperApi23(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
        this.mIsMinMarginsHandlingCorrect = false;
    }
}
