package android.support.p000v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.p000v4.content.IntentCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

/* renamed from: android.support.v4.app.ShareCompat */
public final class ShareCompat {
    public static final String EXTRA_CALLING_ACTIVITY = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_PACKAGE = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
    static ShareCompatImpl IMPL;

    /* renamed from: android.support.v4.app.ShareCompat$IntentBuilder */
    public static class IntentBuilder {
        private Activity mActivity;
        private ArrayList<String> mBccAddresses;
        private ArrayList<String> mCcAddresses;
        private CharSequence mChooserTitle;
        private Intent mIntent = new Intent().setAction("android.intent.action.SEND");
        private ArrayList<Uri> mStreams;
        private ArrayList<String> mToAddresses;

        public static IntentBuilder from(Activity activity) {
            Activity launchingActivity = activity;
            Activity activity2 = launchingActivity;
            return new IntentBuilder(launchingActivity);
        }

        private IntentBuilder(Activity activity) {
            Activity launchingActivity = activity;
            Activity activity2 = launchingActivity;
            this.mActivity = launchingActivity;
            Intent putExtra = this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, launchingActivity.getPackageName());
            Intent putExtra2 = this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, launchingActivity.getComponentName());
            Intent addFlags = this.mIntent.addFlags(524288);
        }

        public Intent getIntent() {
            if (this.mToAddresses != null) {
                combineArrayExtra("android.intent.extra.EMAIL", this.mToAddresses);
                this.mToAddresses = null;
            }
            if (this.mCcAddresses != null) {
                combineArrayExtra("android.intent.extra.CC", this.mCcAddresses);
                this.mCcAddresses = null;
            }
            if (this.mBccAddresses != null) {
                combineArrayExtra("android.intent.extra.BCC", this.mBccAddresses);
                this.mBccAddresses = null;
            }
            boolean needsSendMultiple = this.mStreams != null && this.mStreams.size() > 1;
            boolean isSendMultiple = this.mIntent.getAction().equals("android.intent.action.SEND_MULTIPLE");
            if (!needsSendMultiple && isSendMultiple) {
                Intent action = this.mIntent.setAction("android.intent.action.SEND");
                if (this.mStreams != null && !this.mStreams.isEmpty()) {
                    Intent putExtra = this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable) this.mStreams.get(0));
                } else {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                }
                this.mStreams = null;
            }
            if (needsSendMultiple && !isSendMultiple) {
                Intent action2 = this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
                if (this.mStreams != null && !this.mStreams.isEmpty()) {
                    Intent putParcelableArrayListExtra = this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
                } else {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                }
            }
            return this.mIntent;
        }

        /* access modifiers changed from: 0000 */
        public Activity getActivity() {
            return this.mActivity;
        }

        private void combineArrayExtra(String str, ArrayList<String> arrayList) {
            String extra = str;
            ArrayList<String> add = arrayList;
            String str2 = extra;
            ArrayList<String> arrayList2 = add;
            String[] stringArrayExtra = this.mIntent.getStringArrayExtra(extra);
            String[] currentAddresses = stringArrayExtra;
            int currentLength = stringArrayExtra == null ? 0 : currentAddresses.length;
            String[] finalAddresses = new String[(currentLength + add.size())];
            Object[] array = add.toArray(finalAddresses);
            if (currentAddresses != null) {
                System.arraycopy(currentAddresses, 0, finalAddresses, add.size(), currentLength);
            }
            Intent putExtra = this.mIntent.putExtra(extra, finalAddresses);
        }

        private void combineArrayExtra(String str, String[] strArr) {
            String extra = str;
            String[] add = strArr;
            String str2 = extra;
            String[] strArr2 = add;
            Intent intent = getIntent();
            Intent intent2 = intent;
            String[] stringArrayExtra = intent.getStringArrayExtra(extra);
            String[] old = stringArrayExtra;
            int oldLength = stringArrayExtra == null ? 0 : old.length;
            String[] result = new String[(oldLength + add.length)];
            if (old != null) {
                System.arraycopy(old, 0, result, 0, oldLength);
            }
            System.arraycopy(add, 0, result, oldLength, add.length);
            Intent putExtra = intent2.putExtra(extra, result);
        }

        public Intent createChooserIntent() {
            return Intent.createChooser(getIntent(), this.mChooserTitle);
        }

        public void startChooser() {
            this.mActivity.startActivity(createChooserIntent());
        }

        public IntentBuilder setChooserTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mChooserTitle = title;
            return this;
        }

        public IntentBuilder setChooserTitle(@StringRes int i) {
            int resId = i;
            int i2 = resId;
            return setChooserTitle(this.mActivity.getText(resId));
        }

        public IntentBuilder setType(String str) {
            String mimeType = str;
            String str2 = mimeType;
            Intent type = this.mIntent.setType(mimeType);
            return this;
        }

        public IntentBuilder setText(CharSequence charSequence) {
            CharSequence text = charSequence;
            CharSequence charSequence2 = text;
            Intent putExtra = this.mIntent.putExtra("android.intent.extra.TEXT", text);
            return this;
        }

        public IntentBuilder setHtmlText(String str) {
            String htmlText = str;
            String str2 = htmlText;
            Intent putExtra = this.mIntent.putExtra(IntentCompat.EXTRA_HTML_TEXT, htmlText);
            if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
                IntentBuilder text = setText(Html.fromHtml(htmlText));
            }
            return this;
        }

        public IntentBuilder setStream(Uri uri) {
            Uri streamUri = uri;
            Uri uri2 = streamUri;
            if (!this.mIntent.getAction().equals("android.intent.action.SEND")) {
                Intent action = this.mIntent.setAction("android.intent.action.SEND");
            }
            this.mStreams = null;
            Intent putExtra = this.mIntent.putExtra("android.intent.extra.STREAM", streamUri);
            return this;
        }

        public IntentBuilder addStream(Uri uri) {
            Uri streamUri = uri;
            Uri uri2 = streamUri;
            Uri currentStream = (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            if (this.mStreams == null && currentStream == null) {
                return setStream(streamUri);
            }
            if (this.mStreams == null) {
                this.mStreams = new ArrayList<>();
            }
            if (currentStream != null) {
                this.mIntent.removeExtra("android.intent.extra.STREAM");
                boolean add = this.mStreams.add(currentStream);
            }
            boolean add2 = this.mStreams.add(streamUri);
            return this;
        }

        public IntentBuilder setEmailTo(String[] strArr) {
            String[] addresses = strArr;
            String[] strArr2 = addresses;
            if (this.mToAddresses != null) {
                this.mToAddresses = null;
            }
            Intent putExtra = this.mIntent.putExtra("android.intent.extra.EMAIL", addresses);
            return this;
        }

        public IntentBuilder addEmailTo(String str) {
            String address = str;
            String str2 = address;
            if (this.mToAddresses == null) {
                this.mToAddresses = new ArrayList<>();
            }
            boolean add = this.mToAddresses.add(address);
            return this;
        }

        public IntentBuilder addEmailTo(String[] strArr) {
            String[] addresses = strArr;
            String[] strArr2 = addresses;
            combineArrayExtra("android.intent.extra.EMAIL", addresses);
            return this;
        }

        public IntentBuilder setEmailCc(String[] strArr) {
            String[] addresses = strArr;
            String[] strArr2 = addresses;
            Intent putExtra = this.mIntent.putExtra("android.intent.extra.CC", addresses);
            return this;
        }

        public IntentBuilder addEmailCc(String str) {
            String address = str;
            String str2 = address;
            if (this.mCcAddresses == null) {
                this.mCcAddresses = new ArrayList<>();
            }
            boolean add = this.mCcAddresses.add(address);
            return this;
        }

        public IntentBuilder addEmailCc(String[] strArr) {
            String[] addresses = strArr;
            String[] strArr2 = addresses;
            combineArrayExtra("android.intent.extra.CC", addresses);
            return this;
        }

        public IntentBuilder setEmailBcc(String[] strArr) {
            String[] addresses = strArr;
            String[] strArr2 = addresses;
            Intent putExtra = this.mIntent.putExtra("android.intent.extra.BCC", addresses);
            return this;
        }

        public IntentBuilder addEmailBcc(String str) {
            String address = str;
            String str2 = address;
            if (this.mBccAddresses == null) {
                this.mBccAddresses = new ArrayList<>();
            }
            boolean add = this.mBccAddresses.add(address);
            return this;
        }

        public IntentBuilder addEmailBcc(String[] strArr) {
            String[] addresses = strArr;
            String[] strArr2 = addresses;
            combineArrayExtra("android.intent.extra.BCC", addresses);
            return this;
        }

        public IntentBuilder setSubject(String str) {
            String subject = str;
            String str2 = subject;
            Intent putExtra = this.mIntent.putExtra("android.intent.extra.SUBJECT", subject);
            return this;
        }
    }

    /* renamed from: android.support.v4.app.ShareCompat$IntentReader */
    public static class IntentReader {
        private static final String TAG = "IntentReader";
        private Activity mActivity;
        private ComponentName mCallingActivity;
        private String mCallingPackage;
        private Intent mIntent;
        private ArrayList<Uri> mStreams;

        public static IntentReader from(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            return new IntentReader(activity2);
        }

        private IntentReader(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            this.mActivity = activity2;
            this.mIntent = activity2.getIntent();
            this.mCallingPackage = ShareCompat.getCallingPackage(activity2);
            this.mCallingActivity = ShareCompat.getCallingActivity(activity2);
        }

        public boolean isShareIntent() {
            String action = this.mIntent.getAction();
            return "android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action);
        }

        public boolean isSingleShare() {
            return "android.intent.action.SEND".equals(this.mIntent.getAction());
        }

        public boolean isMultipleShare() {
            return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
        }

        public String getType() {
            return this.mIntent.getType();
        }

        public CharSequence getText() {
            return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
        }

        public String getHtmlText() {
            String stringExtra = this.mIntent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT);
            String result = stringExtra;
            if (stringExtra == null) {
                CharSequence text = getText();
                CharSequence text2 = text;
                if (text instanceof Spanned) {
                    result = Html.toHtml((Spanned) text2);
                } else if (text2 != null) {
                    result = ShareCompat.IMPL.escapeHtml(text2);
                }
            }
            return result;
        }

        public Uri getStream() {
            return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        }

        public Uri getStream(int i) {
            int index = i;
            int i2 = index;
            if (this.mStreams == null && isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if (this.mStreams != null) {
                return (Uri) this.mStreams.get(index);
            }
            if (index == 0) {
                return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            }
            throw new IndexOutOfBoundsException("Stream items available: " + getStreamCount() + " index requested: " + index);
        }

        public int getStreamCount() {
            int i;
            if (this.mStreams == null && isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if (this.mStreams != null) {
                return this.mStreams.size();
            }
            if (!this.mIntent.hasExtra("android.intent.extra.STREAM")) {
                i = 0;
            } else {
                i = 1;
            }
            return i;
        }

        public String[] getEmailTo() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
        }

        public String[] getEmailCc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
        }

        public String[] getEmailBcc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
        }

        public String getSubject() {
            return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public ComponentName getCallingActivity() {
            return this.mCallingActivity;
        }

        public Drawable getCallingActivityIcon() {
            if (this.mCallingActivity == null) {
                return null;
            }
            PackageManager packageManager = this.mActivity.getPackageManager();
            PackageManager packageManager2 = packageManager;
            try {
                return packageManager.getActivityIcon(this.mCallingActivity);
            } catch (NameNotFoundException e) {
                NameNotFoundException nameNotFoundException = e;
                int e2 = Log.e(TAG, "Could not retrieve icon for calling activity", e);
                return null;
            }
        }

        public Drawable getCallingApplicationIcon() {
            if (this.mCallingPackage == null) {
                return null;
            }
            PackageManager packageManager = this.mActivity.getPackageManager();
            PackageManager packageManager2 = packageManager;
            try {
                return packageManager.getApplicationIcon(this.mCallingPackage);
            } catch (NameNotFoundException e) {
                NameNotFoundException nameNotFoundException = e;
                int e2 = Log.e(TAG, "Could not retrieve icon for calling application", e);
                return null;
            }
        }

        public CharSequence getCallingApplicationLabel() {
            if (this.mCallingPackage == null) {
                return null;
            }
            PackageManager packageManager = this.mActivity.getPackageManager();
            PackageManager pm = packageManager;
            try {
                return packageManager.getApplicationLabel(pm.getApplicationInfo(this.mCallingPackage, 0));
            } catch (NameNotFoundException e) {
                NameNotFoundException nameNotFoundException = e;
                int e2 = Log.e(TAG, "Could not retrieve label for calling application", e);
                return null;
            }
        }
    }

    /* renamed from: android.support.v4.app.ShareCompat$ShareCompatImpl */
    interface ShareCompatImpl {
        void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder);

        String escapeHtml(CharSequence charSequence);
    }

    /* renamed from: android.support.v4.app.ShareCompat$ShareCompatImplBase */
    static class ShareCompatImplBase implements ShareCompatImpl {
        ShareCompatImplBase() {
        }

        public void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder) {
            MenuItem item = menuItem;
            IntentBuilder shareIntent = intentBuilder;
            MenuItem menuItem2 = item;
            IntentBuilder intentBuilder2 = shareIntent;
            MenuItem intent = item.setIntent(shareIntent.createChooserIntent());
        }

        public String escapeHtml(CharSequence charSequence) {
            CharSequence text = charSequence;
            CharSequence charSequence2 = text;
            StringBuilder sb = new StringBuilder();
            StringBuilder out = sb;
            withinStyle(sb, text, 0, text.length());
            return out.toString();
        }

        private static void withinStyle(StringBuilder sb, CharSequence charSequence, int i, int i2) {
            StringBuilder out = sb;
            CharSequence text = charSequence;
            int start = i;
            int end = i2;
            StringBuilder sb2 = out;
            CharSequence charSequence2 = text;
            int i3 = start;
            int i4 = end;
            int i5 = start;
            while (i5 < end) {
                char charAt = text.charAt(i5);
                char c = charAt;
                if (charAt == '<') {
                    StringBuilder append = out.append("&lt;");
                } else if (c == '>') {
                    StringBuilder append2 = out.append("&gt;");
                } else if (c == '&') {
                    StringBuilder append3 = out.append("&amp;");
                } else if (c > '~' || c < ' ') {
                    StringBuilder append4 = out.append("&#" + c + ";");
                } else if (c != ' ') {
                    StringBuilder append5 = out.append(c);
                } else {
                    while (i5 + 1 < end && text.charAt(i5 + 1) == ' ') {
                        StringBuilder append6 = out.append("&nbsp;");
                        i5++;
                    }
                    StringBuilder append7 = out.append(' ');
                }
                i5++;
            }
        }
    }

    /* renamed from: android.support.v4.app.ShareCompat$ShareCompatImplICS */
    static class ShareCompatImplICS extends ShareCompatImplBase {
        ShareCompatImplICS() {
        }

        public void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder) {
            MenuItem item = menuItem;
            IntentBuilder shareIntent = intentBuilder;
            MenuItem menuItem2 = item;
            IntentBuilder intentBuilder2 = shareIntent;
            ShareCompatICS.configureMenuItem(item, shareIntent.getActivity(), shareIntent.getIntent());
            if (shouldAddChooserIntent(item)) {
                MenuItem intent = item.setIntent(shareIntent.createChooserIntent());
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldAddChooserIntent(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return !item.hasSubMenu();
        }
    }

    /* renamed from: android.support.v4.app.ShareCompat$ShareCompatImplJB */
    static class ShareCompatImplJB extends ShareCompatImplICS {
        ShareCompatImplJB() {
        }

        public String escapeHtml(CharSequence charSequence) {
            CharSequence html = charSequence;
            CharSequence charSequence2 = html;
            return ShareCompatJB.escapeHtml(html);
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldAddChooserIntent(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new ShareCompatImplJB();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new ShareCompatImplBase();
        } else {
            IMPL = new ShareCompatImplICS();
        }
    }

    private ShareCompat() {
    }

    public static String getCallingPackage(Activity activity) {
        Activity calledActivity = activity;
        Activity activity2 = calledActivity;
        String callingPackage = calledActivity.getCallingPackage();
        String result = callingPackage;
        if (callingPackage == null) {
            result = calledActivity.getIntent().getStringExtra(EXTRA_CALLING_PACKAGE);
        }
        return result;
    }

    public static ComponentName getCallingActivity(Activity activity) {
        Activity calledActivity = activity;
        Activity activity2 = calledActivity;
        ComponentName callingActivity = calledActivity.getCallingActivity();
        ComponentName result = callingActivity;
        if (callingActivity == null) {
            result = (ComponentName) calledActivity.getIntent().getParcelableExtra(EXTRA_CALLING_ACTIVITY);
        }
        return result;
    }

    public static void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder) {
        MenuItem item = menuItem;
        IntentBuilder shareIntent = intentBuilder;
        MenuItem menuItem2 = item;
        IntentBuilder intentBuilder2 = shareIntent;
        IMPL.configureMenuItem(item, shareIntent);
    }

    public static void configureMenuItem(Menu menu, int i, IntentBuilder intentBuilder) {
        Menu menu2 = menu;
        int menuItemId = i;
        IntentBuilder shareIntent = intentBuilder;
        Menu menu3 = menu2;
        int i2 = menuItemId;
        IntentBuilder intentBuilder2 = shareIntent;
        MenuItem findItem = menu2.findItem(menuItemId);
        MenuItem item = findItem;
        if (findItem != null) {
            configureMenuItem(item, shareIntent);
            return;
        }
        throw new IllegalArgumentException("Could not find menu item with id " + menuItemId + " in the supplied menu");
    }
}
