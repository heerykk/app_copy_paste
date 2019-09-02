package android.support.p003v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.view.ActionProvider;
import android.view.ActionProvider.VisibilityListener;
import android.view.MenuItem;
import android.view.View;

@RequiresApi(16)
@RestrictTo({Scope.LIBRARY_GROUP})
@TargetApi(16)
/* renamed from: android.support.v7.view.menu.MenuItemWrapperJB */
class MenuItemWrapperJB extends MenuItemWrapperICS {

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperJB$ActionProviderWrapperJB */
    class ActionProviderWrapperJB extends ActionProviderWrapper implements VisibilityListener {
        ActionProvider.VisibilityListener mListener;
        final /* synthetic */ MenuItemWrapperJB this$0;

        public ActionProviderWrapperJB(MenuItemWrapperJB menuItemWrapperJB, Context context, android.view.ActionProvider actionProvider) {
            MenuItemWrapperJB this$02 = menuItemWrapperJB;
            Context context2 = context;
            android.view.ActionProvider inner = actionProvider;
            MenuItemWrapperJB menuItemWrapperJB2 = this$02;
            Context context3 = context2;
            android.view.ActionProvider actionProvider2 = inner;
            this.this$0 = this$02;
            super(this$02, context2, inner);
        }

        public View onCreateActionView(MenuItem menuItem) {
            MenuItem forItem = menuItem;
            MenuItem menuItem2 = forItem;
            return this.mInner.onCreateActionView(forItem);
        }

        public boolean overridesItemVisibility() {
            return this.mInner.overridesItemVisibility();
        }

        public boolean isVisible() {
            return this.mInner.isVisible();
        }

        public void refreshVisibility() {
            this.mInner.refreshVisibility();
        }

        public void setVisibilityListener(ActionProvider.VisibilityListener visibilityListener) {
            ActionProvider.VisibilityListener listener = visibilityListener;
            ActionProvider.VisibilityListener visibilityListener2 = listener;
            this.mListener = listener;
            this.mInner.setVisibilityListener(listener == null ? null : this);
        }

        public void onActionProviderVisibilityChanged(boolean z) {
            boolean isVisible = z;
            if (this.mListener != null) {
                this.mListener.onActionProviderVisibilityChanged(isVisible);
            }
        }
    }

    MenuItemWrapperJB(Context context, SupportMenuItem supportMenuItem) {
        Context context2 = context;
        SupportMenuItem object = supportMenuItem;
        Context context3 = context2;
        SupportMenuItem supportMenuItem2 = object;
        super(context2, object);
    }

    /* access modifiers changed from: 0000 */
    public ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider actionProvider) {
        android.view.ActionProvider provider = actionProvider;
        android.view.ActionProvider actionProvider2 = provider;
        return new ActionProviderWrapperJB(this, this.mContext, provider);
    }
}
