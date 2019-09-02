package android.support.p000v4.util;

/* renamed from: android.support.v4.util.Pair */
public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f, S s) {
        F first2 = f;
        S second2 = s;
        F f2 = first2;
        S s2 = second2;
        this.first = first2;
        this.second = second2;
    }

    public boolean equals(Object obj) {
        Object o = obj;
        Object obj2 = o;
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) o;
        return objectsEqual(pair.first, this.first) && objectsEqual(pair.second, this.second);
    }

    private static boolean objectsEqual(Object obj, Object obj2) {
        Object a = obj;
        Object b = obj2;
        Object obj3 = a;
        Object obj4 = b;
        return a == b || (a != null && a.equals(b));
    }

    public int hashCode() {
        return (this.first != null ? this.first.hashCode() : 0) ^ (this.second != null ? this.second.hashCode() : 0);
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + "}";
    }

    public static <A, B> Pair<A, B> create(A a, B b) {
        A a2 = a;
        B b2 = b;
        A a3 = a2;
        B b3 = b2;
        return new Pair(a2, b2);
    }
}
