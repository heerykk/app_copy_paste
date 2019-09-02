package android.support.p000v4.media;

import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.media.VolumeProviderCompatApi21.Delegate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v4.media.VolumeProviderCompat */
public abstract class VolumeProviderCompat {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private Callback mCallback;
    private final int mControlType;
    private int mCurrentVolume;
    private final int mMaxVolume;
    private Object mVolumeProviderObj;

    /* renamed from: android.support.v4.media.VolumeProviderCompat$Callback */
    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProviderCompat volumeProviderCompat);

        public Callback() {
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.VolumeProviderCompat$ControlType */
    public @interface ControlType {
    }

    public VolumeProviderCompat(int i, int i2, int i3) {
        int volumeControl = i;
        int maxVolume = i2;
        int currentVolume = i3;
        int i4 = volumeControl;
        int i5 = maxVolume;
        int i6 = currentVolume;
        this.mControlType = volumeControl;
        this.mMaxVolume = maxVolume;
        this.mCurrentVolume = currentVolume;
    }

    public final int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public final int getVolumeControl() {
        return this.mControlType;
    }

    public final int getMaxVolume() {
        return this.mMaxVolume;
    }

    public final void setCurrentVolume(int i) {
        int currentVolume = i;
        int i2 = currentVolume;
        this.mCurrentVolume = currentVolume;
        Object volumeProvider = getVolumeProvider();
        Object volumeProviderObj = volumeProvider;
        if (volumeProvider != null) {
            VolumeProviderCompatApi21.setCurrentVolume(volumeProviderObj, currentVolume);
        }
        if (this.mCallback != null) {
            this.mCallback.onVolumeChanged(this);
        }
    }

    public void onSetVolumeTo(int i) {
        int i2 = i;
    }

    public void onAdjustVolume(int i) {
        int i2 = i;
    }

    public void setCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        this.mCallback = callback2;
    }

    public Object getVolumeProvider() {
        if (this.mVolumeProviderObj != null || VERSION.SDK_INT < 21) {
            return this.mVolumeProviderObj;
        }
        this.mVolumeProviderObj = VolumeProviderCompatApi21.createVolumeProvider(this.mControlType, this.mMaxVolume, this.mCurrentVolume, new Delegate(this) {
            final /* synthetic */ VolumeProviderCompat this$0;

            {
                VolumeProviderCompat this$02 = r5;
                VolumeProviderCompat volumeProviderCompat = this$02;
                this.this$0 = this$02;
            }

            public void onSetVolumeTo(int i) {
                int volume = i;
                int i2 = volume;
                this.this$0.onSetVolumeTo(volume);
            }

            public void onAdjustVolume(int i) {
                int direction = i;
                int i2 = direction;
                this.this$0.onAdjustVolume(direction);
            }
        });
        return this.mVolumeProviderObj;
    }
}
