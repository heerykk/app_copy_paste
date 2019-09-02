package android.support.p000v4.media.session;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.p000v4.media.MediaBrowserServiceCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

/* renamed from: android.support.v4.media.session.MediaButtonReceiver */
public class MediaButtonReceiver extends BroadcastReceiver {
    private static final String TAG = "MediaButtonReceiver";

    public MediaButtonReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        Context context2 = context;
        Intent intent2 = intent;
        Context context3 = context2;
        Intent intent3 = intent2;
        Intent intent4 = new Intent("android.intent.action.MEDIA_BUTTON");
        Intent queryIntent = intent4;
        Intent intent5 = intent4.setPackage(context2.getPackageName());
        PackageManager packageManager = context2.getPackageManager();
        PackageManager pm = packageManager;
        List queryIntentServices = packageManager.queryIntentServices(queryIntent, 0);
        List list = queryIntentServices;
        if (queryIntentServices.isEmpty()) {
            Intent action = queryIntent.setAction(MediaBrowserServiceCompat.SERVICE_INTERFACE);
            list = pm.queryIntentServices(queryIntent, 0);
        }
        if (list.isEmpty()) {
            throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or a media browser service implementation");
        } else if (list.size() == 1) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(0);
            Intent component = intent2.setComponent(new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name));
            ComponentName startService = context2.startService(intent2);
        } else {
            throw new IllegalStateException("Expected 1 Service that handles " + queryIntent.getAction() + ", found " + list.size());
        }
    }

    public static KeyEvent handleIntent(MediaSessionCompat mediaSessionCompat, Intent intent) {
        MediaSessionCompat mediaSessionCompat2 = mediaSessionCompat;
        Intent intent2 = intent;
        MediaSessionCompat mediaSessionCompat3 = mediaSessionCompat2;
        Intent intent3 = intent2;
        if (mediaSessionCompat2 == null || intent2 == null || !"android.intent.action.MEDIA_BUTTON".equals(intent2.getAction()) || !intent2.hasExtra("android.intent.extra.KEY_EVENT")) {
            return null;
        }
        KeyEvent ke = (KeyEvent) intent2.getParcelableExtra("android.intent.extra.KEY_EVENT");
        MediaControllerCompat controller = mediaSessionCompat2.getController();
        MediaControllerCompat mediaControllerCompat = controller;
        boolean dispatchMediaButtonEvent = controller.dispatchMediaButtonEvent(ke);
        return ke;
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context context, long j) {
        Context context2 = context;
        long action = j;
        Context context3 = context2;
        long j2 = action;
        ComponentName mediaButtonReceiverComponent = getMediaButtonReceiverComponent(context2);
        ComponentName mbrComponent = mediaButtonReceiverComponent;
        if (mediaButtonReceiverComponent != null) {
            return buildMediaButtonPendingIntent(context2, mbrComponent, action);
        }
        int w = Log.w(TAG, "A unique media button receiver could not be found in the given context, so couldn't build a pending intent.");
        return null;
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context context, ComponentName componentName, long j) {
        Context context2 = context;
        ComponentName mbrComponent = componentName;
        long action = j;
        Context context3 = context2;
        ComponentName componentName2 = mbrComponent;
        long j2 = action;
        if (mbrComponent != null) {
            int keyCode = PlaybackStateCompat.toKeyCode(action);
            int keyCode2 = keyCode;
            if (keyCode != 0) {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                Intent intent2 = intent;
                Intent component = intent.setComponent(mbrComponent);
                KeyEvent keyEvent = new KeyEvent(0, keyCode2);
                Intent putExtra = intent2.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                return PendingIntent.getBroadcast(context2, keyCode2, intent2, 0);
            }
            int w = Log.w(TAG, "Cannot build a media button pending intent with the given action: " + action);
            return null;
        }
        int w2 = Log.w(TAG, "The component name of media button receiver should be provided.");
        return null;
    }

    static ComponentName getMediaButtonReceiverComponent(Context context) {
        Context context2 = context;
        Context context3 = context2;
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        Intent queryIntent = intent;
        Intent intent2 = intent.setPackage(context2.getPackageName());
        PackageManager packageManager = context2.getPackageManager();
        PackageManager packageManager2 = packageManager;
        List queryBroadcastReceivers = packageManager.queryBroadcastReceivers(queryIntent, 0);
        List list = queryBroadcastReceivers;
        if (queryBroadcastReceivers.size() != 1) {
            if (list.size() > 1) {
                int w = Log.w(TAG, "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.");
            }
            return null;
        }
        ResolveInfo resolveInfo = (ResolveInfo) list.get(0);
        return new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
    }
}
