package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.OnGetPlaybackPositionListener;
import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnWindowAttachListener;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.media.TransportMediatorJellybeanMR2 */
class TransportMediatorJellybeanMR2 {
    OnAudioFocusChangeListener mAudioFocusChangeListener = new OnAudioFocusChangeListener(this) {
        final /* synthetic */ TransportMediatorJellybeanMR2 this$0;

        {
            TransportMediatorJellybeanMR2 this$02 = r5;
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this$02;
            this.this$0 = this$02;
        }

        public void onAudioFocusChange(int i) {
            int focusChange = i;
            int i2 = focusChange;
            this.this$0.mTransportCallback.handleAudioFocusChange(focusChange);
        }
    };
    boolean mAudioFocused;
    final AudioManager mAudioManager;
    final Context mContext;
    boolean mFocused;
    final OnGetPlaybackPositionListener mGetPlaybackPositionListener = new OnGetPlaybackPositionListener(this) {
        final /* synthetic */ TransportMediatorJellybeanMR2 this$0;

        {
            TransportMediatorJellybeanMR2 this$02 = r5;
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this$02;
            this.this$0 = this$02;
        }

        public long onGetPlaybackPosition() {
            return this.this$0.mTransportCallback.getPlaybackPosition();
        }
    };
    final Intent mIntent;
    final BroadcastReceiver mMediaButtonReceiver = new BroadcastReceiver(this) {
        final /* synthetic */ TransportMediatorJellybeanMR2 this$0;

        {
            TransportMediatorJellybeanMR2 this$02 = r5;
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this$02;
            this.this$0 = this$02;
        }

        public void onReceive(Context context, Intent intent) {
            Intent intent2 = intent;
            Context context2 = context;
            Intent intent3 = intent2;
            try {
                this.this$0.mTransportCallback.handleKey((KeyEvent) intent2.getParcelableExtra("android.intent.extra.KEY_EVENT"));
            } catch (ClassCastException e) {
                ClassCastException classCastException = e;
                int w = Log.w("TransportController", e);
            }
        }
    };
    PendingIntent mPendingIntent;
    int mPlayState = 0;
    final OnPlaybackPositionUpdateListener mPlaybackPositionUpdateListener = new OnPlaybackPositionUpdateListener(this) {
        final /* synthetic */ TransportMediatorJellybeanMR2 this$0;

        {
            TransportMediatorJellybeanMR2 this$02 = r5;
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this$02;
            this.this$0 = this$02;
        }

        public void onPlaybackPositionUpdate(long j) {
            long newPositionMs = j;
            long j2 = newPositionMs;
            this.this$0.mTransportCallback.playbackPositionUpdate(newPositionMs);
        }
    };
    final String mReceiverAction;
    final IntentFilter mReceiverFilter;
    RemoteControlClient mRemoteControl;
    final View mTargetView;
    final TransportMediatorCallback mTransportCallback;
    final OnWindowAttachListener mWindowAttachListener = new OnWindowAttachListener(this) {
        final /* synthetic */ TransportMediatorJellybeanMR2 this$0;

        {
            TransportMediatorJellybeanMR2 this$02 = r5;
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this$02;
            this.this$0 = this$02;
        }

        public void onWindowAttached() {
            this.this$0.windowAttached();
        }

        public void onWindowDetached() {
            this.this$0.windowDetached();
        }
    };
    final OnWindowFocusChangeListener mWindowFocusListener = new OnWindowFocusChangeListener(this) {
        final /* synthetic */ TransportMediatorJellybeanMR2 this$0;

        {
            TransportMediatorJellybeanMR2 this$02 = r5;
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this$02;
            this.this$0 = this$02;
        }

        public void onWindowFocusChanged(boolean z) {
            if (!z) {
                this.this$0.loseFocus();
            } else {
                this.this$0.gainFocus();
            }
        }
    };

