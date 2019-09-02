package android.support.p000v4.print;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument.Page;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.print.PrintHelperKitkat */
class PrintHelperKitkat {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    Options mDecodeOptions = null;
    protected boolean mIsMinMarginsHandlingCorrect = true;
    private final Object mLock = new Object();
    int mOrientation;
    protected boolean mPrintActivityRespectsOrientation = true;
    int mScaleMode = 2;

    /* renamed from: android.support.v4.print.PrintHelperKitkat$OnPrintFinishCallback */
    public interface OnPrintFinishCallback {
        void onFinish();
    }

    static /* synthetic */ void access$000(PrintHelperKitkat printHelperKitkat, PrintAttributes printAttributes, int i, Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
        PrintHelperKitkat x0 = printHelperKitkat;
        PrintAttributes x1 = printAttributes;
        int x2 = i;
        Bitmap x3 = bitmap;
        ParcelFileDescriptor x4 = parcelFileDescriptor;
        CancellationSignal x5 = cancellationSignal;
        WriteResultCallback x6 = writeResultCallback;
        PrintHelperKitkat printHelperKitkat2 = x0;
        PrintAttributes printAttributes2 = x1;
        int i2 = x2;
        Bitmap bitmap2 = x3;
        ParcelFileDescriptor parcelFileDescriptor2 = x4;
        CancellationSignal cancellationSignal2 = x5;
        WriteResultCallback writeResultCallback2 = x6;
        x0.writeBitmap(x1, x2, x3, x4, x5, x6);
    }

    static /* synthetic */ Bitmap access$100(PrintHelperKitkat printHelperKitkat, Bitmap bitmap, int i) {
        PrintHelperKitkat x0 = printHelperKitkat;
        Bitmap x1 = bitmap;
        int x2 = i;
        PrintHelperKitkat printHelperKitkat2 = x0;
        Bitmap bitmap2 = x1;
        int i2 = x2;
        return x0.convertBitmapForColorMode(x1, x2);
    }

    static /* synthetic */ Matrix access$200(PrintHelperKitkat printHelperKitkat, int i, int i2, RectF rectF, int i3) {
        PrintHelperKitkat x0 = printHelperKitkat;
        int x1 = i;
        int x2 = i2;
        RectF x3 = rectF;
        int x4 = i3;
        PrintHelperKitkat printHelperKitkat2 = x0;
        int i4 = x1;
        int i5 = x2;
        RectF rectF2 = x3;
        int i6 = x4;
        return x0.getMatrix(x1, x2, x3, x4);
    }

    static /* synthetic */ Bitmap access$400(PrintHelperKitkat printHelperKitkat, Uri uri, int i) throws FileNotFoundException {
        PrintHelperKitkat x0 = printHelperKitkat;
        Uri x1 = uri;
        int x2 = i;
        PrintHelperKitkat printHelperKitkat2 = x0;
        Uri uri2 = x1;
        int i2 = x2;
        return x0.loadConstrainedBitmap(x1, x2);
    }

    static /* synthetic */ boolean access$600(Bitmap bitmap) {
        Bitmap x0 = bitmap;
        Bitmap bitmap2 = x0;
        return isPortrait(x0);
    }

    static /* synthetic */ Object access$700(PrintHelperKitkat printHelperKitkat) {
        PrintHelperKitkat x0 = printHelperKitkat;
        PrintHelperKitkat printHelperKitkat2 = x0;
        return x0.mLock;
    }

