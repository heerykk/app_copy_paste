package android.support.p000v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

/* renamed from: android.support.v4.media.MediaMetadataCompat */
public final class MediaMetadataCompat implements Parcelable {
    public static final Creator<MediaMetadataCompat> CREATOR = new Creator<MediaMetadataCompat>() {
        public MediaMetadataCompat createFromParcel(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            return new MediaMetadataCompat(in);
        }

        public MediaMetadataCompat[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new MediaMetadataCompat[size];
        }
    };
    static final ArrayMap<String, Integer> METADATA_KEYS_TYPE = new ArrayMap<>();
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    static final int METADATA_TYPE_BITMAP = 2;
    static final int METADATA_TYPE_LONG = 0;
    static final int METADATA_TYPE_RATING = 3;
    static final int METADATA_TYPE_TEXT = 1;
    private static final String[] PREFERRED_BITMAP_ORDER;
    private static final String[] PREFERRED_DESCRIPTION_ORDER;
    private static final String[] PREFERRED_URI_ORDER;
    private static final String TAG = "MediaMetadata";
    final Bundle mBundle;
    private MediaDescriptionCompat mDescription;
    private Object mMetadataObj;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.MediaMetadataCompat$BitmapKey */
    public @interface BitmapKey {
    }

    /* renamed from: android.support.v4.media.MediaMetadataCompat$Builder */
    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(MediaMetadataCompat mediaMetadataCompat) {
            MediaMetadataCompat source = mediaMetadataCompat;
            MediaMetadataCompat mediaMetadataCompat2 = source;
            this.mBundle = new Bundle(source.mBundle);
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Builder(MediaMetadataCompat mediaMetadataCompat, int i) {
            MediaMetadataCompat source = mediaMetadataCompat;
            int maxBitmapSize = i;
            MediaMetadataCompat mediaMetadataCompat2 = source;
            int i2 = maxBitmapSize;
            this(source);
            for (String key : this.mBundle.keySet()) {
                Object obj = this.mBundle.get(key);
                Object value = obj;
                if (obj != null && (value instanceof Bitmap)) {
                    Bitmap bitmap = (Bitmap) value;
                    Bitmap bmp = bitmap;
                    if (bitmap.getHeight() > maxBitmapSize || bmp.getWidth() > maxBitmapSize) {
                        Builder putBitmap = putBitmap(key, scaleBitmap(bmp, maxBitmapSize));
                    } else if (VERSION.SDK_INT >= 14 && (key.equals(MediaMetadataCompat.METADATA_KEY_ART) || key.equals(MediaMetadataCompat.METADATA_KEY_ALBUM_ART))) {
                        Builder putBitmap2 = putBitmap(key, bmp.copy(bmp.getConfig(), false));
                    }
                }
            }
        }

        public Builder putText(String str, CharSequence charSequence) {
            String key = str;
            CharSequence value = charSequence;
            String str2 = key;
            CharSequence charSequence2 = value;
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(key) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(key)).intValue() != 1) {
                throw new IllegalArgumentException("The " + key + " key cannot be used to put a CharSequence");
            }
            this.mBundle.putCharSequence(key, value);
            return this;
        }

        public Builder putString(String str, String str2) {
            String key = str;
            String value = str2;
            String str3 = key;
            String str4 = value;
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(key) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(key)).intValue() != 1) {
                throw new IllegalArgumentException("The " + key + " key cannot be used to put a String");
            }
            this.mBundle.putCharSequence(key, value);
            return this;
        }

        public Builder putLong(String str, long j) {
            String key = str;
            long value = j;
            String str2 = key;
            long j2 = value;
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(key) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(key)).intValue() != 0) {
                throw new IllegalArgumentException("The " + key + " key cannot be used to put a long");
            }
            this.mBundle.putLong(key, value);
            return this;
        }

        public Builder putRating(String str, RatingCompat ratingCompat) {
            String key = str;
            RatingCompat value = ratingCompat;
            String str2 = key;
            RatingCompat ratingCompat2 = value;
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(key) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(key)).intValue() != 3) {
                throw new IllegalArgumentException("The " + key + " key cannot be used to put a Rating");
            }
            if (VERSION.SDK_INT < 19) {
                this.mBundle.putParcelable(key, value);
            } else {
                this.mBundle.putParcelable(key, (Parcelable) value.getRating());
            }
            return this;
        }

        public Builder putBitmap(String str, Bitmap bitmap) {
            String key = str;
            Bitmap value = bitmap;
            String str2 = key;
            Bitmap bitmap2 = value;
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(key) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get(key)).intValue() != 2) {
                throw new IllegalArgumentException("The " + key + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(key, value);
            return this;
        }

        public MediaMetadataCompat build() {
            return new MediaMetadataCompat(this.mBundle);
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int i) {
            Bitmap bmp = bitmap;
            int maxSize = i;
            Bitmap bitmap2 = bmp;
            int i2 = maxSize;
            float f = (float) maxSize;
            float scale = Math.min(f / ((float) bmp.getWidth()), f / ((float) bmp.getHeight()));
            int width = (int) (((float) bmp.getWidth()) * scale);
            int i3 = width;
            return Bitmap.createScaledBitmap(bmp, width, (int) (((float) bmp.getHeight()) * scale), true);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.MediaMetadataCompat$LongKey */
    public @interface LongKey {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.MediaMetadataCompat$RatingKey */
    public @interface RatingKey {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.MediaMetadataCompat$TextKey */
    public @interface TextKey {
    }

    static {
        Object put = METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, Integer.valueOf(1));
        Object put2 = METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, Integer.valueOf(1));
        Object put3 = METADATA_KEYS_TYPE.put(METADATA_KEY_DURATION, Integer.valueOf(0));
        Object put4 = METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, Integer.valueOf(1));
        Object put5 = METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, Integer.valueOf(1));
        Object put6 = METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, Integer.valueOf(1));
        Object put7 = METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, Integer.valueOf(1));
        Object put8 = METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, Integer.valueOf(1));
        Object put9 = METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, Integer.valueOf(1));
        Object put10 = METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, Integer.valueOf(0));
        Object put11 = METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, Integer.valueOf(1));
        Object put12 = METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, Integer.valueOf(0));
        Object put13 = METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, Integer.valueOf(0));
        Object put14 = METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, Integer.valueOf(0));
        Object put15 = METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, Integer.valueOf(1));
        Object put16 = METADATA_KEYS_TYPE.put(METADATA_KEY_ART, Integer.valueOf(2));
        Object put17 = METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, Integer.valueOf(1));
        Object put18 = METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, Integer.valueOf(2));
        Object put19 = METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, Integer.valueOf(1));
        Object put20 = METADATA_KEYS_TYPE.put(METADATA_KEY_USER_RATING, Integer.valueOf(3));
        Object put21 = METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, Integer.valueOf(3));
        Object put22 = METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, Integer.valueOf(1));
        Object put23 = METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, Integer.valueOf(1));
        Object put24 = METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, Integer.valueOf(1));
        Object put25 = METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, Integer.valueOf(2));
        Object put26 = METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, Integer.valueOf(1));
        Object put27 = METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, Integer.valueOf(1));
        Object put28 = METADATA_KEYS_TYPE.put(METADATA_KEY_BT_FOLDER_TYPE, Integer.valueOf(0));
        Object put29 = METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_URI, Integer.valueOf(1));
        String[] strArr = new String[7];
        strArr[0] = METADATA_KEY_TITLE;
        strArr[1] = METADATA_KEY_ARTIST;
        strArr[2] = METADATA_KEY_ALBUM;
        strArr[3] = METADATA_KEY_ALBUM_ARTIST;
        strArr[4] = METADATA_KEY_WRITER;
        strArr[5] = METADATA_KEY_AUTHOR;
        strArr[6] = METADATA_KEY_COMPOSER;
        PREFERRED_DESCRIPTION_ORDER = strArr;
        String[] strArr2 = new String[3];
        strArr2[0] = METADATA_KEY_DISPLAY_ICON;
        strArr2[1] = METADATA_KEY_ART;
        strArr2[2] = METADATA_KEY_ALBUM_ART;
        PREFERRED_BITMAP_ORDER = strArr2;
        String[] strArr3 = new String[3];
        strArr3[0] = METADATA_KEY_DISPLAY_ICON_URI;
        strArr3[1] = METADATA_KEY_ART_URI;
        strArr3[2] = METADATA_KEY_ALBUM_ART_URI;
        PREFERRED_URI_ORDER = strArr3;
    }

    MediaMetadataCompat(Bundle bundle) {
        Bundle bundle2 = bundle;
        Bundle bundle3 = bundle2;
        this.mBundle = new Bundle(bundle2);
    }

    MediaMetadataCompat(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        this.mBundle = in.readBundle();
    }

    public boolean containsKey(String str) {
        String key = str;
        String str2 = key;
        return this.mBundle.containsKey(key);
    }

    public CharSequence getText(String str) {
        String key = str;
        String str2 = key;
        return this.mBundle.getCharSequence(key);
    }

    public String getString(String str) {
        String key = str;
        String str2 = key;
        CharSequence charSequence = this.mBundle.getCharSequence(key);
        CharSequence text = charSequence;
        if (charSequence == null) {
            return null;
        }
        return text.toString();
    }

    public long getLong(String str) {
        String key = str;
        String str2 = key;
        return this.mBundle.getLong(key, 0);
    }

    public RatingCompat getRating(String str) {
        String key = str;
        String str2 = key;
        RatingCompat rating = null;
        try {
            if (VERSION.SDK_INT < 19) {
                rating = (RatingCompat) this.mBundle.getParcelable(key);
            } else {
                rating = RatingCompat.fromRating(this.mBundle.getParcelable(key));
            }
        } catch (Exception e) {
            Exception exc = e;
            int w = Log.w(TAG, "Failed to retrieve a key as Rating.", e);
        }
        return rating;
    }

    public Bitmap getBitmap(String str) {
        String key = str;
        String str2 = key;
        Bitmap bmp = null;
        try {
            bmp = (Bitmap) this.mBundle.getParcelable(key);
        } catch (Exception e) {
            Exception exc = e;
            int w = Log.w(TAG, "Failed to retrieve a key as Bitmap.", e);
        }
        return bmp;
    }

    public MediaDescriptionCompat getDescription() {
        if (this.mDescription != null) {
            return this.mDescription;
        }
        String mediaId = getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] text = new CharSequence[3];
        Bitmap icon = null;
        Uri iconUri = null;
        CharSequence text2 = getText(METADATA_KEY_DISPLAY_TITLE);
        CharSequence displayText = text2;
        if (TextUtils.isEmpty(text2)) {
            int textIndex = 0;
            int keyIndex = 0;
            while (textIndex < text.length && keyIndex < PREFERRED_DESCRIPTION_ORDER.length) {
                int i = keyIndex;
                keyIndex++;
                CharSequence text3 = getText(PREFERRED_DESCRIPTION_ORDER[i]);
                CharSequence next = text3;
                if (!TextUtils.isEmpty(text3)) {
                    int i2 = textIndex;
                    textIndex++;
                    text[i2] = next;
                }
            }
        } else {
            text[0] = displayText;
            text[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            text[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        }
        int i3 = 0;
        while (true) {
            if (i3 < PREFERRED_BITMAP_ORDER.length) {
                Bitmap bitmap = getBitmap(PREFERRED_BITMAP_ORDER[i3]);
                Bitmap next2 = bitmap;
                if (bitmap != null) {
                    icon = next2;
                    break;
                }
                i3++;
            } else {
                break;
            }
        }
        int i4 = 0;
        while (true) {
            if (i4 < PREFERRED_URI_ORDER.length) {
                String string = getString(PREFERRED_URI_ORDER[i4]);
                String next3 = string;
                if (!TextUtils.isEmpty(string)) {
                    iconUri = Uri.parse(next3);
                    break;
                }
                i4++;
            } else {
                break;
            }
        }
        Uri mediaUri = null;
        String string2 = getString(METADATA_KEY_MEDIA_URI);
        String mediaUriStr = string2;
        if (!TextUtils.isEmpty(string2)) {
            mediaUri = Uri.parse(mediaUriStr);
        }
        android.support.p000v4.media.MediaDescriptionCompat.Builder builder = new android.support.p000v4.media.MediaDescriptionCompat.Builder();
        android.support.p000v4.media.MediaDescriptionCompat.Builder bob = builder;
        android.support.p000v4.media.MediaDescriptionCompat.Builder mediaId2 = builder.setMediaId(mediaId);
        android.support.p000v4.media.MediaDescriptionCompat.Builder title = bob.setTitle(text[0]);
        android.support.p000v4.media.MediaDescriptionCompat.Builder subtitle = bob.setSubtitle(text[1]);
        android.support.p000v4.media.MediaDescriptionCompat.Builder description = bob.setDescription(text[2]);
        android.support.p000v4.media.MediaDescriptionCompat.Builder iconBitmap = bob.setIconBitmap(icon);
        android.support.p000v4.media.MediaDescriptionCompat.Builder iconUri2 = bob.setIconUri(iconUri);
        android.support.p000v4.media.MediaDescriptionCompat.Builder mediaUri2 = bob.setMediaUri(mediaUri);
        if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            Bundle bundle = new Bundle();
            Bundle bundle2 = bundle;
            bundle.putLong(MediaDescriptionCompat.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE));
            android.support.p000v4.media.MediaDescriptionCompat.Builder extras = bob.setExtras(bundle2);
        }
        this.mDescription = bob.build();
        return this.mDescription;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        Parcel parcel2 = dest;
        int i2 = i;
        dest.writeBundle(this.mBundle);
    }

    public int size() {
        return this.mBundle.size();
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public static MediaMetadataCompat fromMediaMetadata(Object obj) {
        Object metadataObj = obj;
        Object obj2 = metadataObj;
        if (metadataObj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        Parcel p = Parcel.obtain();
        MediaMetadataCompatApi21.writeToParcel(metadataObj, p, 0);
        p.setDataPosition(0);
        MediaMetadataCompat metadata = (MediaMetadataCompat) CREATOR.createFromParcel(p);
        p.recycle();
        metadata.mMetadataObj = metadataObj;
        return metadata;
    }

    public Object getMediaMetadata() {
        if (this.mMetadataObj != null || VERSION.SDK_INT < 21) {
            return this.mMetadataObj;
        }
        Parcel p = Parcel.obtain();
        writeToParcel(p, 0);
        p.setDataPosition(0);
        this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel(p);
        p.recycle();
        return this.mMetadataObj;
    }
}
