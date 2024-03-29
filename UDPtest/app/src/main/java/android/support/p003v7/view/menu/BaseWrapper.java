package android.support.p003v7.view.menu;

/* renamed from: android.support.v7.view.menu.BaseWrapper */
class BaseWrapper<T> {
    final T mWrappedObject;

    BaseWrapper(T t) {
        T object = t;
        T t2 = object;
        if (null != object) {
            this.mWrappedObject = object;
            return;
        }
        throw new IllegalArgumentException("Wrapped Object can not be null.");
    }

    public T getWrappedObject() {
        return this.mWrappedObject;
    }
}
