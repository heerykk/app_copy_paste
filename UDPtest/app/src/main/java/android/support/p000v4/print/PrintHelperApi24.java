package android.support.p000v4.print;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.print.PrintHelperApi24 */
class PrintHelperApi24 extends PrintHelperApi23 {
    PrintHelperApi24(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
        this.mIsMinMarginsHandlingCorrect = true;
        this.mPrintActivityRespectsOrientation = true;
    }
}
