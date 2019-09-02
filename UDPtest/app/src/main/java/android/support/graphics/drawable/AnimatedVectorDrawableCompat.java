package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.content.res.ResourcesCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@SuppressLint({"NewApi"})
public class AnimatedVectorDrawableCompat extends VectorDrawableCommon implements Animatable {
    private static final String ANIMATED_VECTOR = "animated-vector";
    private static final boolean DBG_ANIMATION_VECTOR_DRAWABLE = false;
    private static final String LOGTAG = "AnimatedVDCompat";
    private static final String TARGET = "target";
    private AnimatedVectorDrawableCompatState mAnimatedVectorState;
    private ArgbEvaluator mArgbEvaluator;
    AnimatedVectorDrawableDelegateState mCachedConstantStateDelegate;
    final Callback mCallback;
    private Context mContext;

    private static class AnimatedVectorDrawableCompatState extends ConstantState {
        ArrayList<Animator> mAnimators;
        int mChangingConfigurations;
        ArrayMap<Animator, String> mTargetNameMap;
        VectorDrawableCompat mVectorDrawable;

        public AnimatedVectorDrawableCompatState(Context context, AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState, Callback callback, Resources resources) {
            AnimatedVectorDrawableCompatState copy = animatedVectorDrawableCompatState;
            Callback owner = callback;
            Resources res = resources;
            Context context2 = context;
            AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState2 = copy;
            Callback callback2 = owner;
            Resources resources2 = res;
            if (copy != null) {
                this.mChangingConfigurations = copy.mChangingConfigurations;
                if (copy.mVectorDrawable != null) {
                    ConstantState cs = copy.mVectorDrawable.getConstantState();
                    if (res == null) {
                        this.mVectorDrawable = (VectorDrawableCompat) cs.newDrawable();
                    } else {
                        this.mVectorDrawable = (VectorDrawableCompat) cs.newDrawable(res);
                    }
                    this.mVectorDrawable = (VectorDrawableCompat) this.mVectorDrawable.mutate();
                    this.mVectorDrawable.setCallback(owner);
                    this.mVectorDrawable.setBounds(copy.mVectorDrawable.getBounds());
                    this.mVectorDrawable.setAllowCaching(false);
                }
                if (copy.mAnimators != null) {
                    int size = copy.mAnimators.size();
                    int numAnimators = size;
                    ArrayList arrayList = new ArrayList(size);
                    this.mAnimators = arrayList;
                    ArrayMap arrayMap = new ArrayMap(size);
                    this.mTargetNameMap = arrayMap;
                    for (int i = 0; i < numAnimators; i++) {
                        Animator animator = (Animator) copy.mAnimators.get(i);
                        Animator anim = animator;
                        Animator animClone = animator.clone();
                        String targetName = (String) copy.mTargetNameMap.get(anim);
                        animClone.setTarget(this.mVectorDrawable.getTargetByName(targetName));
                        boolean add = this.mAnimators.add(animClone);
                        Object put = this.mTargetNameMap.put(animClone, targetName);
                    }
                }
            }
        }

