package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.media.VolumeProvider;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.VolumeProviderCompatApi21 */
class VolumeProviderCompatApi21 {

    /* renamed from: android.support.v4.media.VolumeProviderCompatApi21$Delegate */
    public interface Delegate {
        void onAdjustVolume(int i);

        void onSetVolumeTo(int i);
    }

    VolumeProviderCompatApi21() {
    }

    public static Object createVolumeProvider(int i, int i2, int i3, Delegate delegate) {
        int volumeControl = i;
        int maxVolume = i2;
        int currentVolume = i3;
        Delegate delegate2 = delegate;
        int i4 = volumeControl;
        int i5 = maxVolume;
        int i6 = currentVolume;
        Delegate delegate3 = delegate2;
        return new VolumeProvider(volumeControl, maxVolume, currentVolume, delegate2) {
            final /* synthetic */ Delegate val$delegate;

            {
                int x0 = r10;
                int x1 = r11;
                int x2 = r12;
                int i = x0;
                int i2 = x1;
                int i3 = x2;
                this.val$delegate = r13;
            }

            public void onSetVolumeTo(int i) {
                int volume = i;
                int i2 = volume;
                this.val$delegate.onSetVolumeTo(volume);
            }

            public void onAdjustVolume(int i) {
                int direction = i;
                int i2 = direction;
                this.val$delegate.onAdjustVolume(direction);
            }
        };
    }

    public static void setCurrentVolume(Object obj, int i) {
        Object volumeProviderObj = obj;
        int currentVolume = i;
        Object obj2 = volumeProviderObj;
        int i2 = currentVolume;
        ((VolumeProvider) volumeProviderObj).setCurrentVolume(currentVolume);
    }
}
