package android.support.p000v4.content;

import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.content.SharedPreferencesCompat */
public final class SharedPreferencesCompat {

    /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat */
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat$Helper */
        private static class Helper {
            Helper() {
            }

            public void apply(@NonNull Editor editor) {
                Editor editor2 = editor;
                Editor editor3 = editor2;
                try {
                    editor2.apply();
                } catch (AbstractMethodError e) {
                    AbstractMethodError abstractMethodError = e;
                    boolean commit = editor2.commit();
                }
            }
        }

        private EditorCompat() {
        }

        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        public void apply(@NonNull Editor editor) {
            Editor editor2 = editor;
            Editor editor3 = editor2;
            this.mHelper.apply(editor2);
        }
    }

    private SharedPreferencesCompat() {
    }
}