    public TransportMediatorJellybeanMR2(Context context, AudioManager audioManager, View view, TransportMediatorCallback transportMediatorCallback) {
        Context context2 = context;
        AudioManager audioManager2 = audioManager;
        View view2 = view;
        TransportMediatorCallback transportCallback = transportMediatorCallback;
        Context context3 = context2;
        AudioManager audioManager3 = audioManager2;
        View view3 = view2;
        TransportMediatorCallback transportMediatorCallback2 = transportCallback;
        this.mContext = context2;
        this.mAudioManager = audioManager2;
        this.mTargetView = view2;
        this.mTransportCallback = transportCallback;
        this.mReceiverAction = context2.getPackageName() + ":transport:" + System.identityHashCode(this);
        this.mIntent = new Intent(this.mReceiverAction);
        Intent intent = this.mIntent.setPackage(context2.getPackageName());
        this.mReceiverFilter = new IntentFilter();
        this.mReceiverFilter.addAction(this.mReceiverAction);
        this.mTargetView.getViewTreeObserver().addOnWindowAttachListener(this.mWindowAttachListener);
        this.mTargetView.getViewTreeObserver().addOnWindowFocusChangeListener(this.mWindowFocusListener);
    }

    public Object getRemoteControlClient() {
        return this.mRemoteControl;
    }

    public void destroy() {
        windowDetached();
        this.mTargetView.getViewTreeObserver().removeOnWindowAttachListener(this.mWindowAttachListener);
        this.mTargetView.getViewTreeObserver().removeOnWindowFocusChangeListener(this.mWindowFocusListener);
    }

    /* access modifiers changed from: 0000 */
    public void windowAttached() {
        Intent registerReceiver = this.mContext.registerReceiver(this.mMediaButtonReceiver, this.mReceiverFilter);
        this.mPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, this.mIntent, 268435456);
        this.mRemoteControl = new RemoteControlClient(this.mPendingIntent);
        this.mRemoteControl.setOnGetPlaybackPositionListener(this.mGetPlaybackPositionListener);
        this.mRemoteControl.setPlaybackPositionUpdateListener(this.mPlaybackPositionUpdateListener);
    }

    /* access modifiers changed from: 0000 */
    public void gainFocus() {
        if (!this.mFocused) {
            this.mFocused = true;
            this.mAudioManager.registerMediaButtonEventReceiver(this.mPendingIntent);
            this.mAudioManager.registerRemoteControlClient(this.mRemoteControl);
            if (this.mPlayState == 3) {
                takeAudioFocus();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void takeAudioFocus() {
        if (!this.mAudioFocused) {
            this.mAudioFocused = true;
            int requestAudioFocus = this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
        }
    }

    public void startPlaying() {
        if (this.mPlayState != 3) {
            this.mPlayState = 3;
            this.mRemoteControl.setPlaybackState(3);
        }
        if (this.mFocused) {
            takeAudioFocus();
        }
    }

    public void refreshState(boolean z, long j, int i) {
        long position = j;
        int transportControls = i;
        boolean playing = z;
        long j2 = position;
        int i2 = transportControls;
        if (this.mRemoteControl != null) {
            this.mRemoteControl.setPlaybackState(!playing ? 1 : 3, position, !playing ? 0.0f : 1.0f);
            this.mRemoteControl.setTransportControlFlags(transportControls);
        }
    }

    public void pausePlaying() {
        if (this.mPlayState == 3) {
            this.mPlayState = 2;
            this.mRemoteControl.setPlaybackState(2);
        }
        dropAudioFocus();
    }

    public void stopPlaying() {
        if (this.mPlayState != 1) {
            this.mPlayState = 1;
            this.mRemoteControl.setPlaybackState(1);
        }
        dropAudioFocus();
    }

    /* access modifiers changed from: 0000 */
    public void dropAudioFocus() {
        if (this.mAudioFocused) {
            this.mAudioFocused = false;
            int abandonAudioFocus = this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void loseFocus() {
        dropAudioFocus();
        if (this.mFocused) {
            this.mFocused = false;
            this.mAudioManager.unregisterRemoteControlClient(this.mRemoteControl);
            this.mAudioManager.unregisterMediaButtonEventReceiver(this.mPendingIntent);
        }
    }

    /* access modifiers changed from: 0000 */
    public void windowDetached() {
        loseFocus();
        if (this.mPendingIntent != null) {
            this.mContext.unregisterReceiver(this.mMediaButtonReceiver);
            this.mPendingIntent.cancel();
            this.mPendingIntent = null;
            this.mRemoteControl = null;
        }
    }
}
