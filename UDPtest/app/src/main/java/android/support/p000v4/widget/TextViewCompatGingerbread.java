package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Field;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.widget.TextViewCompatGingerbread */
class TextViewCompatGingerbread {
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompatGingerbread";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;

    TextViewCompatGingerbread() {
    }

    static int getMaxLines(TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        if (!sMaxModeFieldFetched) {
            sMaxModeField = retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        if (sMaxModeField != null && retrieveIntFromField(sMaxModeField, textView2) == 1) {
            if (!sMaximumFieldFetched) {
                sMaximumField = retrieveField("mMaximum");
                sMaximumFieldFetched = true;
            }
            if (sMaximumField != null) {
                return retrieveIntFromField(sMaximumField, textView2);
            }
        }
        return -1;
    }

    static int getMinLines(TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        if (!sMinModeFieldFetched) {
            sMinModeField = retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        if (sMinModeField != null && retrieveIntFromField(sMinModeField, textView2) == 1) {
            if (!sMinimumFieldFetched) {
                sMinimumField = retrieveField("mMinimum");
                sMinimumFieldFetched = true;
            }
            if (sMinimumField != null) {
                return retrieveIntFromField(sMinimumField, textView2);
            }
        }
        return -1;
    }

    private static Field retrieveField(String str) {
        String fieldName = str;
        String str2 = fieldName;
        Field field = null;
        try {
            Field declaredField = TextView.class.getDeclaredField(fieldName);
            field = declaredField;
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            NoSuchFieldException noSuchFieldException = e;
            int e2 = Log.e(LOG_TAG, "Could not retrieve " + fieldName + " field.");
        }
        return field;
    }

    private static int retrieveIntFromField(Field field, TextView textView) {
        Field field2 = field;
        TextView textView2 = textView;
        Field field3 = field2;
        TextView textView3 = textView2;
        try {
            return field2.getInt(textView2);
        } catch (IllegalAccessException e) {
            IllegalAccessException illegalAccessException = e;
            int d = Log.d(LOG_TAG, "Could not retrieve value of " + field2.getName() + " field.");
            return -1;
        }
    }

    static void setTextAppearance(TextView textView, int i) {
        TextView textView2 = textView;
        int resId = i;
        TextView textView3 = textView2;
        int i2 = resId;
        textView2.setTextAppearance(textView2.getContext(), resId);
    }

    static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        return textView2.getCompoundDrawables();
    }
}
