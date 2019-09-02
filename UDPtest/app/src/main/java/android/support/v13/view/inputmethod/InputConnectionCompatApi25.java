package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

@TargetApi(25)
@RequiresApi(25)
final class InputConnectionCompatApi25 {

    public interface OnCommitContentListener {
        boolean onCommitContent(Object obj, int i, Bundle bundle);
    }

    InputConnectionCompatApi25() {
    }

    public static boolean commitContent(InputConnection inputConnection, Object obj, int i, Bundle bundle) {
        InputConnection ic = inputConnection;
        Object inputContentInfo = obj;
        int flags = i;
        Bundle opts = bundle;
        InputConnection inputConnection2 = ic;
        Object obj2 = inputContentInfo;
        int i2 = flags;
        Bundle bundle2 = opts;
        return ic.commitContent((InputContentInfo) inputContentInfo, flags, opts);
    }

    public static InputConnection createWrapper(InputConnection inputConnection, OnCommitContentListener onCommitContentListener) {
        InputConnection ic = inputConnection;
        OnCommitContentListener onCommitContentListener2 = onCommitContentListener;
        InputConnection inputConnection2 = ic;
        OnCommitContentListener onCommitContentListener3 = onCommitContentListener2;
        return new InputConnectionWrapper(ic, false, onCommitContentListener2) {
            final /* synthetic */ OnCommitContentListener val$onCommitContentListener;

            {
                InputConnection x0 = r10;
                InputConnection inputConnection = x0;
                boolean x1 = r11;
                this.val$onCommitContentListener = r12;
            }

            public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                InputContentInfo inputContentInfo2 = inputContentInfo;
                int flags = i;
                Bundle opts = bundle;
                InputContentInfo inputContentInfo3 = inputContentInfo2;
                int i2 = flags;
                Bundle bundle2 = opts;
                if (this.val$onCommitContentListener.onCommitContent(inputContentInfo2, flags, opts)) {
                    return true;
                }
                return super.commitContent(inputContentInfo2, flags, opts);
            }
        };
    }
}
