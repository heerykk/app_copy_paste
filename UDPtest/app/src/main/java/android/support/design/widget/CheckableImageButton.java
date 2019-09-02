package android.support.design.widget;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;

@RestrictTo({Scope.LIBRARY_GROUP})
public class CheckableImageButton extends AppCompatImageButton implements Checkable {
    private static final int[] DRAWABLE_STATE_CHECKED;
    private boolean mChecked;

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842912;
        DRAWABLE_STATE_CHECKED = iArr;
    }

    public CheckableImageButton(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat(this) {
            final /* synthetic */ CheckableImageButton this$0;

            {
                CheckableImageButton this$02 = r5;
                CheckableImageButton checkableImageButton = this$02;
                this.this$0 = this$02;
            }

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                super.onInitializeAccessibilityEvent(host, event);
                event.setChecked(this.this$0.isChecked());
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                View host = view;
                AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
                View view2 = host;
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setCheckable(true);
                info.setChecked(this.this$0.isChecked());
            }
        });
    }

    public void setChecked(boolean z) {
        boolean checked = z;
        if (this.mChecked != checked) {
            this.mChecked = checked;
            refreshDrawableState();
            sendAccessibilityEvent(2048);
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        setChecked(!this.mChecked);
    }

    public int[] onCreateDrawableState(int i) {
        int extraSpace = i;
        int i2 = extraSpace;
        if (!this.mChecked) {
            return super.onCreateDrawableState(extraSpace);
        }
        return mergeDrawableStates(super.onCreateDrawableState(extraSpace + DRAWABLE_STATE_CHECKED.length), DRAWABLE_STATE_CHECKED);
    }
}
