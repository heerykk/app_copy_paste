package android.support.p000v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.p000v4.content.Loader.ForceLoadContentObserver;
import android.support.p000v4.p002os.CancellationSignal;
import android.support.p000v4.p002os.OperationCanceledException;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* renamed from: android.support.v4.content.CursorLoader */
public class CursorLoader extends AsyncTaskLoader<Cursor> {
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    final ForceLoadContentObserver mObserver = new ForceLoadContentObserver<>(this);
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public /* bridge */ /* synthetic */ void deliverResult(Object obj) {
        deliverResult((Cursor) obj);
    }

    public /* bridge */ /* synthetic */ void onCanceled(Object obj) {
        onCanceled((Cursor) obj);
    }

    public Cursor loadInBackground() {
        synchronized (this) {
            try {
                if (!isLoadInBackgroundCanceled()) {
                    this.mCancellationSignal = new CancellationSignal();
                } else {
                    throw new OperationCanceledException();
                }
            } finally {
            }
        }
        try {
            Cursor query = ContentResolverCompat.query(getContext().getContentResolver(), this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            this = query;
            if (query != null) {
                int count = this.getCount();
                this.registerContentObserver(this.mObserver);
            }
            synchronized (this) {
                try {
                    this.mCancellationSignal = null;
                } finally {
                    while (true) {
                    }
                }
            }
            return this;
        } catch (RuntimeException e) {
            this.close();
            throw e;
        } catch (Throwable th) {
            synchronized (this) {
                try {
                    this.mCancellationSignal = null;
                    throw th;
                } catch (Throwable th2) {
                    while (true) {
                        throw th2;
                    }
                }
            }
        }
    }

    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            try {
                if (this.mCancellationSignal != null) {
                    this.mCancellationSignal.cancel();
                }
            } finally {
            }
        }
    }

    public void deliverResult(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        if (!isReset()) {
            Cursor oldCursor = this.mCursor;
            this.mCursor = cursor2;
            if (isStarted()) {
                super.deliverResult(cursor2);
            }
            if (!(oldCursor == null || oldCursor == cursor2 || oldCursor.isClosed())) {
                oldCursor.close();
            }
            return;
        }
        if (cursor2 != null) {
            cursor2.close();
        }
    }

    public CursorLoader(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    public CursorLoader(Context context, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Context context2 = context;
        Uri uri2 = uri;
        String[] projection = strArr;
        String selection = str;
        String[] selectionArgs = strArr2;
        String sortOrder = str2;
        Context context3 = context2;
        Uri uri3 = uri2;
        String[] strArr3 = projection;
        String str3 = selection;
        String[] strArr4 = selectionArgs;
        String str4 = sortOrder;
        super(context2);
        this.mUri = uri2;
        this.mProjection = projection;
        this.mSelection = selection;
        this.mSelectionArgs = selectionArgs;
        this.mSortOrder = sortOrder;
    }

    /* access modifiers changed from: protected */
    public void onStartLoading() {
        if (this.mCursor != null) {
            deliverResult(this.mCursor);
        }
        if (takeContentChanged() || this.mCursor == null) {
            forceLoad();
        }
    }

    /* access modifiers changed from: protected */
    public void onStopLoading() {
        boolean cancelLoad = cancelLoad();
    }

    public void onCanceled(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        if (cursor2 != null && !cursor2.isClosed()) {
            cursor2.close();
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        super.onReset();
        onStopLoading();
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public void setUri(Uri uri) {
        Uri uri2 = uri;
        Uri uri3 = uri2;
        this.mUri = uri2;
    }

    public String[] getProjection() {
        return this.mProjection;
    }

    public void setProjection(String[] strArr) {
        String[] projection = strArr;
        String[] strArr2 = projection;
        this.mProjection = projection;
    }

    public String getSelection() {
        return this.mSelection;
    }

    public void setSelection(String str) {
        String selection = str;
        String str2 = selection;
        this.mSelection = selection;
    }

    public String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    public void setSelectionArgs(String[] strArr) {
        String[] selectionArgs = strArr;
        String[] strArr2 = selectionArgs;
        this.mSelectionArgs = selectionArgs;
    }

    public String getSortOrder() {
        return this.mSortOrder;
    }

    public void setSortOrder(String str) {
        String sortOrder = str;
        String str2 = sortOrder;
        this.mSortOrder = sortOrder;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String prefix = str;
        FileDescriptor fd = fileDescriptor;
        PrintWriter writer = printWriter;
        String[] args = strArr;
        String str2 = prefix;
        FileDescriptor fileDescriptor2 = fd;
        PrintWriter printWriter2 = writer;
        String[] strArr2 = args;
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mUri=");
        writer.println(this.mUri);
        writer.print(prefix);
        writer.print("mProjection=");
        writer.println(Arrays.toString(this.mProjection));
        writer.print(prefix);
        writer.print("mSelection=");
        writer.println(this.mSelection);
        writer.print(prefix);
        writer.print("mSelectionArgs=");
        writer.println(Arrays.toString(this.mSelectionArgs));
        writer.print(prefix);
        writer.print("mSortOrder=");
        writer.println(this.mSortOrder);
        writer.print(prefix);
        writer.print("mCursor=");
        writer.println(this.mCursor);
        writer.print(prefix);
        writer.print("mContentChanged=");
        writer.println(this.mContentChanged);
    }
}
