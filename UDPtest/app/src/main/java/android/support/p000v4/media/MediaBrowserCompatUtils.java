package android.support.p000v4.media;

import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.media.MediaBrowserCompatUtils */
public class MediaBrowserCompatUtils {
    public MediaBrowserCompatUtils() {
    }

    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        Bundle options1 = bundle;
        Bundle options2 = bundle2;
        Bundle bundle3 = options1;
        Bundle bundle4 = options2;
        if (options1 == options2) {
            return true;
        }
        if (options1 == null) {
            return options2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && options2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1;
        } else if (options2 != null) {
            return options1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == options2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) && options1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == options2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        } else {
            return options1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && options1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1;
        }
    }

    public static boolean hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        int i;
        int i2;
        int startIndex1;
        int endIndex1;
        int startIndex2;
        int endIndex2;
        Bundle options1 = bundle;
        Bundle options2 = bundle2;
        Bundle bundle3 = options1;
        Bundle bundle4 = options2;
        int page1 = options1 != null ? options1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) : -1;
        int page2 = options2 != null ? options2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) : -1;
        if (options1 != null) {
            i = options1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        } else {
            i = -1;
        }
        int pageSize1 = i;
        if (options2 != null) {
            i2 = options2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        } else {
            i2 = -1;
        }
        int pageSize2 = i2;
        if (page1 == -1 || pageSize1 == -1) {
            startIndex1 = 0;
            endIndex1 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        } else {
            int i3 = pageSize1 * page1;
            startIndex1 = i3;
            endIndex1 = (i3 + pageSize1) - 1;
        }
        if (page2 == -1 || pageSize2 == -1) {
            startIndex2 = 0;
            endIndex2 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        } else {
            int i4 = pageSize2 * page2;
            startIndex2 = i4;
            endIndex2 = (i4 + pageSize2) - 1;
        }
        if (startIndex1 <= startIndex2 && startIndex2 <= endIndex1) {
            return true;
        }
        if (startIndex1 <= endIndex2 && endIndex2 <= endIndex1) {
            return true;
        }
        return false;
    }
}
