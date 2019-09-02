package android.support.p000v4.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: android.support.v4.app.TaskStackBuilder */
public final class TaskStackBuilder implements Iterable<Intent> {
    private static final TaskStackBuilderImpl IMPL;
    private static final String TAG = "TaskStackBuilder";
    private final ArrayList<Intent> mIntents = new ArrayList<>();
    private final Context mSourceContext;

    /* renamed from: android.support.v4.app.TaskStackBuilder$SupportParentable */
    public interface SupportParentable {
        Intent getSupportParentActivityIntent();
    }

    /* renamed from: android.support.v4.app.TaskStackBuilder$TaskStackBuilderImpl */
    interface TaskStackBuilderImpl {
        PendingIntent getPendingIntent(Context context, Intent[] intentArr, int i, int i2, Bundle bundle);
    }

    /* renamed from: android.support.v4.app.TaskStackBuilder$TaskStackBuilderImplBase */
    static class TaskStackBuilderImplBase implements TaskStackBuilderImpl {
        TaskStackBuilderImplBase() {
        }

        public PendingIntent getPendingIntent(Context context, Intent[] intentArr, int i, int i2, Bundle bundle) {
            Context context2 = context;
            Intent[] intents = intentArr;
            int requestCode = i;
            int flags = i2;
            Context context3 = context2;
            Intent[] intentArr2 = intents;
            int i3 = requestCode;
            int i4 = flags;
            Bundle bundle2 = bundle;
            Intent intent = new Intent(intents[intents.length - 1]);
            Intent topIntent = intent;
            Intent addFlags = intent.addFlags(268435456);
            return PendingIntent.getActivity(context2, requestCode, topIntent, flags);
        }
    }

    /* renamed from: android.support.v4.app.TaskStackBuilder$TaskStackBuilderImplHoneycomb */
    static class TaskStackBuilderImplHoneycomb implements TaskStackBuilderImpl {
        TaskStackBuilderImplHoneycomb() {
        }

        public PendingIntent getPendingIntent(Context context, Intent[] intentArr, int i, int i2, Bundle bundle) {
            Context context2 = context;
            Intent[] intents = intentArr;
            int requestCode = i;
            int flags = i2;
            Context context3 = context2;
            Intent[] intentArr2 = intents;
            int i3 = requestCode;
            int i4 = flags;
            Bundle bundle2 = bundle;
            intents[0] = new Intent(intents[0]).addFlags(268484608);
            return TaskStackBuilderHoneycomb.getActivitiesPendingIntent(context2, requestCode, intents, flags);
        }
    }

    /* renamed from: android.support.v4.app.TaskStackBuilder$TaskStackBuilderImplJellybean */
    static class TaskStackBuilderImplJellybean implements TaskStackBuilderImpl {
        TaskStackBuilderImplJellybean() {
        }

        public PendingIntent getPendingIntent(Context context, Intent[] intentArr, int i, int i2, Bundle bundle) {
            Context context2 = context;
            Intent[] intents = intentArr;
            int requestCode = i;
            int flags = i2;
            Bundle options = bundle;
            Context context3 = context2;
            Intent[] intentArr2 = intents;
            int i3 = requestCode;
            int i4 = flags;
            Bundle bundle2 = options;
            Intent intent = new Intent(intents[0]);
            intents[0] = intent.addFlags(268484608);
            return TaskStackBuilderJellybean.getActivitiesPendingIntent(context2, requestCode, intents, flags, options);
        }
    }

    static {
        if (VERSION.SDK_INT < 11) {
            IMPL = new TaskStackBuilderImplBase();
        } else {
            IMPL = new TaskStackBuilderImplHoneycomb();
        }
    }

    private TaskStackBuilder(Context context) {
        Context a = context;
        Context context2 = a;
        this.mSourceContext = a;
    }

    public static TaskStackBuilder create(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return new TaskStackBuilder(context2);
    }

    @Deprecated
    public static TaskStackBuilder from(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return create(context2);
    }

    public TaskStackBuilder addNextIntent(Intent intent) {
        Intent nextIntent = intent;
        Intent intent2 = nextIntent;
        boolean add = this.mIntents.add(nextIntent);
        return this;
    }

    public TaskStackBuilder addNextIntentWithParentStack(Intent intent) {
        Intent nextIntent = intent;
        Intent intent2 = nextIntent;
        ComponentName component = nextIntent.getComponent();
        ComponentName target = component;
        if (component == null) {
            target = nextIntent.resolveActivity(this.mSourceContext.getPackageManager());
        }
        if (target != null) {
            TaskStackBuilder addParentStack = addParentStack(target);
        }
        TaskStackBuilder addNextIntent = addNextIntent(nextIntent);
        return this;
    }

