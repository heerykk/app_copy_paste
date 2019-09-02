package android.support.p000v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat */
public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    static final AccessibilityNodeInfoImpl IMPL;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private final Object mInfo;
    @RestrictTo({Scope.LIBRARY_GROUP})
    public int mParentVirtualDescendantId = -1;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat */
    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionContextClick());
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollDown());
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollLeft());
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollRight());
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollToPosition());
        public static final AccessibilityActionCompat ACTION_SCROLL_UP = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollUp());
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionSetProgress());
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionShowOnScreen());
        final Object mAction;

        public AccessibilityActionCompat(int i, CharSequence charSequence) {
            int actionId = i;
            CharSequence label = charSequence;
            int i2 = actionId;
            CharSequence charSequence2 = label;
            this(AccessibilityNodeInfoCompat.IMPL.newAccessibilityAction(actionId, label));
        }

        AccessibilityActionCompat(Object obj) {
            Object action = obj;
            Object obj2 = action;
            this.mAction = action;
        }

        public int getId() {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionId(this.mAction);
        }

        public CharSequence getLabel() {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionLabel(this.mAction);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi21Impl */
    static class AccessibilityNodeInfoApi21Impl extends AccessibilityNodeInfoKitKatImpl {
        AccessibilityNodeInfoApi21Impl() {
        }

        public Object newAccessibilityAction(int i, CharSequence charSequence) {
            int actionId = i;
            CharSequence label = charSequence;
            int i2 = actionId;
            CharSequence charSequence2 = label;
            return AccessibilityNodeInfoCompatApi21.newAccessibilityAction(actionId, label);
        }

        public List<Object> getActionList(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi21.getActionList(info);
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
            int rowCount = i;
            int columnCount = i2;
            int selectionMode = i3;
            int i4 = rowCount;
            int i5 = columnCount;
            int i6 = selectionMode;
            return AccessibilityNodeInfoCompatApi21.obtainCollectionInfo(rowCount, columnCount, z, selectionMode);
        }

        public void addAction(Object obj, Object obj2) {
            Object info = obj;
            Object action = obj2;
            Object obj3 = info;
            Object obj4 = action;
            AccessibilityNodeInfoCompatApi21.addAction(info, action);
        }

        public boolean removeAction(Object obj, Object obj2) {
            Object info = obj;
            Object action = obj2;
            Object obj3 = info;
            Object obj4 = action;
            return AccessibilityNodeInfoCompatApi21.removeAction(info, action);
        }

        public int getAccessibilityActionId(Object obj) {
            Object action = obj;
            Object obj2 = action;
            return AccessibilityNodeInfoCompatApi21.getAccessibilityActionId(action);
        }

        public CharSequence getAccessibilityActionLabel(Object obj) {
            Object action = obj;
            Object obj2 = action;
            return AccessibilityNodeInfoCompatApi21.getAccessibilityActionLabel(action);
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            int rowIndex = i;
            int rowSpan = i2;
            int columnIndex = i3;
            int columnSpan = i4;
            int i5 = rowIndex;
            int i6 = rowSpan;
            int i7 = columnIndex;
            int i8 = columnSpan;
            return AccessibilityNodeInfoCompatApi21.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, z, z2);
        }

        public boolean isCollectionItemSelected(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionItemInfo.isSelected(info);
        }

        public CharSequence getError(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi21.getError(info);
        }

        public void setError(Object obj, CharSequence charSequence) {
            Object info = obj;
            CharSequence error = charSequence;
            Object obj2 = info;
            CharSequence charSequence2 = error;
            AccessibilityNodeInfoCompatApi21.setError(info, error);
        }

        public void setMaxTextLength(Object obj, int i) {
            Object info = obj;
            int max = i;
            Object obj2 = info;
            int i2 = max;
            AccessibilityNodeInfoCompatApi21.setMaxTextLength(info, max);
        }

        public int getMaxTextLength(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi21.getMaxTextLength(info);
        }

        public Object getWindow(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi21.getWindow(info);
        }

        public boolean removeChild(Object obj, View view) {
            Object info = obj;
            View child = view;
            Object obj2 = info;
            View view2 = child;
            return AccessibilityNodeInfoCompatApi21.removeChild(info, child);
        }

        public boolean removeChild(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            return AccessibilityNodeInfoCompatApi21.removeChild(info, root, virtualDescendantId);
        }

        public int getCollectionInfoSelectionMode(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionInfo.getSelectionMode(info);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi22Impl */
    static class AccessibilityNodeInfoApi22Impl extends AccessibilityNodeInfoApi21Impl {
        AccessibilityNodeInfoApi22Impl() {
        }

        public Object getTraversalBefore(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi22.getTraversalBefore(info);
        }

        public void setTraversalBefore(Object obj, View view) {
            Object info = obj;
            View view2 = view;
            Object obj2 = info;
            View view3 = view2;
            AccessibilityNodeInfoCompatApi22.setTraversalBefore(info, view2);
        }

        public void setTraversalBefore(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatApi22.setTraversalBefore(info, root, virtualDescendantId);
        }

        public Object getTraversalAfter(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi22.getTraversalAfter(info);
        }

        public void setTraversalAfter(Object obj, View view) {
            Object info = obj;
            View view2 = view;
            Object obj2 = info;
            View view3 = view2;
            AccessibilityNodeInfoCompatApi22.setTraversalAfter(info, view2);
        }

        public void setTraversalAfter(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatApi22.setTraversalAfter(info, root, virtualDescendantId);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi23Impl */
    static class AccessibilityNodeInfoApi23Impl extends AccessibilityNodeInfoApi22Impl {
        AccessibilityNodeInfoApi23Impl() {
        }

        public Object getActionScrollToPosition() {
            return AccessibilityNodeInfoCompatApi23.getActionScrollToPosition();
        }

        public Object getActionShowOnScreen() {
            return AccessibilityNodeInfoCompatApi23.getActionShowOnScreen();
        }

        public Object getActionScrollUp() {
            return AccessibilityNodeInfoCompatApi23.getActionScrollUp();
        }

        public Object getActionScrollDown() {
            return AccessibilityNodeInfoCompatApi23.getActionScrollDown();
        }

        public Object getActionScrollLeft() {
            return AccessibilityNodeInfoCompatApi23.getActionScrollLeft();
        }

        public Object getActionScrollRight() {
            return AccessibilityNodeInfoCompatApi23.getActionScrollRight();
        }

        public Object getActionContextClick() {
            return AccessibilityNodeInfoCompatApi23.getActionContextClick();
        }

        public boolean isContextClickable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi23.isContextClickable(info);
        }

        public void setContextClickable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatApi23.setContextClickable(info, z);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi24Impl */
    static class AccessibilityNodeInfoApi24Impl extends AccessibilityNodeInfoApi23Impl {
        AccessibilityNodeInfoApi24Impl() {
        }

        public Object getActionSetProgress() {
            return AccessibilityNodeInfoCompatApi24.getActionSetProgress();
        }

        public int getDrawingOrder(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi24.getDrawingOrder(info);
        }

        public void setDrawingOrder(Object obj, int i) {
            Object info = obj;
            int drawingOrderInParent = i;
            Object obj2 = info;
            int i2 = drawingOrderInParent;
            AccessibilityNodeInfoCompatApi24.setDrawingOrder(info, drawingOrderInParent);
        }

        public boolean isImportantForAccessibility(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatApi24.isImportantForAccessibility(info);
        }

        public void setImportantForAccessibility(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatApi24.setImportantForAccessibility(info, z);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoIcsImpl */
    static class AccessibilityNodeInfoIcsImpl extends AccessibilityNodeInfoStubImpl {
        AccessibilityNodeInfoIcsImpl() {
        }

        public Object obtain() {
            return AccessibilityNodeInfoCompatIcs.obtain();
        }

        public Object obtain(View view) {
            View source = view;
            View view2 = source;
            return AccessibilityNodeInfoCompatIcs.obtain(source);
        }

        public Object obtain(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.obtain(info);
        }

        public void addAction(Object obj, int i) {
            Object info = obj;
            int action = i;
            Object obj2 = info;
            int i2 = action;
            AccessibilityNodeInfoCompatIcs.addAction(info, action);
        }

        public void addChild(Object obj, View view) {
            Object info = obj;
            View child = view;
            Object obj2 = info;
            View view2 = child;
            AccessibilityNodeInfoCompatIcs.addChild(info, child);
        }

        public List<Object> findAccessibilityNodeInfosByText(Object obj, String str) {
            Object info = obj;
            String text = str;
            Object obj2 = info;
            String str2 = text;
            return AccessibilityNodeInfoCompatIcs.findAccessibilityNodeInfosByText(info, text);
        }

        public int getActions(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getActions(info);
        }

        public void getBoundsInParent(Object obj, Rect rect) {
            Object info = obj;
            Rect outBounds = rect;
            Object obj2 = info;
            Rect rect2 = outBounds;
            AccessibilityNodeInfoCompatIcs.getBoundsInParent(info, outBounds);
        }

        public void getBoundsInScreen(Object obj, Rect rect) {
            Object info = obj;
            Rect outBounds = rect;
            Object obj2 = info;
            Rect rect2 = outBounds;
            AccessibilityNodeInfoCompatIcs.getBoundsInScreen(info, outBounds);
        }

        public Object getChild(Object obj, int i) {
            Object info = obj;
            int index = i;
            Object obj2 = info;
            int i2 = index;
            return AccessibilityNodeInfoCompatIcs.getChild(info, index);
        }

        public int getChildCount(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getChildCount(info);
        }

        public CharSequence getClassName(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getClassName(info);
        }

        public CharSequence getContentDescription(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getContentDescription(info);
        }

        public CharSequence getPackageName(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getPackageName(info);
        }

        public Object getParent(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getParent(info);
        }

        public CharSequence getText(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getText(info);
        }

        public int getWindowId(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.getWindowId(info);
        }

        public boolean isCheckable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isCheckable(info);
        }

        public boolean isChecked(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isChecked(info);
        }

        public boolean isClickable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isClickable(info);
        }

        public boolean isEnabled(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isEnabled(info);
        }

        public boolean isFocusable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isFocusable(info);
        }

        public boolean isFocused(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isFocused(info);
        }

        public boolean isLongClickable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isLongClickable(info);
        }

        public boolean isPassword(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isPassword(info);
        }

        public boolean isScrollable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isScrollable(info);
        }

        public boolean isSelected(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatIcs.isSelected(info);
        }

        public boolean performAction(Object obj, int i) {
            Object info = obj;
            int action = i;
            Object obj2 = info;
            int i2 = action;
            return AccessibilityNodeInfoCompatIcs.performAction(info, action);
        }

        public void setBoundsInParent(Object obj, Rect rect) {
            Object info = obj;
            Rect bounds = rect;
            Object obj2 = info;
            Rect rect2 = bounds;
            AccessibilityNodeInfoCompatIcs.setBoundsInParent(info, bounds);
        }

        public void setBoundsInScreen(Object obj, Rect rect) {
            Object info = obj;
            Rect bounds = rect;
            Object obj2 = info;
            Rect rect2 = bounds;
            AccessibilityNodeInfoCompatIcs.setBoundsInScreen(info, bounds);
        }

        public void setCheckable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setCheckable(info, z);
        }

        public void setChecked(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setChecked(info, z);
        }

        public void setClassName(Object obj, CharSequence charSequence) {
            Object info = obj;
            CharSequence className = charSequence;
            Object obj2 = info;
            CharSequence charSequence2 = className;
            AccessibilityNodeInfoCompatIcs.setClassName(info, className);
        }

        public void setClickable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setClickable(info, z);
        }

        public void setContentDescription(Object obj, CharSequence charSequence) {
            Object info = obj;
            CharSequence contentDescription = charSequence;
            Object obj2 = info;
            CharSequence charSequence2 = contentDescription;
            AccessibilityNodeInfoCompatIcs.setContentDescription(info, contentDescription);
        }

        public void setEnabled(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setEnabled(info, z);
        }

        public void setFocusable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setFocusable(info, z);
        }

        public void setFocused(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setFocused(info, z);
        }

        public void setLongClickable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setLongClickable(info, z);
        }

        public void setPackageName(Object obj, CharSequence charSequence) {
            Object info = obj;
            CharSequence packageName = charSequence;
            Object obj2 = info;
            CharSequence charSequence2 = packageName;
            AccessibilityNodeInfoCompatIcs.setPackageName(info, packageName);
        }

        public void setParent(Object obj, View view) {
            Object info = obj;
            View parent = view;
            Object obj2 = info;
            View view2 = parent;
            AccessibilityNodeInfoCompatIcs.setParent(info, parent);
        }

        public void setPassword(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setPassword(info, z);
        }

        public void setScrollable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setScrollable(info, z);
        }

        public void setSelected(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.setSelected(info, z);
        }

        public void setSource(Object obj, View view) {
            Object info = obj;
            View source = view;
            Object obj2 = info;
            View view2 = source;
            AccessibilityNodeInfoCompatIcs.setSource(info, source);
        }

        public void setText(Object obj, CharSequence charSequence) {
            Object info = obj;
            CharSequence text = charSequence;
            Object obj2 = info;
            CharSequence charSequence2 = text;
            AccessibilityNodeInfoCompatIcs.setText(info, text);
        }

        public void recycle(Object obj) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatIcs.recycle(info);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoImpl */
    interface AccessibilityNodeInfoImpl {
        void addAction(Object obj, int i);

        void addAction(Object obj, Object obj2);

        void addChild(Object obj, View view);

        void addChild(Object obj, View view, int i);

        boolean canOpenPopup(Object obj);

        List<Object> findAccessibilityNodeInfosByText(Object obj, String str);

        List<Object> findAccessibilityNodeInfosByViewId(Object obj, String str);

        Object findFocus(Object obj, int i);

        Object focusSearch(Object obj, int i);

        int getAccessibilityActionId(Object obj);

        CharSequence getAccessibilityActionLabel(Object obj);

        Object getActionContextClick();

        List<Object> getActionList(Object obj);

        Object getActionScrollDown();

        Object getActionScrollLeft();

        Object getActionScrollRight();

        Object getActionScrollToPosition();

        Object getActionScrollUp();

        Object getActionSetProgress();

        Object getActionShowOnScreen();

        int getActions(Object obj);

        void getBoundsInParent(Object obj, Rect rect);

        void getBoundsInScreen(Object obj, Rect rect);

        Object getChild(Object obj, int i);

        int getChildCount(Object obj);

        CharSequence getClassName(Object obj);

        Object getCollectionInfo(Object obj);

        int getCollectionInfoColumnCount(Object obj);

        int getCollectionInfoRowCount(Object obj);

        int getCollectionInfoSelectionMode(Object obj);

        int getCollectionItemColumnIndex(Object obj);

        int getCollectionItemColumnSpan(Object obj);

        Object getCollectionItemInfo(Object obj);

        int getCollectionItemRowIndex(Object obj);

        int getCollectionItemRowSpan(Object obj);

        CharSequence getContentDescription(Object obj);

        int getDrawingOrder(Object obj);

        CharSequence getError(Object obj);

        Bundle getExtras(Object obj);

        int getInputType(Object obj);

        Object getLabelFor(Object obj);

        Object getLabeledBy(Object obj);

        int getLiveRegion(Object obj);

        int getMaxTextLength(Object obj);

        int getMovementGranularities(Object obj);

        CharSequence getPackageName(Object obj);

        Object getParent(Object obj);

        Object getRangeInfo(Object obj);

        CharSequence getRoleDescription(Object obj);

        CharSequence getText(Object obj);

        int getTextSelectionEnd(Object obj);

        int getTextSelectionStart(Object obj);

        Object getTraversalAfter(Object obj);

        Object getTraversalBefore(Object obj);

        String getViewIdResourceName(Object obj);

        Object getWindow(Object obj);

        int getWindowId(Object obj);

        boolean isAccessibilityFocused(Object obj);

        boolean isCheckable(Object obj);

        boolean isChecked(Object obj);

        boolean isClickable(Object obj);

        boolean isCollectionInfoHierarchical(Object obj);

        boolean isCollectionItemHeading(Object obj);

        boolean isCollectionItemSelected(Object obj);

        boolean isContentInvalid(Object obj);

        boolean isContextClickable(Object obj);

        boolean isDismissable(Object obj);

        boolean isEditable(Object obj);

        boolean isEnabled(Object obj);

        boolean isFocusable(Object obj);

        boolean isFocused(Object obj);

        boolean isImportantForAccessibility(Object obj);

        boolean isLongClickable(Object obj);

        boolean isMultiLine(Object obj);

        boolean isPassword(Object obj);

        boolean isScrollable(Object obj);

        boolean isSelected(Object obj);

        boolean isVisibleToUser(Object obj);

        Object newAccessibilityAction(int i, CharSequence charSequence);

        Object obtain();

        Object obtain(View view);

        Object obtain(View view, int i);

        Object obtain(Object obj);

        Object obtainCollectionInfo(int i, int i2, boolean z);

        Object obtainCollectionInfo(int i, int i2, boolean z, int i3);

        Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z);

        Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2);

        Object obtainRangeInfo(int i, float f, float f2, float f3);

        boolean performAction(Object obj, int i);

        boolean performAction(Object obj, int i, Bundle bundle);

        void recycle(Object obj);

        boolean refresh(Object obj);

        boolean removeAction(Object obj, Object obj2);

        boolean removeChild(Object obj, View view);

        boolean removeChild(Object obj, View view, int i);

        void setAccessibilityFocused(Object obj, boolean z);

        void setBoundsInParent(Object obj, Rect rect);

        void setBoundsInScreen(Object obj, Rect rect);

        void setCanOpenPopup(Object obj, boolean z);

        void setCheckable(Object obj, boolean z);

        void setChecked(Object obj, boolean z);

        void setClassName(Object obj, CharSequence charSequence);

        void setClickable(Object obj, boolean z);

        void setCollectionInfo(Object obj, Object obj2);

        void setCollectionItemInfo(Object obj, Object obj2);

        void setContentDescription(Object obj, CharSequence charSequence);

        void setContentInvalid(Object obj, boolean z);

        void setContextClickable(Object obj, boolean z);

        void setDismissable(Object obj, boolean z);

        void setDrawingOrder(Object obj, int i);

        void setEditable(Object obj, boolean z);

        void setEnabled(Object obj, boolean z);

        void setError(Object obj, CharSequence charSequence);

        void setFocusable(Object obj, boolean z);

        void setFocused(Object obj, boolean z);

        void setImportantForAccessibility(Object obj, boolean z);

        void setInputType(Object obj, int i);

        void setLabelFor(Object obj, View view);

        void setLabelFor(Object obj, View view, int i);

        void setLabeledBy(Object obj, View view);

        void setLabeledBy(Object obj, View view, int i);

        void setLiveRegion(Object obj, int i);

        void setLongClickable(Object obj, boolean z);

        void setMaxTextLength(Object obj, int i);

        void setMovementGranularities(Object obj, int i);

        void setMultiLine(Object obj, boolean z);

        void setPackageName(Object obj, CharSequence charSequence);

        void setParent(Object obj, View view);

        void setParent(Object obj, View view, int i);

        void setPassword(Object obj, boolean z);

        void setRangeInfo(Object obj, Object obj2);

        void setRoleDescription(Object obj, CharSequence charSequence);

        void setScrollable(Object obj, boolean z);

        void setSelected(Object obj, boolean z);

        void setSource(Object obj, View view);

        void setSource(Object obj, View view, int i);

        void setText(Object obj, CharSequence charSequence);

        void setTextSelection(Object obj, int i, int i2);

        void setTraversalAfter(Object obj, View view);

        void setTraversalAfter(Object obj, View view, int i);

        void setTraversalBefore(Object obj, View view);

        void setTraversalBefore(Object obj, View view, int i);

        void setViewIdResourceName(Object obj, String str);

        void setVisibleToUser(Object obj, boolean z);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoJellybeanImpl */
    static class AccessibilityNodeInfoJellybeanImpl extends AccessibilityNodeInfoIcsImpl {
        AccessibilityNodeInfoJellybeanImpl() {
        }

        public Object obtain(View view, int i) {
            View root = view;
            int virtualDescendantId = i;
            View view2 = root;
            int i2 = virtualDescendantId;
            return AccessibilityNodeInfoCompatJellyBean.obtain(root, virtualDescendantId);
        }

        public Object findFocus(Object obj, int i) {
            Object info = obj;
            int focus = i;
            Object obj2 = info;
            int i2 = focus;
            return AccessibilityNodeInfoCompatJellyBean.findFocus(info, focus);
        }

        public Object focusSearch(Object obj, int i) {
            Object info = obj;
            int direction = i;
            Object obj2 = info;
            int i2 = direction;
            return AccessibilityNodeInfoCompatJellyBean.focusSearch(info, direction);
        }

        public void addChild(Object obj, View view, int i) {
            Object info = obj;
            View child = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = child;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatJellyBean.addChild(info, child, virtualDescendantId);
        }

        public void setSource(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatJellyBean.setSource(info, root, virtualDescendantId);
        }

        public boolean isVisibleToUser(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellyBean.isVisibleToUser(info);
        }

        public void setVisibleToUser(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatJellyBean.setVisibleToUser(info, z);
        }

        public boolean isAccessibilityFocused(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellyBean.isAccessibilityFocused(info);
        }

        public void setAccessibilityFocused(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatJellyBean.setAccesibilityFocused(info, z);
        }

        public boolean performAction(Object obj, int i, Bundle bundle) {
            Object info = obj;
            int action = i;
            Bundle arguments = bundle;
            Object obj2 = info;
            int i2 = action;
            Bundle bundle2 = arguments;
            return AccessibilityNodeInfoCompatJellyBean.performAction(info, action, arguments);
        }

        public void setMovementGranularities(Object obj, int i) {
            Object info = obj;
            int granularities = i;
            Object obj2 = info;
            int i2 = granularities;
            AccessibilityNodeInfoCompatJellyBean.setMovementGranularities(info, granularities);
        }

        public int getMovementGranularities(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellyBean.getMovementGranularities(info);
        }

        public void setParent(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatJellyBean.setParent(info, root, virtualDescendantId);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoJellybeanMr1Impl */
    static class AccessibilityNodeInfoJellybeanMr1Impl extends AccessibilityNodeInfoJellybeanImpl {
        AccessibilityNodeInfoJellybeanMr1Impl() {
        }

        public void setLabelFor(Object obj, View view) {
            Object info = obj;
            View labeled = view;
            Object obj2 = info;
            View view2 = labeled;
            AccessibilityNodeInfoCompatJellybeanMr1.setLabelFor(info, labeled);
        }

        public void setLabelFor(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatJellybeanMr1.setLabelFor(info, root, virtualDescendantId);
        }

        public Object getLabelFor(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr1.getLabelFor(info);
        }

        public void setLabeledBy(Object obj, View view) {
            Object info = obj;
            View labeled = view;
            Object obj2 = info;
            View view2 = labeled;
            AccessibilityNodeInfoCompatJellybeanMr1.setLabeledBy(info, labeled);
        }

        public void setLabeledBy(Object obj, View view, int i) {
            Object info = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = info;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityNodeInfoCompatJellybeanMr1.setLabeledBy(info, root, virtualDescendantId);
        }

        public Object getLabeledBy(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr1.getLabeledBy(info);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoJellybeanMr2Impl */
    static class AccessibilityNodeInfoJellybeanMr2Impl extends AccessibilityNodeInfoJellybeanMr1Impl {
        AccessibilityNodeInfoJellybeanMr2Impl() {
        }

        public String getViewIdResourceName(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr2.getViewIdResourceName(info);
        }

        public void setViewIdResourceName(Object obj, String str) {
            Object info = obj;
            String viewId = str;
            Object obj2 = info;
            String str2 = viewId;
            AccessibilityNodeInfoCompatJellybeanMr2.setViewIdResourceName(info, viewId);
        }

        public List<Object> findAccessibilityNodeInfosByViewId(Object obj, String str) {
            Object info = obj;
            String viewId = str;
            Object obj2 = info;
            String str2 = viewId;
            return AccessibilityNodeInfoCompatJellybeanMr2.findAccessibilityNodeInfosByViewId(info, viewId);
        }

        public void setTextSelection(Object obj, int i, int i2) {
            Object info = obj;
            int start = i;
            int end = i2;
            Object obj2 = info;
            int i3 = start;
            int i4 = end;
            AccessibilityNodeInfoCompatJellybeanMr2.setTextSelection(info, start, end);
        }

        public int getTextSelectionStart(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr2.getTextSelectionStart(info);
        }

        public int getTextSelectionEnd(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr2.getTextSelectionEnd(info);
        }

        public boolean isEditable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr2.isEditable(info);
        }

        public void setEditable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatJellybeanMr2.setEditable(info, z);
        }

        public boolean refresh(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatJellybeanMr2.refresh(info);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoKitKatImpl */
    static class AccessibilityNodeInfoKitKatImpl extends AccessibilityNodeInfoJellybeanMr2Impl {
        AccessibilityNodeInfoKitKatImpl() {
        }

        public int getLiveRegion(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getLiveRegion(info);
        }

        public void setLiveRegion(Object obj, int i) {
            Object info = obj;
            int mode = i;
            Object obj2 = info;
            int i2 = mode;
            AccessibilityNodeInfoCompatKitKat.setLiveRegion(info, mode);
        }

        public Object getCollectionInfo(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getCollectionInfo(info);
        }

        public void setCollectionInfo(Object obj, Object obj2) {
            Object info = obj;
            Object collectionInfo = obj2;
            Object obj3 = info;
            Object obj4 = collectionInfo;
            AccessibilityNodeInfoCompatKitKat.setCollectionInfo(info, collectionInfo);
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
            int rowCount = i;
            int columnCount = i2;
            int selectionMode = i3;
            int i4 = rowCount;
            int i5 = columnCount;
            int i6 = selectionMode;
            return AccessibilityNodeInfoCompatKitKat.obtainCollectionInfo(rowCount, columnCount, z, selectionMode);
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z) {
            int rowCount = i;
            int columnCount = i2;
            int i3 = rowCount;
            int i4 = columnCount;
            return AccessibilityNodeInfoCompatKitKat.obtainCollectionInfo(rowCount, columnCount, z);
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            int rowIndex = i;
            int rowSpan = i2;
            int columnIndex = i3;
            int columnSpan = i4;
            int i5 = rowIndex;
            int i6 = rowSpan;
            int i7 = columnIndex;
            int i8 = columnSpan;
            boolean z3 = z2;
            return AccessibilityNodeInfoCompatKitKat.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, z);
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
            int rowIndex = i;
            int rowSpan = i2;
            int columnIndex = i3;
            int columnSpan = i4;
            int i5 = rowIndex;
            int i6 = rowSpan;
            int i7 = columnIndex;
            int i8 = columnSpan;
            return AccessibilityNodeInfoCompatKitKat.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, z);
        }

        public int getCollectionInfoColumnCount(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionInfo.getColumnCount(info);
        }

        public int getCollectionInfoRowCount(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionInfo.getRowCount(info);
        }

        public boolean isCollectionInfoHierarchical(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionInfo.isHierarchical(info);
        }

        public Object getCollectionItemInfo(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getCollectionItemInfo(info);
        }

        public Object getRangeInfo(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getRangeInfo(info);
        }

        public void setRangeInfo(Object obj, Object obj2) {
            Object info = obj;
            Object rangeInfo = obj2;
            Object obj3 = info;
            Object obj4 = rangeInfo;
            AccessibilityNodeInfoCompatKitKat.setRangeInfo(info, rangeInfo);
        }

        public int getCollectionItemColumnIndex(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionItemInfo.getColumnIndex(info);
        }

        public int getCollectionItemColumnSpan(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionItemInfo.getColumnSpan(info);
        }

        public int getCollectionItemRowIndex(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionItemInfo.getRowIndex(info);
        }

        public int getCollectionItemRowSpan(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionItemInfo.getRowSpan(info);
        }

        public boolean isCollectionItemHeading(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return CollectionItemInfo.isHeading(info);
        }

        public void setCollectionItemInfo(Object obj, Object obj2) {
            Object info = obj;
            Object collectionItemInfo = obj2;
            Object obj3 = info;
            Object obj4 = collectionItemInfo;
            AccessibilityNodeInfoCompatKitKat.setCollectionItemInfo(info, collectionItemInfo);
        }

        public Object obtainRangeInfo(int i, float f, float f2, float f3) {
            int type = i;
            float min = f;
            float max = f2;
            float current = f3;
            int i2 = type;
            float f4 = min;
            float f5 = max;
            float f6 = current;
            return AccessibilityNodeInfoCompatKitKat.obtainRangeInfo(type, min, max, current);
        }

        public void setContentInvalid(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatKitKat.setContentInvalid(info, z);
        }

        public boolean isContentInvalid(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.isContentInvalid(info);
        }

        public boolean canOpenPopup(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.canOpenPopup(info);
        }

        public void setCanOpenPopup(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatKitKat.setCanOpenPopup(info, z);
        }

        public Bundle getExtras(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getExtras(info);
        }

        public int getInputType(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getInputType(info);
        }

        public void setInputType(Object obj, int i) {
            Object info = obj;
            int inputType = i;
            Object obj2 = info;
            int i2 = inputType;
            AccessibilityNodeInfoCompatKitKat.setInputType(info, inputType);
        }

        public boolean isDismissable(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.isDismissable(info);
        }

        public void setDismissable(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatKitKat.setDismissable(info, z);
        }

        public boolean isMultiLine(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.isMultiLine(info);
        }

        public void setMultiLine(Object obj, boolean z) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityNodeInfoCompatKitKat.setMultiLine(info, z);
        }

        public CharSequence getRoleDescription(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityNodeInfoCompatKitKat.getRoleDescription(info);
        }

        public void setRoleDescription(Object obj, CharSequence charSequence) {
            Object info = obj;
            CharSequence roleDescription = charSequence;
            Object obj2 = info;
            CharSequence charSequence2 = roleDescription;
            AccessibilityNodeInfoCompatKitKat.setRoleDescription(info, roleDescription);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoStubImpl */
    static class AccessibilityNodeInfoStubImpl implements AccessibilityNodeInfoImpl {
        AccessibilityNodeInfoStubImpl() {
        }

        public Object newAccessibilityAction(int i, CharSequence charSequence) {
            int i2 = i;
            CharSequence charSequence2 = charSequence;
            return null;
        }

        public Object obtain() {
            return null;
        }

        public Object obtain(View view) {
            View view2 = view;
            return null;
        }

        public Object obtain(View view, int i) {
            View view2 = view;
            int i2 = i;
            return null;
        }

        public Object obtain(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void addAction(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void addAction(Object obj, Object obj2) {
            Object obj3 = obj;
            Object obj4 = obj2;
        }

        public boolean removeAction(Object obj, Object obj2) {
            Object obj3 = obj;
            Object obj4 = obj2;
            return false;
        }

        public int getAccessibilityActionId(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public CharSequence getAccessibilityActionLabel(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void addChild(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void addChild(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public boolean removeChild(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
            return false;
        }

        public boolean removeChild(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
            return false;
        }

        public List<Object> findAccessibilityNodeInfosByText(Object obj, String str) {
            Object obj2 = obj;
            String str2 = str;
            return Collections.emptyList();
        }

        public int getActions(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public void getBoundsInParent(Object obj, Rect rect) {
            Object obj2 = obj;
            Rect rect2 = rect;
        }

        public void getBoundsInScreen(Object obj, Rect rect) {
            Object obj2 = obj;
            Rect rect2 = rect;
        }

        public Object getChild(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
            return null;
        }

        public int getChildCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public CharSequence getClassName(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public CharSequence getContentDescription(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public CharSequence getPackageName(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public Object getParent(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public CharSequence getText(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public int getWindowId(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public boolean isCheckable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isChecked(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isClickable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isEnabled(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isFocusable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isFocused(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isVisibleToUser(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isAccessibilityFocused(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isLongClickable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isPassword(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isScrollable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isSelected(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean performAction(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
            return false;
        }

        public boolean performAction(Object obj, int i, Bundle bundle) {
            Object obj2 = obj;
            int i2 = i;
            Bundle bundle2 = bundle;
            return false;
        }

        public void setMovementGranularities(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public int getMovementGranularities(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public void setBoundsInParent(Object obj, Rect rect) {
            Object obj2 = obj;
            Rect rect2 = rect;
        }

        public void setBoundsInScreen(Object obj, Rect rect) {
            Object obj2 = obj;
            Rect rect2 = rect;
        }

        public void setCheckable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setChecked(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setClassName(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void setClickable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setContentDescription(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void setEnabled(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setFocusable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setFocused(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setVisibleToUser(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setAccessibilityFocused(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setLongClickable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setPackageName(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void setParent(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setPassword(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setScrollable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setSelected(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setSource(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setSource(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public Object findFocus(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
            return null;
        }

        public Object focusSearch(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
            return null;
        }

        public void setText(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void recycle(Object obj) {
            Object obj2 = obj;
        }

        public void setParent(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public String getViewIdResourceName(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setViewIdResourceName(Object obj, String str) {
            Object obj2 = obj;
            String str2 = str;
        }

        public int getLiveRegion(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public void setLiveRegion(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public Object getCollectionInfo(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setCollectionInfo(Object obj, Object obj2) {
            Object obj3 = obj;
            Object obj4 = obj2;
        }

        public Object getCollectionItemInfo(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setCollectionItemInfo(Object obj, Object obj2) {
            Object obj3 = obj;
            Object obj4 = obj2;
        }

        public Object getRangeInfo(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setRangeInfo(Object obj, Object obj2) {
            Object obj3 = obj;
            Object obj4 = obj2;
        }

        public List<Object> getActionList(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
            int i4 = i;
            int i5 = i2;
            boolean z2 = z;
            int i6 = i3;
            return null;
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z) {
            int i3 = i;
            int i4 = i2;
            boolean z2 = z;
            return null;
        }

        public int getCollectionInfoColumnCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getCollectionInfoRowCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public boolean isCollectionInfoHierarchical(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            boolean z3 = z;
            boolean z4 = z2;
            return null;
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            boolean z2 = z;
            return null;
        }

        public int getCollectionItemColumnIndex(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getCollectionItemColumnSpan(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getCollectionItemRowIndex(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getCollectionItemRowSpan(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public boolean isCollectionItemHeading(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isCollectionItemSelected(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public Object obtainRangeInfo(int i, float f, float f2, float f3) {
            int i2 = i;
            float f4 = f;
            float f5 = f2;
            float f6 = f3;
            return null;
        }

        public Object getTraversalBefore(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setTraversalBefore(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setTraversalBefore(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public Object getTraversalAfter(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setTraversalAfter(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setTraversalAfter(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public void setContentInvalid(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public boolean isContentInvalid(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void setError(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public CharSequence getError(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setLabelFor(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setLabelFor(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public Object getLabelFor(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setLabeledBy(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setLabeledBy(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public Object getLabeledBy(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public boolean canOpenPopup(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void setCanOpenPopup(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public List<Object> findAccessibilityNodeInfosByViewId(Object obj, String str) {
            Object obj2 = obj;
            String str2 = str;
            return Collections.emptyList();
        }

        public Bundle getExtras(Object obj) {
            Object obj2 = obj;
            return new Bundle();
        }

        public int getInputType(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public void setInputType(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setMaxTextLength(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public int getMaxTextLength(Object obj) {
            Object obj2 = obj;
            return -1;
        }

        public void setTextSelection(Object obj, int i, int i2) {
            Object obj2 = obj;
            int i3 = i;
            int i4 = i2;
        }

        public int getTextSelectionStart(Object obj) {
            Object obj2 = obj;
            return -1;
        }

        public int getTextSelectionEnd(Object obj) {
            Object obj2 = obj;
            return -1;
        }

        public Object getWindow(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public boolean isDismissable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void setDismissable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public boolean isEditable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void setEditable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public boolean isMultiLine(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void setMultiLine(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public boolean refresh(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public CharSequence getRoleDescription(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public void setRoleDescription(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public Object getActionScrollToPosition() {
            return null;
        }

        public Object getActionSetProgress() {
            return null;
        }

        public boolean isContextClickable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void setContextClickable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public Object getActionShowOnScreen() {
            return null;
        }

        public Object getActionScrollUp() {
            return null;
        }

        public Object getActionScrollDown() {
            return null;
        }

        public Object getActionScrollLeft() {
            return null;
        }

        public Object getActionScrollRight() {
            return null;
        }

        public Object getActionContextClick() {
            return null;
        }

        public int getCollectionInfoSelectionMode(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getDrawingOrder(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public void setDrawingOrder(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public boolean isImportantForAccessibility(Object obj) {
            Object obj2 = obj;
            return true;
        }

        public void setImportantForAccessibility(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionInfoCompat */
    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int i, int i2, boolean z, int i3) {
            int rowCount = i;
            int columnCount = i2;
            int selectionMode = i3;
            int i4 = rowCount;
            int i5 = columnCount;
            int i6 = selectionMode;
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo(rowCount, columnCount, z, selectionMode));
        }

        public static CollectionInfoCompat obtain(int i, int i2, boolean z) {
            int rowCount = i;
            int columnCount = i2;
            int i3 = rowCount;
            int i4 = columnCount;
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo(rowCount, columnCount, z));
        }

        CollectionInfoCompat(Object obj) {
            Object info = obj;
            Object obj2 = info;
            this.mInfo = info;
        }

        public int getColumnCount() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoColumnCount(this.mInfo);
        }

        public int getRowCount() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoRowCount(this.mInfo);
        }

        public boolean isHierarchical() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionInfoHierarchical(this.mInfo);
        }

        public int getSelectionMode() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoSelectionMode(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionItemInfoCompat */
    public static class CollectionItemInfoCompat {
        final Object mInfo;

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            int rowIndex = i;
            int rowSpan = i2;
            int columnIndex = i3;
            int columnSpan = i4;
            int i5 = rowIndex;
            int i6 = rowSpan;
            int i7 = columnIndex;
            int i8 = columnSpan;
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, z, z2));
        }

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z) {
            int rowIndex = i;
            int rowSpan = i2;
            int columnIndex = i3;
            int columnSpan = i4;
            int i5 = rowIndex;
            int i6 = rowSpan;
            int i7 = columnIndex;
            int i8 = columnSpan;
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, z));
        }

        CollectionItemInfoCompat(Object obj) {
            Object info = obj;
            Object obj2 = info;
            this.mInfo = info;
        }

        public int getColumnIndex() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnIndex(this.mInfo);
        }

        public int getColumnSpan() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnSpan(this.mInfo);
        }

        public int getRowIndex() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowIndex(this.mInfo);
        }

        public int getRowSpan() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowSpan(this.mInfo);
        }

        public boolean isHeading() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemHeading(this.mInfo);
        }

        public boolean isSelected() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemSelected(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$RangeInfoCompat */
    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        public static RangeInfoCompat obtain(int i, float f, float f2, float f3) {
            int type = i;
            float min = f;
            float max = f2;
            float current = f3;
            int i2 = type;
            float f4 = min;
            float f5 = max;
            float f6 = current;
            return new RangeInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainRangeInfo(type, min, max, current));
        }

        RangeInfoCompat(Object obj) {
            Object info = obj;
            Object obj2 = info;
            this.mInfo = info;
        }

        public float getCurrent() {
            return RangeInfo.getCurrent(this.mInfo);
        }

        public float getMax() {
            return RangeInfo.getMax(this.mInfo);
        }

        public float getMin() {
            return RangeInfo.getMin(this.mInfo);
        }

        public int getType() {
            return RangeInfo.getType(this.mInfo);
        }
    }

    static {
        if (VERSION.SDK_INT >= 24) {
            IMPL = new AccessibilityNodeInfoApi24Impl();
        } else if (VERSION.SDK_INT >= 23) {
            IMPL = new AccessibilityNodeInfoApi23Impl();
        } else if (VERSION.SDK_INT >= 22) {
            IMPL = new AccessibilityNodeInfoApi22Impl();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityNodeInfoApi21Impl();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeInfoKitKatImpl();
        } else if (VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityNodeInfoJellybeanMr2Impl();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new AccessibilityNodeInfoJellybeanMr1Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeInfoJellybeanImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new AccessibilityNodeInfoStubImpl();
        } else {
            IMPL = new AccessibilityNodeInfoIcsImpl();
        }
    }

    public AccessibilityNodeInfoCompat(Object obj) {
        Object info = obj;
        Object obj2 = info;
        this.mInfo = info;
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object obj) {
        Object object = obj;
        Object obj2 = object;
        if (object == null) {
            return null;
        }
        return new AccessibilityNodeInfoCompat(object);
    }

    public Object getInfo() {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        View source = view;
        View view2 = source;
        return wrapNonNullInstance(IMPL.obtain(source));
    }

    public static AccessibilityNodeInfoCompat obtain(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        return wrapNonNullInstance(IMPL.obtain(root, virtualDescendantId));
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrapNonNullInstance(IMPL.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
        return wrapNonNullInstance(IMPL.obtain(info.mInfo));
    }

    public void setSource(View view) {
        View source = view;
        View view2 = source;
        IMPL.setSource(this.mInfo, source);
    }

    public void setSource(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.setSource(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat findFocus(int i) {
        int focus = i;
        int i2 = focus;
        return wrapNonNullInstance(IMPL.findFocus(this.mInfo, focus));
    }

    public AccessibilityNodeInfoCompat focusSearch(int i) {
        int direction = i;
        int i2 = direction;
        return wrapNonNullInstance(IMPL.focusSearch(this.mInfo, direction));
    }

    public int getWindowId() {
        return IMPL.getWindowId(this.mInfo);
    }

    public int getChildCount() {
        return IMPL.getChildCount(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getChild(int i) {
        int index = i;
        int i2 = index;
        return wrapNonNullInstance(IMPL.getChild(this.mInfo, index));
    }

    public void addChild(View view) {
        View child = view;
        View view2 = child;
        IMPL.addChild(this.mInfo, child);
    }

    public void addChild(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.addChild(this.mInfo, root, virtualDescendantId);
    }

    public boolean removeChild(View view) {
        View child = view;
        View view2 = child;
        return IMPL.removeChild(this.mInfo, child);
    }

    public boolean removeChild(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        return IMPL.removeChild(this.mInfo, root, virtualDescendantId);
    }

    public int getActions() {
        return IMPL.getActions(this.mInfo);
    }

    public void addAction(int i) {
        int action = i;
        int i2 = action;
        IMPL.addAction(this.mInfo, action);
    }

    public void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        AccessibilityActionCompat action = accessibilityActionCompat;
        AccessibilityActionCompat accessibilityActionCompat2 = action;
        IMPL.addAction(this.mInfo, action.mAction);
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        AccessibilityActionCompat action = accessibilityActionCompat;
        AccessibilityActionCompat accessibilityActionCompat2 = action;
        return IMPL.removeAction(this.mInfo, action.mAction);
    }

    public boolean performAction(int i) {
        int action = i;
        int i2 = action;
        return IMPL.performAction(this.mInfo, action);
    }

    public boolean performAction(int i, Bundle bundle) {
        int action = i;
        Bundle arguments = bundle;
        int i2 = action;
        Bundle bundle2 = arguments;
        return IMPL.performAction(this.mInfo, action, arguments);
    }

    public void setMovementGranularities(int i) {
        int granularities = i;
        int i2 = granularities;
        IMPL.setMovementGranularities(this.mInfo, granularities);
    }

    public int getMovementGranularities() {
        return IMPL.getMovementGranularities(this.mInfo);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String str) {
        String text = str;
        String str2 = text;
        ArrayList arrayList = new ArrayList();
        List findAccessibilityNodeInfosByText = IMPL.findAccessibilityNodeInfosByText(this.mInfo, text);
        List list = findAccessibilityNodeInfosByText;
        int infoCount = findAccessibilityNodeInfosByText.size();
        for (int i = 0; i < infoCount; i++) {
            boolean add = arrayList.add(new AccessibilityNodeInfoCompat(list.get(i)));
        }
        return arrayList;
    }

    public AccessibilityNodeInfoCompat getParent() {
        return wrapNonNullInstance(IMPL.getParent(this.mInfo));
    }

    public void setParent(View view) {
        View parent = view;
        View view2 = parent;
        IMPL.setParent(this.mInfo, parent);
    }

    public void setParent(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        this.mParentVirtualDescendantId = virtualDescendantId;
        IMPL.setParent(this.mInfo, root, virtualDescendantId);
    }

    public void getBoundsInParent(Rect rect) {
        Rect outBounds = rect;
        Rect rect2 = outBounds;
        IMPL.getBoundsInParent(this.mInfo, outBounds);
    }

    public void setBoundsInParent(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        IMPL.setBoundsInParent(this.mInfo, bounds);
    }

    public void getBoundsInScreen(Rect rect) {
        Rect outBounds = rect;
        Rect rect2 = outBounds;
        IMPL.getBoundsInScreen(this.mInfo, outBounds);
    }

    public void setBoundsInScreen(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        IMPL.setBoundsInScreen(this.mInfo, bounds);
    }

    public boolean isCheckable() {
        return IMPL.isCheckable(this.mInfo);
    }

    public void setCheckable(boolean z) {
        IMPL.setCheckable(this.mInfo, z);
    }

    public boolean isChecked() {
        return IMPL.isChecked(this.mInfo);
    }

    public void setChecked(boolean z) {
        IMPL.setChecked(this.mInfo, z);
    }

    public boolean isFocusable() {
        return IMPL.isFocusable(this.mInfo);
    }

    public void setFocusable(boolean z) {
        IMPL.setFocusable(this.mInfo, z);
    }

    public boolean isFocused() {
        return IMPL.isFocused(this.mInfo);
    }

    public void setFocused(boolean z) {
        IMPL.setFocused(this.mInfo, z);
    }

    public boolean isVisibleToUser() {
        return IMPL.isVisibleToUser(this.mInfo);
    }

    public void setVisibleToUser(boolean z) {
        IMPL.setVisibleToUser(this.mInfo, z);
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public void setAccessibilityFocused(boolean z) {
        IMPL.setAccessibilityFocused(this.mInfo, z);
    }

    public boolean isSelected() {
        return IMPL.isSelected(this.mInfo);
    }

    public void setSelected(boolean z) {
        IMPL.setSelected(this.mInfo, z);
    }

    public boolean isClickable() {
        return IMPL.isClickable(this.mInfo);
    }

    public void setClickable(boolean z) {
        IMPL.setClickable(this.mInfo, z);
    }

    public boolean isLongClickable() {
        return IMPL.isLongClickable(this.mInfo);
    }

    public void setLongClickable(boolean z) {
        IMPL.setLongClickable(this.mInfo, z);
    }

    public boolean isEnabled() {
        return IMPL.isEnabled(this.mInfo);
    }

    public void setEnabled(boolean z) {
        IMPL.setEnabled(this.mInfo, z);
    }

    public boolean isPassword() {
        return IMPL.isPassword(this.mInfo);
    }

    public void setPassword(boolean z) {
        IMPL.setPassword(this.mInfo, z);
    }

    public boolean isScrollable() {
        return IMPL.isScrollable(this.mInfo);
    }

    public void setScrollable(boolean z) {
        IMPL.setScrollable(this.mInfo, z);
    }

    public boolean isImportantForAccessibility() {
        return IMPL.isImportantForAccessibility(this.mInfo);
    }

    public void setImportantForAccessibility(boolean z) {
        IMPL.setImportantForAccessibility(this.mInfo, z);
    }

    public CharSequence getPackageName() {
        return IMPL.getPackageName(this.mInfo);
    }

    public void setPackageName(CharSequence charSequence) {
        CharSequence packageName = charSequence;
        CharSequence charSequence2 = packageName;
        IMPL.setPackageName(this.mInfo, packageName);
    }

    public CharSequence getClassName() {
        return IMPL.getClassName(this.mInfo);
    }

    public void setClassName(CharSequence charSequence) {
        CharSequence className = charSequence;
        CharSequence charSequence2 = className;
        IMPL.setClassName(this.mInfo, className);
    }

    public CharSequence getText() {
        return IMPL.getText(this.mInfo);
    }

    public void setText(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        IMPL.setText(this.mInfo, text);
    }

    public CharSequence getContentDescription() {
        return IMPL.getContentDescription(this.mInfo);
    }

    public void setContentDescription(CharSequence charSequence) {
        CharSequence contentDescription = charSequence;
        CharSequence charSequence2 = contentDescription;
        IMPL.setContentDescription(this.mInfo, contentDescription);
    }

    public void recycle() {
        IMPL.recycle(this.mInfo);
    }

    public void setViewIdResourceName(String str) {
        String viewId = str;
        String str2 = viewId;
        IMPL.setViewIdResourceName(this.mInfo, viewId);
    }

    public String getViewIdResourceName() {
        return IMPL.getViewIdResourceName(this.mInfo);
    }

    public int getLiveRegion() {
        return IMPL.getLiveRegion(this.mInfo);
    }

    public void setLiveRegion(int i) {
        int mode = i;
        int i2 = mode;
        IMPL.setLiveRegion(this.mInfo, mode);
    }

    public int getDrawingOrder() {
        return IMPL.getDrawingOrder(this.mInfo);
    }

    public void setDrawingOrder(int i) {
        int drawingOrderInParent = i;
        int i2 = drawingOrderInParent;
        IMPL.setDrawingOrder(this.mInfo, drawingOrderInParent);
    }

    public CollectionInfoCompat getCollectionInfo() {
        Object collectionInfo = IMPL.getCollectionInfo(this.mInfo);
        Object info = collectionInfo;
        if (collectionInfo != null) {
            return new CollectionInfoCompat(info);
        }
        return null;
    }

    public void setCollectionInfo(Object obj) {
        Object collectionInfo = obj;
        Object obj2 = collectionInfo;
        IMPL.setCollectionInfo(this.mInfo, ((CollectionInfoCompat) collectionInfo).mInfo);
    }

    public void setCollectionItemInfo(Object obj) {
        Object collectionItemInfo = obj;
        Object obj2 = collectionItemInfo;
        IMPL.setCollectionItemInfo(this.mInfo, ((CollectionItemInfoCompat) collectionItemInfo).mInfo);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        Object collectionItemInfo = IMPL.getCollectionItemInfo(this.mInfo);
        Object info = collectionItemInfo;
        if (collectionItemInfo != null) {
            return new CollectionItemInfoCompat(info);
        }
        return null;
    }

    public RangeInfoCompat getRangeInfo() {
        Object rangeInfo = IMPL.getRangeInfo(this.mInfo);
        Object info = rangeInfo;
        if (rangeInfo != null) {
            return new RangeInfoCompat(info);
        }
        return null;
    }

    public void setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        RangeInfoCompat rangeInfo = rangeInfoCompat;
        RangeInfoCompat rangeInfoCompat2 = rangeInfo;
        IMPL.setRangeInfo(this.mInfo, rangeInfo.mInfo);
    }

    public List<AccessibilityActionCompat> getActionList() {
        List actionList = IMPL.getActionList(this.mInfo);
        List list = actionList;
        if (actionList == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int actionCount = list.size();
        for (int i = 0; i < actionCount; i++) {
            boolean add = arrayList.add(new AccessibilityActionCompat(list.get(i)));
        }
        return arrayList;
    }

    public void setContentInvalid(boolean z) {
        IMPL.setContentInvalid(this.mInfo, z);
    }

    public boolean isContentInvalid() {
        return IMPL.isContentInvalid(this.mInfo);
    }

    public boolean isContextClickable() {
        return IMPL.isContextClickable(this.mInfo);
    }

    public void setContextClickable(boolean z) {
        IMPL.setContextClickable(this.mInfo, z);
    }

    public void setError(CharSequence charSequence) {
        CharSequence error = charSequence;
        CharSequence charSequence2 = error;
        IMPL.setError(this.mInfo, error);
    }

    public CharSequence getError() {
        return IMPL.getError(this.mInfo);
    }

    public void setLabelFor(View view) {
        View labeled = view;
        View view2 = labeled;
        IMPL.setLabelFor(this.mInfo, labeled);
    }

    public void setLabelFor(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.setLabelFor(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        return wrapNonNullInstance(IMPL.getLabelFor(this.mInfo));
    }

    public void setLabeledBy(View view) {
        View label = view;
        View view2 = label;
        IMPL.setLabeledBy(this.mInfo, label);
    }

    public void setLabeledBy(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.setLabeledBy(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        return wrapNonNullInstance(IMPL.getLabeledBy(this.mInfo));
    }

    public boolean canOpenPopup() {
        return IMPL.canOpenPopup(this.mInfo);
    }

    public void setCanOpenPopup(boolean z) {
        IMPL.setCanOpenPopup(this.mInfo, z);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String str) {
        String viewId = str;
        String str2 = viewId;
        List findAccessibilityNodeInfosByViewId = IMPL.findAccessibilityNodeInfosByViewId(this.mInfo, viewId);
        List<Object> list = findAccessibilityNodeInfosByViewId;
        if (findAccessibilityNodeInfosByViewId == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object accessibilityNodeInfoCompat : list) {
            boolean add = arrayList.add(new AccessibilityNodeInfoCompat(accessibilityNodeInfoCompat));
        }
        return arrayList;
    }

    public Bundle getExtras() {
        return IMPL.getExtras(this.mInfo);
    }

    public int getInputType() {
        return IMPL.getInputType(this.mInfo);
    }

    public void setInputType(int i) {
        int inputType = i;
        int i2 = inputType;
        IMPL.setInputType(this.mInfo, inputType);
    }

    public void setMaxTextLength(int i) {
        int max = i;
        int i2 = max;
        IMPL.setMaxTextLength(this.mInfo, max);
    }

    public int getMaxTextLength() {
        return IMPL.getMaxTextLength(this.mInfo);
    }

    public void setTextSelection(int i, int i2) {
        int start = i;
        int end = i2;
        int i3 = start;
        int i4 = end;
        IMPL.setTextSelection(this.mInfo, start, end);
    }

    public int getTextSelectionStart() {
        return IMPL.getTextSelectionStart(this.mInfo);
    }

    public int getTextSelectionEnd() {
        return IMPL.getTextSelectionEnd(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        return wrapNonNullInstance(IMPL.getTraversalBefore(this.mInfo));
    }

    public void setTraversalBefore(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.setTraversalBefore(this.mInfo, view2);
    }

    public void setTraversalBefore(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.setTraversalBefore(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        return wrapNonNullInstance(IMPL.getTraversalAfter(this.mInfo));
    }

    public void setTraversalAfter(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.setTraversalAfter(this.mInfo, view2);
    }

    public void setTraversalAfter(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.setTraversalAfter(this.mInfo, root, virtualDescendantId);
    }

    public AccessibilityWindowInfoCompat getWindow() {
        return AccessibilityWindowInfoCompat.wrapNonNullInstance(IMPL.getWindow(this.mInfo));
    }

    public boolean isDismissable() {
        return IMPL.isDismissable(this.mInfo);
    }

    public void setDismissable(boolean z) {
        IMPL.setDismissable(this.mInfo, z);
    }

    public boolean isEditable() {
        return IMPL.isEditable(this.mInfo);
    }

    public void setEditable(boolean z) {
        IMPL.setEditable(this.mInfo, z);
    }

    public boolean isMultiLine() {
        return IMPL.isMultiLine(this.mInfo);
    }

    public void setMultiLine(boolean z) {
        IMPL.setMultiLine(this.mInfo, z);
    }

    public boolean refresh() {
        return IMPL.refresh(this.mInfo);
    }

    @Nullable
    public CharSequence getRoleDescription() {
        return IMPL.getRoleDescription(this.mInfo);
    }

    public void setRoleDescription(@Nullable CharSequence charSequence) {
        CharSequence roleDescription = charSequence;
        CharSequence charSequence2 = roleDescription;
        IMPL.setRoleDescription(this.mInfo, roleDescription);
    }

    public int hashCode() {
        return this.mInfo != null ? this.mInfo.hashCode() : 0;
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
        AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj2;
        if (this.mInfo != null) {
            if (!this.mInfo.equals(other.mInfo)) {
                return false;
            }
        } else if (other.mInfo != null) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder builder = sb;
        StringBuilder append = sb.append(super.toString());
        Rect bounds = new Rect();
        getBoundsInParent(bounds);
        StringBuilder append2 = builder.append("; boundsInParent: " + bounds);
        getBoundsInScreen(bounds);
        StringBuilder append3 = builder.append("; boundsInScreen: " + bounds);
        StringBuilder append4 = builder.append("; packageName: ").append(getPackageName());
        StringBuilder append5 = builder.append("; className: ").append(getClassName());
        StringBuilder append6 = builder.append("; text: ").append(getText());
        StringBuilder append7 = builder.append("; contentDescription: ").append(getContentDescription());
        StringBuilder append8 = builder.append("; viewId: ").append(getViewIdResourceName());
        StringBuilder append9 = builder.append("; checkable: ").append(isCheckable());
        StringBuilder append10 = builder.append("; checked: ").append(isChecked());
        StringBuilder append11 = builder.append("; focusable: ").append(isFocusable());
        StringBuilder append12 = builder.append("; focused: ").append(isFocused());
        StringBuilder append13 = builder.append("; selected: ").append(isSelected());
        StringBuilder append14 = builder.append("; clickable: ").append(isClickable());
        StringBuilder append15 = builder.append("; longClickable: ").append(isLongClickable());
        StringBuilder append16 = builder.append("; enabled: ").append(isEnabled());
        StringBuilder append17 = builder.append("; password: ").append(isPassword());
        StringBuilder append18 = builder.append("; scrollable: " + isScrollable());
        StringBuilder append19 = builder.append("; [");
        int actionBits = getActions();
        while (actionBits != 0) {
            int action = 1 << Integer.numberOfTrailingZeros(actionBits);
            int i = actionBits & (action ^ -1);
            actionBits = i;
            StringBuilder append20 = builder.append(getActionSymbolicName(action));
            if (i != 0) {
                StringBuilder append21 = builder.append(", ");
            }
        }
        StringBuilder append22 = builder.append("]");
        return builder.toString();
    }

    private static String getActionSymbolicName(int i) {
        int action = i;
        int i2 = action;
        switch (action) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }
}
