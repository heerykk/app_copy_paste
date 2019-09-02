package android.support.transition;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(14)
@RequiresApi(14)
class ChangeBoundsPort extends TransitionPort {
    private static final String LOG_TAG = "ChangeBounds";
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private static final String[] sTransitionProperties;
    boolean mReparent = false;
    boolean mResizeClip = false;
    int[] tempLocation = new int[2];

    ChangeBoundsPort() {
    }

    static {
        String[] strArr = new String[4];
        strArr[0] = PROPNAME_BOUNDS;
        strArr[1] = PROPNAME_PARENT;
        strArr[2] = PROPNAME_WINDOW_X;
        strArr[3] = PROPNAME_WINDOW_Y;
        sTransitionProperties = strArr;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean z) {
        this.mResizeClip = z;
    }

    public void setReparent(boolean z) {
        this.mReparent = z;
    }

    private void captureValues(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        View view = values.view;
        Object put = values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        Object put2 = values.values.put(PROPNAME_PARENT, values.view.getParent());
        values.view.getLocationInWindow(this.tempLocation);
        Object put3 = values.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.tempLocation[0]));
        Object put4 = values.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.tempLocation[1]));
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        captureValues(transitionValues2);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        captureValues(transitionValues2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00e8, code lost:
        r23 = r19.getId();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.animation.Animator createAnimator(android.view.ViewGroup r156, android.support.transition.TransitionValues r157, android.support.transition.TransitionValues r158) {
        /*
            r155 = this;
            r5 = r155
            r6 = r156
            r7 = r157
            r8 = r158
            r9 = r5
            r10 = r6
            r11 = r7
            r12 = r8
            r13 = 0
            if (r7 != r13) goto L_0x0013
        L_0x000f:
            r14 = 0
            r13 = 0
            r15 = r13
            return r15
        L_0x0013:
            r13 = 0
            if (r8 == r13) goto L_0x000f
            java.util.Map<java.lang.String, java.lang.Object> r14 = r7.values
            r16 = r14
            java.util.Map<java.lang.String, java.lang.Object> r14 = r8.values
            r17 = r14
            java.lang.String r18 = "android:changeBounds:parent"
            r0 = r16
            r1 = r18
            java.lang.Object r14 = r0.get(r1)
            android.view.ViewGroup r14 = (android.view.ViewGroup) r14
            r19 = r14
            java.lang.String r18 = "android:changeBounds:parent"
            java.lang.Object r14 = r17.get(r18)
            android.view.ViewGroup r14 = (android.view.ViewGroup) r14
            r20 = r14
            r13 = 0
            r0 = r19
            if (r0 != r13) goto L_0x0042
        L_0x003d:
            r14 = 0
            r13 = 0
            r21 = r13
            return r21
        L_0x0042:
            r13 = 0
            r0 = r20
            if (r0 == r13) goto L_0x003d
            android.view.View r14 = r8.view
            r22 = r14
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x00e8
        L_0x0051:
            r23 = 1
        L_0x0053:
            r25 = r23
            boolean r0 = r5.mReparent
            r26 = r0
            r23 = r26
            r27 = 0
            r0 = r23
            r1 = r27
            if (r0 != r1) goto L_0x00fa
        L_0x0063:
            java.util.Map<java.lang.String, java.lang.Object> r14 = r7.values
            java.lang.String r18 = "android:changeBounds:bounds"
            r0 = r18
            java.lang.Object r14 = r14.get(r0)
            android.graphics.Rect r14 = (android.graphics.Rect) r14
            r28 = r14
            java.util.Map<java.lang.String, java.lang.Object> r14 = r8.values
            java.lang.String r18 = "android:changeBounds:bounds"
            r0 = r18
            java.lang.Object r14 = r14.get(r0)
            android.graphics.Rect r14 = (android.graphics.Rect) r14
            r29 = r14
            r0 = r28
            int r0 = r0.left
            r23 = r0
            r30 = r23
            r0 = r29
            int r0 = r0.left
            r23 = r0
            r31 = r23
            r0 = r28
            int r0 = r0.top
            r23 = r0
            r32 = r23
            r0 = r29
            int r0 = r0.top
            r23 = r0
            r33 = r23
            r0 = r28
            int r0 = r0.right
            r23 = r0
            r34 = r23
            r0 = r29
            int r0 = r0.right
            r23 = r0
            r35 = r23
            r0 = r28
            int r0 = r0.bottom
            r23 = r0
            r36 = r23
            r0 = r29
            int r0 = r0.bottom
            r23 = r0
            r37 = r23
            int r23 = r34 - r30
            r38 = r23
            int r23 = r36 - r32
            r39 = r23
            int r23 = r35 - r31
            r40 = r23
            int r23 = r37 - r33
            r41 = r23
            r42 = 0
            r27 = 0
            r0 = r38
            r1 = r27
            if (r0 != r1) goto L_0x0291
        L_0x00db:
            r27 = 0
            r0 = r42
            r1 = r27
            if (r0 > r1) goto L_0x02d6
        L_0x00e3:
            r14 = 0
            r13 = 0
            r154 = r13
            return r154
        L_0x00e8:
            int r23 = r19.getId()
            int r24 = r20.getId()
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x0051
            r23 = 0
            goto L_0x0053
        L_0x00fa:
            r27 = 0
            r0 = r25
            r1 = r27
            if (r0 != r1) goto L_0x0063
            java.util.Map<java.lang.String, java.lang.Object> r14 = r7.values
            java.lang.String r18 = "android:changeBounds:windowX"
            r0 = r18
            java.lang.Object r14 = r14.get(r0)
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r23 = r14.intValue()
            r114 = r23
            java.util.Map<java.lang.String, java.lang.Object> r14 = r7.values
            java.lang.String r18 = "android:changeBounds:windowY"
            r0 = r18
            java.lang.Object r14 = r14.get(r0)
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r23 = r14.intValue()
            r115 = r23
            java.util.Map<java.lang.String, java.lang.Object> r14 = r8.values
            java.lang.String r18 = "android:changeBounds:windowX"
            r0 = r18
            java.lang.Object r14 = r14.get(r0)
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r23 = r14.intValue()
            r30 = r23
            java.util.Map<java.lang.String, java.lang.Object> r14 = r8.values
            java.lang.String r18 = "android:changeBounds:windowY"
            r0 = r18
            java.lang.Object r14 = r14.get(r0)
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r23 = r14.intValue()
            r31 = r23
            r0 = r114
            r1 = r30
            if (r0 == r1) goto L_0x05be
        L_0x0154:
            int[] r0 = r5.tempLocation
            r18 = r0
            r116 = r18
            r0 = r116
            r6.getLocationInWindow(r0)
            int r23 = r22.getWidth()
            int r24 = r22.getHeight()
            android.graphics.Bitmap$Config r46 = android.graphics.Bitmap.Config.ARGB_8888
            r117 = r46
            r0 = r23
            r1 = r24
            r2 = r117
            android.graphics.Bitmap r14 = android.graphics.Bitmap.createBitmap(r0, r1, r2)
            r118 = r14
            android.graphics.Canvas r14 = new android.graphics.Canvas
            r119 = r118
            r0 = r119
            r14.<init>(r0)
            r120 = r14
            r121 = r120
            r0 = r22
            r1 = r121
            r0.draw(r1)
            android.graphics.drawable.BitmapDrawable r14 = new android.graphics.drawable.BitmapDrawable
            r122 = r118
            r0 = r122
            r14.<init>(r0)
            r123 = r14
            r24 = 4
            r0 = r22
            r1 = r24
            r0.setVisibility(r1)
            r124 = r6
            android.support.transition.ViewOverlay r14 = android.support.transition.ViewOverlay.createFrom(r124)
            r125 = r123
            r0 = r125
            r14.add(r0)
            android.graphics.Rect r14 = new android.graphics.Rect
            int[] r0 = r5.tempLocation
            r48 = r0
            r126 = 0
            r127 = r48
            r47 = r127[r126]
            int r79 = r114 - r47
            int[] r0 = r5.tempLocation
            r128 = r0
            r49 = 1
            r129 = r128
            r126 = r129[r49]
            int r47 = r115 - r126
            int[] r0 = r5.tempLocation
            r130 = r0
            r131 = 0
            r132 = r130
            r49 = r132[r131]
            int r126 = r114 - r49
            int r49 = r22.getWidth()
            int r126 = r126 + r49
            int[] r0 = r5.tempLocation
            r133 = r0
            r134 = 1
            r135 = r133
            r131 = r135[r134]
            int r49 = r115 - r131
            int r131 = r22.getHeight()
            int r49 = r49 + r131
            r0 = r79
            r1 = r47
            r2 = r126
            r3 = r49
            r14.<init>(r0, r1, r2, r3)
            r136 = r14
            android.graphics.Rect r14 = new android.graphics.Rect
            int[] r0 = r5.tempLocation
            r48 = r0
            r126 = 0
            r137 = r48
            r47 = r137[r126]
            int r79 = r30 - r47
            int[] r0 = r5.tempLocation
            r128 = r0
            r49 = 1
            r138 = r128
            r126 = r138[r49]
            int r47 = r31 - r126
            int[] r0 = r5.tempLocation
            r130 = r0
            r131 = 0
            r139 = r130
            r49 = r139[r131]
            int r126 = r30 - r49
            int r49 = r22.getWidth()
            int r126 = r126 + r49
            int[] r0 = r5.tempLocation
            r133 = r0
            r134 = 1
            r140 = r133
            r131 = r140[r134]
            int r49 = r31 - r131
            int r131 = r22.getHeight()
            int r49 = r49 + r131
            r0 = r79
            r1 = r47
            r2 = r126
            r3 = r49
            r14.<init>(r0, r1, r2, r3)
            r141 = r14
            java.lang.String r18 = "bounds"
            android.support.transition.RectEvaluator r46 = sRectEvaluator
            r47 = 2
            r0 = r47
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r48 = r0
            r49 = 0
            r142 = r48
            r142[r49] = r136
            r49 = 1
            r143 = r48
            r143[r49] = r141
            r144 = r18
            r145 = r46
            r146 = r48
            r0 = r123
            r1 = r144
            r2 = r145
            r3 = r146
            android.animation.ObjectAnimator r14 = android.animation.ObjectAnimator.ofObject(r0, r1, r2, r3)
            r147 = r14
            r14 = r14
            android.support.transition.ChangeBoundsPort$4 r18 = new android.support.transition.ChangeBoundsPort$4
            r148 = r5
            r149 = r6
            r150 = r123
            r151 = r22
            r0 = r18
            r1 = r148
            r2 = r149
            r3 = r150
            r4 = r151
            r0.<init>(r1, r2, r3, r4)
            r152 = r18
            r0 = r152
            r14.addListener(r0)
            r153 = r147
            return r153
        L_0x0291:
            r27 = 0
            r0 = r39
            r1 = r27
            if (r0 != r1) goto L_0x029b
            goto L_0x00db
        L_0x029b:
            r27 = 0
            r0 = r40
            r1 = r27
            if (r0 != r1) goto L_0x02a5
            goto L_0x00db
        L_0x02a5:
            r27 = 0
            r0 = r41
            r1 = r27
            if (r0 != r1) goto L_0x02af
            goto L_0x00db
        L_0x02af:
            r0 = r30
            r1 = r31
            if (r0 != r1) goto L_0x02c9
        L_0x02b5:
            r0 = r32
            r1 = r33
            if (r0 != r1) goto L_0x02cc
        L_0x02bb:
            r0 = r34
            r1 = r35
            if (r0 != r1) goto L_0x02cf
        L_0x02c1:
            r0 = r36
            r1 = r37
            if (r0 != r1) goto L_0x02d2
            goto L_0x00db
        L_0x02c9:
            r42 = 1
            goto L_0x02b5
        L_0x02cc:
            int r42 = r42 + 1
            goto L_0x02bb
        L_0x02cf:
            int r42 = r42 + 1
            goto L_0x02c1
        L_0x02d2:
            int r42 = r42 + 1
            goto L_0x00db
        L_0x02d6:
            boolean r0 = r5.mResizeClip
            r43 = r0
            r23 = r43
            r27 = 0
            r0 = r23
            r1 = r27
            if (r0 == r1) goto L_0x0404
            r0 = r38
            r1 = r40
            if (r0 != r1) goto L_0x053f
        L_0x02ea:
            r0 = r39
            r1 = r41
            if (r0 != r1) goto L_0x0552
        L_0x02f0:
            r0 = r30
            r1 = r31
            if (r0 != r1) goto L_0x0565
        L_0x02f6:
            r0 = r32
            r1 = r33
            if (r0 != r1) goto L_0x0575
        L_0x02fc:
            int r23 = r31 - r30
            r0 = r23
            float r0 = (float) r0
            r81 = r0
            r82 = r81
            int r23 = r33 - r32
            r0 = r23
            float r0 = (float) r0
            r81 = r0
            r83 = r81
            int r23 = r40 - r38
            r84 = r23
            int r23 = r41 - r39
            r85 = r23
            r42 = 0
            r86 = 0
            int r87 = (r82 > r86 ? 1 : (r82 == r86 ? 0 : -1))
            if (r87 == 0) goto L_0x0585
            r42 = 1
        L_0x0320:
            r86 = 0
            int r88 = (r83 > r86 ? 1 : (r83 == r86 ? 0 : -1))
            if (r88 == 0) goto L_0x0587
            int r42 = r42 + 1
        L_0x0328:
            r27 = 0
            r0 = r84
            r1 = r27
            if (r0 == r1) goto L_0x0589
        L_0x0330:
            int r42 = r42 + 1
        L_0x0332:
            r0 = r42
            android.animation.PropertyValuesHolder[] r14 = new android.animation.PropertyValuesHolder[r0]
            r76 = r14
            r89 = 0
            r86 = 0
            int r90 = (r82 > r86 ? 1 : (r82 == r86 ? 0 : -1))
            if (r90 == 0) goto L_0x0593
            r14 = r14
            r24 = 0
            r89 = 1
            java.lang.String r46 = "translationX"
            r47 = 2
            r0 = r47
            float[] r0 = new float[r0]
            r48 = r0
            r49 = 0
            float r91 = r22.getTranslationX()
            r92 = r48
            r92[r49] = r91
            r49 = 1
            r93 = r48
            r86 = 0
            r93[r49] = r86
            r94 = r46
            r95 = r48
            android.animation.PropertyValuesHolder r46 = android.animation.PropertyValuesHolder.ofFloat(r94, r95)
            r96 = r14
            r96[r24] = r46
        L_0x036e:
            r86 = 0
            int r97 = (r83 > r86 ? 1 : (r83 == r86 ? 0 : -1))
            if (r97 == 0) goto L_0x0595
            r24 = r89
            int r89 = r89 + 1
            java.lang.String r46 = "translationY"
            r47 = 2
            r0 = r47
            float[] r0 = new float[r0]
            r48 = r0
            r49 = 0
            float r91 = r22.getTranslationY()
            r98 = r48
            r98[r49] = r91
            r49 = 1
            r99 = r48
            r86 = 0
            r99[r49] = r86
            r100 = r46
            r101 = r48
            android.animation.PropertyValuesHolder r46 = android.animation.PropertyValuesHolder.ofFloat(r100, r101)
            r102 = r76
            r102[r24] = r46
        L_0x03a1:
            r27 = 0
            r0 = r84
            r1 = r27
            if (r0 == r1) goto L_0x0597
        L_0x03a9:
            android.graphics.Rect r14 = new android.graphics.Rect
            r79 = 0
            r47 = 0
            r0 = r79
            r1 = r47
            r2 = r38
            r3 = r39
            r14.<init>(r0, r1, r2, r3)
            r103 = r14
            android.graphics.Rect r14 = new android.graphics.Rect
            r79 = 0
            r47 = 0
            r0 = r79
            r1 = r47
            r2 = r40
            r3 = r41
            r14.<init>(r0, r1, r2, r3)
        L_0x03cd:
            r104 = r76
            r0 = r22
            r1 = r104
            android.animation.ObjectAnimator r14 = android.animation.ObjectAnimator.ofPropertyValuesHolder(r0, r1)
            r103 = r14
            android.view.ViewParent r14 = r22.getParent()
            boolean r0 = r14 instanceof android.view.ViewGroup
            r105 = r0
            r106 = r105
            r23 = r106
            r27 = 0
            r0 = r23
            r1 = r27
            if (r0 != r1) goto L_0x05a1
        L_0x03ed:
            android.support.transition.ChangeBoundsPort$3 r18 = new android.support.transition.ChangeBoundsPort$3
            r111 = r5
            r0 = r18
            r1 = r111
            r0.<init>(r1)
            r112 = r18
            r0 = r103
            r1 = r112
            r0.addListener(r1)
            r113 = r103
            return r113
        L_0x0404:
            r0 = r42
            android.animation.PropertyValuesHolder[] r14 = new android.animation.PropertyValuesHolder[r0]
            r44 = r14
            r45 = 0
            r0 = r30
            r1 = r31
            if (r0 != r1) goto L_0x045f
        L_0x0412:
            r0 = r32
            r1 = r33
            if (r0 != r1) goto L_0x0467
        L_0x0418:
            r0 = r34
            r1 = r35
            if (r0 != r1) goto L_0x046f
        L_0x041e:
            r0 = r36
            r1 = r37
            if (r0 != r1) goto L_0x0477
        L_0x0424:
            r0 = r30
            r1 = r31
            if (r0 != r1) goto L_0x047f
        L_0x042a:
            r0 = r32
            r1 = r33
            if (r0 != r1) goto L_0x04a7
        L_0x0430:
            r0 = r34
            r1 = r35
            if (r0 != r1) goto L_0x04d0
        L_0x0436:
            r0 = r36
            r1 = r37
            if (r0 != r1) goto L_0x04f9
        L_0x043c:
            r70 = r44
            r0 = r22
            r1 = r70
            android.animation.ObjectAnimator r14 = android.animation.ObjectAnimator.ofPropertyValuesHolder(r0, r1)
            r71 = r14
            android.view.ViewParent r14 = r22.getParent()
            boolean r0 = r14 instanceof android.view.ViewGroup
            r72 = r0
            r73 = r72
            r23 = r73
            r27 = 0
            r0 = r23
            r1 = r27
            if (r0 != r1) goto L_0x0522
        L_0x045c:
            r78 = r71
            return r78
        L_0x045f:
            r0 = r22
            r1 = r30
            r0.setLeft(r1)
            goto L_0x0412
        L_0x0467:
            r0 = r22
            r1 = r32
            r0.setTop(r1)
            goto L_0x0418
        L_0x046f:
            r0 = r22
            r1 = r34
            r0.setRight(r1)
            goto L_0x041e
        L_0x0477:
            r0 = r22
            r1 = r36
            r0.setBottom(r1)
            goto L_0x0424
        L_0x047f:
            r24 = 0
            r45 = 1
            java.lang.String r46 = "left"
            r47 = 2
            r0 = r47
            int[] r0 = new int[r0]
            r48 = r0
            r49 = 0
            r50 = r48
            r50[r49] = r30
            r49 = 1
            r51 = r48
            r51[r49] = r31
            r52 = r46
            r53 = r48
            android.animation.PropertyValuesHolder r46 = android.animation.PropertyValuesHolder.ofInt(r52, r53)
            r54 = r44
            r54[r24] = r46
            goto L_0x042a
        L_0x04a7:
            r24 = r45
            int r45 = r45 + 1
            java.lang.String r46 = "top"
            r47 = 2
            r0 = r47
            int[] r0 = new int[r0]
            r48 = r0
            r49 = 0
            r55 = r48
            r55[r49] = r32
            r49 = 1
            r56 = r48
            r56[r49] = r33
            r57 = r46
            r58 = r48
            android.animation.PropertyValuesHolder r46 = android.animation.PropertyValuesHolder.ofInt(r57, r58)
            r59 = r44
            r59[r24] = r46
            goto L_0x0430
        L_0x04d0:
            r24 = r45
            int r45 = r45 + 1
            java.lang.String r46 = "right"
            r47 = 2
            r0 = r47
            int[] r0 = new int[r0]
            r48 = r0
            r49 = 0
            r60 = r48
            r60[r49] = r34
            r49 = 1
            r61 = r48
            r61[r49] = r35
            r62 = r46
            r63 = r48
            android.animation.PropertyValuesHolder r46 = android.animation.PropertyValuesHolder.ofInt(r62, r63)
            r64 = r44
            r64[r24] = r46
            goto L_0x0436
        L_0x04f9:
            r24 = r45
            int r45 = r45 + 1
            java.lang.String r46 = "bottom"
            r47 = 2
            r0 = r47
            int[] r0 = new int[r0]
            r48 = r0
            r49 = 0
            r65 = r48
            r65[r49] = r36
            r49 = 1
            r66 = r48
            r66[r49] = r37
            r67 = r46
            r68 = r48
            android.animation.PropertyValuesHolder r46 = android.animation.PropertyValuesHolder.ofInt(r67, r68)
            r69 = r44
            r69[r24] = r46
            goto L_0x043c
        L_0x0522:
            android.view.ViewParent r14 = r22.getParent()
            android.view.ViewGroup r14 = (android.view.ViewGroup) r14
            r74 = r14
            android.support.transition.ChangeBoundsPort$1 r14 = new android.support.transition.ChangeBoundsPort$1
            r75 = r5
            r0 = r75
            r14.<init>(r0)
            r76 = r14
            r77 = r76
            r0 = r77
            android.support.transition.TransitionPort r14 = r5.addListener(r0)
            goto L_0x045c
        L_0x053f:
            r0 = r38
            r1 = r40
            int r79 = java.lang.Math.max(r0, r1)
            int r24 = r31 + r79
            r0 = r22
            r1 = r24
            r0.setRight(r1)
            goto L_0x02ea
        L_0x0552:
            r0 = r39
            r1 = r41
            int r79 = java.lang.Math.max(r0, r1)
            int r24 = r33 + r79
            r0 = r22
            r1 = r24
            r0.setBottom(r1)
            goto L_0x02f0
        L_0x0565:
            int r24 = r30 - r31
            r0 = r24
            float r0 = (float) r0
            r80 = r0
            r0 = r22
            r1 = r80
            r0.setTranslationX(r1)
            goto L_0x02f6
        L_0x0575:
            int r24 = r32 - r33
            r0 = r24
            float r0 = (float) r0
            r80 = r0
            r0 = r22
            r1 = r80
            r0.setTranslationY(r1)
            goto L_0x02fc
        L_0x0585:
            goto L_0x0320
        L_0x0587:
            goto L_0x0328
        L_0x0589:
            r27 = 0
            r0 = r85
            r1 = r27
            if (r0 != r1) goto L_0x0330
            goto L_0x0332
        L_0x0593:
            goto L_0x036e
        L_0x0595:
            goto L_0x03a1
        L_0x0597:
            r27 = 0
            r0 = r85
            r1 = r27
            if (r0 != r1) goto L_0x03a9
            goto L_0x03cd
        L_0x05a1:
            android.view.ViewParent r14 = r22.getParent()
            android.view.ViewGroup r14 = (android.view.ViewGroup) r14
            r107 = r14
            android.support.transition.ChangeBoundsPort$2 r14 = new android.support.transition.ChangeBoundsPort$2
            r108 = r5
            r0 = r108
            r14.<init>(r0)
            r109 = r14
            r110 = r109
            r0 = r110
            android.support.transition.TransitionPort r14 = r5.addListener(r0)
            goto L_0x03ed
        L_0x05be:
            r0 = r115
            r1 = r31
            if (r0 != r1) goto L_0x0154
            goto L_0x00e3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.ChangeBoundsPort.createAnimator(android.view.ViewGroup, android.support.transition.TransitionValues, android.support.transition.TransitionValues):android.animation.Animator");
    }
}
