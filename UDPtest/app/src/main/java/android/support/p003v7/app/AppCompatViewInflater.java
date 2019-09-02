package android.support.p003v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* renamed from: android.support.v7.app.AppCompatViewInflater */
class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList;
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs = new Object[2];

    /* renamed from: android.support.v7.app.AppCompatViewInflater$DeclaredOnClickListener */
    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View view, @NonNull String str) {
            View hostView = view;
            String methodName = str;
            View view2 = hostView;
            String str2 = methodName;
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        public void onClick(@NonNull View view) {
            View v = view;
            View view2 = v;
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                Object invoke = this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{v});
            } catch (IllegalAccessException e) {
                IllegalAccessException illegalAccessException = e;
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                InvocationTargetException invocationTargetException = e2;
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String str) {
            String str2;
            Context context2 = context;
            String str3 = str;
            while (context2 != null) {
                try {
                    if (!context2.isRestricted()) {
                        Class cls = context2.getClass();
                        String str4 = this.mMethodName;
                        Class[] clsArr = new Class[1];
                        clsArr[0] = View.class;
                        Method method = cls.getMethod(str4, clsArr);
                        Method method2 = method;
                        if (method != null) {
                            this.mResolvedMethod = method2;
                            this.mResolvedContext = context2;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                }
                if (!(context2 instanceof ContextWrapper)) {
                    context2 = null;
                } else {
                    context2 = ((ContextWrapper) context2).getBaseContext();
                }
            }
            int id = this.mHostView.getId();
            int id2 = id;
            if (id != -1) {
                str2 = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id2) + "'";
            } else {
                str2 = "";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + str2);
        }
    }

    AppCompatViewInflater() {
    }

    static {
        Class[] clsArr = new Class[2];
        clsArr[0] = Context.class;
        clsArr[1] = AttributeSet.class;
        sConstructorSignature = clsArr;
        int[] iArr = new int[1];
        iArr[0] = 16843375;
        sOnClickAttrs = iArr;
        String[] strArr = new String[3];
        strArr[0] = "android.widget.";
        strArr[1] = "android.view.";
        strArr[2] = "android.webkit.";
        sClassPrefixList = strArr;
    }

    /* JADX WARNING: type inference failed for: r30v0 */
    /* JADX WARNING: type inference failed for: r30v1 */
    /* JADX WARNING: type inference failed for: r30v2 */
    /* JADX WARNING: type inference failed for: r30v3 */
    /* JADX WARNING: type inference failed for: r30v4 */
    /* JADX WARNING: type inference failed for: r30v5 */
    /* JADX WARNING: type inference failed for: r30v6 */
    /* JADX WARNING: type inference failed for: r30v7 */
    /* JADX WARNING: type inference failed for: r30v8 */
    /* JADX WARNING: type inference failed for: r30v9 */
    /* JADX WARNING: type inference failed for: r30v10 */
    /* JADX WARNING: type inference failed for: r30v11 */
    /* JADX WARNING: type inference failed for: r30v12 */
    /* JADX WARNING: type inference failed for: r30v13 */
    /* JADX WARNING: type inference failed for: r30v14 */
    /* JADX WARNING: type inference failed for: r0v44 */
    /* JADX WARNING: type inference failed for: r24v16, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r30v15 */
    /* JADX WARNING: type inference failed for: r30v16 */
    /* JADX WARNING: type inference failed for: r0v46 */
    /* JADX WARNING: type inference failed for: r76v0 */
    /* JADX WARNING: type inference failed for: r0v47, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r78v0, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View createView(android.view.View r80, java.lang.String r81, @android.support.annotation.NonNull android.content.Context r82, @android.support.annotation.NonNull android.util.AttributeSet r83, boolean r84, boolean r85, boolean r86, boolean r87) {
        /*
            r79 = this;
            r3 = r79
            r4 = r80
            r5 = r81
            r6 = r82
            r7 = r83
            r8 = r84
            r9 = r85
            r10 = r86
            r11 = r87
            r12 = r3
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            r17 = r8
            r18 = r9
            r19 = r10
            r20 = r11
            r21 = r6
            r22 = 0
            r0 = r17
            r1 = r22
            if (r0 != r1) goto L_0x006b
        L_0x002a:
            r22 = 0
            r0 = r18
            r1 = r22
            if (r0 == r1) goto L_0x0079
        L_0x0032:
            r25 = r15
            r26 = r7
            r27 = r18
            r28 = r19
            android.content.Context r24 = themifyContext(r25, r26, r27, r28)
            r15 = r24
        L_0x0040:
            r22 = 0
            r0 = r20
            r1 = r22
            if (r0 != r1) goto L_0x0082
        L_0x0048:
            r24 = 0
            r30 = 0
            r31 = -1
            int r32 = r5.hashCode()
            switch(r32) {
                case -1946472170: goto L_0x008b;
                case -1455429095: goto L_0x009f;
                case -1346021293: goto L_0x00b3;
                case -938935918: goto L_0x00c7;
                case -937446323: goto L_0x00dc;
                case -658531749: goto L_0x00f1;
                case -339785223: goto L_0x0106;
                case 776382189: goto L_0x011b;
                case 1125864064: goto L_0x0130;
                case 1413872058: goto L_0x0145;
                case 1601505219: goto L_0x015a;
                case 1666676343: goto L_0x016f;
                case 2001146706: goto L_0x0184;
                default: goto L_0x0055;
            }
        L_0x0055:
            switch(r31) {
                case 0: goto L_0x01cd;
                case 1: goto L_0x01e0;
                case 2: goto L_0x01f3;
                case 3: goto L_0x0206;
                case 4: goto L_0x0219;
                case 5: goto L_0x022c;
                case 6: goto L_0x023f;
                case 7: goto L_0x0252;
                case 8: goto L_0x0265;
                case 9: goto L_0x0278;
                case 10: goto L_0x028b;
                case 11: goto L_0x029e;
                case 12: goto L_0x02b1;
                default: goto L_0x0058;
            }
        L_0x0058:
            r23 = 0
            r0 = r30
            r1 = r23
            if (r0 == r1) goto L_0x02c4
        L_0x0060:
            r23 = 0
            r0 = r30
            r1 = r23
            if (r0 != r1) goto L_0x02dc
        L_0x0068:
            r78 = r30
            return r78
        L_0x006b:
            r23 = 0
            r0 = r23
            if (r4 != r0) goto L_0x0072
            goto L_0x002a
        L_0x0072:
            android.content.Context r24 = r4.getContext()
            r15 = r24
            goto L_0x002a
        L_0x0079:
            r22 = 0
            r0 = r19
            r1 = r22
            if (r0 != r1) goto L_0x0032
            goto L_0x0040
        L_0x0082:
            r29 = r15
            android.content.Context r24 = android.support.p003v7.widget.TintContextWrapper.wrap(r29)
            r15 = r24
            goto L_0x0048
        L_0x008b:
            java.lang.String r33 = "RatingBar"
            r0 = r33
            boolean r45 = r5.equals(r0)
            r32 = r45
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01c5
            goto L_0x0055
        L_0x009f:
            java.lang.String r33 = "CheckedTextView"
            r0 = r33
            boolean r42 = r5.equals(r0)
            r32 = r42
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01b9
            goto L_0x0055
        L_0x00b3:
            java.lang.String r33 = "MultiAutoCompleteTextView"
            r0 = r33
            boolean r44 = r5.equals(r0)
            r32 = r44
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01c1
            goto L_0x0055
        L_0x00c7:
            java.lang.String r33 = "TextView"
            r0 = r33
            boolean r34 = r5.equals(r0)
            r32 = r34
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x0199
            goto L_0x0055
        L_0x00dc:
            java.lang.String r33 = "ImageButton"
            r0 = r33
            boolean r39 = r5.equals(r0)
            r32 = r39
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01ad
            goto L_0x0055
        L_0x00f1:
            java.lang.String r33 = "SeekBar"
            r0 = r33
            boolean r46 = r5.equals(r0)
            r32 = r46
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01c9
            goto L_0x0055
        L_0x0106:
            java.lang.String r33 = "Spinner"
            r0 = r33
            boolean r38 = r5.equals(r0)
            r32 = r38
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01a9
            goto L_0x0055
        L_0x011b:
            java.lang.String r33 = "RadioButton"
            r0 = r33
            boolean r41 = r5.equals(r0)
            r32 = r41
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01b5
            goto L_0x0055
        L_0x0130:
            java.lang.String r33 = "ImageView"
            r0 = r33
            boolean r35 = r5.equals(r0)
            r32 = r35
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x019d
            goto L_0x0055
        L_0x0145:
            java.lang.String r33 = "AutoCompleteTextView"
            r0 = r33
            boolean r43 = r5.equals(r0)
            r32 = r43
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01bd
            goto L_0x0055
        L_0x015a:
            java.lang.String r33 = "CheckBox"
            r0 = r33
            boolean r40 = r5.equals(r0)
            r32 = r40
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01b1
            goto L_0x0055
        L_0x016f:
            java.lang.String r33 = "EditText"
            r0 = r33
            boolean r37 = r5.equals(r0)
            r32 = r37
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01a5
            goto L_0x0055
        L_0x0184:
            java.lang.String r33 = "Button"
            r0 = r33
            boolean r36 = r5.equals(r0)
            r32 = r36
            r22 = 0
            r0 = r32
            r1 = r22
            if (r0 != r1) goto L_0x01a1
            goto L_0x0055
        L_0x0199:
            r31 = 0
            goto L_0x0055
        L_0x019d:
            r31 = 1
            goto L_0x0055
        L_0x01a1:
            r31 = 2
            goto L_0x0055
        L_0x01a5:
            r31 = 3
            goto L_0x0055
        L_0x01a9:
            r31 = 4
            goto L_0x0055
        L_0x01ad:
            r31 = 5
            goto L_0x0055
        L_0x01b1:
            r31 = 6
            goto L_0x0055
        L_0x01b5:
            r31 = 7
            goto L_0x0055
        L_0x01b9:
            r31 = 8
            goto L_0x0055
        L_0x01bd:
            r31 = 9
            goto L_0x0055
        L_0x01c1:
            r31 = 10
            goto L_0x0055
        L_0x01c5:
            r31 = 11
            goto L_0x0055
        L_0x01c9:
            r31 = 12
            goto L_0x0055
        L_0x01cd:
            android.support.v7.widget.AppCompatTextView r24 = new android.support.v7.widget.AppCompatTextView
            r47 = r15
            r48 = r7
            r0 = r24
            r1 = r47
            r2 = r48
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x01e0:
            android.support.v7.widget.AppCompatImageView r24 = new android.support.v7.widget.AppCompatImageView
            r49 = r15
            r50 = r7
            r0 = r24
            r1 = r49
            r2 = r50
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x01f3:
            android.support.v7.widget.AppCompatButton r24 = new android.support.v7.widget.AppCompatButton
            r51 = r15
            r52 = r7
            r0 = r24
            r1 = r51
            r2 = r52
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x0206:
            android.support.v7.widget.AppCompatEditText r24 = new android.support.v7.widget.AppCompatEditText
            r53 = r15
            r54 = r7
            r0 = r24
            r1 = r53
            r2 = r54
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x0219:
            android.support.v7.widget.AppCompatSpinner r24 = new android.support.v7.widget.AppCompatSpinner
            r55 = r15
            r56 = r7
            r0 = r24
            r1 = r55
            r2 = r56
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x022c:
            android.support.v7.widget.AppCompatImageButton r24 = new android.support.v7.widget.AppCompatImageButton
            r57 = r15
            r58 = r7
            r0 = r24
            r1 = r57
            r2 = r58
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x023f:
            android.support.v7.widget.AppCompatCheckBox r24 = new android.support.v7.widget.AppCompatCheckBox
            r59 = r15
            r60 = r7
            r0 = r24
            r1 = r59
            r2 = r60
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x0252:
            android.support.v7.widget.AppCompatRadioButton r24 = new android.support.v7.widget.AppCompatRadioButton
            r61 = r15
            r62 = r7
            r0 = r24
            r1 = r61
            r2 = r62
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x0265:
            android.support.v7.widget.AppCompatCheckedTextView r24 = new android.support.v7.widget.AppCompatCheckedTextView
            r63 = r15
            r64 = r7
            r0 = r24
            r1 = r63
            r2 = r64
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x0278:
            android.support.v7.widget.AppCompatAutoCompleteTextView r24 = new android.support.v7.widget.AppCompatAutoCompleteTextView
            r65 = r15
            r66 = r7
            r0 = r24
            r1 = r65
            r2 = r66
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x028b:
            android.support.v7.widget.AppCompatMultiAutoCompleteTextView r24 = new android.support.v7.widget.AppCompatMultiAutoCompleteTextView
            r67 = r15
            r68 = r7
            r0 = r24
            r1 = r67
            r2 = r68
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x029e:
            android.support.v7.widget.AppCompatRatingBar r24 = new android.support.v7.widget.AppCompatRatingBar
            r69 = r15
            r70 = r7
            r0 = r24
            r1 = r69
            r2 = r70
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x02b1:
            android.support.v7.widget.AppCompatSeekBar r24 = new android.support.v7.widget.AppCompatSeekBar
            r71 = r15
            r72 = r7
            r0 = r24
            r1 = r71
            r2 = r72
            r0.<init>(r1, r2)
            r30 = r24
            goto L_0x0058
        L_0x02c4:
            if (r6 != r15) goto L_0x02c8
            goto L_0x0060
        L_0x02c8:
            r73 = r15
            r74 = r5
            r75 = r7
            r0 = r73
            r1 = r74
            r2 = r75
            android.view.View r24 = r3.createViewFromTag(r0, r1, r2)
            r30 = r24
            goto L_0x0060
        L_0x02dc:
            r76 = r30
            r77 = r7
            r0 = r76
            r1 = r77
            r3.checkOnClickListener(r0, r1)
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.app.AppCompatViewInflater.createView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean, boolean, boolean, boolean):android.view.View");
    }

    private View createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        Context context2 = context;
        String name = str;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        String name2 = name;
        AttributeSet attributeSet2 = attrs;
        if (name.equals("view")) {
            name2 = attrs.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context2;
            this.mConstructorArgs[1] = attrs;
            if (-1 != name2.indexOf(46)) {
                View createView = createView(context2, name2, null);
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return createView;
            }
            int i = 0;
            while (i < sClassPrefixList.length) {
                View createView2 = createView(context2, name2, sClassPrefixList[i]);
                View view = createView2;
                if (createView2 == null) {
                    i++;
                } else {
                    this.mConstructorArgs[0] = null;
                    this.mConstructorArgs[1] = null;
                    return view;
                }
            }
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return null;
        } catch (Exception e) {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return null;
        } catch (Throwable th) {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            throw th;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        View view2 = view;
        AttributeSet attrs = attributeSet;
        View view3 = view2;
        AttributeSet attributeSet2 = attrs;
        Context context = view2.getContext();
        Context context2 = context;
        if ((context instanceof ContextWrapper) && (VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view2))) {
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, sOnClickAttrs);
            TypedArray a = obtainStyledAttributes;
            String string = obtainStyledAttributes.getString(0);
            String handlerName = string;
            if (string != null) {
                view2.setOnClickListener(new DeclaredOnClickListener(view2, handlerName));
            }
            a.recycle();
        }
    }

    private View createView(Context context, String str, String str2) throws ClassNotFoundException, InflateException {
        Context context2 = context;
        String name = str;
        String prefix = str2;
        Context context3 = context2;
        String str3 = name;
        String str4 = prefix;
        Constructor constructor = (Constructor) sConstructorMap.get(name);
        Constructor constructor2 = constructor;
        if (constructor == null) {
            Class asSubclass = context2.getClassLoader().loadClass(prefix == null ? name : prefix + name).asSubclass(View.class);
            Class cls = asSubclass;
            constructor2 = asSubclass.getConstructor(sConstructorSignature);
            Object put = sConstructorMap.put(name, constructor2);
        }
        try {
            constructor2.setAccessible(true);
            return (View) constructor2.newInstance(this.mConstructorArgs);
        } catch (Exception e) {
            Exception exc = e;
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r28v0 */
    /* JADX WARNING: type inference failed for: r1v7, types: [android.content.Context] */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r29v0, types: [android.content.Context] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.Context themifyContext(android.content.Context r30, android.util.AttributeSet r31, boolean r32, boolean r33) {
        /*
            r3 = r30
            r4 = r31
            r5 = r32
            r6 = r33
            r7 = r3
            r8 = r4
            r9 = r5
            r10 = r6
            int[] r11 = android.support.p003v7.appcompat.C0268R.styleable.View
            r12 = 0
            r13 = 0
            r14 = r4
            r15 = r11
            android.content.res.TypedArray r16 = r3.obtainStyledAttributes(r14, r15, r12, r13)
            r17 = r16
            r18 = 0
            r19 = 0
            r0 = r19
            if (r9 != r0) goto L_0x0034
        L_0x0020:
            r19 = 0
            r0 = r19
            if (r10 != r0) goto L_0x0047
        L_0x0026:
            r17.recycle()
            r19 = 0
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x0078
        L_0x0031:
            r29 = r7
            return r29
        L_0x0034:
            r16 = r16
            int r20 = android.support.p003v7.appcompat.C0268R.styleable.View_android_theme
            r21 = 0
            r0 = r16
            r1 = r20
            r2 = r21
            int r22 = r0.getResourceId(r1, r2)
            r18 = r22
            goto L_0x0020
        L_0x0047:
            r19 = 0
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x0050
            goto L_0x0026
        L_0x0050:
            int r20 = android.support.p003v7.appcompat.C0268R.styleable.View_theme
            r21 = 0
            r0 = r17
            r1 = r20
            r2 = r21
            int r22 = r0.getResourceId(r1, r2)
            r18 = r22
            r19 = 0
            r0 = r22
            r1 = r19
            if (r0 != r1) goto L_0x0069
            goto L_0x0026
        L_0x0069:
            java.lang.String r16 = "AppCompatViewInflater"
            java.lang.String r23 = "app:theme is now deprecated. Please move to using android:theme instead."
            r24 = r16
            r25 = r23
            int r22 = android.util.Log.i(r24, r25)
            goto L_0x0026
        L_0x0078:
            boolean r0 = r3 instanceof android.support.p003v7.view.ContextThemeWrapper
            r26 = r0
            r27 = r26
            r22 = r27
            r19 = 0
            r0 = r22
            r1 = r19
            if (r0 != r1) goto L_0x0098
        L_0x0088:
            android.support.v7.view.ContextThemeWrapper r16 = new android.support.v7.view.ContextThemeWrapper
            r28 = r3
            r0 = r16
            r1 = r28
            r2 = r18
            r0.<init>(r1, r2)
            r7 = r16
            goto L_0x0031
        L_0x0098:
            r0 = r3
            android.support.v7.view.ContextThemeWrapper r0 = (android.support.p003v7.view.ContextThemeWrapper) r0
            r16 = r0
            int r22 = r16.getThemeResId()
            r0 = r22
            r1 = r18
            if (r0 != r1) goto L_0x0088
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.app.AppCompatViewInflater.themifyContext(android.content.Context, android.util.AttributeSet, boolean, boolean):android.content.Context");
    }
}
