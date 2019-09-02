package android.support.p000v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/* renamed from: android.support.v4.app.ListFragment */
public class ListFragment extends Fragment {
    static final int INTERNAL_EMPTY_ID = 16711681;
    static final int INTERNAL_LIST_CONTAINER_ID = 16711683;
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 16711682;
    ListAdapter mAdapter;
    CharSequence mEmptyText;
    View mEmptyView;
    private final Handler mHandler = new Handler();
    ListView mList;
    View mListContainer;
    boolean mListShown;
    private final OnItemClickListener mOnClickListener = new OnItemClickListener(this) {
        final /* synthetic */ ListFragment this$0;

        {
            ListFragment this$02 = r5;
            ListFragment listFragment = this$02;
            this.this$0 = this$02;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            AdapterView<?> parent = adapterView;
            View v = view;
            int position = i;
            long id = j;
            AdapterView<?> adapterView2 = parent;
            View view2 = v;
            int i2 = position;
            long j2 = id;
            this.this$0.onListItemClick((ListView) parent, v, position, id);
        }
    };
    View mProgressContainer;
    private final Runnable mRequestFocus = new Runnable(this) {
        final /* synthetic */ ListFragment this$0;

        {
            ListFragment this$02 = r5;
            ListFragment listFragment = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            this.this$0.mList.focusableViewAvailable(this.this$0.mList);
        }
    };
    TextView mStandardEmptyView;

    public ListFragment() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LayoutInflater layoutInflater2 = layoutInflater;
        ViewGroup viewGroup2 = viewGroup;
        Bundle bundle2 = bundle;
        Context context = getContext();
        FrameLayout root = new FrameLayout(context);
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout pframe = linearLayout;
        linearLayout.setId(INTERNAL_PROGRESS_CONTAINER_ID);
        pframe.setOrientation(1);
        pframe.setVisibility(8);
        pframe.setGravity(17);
        ProgressBar progress = new ProgressBar(context, null, 16842874);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        pframe.addView(progress, layoutParams);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        root.addView(pframe, layoutParams2);
        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout lframe = frameLayout;
        frameLayout.setId(INTERNAL_LIST_CONTAINER_ID);
        TextView textView = new TextView(context);
        TextView tv = textView;
        textView.setId(INTERNAL_EMPTY_ID);
        tv.setGravity(17);
        LayoutParams layoutParams3 = new LayoutParams(-1, -1);
        lframe.addView(tv, layoutParams3);
        ListView listView = new ListView(context);
        ListView lv = listView;
        listView.setId(16908298);
        lv.setDrawSelectorOnTop(false);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        lframe.addView(lv, layoutParams4);
        LayoutParams layoutParams5 = new LayoutParams(-1, -1);
        root.addView(lframe, layoutParams5);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        root.setLayoutParams(layoutParams6);
        return root;
    }

    public void onViewCreated(View view, Bundle bundle) {
        View view2 = view;
        Bundle savedInstanceState = bundle;
        View view3 = view2;
        Bundle bundle2 = savedInstanceState;
        super.onViewCreated(view2, savedInstanceState);
        ensureList();
    }

    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mList = null;
        this.mListShown = false;
        this.mListContainer = null;
        this.mProgressContainer = null;
        this.mEmptyView = null;
        this.mStandardEmptyView = null;
        super.onDestroyView();
    }

    public void onListItemClick(ListView listView, View view, int i, long j) {
        ListView listView2 = listView;
        View view2 = view;
        int i2 = i;
        long j2 = j;
    }

    public void setListAdapter(ListAdapter listAdapter) {
        ListAdapter adapter = listAdapter;
        ListAdapter listAdapter2 = adapter;
        boolean hadAdapter = this.mAdapter != null;
        this.mAdapter = adapter;
        if (this.mList != null) {
            this.mList.setAdapter(adapter);
            if (!this.mListShown && !hadAdapter) {
                setListShown(true, getView().getWindowToken() != null);
            }
        }
    }

    public void setSelection(int i) {
        int position = i;
        int i2 = position;
        ensureList();
        this.mList.setSelection(position);
    }

    public int getSelectedItemPosition() {
        ensureList();
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        ensureList();
        return this.mList.getSelectedItemId();
    }

    public ListView getListView() {
        ensureList();
        return this.mList;
    }

    public void setEmptyText(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        ensureList();
        if (this.mStandardEmptyView != null) {
            this.mStandardEmptyView.setText(text);
            if (this.mEmptyText == null) {
                this.mList.setEmptyView(this.mStandardEmptyView);
            }
            this.mEmptyText = text;
            return;
        }
        throw new IllegalStateException("Can't be used with a custom content view");
    }

    public void setListShown(boolean z) {
        setListShown(z, true);
    }

    public void setListShownNoAnimation(boolean z) {
        setListShown(z, false);
    }

    private void setListShown(boolean z, boolean z2) {
        boolean shown = z;
        boolean animate = z2;
        ensureList();
        if (this.mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        } else if (this.mListShown != shown) {
            this.mListShown = shown;
            if (!shown) {
                if (!animate) {
                    this.mProgressContainer.clearAnimation();
                    this.mListContainer.clearAnimation();
                } else {
                    this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
                }
                this.mProgressContainer.setVisibility(0);
                this.mListContainer.setVisibility(8);
            } else {
                if (!animate) {
                    this.mProgressContainer.clearAnimation();
                    this.mListContainer.clearAnimation();
                } else {
                    this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
                }
                this.mProgressContainer.setVisibility(8);
                this.mListContainer.setVisibility(0);
            }
        }
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    private void ensureList() {
        if (this.mList == null) {
            View view = getView();
            View root = view;
            if (view != null) {
                if (!(root instanceof ListView)) {
                    this.mStandardEmptyView = (TextView) root.findViewById(INTERNAL_EMPTY_ID);
                    if (this.mStandardEmptyView != null) {
                        this.mStandardEmptyView.setVisibility(8);
                    } else {
                        this.mEmptyView = root.findViewById(16908292);
                    }
                    this.mProgressContainer = root.findViewById(INTERNAL_PROGRESS_CONTAINER_ID);
                    this.mListContainer = root.findViewById(INTERNAL_LIST_CONTAINER_ID);
                    View findViewById = root.findViewById(16908298);
                    View rawListView = findViewById;
                    if (findViewById instanceof ListView) {
                        this.mList = (ListView) rawListView;
                        if (this.mEmptyView != null) {
                            this.mList.setEmptyView(this.mEmptyView);
                        } else if (this.mEmptyText != null) {
                            this.mStandardEmptyView.setText(this.mEmptyText);
                            this.mList.setEmptyView(this.mStandardEmptyView);
                        }
                    } else if (rawListView != null) {
                        throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
                    } else {
                        throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
                    }
                } else {
                    this.mList = (ListView) root;
                }
                this.mListShown = true;
                this.mList.setOnItemClickListener(this.mOnClickListener);
                if (this.mAdapter != null) {
                    ListAdapter adapter = this.mAdapter;
                    this.mAdapter = null;
                    setListAdapter(adapter);
                } else if (this.mProgressContainer != null) {
                    setListShown(false, false);
                }
                boolean post = this.mHandler.post(this.mRequestFocus);
                return;
            }
            throw new IllegalStateException("Content view not yet created");
        }
    }
}