    PrintHelperKitkat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mContext = context2;
    }

    public void setScaleMode(int i) {
        int scaleMode = i;
        int i2 = scaleMode;
        this.mScaleMode = scaleMode;
    }

    public int getScaleMode() {
        return this.mScaleMode;
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
        if (this.mOrientation != 0) {
            return this.mOrientation;
        }
        return 1;
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    private static boolean isPortrait(Bitmap bitmap) {
        Bitmap bitmap2 = bitmap;
        Bitmap bitmap3 = bitmap2;
        if (bitmap2.getWidth() > bitmap2.getHeight()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes other = printAttributes;
        PrintAttributes printAttributes2 = other;
        Builder b = new Builder().setMediaSize(other.getMediaSize()).setResolution(other.getResolution()).setMinMargins(other.getMinMargins());
        if (other.getColorMode() != 0) {
            Builder colorMode = b.setColorMode(other.getColorMode());
        }
        return b;
    }

    public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        MediaSize mediaSize;
        String jobName = str;
        Bitmap bitmap2 = bitmap;
        OnPrintFinishCallback callback = onPrintFinishCallback;
        String str2 = jobName;
        Bitmap bitmap3 = bitmap2;
        OnPrintFinishCallback onPrintFinishCallback2 = callback;
        if (bitmap2 != null) {
            int fittingMode = this.mScaleMode;
            PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
            if (!isPortrait(bitmap2)) {
                mediaSize = MediaSize.UNKNOWN_LANDSCAPE;
            } else {
                mediaSize = MediaSize.UNKNOWN_PORTRAIT;
            }
            PrintAttributes attr = new Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
            final String str3 = jobName;
            final int i = fittingMode;
            final Bitmap bitmap4 = bitmap2;
            final OnPrintFinishCallback onPrintFinishCallback3 = callback;
            C01801 r0 = new PrintDocumentAdapter(this) {
                private PrintAttributes mAttributes;
                final /* synthetic */ PrintHelperKitkat this$0;

                {
                    PrintHelperKitkat this$02 = r9;
                    String str = r10;
                    int i = r11;
                    Bitmap bitmap = r12;
                    OnPrintFinishCallback onPrintFinishCallback = r13;
                    PrintHelperKitkat printHelperKitkat = this$02;
                    this.this$0 = this$02;
                }

                public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                    PrintAttributes oldPrintAttributes = printAttributes;
                    PrintAttributes newPrintAttributes = printAttributes2;
                    LayoutResultCallback layoutResultCallback2 = layoutResultCallback;
                    PrintAttributes printAttributes3 = oldPrintAttributes;
                    PrintAttributes printAttributes4 = newPrintAttributes;
                    CancellationSignal cancellationSignal2 = cancellationSignal;
                    LayoutResultCallback layoutResultCallback3 = layoutResultCallback2;
                    Bundle bundle2 = bundle;
                    this.mAttributes = newPrintAttributes;
                    layoutResultCallback2.onLayoutFinished(new PrintDocumentInfo.Builder(str3).setContentType(1).setPageCount(1).build(), !newPrintAttributes.equals(oldPrintAttributes));
                }

                public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
                    ParcelFileDescriptor fileDescriptor = parcelFileDescriptor;
                    CancellationSignal cancellationSignal2 = cancellationSignal;
                    WriteResultCallback writeResultCallback2 = writeResultCallback;
                    PageRange[] pageRangeArr2 = pageRangeArr;
                    ParcelFileDescriptor parcelFileDescriptor2 = fileDescriptor;
                    CancellationSignal cancellationSignal3 = cancellationSignal2;
                    WriteResultCallback writeResultCallback3 = writeResultCallback2;
                    PrintHelperKitkat.access$000(this.this$0, this.mAttributes, i, bitmap4, fileDescriptor, cancellationSignal2, writeResultCallback2);
                }

                public void onFinish() {
                    if (onPrintFinishCallback3 != null) {
                        onPrintFinishCallback3.onFinish();
                    }
                }
            };
            PrintJob print = printManager.print(jobName, r0, attr);
        }
    }

    private Matrix getMatrix(int i, int i2, RectF rectF, int i3) {
        float scale;
        int imageWidth = i;
        int imageHeight = i2;
        RectF content = rectF;
        int fittingMode = i3;
        int i4 = imageWidth;
        int i5 = imageHeight;
        RectF rectF2 = content;
        int i6 = fittingMode;
        Matrix matrix = new Matrix();
        float width = content.width() / ((float) imageWidth);
        float f = width;
        if (fittingMode != 2) {
            scale = Math.min(width, content.height() / ((float) imageHeight));
        } else {
            scale = Math.max(width, content.height() / ((float) imageHeight));
        }
        boolean postScale = matrix.postScale(scale, scale);
        float height = (content.height() - (((float) imageHeight) * scale)) / 2.0f;
        float f2 = height;
        boolean postTranslate = matrix.postTranslate((content.width() - (((float) imageWidth) * scale)) / 2.0f, height);
        return matrix;
    }

    private void writeBitmap(PrintAttributes printAttributes, int i, Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
        PrintAttributes pdfAttributes;
        PrintAttributes attributes = printAttributes;
        int fittingMode = i;
        Bitmap bitmap2 = bitmap;
        ParcelFileDescriptor fileDescriptor = parcelFileDescriptor;
        CancellationSignal cancellationSignal2 = cancellationSignal;
        WriteResultCallback writeResultCallback2 = writeResultCallback;
        PrintAttributes printAttributes2 = attributes;
        int i2 = fittingMode;
        Bitmap bitmap3 = bitmap2;
        ParcelFileDescriptor parcelFileDescriptor2 = fileDescriptor;
        CancellationSignal cancellationSignal3 = cancellationSignal2;
        WriteResultCallback writeResultCallback3 = writeResultCallback2;
        if (!this.mIsMinMarginsHandlingCorrect) {
            pdfAttributes = copyAttributes(attributes).setMinMargins(new Margins(0, 0, 0, 0)).build();
        } else {
            pdfAttributes = attributes;
        }
        final CancellationSignal cancellationSignal4 = cancellationSignal2;
        final PrintAttributes printAttributes3 = pdfAttributes;
        final Bitmap bitmap4 = bitmap2;
        final PrintAttributes printAttributes4 = attributes;
        final int i3 = fittingMode;
        final ParcelFileDescriptor parcelFileDescriptor3 = fileDescriptor;
        final WriteResultCallback writeResultCallback4 = writeResultCallback2;
        C01812 r0 = new AsyncTask<Void, Void, Throwable>(this) {
            final /* synthetic */ PrintHelperKitkat this$0;

            {
                PrintHelperKitkat this$02 = r12;
                CancellationSignal cancellationSignal = r13;
                PrintAttributes printAttributes = r14;
                Bitmap bitmap = r15;
                PrintAttributes printAttributes2 = r16;
                int i = r17;
                ParcelFileDescriptor parcelFileDescriptor = r18;
                WriteResultCallback writeResultCallback = r19;
                PrintHelperKitkat printHelperKitkat = this$02;
                this.this$0 = this$02;
            }

            /* access modifiers changed from: protected */
            public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
                return doInBackground((Void[]) objArr);
            }

            /* access modifiers changed from: protected */
            public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
                onPostExecute((Throwable) obj);
            }

            /* access modifiers changed from: protected */
            public Throwable doInBackground(Void... voidArr) {
                PrintedPdfDocument pdfDocument;
                Bitmap maybeGrayscale;
                RectF contentRect;
                Void[] voidArr2 = voidArr;
                try {
                    if (cancellationSignal4.isCanceled()) {
                        return null;
                    }
                    pdfDocument = new PrintedPdfDocument(this.this$0.mContext, printAttributes3);
                    maybeGrayscale = PrintHelperKitkat.access$100(this.this$0, bitmap4, printAttributes3.getColorMode());
                    if (cancellationSignal4.isCanceled()) {
                        return null;
                    }
                    Page page = pdfDocument.startPage(1);
                    if (!this.this$0.mIsMinMarginsHandlingCorrect) {
                        PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(this.this$0.mContext, printAttributes4);
                        PrintedPdfDocument dummyDocument = printedPdfDocument;
                        Page dummyPage = printedPdfDocument.startPage(1);
                        contentRect = new RectF(dummyPage.getInfo().getContentRect());
                        dummyDocument.finishPage(dummyPage);
                        dummyDocument.close();
                    } else {
                        contentRect = new RectF(page.getInfo().getContentRect());
                    }
                    Matrix matrix = PrintHelperKitkat.access$200(this.this$0, maybeGrayscale.getWidth(), maybeGrayscale.getHeight(), contentRect, i3);
                    if (!this.this$0.mIsMinMarginsHandlingCorrect) {
                        boolean postTranslate = matrix.postTranslate(contentRect.left, contentRect.top);
                        boolean clipRect = page.getCanvas().clipRect(contentRect);
                    }
                    page.getCanvas().drawBitmap(maybeGrayscale, matrix, null);
                    pdfDocument.finishPage(page);
                    if (!cancellationSignal4.isCanceled()) {
                        FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor3.getFileDescriptor());
                        pdfDocument.writeTo(fileOutputStream);
                        pdfDocument.close();
                        if (parcelFileDescriptor3 != null) {
                            try {
                                parcelFileDescriptor3.close();
                            } catch (IOException e) {
                            }
                        }
                        if (maybeGrayscale != bitmap4) {
                            maybeGrayscale.recycle();
                        }
                        return null;
                    }
                    pdfDocument.close();
                    if (parcelFileDescriptor3 != null) {
                        try {
                            parcelFileDescriptor3.close();
                        } catch (IOException e2) {
                        }
                    }
                    if (maybeGrayscale != bitmap4) {
                        maybeGrayscale.recycle();
                    }
                    return null;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    return th;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Throwable th) {
                Throwable throwable = th;
                Throwable th2 = throwable;
                if (cancellationSignal4.isCanceled()) {
                    writeResultCallback4.onWriteCancelled();
                } else if (throwable != null) {
                    int e = Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", throwable);
                    writeResultCallback4.onWriteFailed(null);
                } else {
                    WriteResultCallback writeResultCallback = writeResultCallback4;
                    PageRange[] pageRangeArr = new PageRange[1];
                    pageRangeArr[0] = PageRange.ALL_PAGES;
                    writeResultCallback.onWriteFinished(pageRangeArr);
                }
            }
        };
        AsyncTask execute = r0.execute(new Void[0]);
    }

    public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        String jobName = str;
        Uri imageFile = uri;
        OnPrintFinishCallback callback = onPrintFinishCallback;
        String str2 = jobName;
        Uri uri2 = imageFile;
        OnPrintFinishCallback onPrintFinishCallback2 = callback;
        int i = this.mScaleMode;
        int i2 = i;
        final String str3 = jobName;
        final Uri uri3 = imageFile;
        final OnPrintFinishCallback onPrintFinishCallback3 = callback;
        final int i3 = i;
        C01823 r0 = new PrintDocumentAdapter(this) {
            private PrintAttributes mAttributes;
            Bitmap mBitmap = null;
            AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
            final /* synthetic */ PrintHelperKitkat this$0;

            {
                PrintHelperKitkat this$02 = r11;
                String str = r12;
                Uri uri = r13;
                OnPrintFinishCallback onPrintFinishCallback = r14;
                int i = r15;
                PrintHelperKitkat printHelperKitkat = this$02;
                this.this$0 = this$02;
            }

            static /* synthetic */ void access$300(C01823 r2) {
                C01823 x0 = r2;
                C01823 r1 = x0;
                x0.cancelLoad();
            }

            static /* synthetic */ PrintAttributes access$500(C01823 r4) {
                C01823 x0 = r4;
                C01823 r1 = x0;
                return x0.mAttributes;
            }

            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                PrintAttributes oldPrintAttributes = printAttributes;
                PrintAttributes newPrintAttributes = printAttributes2;
                CancellationSignal cancellationSignal2 = cancellationSignal;
                LayoutResultCallback layoutResultCallback2 = layoutResultCallback;
                PrintAttributes printAttributes3 = oldPrintAttributes;
                PrintAttributes printAttributes4 = newPrintAttributes;
                CancellationSignal cancellationSignal3 = cancellationSignal2;
                LayoutResultCallback layoutResultCallback3 = layoutResultCallback2;
                Bundle bundle2 = bundle;
                synchronized (this) {
                    try {
                        this.mAttributes = newPrintAttributes;
                    } finally {
                        while (true) {
                        }
                    }
                }
                if (cancellationSignal2.isCanceled() != null) {
                    layoutResultCallback2.onLayoutCancelled();
                } else if (this.mBitmap == null) {
                    final CancellationSignal cancellationSignal4 = cancellationSignal2;
                    final PrintAttributes printAttributes5 = newPrintAttributes;
                    final PrintAttributes printAttributes6 = oldPrintAttributes;
                    final LayoutResultCallback layoutResultCallback4 = layoutResultCallback2;
                    this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>(this) {
                        final /* synthetic */ C01823 this$1;

                        {
                            C01823 this$12 = r9;
                            CancellationSignal cancellationSignal = r10;
                            PrintAttributes printAttributes = r11;
                            PrintAttributes printAttributes2 = r12;
                            LayoutResultCallback layoutResultCallback = r13;
                            C01823 r7 = this$12;
                            this.this$1 = this$12;
                        }

                        /* access modifiers changed from: protected */
                        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
                            return doInBackground((Uri[]) objArr);
                        }

                        /* access modifiers changed from: protected */
                        public /* bridge */ /* synthetic */ void onCancelled(Object obj) {
                            onCancelled((Bitmap) obj);
                        }

                        /* access modifiers changed from: protected */
                        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
                            onPostExecute((Bitmap) obj);
                        }

                        /* access modifiers changed from: protected */
                        public void onPreExecute() {
                            cancellationSignal4.setOnCancelListener(new OnCancelListener(this) {
                                final /* synthetic */ C01831 this$2;

                                {
                                    C01831 this$22 = r5;
                                    C01831 r3 = this$22;
                                    this.this$2 = this$22;
                                }

                                public void onCancel() {
                                    C01823.access$300(this.this$2.this$1);
                                    boolean cancel = this.this$2.cancel(false);
                                }
                            });
                        }

                        /* access modifiers changed from: protected */
                        public Bitmap doInBackground(Uri... uriArr) {
                            Uri[] uriArr2 = uriArr;
                            try {
                                return PrintHelperKitkat.access$400(this.this$1.this$0, uri3, PrintHelperKitkat.MAX_PRINT_SIZE);
                            } catch (FileNotFoundException e) {
                                return null;
                            }
                        }

                        /* access modifiers changed from: protected */
                        public void onPostExecute(Bitmap bitmap) {
                            Bitmap bitmap2 = bitmap;
                            Bitmap bitmap3 = bitmap2;
                            super.onPostExecute(bitmap2);
                            if (bitmap2 != null && (!this.this$1.this$0.mPrintActivityRespectsOrientation || this.this$1.this$0.mOrientation == 0)) {
                                synchronized (this) {
                                    try {
                                        mediaSize = C01823.access$500(this.this$1).getMediaSize();
                                    } finally {
                                        while (true) {
                                        }
                                    }
                                }
                                if (!(mediaSize == null || mediaSize.isPortrait() == PrintHelperKitkat.access$600(bitmap2))) {
                                    Matrix matrix = new Matrix();
                                    Matrix matrix2 = matrix;
                                    boolean postRotate = matrix.postRotate(90.0f);
                                    bitmap3 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix2, true);
                                }
                            }
                            this.this$1.mBitmap = bitmap3;
                            if (bitmap3 == null) {
                                layoutResultCallback4.onLayoutFailed(null);
                            } else {
                                layoutResultCallback4.onLayoutFinished(new PrintDocumentInfo.Builder(str3).setContentType(1).setPageCount(1).build(), !printAttributes5.equals(printAttributes6));
                            }
                            this.this$1.mLoadBitmap = null;
                        }

                        /* access modifiers changed from: protected */
                        public void onCancelled(Bitmap bitmap) {
                            Bitmap bitmap2 = bitmap;
                            layoutResultCallback4.onLayoutCancelled();
                            this.this$1.mLoadBitmap = null;
                        }
                    }.execute(new Uri[0]);
                } else {
                    layoutResultCallback2.onLayoutFinished(new PrintDocumentInfo.Builder(str3).setContentType(1).setPageCount(1).build(), !newPrintAttributes.equals(oldPrintAttributes));
                }
            }

            private void cancelLoad() {
                Object access$700 = PrintHelperKitkat.access$700(this.this$0);
                Object obj = access$700;
                synchronized (access$700) {
                    try {
                        if (this.this$0.mDecodeOptions != null) {
                            this.this$0.mDecodeOptions.requestCancelDecode();
                            this.this$0.mDecodeOptions = null;
                        }
                    } finally {
                        Object obj2 = obj;
                    }
                }
            }

            public void onFinish() {
                super.onFinish();
                cancelLoad();
                if (this.mLoadBitmap != null) {
                    boolean cancel = this.mLoadBitmap.cancel(true);
                }
                if (onPrintFinishCallback3 != null) {
                    onPrintFinishCallback3.onFinish();
                }
                if (this.mBitmap != null) {
                    this.mBitmap.recycle();
                    this.mBitmap = null;
                }
            }

            public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
                ParcelFileDescriptor fileDescriptor = parcelFileDescriptor;
                CancellationSignal cancellationSignal2 = cancellationSignal;
                WriteResultCallback writeResultCallback2 = writeResultCallback;
                PageRange[] pageRangeArr2 = pageRangeArr;
                ParcelFileDescriptor parcelFileDescriptor2 = fileDescriptor;
                CancellationSignal cancellationSignal3 = cancellationSignal2;
                WriteResultCallback writeResultCallback3 = writeResultCallback2;
                PrintHelperKitkat.access$000(this.this$0, this.mAttributes, i3, this.mBitmap, fileDescriptor, cancellationSignal2, writeResultCallback2);
            }
        };
        C01823 r21 = r0;
        PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
        Builder builder = new Builder();
        Builder builder2 = builder;
        Builder colorMode = builder.setColorMode(this.mColorMode);
        if (this.mOrientation == 1 || this.mOrientation == 0) {
            Builder mediaSize = builder2.setMediaSize(MediaSize.UNKNOWN_LANDSCAPE);
        } else if (this.mOrientation == 2) {
            Builder mediaSize2 = builder2.setMediaSize(MediaSize.UNKNOWN_PORTRAIT);
        }
        PrintJob print = printManager.print(jobName, r21, builder2.build());
    }

    private Bitmap loadConstrainedBitmap(Uri uri, int i) throws FileNotFoundException {
        Object obj;
        Uri uri2 = uri;
        int maxSideLength = i;
        Uri uri3 = uri2;
        int i2 = maxSideLength;
        if (maxSideLength <= 0 || uri2 == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Options options = new Options();
        Options opt = options;
        options.inJustDecodeBounds = true;
        Bitmap loadBitmap = loadBitmap(uri2, opt);
        int w = opt.outWidth;
        int h = opt.outHeight;
        if (w <= 0 || h <= 0) {
            return null;
        }
        int imageSide = Math.max(w, h);
        int sampleSize = 1;
        while (imageSide > maxSideLength) {
            imageSide >>>= 1;
            sampleSize <<= 1;
        }
        if (sampleSize <= 0 || 0 >= Math.min(w, h) / sampleSize) {
            return null;
        }
        Object obj2 = this.mLock;
        Options options2 = obj2;
        synchronized (obj2) {
            try {
                this.mDecodeOptions = new Options();
                this.mDecodeOptions.inMutable = true;
                this.mDecodeOptions.inSampleSize = sampleSize;
                Options decodeOptions = this.mDecodeOptions;
                try {
                    Bitmap loadBitmap2 = loadBitmap(uri2, decodeOptions);
                    obj = this.mLock;
                    synchronized (obj) {
                        try {
                            this.mDecodeOptions = null;
                            return loadBitmap2;
                        } finally {
                            while (true) {
                                Object obj3 = obj;
                            }
                        }
                    }
                } catch (Throwable th) {
                    obj = this.mLock;
                    synchronized (obj) {
                        this.mDecodeOptions = null;
                        Object obj4 = obj;
                        throw th;
                    }
                } finally {
                    while (true) {
                        obj = obj;
                    }
                }
            } finally {
                while (true) {
                    Options options3 = options2;
                }
            }
        }
    }

    private Bitmap loadBitmap(Uri uri, Options options) throws FileNotFoundException {
        Uri uri2 = uri;
        Options o = options;
        Uri uri3 = uri2;
        Options options2 = o;
        if (uri2 == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream is = null;
        try {
            InputStream openInputStream = this.mContext.getContentResolver().openInputStream(uri2);
            is = openInputStream;
            Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream, null, o);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    IOException iOException = e;
                    int w = Log.w(LOG_TAG, "close fail ", e);
                }
            }
            return decodeStream;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    IOException iOException2 = e2;
                    int w2 = Log.w(LOG_TAG, "close fail ", e2);
                }
            }
            throw th;
        }
    }

    private Bitmap convertBitmapForColorMode(Bitmap bitmap, int i) {
        Bitmap original = bitmap;
        int colorMode = i;
        Bitmap bitmap2 = original;
        int i2 = colorMode;
        if (colorMode != 1) {
            return original;
        }
        Bitmap grayscale = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(grayscale);
        Canvas c = canvas;
        Paint p = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix cm = colorMatrix;
        colorMatrix.setSaturation(0.0f);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(cm);
        ColorFilter colorFilter = p.setColorFilter(colorMatrixColorFilter);
        c.drawBitmap(original, 0.0f, 0.0f, p);
        c.setBitmap(null);
        return grayscale;
    }
}
