package android.support.p003v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.p000v4.p002os.AsyncTaskCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* renamed from: android.support.v7.widget.ActivityChooserModel */
class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter(this);
    boolean mCanReadHistoricalData = true;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    /* renamed from: android.support.v7.widget.ActivityChooserModel$ActivityChooserModelClient */
    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$ActivityResolveInfo */
    public final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        final /* synthetic */ ActivityChooserModel this$0;
        public float weight;

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return compareTo((ActivityResolveInfo) obj);
        }

        public ActivityResolveInfo(ActivityChooserModel activityChooserModel, ResolveInfo resolveInfo2) {
            ActivityChooserModel this$02 = activityChooserModel;
            ResolveInfo resolveInfo3 = resolveInfo2;
            ActivityChooserModel activityChooserModel2 = this$02;
            ResolveInfo resolveInfo4 = resolveInfo3;
            this.this$0 = this$02;
            this.resolveInfo = resolveInfo3;
        }

        public int hashCode() {
            return 31 + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            Object obj2 = obj;
            Object obj3 = obj2;
            if (this == obj2) {
                return true;
            }
            if (obj2 == null) {
                return false;
            }
            if (getClass() != obj2.getClass()) {
                return false;
            }
            if (Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj2).weight)) {
                return true;
            }
            return false;
        }

        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            ActivityResolveInfo another = activityResolveInfo;
            ActivityResolveInfo activityResolveInfo2 = another;
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder builder = sb;
            StringBuilder append = sb.append("[");
            StringBuilder append2 = builder.append("resolveInfo:").append(this.resolveInfo.toString());
            StringBuilder append3 = builder.append("; weight:").append(new BigDecimal((double) this.weight));
            StringBuilder append4 = builder.append("]");
            return builder.toString();
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$ActivitySorter */
    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$DefaultSorter */
    private final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();
        final /* synthetic */ ActivityChooserModel this$0;

        DefaultSorter(ActivityChooserModel activityChooserModel) {
            this.this$0 = activityChooserModel;
        }

        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            List<ActivityResolveInfo> activities = list;
            List<HistoricalRecord> historicalRecords = list2;
            Intent intent2 = intent;
            List<ActivityResolveInfo> list3 = activities;
            List<HistoricalRecord> list4 = historicalRecords;
            Map<ComponentName, ActivityResolveInfo> map = this.mPackageNameToActivityMap;
            Map<ComponentName, ActivityResolveInfo> componentNameToActivityMap = map;
            map.clear();
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) activities.get(i);
                ActivityResolveInfo activity = activityResolveInfo;
                activityResolveInfo.weight = 0.0f;
                Object put = componentNameToActivityMap.put(new ComponentName(activity.resolveInfo.activityInfo.packageName, activity.resolveInfo.activityInfo.name), activity);
            }
            int size = historicalRecords.size() - 1;
            int i2 = size;
            float nextRecordWeight = ActivityChooserModel.DEFAULT_HISTORICAL_RECORD_WEIGHT;
            for (int i3 = size; i3 >= 0; i3--) {
                HistoricalRecord historicalRecord = (HistoricalRecord) historicalRecords.get(i3);
                HistoricalRecord historicalRecord2 = historicalRecord;
                ActivityResolveInfo activityResolveInfo2 = (ActivityResolveInfo) componentNameToActivityMap.get(historicalRecord.activity);
                ActivityResolveInfo activity2 = activityResolveInfo2;
                if (activityResolveInfo2 != null) {
                    activity2.weight += historicalRecord2.weight * nextRecordWeight;
                    nextRecordWeight *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(activities);
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$HistoricalRecord */
    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j, float f) {
            String activityName = str;
            long time2 = j;
            float weight2 = f;
            String str2 = activityName;
            long j2 = time2;
            float f2 = weight2;
            this(ComponentName.unflattenFromString(activityName), time2, weight2);
        }

        public HistoricalRecord(ComponentName componentName, long j, float f) {
            ComponentName activityName = componentName;
            long time2 = j;
            float weight2 = f;
            ComponentName componentName2 = activityName;
            long j2 = time2;
            float f2 = weight2;
            this.activity = activityName;
            this.time = time2;
            this.weight = weight2;
        }

        public int hashCode() {
            int hashCode = (31 * ((31 * (31 + (this.activity != null ? this.activity.hashCode() : 0))) + ((int) (this.time ^ (this.time >>> 32))))) + Float.floatToIntBits(this.weight);
            int result = hashCode;
            return hashCode;
        }

        public boolean equals(Object obj) {
            Object obj2 = obj;
            Object obj3 = obj2;
            if (this == obj2) {
                return true;
            }
            if (obj2 == null) {
                return false;
            }
            if (getClass() != obj2.getClass()) {
                return false;
            }
            HistoricalRecord other = (HistoricalRecord) obj2;
            if (this.activity != null) {
                if (!this.activity.equals(other.activity)) {
                    return false;
                }
            } else if (other.activity != null) {
                return false;
            }
            if (this.time != other.time) {
                return false;
            }
            if (Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight)) {
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder builder = sb;
            StringBuilder append = sb.append("[");
            StringBuilder append2 = builder.append("; activity:").append(this.activity);
            StringBuilder append3 = builder.append("; time:").append(this.time);
            StringBuilder append4 = builder.append("; weight:").append(new BigDecimal((double) this.weight));
            StringBuilder append5 = builder.append("]");
            return builder.toString();
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$OnChooseActivityListener */
    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    /* renamed from: android.support.v7.widget.ActivityChooserModel$PersistHistoryAsyncTask */
    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        final /* synthetic */ ActivityChooserModel this$0;

        PersistHistoryAsyncTask(ActivityChooserModel activityChooserModel) {
            this.this$0 = activityChooserModel;
        }

        public Void doInBackground(Object... objArr) {
            Object[] args = objArr;
            Object[] objArr2 = args;
            List list = (List) args[0];
            String historyFileName = (String) args[1];
            try {
                FileOutputStream fos = this.this$0.mContext.openFileOutput(historyFileName, 0);
                XmlSerializer newSerializer = Xml.newSerializer();
                XmlSerializer serializer = newSerializer;
                try {
                    newSerializer.setOutput(fos, null);
                    serializer.startDocument("UTF-8", Boolean.valueOf(true));
                    XmlSerializer startTag = serializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                    int recordCount = list.size();
                    for (int i = 0; i < recordCount; i++) {
                        HistoricalRecord record = (HistoricalRecord) list.remove(0);
                        XmlSerializer startTag2 = serializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                        XmlSerializer attribute = serializer.attribute(null, ActivityChooserModel.ATTRIBUTE_ACTIVITY, record.activity.flattenToString());
                        XmlSerializer attribute2 = serializer.attribute(null, ActivityChooserModel.ATTRIBUTE_TIME, String.valueOf(record.time));
                        XmlSerializer attribute3 = serializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(record.weight));
                        XmlSerializer endTag = serializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                    }
                    XmlSerializer endTag2 = serializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                    serializer.endDocument();
                    this.this$0.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                        }
                    }
                } catch (IllegalArgumentException e2) {
                    int e3 = Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + this.this$0.mHistoryFileName, e2);
                    this.this$0.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (IllegalStateException e5) {
                    int e6 = Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + this.this$0.mHistoryFileName, e5);
                    this.this$0.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e7) {
                        }
                    }
                } catch (IOException e8) {
                    int e9 = Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + this.this$0.mHistoryFileName, e8);
                    this.this$0.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e10) {
                        }
                    }
                } catch (Throwable th) {
                    this.this$0.mCanReadHistoricalData = true;
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e11) {
                        }
                    }
                    throw th;
                }
                return null;
            } catch (FileNotFoundException e12) {
                FileNotFoundException fileNotFoundException = e12;
                int e13 = Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + historyFileName, e12);
                return null;
            }
        }
    }

    private ActivityChooserModel(Context context, String str) {
        Context context2 = context;
        String historyFileName = str;
        Context context3 = context2;
        String str2 = historyFileName;
        this.mContext = context2.getApplicationContext();
        if (!TextUtils.isEmpty(historyFileName) && !historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = historyFileName + HISTORY_FILE_EXTENSION;
        } else {
            this.mHistoryFileName = historyFileName;
        }
    }

    public static ActivityChooserModel get(Context context, String str) {
        Context context2 = context;
        String historyFileName = str;
        Context context3 = context2;
        String str2 = historyFileName;
        Object obj = sRegistryLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                ActivityChooserModel activityChooserModel = (ActivityChooserModel) sDataModelRegistry.get(historyFileName);
                ActivityChooserModel dataModel = activityChooserModel;
                if (activityChooserModel == null) {
                    dataModel = new ActivityChooserModel(context2, historyFileName);
                    Object put = sDataModelRegistry.put(historyFileName, dataModel);
                }
                ActivityChooserModel activityChooserModel2 = dataModel;
                return activityChooserModel2;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public void setIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (this.mIntent != intent2) {
                    this.mIntent = intent2;
                    this.mReloadActivities = true;
                    ensureConsistentState();
                    return;
                }
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public Intent getIntent() {
        Object obj = this.mInstanceLock;
        Intent intent = obj;
        synchronized (obj) {
            try {
                intent = this.mIntent;
                return intent;
            } finally {
                Intent intent2 = intent;
            }
        }
    }

    public int getActivityCount() {
        Object obj = this.mInstanceLock;
        int i = obj;
        synchronized (obj) {
            try {
                ensureConsistentState();
                i = this.mActivities.size();
                return i;
            } finally {
                int i2 = i;
            }
        }
    }

    public ResolveInfo getActivity(int i) {
        int index = i;
        int i2 = index;
        Object obj = this.mInstanceLock;
        ResolveInfo resolveInfo = obj;
        synchronized (obj) {
            try {
                ensureConsistentState();
                resolveInfo = ((ActivityResolveInfo) this.mActivities.get(index)).resolveInfo;
                return resolveInfo;
            } finally {
                ResolveInfo resolveInfo2 = resolveInfo;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public int getActivityIndex(ResolveInfo resolveInfo) {
        ResolveInfo activity = resolveInfo;
        ResolveInfo resolveInfo2 = activity;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                ensureConsistentState();
                List<ActivityResolveInfo> list = this.mActivities;
                List<ActivityResolveInfo> activities = list;
                int activityCount = list.size();
                int i = 0;
                while (i < activityCount) {
                    ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) activities.get(i);
                    ActivityResolveInfo activityResolveInfo2 = activityResolveInfo;
                    if (activityResolveInfo.resolveInfo != activity) {
                        i++;
                    } else {
                        int i2 = i;
                        return i2;
                    }
                }
                return -1;
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public Intent chooseActivity(int i) {
        int index = i;
        int i2 = index;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (this.mIntent != null) {
                    ensureConsistentState();
                    ActivityResolveInfo chosenActivity = (ActivityResolveInfo) this.mActivities.get(index);
                    ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
                    Intent intent = new Intent(this.mIntent);
                    Intent choiceIntent = intent;
                    Intent component = intent.setComponent(chosenName);
                    if (this.mActivityChoserModelPolicy != null) {
                        boolean onChooseActivity = this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(choiceIntent));
                        boolean z = onChooseActivity;
                        if (onChooseActivity) {
                            return null;
                        }
                    }
                    boolean addHistoricalRecord = addHistoricalRecord(new HistoricalRecord(chosenName, System.currentTimeMillis(), (float) DEFAULT_HISTORICAL_RECORD_WEIGHT));
                    return choiceIntent;
                }
                return null;
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        OnChooseActivityListener listener = onChooseActivityListener;
        OnChooseActivityListener onChooseActivityListener2 = listener;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                this.mActivityChoserModelPolicy = listener;
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    public ResolveInfo getDefaultActivity() {
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                ensureConsistentState();
                if (this.mActivities.isEmpty()) {
                    return null;
                }
                ResolveInfo resolveInfo = ((ActivityResolveInfo) this.mActivities.get(0)).resolveInfo;
                return resolveInfo;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public void setDefaultActivity(int i) {
        float weight;
        int index = i;
        int i2 = index;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                ensureConsistentState();
                ActivityResolveInfo newDefaultActivity = (ActivityResolveInfo) this.mActivities.get(index);
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) this.mActivities.get(0);
                ActivityResolveInfo oldDefaultActivity = activityResolveInfo;
                if (activityResolveInfo == null) {
                    weight = DEFAULT_HISTORICAL_RECORD_WEIGHT;
                } else {
                    weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
                }
                boolean addHistoricalRecord = addHistoricalRecord(new HistoricalRecord(new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name), System.currentTimeMillis(), weight));
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                PersistHistoryAsyncTask persistHistoryAsyncTask = new PersistHistoryAsyncTask(this);
                Object[] objArr = new Object[2];
                ArrayList arrayList = new ArrayList(this.mHistoricalRecords);
                objArr[0] = arrayList;
                objArr[1] = this.mHistoryFileName;
                AsyncTask executeParallel = AsyncTaskCompat.executeParallel(persistHistoryAsyncTask, objArr);
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void setActivitySorter(ActivitySorter activitySorter) {
        ActivitySorter activitySorter2 = activitySorter;
        ActivitySorter activitySorter3 = activitySorter2;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (this.mActivitySorter != activitySorter2) {
                    this.mActivitySorter = activitySorter2;
                    if (sortActivitiesIfNeeded()) {
                        notifyChanged();
                    }
                    return;
                }
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void setHistoryMaxSize(int i) {
        int historyMaxSize = i;
        int i2 = historyMaxSize;
        Object obj = this.mInstanceLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (this.mHistoryMaxSize != historyMaxSize) {
                    this.mHistoryMaxSize = historyMaxSize;
                    pruneExcessiveHistoricalRecordsIfNeeded();
                    if (sortActivitiesIfNeeded()) {
                        notifyChanged();
                    }
                    return;
                }
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    public int getHistoryMaxSize() {
        Object obj = this.mInstanceLock;
        int i = obj;
        synchronized (obj) {
            try {
                i = this.mHistoryMaxSize;
                return i;
            } finally {
                int i2 = i;
            }
        }
    }

    public int getHistorySize() {
        Object obj = this.mInstanceLock;
        int i = obj;
        synchronized (obj) {
            try {
                ensureConsistentState();
                i = this.mHistoricalRecords.size();
                return i;
            } finally {
                int i2 = i;
            }
        }
    }

    private void ensureConsistentState() {
        boolean loadActivitiesIfNeeded = loadActivitiesIfNeeded();
        boolean z = loadActivitiesIfNeeded;
        boolean readHistoricalDataIfNeeded = loadActivitiesIfNeeded | readHistoricalDataIfNeeded();
        boolean stateChanged = readHistoricalDataIfNeeded;
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (readHistoricalDataIfNeeded) {
            boolean sortActivitiesIfNeeded = sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        List list = queryIntentActivities;
        int resolveInfoCount = queryIntentActivities.size();
        for (int i = 0; i < resolveInfoCount; i++) {
            boolean add = this.mActivities.add(new ActivityResolveInfo(this, (ResolveInfo) list.get(i)));
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        HistoricalRecord historicalRecord2 = historicalRecord;
        HistoricalRecord historicalRecord3 = historicalRecord2;
        boolean add = this.mHistoricalRecords.add(historicalRecord2);
        boolean added = add;
        if (add) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            boolean sortActivitiesIfNeeded = sortActivitiesIfNeeded();
            notifyChanged();
        }
        return added;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        int pruneCount = size;
        if (size > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < pruneCount; i++) {
                HistoricalRecord historicalRecord = (HistoricalRecord) this.mHistoricalRecords.remove(0);
            }
        }
    }

    private void readHistoricalDataImpl() {
        FileInputStream fis = null;
        try {
            fis = this.mContext.openFileInput(this.mHistoryFileName);
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                XmlPullParser parser = newPullParser;
                newPullParser.setInput(fis, "UTF-8");
                int type = 0;
                while (true) {
                    if (type == 1) {
                        break;
                    } else if (type == 2) {
                        break;
                    } else {
                        type = parser.next();
                    }
                }
                if (TAG_HISTORICAL_RECORDS.equals(parser.getName())) {
                    List<HistoricalRecord> list = this.mHistoricalRecords;
                    List<HistoricalRecord> historicalRecords = list;
                    list.clear();
                    while (true) {
                        int next = parser.next();
                        int type2 = next;
                        if (next != 1) {
                            if (type2 != 3) {
                                if (type2 != 4) {
                                    if (TAG_HISTORICAL_RECORD.equals(parser.getName())) {
                                        String activity = parser.getAttributeValue(null, ATTRIBUTE_ACTIVITY);
                                        long parseLong = Long.parseLong(parser.getAttributeValue(null, ATTRIBUTE_TIME));
                                        long j = parseLong;
                                        float parseFloat = Float.parseFloat(parser.getAttributeValue(null, ATTRIBUTE_WEIGHT));
                                        float f = parseFloat;
                                        boolean add = historicalRecords.add(new HistoricalRecord(activity, parseLong, parseFloat));
                                    } else {
                                        throw new XmlPullParserException("Share records file not well-formed.");
                                    }
                                }
                            }
                        } else if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e) {
                                IOException iOException = e;
                            }
                        }
                    }
                    return;
                }
                throw new XmlPullParserException("Share records file does not start with historical-records tag.");
            } catch (XmlPullParserException e2) {
                XmlPullParserException xmlPullParserException = e2;
                int e3 = Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, e2);
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e4) {
                        IOException iOException2 = e4;
                    }
                }
            } catch (IOException e5) {
                IOException iOException3 = e5;
                int e6 = Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, e5);
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e7) {
                        IOException ioe = e7;
                    }
                }
            } catch (Throwable th) {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e8) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e9) {
            FileNotFoundException fileNotFoundException = e9;
        }
    }
}
