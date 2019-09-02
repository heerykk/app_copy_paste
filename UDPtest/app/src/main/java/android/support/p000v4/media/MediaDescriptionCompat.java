package android.support.p000v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.TextUtils;

/* renamed from: android.support.v4.media.MediaDescriptionCompat */
public final class MediaDescriptionCompat implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final Creator<MediaDescriptionCompat> CREATOR = new Creator<MediaDescriptionCompat>() {
        public MediaDescriptionCompat createFromParcel(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            if (VERSION.SDK_INT >= 21) {
                return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(in));
            }
            return new MediaDescriptionCompat(in);
        }

        public MediaDescriptionCompat[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new MediaDescriptionCompat[size];
        }
    };
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    /* renamed from: android.support.v4.media.MediaDescriptionCompat$Builder */
    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public Builder() {
        }

        public Builder setMediaId(@Nullable String str) {
            String mediaId = str;
            String str2 = mediaId;
            this.mMediaId = mediaId;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mTitle = title;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            CharSequence subtitle = charSequence;
            CharSequence charSequence2 = subtitle;
            this.mSubtitle = subtitle;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            CharSequence description = charSequence;
            CharSequence charSequence2 = description;
            this.mDescription = description;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap bitmap) {
            Bitmap icon = bitmap;
            Bitmap bitmap2 = icon;
            this.mIcon = icon;
            return this;
        }

        public Builder setIconUri(@Nullable Uri uri) {
            Uri iconUri = uri;
            Uri uri2 = iconUri;
            this.mIconUri = iconUri;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            this.mExtras = extras;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            Uri mediaUri = uri;
            Uri uri2 = mediaUri;
            this.mMediaUri = mediaUri;
            return this;
        }

        public MediaDescriptionCompat build() {
            MediaDescriptionCompat mediaDescriptionCompat = new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
            return mediaDescriptionCompat;
        }
    }

    MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        String mediaId = str;
        CharSequence title = charSequence;
        CharSequence subtitle = charSequence2;
        CharSequence description = charSequence3;
        Bitmap icon = bitmap;
        Uri iconUri = uri;
        Bundle extras = bundle;
        Uri mediaUri = uri2;
        String str2 = mediaId;
        CharSequence charSequence4 = title;
        CharSequence charSequence5 = subtitle;
        CharSequence charSequence6 = description;
        Bitmap bitmap2 = icon;
        Uri uri3 = iconUri;
        Bundle bundle2 = extras;
        Uri uri4 = mediaUri;
        this.mMediaId = mediaId;
        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mDescription = description;
        this.mIcon = icon;
        this.mIconUri = iconUri;
        this.mExtras = extras;
        this.mMediaUri = mediaUri;
    }

    MediaDescriptionCompat(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        this.mMediaId = in.readString();
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mIcon = (Bitmap) in.readParcelable(null);
        this.mIconUri = (Uri) in.readParcelable(null);
        this.mExtras = in.readBundle();
        this.mMediaUri = (Uri) in.readParcelable(null);
    }

    @Nullable
    public String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Nullable
    public CharSequence getDescription() {
        return this.mDescription;
    }

    @Nullable
    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @Nullable
    public Uri getIconUri() {
        return this.mIconUri;
    }

    @Nullable
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        int flags = i;
        Parcel parcel2 = dest;
        int i2 = flags;
        if (VERSION.SDK_INT >= 21) {
            MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), dest, flags);
            return;
        }
        dest.writeString(this.mMediaId);
        TextUtils.writeToParcel(this.mTitle, dest, flags);
        TextUtils.writeToParcel(this.mSubtitle, dest, flags);
        TextUtils.writeToParcel(this.mDescription, dest, flags);
        dest.writeParcelable(this.mIcon, flags);
        dest.writeParcelable(this.mIconUri, flags);
        dest.writeBundle(this.mExtras);
        dest.writeParcelable(this.mMediaUri, flags);
    }

    public String toString() {
        return this.mTitle + ", " + this.mSubtitle + ", " + this.mDescription;
    }

    public Object getMediaDescription() {
        if (this.mDescriptionObj != null || VERSION.SDK_INT < 21) {
            return this.mDescriptionObj;
        }
        Object newInstance = Builder.newInstance();
        Object bob = newInstance;
        Builder.setMediaId(newInstance, this.mMediaId);
        Builder.setTitle(bob, this.mTitle);
        Builder.setSubtitle(bob, this.mSubtitle);
        Builder.setDescription(bob, this.mDescription);
        Builder.setIconBitmap(bob, this.mIcon);
        Builder.setIconUri(bob, this.mIconUri);
        Bundle extras = this.mExtras;
        if (VERSION.SDK_INT < 23 && this.mMediaUri != null) {
            if (extras == null) {
                Bundle bundle = new Bundle();
                extras = bundle;
                bundle.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
            }
            extras.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.mMediaUri);
        }
        Builder.setExtras(bob, extras);
        if (VERSION.SDK_INT >= 23) {
            Builder.setMediaUri(bob, this.mMediaUri);
        }
        this.mDescriptionObj = Builder.build(bob);
        return this.mDescriptionObj;
    }

    public static MediaDescriptionCompat fromMediaDescription(Object obj) {
        Uri uri;
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        if (descriptionObj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        Builder builder = new Builder();
        Builder bob = builder;
        Builder mediaId = builder.setMediaId(MediaDescriptionCompatApi21.getMediaId(descriptionObj));
        Builder title = bob.setTitle(MediaDescriptionCompatApi21.getTitle(descriptionObj));
        Builder subtitle = bob.setSubtitle(MediaDescriptionCompatApi21.getSubtitle(descriptionObj));
        Builder description = bob.setDescription(MediaDescriptionCompatApi21.getDescription(descriptionObj));
        Builder iconBitmap = bob.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(descriptionObj));
        Builder iconUri = bob.setIconUri(MediaDescriptionCompatApi21.getIconUri(descriptionObj));
        Bundle extras = MediaDescriptionCompatApi21.getExtras(descriptionObj);
        Bundle extras2 = extras;
        if (extras != null) {
            uri = (Uri) extras2.getParcelable(DESCRIPTION_KEY_MEDIA_URI);
        } else {
            uri = null;
        }
        Uri mediaUri = uri;
        if (mediaUri != null) {
            if (extras2.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) && extras2.size() == 2) {
                extras2 = null;
            } else {
                extras2.remove(DESCRIPTION_KEY_MEDIA_URI);
                extras2.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG);
            }
        }
        Builder extras3 = bob.setExtras(extras2);
        if (mediaUri != null) {
            Builder mediaUri2 = bob.setMediaUri(mediaUri);
        } else if (VERSION.SDK_INT >= 23) {
            Builder mediaUri3 = bob.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(descriptionObj));
        }
        MediaDescriptionCompat build = bob.build();
        MediaDescriptionCompat descriptionCompat = build;
        build.mDescriptionObj = descriptionObj;
        return descriptionCompat;
    }
}