        public Drawable newDrawable() {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        public Drawable newDrawable(Resources resources) {
            Resources resources2 = resources;
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    private static class AnimatedVectorDrawableDelegateState extends ConstantState {
        private final ConstantState mDelegateState;

        public AnimatedVectorDrawableDelegateState(ConstantState constantState) {
            ConstantState state = constantState;
            ConstantState constantState2 = state;
            this.mDelegateState = state;
        }

        public Drawable newDrawable() {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat();
            AnimatedVectorDrawableCompat drawableCompat = animatedVectorDrawableCompat;
            animatedVectorDrawableCompat.mDelegateDrawable = this.mDelegateState.newDrawable();
            drawableCompat.mDelegateDrawable.setCallback(drawableCompat.mCallback);
            return drawableCompat;
        }

        public Drawable newDrawable(Resources resources) {
            Resources res = resources;
            Resources resources2 = res;
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat();
            AnimatedVectorDrawableCompat drawableCompat = animatedVectorDrawableCompat;
            animatedVectorDrawableCompat.mDelegateDrawable = this.mDelegateState.newDrawable(res);
            drawableCompat.mDelegateDrawable.setCallback(drawableCompat.mCallback);
            return drawableCompat;
        }

        public Drawable newDrawable(Resources resources, Theme theme) {
            Resources res = resources;
            Theme theme2 = theme;
            Resources resources2 = res;
            Theme theme3 = theme2;
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat();
            AnimatedVectorDrawableCompat drawableCompat = animatedVectorDrawableCompat;
            animatedVectorDrawableCompat.mDelegateDrawable = this.mDelegateState.newDrawable(res, theme2);
            drawableCompat.mDelegateDrawable.setCallback(drawableCompat.mCallback);
            return drawableCompat;
        }

        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }
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
        Rect x0 = rect;
        Rect rect2 = x0;
        return super.getPadding(x0);
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
        int x0 = i;
        int i2 = x0;
        super.setChangingConfigurations(x0);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i, Mode mode) {
        int x0 = i;
        Mode x1 = mode;
        int i2 = x0;
        Mode mode2 = x1;
        super.setColorFilter(x0, x1);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
        float x0 = f;
        float x1 = f2;
        float f3 = x0;
        float f4 = x1;
        super.setHotspot(x0, x1);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        int x0 = i;
        int x1 = i2;
        int x2 = i3;
        int x3 = i4;
        int i5 = x0;
        int i6 = x1;
        int i7 = x2;
        int i8 = x3;
        super.setHotspotBounds(x0, x1, x2, x3);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        int[] x0 = iArr;
        int[] iArr2 = x0;
        return super.setState(x0);
    }

    private AnimatedVectorDrawableCompat(@Nullable Context context, @Nullable AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState, @Nullable Resources resources) {
        Context context2 = context;
        AnimatedVectorDrawableCompatState state = animatedVectorDrawableCompatState;
        Resources res = resources;
        Context context3 = context2;
        AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState2 = state;
        Resources resources2 = res;
        this.mArgbEvaluator = null;
        this.mCallback = new Callback(this) {
            final /* synthetic */ AnimatedVectorDrawableCompat this$0;

            {
                AnimatedVectorDrawableCompat this$02 = r5;
                AnimatedVectorDrawableCompat animatedVectorDrawableCompat = this$02;
                this.this$0 = this$02;
            }

            public void invalidateDrawable(Drawable drawable) {
                Drawable drawable2 = drawable;
                this.this$0.invalidateSelf();
            }

            public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                Runnable what = runnable;
                long when = j;
                Drawable drawable2 = drawable;
                Runnable runnable2 = what;
                long j2 = when;
                this.this$0.scheduleSelf(what, when);
            }

            public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                Runnable what = runnable;
                Drawable drawable2 = drawable;
                Runnable runnable2 = what;
                this.this$0.unscheduleSelf(what);
            }
        };
        this.mContext = context2;
        if (state == null) {
            this.mAnimatedVectorState = new AnimatedVectorDrawableCompatState(context2, state, this.mCallback, res);
        } else {
            this.mAnimatedVectorState = state;
        }
    }

    AnimatedVectorDrawableCompat() {
        this(null, null, null);
    }

