package android.support.p000v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* renamed from: android.support.v4.content.LocalBroadcastManager */
public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();

    /* renamed from: android.support.v4.content.LocalBroadcastManager$BroadcastRecord */
    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent2, ArrayList<ReceiverRecord> arrayList) {
            Intent _intent = intent2;
            ArrayList<ReceiverRecord> _receivers = arrayList;
            Intent intent3 = _intent;
            ArrayList<ReceiverRecord> arrayList2 = _receivers;
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    /* renamed from: android.support.v4.content.LocalBroadcastManager$ReceiverRecord */
    private static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            IntentFilter _filter = intentFilter;
            BroadcastReceiver _receiver = broadcastReceiver;
            IntentFilter intentFilter2 = _filter;
            BroadcastReceiver broadcastReceiver2 = _receiver;
            this.filter = _filter;
            this.receiver = _receiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            StringBuilder builder = sb;
            StringBuilder append = sb.append("Receiver{");
            StringBuilder append2 = builder.append(this.receiver);
            StringBuilder append3 = builder.append(" filter=");
            StringBuilder append4 = builder.append(this.filter);
            StringBuilder append5 = builder.append("}");
            return builder.toString();
        }
    }

    static /* synthetic */ void access$000(LocalBroadcastManager localBroadcastManager) {
        LocalBroadcastManager x0 = localBroadcastManager;
        LocalBroadcastManager localBroadcastManager2 = x0;
        x0.executePendingBroadcasts();
    }

    private LocalBroadcastManager(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mAppContext = context2;
        this.mHandler = new Handler(this, context2.getMainLooper()) {
            final /* synthetic */ LocalBroadcastManager this$0;

            {
                LocalBroadcastManager this$02 = r8;
                Looper x0 = r9;
                LocalBroadcastManager localBroadcastManager = this$02;
                Looper looper = x0;
                this.this$0 = this$02;
            }

            public void handleMessage(Message message) {
                Message msg = message;
                Message message2 = msg;
                switch (msg.what) {
                    case 1:
                        LocalBroadcastManager.access$000(this.this$0);
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        };
    }

    public static LocalBroadcastManager getInstance(Context context) {
        Context context2 = context;
        Context context3 = context2;
        Object obj = mLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (mInstance == null) {
                    mInstance = new LocalBroadcastManager(context2.getApplicationContext());
                }
                LocalBroadcastManager localBroadcastManager = mInstance;
                return localBroadcastManager;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        BroadcastReceiver receiver = broadcastReceiver;
        IntentFilter filter = intentFilter;
        BroadcastReceiver broadcastReceiver2 = receiver;
        IntentFilter intentFilter2 = filter;
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
        int i = hashMap;
        synchronized (hashMap) {
            try {
                ReceiverRecord entry = new ReceiverRecord(filter, receiver);
                ArrayList arrayList = (ArrayList) this.mReceivers.get(receiver);
                ArrayList arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList(1);
                    Object put = this.mReceivers.put(receiver, arrayList2);
                }
                boolean add = arrayList2.add(filter);
                for (int i2 = 0; i2 < filter.countActions(); i2++) {
                    String action = filter.getAction(i2);
                    ArrayList arrayList3 = (ArrayList) this.mActions.get(action);
                    ArrayList arrayList4 = arrayList3;
                    if (arrayList3 == null) {
                        arrayList4 = new ArrayList(1);
                        Object put2 = this.mActions.put(action, arrayList4);
                    }
                    boolean add2 = arrayList4.add(entry);
                }
            } finally {
                int i3 = i;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        BroadcastReceiver receiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = receiver;
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap2 = hashMap;
        synchronized (hashMap) {
            try {
                ArrayList arrayList = (ArrayList) this.mReceivers.remove(receiver);
                ArrayList arrayList2 = arrayList;
                if (arrayList != null) {
                    for (int i = 0; i < arrayList2.size(); i++) {
                        IntentFilter filter = (IntentFilter) arrayList2.get(i);
                        for (int j = 0; j < filter.countActions(); j++) {
                            String action = filter.getAction(j);
                            ArrayList arrayList3 = (ArrayList) this.mActions.get(action);
                            ArrayList arrayList4 = arrayList3;
                            if (arrayList3 != null) {
                                int k = 0;
                                while (k < arrayList4.size()) {
                                    if (((ReceiverRecord) arrayList4.get(k)).receiver == receiver) {
                                        Object remove = arrayList4.remove(k);
                                        k--;
                                    }
                                    k++;
                                }
                                if (arrayList4.size() <= 0) {
                                    Object remove2 = this.mActions.remove(action);
                                }
                            }
                        }
                    }
                    return;
                }
            } catch (Throwable th) {
                HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap3 = hashMap2;
                throw th;
            }
        }
    }

    public boolean sendBroadcast(Intent intent) {
        String reason;
        Intent intent2 = intent;
        Intent intent3 = intent2;
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
        boolean z = hashMap;
        synchronized (hashMap) {
            try {
                String action = intent2.getAction();
                String type = intent2.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                Uri data = intent2.getData();
                String scheme = intent2.getScheme();
                Set categories = intent2.getCategories();
                boolean debug = (intent2.getFlags() & 8) != 0;
                if (debug) {
                    int v = Log.v(TAG, "Resolving type " + type + " scheme " + scheme + " of intent " + intent2);
                }
                ArrayList arrayList = (ArrayList) this.mActions.get(intent2.getAction());
                ArrayList arrayList2 = arrayList;
                if (arrayList != null) {
                    if (debug) {
                        int v2 = Log.v(TAG, "Action list: " + arrayList2);
                    }
                    ArrayList arrayList3 = null;
                    for (int i = 0; i < arrayList2.size(); i++) {
                        ReceiverRecord receiver = (ReceiverRecord) arrayList2.get(i);
                        if (debug) {
                            int v3 = Log.v(TAG, "Matching against filter " + receiver.filter);
                        }
                        if (!receiver.broadcasting) {
                            int match = receiver.filter.match(action, type, scheme, data, categories, TAG);
                            int match2 = match;
                            if (match >= 0) {
                                if (debug) {
                                    int v4 = Log.v(TAG, "  Filter matched!  match=0x" + Integer.toHexString(match2));
                                }
                                if (arrayList3 == null) {
                                    arrayList3 = new ArrayList();
                                }
                                boolean add = arrayList3.add(receiver);
                                receiver.broadcasting = true;
                            } else if (debug) {
                                switch (match2) {
                                    case -4:
                                        reason = "category";
                                        break;
                                    case -3:
                                        reason = "action";
                                        break;
                                    case -2:
                                        reason = "data";
                                        break;
                                    case -1:
                                        reason = "type";
                                        break;
                                    default:
                                        reason = "unknown reason";
                                        break;
                                }
                                int v5 = Log.v(TAG, "  Filter did not match: " + reason);
                            }
                        } else if (debug) {
                            int v6 = Log.v(TAG, "  Filter's target already added");
                        }
                    }
                    if (arrayList3 != null) {
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            ((ReceiverRecord) arrayList3.get(i2)).broadcasting = false;
                        }
                        boolean add2 = this.mPendingBroadcasts.add(new BroadcastRecord(intent2, arrayList3));
                        if (!this.mHandler.hasMessages(1)) {
                            boolean sendEmptyMessage = this.mHandler.sendEmptyMessage(1);
                        }
                        return true;
                    }
                }
                return false;
            } finally {
                boolean z2 = z;
            }
        }
    }

    public void sendBroadcastSync(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        if (sendBroadcast(intent2)) {
            executePendingBroadcasts();
        }
    }

    private void executePendingBroadcasts() {
        while (true) {
            HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
            HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap2 = hashMap;
            synchronized (hashMap) {
                try {
                    int size = this.mPendingBroadcasts.size();
                    int N = size;
                    if (size > 0) {
                        BroadcastRecord[] brs = new BroadcastRecord[N];
                        Object[] array = this.mPendingBroadcasts.toArray(brs);
                        this.mPendingBroadcasts.clear();
                        for (int i = 0; i < brs.length; i++) {
                            BroadcastRecord br = brs[i];
                            for (int j = 0; j < br.receivers.size(); j++) {
                                ((ReceiverRecord) br.receivers.get(j)).receiver.onReceive(this.mAppContext, br.intent);
                            }
                        }
                    } else {
                        return;
                    }
                } finally {
                    HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap3 = hashMap2;
                }
            }
        }
    }
}
