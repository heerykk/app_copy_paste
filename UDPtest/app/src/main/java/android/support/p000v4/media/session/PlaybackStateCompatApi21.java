package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.media.session.PlaybackState;
import android.media.session.PlaybackState.CustomAction.Builder;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import java.util.Iterator;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.session.PlaybackStateCompatApi21 */
class PlaybackStateCompatApi21 {

    /* renamed from: android.support.v4.media.session.PlaybackStateCompatApi21$CustomAction */
    static final class CustomAction {
        CustomAction() {
        }

        public static String getAction(Object obj) {
            Object customActionObj = obj;
            Object obj2 = customActionObj;
            return ((android.media.session.PlaybackState.CustomAction) customActionObj).getAction();
        }

        public static CharSequence getName(Object obj) {
            Object customActionObj = obj;
            Object obj2 = customActionObj;
            return ((android.media.session.PlaybackState.CustomAction) customActionObj).getName();
        }

        public static int getIcon(Object obj) {
            Object customActionObj = obj;
            Object obj2 = customActionObj;
            return ((android.media.session.PlaybackState.CustomAction) customActionObj).getIcon();
        }

        public static Bundle getExtras(Object obj) {
            Object customActionObj = obj;
            Object obj2 = customActionObj;
            return ((android.media.session.PlaybackState.CustomAction) customActionObj).getExtras();
        }

        public static Object newInstance(String str, CharSequence charSequence, int i, Bundle bundle) {
            String action = str;
            CharSequence name = charSequence;
            int icon = i;
            Bundle extras = bundle;
            String str2 = action;
            CharSequence charSequence2 = name;
            int i2 = icon;
            Bundle bundle2 = extras;
            Builder builder = new Builder(action, name, icon);
            Builder customActionObj = builder;
            Builder extras2 = builder.setExtras(extras);
            return customActionObj.build();
        }
    }

    PlaybackStateCompatApi21() {
    }

    public static int getState(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getState();
    }

    public static long getPosition(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getPosition();
    }

    public static long getBufferedPosition(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getBufferedPosition();
    }

    public static float getPlaybackSpeed(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getPlaybackSpeed();
    }

    public static long getActions(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getActions();
    }

    public static CharSequence getErrorMessage(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getErrorMessage();
    }

    public static long getLastPositionUpdateTime(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getLastPositionUpdateTime();
    }

    public static List<Object> getCustomActions(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getCustomActions();
    }

    public static long getActiveQueueItemId(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getActiveQueueItemId();
    }

    public static Object newInstance(int i, long j, long j2, float f, long j3, CharSequence charSequence, long j4, List<Object> list, long j5) {
        int state = i;
        long position = j;
        long bufferedPosition = j2;
        float speed = f;
        long actions = j3;
        CharSequence errorMessage = charSequence;
        long updateTime = j4;
        List<Object> customActions = list;
        long activeItemId = j5;
        int i2 = state;
        long j6 = position;
        long j7 = bufferedPosition;
        float f2 = speed;
        long j8 = actions;
        CharSequence charSequence2 = errorMessage;
        long j9 = updateTime;
        List<Object> list2 = customActions;
        long j10 = activeItemId;
        PlaybackState.Builder builder = new PlaybackState.Builder();
        PlaybackState.Builder stateObj = builder;
        PlaybackState.Builder state2 = builder.setState(state, position, speed, updateTime);
        PlaybackState.Builder bufferedPosition2 = stateObj.setBufferedPosition(bufferedPosition);
        PlaybackState.Builder actions2 = stateObj.setActions(actions);
        PlaybackState.Builder errorMessage2 = stateObj.setErrorMessage(errorMessage);
        Iterator it = customActions.iterator();
        while (it.hasNext()) {
            PlaybackState.Builder addCustomAction = stateObj.addCustomAction((android.media.session.PlaybackState.CustomAction) it.next());
        }
        PlaybackState.Builder activeQueueItemId = stateObj.setActiveQueueItemId(activeItemId);
        return stateObj.build();
    }
}
