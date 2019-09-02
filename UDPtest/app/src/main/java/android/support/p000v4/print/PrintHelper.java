package android.support.p000v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import java.io.FileNotFoundException;

/* renamed from: android.support.v4.print.PrintHelper */
public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    PrintHelperVersionImpl mImpl;

    /* renamed from: android.support.v4.print.PrintHelper$OnPrintFinishCallback */
    public interface OnPrintFinishCallback {
        void onFinish();
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperApi20Impl */
    private static final class PrintHelperApi20Impl extends PrintHelperImpl<PrintHelperApi20> {
        PrintHelperApi20Impl(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(new PrintHelperApi20(context2));
        }
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperApi23Impl */
    private static final class PrintHelperApi23Impl extends PrintHelperImpl<PrintHelperApi23> {
        PrintHelperApi23Impl(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(new PrintHelperApi23(context2));
        }
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperApi24Impl */
    private static final class PrintHelperApi24Impl extends PrintHelperImpl<PrintHelperApi24> {
        PrintHelperApi24Impl(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(new PrintHelperApi24(context2));
        }
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperImpl */
    private static class PrintHelperImpl<RealHelper extends PrintHelperKitkat> implements PrintHelperVersionImpl {
        private final RealHelper mPrintHelper;

        protected PrintHelperImpl(RealHelper realhelper) {
            RealHelper helper = realhelper;
            RealHelper realhelper2 = helper;
            this.mPrintHelper = helper;
        }

        public void setScaleMode(int i) {
            int scaleMode = i;
            int i2 = scaleMode;
            this.mPrintHelper.setScaleMode(scaleMode);
        }

        public int getScaleMode() {
            return this.mPrintHelper.getScaleMode();
        }

        public void setColorMode(int i) {
            int colorMode = i;
            int i2 = colorMode;
            this.mPrintHelper.setColorMode(colorMode);
        }

        public int getColorMode() {
            return this.mPrintHelper.getColorMode();
        }

        public void setOrientation(int i) {
            int orientation = i;
            int i2 = orientation;
            this.mPrintHelper.setOrientation(orientation);
        }

        public int getOrientation() {
            return this.mPrintHelper.getOrientation();
        }

        public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            String jobName = str;
            Bitmap bitmap2 = bitmap;
            OnPrintFinishCallback callback = onPrintFinishCallback;
            String str2 = jobName;
            Bitmap bitmap3 = bitmap2;
            OnPrintFinishCallback onPrintFinishCallback2 = callback;
            C01781 r9 = null;
            if (callback != null) {
                final OnPrintFinishCallback onPrintFinishCallback3 = callback;
                r9 = new android.support.p000v4.print.PrintHelperKitkat.OnPrintFinishCallback(this) {
                    final /* synthetic */ PrintHelperImpl this$0;

                    {
                        PrintHelperImpl this$02 = r6;
                        OnPrintFinishCallback onPrintFinishCallback = r7;
                        PrintHelperImpl printHelperImpl = this$02;
                        this.this$0 = this$02;
                    }

                    public void onFinish() {
                        onPrintFinishCallback3.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap(jobName, bitmap2, (android.support.p000v4.print.PrintHelperKitkat.OnPrintFinishCallback) r9);
        }

        public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
            String jobName = str;
            Uri imageFile = uri;
            OnPrintFinishCallback callback = onPrintFinishCallback;
            String str2 = jobName;
            Uri uri2 = imageFile;
            OnPrintFinishCallback onPrintFinishCallback2 = callback;
            C01792 r9 = null;
            if (callback != null) {
                final OnPrintFinishCallback onPrintFinishCallback3 = callback;
                r9 = new android.support.p000v4.print.PrintHelperKitkat.OnPrintFinishCallback(this) {
                    final /* synthetic */ PrintHelperImpl this$0;

                    {
                        PrintHelperImpl this$02 = r6;
                        OnPrintFinishCallback onPrintFinishCallback = r7;
                        PrintHelperImpl printHelperImpl = this$02;
                        this.this$0 = this$02;
                    }

                    public void onFinish() {
                        onPrintFinishCallback3.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap(jobName, imageFile, (android.support.p000v4.print.PrintHelperKitkat.OnPrintFinishCallback) r9);
        }
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperKitkatImpl */
    private static final class PrintHelperKitkatImpl extends PrintHelperImpl<PrintHelperKitkat> {
        PrintHelperKitkatImpl(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(new PrintHelperKitkat(context2));
        }
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperStubImpl */
    private static final class PrintHelperStubImpl implements PrintHelperVersionImpl {
        int mColorMode;
        int mOrientation;
        int mScaleMode;

        private PrintHelperStubImpl() {
            this.mScaleMode = 2;
            this.mColorMode = 2;
            this.mOrientation = 1;
        }

        /* synthetic */ PrintHelperStubImpl(C01771 r5) {
            C01771 r3 = r5;
            this();
        }

        public void setScaleMode(int i) {
            int scaleMode = i;
            int i2 = scaleMode;
            this.mScaleMode = scaleMode;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public void setColorMode(int i) {
            int colorMode = i;
            int i2 = colorMode;
            this.mColorMode = colorMode;
        }

        public void setOrientation(int i) {
            int orientation = i;
            int i2 = orientation;
            this.mOrientation = orientation;
        }

        public int getOrientation() {
            return this.mOrientation;
        }

        public int getScaleMode() {
            return this.mScaleMode;
        }

        public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            String str2 = str;
            Bitmap bitmap2 = bitmap;
            OnPrintFinishCallback onPrintFinishCallback2 = onPrintFinishCallback;
        }

        public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
            String str2 = str;
            Uri uri2 = uri;
            OnPrintFinishCallback onPrintFinishCallback2 = onPrintFinishCallback;
        }
    }

    /* renamed from: android.support.v4.print.PrintHelper$PrintHelperVersionImpl */
    interface PrintHelperVersionImpl {
        int getColorMode();

        int getOrientation();

        int getScaleMode();

        void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback);

        void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException;

        void setColorMode(int i);

        void setOrientation(int i);

        void setScaleMode(int i);
    }

    public static boolean systemSupportsPrint() {
        if (VERSION.SDK_INT < 19) {
            return false;
        }
        return true;
    }

    public PrintHelper(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (!systemSupportsPrint()) {
            this.mImpl = new PrintHelperStubImpl(null);
        } else if (VERSION.SDK_INT >= 24) {
            this.mImpl = new PrintHelperApi24Impl(context2);
        } else if (VERSION.SDK_INT >= 23) {
            this.mImpl = new PrintHelperApi23Impl(context2);
        } else if (VERSION.SDK_INT < 20) {
            this.mImpl = new PrintHelperKitkatImpl(context2);
        } else {
            this.mImpl = new PrintHelperApi20Impl(context2);
        }
    }

    public void setScaleMode(int i) {
        int scaleMode = i;
        int i2 = scaleMode;
        this.mImpl.setScaleMode(scaleMode);
    }

    public int getScaleMode() {
        return this.mImpl.getScaleMode();
    }

    public void setColorMode(int i) {
        int colorMode = i;
        int i2 = colorMode;
        this.mImpl.setColorMode(colorMode);
    }

    public int getColorMode() {
        return this.mImpl.getColorMode();
    }

    public void setOrientation(int i) {
        int orientation = i;
        int i2 = orientation;
        this.mImpl.setOrientation(orientation);
    }

    public int getOrientation() {
        return this.mImpl.getOrientation();
    }

    public void printBitmap(String str, Bitmap bitmap) {
        String jobName = str;
        Bitmap bitmap2 = bitmap;
        String str2 = jobName;
        Bitmap bitmap3 = bitmap2;
        this.mImpl.printBitmap(jobName, bitmap2, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        String jobName = str;
        Bitmap bitmap2 = bitmap;
        OnPrintFinishCallback callback = onPrintFinishCallback;
        String str2 = jobName;
        Bitmap bitmap3 = bitmap2;
        OnPrintFinishCallback onPrintFinishCallback2 = callback;
        this.mImpl.printBitmap(jobName, bitmap2, callback);
    }

    public void printBitmap(String str, Uri uri) throws FileNotFoundException {
        String jobName = str;
        Uri imageFile = uri;
        String str2 = jobName;
        Uri uri2 = imageFile;
        this.mImpl.printBitmap(jobName, imageFile, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        String jobName = str;
        Uri imageFile = uri;
        OnPrintFinishCallback callback = onPrintFinishCallback;
        String str2 = jobName;
        Uri uri2 = imageFile;
        OnPrintFinishCallback onPrintFinishCallback2 = callback;
        this.mImpl.printBitmap(jobName, imageFile, callback);
    }
}
