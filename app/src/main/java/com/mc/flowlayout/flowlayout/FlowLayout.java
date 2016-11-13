package com.mc.flowlayout.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mc.flowlayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FlowLayout is much more like a {@link android.widget.LinearLayout}, but it can automatically
 * separate the widgets wrapped in it into multiple lines just like the water flow.
 *
 * Inspired by {@see http://hzqtc.github.io/2013/12/android-custom-layout-flowlayout.html}
 *
 * @author liangfeizc {@see http://www.liangfeizc.com}
 */
public class FlowLayout extends ViewGroup {

    private static final int DEFAULT_HORIZONTAL_SPACING = 16;
    private static final int DEFAULT_VERTICAL_SPACING = 16;

    private int mVerticalSpacing;
    private int mHorizontalSpacing;
    Context mContext;
    private OnChildChangeListener mOnChildChangeListener;

    
    public FlowLayout(Context context) {
        super(context);
        mContext = context;
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_horizontal_spacing, DEFAULT_HORIZONTAL_SPACING);
            mVerticalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_vertical_spacing, DEFAULT_VERTICAL_SPACING);
        } finally {
            a.recycle();
        }
    }

    public void setHorizontalSpacing(int pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(int pixelSize) {
        mVerticalSpacing = pixelSize;
    }

    @Override
    public void addView(final View child) {
        if (child instanceof FlowChildButton){
            ((FlowChildButton)child).setOnDeleteListener(new FlowChildButton.OnDeleteListener() {
                @Override
                public void delete() {
                    removeView(child);
                    invalidate();
                    mOnChildChangeListener.deleteItem(((FlowChildButton)child).getCode(),((FlowChildButton)child).getContentText());
                    mOnChildChangeListener.onChange();
                }
            });
        }
        super.addView(child);
        if (mOnChildChangeListener != null){
            mOnChildChangeListener.onChange();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int myWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        // Measure each child and put the child to the right of previous child
        // if there's enough room for it, otherwise, wrap the line and put the child to next line.
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            } else {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;

        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }

    public interface OnChildChangeListener{
        /**
         * child delete listener
         * @param code deleted child code
         * @param name deleted child name
         */
        void deleteItem(String code, String name);

        /**
         * child changed listener
         */
        void onChange();
    }

    public void setOnChildChangeListener(OnChildChangeListener onChildChangeListener){
        mOnChildChangeListener = onChildChangeListener;
    }

    /**
     * current childView data
     * @return all children data
     */
    public List<FlowChild> getContentChild(){
        int count = getChildCount();
        List<FlowChild> children = new ArrayList<>();
        for (int i=0;i<count;i++){
            String code = ((FlowChildButton)getChildAt(i)).getCode();
            String name = ((FlowChildButton)getChildAt(i)).getContentText();
            FlowChild flowChild = new FlowChild();
            flowChild.setCode(code);
            flowChild.setName(name);
            children.add(flowChild);
        }
        return children;
    }
}
