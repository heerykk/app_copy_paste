package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.media.session.PlaybackState;
import android.media.session.PlaybackState.Builder;
import android.media.session.PlaybackState.CustomAction;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import java.util.Iterator;
import java.util.List;

@TargetApi(22)
@RequiresApi(22)
/* renamed from: android.support.v4.media.session.PlaybackStateCompatApi22 */
class PlaybackStateCompatApi22 {
    PlaybackStateCompatApi22() {
    }

    public static Bundle getExtras(Object obj) {
        Object stateObj = obj;
        Object obj2 = stateObj;
        return ((PlaybackState) stateObj).getExtras();
    }

    public static Object newInstance(int i, long j, long j2, float f, long j3, CharSequence charSequence, long j4, List<Object> list, long j5, Bundle bundle) {
        int state = i;
        long position = j;
        long bufferedPosition = j2;
        float speed = f;
        long actions = j3;
        CharSequence errorMessage = charSequence;
        long updateTime = j4;
        List<Object> customActions = list;
        long activeItemId = j5;
        Bundle extras = bundle;
        int i2 = state;
        long j6 = position;
        long j7 = bufferedPosition;
        float f2 = speed;
        long j8 = actions;
        CharSequence charSequence2 = errorMessage;
        long j9 = updateTime;
        List<Object> list2 = customActions;
        long j10 = activeItemId;
        Bundle bundle2 = extras;
        Builder builder = new Builder();
        Builder stateObj = builder;
        Builder state2 = builder.setState(state, position, speed, updateTime);
        Builder bufferedPosition2 = stateObj.setBufferedPosition(bufferedPosition);
        Builder actions2 = stateObj.setActions(actions);
        Builder errorMessage2 = stateObj.setErrorMessage(errorMessage);
        Iterator it = customActions.iterator();
        while (it.hasNext()) {
            Builder addCustomAction = stateObj.addCustomAction((CustomAction) it.next());
        }
        Builder activeQueueItemId = stateObj.setActiveQueueItemId(activeItemId);
        Builder extras2 = stateObj.setExtras(extras);
        return stateObj.build();
    }
}
