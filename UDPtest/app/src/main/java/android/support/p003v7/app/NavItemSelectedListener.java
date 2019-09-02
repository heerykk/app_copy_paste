package android.support.p003v7.app;

import android.support.p003v7.app.ActionBar.OnNavigationListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/* renamed from: android.support.v7.app.NavItemSelectedListener */
class NavItemSelectedListener implements OnItemSelectedListener {
    private final OnNavigationListener mListener;

    public NavItemSelectedListener(OnNavigationListener onNavigationListener) {
        OnNavigationListener listener = onNavigationListener;
        OnNavigationListener onNavigationListener2 = listener;
        this.mListener = listener;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        int position = i;
        long id = j;
        AdapterView<?> adapterView2 = adapterView;
        View view2 = view;
        int i2 = position;
        long j2 = id;
        if (this.mListener != null) {
            boolean onNavigationItemSelected = this.mListener.onNavigationItemSelected(position, id);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        AdapterView<?> adapterView2 = adapterView;
    }
}
