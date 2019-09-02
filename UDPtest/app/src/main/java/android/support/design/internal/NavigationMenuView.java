package android.support.design.internal;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuView;
import android.support.p003v7.widget.LinearLayoutManager;
import android.support.p003v7.widget.RecyclerView;
import android.util.AttributeSet;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenuView extends RecyclerView implements MenuView {
    public NavigationMenuView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public NavigationMenuView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public NavigationMenuView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        setLayoutManager(new LinearLayoutManager(context2, 1, false));
    }

    public void initialize(MenuBuilder menuBuilder) {
        MenuBuilder menuBuilder2 = menuBuilder;
    }

    public int getWindowAnimations() {
        return 0;
    }
}
