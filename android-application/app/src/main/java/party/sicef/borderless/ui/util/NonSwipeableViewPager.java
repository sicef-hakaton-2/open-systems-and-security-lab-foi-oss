package party.sicef.borderless.ui.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Andro on 11/15/2015.
 */
public class NonSwipeableViewPager extends ViewPager {
    private boolean swipe = true;

    public boolean isSwipe() {
        return swipe;
    }

    public void setSwipe(boolean swipe) {
        this.swipe = swipe;
    }

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (swipe) {
            return super.onInterceptTouchEvent(arg0);
        }
        // Never allow swiping to switch between pages
        return false;
    }

}