package android.support.p003v7.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportMenu;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.util.SimpleArrayMap;
import android.support.p003v7.view.ActionMode.Callback;
import android.support.p003v7.view.menu.MenuWrapperFactory;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

@TargetApi(11)
@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.SupportActionModeWrapper */
public class SupportActionModeWrapper extends ActionMode {
    final Context mContext;
    final ActionMode mWrappedObject;

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.view.SupportActionModeWrapper$CallbackWrapper */
    public static class CallbackWrapper implements Callback {
        final ArrayList<SupportActionModeWrapper> mActionModes = new ArrayList<>();
        final Context mContext;
        final SimpleArrayMap<Menu, Menu> mMenus = new SimpleArrayMap<>();
        final ActionMode.Callback mWrappedCallback;

        public CallbackWrapper(Context context, ActionMode.Callback callback) {
            Context context2 = context;
            ActionMode.Callback supportCallback = callback;
            Context context3 = context2;
            ActionMode.Callback callback2 = supportCallback;
            this.mContext = context2;
            this.mWrappedCallback = supportCallback;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            ActionMode mode = actionMode;
            Menu menu2 = menu;
            ActionMode actionMode2 = mode;
            Menu menu3 = menu2;
            return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(mode), getMenuWrapper(menu2));
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            ActionMode mode = actionMode;
            Menu menu2 = menu;
            ActionMode actionMode2 = mode;
            Menu menu3 = menu2;
            return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(mode), getMenuWrapper(menu2));
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            ActionMode mode = actionMode;
            MenuItem item = menuItem;
            ActionMode actionMode2 = mode;
            MenuItem menuItem2 = item;
            return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(mode), MenuWrapperFactory.wrapSupportMenuItem(this.mContext, (SupportMenuItem) item));
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            ActionMode mode = actionMode;
            ActionMode actionMode2 = mode;
            this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(mode));
        }

        private Menu getMenuWrapper(Menu menu) {
            Menu menu2 = menu;
            Menu menu3 = menu2;
            Menu menu4 = (Menu) this.mMenus.get(menu2);
            Menu wrapper = menu4;
            if (menu4 == null) {
                wrapper = MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu) menu2);
                Object put = this.mMenus.put(menu2, wrapper);
            }
            return wrapper;
        }

        public ActionMode getActionModeWrapper(ActionMode actionMode) {
            ActionMode mode = actionMode;
            ActionMode actionMode2 = mode;
            int count = this.mActionModes.size();
            for (int i = 0; i < count; i++) {
                SupportActionModeWrapper supportActionModeWrapper = (SupportActionModeWrapper) this.mActionModes.get(i);
                SupportActionModeWrapper wrapper = supportActionModeWrapper;
                if (supportActionModeWrapper != null && wrapper.mWrappedObject == mode) {
                    return wrapper;
                }
            }
            SupportActionModeWrapper wrapper2 = new SupportActionModeWrapper(this.mContext, mode);
            boolean add = this.mActionModes.add(wrapper2);
            return wrapper2;
        }
    }

    public SupportActionModeWrapper(Context context, ActionMode actionMode) {
        Context context2 = context;
        ActionMode supportActionMode = actionMode;
        Context context3 = context2;
        ActionMode actionMode2 = supportActionMode;
        this.mContext = context2;
        this.mWrappedObject = supportActionMode;
    }

    public Object getTag() {
        return this.mWrappedObject.getTag();
    }

    public void setTag(Object obj) {
        Object tag = obj;
        Object obj2 = tag;
        this.mWrappedObject.setTag(tag);
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mWrappedObject.setTitle(title);
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        this.mWrappedObject.setSubtitle(subtitle);
    }

    public void invalidate() {
        this.mWrappedObject.invalidate();
    }

    public void finish() {
        this.mWrappedObject.finish();
    }

    public Menu getMenu() {
        return MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu) this.mWrappedObject.getMenu());
    }

    public CharSequence getTitle() {
        return this.mWrappedObject.getTitle();
    }

    public void setTitle(int i) {
        int resId = i;
        int i2 = resId;
        this.mWrappedObject.setTitle(resId);
    }

    public CharSequence getSubtitle() {
        return this.mWrappedObject.getSubtitle();
    }

    public void setSubtitle(int i) {
        int resId = i;
        int i2 = resId;
        this.mWrappedObject.setSubtitle(resId);
    }

    public View getCustomView() {
        return this.mWrappedObject.getCustomView();
    }

    public void setCustomView(View view) {
        View view2 = view;
        View view3 = view2;
        this.mWrappedObject.setCustomView(view2);
    }

    public MenuInflater getMenuInflater() {
        return this.mWrappedObject.getMenuInflater();
    }

    public boolean getTitleOptionalHint() {
        return this.mWrappedObject.getTitleOptionalHint();
    }

    public void setTitleOptionalHint(boolean z) {
        this.mWrappedObject.setTitleOptionalHint(z);
    }

    public boolean isTitleOptional() {
        return this.mWrappedObject.isTitleOptional();
    }
}
