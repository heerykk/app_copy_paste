package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p003v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;

/* renamed from: android.support.v7.widget.ThemedSpinnerAdapter */
public interface ThemedSpinnerAdapter extends SpinnerAdapter {

    /* renamed from: android.support.v7.widget.ThemedSpinnerAdapter$Helper */
    public static final class Helper {
        private final Context mContext;
        private LayoutInflater mDropDownInflater;
        private final LayoutInflater mInflater;

        public Helper(@NonNull Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mContext = context2;
            this.mInflater = LayoutInflater.from(context2);
        }

        public void setDropDownViewTheme(@Nullable Theme theme) {
            Theme theme2 = theme;
            Theme theme3 = theme2;
            if (theme2 == null) {
                this.mDropDownInflater = null;
            } else if (theme2 != this.mContext.getTheme()) {
                this.mDropDownInflater = LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme2));
            } else {
                this.mDropDownInflater = this.mInflater;
            }
        }

        @Nullable
        public Theme getDropDownViewTheme() {
            return this.mDropDownInflater != null ? this.mDropDownInflater.getContext().getTheme() : null;
        }

        @NonNull
        public LayoutInflater getDropDownViewInflater() {
            return this.mDropDownInflater == null ? this.mInflater : this.mDropDownInflater;
        }
    }

    @Nullable
    Theme getDropDownViewTheme();

    void setDropDownViewTheme(@Nullable Theme theme);
}
