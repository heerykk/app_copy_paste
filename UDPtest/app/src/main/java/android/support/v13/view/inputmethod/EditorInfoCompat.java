package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.p002os.BuildCompat;
import android.view.inputmethod.EditorInfo;

@TargetApi(13)
@RequiresApi(13)
public final class EditorInfoCompat {
    /* access modifiers changed from: private */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final EditorInfoCompatImpl IMPL;

    private static final class Api25EditorInfoCompatImpl implements EditorInfoCompatImpl {
        private Api25EditorInfoCompatImpl() {
        }

        /* synthetic */ Api25EditorInfoCompatImpl(C00811 r5) {
            C00811 r3 = r5;
            this();
        }

        public void setContentMimeTypes(@NonNull EditorInfo editorInfo, @Nullable String[] strArr) {
            EditorInfo editorInfo2 = editorInfo;
            String[] contentMimeTypes = strArr;
            EditorInfo editorInfo3 = editorInfo2;
            String[] strArr2 = contentMimeTypes;
            EditorInfoCompatApi25.setContentMimeTypes(editorInfo2, contentMimeTypes);
        }

        @NonNull
        public String[] getContentMimeTypes(@NonNull EditorInfo editorInfo) {
            EditorInfo editorInfo2 = editorInfo;
            EditorInfo editorInfo3 = editorInfo2;
            String[] contentMimeTypes = EditorInfoCompatApi25.getContentMimeTypes(editorInfo2);
            return contentMimeTypes == null ? EditorInfoCompat.EMPTY_STRING_ARRAY : contentMimeTypes;
        }
    }

    private static final class BaseEditorInfoCompatImpl implements EditorInfoCompatImpl {
        private static String CONTENT_MIME_TYPES_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";

        private BaseEditorInfoCompatImpl() {
        }

        /* synthetic */ BaseEditorInfoCompatImpl(C00811 r5) {
            C00811 r3 = r5;
            this();
        }

        public void setContentMimeTypes(@NonNull EditorInfo editorInfo, @Nullable String[] strArr) {
            EditorInfo editorInfo2 = editorInfo;
            String[] contentMimeTypes = strArr;
            EditorInfo editorInfo3 = editorInfo2;
            String[] strArr2 = contentMimeTypes;
            if (editorInfo2.extras == null) {
                editorInfo2.extras = new Bundle();
            }
            editorInfo2.extras.putStringArray(CONTENT_MIME_TYPES_KEY, contentMimeTypes);
        }

        @NonNull
        public String[] getContentMimeTypes(@NonNull EditorInfo editorInfo) {
            EditorInfo editorInfo2 = editorInfo;
            EditorInfo editorInfo3 = editorInfo2;
            if (editorInfo2.extras == null) {
                return EditorInfoCompat.EMPTY_STRING_ARRAY;
            }
            String[] stringArray = editorInfo2.extras.getStringArray(CONTENT_MIME_TYPES_KEY);
            return stringArray == null ? EditorInfoCompat.EMPTY_STRING_ARRAY : stringArray;
        }
    }

    private interface EditorInfoCompatImpl {
        @NonNull
        String[] getContentMimeTypes(@NonNull EditorInfo editorInfo);

        void setContentMimeTypes(@NonNull EditorInfo editorInfo, @Nullable String[] strArr);
    }

    public EditorInfoCompat() {
    }

    static {
        if (!BuildCompat.isAtLeastNMR1()) {
            IMPL = new BaseEditorInfoCompatImpl(null);
        } else {
            IMPL = new Api25EditorInfoCompatImpl(null);
        }
    }

    public static void setContentMimeTypes(@NonNull EditorInfo editorInfo, @Nullable String[] strArr) {
        EditorInfo editorInfo2 = editorInfo;
        String[] contentMimeTypes = strArr;
        EditorInfo editorInfo3 = editorInfo2;
        String[] strArr2 = contentMimeTypes;
        IMPL.setContentMimeTypes(editorInfo2, contentMimeTypes);
    }

    @NonNull
    public static String[] getContentMimeTypes(EditorInfo editorInfo) {
        EditorInfo editorInfo2 = editorInfo;
        EditorInfo editorInfo3 = editorInfo2;
        return IMPL.getContentMimeTypes(editorInfo2);
    }
}
