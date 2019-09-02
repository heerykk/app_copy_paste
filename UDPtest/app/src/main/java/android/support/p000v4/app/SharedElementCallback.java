package android.support.p000v4.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.List;
import java.util.Map;

/* renamed from: android.support.v4.app.SharedElementCallback */
public abstract class SharedElementCallback {
    private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    private static int MAX_IMAGE_SIZE = 1048576;
    private Matrix mTempMatrix;

    /* renamed from: android.support.v4.app.SharedElementCallback$OnSharedElementsReadyListener */
    public interface OnSharedElementsReadyListener {
        void onSharedElementsReady();
    }

    public SharedElementCallback() {
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
        List<String> list4 = list;
        List<View> list5 = list2;
        List<View> list6 = list3;
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
        List<String> list4 = list;
        List<View> list5 = list2;
        List<View> list6 = list3;
    }

    public void onRejectSharedElements(List<View> list) {
        List<View> list2 = list;
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
        List<String> list2 = list;
        Map<String, View> map2 = map;
    }

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
        View sharedElement = view;
        Matrix viewToGlobalMatrix = matrix;
        RectF screenBounds = rectF;
        View view2 = sharedElement;
        Matrix matrix2 = viewToGlobalMatrix;
        RectF rectF2 = screenBounds;
        if (sharedElement instanceof ImageView) {
            ImageView imageView = (ImageView) sharedElement;
            ImageView imageView2 = imageView;
            Drawable d = imageView.getDrawable();
            Drawable bg = imageView2.getBackground();
            if (d != null && bg == null) {
                Bitmap createDrawableBitmap = createDrawableBitmap(d);
                Bitmap bitmap = createDrawableBitmap;
                if (createDrawableBitmap != null) {
                    Bundle bundle = new Bundle();
                    Bundle bundle2 = bundle;
                    bundle.putParcelable(BUNDLE_SNAPSHOT_BITMAP, bitmap);
                    bundle2.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, imageView2.getScaleType().toString());
                    if (imageView2.getScaleType() == ScaleType.MATRIX) {
                        float[] values = new float[9];
                        imageView2.getImageMatrix().getValues(values);
                        bundle2.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, values);
                    }
                    return bundle2;
                }
            }
        }
        int bitmapWidth = Math.round(screenBounds.width());
        int bitmapHeight = Math.round(screenBounds.height());
        Bitmap bitmap2 = null;
        if (bitmapWidth > 0 && bitmapHeight > 0) {
            float scale = Math.min(1.0f, ((float) MAX_IMAGE_SIZE) / ((float) (bitmapWidth * bitmapHeight)));
            int bitmapWidth2 = (int) (((float) bitmapWidth) * scale);
            int bitmapHeight2 = (int) (((float) bitmapHeight) * scale);
            if (this.mTempMatrix == null) {
                this.mTempMatrix = new Matrix();
            }
            this.mTempMatrix.set(viewToGlobalMatrix);
            boolean postTranslate = this.mTempMatrix.postTranslate(-screenBounds.left, -screenBounds.top);
            boolean postScale = this.mTempMatrix.postScale(scale, scale);
            bitmap2 = Bitmap.createBitmap(bitmapWidth2, bitmapHeight2, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            Canvas canvas2 = canvas;
            canvas.concat(this.mTempMatrix);
            sharedElement.draw(canvas2);
        }
        return bitmap2;
    }

    private static Bitmap createDrawableBitmap(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        int width = drawable2.getIntrinsicWidth();
        int height = drawable2.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        float min = Math.min(1.0f, ((float) MAX_IMAGE_SIZE) / ((float) (width * height)));
        float scale = min;
        if ((drawable2 instanceof BitmapDrawable) && min == 1.0f) {
            return ((BitmapDrawable) drawable2).getBitmap();
        }
        int bitmapWidth = (int) (((float) width) * scale);
        int bitmapHeight = (int) (((float) height) * scale);
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect bounds = drawable2.getBounds();
        Rect existingBounds = bounds;
        int left = bounds.left;
        int top = existingBounds.top;
        int right = existingBounds.right;
        int i = existingBounds.bottom;
        int i2 = i;
        drawable2.setBounds(0, 0, bitmapWidth, bitmapHeight);
        drawable2.draw(canvas);
        drawable2.setBounds(left, top, right, i);
        return bitmap;
    }

    public View onCreateSnapshotView(Context context, Parcelable parcelable) {
        Context context2 = context;
        Parcelable snapshot = parcelable;
        Context context3 = context2;
        Parcelable parcelable2 = snapshot;
        ImageView view = null;
        if (snapshot instanceof Bundle) {
            Bundle bundle = (Bundle) snapshot;
            Bundle bundle2 = bundle;
            Bitmap bitmap = (Bitmap) bundle.getParcelable(BUNDLE_SNAPSHOT_BITMAP);
            Bitmap bitmap2 = bitmap;
            if (bitmap == null) {
                return null;
            }
            ImageView imageView = new ImageView(context2);
            ImageView imageView2 = imageView;
            view = imageView;
            imageView2.setImageBitmap(bitmap2);
            imageView2.setScaleType(ScaleType.valueOf(bundle2.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
            if (imageView2.getScaleType() == ScaleType.MATRIX) {
                float[] values = bundle2.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
                Matrix matrix = new Matrix();
                Matrix matrix2 = matrix;
                matrix.setValues(values);
                imageView2.setImageMatrix(matrix2);
            }
        } else if (snapshot instanceof Bitmap) {
            Bitmap bitmap3 = (Bitmap) snapshot;
            ImageView imageView3 = new ImageView(context2);
            view = imageView3;
            imageView3.setImageBitmap(bitmap3);
        }
        return view;
    }

    public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
        OnSharedElementsReadyListener listener = onSharedElementsReadyListener;
        List<String> list3 = list;
        List<View> list4 = list2;
        OnSharedElementsReadyListener onSharedElementsReadyListener2 = listener;
        listener.onSharedElementsReady();
    }
}
