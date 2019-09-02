package android.support.design.widget;

import android.content.Context;
import android.support.p003v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class TextInputEditText extends AppCompatEditText {
    public TextInputEditText(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        EditorInfo outAttrs = editorInfo;
        EditorInfo editorInfo2 = outAttrs;
        InputConnection onCreateInputConnection = super.onCreateInputConnection(outAttrs);
        InputConnection ic = onCreateInputConnection;
        if (onCreateInputConnection != null && outAttrs.hintText == null) {
            ViewParent parent = getParent();
            while (true) {
                ViewParent parent2 = parent;
                if (parent2 instanceof View) {
                    if (parent2 instanceof TextInputLayout) {
                        outAttrs.hintText = ((TextInputLayout) parent2).getHint();
                        break;
                    }
                    parent = parent2.getParent();
                } else {
                    break;
                }
            }
        }
        return ic;
    }
}
