package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.text.Html;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.app.ShareCompatJB */
class ShareCompatJB {
    ShareCompatJB() {
    }

    public static String escapeHtml(CharSequence charSequence) {
        CharSequence html = charSequence;
        CharSequence charSequence2 = html;
        return Html.escapeHtml(html);
    }
}