    private AnimatedVectorDrawableCompat(@Nullable Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null, null);
    }

    public Drawable mutate() {
        if (this.mDelegateDrawable != null) {
            Drawable mutate = this.mDelegateDrawable.mutate();
        }
        return this;
    }

    @Nullable
    public static AnimatedVectorDrawableCompat create(@NonNull Context context, @DrawableRes int i) {
        int next;
        int type;
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        if (VERSION.SDK_INT < 24) {
            Resources resources = context2.getResources();
            Resources resources2 = resources;
            try {
                XmlResourceParser xml = resources.getXml(resId);
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
                    return createFromXmlInner(context2, context2.getResources(), xmlResourceParser, attrs, context2.getTheme());
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
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat(context2);
            AnimatedVectorDrawableCompat drawable = animatedVectorDrawableCompat;
            animatedVectorDrawableCompat.mDelegateDrawable = ResourcesCompat.getDrawable(context2.getResources(), resId, context2.getTheme());
            drawable.mDelegateDrawable.setCallback(drawable.mCallback);
            drawable.mCachedConstantStateDelegate = new AnimatedVectorDrawableDelegateState(drawable.mDelegateDrawable.getConstantState());
            return drawable;
        }
    }

    public static AnimatedVectorDrawableCompat createFromXmlInner(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws XmlPullParserException, IOException {
        Context context2 = context;
        Resources r = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Context context3 = context2;
        Resources resources2 = r;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = new AnimatedVectorDrawableCompat(context2);
        AnimatedVectorDrawableCompat drawable = animatedVectorDrawableCompat;
        animatedVectorDrawableCompat.inflate(r, parser, attrs, theme2);
        return drawable;
    }

    public ConstantState getConstantState() {
        if (this.mDelegateDrawable == null) {
            return null;
        }
        return new AnimatedVectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
    }

    public int getChangingConfigurations() {
        if (this.mDelegateDrawable == null) {
            return super.getChangingConfigurations() | this.mAnimatedVectorState.mChangingConfigurations;
        }
        return this.mDelegateDrawable.getChangingConfigurations();
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.draw(canvas2);
            if (isStarted()) {
                invalidateSelf();
            }
            return;
        }
        this.mDelegateDrawable.draw(canvas2);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setBounds(bounds);
        } else {
            this.mDelegateDrawable.setBounds(bounds);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        int[] state = iArr;
        int[] iArr2 = state;
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.setState(state);
        }
        return this.mDelegateDrawable.setState(state);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        int level = i;
        int i2 = level;
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.setLevel(level);
        }
        return this.mDelegateDrawable.setLevel(level);
    }

    public int getAlpha() {
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.getAlpha();
        }
        return DrawableCompat.getAlpha(this.mDelegateDrawable);
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setAlpha(alpha);
        } else {
            this.mDelegateDrawable.setAlpha(alpha);
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ColorFilter colorFilter2 = colorFilter;
        ColorFilter colorFilter3 = colorFilter2;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setColorFilter(colorFilter2);
        } else {
            this.mDelegateDrawable.setColorFilter(colorFilter2);
        }
    }

    public void setTint(int i) {
        int tint = i;
        int i2 = tint;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setTint(tint);
        } else {
            DrawableCompat.setTint(this.mDelegateDrawable, tint);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setTintList(tint);
        } else {
            DrawableCompat.setTintList(this.mDelegateDrawable, tint);
        }
    }

    public void setTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setTintMode(tintMode);
        } else {
            DrawableCompat.setTintMode(this.mDelegateDrawable, tintMode);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = z;
        boolean restart = z2;
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setVisible(visible, restart);
        }
        boolean visible2 = this.mAnimatedVectorState.mVectorDrawable.setVisible(visible, restart);
        return super.setVisible(visible, restart);
    }

    public boolean isStateful() {
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.isStateful();
        }
        return this.mDelegateDrawable.isStateful();
    }

    public int getOpacity() {
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.getOpacity();
        }
        return this.mDelegateDrawable.getOpacity();
    }

    public int getIntrinsicWidth() {
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicWidth();
        }
        return this.mDelegateDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicHeight();
        }
        return this.mDelegateDrawable.getIntrinsicHeight();
    }

    public boolean isAutoMirrored() {
        if (this.mDelegateDrawable == null) {
            return this.mAnimatedVectorState.mVectorDrawable.isAutoMirrored();
        }
        return DrawableCompat.isAutoMirrored(this.mDelegateDrawable);
    }

    public void setAutoMirrored(boolean z) {
        boolean mirrored = z;
        if (this.mDelegateDrawable == null) {
            this.mAnimatedVectorState.mVectorDrawable.setAutoMirrored(mirrored);
        } else {
            this.mDelegateDrawable.setAutoMirrored(mirrored);
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
            int eventType = parser.getEventType();
            int innerDepth = parser.getDepth() + 1;
            while (eventType != 1 && (parser.getDepth() >= innerDepth || eventType != 3)) {
                if (eventType == 2) {
                    String tagName = parser.getName();
                    if (ANIMATED_VECTOR.equals(tagName)) {
                        TypedArray obtainAttributes = VectorDrawableCommon.obtainAttributes(res, theme2, attrs, AndroidResources.styleable_AnimatedVectorDrawable);
                        TypedArray a = obtainAttributes;
                        int resourceId = obtainAttributes.getResourceId(0, 0);
                        int drawableRes = resourceId;
                        if (resourceId != 0) {
                            VectorDrawableCompat create = VectorDrawableCompat.create(res, drawableRes, theme2);
                            VectorDrawableCompat vectorDrawable = create;
                            create.setAllowCaching(false);
                            vectorDrawable.setCallback(this.mCallback);
                            if (this.mAnimatedVectorState.mVectorDrawable != null) {
                                this.mAnimatedVectorState.mVectorDrawable.setCallback(null);
                            }
                            this.mAnimatedVectorState.mVectorDrawable = vectorDrawable;
                        }
                        a.recycle();
                    } else if (!TARGET.equals(tagName)) {
                        continue;
                    } else {
                        TypedArray obtainAttributes2 = res.obtainAttributes(attrs, AndroidResources.styleable_AnimatedVectorDrawableTarget);
                        TypedArray a2 = obtainAttributes2;
                        String target = obtainAttributes2.getString(0);
                        int resourceId2 = a2.getResourceId(1, 0);
                        int id = resourceId2;
                        if (resourceId2 != 0) {
                            if (this.mContext == null) {
                                throw new IllegalStateException("Context can't be null when inflating animators");
                            }
                            setupAnimatorsForTarget(target, AnimatorInflater.loadAnimator(this.mContext, id));
                        }
                        a2.recycle();
                    }
                }
                eventType = parser.next();
            }
            return;
        }
        DrawableCompat.inflate(this.mDelegateDrawable, res, parser, attrs, theme2);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Resources res = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Resources resources2 = res;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        inflate(res, parser, attrs, null);
    }

    public void applyTheme(Theme theme) {
        Theme t = theme;
        Theme theme2 = t;
        if (this.mDelegateDrawable != null) {
            DrawableCompat.applyTheme(this.mDelegateDrawable, t);
        }
    }

    public boolean canApplyTheme() {
        if (this.mDelegateDrawable == null) {
            return false;
        }
        return DrawableCompat.canApplyTheme(this.mDelegateDrawable);
    }

    private void setupColorAnimator(Animator animator) {
        Animator animator2 = animator;
        Animator animator3 = animator2;
        if (animator2 instanceof AnimatorSet) {
            ArrayList childAnimations = ((AnimatorSet) animator2).getChildAnimations();
            ArrayList arrayList = childAnimations;
            if (childAnimations != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    setupColorAnimator((Animator) arrayList.get(i));
                }
            }
        }
        if (animator2 instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animator2;
            ObjectAnimator objectAnim = objectAnimator;
            String propertyName = objectAnimator.getPropertyName();
            if ("fillColor".equals(propertyName) || "strokeColor".equals(propertyName)) {
                if (this.mArgbEvaluator == null) {
                    this.mArgbEvaluator = new ArgbEvaluator();
                }
                objectAnim.setEvaluator(this.mArgbEvaluator);
            }
        }
    }

    private void setupAnimatorsForTarget(String str, Animator animator) {
        String name = str;
        Animator animator2 = animator;
        String str2 = name;
        Animator animator3 = animator2;
        animator2.setTarget(this.mAnimatedVectorState.mVectorDrawable.getTargetByName(name));
        if (VERSION.SDK_INT < 21) {
            setupColorAnimator(animator2);
        }
        if (this.mAnimatedVectorState.mAnimators == null) {
            this.mAnimatedVectorState.mAnimators = new ArrayList<>();
            this.mAnimatedVectorState.mTargetNameMap = new ArrayMap<>();
        }
        boolean add = this.mAnimatedVectorState.mAnimators.add(animator2);
        Object put = this.mAnimatedVectorState.mTargetNameMap.put(animator2, name);
    }

    public boolean isRunning() {
        if (this.mDelegateDrawable != null) {
            return ((AnimatedVectorDrawable) this.mDelegateDrawable).isRunning();
        }
        ArrayList<Animator> arrayList = this.mAnimatedVectorState.mAnimators;
        ArrayList<Animator> animators = arrayList;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) animators.get(i);
            Animator animator2 = animator;
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    private boolean isStarted() {
        ArrayList<Animator> arrayList = this.mAnimatedVectorState.mAnimators;
        ArrayList<Animator> animators = arrayList;
        if (arrayList == null) {
            return false;
        }
        int size = animators.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) animators.get(i);
            Animator animator2 = animator;
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        if (this.mDelegateDrawable != null) {
            ((AnimatedVectorDrawable) this.mDelegateDrawable).start();
        } else if (!isStarted()) {
            ArrayList<Animator> arrayList = this.mAnimatedVectorState.mAnimators;
            ArrayList<Animator> animators = arrayList;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Animator animator = (Animator) animators.get(i);
                Animator animator2 = animator;
                animator.start();
            }
            invalidateSelf();
        }
    }

    public void stop() {
        if (this.mDelegateDrawable == null) {
            ArrayList<Animator> arrayList = this.mAnimatedVectorState.mAnimators;
            ArrayList<Animator> animators = arrayList;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Animator animator = (Animator) animators.get(i);
                Animator animator2 = animator;
                animator.end();
            }
            return;
        }
        ((AnimatedVectorDrawable) this.mDelegateDrawable).stop();
    }
}
