package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.p002os.BuildCompat;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

@TargetApi(13)
@RequiresApi(13)
public final class InputConnectionCompat {
    private static final InputConnectionCompatImpl IMPL;
    public static int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    private static final class Api25InputContentInfoCompatImpl implements InputConnectionCompatImpl {
        private Api25InputContentInfoCompatImpl() {
        }

        /* synthetic */ Api25InputContentInfoCompatImpl(C00821 r5) {
            C00821 r3 = r5;
            this();
        }

        public boolean commitContent(@NonNull InputConnection inputConnection, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle) {
            InputConnection inputConnection2 = inputConnection;
            InputContentInfoCompat inputContentInfo = inputContentInfoCompat;
            int flags = i;
            Bundle opts = bundle;
            InputConnection inputConnection3 = inputConnection2;
            InputContentInfoCompat inputContentInfoCompat2 = inputContentInfo;
            int i2 = flags;
            Bundle bundle2 = opts;
            return InputConnectionCompatApi25.commitContent(inputConnection2, inputContentInfo.unwrap(), flags, opts);
        }

        @Nullable
        public InputConnection createWrapper(@Nullable InputConnection inputConnection, @NonNull EditorInfo editorInfo, @Nullable OnCommitContentListener onCommitContentListener) {
            InputConnection inputConnection2 = inputConnection;
            OnCommitContentListener onCommitContentListener2 = onCommitContentListener;
            InputConnection inputConnection3 = inputConnection2;
            EditorInfo editorInfo2 = editorInfo;
            OnCommitContentListener onCommitContentListener3 = onCommitContentListener2;
            OnCommitContentListener onCommitContentListener4 = onCommitContentListener2;
            final OnCommitContentListener onCommitContentListener5 = onCommitContentListener2;
            return InputConnectionCompatApi25.createWrapper(inputConnection2, new android.support.v13.view.inputmethod.InputConnectionCompatApi25.OnCommitContentListener(this) {
                final /* synthetic */ Api25InputContentInfoCompatImpl this$0;

                {
                    Api25InputContentInfoCompatImpl this$02 = r6;
                    OnCommitContentListener onCommitContentListener = r7;
                    Api25InputContentInfoCompatImpl api25InputContentInfoCompatImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean onCommitContent(Object obj, int i, Bundle bundle) {
                    Object inputContentInfo = obj;
                    int flags = i;
                    Bundle opts = bundle;
                    Object obj2 = inputContentInfo;
                    int i2 = flags;
                    Bundle bundle2 = opts;
                    return onCommitContentListener5.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), flags, opts);
                }
            });
        }
    }

    static final class BaseInputContentInfoCompatImpl implements InputConnectionCompatImpl {
        private static String COMMIT_CONTENT_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
        private static String COMMIT_CONTENT_CONTENT_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
        private static String COMMIT_CONTENT_DESCRIPTION_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
        private static String COMMIT_CONTENT_FLAGS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
        private static String COMMIT_CONTENT_LINK_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
        private static String COMMIT_CONTENT_OPTS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
        private static String COMMIT_CONTENT_RESULT_RECEIVER = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";

        BaseInputContentInfoCompatImpl() {
        }

        public boolean commitContent(@NonNull InputConnection inputConnection, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle) {
            InputConnection inputConnection2 = inputConnection;
            InputContentInfoCompat inputContentInfo = inputContentInfoCompat;
            int flags = i;
            Bundle opts = bundle;
            InputConnection inputConnection3 = inputConnection2;
            InputContentInfoCompat inputContentInfoCompat2 = inputContentInfo;
            int i2 = flags;
            Bundle bundle2 = opts;
            Bundle bundle3 = new Bundle();
            Bundle params = bundle3;
            bundle3.putParcelable(COMMIT_CONTENT_CONTENT_URI_KEY, inputContentInfo.getContentUri());
            params.putParcelable(COMMIT_CONTENT_DESCRIPTION_KEY, inputContentInfo.getDescription());
            params.putParcelable(COMMIT_CONTENT_LINK_URI_KEY, inputContentInfo.getLinkUri());
            params.putInt(COMMIT_CONTENT_FLAGS_KEY, flags);
            params.putParcelable(COMMIT_CONTENT_OPTS_KEY, opts);
            return inputConnection2.performPrivateCommand(COMMIT_CONTENT_ACTION, params);
        }

        @NonNull
        public InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull OnCommitContentListener onCommitContentListener) {
            InputConnection ic = inputConnection;
            EditorInfo editorInfo2 = editorInfo;
            OnCommitContentListener onCommitContentListener2 = onCommitContentListener;
            InputConnection inputConnection2 = ic;
            EditorInfo editorInfo3 = editorInfo2;
            OnCommitContentListener onCommitContentListener3 = onCommitContentListener2;
            String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo2);
            String[] strArr = contentMimeTypes;
            if (contentMimeTypes.length == 0) {
                return ic;
            }
            OnCommitContentListener onCommitContentListener4 = onCommitContentListener2;
            return new InputConnectionWrapper(this, ic, false, onCommitContentListener2) {
                final /* synthetic */ BaseInputContentInfoCompatImpl this$0;
                final /* synthetic */ OnCommitContentListener val$listener;

                {
                    BaseInputContentInfoCompatImpl this$02 = r12;
                    InputConnection x0 = r13;
                    OnCommitContentListener onCommitContentListener = r15;
                    BaseInputContentInfoCompatImpl baseInputContentInfoCompatImpl = this$02;
                    InputConnection inputConnection = x0;
                    boolean x1 = r14;
                    this.this$0 = this$02;
                    this.val$listener = onCommitContentListener;
                }

                public boolean performPrivateCommand(String str, Bundle bundle) {
                    String action = str;
                    Bundle data = bundle;
                    String str2 = action;
                    Bundle bundle2 = data;
                    if (!BaseInputContentInfoCompatImpl.handlePerformPrivateCommand(action, data, this.val$listener)) {
                        return super.performPrivateCommand(action, data);
                    }
                    return true;
                }
            };
        }

        /* JADX INFO: finally extract failed */
        static boolean handlePerformPrivateCommand(@Nullable String str, @NonNull Bundle bundle, @NonNull OnCommitContentListener onCommitContentListener) {
            String action = str;
            Bundle data = bundle;
            OnCommitContentListener onCommitContentListener2 = onCommitContentListener;
            String str2 = action;
            Bundle bundle2 = data;
            OnCommitContentListener onCommitContentListener3 = onCommitContentListener2;
            if (!TextUtils.equals(COMMIT_CONTENT_ACTION, action)) {
                return false;
            }
            if (data == null) {
                return false;
            }
            ResultReceiver resultReceiver = null;
            try {
                resultReceiver = (ResultReceiver) data.getParcelable(COMMIT_CONTENT_RESULT_RECEIVER);
                Uri contentUri = (Uri) data.getParcelable(COMMIT_CONTENT_CONTENT_URI_KEY);
                ClipDescription description = (ClipDescription) data.getParcelable(COMMIT_CONTENT_DESCRIPTION_KEY);
                Uri linkUri = (Uri) data.getParcelable(COMMIT_CONTENT_LINK_URI_KEY);
                int i = data.getInt(COMMIT_CONTENT_FLAGS_KEY);
                int i2 = i;
                boolean onCommitContent = onCommitContentListener2.onCommitContent(new InputContentInfoCompat(contentUri, description, linkUri), i, (Bundle) data.getParcelable(COMMIT_CONTENT_OPTS_KEY));
                boolean result = onCommitContent;
                if (resultReceiver != null) {
                    resultReceiver.send(!onCommitContent ? 0 : 1, null);
                }
                return onCommitContent;
            } catch (Throwable th) {
                if (resultReceiver != null) {
                    resultReceiver.send(0, null);
                }
                throw th;
            }
        }
    }

    private interface InputConnectionCompatImpl {
        boolean commitContent(@NonNull InputConnection inputConnection, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle);

        @NonNull
        InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull OnCommitContentListener onCommitContentListener);
    }

    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    public InputConnectionCompat() {
    }

    static {
        if (!BuildCompat.isAtLeastNMR1()) {
            IMPL = new BaseInputContentInfoCompatImpl();
        } else {
            IMPL = new Api25InputContentInfoCompatImpl(null);
        }
    }

    public static boolean commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle) {
        InputConnection inputConnection2 = inputConnection;
        EditorInfo editorInfo2 = editorInfo;
        InputContentInfoCompat inputContentInfo = inputContentInfoCompat;
        int flags = i;
        Bundle opts = bundle;
        InputConnection inputConnection3 = inputConnection2;
        EditorInfo editorInfo3 = editorInfo2;
        InputContentInfoCompat inputContentInfoCompat2 = inputContentInfo;
        int i2 = flags;
        Bundle bundle2 = opts;
        ClipDescription description = inputContentInfo.getDescription();
        boolean supported = false;
        String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo2);
        String[] strArr = contentMimeTypes;
        int length = contentMimeTypes.length;
        int i3 = 0;
        while (true) {
            if (i3 < length) {
                if (description.hasMimeType(strArr[i3])) {
                    supported = true;
                    break;
                }
                i3++;
            } else {
                break;
            }
        }
        if (supported) {
            return IMPL.commitContent(inputConnection2, inputContentInfo, flags, opts);
        }
        return false;
    }

    @NonNull
    public static InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull OnCommitContentListener onCommitContentListener) {
        InputConnection inputConnection2 = inputConnection;
        EditorInfo editorInfo2 = editorInfo;
        OnCommitContentListener onCommitContentListener2 = onCommitContentListener;
        InputConnection inputConnection3 = inputConnection2;
        EditorInfo editorInfo3 = editorInfo2;
        OnCommitContentListener onCommitContentListener3 = onCommitContentListener2;
        if (inputConnection2 == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        } else if (editorInfo2 == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        } else if (onCommitContentListener2 != null) {
            return IMPL.createWrapper(inputConnection2, editorInfo2, onCommitContentListener2);
        } else {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        }
    }
}
