package com.jixstreet.spaace.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * An image view which always remains square with respect to its width.
 */
public class SquaredTextView extends RelativeLayout {
    public SquaredTextView(Context context) {
        super(context);
    }

    public SquaredTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}