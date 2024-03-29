package android.support.transition;

import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class TransitionValues {
    public final Map<String, Object> values = new HashMap();
    public View view;

    public TransitionValues() {
    }

    public boolean equals(Object obj) {
        Object other = obj;
        Object obj2 = other;
        if ((other instanceof TransitionValues) && this.view == ((TransitionValues) other).view && this.values.equals(((TransitionValues) other).values)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (31 * this.view.hashCode()) + this.values.hashCode();
    }

    public String toString() {
        String returnValue = (("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.view + "\n") + "    values:";
        for (String s : this.values.keySet()) {
            returnValue = returnValue + "    " + s + ": " + this.values.get(s) + "\n";
        }
        return returnValue;
    }
}
