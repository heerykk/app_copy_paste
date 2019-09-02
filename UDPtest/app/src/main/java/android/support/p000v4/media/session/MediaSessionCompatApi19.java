package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi19 */
class MediaSessionCompatApi19 {
    private static final long ACTION_SET_RATING = 128;
    private static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    private static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    private static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi19$Callback */
    interface Callback extends Callback {
        void onSetRating(Object obj);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi19$OnMetadataUpdateListener */
    static class OnMetadataUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnMetadataUpdateListener {
        protected final T mCallback;

        public OnMetadataUpdateListener(T t) {
            T callback = t;
            T t2 = callback;
            this.mCallback = callback;
        }

        public void onMetadataUpdate(int i, Object obj) {
            int key = i;
            Object newValue = obj;
            int i2 = key;
            Object obj2 = newValue;
            if (key == 268435457 && (newValue instanceof Rating)) {
                this.mCallback.onSetRating(newValue);
            }
        }
    }

    MediaSessionCompatApi19() {
    }

    public static void setTransportControlFlags(Object obj, long j) {
        Object rccObj = obj;
        long actions = j;
        Object obj2 = rccObj;
        long j2 = actions;
        ((RemoteControlClient) rccObj).setTransportControlFlags(getRccTransportControlFlagsFromActions(actions));
    }

    public static Object createMetadataUpdateListener(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new OnMetadataUpdateListener(callback2);
    }

    public static void setMetadata(Object obj, Bundle bundle, long j) {
        Object rccObj = obj;
        Bundle metadata = bundle;
        long actions = j;
        Object obj2 = rccObj;
        Bundle bundle2 = metadata;
        long j2 = actions;
        MetadataEditor editor = ((RemoteControlClient) rccObj).editMetadata(true);
        MediaSessionCompatApi14.buildOldMetadata(metadata, editor);
        addNewMetadata(metadata, editor);
        if ((actions & 128) != 0) {
            editor.addEditableKey(268435457);
        }
        editor.apply();
    }

    public static void setOnMetadataUpdateListener(Object obj, Object obj2) {
        Object rccObj = obj;
        Object onMetadataUpdateObj = obj2;
        Object obj3 = rccObj;
        Object obj4 = onMetadataUpdateObj;
        ((RemoteControlClient) rccObj).setMetadataUpdateListener((android.media.RemoteControlClient.OnMetadataUpdateListener) onMetadataUpdateObj);
    }

    static int getRccTransportControlFlagsFromActions(long j) {
        long actions = j;
        long j2 = actions;
        int rccTransportControlFlagsFromActions = MediaSessionCompatApi18.getRccTransportControlFlagsFromActions(actions);
        int transportControlFlags = rccTransportControlFlagsFromActions;
        if ((actions & 128) != 0) {
            transportControlFlags = rccTransportControlFlagsFromActions | 512;
        }
        return transportControlFlags;
    }

    static void addNewMetadata(Bundle bundle, MetadataEditor metadataEditor) {
        Bundle metadata = bundle;
        MetadataEditor editor = metadataEditor;
        Bundle bundle2 = metadata;
        MetadataEditor metadataEditor2 = editor;
        if (metadata != null) {
            if (metadata.containsKey("android.media.metadata.YEAR")) {
                MetadataEditor putLong = editor.putLong(8, metadata.getLong("android.media.metadata.YEAR"));
            }
            if (metadata.containsKey("android.media.metadata.RATING")) {
                MetadataEditor putObject = editor.putObject(101, metadata.getParcelable("android.media.metadata.RATING"));
            }
            if (metadata.containsKey("android.media.metadata.USER_RATING")) {
                MetadataEditor putObject2 = editor.putObject(268435457, metadata.getParcelable("android.media.metadata.USER_RATING"));
            }
        }
    }
}
