package android.support.p003v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportMenu;
import android.support.p000v4.view.ActionProvider;
import android.support.p000v4.view.MenuItemCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuItemWrapperICS;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.SupportMenuInflater */
public class SupportMenuInflater extends MenuInflater {
    static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    static final String LOG_TAG = "SupportMenuInflater";
    static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    final Object[] mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    final Object[] mActionViewConstructorArguments;
    Context mContext;
    private Object mRealOwner;

    /* renamed from: android.support.v7.view.SupportMenuInflater$InflatedOnMenuItemClickListener */
    private static class InflatedOnMenuItemClickListener implements OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES;
        private Method mMethod;
        private Object mRealOwner;

        static {
            Class[] clsArr = new Class[1];
            clsArr[0] = MenuItem.class;
            PARAM_TYPES = clsArr;
        }

        public InflatedOnMenuItemClickListener(Object obj, String str) {
            Object realOwner = obj;
            String methodName = str;
            Object obj2 = realOwner;
            String str2 = methodName;
            this.mRealOwner = realOwner;
            Class cls = realOwner.getClass();
            try {
                this.mMethod = cls.getMethod(methodName, PARAM_TYPES);
            } catch (Exception e) {
                Exception exc = e;
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + methodName + " in class " + cls.getName());
                InflateException ex = inflateException;
                Throwable initCause = inflateException.initCause(e);
                throw ex;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            try {
                if (this.mMethod.getReturnType() != Boolean.TYPE) {
                    Object invoke = this.mMethod.invoke(this.mRealOwner, new Object[]{item});
                    return true;
                }
                return ((Boolean) this.mMethod.invoke(this.mRealOwner, new Object[]{item})).booleanValue();
            } catch (Exception e) {
                Exception exc = e;
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: android.support.v7.view.SupportMenuInflater$MenuState */
    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private String itemListenerMethodName;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private boolean itemVisible;
        private Menu menu;
        final /* synthetic */ SupportMenuInflater this$0;

        public MenuState(SupportMenuInflater supportMenuInflater, Menu menu2) {
            Menu menu3 = menu2;
            Menu menu4 = menu3;
            this.this$0 = supportMenuInflater;
            this.menu = menu3;
            resetGroup();
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }

        public void readGroup(AttributeSet attributeSet) {
            AttributeSet attrs = attributeSet;
            AttributeSet attributeSet2 = attrs;
            TypedArray a = this.this$0.mContext.obtainStyledAttributes(attrs, C0268R.styleable.MenuGroup);
            this.groupId = a.getResourceId(C0268R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = a.getInt(C0268R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = a.getInt(C0268R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = a.getInt(C0268R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = a.getBoolean(C0268R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = a.getBoolean(C0268R.styleable.MenuGroup_android_enabled, true);
            a.recycle();
        }

        public void readItem(AttributeSet attributeSet) {
            AttributeSet attrs = attributeSet;
            AttributeSet attributeSet2 = attrs;
            TypedArray a = this.this$0.mContext.obtainStyledAttributes(attrs, C0268R.styleable.MenuItem);
            this.itemId = a.getResourceId(C0268R.styleable.MenuItem_android_id, 0);
            int category = a.getInt(C0268R.styleable.MenuItem_android_menuCategory, this.groupCategory);
            int i = a.getInt(C0268R.styleable.MenuItem_android_orderInCategory, this.groupOrder);
            int i2 = i;
            this.itemCategoryOrder = (category & SupportMenu.CATEGORY_MASK) | (i & SupportMenu.USER_MASK);
            this.itemTitle = a.getText(C0268R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = a.getText(C0268R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = a.getResourceId(C0268R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = (char) getShortcut(a.getString(C0268R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemNumericShortcut = (char) getShortcut(a.getString(C0268R.styleable.MenuItem_android_numericShortcut));
            if (!a.hasValue(C0268R.styleable.MenuItem_android_checkable)) {
                this.itemCheckable = this.groupCheckable;
            } else {
                this.itemCheckable = !a.getBoolean(C0268R.styleable.MenuItem_android_checkable, false) ? 0 : 1;
            }
            this.itemChecked = a.getBoolean(C0268R.styleable.MenuItem_android_checked, false);
            this.itemVisible = a.getBoolean(C0268R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = a.getBoolean(C0268R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = a.getInt(C0268R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = a.getString(C0268R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = a.getResourceId(C0268R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = a.getString(C0268R.styleable.MenuItem_actionViewClass);
            this.itemActionProviderClassName = a.getString(C0268R.styleable.MenuItem_actionProviderClass);
            boolean hasActionProvider = this.itemActionProviderClassName != null;
            if (hasActionProvider && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, this.this$0.mActionProviderConstructorArguments);
            } else {
                if (hasActionProvider) {
                    int w = Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            a.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(String str) {
            String shortcutString = str;
            String str2 = shortcutString;
            if (shortcutString != null) {
                return shortcutString.charAt(0);
            }
            return 0;
        }

        private void setItem(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            MenuItem numericShortcut = item.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
            if (this.itemShowAsAction >= 0) {
                MenuItemCompat.setShowAsAction(item, this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (!this.this$0.mContext.isRestricted()) {
                    InflatedOnMenuItemClickListener inflatedOnMenuItemClickListener = new InflatedOnMenuItemClickListener(this.this$0.getRealOwner(), this.itemListenerMethodName);
                    MenuItem onMenuItemClickListener = item.setOnMenuItemClickListener(inflatedOnMenuItemClickListener);
                } else {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
            }
            MenuItemImpl menuItemImpl = !(item instanceof MenuItemImpl) ? null : (MenuItemImpl) item;
            if (this.itemCheckable >= 2) {
                if (item instanceof MenuItemImpl) {
                    ((MenuItemImpl) item).setExclusiveCheckable(true);
                } else if (item instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS) item).setExclusiveCheckable(true);
                }
            }
            boolean actionViewSpecified = false;
            if (this.itemActionViewClassName != null) {
                MenuItem actionView = MenuItemCompat.setActionView(item, (View) newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, this.this$0.mActionViewConstructorArguments));
                actionViewSpecified = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (actionViewSpecified) {
                    int w = Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                } else {
                    MenuItem actionView2 = MenuItemCompat.setActionView(item, this.itemActionViewLayout);
                }
            }
            if (this.itemActionProvider != null) {
                MenuItem actionProvider = MenuItemCompat.setActionProvider(item, this.itemActionProvider);
            }
        }

        public void addItem() {
            this.itemAdded = true;
            setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) {
            String className = str;
            Class<?>[] constructorSignature = clsArr;
            Object[] arguments = objArr;
            String str2 = className;
            Class<?>[] clsArr2 = constructorSignature;
            Object[] objArr2 = arguments;
            try {
                Class loadClass = this.this$0.mContext.getClassLoader().loadClass(className);
                Class cls = loadClass;
                Constructor constructor = loadClass.getConstructor(constructorSignature);
                Constructor constructor2 = constructor;
                constructor.setAccessible(true);
                return constructor2.newInstance(arguments);
            } catch (Exception e) {
                Exception exc = e;
                int w = Log.w(SupportMenuInflater.LOG_TAG, "Cannot instantiate class: " + className, e);
                return null;
            }
        }
    }

    static {
        Class[] clsArr = new Class[1];
        clsArr[0] = Context.class;
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = clsArr;
    }

    public SupportMenuInflater(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
        this.mContext = context2;
        Object[] objArr = new Object[1];
        objArr[0] = context2;
        this.mActionViewConstructorArguments = objArr;
    }

    public void inflate(int i, Menu menu) {
        int menuRes = i;
        Menu menu2 = menu;
        int i2 = menuRes;
        Menu menu3 = menu2;
        if (menu2 instanceof SupportMenu) {
            XmlResourceParser parser = null;
            try {
                XmlResourceParser layout = this.mContext.getResources().getLayout(menuRes);
                parser = layout;
                parseMenu(parser, Xml.asAttributeSet(layout), menu2);
                if (parser != null) {
                    parser.close();
                }
            } catch (XmlPullParserException e) {
                XmlPullParserException xmlPullParserException = e;
                throw new InflateException("Error inflating menu XML", e);
            } catch (IOException e2) {
                IOException iOException = e2;
                throw new InflateException("Error inflating menu XML", e2);
            } catch (Throwable th) {
                if (parser != null) {
                    parser.close();
                }
                throw th;
            }
        } else {
            super.inflate(menuRes, menu2);
        }
    }

    private void parseMenu(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Menu menu2 = menu;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Menu menu3 = menu2;
        MenuState menuState = new MenuState(this, menu2);
        int eventType = parser.getEventType();
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;
        while (true) {
            if (eventType != 2) {
                int next = parser.next();
                eventType = next;
                if (next == 1) {
                    break;
                }
            } else {
                String name = parser.getName();
                String tagName = name;
                if (!name.equals(XML_MENU)) {
                    throw new RuntimeException("Expecting menu, got " + tagName);
                }
                eventType = parser.next();
            }
        }
        boolean reachedEndOfMenu = false;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case 1:
                    throw new RuntimeException("Unexpected end of document");
                case 2:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    } else {
                        String name2 = parser.getName();
                        String tagName2 = name2;
                        if (!name2.equals(XML_GROUP)) {
                            if (!tagName2.equals(XML_ITEM)) {
                                if (tagName2.equals(XML_MENU)) {
                                    parseMenu(parser, attrs, menuState.addSubMenuItem());
                                    break;
                                } else {
                                    lookingForEndOfUnknownTag = true;
                                    unknownTagName = tagName2;
                                    break;
                                }
                            } else {
                                menuState.readItem(attrs);
                                break;
                            }
                        } else {
                            menuState.readGroup(attrs);
                            break;
                        }
                    }
                case 3:
                    String name3 = parser.getName();
                    String tagName3 = name3;
                    if (!lookingForEndOfUnknownTag || !name3.equals(unknownTagName)) {
                        if (!tagName3.equals(XML_GROUP)) {
                            if (tagName3.equals(XML_ITEM)) {
                                if (!menuState.hasAddedItem()) {
                                    if (menuState.itemActionProvider == null || !menuState.itemActionProvider.hasSubMenu()) {
                                        menuState.addItem();
                                        break;
                                    } else {
                                        SubMenu addSubMenuItem = menuState.addSubMenuItem();
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            } else if (tagName3.equals(XML_MENU)) {
                                reachedEndOfMenu = true;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            menuState.resetGroup();
                            break;
                        }
                    } else {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                        break;
                    }
            }
            eventType = parser.next();
        }
    }

    /* access modifiers changed from: 0000 */
    public Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private Object findRealOwner(Object obj) {
        Object owner = obj;
        Object obj2 = owner;
        if ((owner instanceof Activity) || !(owner instanceof ContextWrapper)) {
            return owner;
        }
        return findRealOwner(((ContextWrapper) owner).getBaseContext());
    }
}