    public TaskStackBuilder addParentStack(Activity activity) {
        Activity sourceActivity = activity;
        Activity activity2 = sourceActivity;
        Intent parent = null;
        if (sourceActivity instanceof SupportParentable) {
            parent = ((SupportParentable) sourceActivity).getSupportParentActivityIntent();
        }
        if (parent == null) {
            parent = NavUtils.getParentActivityIntent(sourceActivity);
        }
        if (parent != null) {
            ComponentName component = parent.getComponent();
            ComponentName target = component;
            if (component == null) {
                target = parent.resolveActivity(this.mSourceContext.getPackageManager());
            }
            TaskStackBuilder addParentStack = addParentStack(target);
            TaskStackBuilder addNextIntent = addNextIntent(parent);
        }
        return this;
    }

    public TaskStackBuilder addParentStack(Class<?> cls) {
        Class<?> sourceActivityClass = cls;
        Class<?> cls2 = sourceActivityClass;
        return addParentStack(new ComponentName(this.mSourceContext, sourceActivityClass));
    }

    public TaskStackBuilder addParentStack(ComponentName componentName) {
        ComponentName sourceActivityName = componentName;
        ComponentName componentName2 = sourceActivityName;
        int size = this.mIntents.size();
        int i = size;
        try {
            Intent parentActivityIntent = NavUtils.getParentActivityIntent(this.mSourceContext, sourceActivityName);
            while (true) {
                Intent parent = parentActivityIntent;
                if (parent == null) {
                    return this;
                }
                this.mIntents.add(size, parent);
                parentActivityIntent = NavUtils.getParentActivityIntent(this.mSourceContext, parent.getComponent());
            }
        } catch (NameNotFoundException e) {
            NameNotFoundException nameNotFoundException = e;
            int e2 = Log.e(TAG, "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    public int getIntentCount() {
        return this.mIntents.size();
    }

    @Deprecated
    public Intent getIntent(int i) {
        int index = i;
        int i2 = index;
        return editIntentAt(index);
    }

    public Intent editIntentAt(int i) {
        int index = i;
        int i2 = index;
        return (Intent) this.mIntents.get(index);
    }

    @Deprecated
    public Iterator<Intent> iterator() {
        return this.mIntents.iterator();
    }

    public void startActivities() {
        startActivities(null);
    }

    public void startActivities(Bundle bundle) {
        Bundle options = bundle;
        Bundle bundle2 = options;
        if (!this.mIntents.isEmpty()) {
            Intent[] intentArr = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
            Intent[] intents = intentArr;
            intentArr[0] = new Intent(intents[0]).addFlags(268484608);
            if (!ContextCompat.startActivities(this.mSourceContext, intents, options)) {
                Intent intent = new Intent(intents[intents.length - 1]);
                Intent topIntent = intent;
                Intent addFlags = intent.addFlags(268435456);
                this.mSourceContext.startActivity(topIntent);
                return;
            }
            return;
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }

    public PendingIntent getPendingIntent(int i, int i2) {
        int requestCode = i;
        int flags = i2;
        int i3 = requestCode;
        int i4 = flags;
        return getPendingIntent(requestCode, flags, null);
    }

    public PendingIntent getPendingIntent(int i, int i2, Bundle bundle) {
        int requestCode = i;
        int flags = i2;
        Bundle options = bundle;
        int i3 = requestCode;
        int i4 = flags;
        Bundle bundle2 = options;
        if (!this.mIntents.isEmpty()) {
            Intent[] intentArr = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
            Intent[] intents = intentArr;
            Intent[] intentArr2 = intentArr;
            Intent intent = new Intent(intents[0]);
            intentArr2[0] = intent.addFlags(268484608);
            return IMPL.getPendingIntent(this.mSourceContext, intents, requestCode, flags, options);
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
    }

    public Intent[] getIntents() {
        Intent[] intentArr = new Intent[this.mIntents.size()];
        Intent[] intents = intentArr;
        if (intentArr.length == 0) {
            return intents;
        }
        intents[0] = new Intent((Intent) this.mIntents.get(0)).addFlags(268484608);
        for (int i = 1; i < intents.length; i++) {
            intents[i] = new Intent((Intent) this.mIntents.get(i));
        }
        return intents;
    }
}
