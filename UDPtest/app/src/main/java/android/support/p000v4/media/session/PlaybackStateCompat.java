package android.support.p000v4.media.session;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.media.session.PlaybackStateCompat */
public final class PlaybackStateCompat implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_PREPARE = 16384;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536;
    public static final long ACTION_PREPARE_FROM_URI = 131072;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final Creator<PlaybackStateCompat> CREATOR = new Creator<PlaybackStateCompat>() {
        public PlaybackStateCompat createFromParcel(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            return new PlaybackStateCompat(in);
        }

        public PlaybackStateCompat[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new PlaybackStateCompat[size];
        }
    };
    private static final int KEYCODE_MEDIA_PAUSE = 127;
    private static final int KEYCODE_MEDIA_PLAY = 126;
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    final long mActions;
    final long mActiveItemId;
    final long mBufferedPosition;
    List<CustomAction> mCustomActions;
    final CharSequence mErrorMessage;
    final Bundle mExtras;
    final long mPosition;
    final float mSpeed;
    final int mState;
    private Object mStateObj;
    final long mUpdateTime;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.session.PlaybackStateCompat$Actions */
    public @interface Actions {
    }

    /* renamed from: android.support.v4.media.session.PlaybackStateCompat$Builder */
    public static final class Builder {
        private long mActions;
        private long mActiveItemId = -1;
        private long mBufferedPosition;
        private final List<CustomAction> mCustomActions = new ArrayList();
        private CharSequence mErrorMessage;
        private Bundle mExtras;
        private long mPosition;
        private float mRate;
        private int mState;
        private long mUpdateTime;

        public Builder() {
        }

        public Builder(PlaybackStateCompat playbackStateCompat) {
            PlaybackStateCompat source = playbackStateCompat;
            PlaybackStateCompat playbackStateCompat2 = source;
            this.mState = source.mState;
            this.mPosition = source.mPosition;
            this.mRate = source.mSpeed;
            this.mUpdateTime = source.mUpdateTime;
            this.mBufferedPosition = source.mBufferedPosition;
            this.mActions = source.mActions;
            this.mErrorMessage = source.mErrorMessage;
            if (source.mCustomActions != null) {
                boolean addAll = this.mCustomActions.addAll(source.mCustomActions);
            }
            this.mActiveItemId = source.mActiveItemId;
            this.mExtras = source.mExtras;
        }

        public Builder setState(int i, long j, float f) {
            int state = i;
            long position = j;
            float playbackSpeed = f;
            int i2 = state;
            long j2 = position;
            float f2 = playbackSpeed;
            return setState(state, position, playbackSpeed, SystemClock.elapsedRealtime());
        }

        public Builder setState(int i, long j, float f, long j2) {
            int state = i;
            long position = j;
            float playbackSpeed = f;
            long updateTime = j2;
            int i2 = state;
            long j3 = position;
            float f2 = playbackSpeed;
            long j4 = updateTime;
            this.mState = state;
            this.mPosition = position;
            this.mUpdateTime = updateTime;
            this.mRate = playbackSpeed;
            return this;
        }

        public Builder setBufferedPosition(long j) {
            long bufferPosition = j;
            long j2 = bufferPosition;
            this.mBufferedPosition = bufferPosition;
            return this;
        }

        public Builder setActions(long j) {
            long capabilities = j;
            long j2 = capabilities;
            this.mActions = capabilities;
            return this;
        }

        public Builder addCustomAction(String str, String str2, int i) {
            String action = str;
            String name = str2;
            int icon = i;
            String str3 = action;
            String str4 = name;
            int i2 = icon;
            return addCustomAction(new CustomAction(action, name, icon, null));
        }

        public Builder addCustomAction(CustomAction customAction) {
            CustomAction customAction2 = customAction;
            CustomAction customAction3 = customAction2;
            if (customAction2 != null) {
                boolean add = this.mCustomActions.add(customAction2);
                return this;
            }
            throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.");
        }

        public Builder setActiveQueueItemId(long j) {
            long id = j;
            long j2 = id;
            this.mActiveItemId = id;
            return this;
        }

        public Builder setErrorMessage(CharSequence charSequence) {
            CharSequence errorMessage = charSequence;
            CharSequence charSequence2 = errorMessage;
            this.mErrorMessage = errorMessage;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            this.mExtras = extras;
            return this;
        }

        public PlaybackStateCompat build() {
            PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(this.mState, this.mPosition, this.mBufferedPosition, this.mRate, this.mActions, this.mErrorMessage, this.mUpdateTime, this.mCustomActions, this.mActiveItemId, this.mExtras);
            return playbackStateCompat;
        }
    }

    /* renamed from: android.support.v4.media.session.PlaybackStateCompat$CustomAction */
    public static final class CustomAction implements Parcelable {
        public static final Creator<CustomAction> CREATOR = new Creator<CustomAction>() {
            public CustomAction createFromParcel(Parcel parcel) {
                Parcel p = parcel;
                Parcel parcel2 = p;
                return new CustomAction(p);
            }

            public CustomAction[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new CustomAction[size];
            }
        };
        private final String mAction;
        private Object mCustomActionObj;
        private final Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        /* renamed from: android.support.v4.media.session.PlaybackStateCompat$CustomAction$Builder */
        public static final class Builder {
            private final String mAction;
            private Bundle mExtras;
            private final int mIcon;
            private final CharSequence mName;

            public Builder(String str, CharSequence charSequence, int i) {
                String action = str;
                CharSequence name = charSequence;
                int icon = i;
                String str2 = action;
                CharSequence charSequence2 = name;
                int i2 = icon;
                if (TextUtils.isEmpty(action)) {
                    throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
                } else if (TextUtils.isEmpty(name)) {
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
                } else if (icon != 0) {
                    this.mAction = action;
                    this.mName = name;
                    this.mIcon = icon;
                } else {
                    throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                }
            }

            public Builder setExtras(Bundle bundle) {
                Bundle extras = bundle;
                Bundle bundle2 = extras;
                this.mExtras = extras;
                return this;
            }

            public CustomAction build() {
                String str = this.mAction;
                return new CustomAction(str, this.mName, this.mIcon, this.mExtras);
            }
        }

        CustomAction(String str, CharSequence charSequence, int i, Bundle bundle) {
            String action = str;
            CharSequence name = charSequence;
            int icon = i;
            Bundle extras = bundle;
            String str2 = action;
            CharSequence charSequence2 = name;
            int i2 = icon;
            Bundle bundle2 = extras;
            this.mAction = action;
            this.mName = name;
            this.mIcon = icon;
            this.mExtras = extras;
        }

        CustomAction(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.mAction = in.readString();
            this.mName = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.mIcon = in.readInt();
            this.mExtras = in.readBundle();
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            dest.writeString(this.mAction);
            TextUtils.writeToParcel(this.mName, dest, flags);
            dest.writeInt(this.mIcon);
            dest.writeBundle(this.mExtras);
        }

        public int describeContents() {
            return 0;
        }

        public static CustomAction fromCustomAction(Object obj) {
            Object customActionObj = obj;
            Object obj2 = customActionObj;
            if (customActionObj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            String action = CustomAction.getAction(customActionObj);
            CustomAction customAction = new CustomAction(action, CustomAction.getName(customActionObj), CustomAction.getIcon(customActionObj), CustomAction.getExtras(customActionObj));
            CustomAction customAction2 = customAction;
            customAction.mCustomActionObj = customActionObj;
            return customAction2;
        }

        public Object getCustomAction() {
            if (this.mCustomActionObj != null || VERSION.SDK_INT < 21) {
                return this.mCustomActionObj;
            }
            String str = this.mAction;
            this.mCustomActionObj = CustomAction.newInstance(str, this.mName, this.mIcon, this.mExtras);
            return this.mCustomActionObj;
        }

        public String getAction() {
            return this.mAction;
        }

        public CharSequence getName() {
            return this.mName;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String toString() {
            return "Action:mName='" + this.mName + ", mIcon=" + this.mIcon + ", mExtras=" + this.mExtras;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.session.PlaybackStateCompat$MediaKeyAction */
    public @interface MediaKeyAction {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.session.PlaybackStateCompat$State */
    public @interface State {
    }

    public static int toKeyCode(long j) {
        long action = j;
        long j2 = action;
        if (action == 4) {
            return 126;
        }
        if (action == 2) {
            return 127;
        }
        if (action == 32) {
            return 87;
        }
        if (action == 16) {
            return 88;
        }
        if (action == 1) {
            return 86;
        }
        if (action == 64) {
            return 90;
        }
        if (action == 8) {
            return 89;
        }
        if (action == 512) {
            return 85;
        }
        return 0;
    }

    PlaybackStateCompat(int i, long j, long j2, float f, long j3, CharSequence charSequence, long j4, List<CustomAction> list, long j5, Bundle bundle) {
        int state = i;
        long position = j;
        long bufferedPosition = j2;
        float rate = f;
        long actions = j3;
        CharSequence errorMessage = charSequence;
        long updateTime = j4;
        List<CustomAction> customActions = list;
        long activeItemId = j5;
        Bundle extras = bundle;
        int i2 = state;
        long j6 = position;
        long j7 = bufferedPosition;
        float f2 = rate;
        long j8 = actions;
        CharSequence charSequence2 = errorMessage;
        long j9 = updateTime;
        List<CustomAction> list2 = customActions;
        long j10 = activeItemId;
        Bundle bundle2 = extras;
        this.mState = state;
        this.mPosition = position;
        this.mBufferedPosition = bufferedPosition;
        this.mSpeed = rate;
        this.mActions = actions;
        this.mErrorMessage = errorMessage;
        this.mUpdateTime = updateTime;
        this.mCustomActions = new ArrayList(customActions);
        this.mActiveItemId = activeItemId;
        this.mExtras = extras;
    }

    PlaybackStateCompat(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        this.mState = in.readInt();
        this.mPosition = in.readLong();
        this.mSpeed = in.readFloat();
        this.mUpdateTime = in.readLong();
        this.mBufferedPosition = in.readLong();
        this.mActions = in.readLong();
        this.mErrorMessage = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mCustomActions = in.createTypedArrayList(CustomAction.CREATOR);
        this.mActiveItemId = in.readLong();
        this.mExtras = in.readBundle();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PlaybackState {");
        StringBuilder bob = sb;
        StringBuilder append = sb.append("state=").append(this.mState);
        StringBuilder append2 = bob.append(", position=").append(this.mPosition);
        StringBuilder append3 = bob.append(", buffered position=").append(this.mBufferedPosition);
        StringBuilder append4 = bob.append(", speed=").append(this.mSpeed);
        StringBuilder append5 = bob.append(", updated=").append(this.mUpdateTime);
        StringBuilder append6 = bob.append(", actions=").append(this.mActions);
        StringBuilder append7 = bob.append(", error=").append(this.mErrorMessage);
        StringBuilder append8 = bob.append(", custom actions=").append(this.mCustomActions);
        StringBuilder append9 = bob.append(", active item id=").append(this.mActiveItemId);
        StringBuilder append10 = bob.append("}");
        return bob.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        int flags = i;
        Parcel parcel2 = dest;
        int i2 = flags;
        dest.writeInt(this.mState);
        dest.writeLong(this.mPosition);
        dest.writeFloat(this.mSpeed);
        dest.writeLong(this.mUpdateTime);
        dest.writeLong(this.mBufferedPosition);
        dest.writeLong(this.mActions);
        TextUtils.writeToParcel(this.mErrorMessage, dest, flags);
        dest.writeTypedList(this.mCustomActions);
        dest.writeLong(this.mActiveItemId);
        dest.writeBundle(this.mExtras);
    }

    public int getState() {
        return this.mState;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public long getBufferedPosition() {
        return this.mBufferedPosition;
    }

    public float getPlaybackSpeed() {
        return this.mSpeed;
    }

    public long getActions() {
        return this.mActions;
    }

    public List<CustomAction> getCustomActions() {
        return this.mCustomActions;
    }

    public CharSequence getErrorMessage() {
        return this.mErrorMessage;
    }

    public long getLastPositionUpdateTime() {
        return this.mUpdateTime;
    }

    public long getActiveQueueItemId() {
        return this.mActiveItemId;
    }

    @Nullable
    public Bundle getExtras() {
        return this.mExtras;
    }

    public static PlaybackStateCompat fromPlaybackState(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        if (stateObj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        List<Object> customActions = PlaybackStateCompatApi21.getCustomActions(stateObj);
        ArrayList arrayList = null;
        if (customActions != null) {
            ArrayList arrayList2 = new ArrayList(customActions.size());
            arrayList = arrayList2;
            for (Object fromCustomAction : customActions) {
                boolean add = arrayList.add(CustomAction.fromCustomAction(fromCustomAction));
            }
        }
        PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(PlaybackStateCompatApi21.getState(stateObj), PlaybackStateCompatApi21.getPosition(stateObj), PlaybackStateCompatApi21.getBufferedPosition(stateObj), PlaybackStateCompatApi21.getPlaybackSpeed(stateObj), PlaybackStateCompatApi21.getActions(stateObj), PlaybackStateCompatApi21.getErrorMessage(stateObj), PlaybackStateCompatApi21.getLastPositionUpdateTime(stateObj), arrayList, PlaybackStateCompatApi21.getActiveQueueItemId(stateObj), VERSION.SDK_INT < 22 ? null : PlaybackStateCompatApi22.getExtras(stateObj));
        PlaybackStateCompat state = playbackStateCompat;
        playbackStateCompat.mStateObj = stateObj;
        return state;
    }

    public Object getPlaybackState() {
        if (this.mStateObj != null || VERSION.SDK_INT < 21) {
            return this.mStateObj;
        }
        ArrayList arrayList = null;
        if (this.mCustomActions != null) {
            ArrayList arrayList2 = new ArrayList(this.mCustomActions.size());
            arrayList = arrayList2;
            for (CustomAction customAction : this.mCustomActions) {
                boolean add = arrayList.add(customAction.getCustomAction());
            }
        }
        if (VERSION.SDK_INT < 22) {
            this.mStateObj = PlaybackStateCompatApi21.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, arrayList, this.mActiveItemId);
        } else {
            this.mStateObj = PlaybackStateCompatApi22.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, arrayList, this.mActiveItemId, this.mExtras);
        }
        return this.mStateObj;
    }
}
