package android.support.p000v4.media;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.KeyEvent.DispatcherState;
import android.view.View;
import java.util.ArrayList;

/* renamed from: android.support.v4.media.TransportMediator */
public class TransportMediator extends TransportController {
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_RECORD = 130;
    final AudioManager mAudioManager;
    final TransportPerformer mCallbacks;
    final Context mContext;
    final TransportMediatorJellybeanMR2 mController;
    final Object mDispatcherState;
    final Callback mKeyEventCallback;
    final ArrayList<TransportStateListener> mListeners;
    final TransportMediatorCallback mTransportKeyCallback;
    final View mView;

    private TransportMediator(Activity activity, View view, TransportPerformer transportPerformer) {
        Activity activity2 = activity;
        View view2 = view;
        TransportPerformer callbacks = transportPerformer;
        Object obj = activity2;
        View view3 = view2;
        TransportPerformer transportPerformer2 = callbacks;
        this.mListeners = new ArrayList<>();
        this.mTransportKeyCallback = new TransportMediatorCallback(this) {
            final /* synthetic */ TransportMediator this$0;

            {
                TransportMediator this$02 = r5;
                TransportMediator transportMediator = this$02;
                this.this$0 = this$02;
            }

            public void handleKey(KeyEvent keyEvent) {
                KeyEvent key = keyEvent;
                KeyEvent keyEvent2 = key;
                boolean dispatch = key.dispatch(this.this$0.mKeyEventCallback);
            }

            public void handleAudioFocusChange(int i) {
                int focusChange = i;
                int i2 = focusChange;
                this.this$0.mCallbacks.onAudioFocusChange(focusChange);
            }

            public long getPlaybackPosition() {
                return this.this$0.mCallbacks.onGetCurrentPosition();
            }

            public void playbackPositionUpdate(long j) {
                long newPositionMs = j;
                long j2 = newPositionMs;
                this.this$0.mCallbacks.onSeekTo(newPositionMs);
            }
        };
        this.mKeyEventCallback = new Callback(this) {
            final /* synthetic */ TransportMediator this$0;

            {
                TransportMediator this$02 = r5;
                TransportMediator transportMediator = this$02;
                this.this$0 = this$02;
            }

            public boolean onKeyDown(int i, KeyEvent keyEvent) {
                int keyCode = i;
                KeyEvent event = keyEvent;
                int i2 = keyCode;
                KeyEvent keyEvent2 = event;
                return !TransportMediator.isMediaKey(keyCode) ? false : this.this$0.mCallbacks.onMediaButtonDown(keyCode, event);
            }

            public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
                int i2 = i;
                KeyEvent keyEvent2 = keyEvent;
                return false;
            }

            public boolean onKeyUp(int i, KeyEvent keyEvent) {
                int keyCode = i;
                KeyEvent event = keyEvent;
                int i2 = keyCode;
                KeyEvent keyEvent2 = event;
                return !TransportMediator.isMediaKey(keyCode) ? false : this.this$0.mCallbacks.onMediaButtonUp(keyCode, event);
            }

            public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
                int i3 = i;
                int i4 = i2;
                KeyEvent keyEvent2 = keyEvent;
                return false;
            }
        };
        this.mContext = activity2 == 0 ? view2.getContext() : activity2;
        this.mCallbacks = callbacks;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mView = activity2 == 0 ? view2 : activity2.getWindow().getDecorView();
        this.mDispatcherState = this.mView.getKeyDispatcherState();
        if (VERSION.SDK_INT < 18) {
            this.mController = null;
        } else {
            this.mController = new TransportMediatorJellybeanMR2(this.mContext, this.mAudioManager, this.mView, this.mTransportKeyCallback);
        }
    }

    static boolean isMediaKey(int i) {
        int keyCode = i;
        int i2 = keyCode;
        switch (keyCode) {
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case KEYCODE_MEDIA_PLAY /*126*/:
            case KEYCODE_MEDIA_PAUSE /*127*/:
            case KEYCODE_MEDIA_RECORD /*130*/:
                return true;
            default:
                return false;
        }
    }

    public TransportMediator(Activity activity, TransportPerformer transportPerformer) {
        Activity activity2 = activity;
        TransportPerformer callbacks = transportPerformer;
        Activity activity3 = activity2;
        TransportPerformer transportPerformer2 = callbacks;
        this(activity2, null, callbacks);
    }

    public TransportMediator(View view, TransportPerformer transportPerformer) {
        View view2 = view;
        TransportPerformer callbacks = transportPerformer;
        View view3 = view2;
        TransportPerformer transportPerformer2 = callbacks;
        this(null, view2, callbacks);
    }

    public Object getRemoteControlClient() {
        if (this.mController == null) {
            return null;
        }
        return this.mController.getRemoteControlClient();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return event.dispatch(this.mKeyEventCallback, (DispatcherState) this.mDispatcherState, this);
    }

    public void registerStateListener(TransportStateListener transportStateListener) {
        TransportStateListener listener = transportStateListener;
        TransportStateListener transportStateListener2 = listener;
        boolean add = this.mListeners.add(listener);
    }

    public void unregisterStateListener(TransportStateListener transportStateListener) {
        TransportStateListener listener = transportStateListener;
        TransportStateListener transportStateListener2 = listener;
        boolean remove = this.mListeners.remove(listener);
    }

    private TransportStateListener[] getListeners() {
        if (this.mListeners.size() <= 0) {
            return null;
        }
        TransportStateListener[] listeners = new TransportStateListener[this.mListeners.size()];
        Object[] array = this.mListeners.toArray(listeners);
        return listeners;
    }

    private void reportPlayingChanged() {
        TransportStateListener[] listeners = getListeners();
        TransportStateListener[] listeners2 = listeners;
        if (listeners != null) {
            for (TransportStateListener transportStateListener : listeners2) {
                TransportStateListener transportStateListener2 = transportStateListener;
                transportStateListener.onPlayingChanged(this);
            }
        }
    }

    private void reportTransportControlsChanged() {
        TransportStateListener[] listeners = getListeners();
        TransportStateListener[] listeners2 = listeners;
        if (listeners != null) {
            for (TransportStateListener transportStateListener : listeners2) {
                TransportStateListener transportStateListener2 = transportStateListener;
                transportStateListener.onTransportControlsChanged(this);
            }
        }
    }

    private void pushControllerState() {
        if (this.mController != null) {
            this.mController.refreshState(this.mCallbacks.onIsPlaying(), this.mCallbacks.onGetCurrentPosition(), this.mCallbacks.onGetTransportControlFlags());
        }
    }

    public void refreshState() {
        pushControllerState();
        reportPlayingChanged();
        reportTransportControlsChanged();
    }

    public void startPlaying() {
        if (this.mController != null) {
            this.mController.startPlaying();
        }
        this.mCallbacks.onStart();
        pushControllerState();
        reportPlayingChanged();
    }

    public void pausePlaying() {
        if (this.mController != null) {
            this.mController.pausePlaying();
        }
        this.mCallbacks.onPause();
        pushControllerState();
        reportPlayingChanged();
    }

    public void stopPlaying() {
        if (this.mController != null) {
            this.mController.stopPlaying();
        }
        this.mCallbacks.onStop();
        pushControllerState();
        reportPlayingChanged();
    }

    public long getDuration() {
        return this.mCallbacks.onGetDuration();
    }

    public long getCurrentPosition() {
        return this.mCallbacks.onGetCurrentPosition();
    }

    public void seekTo(long j) {
        long pos = j;
        long j2 = pos;
        this.mCallbacks.onSeekTo(pos);
    }

    public boolean isPlaying() {
        return this.mCallbacks.onIsPlaying();
    }

    public int getBufferPercentage() {
        return this.mCallbacks.onGetBufferPercentage();
    }

    public int getTransportControlFlags() {
        return this.mCallbacks.onGetTransportControlFlags();
    }

    public void destroy() {
        this.mController.destroy();
    }
}
