package android.support.p000v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* renamed from: android.support.v4.media.session.ParcelableVolumeInfo */
public class ParcelableVolumeInfo implements Parcelable {
    public static final Creator<ParcelableVolumeInfo> CREATOR = new Creator<ParcelableVolumeInfo>() {
        public ParcelableVolumeInfo createFromParcel(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            return new ParcelableVolumeInfo(in);
        }

        public ParcelableVolumeInfo[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new ParcelableVolumeInfo[size];
        }
    };
    public int audioStream;
    public int controlType;
    public int currentVolume;
    public int maxVolume;
    public int volumeType;

    public ParcelableVolumeInfo(int i, int i2, int i3, int i4, int i5) {
        int volumeType2 = i;
        int audioStream2 = i2;
        int controlType2 = i3;
        int maxVolume2 = i4;
        int currentVolume2 = i5;
        int i6 = volumeType2;
        int i7 = audioStream2;
        int i8 = controlType2;
        int i9 = maxVolume2;
        int i10 = currentVolume2;
        this.volumeType = volumeType2;
        this.audioStream = audioStream2;
        this.controlType = controlType2;
        this.maxVolume = maxVolume2;
        this.currentVolume = currentVolume2;
    }

    public ParcelableVolumeInfo(Parcel parcel) {
        Parcel from = parcel;
        Parcel parcel2 = from;
        this.volumeType = from.readInt();
        this.controlType = from.readInt();
        this.maxVolume = from.readInt();
        this.currentVolume = from.readInt();
        this.audioStream = from.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        Parcel parcel2 = dest;
        int i2 = i;
        dest.writeInt(this.volumeType);
        dest.writeInt(this.controlType);
        dest.writeInt(this.maxVolume);
        dest.writeInt(this.currentVolume);
        dest.writeInt(this.audioStream);
    }
}
