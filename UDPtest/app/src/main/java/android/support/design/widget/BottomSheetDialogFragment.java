package android.support.design.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.p003v7.app.AppCompatDialogFragment;

public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    public BottomSheetDialogFragment() {
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Bundle bundle2 = bundle;
        return new BottomSheetDialog(getContext(), getTheme());
    }
}
