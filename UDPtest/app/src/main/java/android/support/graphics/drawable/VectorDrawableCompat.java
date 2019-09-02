package android.support.graphics.drawable;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.VectorDrawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.graphics.drawable.PathParser.PathDataNode;
import android.support.p000v4.content.res.ResourcesCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat extends VectorDrawableCommon {
    private static final boolean DBG_VECTOR_DRAWABLE = false;
    static final Mode DEFAULT_TINT_MODE = Mode.SRC_IN;
    private static final int LINECAP_BUTT = 0;
    private static final int LINECAP_ROUND = 1;
    private static final int LINECAP_SQUARE = 2;
    private static final int LINEJOIN_BEVEL = 2;
    private static final int LINEJOIN_MITER = 0;
    private static final int LINEJOIN_ROUND = 1;
    static final String LOGTAG = "VectorDrawableCompat";
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    private boolean mAllowCaching;
    private ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private final float[] mTmpFloats;
    private final Matrix mTmpMatrix;
    private VectorDrawableCompatState mVectorState;

    private static class VClipPath extends VPath {
        public VClipPath() {
        }

        public VClipPath(VClipPath vClipPath) {
            VClipPath copy = vClipPath;
            VClipPath vClipPath2 = copy;
            super(copy);
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            Resources r = resources;
            AttributeSet attrs = attributeSet;
            Theme theme2 = theme;
            XmlPullParser parser = xmlPullParser;
            Resources resources2 = r;
            AttributeSet attributeSet2 = attrs;
            Theme theme3 = theme2;
            XmlPullParser xmlPullParser2 = parser;
            boolean hasAttribute = TypedArrayUtils.hasAttribute(parser, "pathData");
            boolean z = hasAttribute;
            if (hasAttribute) {
                TypedArray a = VectorDrawableCommon.obtainAttributes(r, theme2, attrs, AndroidResources.styleable_VectorDrawableClipPath);
                updateStateFromTypedArray(a);
                a.recycle();
            }
        }

        private void updateStateFromTypedArray(TypedArray typedArray) {
            TypedArray a = typedArray;
            TypedArray typedArray2 = a;
            String string = a.getString(0);
            String pathName = string;
            if (string != null) {
                this.mPathName = pathName;
            }
            String string2 = a.getString(1);
            String pathData = string2;
            if (string2 != null) {
                this.mNodes = PathParser.createNodesFromPathData(pathData);
            }
        }

        public boolean isClipPath() {
            return true;
        }
    }

    private static class VFullPath extends VPath {
        float mFillAlpha = 1.0f;
        int mFillColor = 0;
        int mFillRule;
        float mStrokeAlpha = 1.0f;
        int mStrokeColor = 0;
        Cap mStrokeLineCap = Cap.BUTT;
        Join mStrokeLineJoin = Join.MITER;
        float mStrokeMiterlimit = 4.0f;
        float mStrokeWidth = 0.0f;
        private int[] mThemeAttrs;
        float mTrimPathEnd = 1.0f;
        float mTrimPathOffset = 0.0f;
        float mTrimPathStart = 0.0f;

        public VFullPath() {
        }

        public VFullPath(VFullPath vFullPath) {
            VFullPath copy = vFullPath;
            VFullPath vFullPath2 = copy;
            super(copy);
            this.mThemeAttrs = copy.mThemeAttrs;
            this.mStrokeColor = copy.mStrokeColor;
            this.mStrokeWidth = copy.mStrokeWidth;
            this.mStrokeAlpha = copy.mStrokeAlpha;
            this.mFillColor = copy.mFillColor;
            this.mFillRule = copy.mFillRule;
            this.mFillAlpha = copy.mFillAlpha;
            this.mTrimPathStart = copy.mTrimPathStart;
            this.mTrimPathEnd = copy.mTrimPathEnd;
            this.mTrimPathOffset = copy.mTrimPathOffset;
            this.mStrokeLineCap = copy.mStrokeLineCap;
            this.mStrokeLineJoin = copy.mStrokeLineJoin;
            this.mStrokeMiterlimit = copy.mStrokeMiterlimit;
        }

        private Cap getStrokeLineCap(int i, Cap cap) {
            int id = i;
            Cap defValue = cap;
            int i2 = id;
            Cap cap2 = defValue;
            switch (id) {
                case 0:
                    return Cap.BUTT;
                case 1:
                    return Cap.ROUND;
                case 2:
                    return Cap.SQUARE;
                default:
                    return defValue;
            }
        }

        private Join getStrokeLineJoin(int i, Join join) {
            int id = i;
            Join defValue = join;
            int i2 = id;
            Join join2 = defValue;
            switch (id) {
                case 0:
                    return Join.MITER;
                case 1:
                    return Join.ROUND;
                case 2:
                    return Join.BEVEL;
                default:
                    return defValue;
            }
        }

        public boolean canApplyTheme() {
            return this.mThemeAttrs != null;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            Resources r = resources;
            AttributeSet attrs = attributeSet;
            Theme theme2 = theme;
            XmlPullParser parser = xmlPullParser;
            Resources resources2 = r;
            AttributeSet attributeSet2 = attrs;
            Theme theme3 = theme2;
            XmlPullParser xmlPullParser2 = parser;
            TypedArray a = VectorDrawableCommon.obtainAttributes(r, theme2, attrs, AndroidResources.styleable_VectorDrawablePath);
            updateStateFromTypedArray(a, parser);
            a.recycle();
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            TypedArray a = typedArray;
            XmlPullParser parser = xmlPullParser;
            TypedArray typedArray2 = a;
            XmlPullParser xmlPullParser2 = parser;
            this.mThemeAttrs = null;
            boolean hasAttribute = TypedArrayUtils.hasAttribute(parser, "pathData");
            boolean z = hasAttribute;
            if (hasAttribute) {
                String string = a.getString(0);
                String pathName = string;
                if (string != null) {
                    this.mPathName = pathName;
                }
                String string2 = a.getString(2);
                String pathData = string2;
                if (string2 != null) {
                    this.mNodes = PathParser.createNodesFromPathData(pathData);
                }
                this.mFillColor = TypedArrayUtils.getNamedColor(a, parser, "fillColor", 1, this.mFillColor);
                this.mFillAlpha = TypedArrayUtils.getNamedFloat(a, parser, "fillAlpha", 12, this.mFillAlpha);
                int namedInt = TypedArrayUtils.getNamedInt(a, parser, "strokeLineCap", 8, -1);
                int i = namedInt;
                this.mStrokeLineCap = getStrokeLineCap(namedInt, this.mStrokeLineCap);
                int namedInt2 = TypedArrayUtils.getNamedInt(a, parser, "strokeLineJoin", 9, -1);
                int i2 = namedInt2;
                this.mStrokeLineJoin = getStrokeLineJoin(namedInt2, this.mStrokeLineJoin);
                this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(a, parser, "strokeMiterLimit", 10, this.mStrokeMiterlimit);
                this.mStrokeColor = TypedArrayUtils.getNamedColor(a, parser, "strokeColor", 3, this.mStrokeColor);
                this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(a, parser, "strokeAlpha", 11, this.mStrokeAlpha);
                this.mStrokeWidth = TypedArrayUtils.getNamedFloat(a, parser, "strokeWidth", 4, this.mStrokeWidth);
                this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(a, parser, "trimPathEnd", 6, this.mTrimPathEnd);
                this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(a, parser, "trimPathOffset", 7, this.mTrimPathOffset);
                this.mTrimPathStart = TypedArrayUtils.getNamedFloat(a, parser, "trimPathStart", 5, this.mTrimPathStart);
            }
        }

        public void applyTheme(Theme theme) {
            Theme theme2 = theme;
            if (this.mThemeAttrs == null) {
            }
        }

        /* access modifiers changed from: 0000 */
        public int getStrokeColor() {
            return this.mStrokeColor;
        }

        /* access modifiers changed from: 0000 */
        public void setStrokeColor(int i) {
            int strokeColor = i;
            int i2 = strokeColor;
            this.mStrokeColor = strokeColor;
        }

        /* access modifiers changed from: 0000 */
        public float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        /* access modifiers changed from: 0000 */
        public void setStrokeWidth(float f) {
            float strokeWidth = f;
            float f2 = strokeWidth;
            this.mStrokeWidth = strokeWidth;
        }

        /* access modifiers changed from: 0000 */
        public float getStrokeAlpha() {
            return this.mStrokeAlpha;
        }

        /* access modifiers changed from: 0000 */
        public void setStrokeAlpha(float f) {
            float strokeAlpha = f;
            float f2 = strokeAlpha;
            this.mStrokeAlpha = strokeAlpha;
        }

        /* access modifiers changed from: 0000 */
        public int getFillColor() {
            return this.mFillColor;
        }

        /* access modifiers changed from: 0000 */
        public void setFillColor(int i) {
            int fillColor = i;
            int i2 = fillColor;
            this.mFillColor = fillColor;
        }

        /* access modifiers changed from: 0000 */
        public float getFillAlpha() {
            return this.mFillAlpha;
        }

        /* access modifiers changed from: 0000 */
        public void setFillAlpha(float f) {
            float fillAlpha = f;
            float f2 = fillAlpha;
            this.mFillAlpha = fillAlpha;
        }

        /* access modifiers changed from: 0000 */
        public float getTrimPathStart() {
            return this.mTrimPathStart;
        }

        /* access modifiers changed from: 0000 */
        public void setTrimPathStart(float f) {
            float trimPathStart = f;
            float f2 = trimPathStart;
            this.mTrimPathStart = trimPathStart;
        }

        /* access modifiers changed from: 0000 */
        public float getTrimPathEnd() {
            return this.mTrimPathEnd;
        }

        /* access modifiers changed from: 0000 */
        public void setTrimPathEnd(float f) {
            float trimPathEnd = f;
            float f2 = trimPathEnd;
            this.mTrimPathEnd = trimPathEnd;
        }

        /* access modifiers changed from: 0000 */
        public float getTrimPathOffset() {
            return this.mTrimPathOffset;
        }

        /* access modifiers changed from: 0000 */
        public void setTrimPathOffset(float f) {
            float trimPathOffset = f;
            float f2 = trimPathOffset;
            this.mTrimPathOffset = trimPathOffset;
        }
    }

    private static class VGroup {
        int mChangingConfigurations;
        final ArrayList<Object> mChildren = new ArrayList<>();
        private String mGroupName = null;
        private final Matrix mLocalMatrix = new Matrix();
        private float mPivotX = 0.0f;
        private float mPivotY = 0.0f;
        float mRotate = 0.0f;
        private float mScaleX = 1.0f;
        private float mScaleY = 1.0f;
        private final Matrix mStackedMatrix = new Matrix();
        private int[] mThemeAttrs;
        private float mTranslateX = 0.0f;
        private float mTranslateY = 0.0f;

        static /* synthetic */ Matrix access$200(VGroup vGroup) {
            VGroup x0 = vGroup;
            VGroup vGroup2 = x0;
            return x0.mStackedMatrix;
        }

        static /* synthetic */ Matrix access$300(VGroup vGroup) {
            VGroup x0 = vGroup;
            VGroup vGroup2 = x0;
            return x0.mLocalMatrix;
        }

        public VGroup() {
        }

        public VGroup(VGroup vGroup, ArrayMap<String, Object> arrayMap) {
            VPath newPath;
            VGroup copy = vGroup;
            ArrayMap<String, Object> targetsMap = arrayMap;
            VGroup vGroup2 = copy;
            ArrayMap<String, Object> arrayMap2 = targetsMap;
            this.mRotate = copy.mRotate;
            this.mPivotX = copy.mPivotX;
            this.mPivotY = copy.mPivotY;
            this.mScaleX = copy.mScaleX;
            this.mScaleY = copy.mScaleY;
            this.mTranslateX = copy.mTranslateX;
            this.mTranslateY = copy.mTranslateY;
            this.mThemeAttrs = copy.mThemeAttrs;
            this.mGroupName = copy.mGroupName;
            this.mChangingConfigurations = copy.mChangingConfigurations;
            if (this.mGroupName != null) {
                Object put = targetsMap.put(this.mGroupName, this);
            }
            this.mLocalMatrix.set(copy.mLocalMatrix);
            ArrayList<Object> children = copy.mChildren;
            for (int i = 0; i < children.size(); i++) {
                Object obj = children.get(i);
                Object copyChild = obj;
                if (!(obj instanceof VGroup)) {
                    if (copyChild instanceof VFullPath) {
                        newPath = new VFullPath((VFullPath) copyChild);
                    } else if (!(copyChild instanceof VClipPath)) {
                        throw new IllegalStateException("Unknown object in the tree!");
                    } else {
                        newPath = new VClipPath((VClipPath) copyChild);
                    }
                    boolean add = this.mChildren.add(newPath);
                    if (newPath.mPathName != null) {
                        Object put2 = targetsMap.put(newPath.mPathName, newPath);
                    }
                } else {
                    boolean add2 = this.mChildren.add(new VGroup((VGroup) copyChild, targetsMap));
                }
            }
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.mLocalMatrix;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            Resources res = resources;
            AttributeSet attrs = attributeSet;
            Theme theme2 = theme;
            XmlPullParser parser = xmlPullParser;
            Resources resources2 = res;
            AttributeSet attributeSet2 = attrs;
            Theme theme3 = theme2;
            XmlPullParser xmlPullParser2 = parser;
            TypedArray a = VectorDrawableCommon.obtainAttributes(res, theme2, attrs, AndroidResources.styleable_VectorDrawableGroup);
            updateStateFromTypedArray(a, parser);
            a.recycle();
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            TypedArray a = typedArray;
            XmlPullParser parser = xmlPullParser;
            TypedArray typedArray2 = a;
            XmlPullParser xmlPullParser2 = parser;
            this.mThemeAttrs = null;
            this.mRotate = TypedArrayUtils.getNamedFloat(a, parser, "rotation", 5, this.mRotate);
            this.mPivotX = a.getFloat(1, this.mPivotX);
            this.mPivotY = a.getFloat(2, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat(a, parser, "scaleX", 3, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat(a, parser, "scaleY", 4, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat(a, parser, "translateX", 6, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat(a, parser, "translateY", 7, this.mTranslateY);
            String string = a.getString(0);
            String groupName = string;
            if (string != null) {
                this.mGroupName = groupName;
            }
            updateLocalMatrix();
        }

        private void updateLocalMatrix() {
            this.mLocalMatrix.reset();
            boolean postTranslate = this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
            boolean postScale = this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
            boolean postRotate = this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f);
            boolean postTranslate2 = this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        public float getRotation() {
            return this.mRotate;
        }

        public void setRotation(float f) {
            float rotation = f;
            float f2 = rotation;
            if (rotation != this.mRotate) {
                this.mRotate = rotation;
                updateLocalMatrix();
            }
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public void setPivotX(float f) {
            float pivotX = f;
            float f2 = pivotX;
            if (pivotX != this.mPivotX) {
                this.mPivotX = pivotX;
                updateLocalMatrix();
            }
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public void setPivotY(float f) {
            float pivotY = f;
            float f2 = pivotY;
            if (pivotY != this.mPivotY) {
                this.mPivotY = pivotY;
                updateLocalMatrix();
            }
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public void setScaleX(float f) {
            float scaleX = f;
            float f2 = scaleX;
            if (scaleX != this.mScaleX) {
                this.mScaleX = scaleX;
                updateLocalMatrix();
            }
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public void setScaleY(float f) {
            float scaleY = f;
            float f2 = scaleY;
            if (scaleY != this.mScaleY) {
                this.mScaleY = scaleY;
                updateLocalMatrix();
            }
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public void setTranslateX(float f) {
            float translateX = f;
            float f2 = translateX;
            if (translateX != this.mTranslateX) {
                this.mTranslateX = translateX;
                updateLocalMatrix();
            }
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        public void setTranslateY(float f) {
            float translateY = f;
            float f2 = translateY;
            if (translateY != this.mTranslateY) {
                this.mTranslateY = translateY;
                updateLocalMatrix();
            }
        }
    }

    private static class VPath {
        int mChangingConfigurations;
        protected PathDataNode[] mNodes = null;
        String mPathName;

        public VPath() {
        }

        public VPath(VPath vPath) {
            VPath copy = vPath;
            VPath vPath2 = copy;
            this.mPathName = copy.mPathName;
            this.mChangingConfigurations = copy.mChangingConfigurations;
            this.mNodes = PathParser.deepCopyNodes(copy.mNodes);
        }

        public void printVPath(int i) {
            int level = i;
            int i2 = level;
            String indent = "";
            for (int i3 = 0; i3 < level; i3++) {
                indent = indent + "    ";
            }
            int v = Log.v(VectorDrawableCompat.LOGTAG, indent + "current path is :" + this.mPathName + " pathData is " + NodesToString(this.mNodes));
        }

        public String NodesToString(PathDataNode[] pathDataNodeArr) {
            PathDataNode[] nodes = pathDataNodeArr;
            PathDataNode[] pathDataNodeArr2 = nodes;
            String result = " ";
            for (int i = 0; i < nodes.length; i++) {
                result = result + nodes[i].type + ":";
                float[] params = nodes[i].params;
                for (int j = 0; j < params.length; j++) {
                    result = result + params[j] + ",";
                }
            }
            return result;
        }

        public void toPath(Path path) {
            Path path2 = path;
            Path path3 = path2;
            path2.reset();
            if (this.mNodes != null) {
                PathDataNode.nodesToPath(this.mNodes, path2);
            }
        }

        public String getPathName() {
            return this.mPathName;
        }

        public boolean canApplyTheme() {
            return false;
        }

        public void applyTheme(Theme theme) {
            Theme theme2 = theme;
        }

        public boolean isClipPath() {
            return false;
        }

        public PathDataNode[] getPathData() {
            return this.mNodes;
        }

        public void setPathData(PathDataNode[] pathDataNodeArr) {
            PathDataNode[] nodes = pathDataNodeArr;
            PathDataNode[] pathDataNodeArr2 = nodes;
            if (PathParser.canMorph(this.mNodes, nodes)) {
                PathParser.updateNodes(this.mNodes, nodes);
            } else {
                this.mNodes = PathParser.deepCopyNodes(nodes);
            }
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX = new Matrix();
        float mBaseHeight;
        float mBaseWidth;
        private int mChangingConfigurations;
        private Paint mFillPaint;
        private final Matrix mFinalPathMatrix;
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;
        int mRootAlpha;
        final VGroup mRootGroup;
        String mRootName;
        private Paint mStrokePaint;
        final ArrayMap<String, Object> mVGTargetsMap;
        float mViewportHeight;
        float mViewportWidth;

        static /* synthetic */ Paint access$000(VPathRenderer vPathRenderer) {
            VPathRenderer x0 = vPathRenderer;
            VPathRenderer vPathRenderer2 = x0;
            return x0.mFillPaint;
        }

        static /* synthetic */ Paint access$002(VPathRenderer vPathRenderer, Paint paint) {
            VPathRenderer x0 = vPathRenderer;
            Paint x1 = paint;
            VPathRenderer vPathRenderer2 = x0;
            Paint paint2 = x1;
            x0.mFillPaint = x1;
            return x1;
        }

        static /* synthetic */ Paint access$100(VPathRenderer vPathRenderer) {
            VPathRenderer x0 = vPathRenderer;
            VPathRenderer vPathRenderer2 = x0;
            return x0.mStrokePaint;
        }

        static /* synthetic */ Paint access$102(VPathRenderer vPathRenderer, Paint paint) {
            VPathRenderer x0 = vPathRenderer;
            Paint x1 = paint;
            VPathRenderer vPathRenderer2 = x0;
            Paint paint2 = x1;
            x0.mStrokePaint = x1;
            return x1;
        }

        public VPathRenderer() {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mVGTargetsMap = new ArrayMap<>();
            this.mRootGroup = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            VPathRenderer copy = vPathRenderer;
            VPathRenderer vPathRenderer2 = copy;
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mVGTargetsMap = new ArrayMap<>();
            this.mRootGroup = new VGroup(copy.mRootGroup, this.mVGTargetsMap);
            this.mPath = new Path(copy.mPath);
            this.mRenderPath = new Path(copy.mRenderPath);
            this.mBaseWidth = copy.mBaseWidth;
            this.mBaseHeight = copy.mBaseHeight;
            this.mViewportWidth = copy.mViewportWidth;
            this.mViewportHeight = copy.mViewportHeight;
            this.mChangingConfigurations = copy.mChangingConfigurations;
            this.mRootAlpha = copy.mRootAlpha;
            this.mRootName = copy.mRootName;
            if (copy.mRootName != null) {
                Object put = this.mVGTargetsMap.put(copy.mRootName, this);
            }
        }

        public void setRootAlpha(int i) {
            int alpha = i;
            int i2 = alpha;
            this.mRootAlpha = alpha;
        }

        public int getRootAlpha() {
            return this.mRootAlpha;
        }

        public void setAlpha(float f) {
            float alpha = f;
            float f2 = alpha;
            setRootAlpha((int) (alpha * 255.0f));
        }

        public float getAlpha() {
            return ((float) getRootAlpha()) / 255.0f;
        }

        private void drawGroupTree(VGroup vGroup, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            VGroup currentGroup = vGroup;
            Matrix currentMatrix = matrix;
            Canvas canvas2 = canvas;
            int w = i;
            int h = i2;
            ColorFilter filter = colorFilter;
            VGroup vGroup2 = currentGroup;
            Matrix matrix2 = currentMatrix;
            Canvas canvas3 = canvas2;
            int i3 = w;
            int i4 = h;
            ColorFilter colorFilter2 = filter;
            VGroup.access$200(currentGroup).set(currentMatrix);
            boolean preConcat = VGroup.access$200(currentGroup).preConcat(VGroup.access$300(currentGroup));
            int save = canvas2.save();
            for (int i5 = 0; i5 < currentGroup.mChildren.size(); i5++) {
                Object obj = currentGroup.mChildren.get(i5);
                Object child = obj;
                if (obj instanceof VGroup) {
                    drawGroupTree((VGroup) child, VGroup.access$200(currentGroup), canvas2, w, h, filter);
                } else if (child instanceof VPath) {
                    drawPath(currentGroup, (VPath) child, canvas2, w, h, filter);
                }
            }
            canvas2.restore();
        }

        public void draw(Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            Canvas canvas2 = canvas;
            int w = i;
            int h = i2;
            ColorFilter filter = colorFilter;
            Canvas canvas3 = canvas2;
            int i3 = w;
            int i4 = h;
            ColorFilter colorFilter2 = filter;
            drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, canvas2, w, h, filter);
        }

        private void drawPath(VGroup vGroup, VPath vPath, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            VGroup vGroup2 = vGroup;
            VPath vPath2 = vPath;
            Canvas canvas2 = canvas;
            int w = i;
            int h = i2;
            ColorFilter filter = colorFilter;
            VGroup vGroup3 = vGroup2;
            VPath vPath3 = vPath2;
            Canvas canvas3 = canvas2;
            int i3 = w;
            int i4 = h;
            ColorFilter colorFilter2 = filter;
            float scaleX = ((float) w) / this.mViewportWidth;
            float scaleY = ((float) h) / this.mViewportHeight;
            float minScale = Math.min(scaleX, scaleY);
            Matrix groupStackedMatrix = VGroup.access$200(vGroup2);
            this.mFinalPathMatrix.set(groupStackedMatrix);
            boolean postScale = this.mFinalPathMatrix.postScale(scaleX, scaleY);
            float matrixScale = getMatrixScale(groupStackedMatrix);
            float matrixScale2 = matrixScale;
            if (matrixScale != 0.0f) {
                vPath2.toPath(this.mPath);
                Path path = this.mPath;
                this.mRenderPath.reset();
                if (!vPath2.isClipPath()) {
                    VFullPath vFullPath = (VFullPath) vPath2;
                    VFullPath fullPath = vFullPath;
                    if (!(vFullPath.mTrimPathStart == 0.0f && fullPath.mTrimPathEnd == 1.0f)) {
                        float start = (fullPath.mTrimPathStart + fullPath.mTrimPathOffset) % 1.0f;
                        float end = (fullPath.mTrimPathEnd + fullPath.mTrimPathOffset) % 1.0f;
                        if (this.mPathMeasure == null) {
                            this.mPathMeasure = new PathMeasure();
                        }
                        this.mPathMeasure.setPath(this.mPath, false);
                        float len = this.mPathMeasure.getLength();
                        float start2 = start * len;
                        float end2 = end * len;
                        path.reset();
                        if (start2 > end2) {
                            boolean segment = this.mPathMeasure.getSegment(start2, len, path, true);
                            boolean segment2 = this.mPathMeasure.getSegment(0.0f, end2, path, true);
                        } else {
                            boolean segment3 = this.mPathMeasure.getSegment(start2, end2, path, true);
                        }
                        path.rLineTo(0.0f, 0.0f);
                    }
                    this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                    if (fullPath.mFillColor != 0) {
                        if (this.mFillPaint == null) {
                            this.mFillPaint = new Paint();
                            this.mFillPaint.setStyle(Style.FILL);
                            this.mFillPaint.setAntiAlias(true);
                        }
                        Paint paint = this.mFillPaint;
                        Paint fillPaint = paint;
                        paint.setColor(VectorDrawableCompat.applyAlpha(fullPath.mFillColor, fullPath.mFillAlpha));
                        ColorFilter colorFilter3 = fillPaint.setColorFilter(filter);
                        canvas2.drawPath(this.mRenderPath, fillPaint);
                    }
                    if (fullPath.mStrokeColor != 0) {
                        if (this.mStrokePaint == null) {
                            this.mStrokePaint = new Paint();
                            this.mStrokePaint.setStyle(Style.STROKE);
                            this.mStrokePaint.setAntiAlias(true);
                        }
                        Paint strokePaint = this.mStrokePaint;
                        if (fullPath.mStrokeLineJoin != null) {
                            strokePaint.setStrokeJoin(fullPath.mStrokeLineJoin);
                        }
                        if (fullPath.mStrokeLineCap != null) {
                            strokePaint.setStrokeCap(fullPath.mStrokeLineCap);
                        }
                        strokePaint.setStrokeMiter(fullPath.mStrokeMiterlimit);
                        strokePaint.setColor(VectorDrawableCompat.applyAlpha(fullPath.mStrokeColor, fullPath.mStrokeAlpha));
                        ColorFilter colorFilter4 = strokePaint.setColorFilter(filter);
                        float f = minScale * matrixScale2;
                        float f2 = f;
                        strokePaint.setStrokeWidth(fullPath.mStrokeWidth * f);
                        canvas2.drawPath(this.mRenderPath, strokePaint);
                    }
                } else {
                    this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                    boolean clipPath = canvas2.clipPath(this.mRenderPath);
                }
            }
        }

        private static float cross(float f, float f2, float f3, float f4) {
            float v1x = f;
            float v1y = f2;
            float v2x = f3;
            float v2y = f4;
            float f5 = v1x;
            float f6 = v1y;
            float f7 = v2x;
            float f8 = v2y;
            return (v1x * v2y) - (v1y * v2x);
        }

        private float getMatrixScale(Matrix matrix) {
            Matrix groupStackedMatrix = matrix;
            Matrix matrix2 = groupStackedMatrix;
            float[] fArr = new float[4];
            fArr[0] = 0.0f;
            fArr[1] = 1.0f;
            fArr[2] = 1.0f;
            fArr[3] = 0.0f;
            float[] unitVectors = fArr;
            groupStackedMatrix.mapVectors(unitVectors);
            float scaleX = (float) Math.hypot((double) unitVectors[0], (double) unitVectors[1]);
            float scaleY = (float) Math.hypot((double) unitVectors[2], (double) unitVectors[3]);
            float crossProduct = cross(unitVectors[0], unitVectors[1], unitVectors[2], unitVectors[3]);
            float maxScale = Math.max(scaleX, scaleY);
            if (maxScale > 0.0f) {
                return Math.abs(crossProduct) / maxScale;
            }
            return 0.0f;
        }
    }

    private static class VectorDrawableCompatState extends ConstantState {
        boolean mAutoMirrored;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        Bitmap mCachedBitmap;
        int mCachedRootAlpha;
        int[] mCachedThemeAttrs;
        ColorStateList mCachedTint;
        Mode mCachedTintMode;
        int mChangingConfigurations;
        Paint mTempPaint;
        ColorStateList mTint;
        Mode mTintMode;
        VPathRenderer mVPathRenderer;

        public VectorDrawableCompatState() {
            this.mTint = null;
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
            this.mVPathRenderer = new VPathRenderer();
        }

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            VectorDrawableCompatState copy = vectorDrawableCompatState;
            VectorDrawableCompatState vectorDrawableCompatState2 = copy;
            this.mTint = null;
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
            if (copy != null) {
                this.mChangingConfigurations = copy.mChangingConfigurations;
                this.mVPathRenderer = new VPathRenderer(copy.mVPathRenderer);
                if (VPathRenderer.access$000(copy.mVPathRenderer) != null) {
                    Paint access$002 = VPathRenderer.access$002(this.mVPathRenderer, new Paint(VPathRenderer.access$000(copy.mVPathRenderer)));
                }
                if (VPathRenderer.access$100(copy.mVPathRenderer) != null) {
                    Paint access$102 = VPathRenderer.access$102(this.mVPathRenderer, new Paint(VPathRenderer.access$100(copy.mVPathRenderer)));
                }
                this.mTint = copy.mTint;
                this.mTintMode = copy.mTintMode;
                this.mAutoMirrored = copy.mAutoMirrored;
            }
        }

        public void drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            Canvas canvas2 = canvas;
            ColorFilter filter = colorFilter;
            Rect originalBounds = rect;
            Canvas canvas3 = canvas2;
            ColorFilter colorFilter2 = filter;
            Rect rect2 = originalBounds;
            canvas2.drawBitmap(this.mCachedBitmap, null, originalBounds, getPaint(filter));
        }

        public boolean hasTranslucentRoot() {
            return this.mVPathRenderer.getRootAlpha() < 255;
        }

        public Paint getPaint(ColorFilter colorFilter) {
            ColorFilter filter = colorFilter;
            ColorFilter colorFilter2 = filter;
            if (!hasTranslucentRoot() && filter == null) {
                return null;
            }
            if (this.mTempPaint == null) {
                this.mTempPaint = new Paint();
                this.mTempPaint.setFilterBitmap(true);
            }
            this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha());
            ColorFilter colorFilter3 = this.mTempPaint.setColorFilter(filter);
            return this.mTempPaint;
        }

        public void updateCachedBitmap(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            this.mCachedBitmap.eraseColor(0);
            this.mVPathRenderer.draw(new Canvas(this.mCachedBitmap), width, height, null);
        }

        public void createCachedBitmapIfNeeded(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            if (this.mCachedBitmap == null || !canReuseBitmap(width, height)) {
                this.mCachedBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                this.mCacheDirty = true;
            }
        }

        public boolean canReuseBitmap(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            if (width == this.mCachedBitmap.getWidth() && height == this.mCachedBitmap.getHeight()) {
                return true;
            }
            return false;
        }

        public boolean canReuseCache() {
            if (!this.mCacheDirty && this.mCachedTint == this.mTint && this.mCachedTintMode == this.mTintMode && this.mCachedAutoMirrored == this.mAutoMirrored && this.mCachedRootAlpha == this.mVPathRenderer.getRootAlpha()) {
                return true;
            }
            return false;
        }

        public void updateCacheStates() {
            this.mCachedTint = this.mTint;
            this.mCachedTintMode = this.mTintMode;
            this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha();
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public Drawable newDrawable(Resources resources) {
            Resources resources2 = resources;
            return new VectorDrawableCompat(this);
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    private static class VectorDrawableDelegateState extends ConstantState {
        private final ConstantState mDelegateState;

        public VectorDrawableDelegateState(ConstantState constantState) {
            ConstantState state = constantState;
            ConstantState constantState2 = state;
            this.mDelegateState = state;
        }

        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            VectorDrawableCompat drawableCompat = vectorDrawableCompat;
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable();
            return drawableCompat;
        }

        public Drawable newDrawable(Resources resources) {
            Resources res = resources;
            Resources resources2 = res;
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            VectorDrawableCompat drawableCompat = vectorDrawableCompat;
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(res);
            return drawableCompat;
        }

        public Drawable newDrawable(Resources resources, Theme theme) {
            Resources res = resources;
            Theme theme2 = theme;
            Resources resources2 = res;
            Theme theme3 = theme2;
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            VectorDrawableCompat drawableCompat = vectorDrawableCompat;
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(res, theme2);
            return drawableCompat;
        }

        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }
    }

    public /* bridge */ /* synthetic */ void applyTheme(Theme theme) {
        super.applyTheme(theme);
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i, Mode mode) {
        super.setColorFilter(i, mode);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    VectorDrawableCompat() {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    VectorDrawableCompat(@NonNull VectorDrawableCompatState vectorDrawableCompatState) {
        VectorDrawableCompatState state = vectorDrawableCompatState;
        VectorDrawableCompatState vectorDrawableCompatState2 = state;
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = state;
        this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, state.mTintMode);
    }

    public Drawable mutate() {
        if (this.mDelegateDrawable == null) {
            if (!this.mMutated && super.mutate() == this) {
                this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
                this.mMutated = true;
            }
            return this;
        }
        Drawable mutate = this.mDelegateDrawable.mutate();
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Object getTargetByName(String str) {
        String name = str;
        String str2 = name;
        return this.mVectorState.mVPathRenderer.mVGTargetsMap.get(name);
    }

    public ConstantState getConstantState() {
        if (this.mDelegateDrawable != null) {
            return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        this.mVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mVectorState;
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (this.mDelegateDrawable == null) {
            copyBounds(this.mTmpBounds);
            if (this.mTmpBounds.width() > 0 && this.mTmpBounds.height() > 0) {
                ColorFilter colorFilter = this.mColorFilter != null ? this.mColorFilter : this.mTintFilter;
                canvas2.getMatrix(this.mTmpMatrix);
                this.mTmpMatrix.getValues(this.mTmpFloats);
                float canvasScaleX = Math.abs(this.mTmpFloats[0]);
                float canvasScaleY = Math.abs(this.mTmpFloats[4]);
                float canvasSkewX = Math.abs(this.mTmpFloats[1]);
                float canvasSkewY = Math.abs(this.mTmpFloats[3]);
                if (!(canvasSkewX == 0.0f && canvasSkewY == 0.0f)) {
                    canvasScaleX = 1.0f;
                    canvasScaleY = 1.0f;
                }
                int scaledHeight = (int) (((float) this.mTmpBounds.height()) * canvasScaleY);
                int scaledWidth = Math.min(2048, (int) (((float) this.mTmpBounds.width()) * canvasScaleX));
                int scaledHeight2 = Math.min(2048, scaledHeight);
                if (scaledWidth > 0 && scaledHeight2 > 0) {
                    int saveCount = canvas2.save();
                    canvas2.translate((float) this.mTmpBounds.left, (float) this.mTmpBounds.top);
                    boolean needMirroring = needMirroring();
                    boolean z = needMirroring;
                    if (needMirroring) {
                        canvas2.translate((float) this.mTmpBounds.width(), 0.0f);
                        canvas2.scale(-1.0f, 1.0f);
                    }
                    this.mTmpBounds.offsetTo(0, 0);
                    this.mVectorState.createCachedBitmapIfNeeded(scaledWidth, scaledHeight2);
                    if (!this.mAllowCaching) {
                        this.mVectorState.updateCachedBitmap(scaledWidth, scaledHeight2);
                    } else if (!this.mVectorState.canReuseCache()) {
                        this.mVectorState.updateCachedBitmap(scaledWidth, scaledHeight2);
                        this.mVectorState.updateCacheStates();
                    }
                    this.mVectorState.drawCachedBitmapWithRootAlpha(canvas2, colorFilter, this.mTmpBounds);
                    canvas2.restoreToCount(saveCount);
                    return;
                }
                return;
            }
            return;
        }
        this.mDelegateDrawable.draw(canvas2);
    }

    public int getAlpha() {
        if (this.mDelegateDrawable == null) {
            return this.mVectorState.mVPathRenderer.getRootAlpha();
        }
        return DrawableCompat.getAlpha(this.mDelegateDrawable);
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        if (this.mDelegateDrawable == null) {
            if (this.mVectorState.mVPathRenderer.getRootAlpha() != alpha) {
                this.mVectorState.mVPathRenderer.setRootAlpha(alpha);
                invalidateSelf();
            }
            return;
        }
        this.mDelegateDrawable.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ColorFilter colorFilter2 = colorFilter;
        ColorFilter colorFilter3 = colorFilter2;
        if (this.mDelegateDrawable == null) {
            this.mColorFilter = colorFilter2;
            invalidateSelf();
            return;
        }
        this.mDelegateDrawable.setColorFilter(colorFilter2);
    }

    /* access modifiers changed from: 0000 */
    public PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, Mode mode) {
        ColorStateList tint = colorStateList;
        Mode tintMode = mode;
        PorterDuffColorFilter porterDuffColorFilter2 = porterDuffColorFilter;
        ColorStateList colorStateList2 = tint;
        Mode mode2 = tintMode;
        if (tint == null || tintMode == null) {
            return null;
        }
        int colorForState = tint.getColorForState(getState(), 0);
        int i = colorForState;
        return new PorterDuffColorFilter(colorForState, tintMode);
    }

    @SuppressLint({"NewApi"})
    public void setTint(int i) {
        int tint = i;
        int i2 = tint;
        if (this.mDelegateDrawable == null) {
            setTintList(ColorStateList.valueOf(tint));
        } else {
            DrawableCompat.setTint(this.mDelegateDrawable, tint);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mDelegateDrawable == null) {
            VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
            VectorDrawableCompatState state = vectorDrawableCompatState;
            if (vectorDrawableCompatState.mTint != tint) {
                state.mTint = tint;
                this.mTintFilter = updateTintFilter(this.mTintFilter, tint, state.mTintMode);
                invalidateSelf();
            }
            return;
        }
        DrawableCompat.setTintList(this.mDelegateDrawable, tint);
    }

    public void setTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mDelegateDrawable == null) {
            VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
            VectorDrawableCompatState state = vectorDrawableCompatState;
            if (vectorDrawableCompatState.mTintMode != tintMode) {
                state.mTintMode = tintMode;
                this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, tintMode);
                invalidateSelf();
            }
            return;
        }
        DrawableCompat.setTintMode(this.mDelegateDrawable, tintMode);
    }

    public boolean isStateful() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.isStateful();
        }
        return super.isStateful() || !(this.mVectorState == null || this.mVectorState.mTint == null || !this.mVectorState.mTint.isStateful());
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        int[] stateSet = iArr;
        int[] iArr2 = stateSet;
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState(stateSet);
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VectorDrawableCompatState state = vectorDrawableCompatState;
        if (vectorDrawableCompatState.mTint == null || state.mTintMode == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, state.mTintMode);
        invalidateSelf();
        return true;
    }

    public int getOpacity() {
        if (this.mDelegateDrawable == null) {
            return -3;
        }
        return this.mDelegateDrawable.getOpacity();
    }

    public int getIntrinsicWidth() {
        if (this.mDelegateDrawable == null) {
            return (int) this.mVectorState.mVPathRenderer.mBaseWidth;
        }
        return this.mDelegateDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        if (this.mDelegateDrawable == null) {
            return (int) this.mVectorState.mVPathRenderer.mBaseHeight;
        }
        return this.mDelegateDrawable.getIntrinsicHeight();
    }

    public boolean canApplyTheme() {
        if (this.mDelegateDrawable != null) {
            boolean canApplyTheme = DrawableCompat.canApplyTheme(this.mDelegateDrawable);
        }
        return false;
    }

    public boolean isAutoMirrored() {
        if (this.mDelegateDrawable == null) {
            return this.mVectorState.mAutoMirrored;
        }
        return DrawableCompat.isAutoMirrored(this.mDelegateDrawable);
    }

    public void setAutoMirrored(boolean z) {
        boolean mirrored = z;
        if (this.mDelegateDrawable == null) {
            this.mVectorState.mAutoMirrored = mirrored;
            return;
        }
        DrawableCompat.setAutoMirrored(this.mDelegateDrawable, mirrored);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public float getPixelSize() {
        if ((this.mVectorState == null && this.mVectorState.mVPathRenderer == null) || this.mVectorState.mVPathRenderer.mBaseWidth == 0.0f || this.mVectorState.mVPathRenderer.mBaseHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportWidth == 0.0f) {
            return 1.0f;
        }
        float intrinsicWidth = this.mVectorState.mVPathRenderer.mBaseWidth;
        float intrinsicHeight = this.mVectorState.mVPathRenderer.mBaseHeight;
        return Math.min(this.mVectorState.mVPathRenderer.mViewportWidth / intrinsicWidth, this.mVectorState.mVPathRenderer.mViewportHeight / intrinsicHeight);
    }

    @Nullable
    @SuppressLint({"NewApi"})
    public static VectorDrawableCompat create(@NonNull Resources resources, @DrawableRes int i, @Nullable Theme theme) {
        int next;
        int type;
        Resources res = resources;
        int resId = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = resId;
        Theme theme3 = theme2;
        if (VERSION.SDK_INT < 24) {
            try {
                XmlResourceParser xml = res.getXml(resId);
                XmlResourceParser xmlResourceParser = xml;
                AttributeSet attrs = Xml.asAttributeSet(xml);
                do {
                    next = xmlResourceParser.next();
                    type = next;
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (type == 2) {
                    return createFromXmlInner(res, xmlResourceParser, attrs, theme2);
                }
                throw new XmlPullParserException("No start tag found");
            } catch (XmlPullParserException e) {
                XmlPullParserException xmlPullParserException = e;
                int e2 = Log.e(LOGTAG, "parser error", e);
                return null;
            } catch (IOException e3) {
                IOException iOException = e3;
                int e4 = Log.e(LOGTAG, "parser error", e3);
                return null;
            }
        } else {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            VectorDrawableCompat drawable = vectorDrawableCompat;
            vectorDrawableCompat.mDelegateDrawable = ResourcesCompat.getDrawable(res, resId, theme2);
            drawable.mCachedConstantStateDelegate = new VectorDrawableDelegateState(drawable.mDelegateDrawable.getConstantState());
            return drawable;
        }
    }

    @SuppressLint({"NewApi"})
    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws XmlPullParserException, IOException {
        Resources r = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Resources resources2 = r;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        VectorDrawableCompat drawable = vectorDrawableCompat;
        vectorDrawableCompat.inflate(r, parser, attrs, theme2);
        return drawable;
    }

    static int applyAlpha(int i, float f) {
        int color = i;
        float alpha = f;
        int i2 = color;
        float f2 = alpha;
        int i3 = color & ViewCompat.MEASURED_SIZE_MASK;
        int color2 = i3;
        int alpha2 = i3 | (((int) (((float) Color.alpha(color)) * alpha)) << 24);
        int color3 = alpha2;
        return alpha2;
    }

    @SuppressLint({"NewApi"})
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Resources res = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Resources resources2 = res;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        if (this.mDelegateDrawable == null) {
            inflate(res, parser, attrs, null);
        } else {
            this.mDelegateDrawable.inflate(res, parser, attrs);
        }
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws XmlPullParserException, IOException {
        Resources res = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Resources resources2 = res;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        if (this.mDelegateDrawable == null) {
            VectorDrawableCompatState state = this.mVectorState;
            state.mVPathRenderer = new VPathRenderer();
            TypedArray a = obtainAttributes(res, theme2, attrs, AndroidResources.styleable_VectorDrawableTypeArray);
            updateStateFromTypedArray(a, parser);
            a.recycle();
            state.mChangingConfigurations = getChangingConfigurations();
            state.mCacheDirty = true;
            inflateInternal(res, parser, attrs, theme2);
            this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, state.mTintMode);
            return;
        }
        DrawableCompat.inflate(this.mDelegateDrawable, res, parser, attrs, theme2);
    }

    private static Mode parseTintModeCompat(int i, Mode mode) {
        int value = i;
        Mode defaultMode = mode;
        int i2 = value;
        Mode mode2 = defaultMode;
        switch (value) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                if (VERSION.SDK_INT < 11) {
                    return defaultMode;
                }
                return Mode.ADD;
            default:
                return defaultMode;
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) throws XmlPullParserException {
        TypedArray a = typedArray;
        XmlPullParser parser = xmlPullParser;
        TypedArray typedArray2 = a;
        XmlPullParser xmlPullParser2 = parser;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VectorDrawableCompatState state = vectorDrawableCompatState;
        VPathRenderer pathRenderer = vectorDrawableCompatState.mVPathRenderer;
        int namedInt = TypedArrayUtils.getNamedInt(a, parser, "tintMode", 6, -1);
        int i = namedInt;
        state.mTintMode = parseTintModeCompat(namedInt, Mode.SRC_IN);
        ColorStateList colorStateList = a.getColorStateList(1);
        ColorStateList tint = colorStateList;
        if (colorStateList != null) {
            state.mTint = tint;
        }
        state.mAutoMirrored = TypedArrayUtils.getNamedBoolean(a, parser, "autoMirrored", 5, state.mAutoMirrored);
        pathRenderer.mViewportWidth = TypedArrayUtils.getNamedFloat(a, parser, "viewportWidth", 7, pathRenderer.mViewportWidth);
        pathRenderer.mViewportHeight = TypedArrayUtils.getNamedFloat(a, parser, "viewportHeight", 8, pathRenderer.mViewportHeight);
        if (pathRenderer.mViewportWidth <= 0.0f) {
            throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (pathRenderer.mViewportHeight <= 0.0f) {
            throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        } else {
            pathRenderer.mBaseWidth = a.getDimension(3, pathRenderer.mBaseWidth);
            pathRenderer.mBaseHeight = a.getDimension(2, pathRenderer.mBaseHeight);
            if (pathRenderer.mBaseWidth <= 0.0f) {
                throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (pathRenderer.mBaseHeight <= 0.0f) {
                throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires height > 0");
            } else {
                float namedFloat = TypedArrayUtils.getNamedFloat(a, parser, "alpha", 4, pathRenderer.getAlpha());
                float f = namedFloat;
                pathRenderer.setAlpha(namedFloat);
                String string = a.getString(0);
                String name = string;
                if (string != null) {
                    pathRenderer.mRootName = name;
                    Object put = pathRenderer.mVGTargetsMap.put(name, pathRenderer);
                }
            }
        }
    }

    private void inflateInternal(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws XmlPullParserException, IOException {
        Resources res = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Resources resources2 = res;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VectorDrawableCompatState state = vectorDrawableCompatState;
        VPathRenderer pathRenderer = vectorDrawableCompatState.mVPathRenderer;
        boolean noPathTag = true;
        Stack stack = new Stack();
        Stack stack2 = stack;
        Object push = stack.push(pathRenderer.mRootGroup);
        int eventType = parser.getEventType();
        int innerDepth = parser.getDepth() + 1;
        while (eventType != 1 && (parser.getDepth() >= innerDepth || eventType != 3)) {
            if (eventType == 2) {
                String tagName = parser.getName();
                VGroup currentGroup = (VGroup) stack2.peek();
                if (SHAPE_PATH.equals(tagName)) {
                    VFullPath vFullPath = new VFullPath();
                    VFullPath path = vFullPath;
                    vFullPath.inflate(res, attrs, theme2, parser);
                    boolean add = currentGroup.mChildren.add(path);
                    if (path.getPathName() != null) {
                        Object put = pathRenderer.mVGTargetsMap.put(path.getPathName(), path);
                    }
                    noPathTag = false;
                    state.mChangingConfigurations |= path.mChangingConfigurations;
                } else if (SHAPE_CLIP_PATH.equals(tagName)) {
                    VClipPath vClipPath = new VClipPath();
                    VClipPath path2 = vClipPath;
                    vClipPath.inflate(res, attrs, theme2, parser);
                    boolean add2 = currentGroup.mChildren.add(path2);
                    if (path2.getPathName() != null) {
                        Object put2 = pathRenderer.mVGTargetsMap.put(path2.getPathName(), path2);
                    }
                    state.mChangingConfigurations |= path2.mChangingConfigurations;
                } else if (SHAPE_GROUP.equals(tagName)) {
                    VGroup vGroup = new VGroup();
                    VGroup newChildGroup = vGroup;
                    vGroup.inflate(res, attrs, theme2, parser);
                    boolean add3 = currentGroup.mChildren.add(newChildGroup);
                    Object push2 = stack2.push(newChildGroup);
                    if (newChildGroup.getGroupName() != null) {
                        Object put3 = pathRenderer.mVGTargetsMap.put(newChildGroup.getGroupName(), newChildGroup);
                    }
                    state.mChangingConfigurations |= newChildGroup.mChangingConfigurations;
                }
            } else if (eventType == 3) {
                if (SHAPE_GROUP.equals(parser.getName())) {
                    Object pop = stack2.pop();
                }
            }
            eventType = parser.next();
        }
        if (noPathTag) {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer tag = stringBuffer;
            if (stringBuffer.length() > 0) {
                StringBuffer append = tag.append(" or ");
            }
            StringBuffer append2 = tag.append(SHAPE_PATH);
            throw new XmlPullParserException("no " + tag + " defined");
        }
    }

    private void printGroupTree(VGroup vGroup, int i) {
        VGroup currentGroup = vGroup;
        int level = i;
        VGroup vGroup2 = currentGroup;
        int i2 = level;
        String indent = "";
        for (int i3 = 0; i3 < level; i3++) {
            indent = indent + "    ";
        }
        int v = Log.v(LOGTAG, indent + "current group is :" + currentGroup.getGroupName() + " rotation is " + currentGroup.mRotate);
        int v2 = Log.v(LOGTAG, indent + "matrix is :" + currentGroup.getLocalMatrix().toString());
        for (int i4 = 0; i4 < currentGroup.mChildren.size(); i4++) {
            Object obj = currentGroup.mChildren.get(i4);
            Object child = obj;
            if (!(obj instanceof VGroup)) {
                ((VPath) child).printVPath(level + 1);
            } else {
                printGroupTree((VGroup) child, level + 1);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAllowCaching(boolean z) {
        this.mAllowCaching = z;
    }

    @SuppressLint({"NewApi"})
    private boolean needMirroring() {
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds(bounds);
        }
    }

    public int getChangingConfigurations() {
        if (this.mDelegateDrawable == null) {
            return super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
        }
        return this.mDelegateDrawable.getChangingConfigurations();
    }

    public void invalidateSelf() {
        if (this.mDelegateDrawable == null) {
            super.invalidateSelf();
        } else {
            this.mDelegateDrawable.invalidateSelf();
        }
    }

    public void scheduleSelf(Runnable runnable, long j) {
        Runnable what = runnable;
        long when = j;
        Runnable runnable2 = what;
        long j2 = when;
        if (this.mDelegateDrawable == null) {
            super.scheduleSelf(what, when);
        } else {
            this.mDelegateDrawable.scheduleSelf(what, when);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = z;
        boolean restart = z2;
        if (this.mDelegateDrawable == null) {
            return super.setVisible(visible, restart);
        }
        return this.mDelegateDrawable.setVisible(visible, restart);
    }

    public void unscheduleSelf(Runnable runnable) {
        Runnable what = runnable;
        Runnable runnable2 = what;
        if (this.mDelegateDrawable == null) {
            super.unscheduleSelf(what);
        } else {
            this.mDelegateDrawable.unscheduleSelf(what);
        }
    }
}
