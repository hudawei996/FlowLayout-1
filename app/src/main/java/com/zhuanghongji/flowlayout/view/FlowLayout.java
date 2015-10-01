package com.zhuanghongji.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局的实现类
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // MeasureSpec是一个测量说明，表示父View传递给子View的布局要求和说明，
    // 封装了size和mode，而且包含了高度和宽度的size和mode。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 父控件传入的测量值（宽度）
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        // 测量模式（宽度）
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        // 比如说你指定android:layout_width="match_parent"
        // 那么sizeWidth就是上级容器的宽度，测量模式modeWidth就是MeasureSpec.EXACTLY

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果用的是wrap_content，则需要我们自己去计算width和height
        // 自己测量的宽度
        int width = 0;
        int height = 0;

        // 记录每一行的宽度与高度
        int lineWidth = 0;
        int lineHeight = 0;

        //得到ViewGroup内部元素的个数
        int cCount = getChildCount();

        // 遍历所有子View(内部元素)
        for (int i = 0; i < cCount; i++) {
            // 拿到每一个子View
            View child = getChildAt(i);
            // 测量子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 在generateLayoutParams(...)方法中我们指定了marginLayoutParams
            // 不能写其他的，否则出现强转的错误
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            // 子View占据的宽度，包括它的对应margin
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            // 子View占据的高度，包括它的对应margin
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 换行
            if ((lineWidth + childWidth) > (sizeWidth - getPaddingLeft() - getPaddingRight())) {
                // 对比得到最大的宽度
                width = Math.max(width, lineWidth);
                // 重置lineWidth行宽
                lineWidth = childWidth;
                // 叠加行高
                height += lineHeight;
                lineHeight = childHeight;
            } else { // 未换行
                // 叠加行宽
                lineWidth += childWidth;
                // 得到当前行最大的宽度
                lineHeight = Math.max(lineHeight, childHeight);
            }

            // 到达最后一个控件，要不然最后一行显示不了
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        // 遍历完子View后，根据判断是AT_MOST模式（比如wrap_content时）还是EXACTLY模式
        // 设置测量好的width和height
        setMeasuredDimension(
                // sizeWidth 父控件传入的宽度；后者是自己测量的宽度
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : (width + getPaddingLeft() + getPaddingRight()),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : (height + getPaddingTop() + getPaddingRight())
        );
    }

    /**
     * 存储所有的View的集合,一行一行进行存储
     */
    private List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 存储每一行的高度的集合
     */
    private List<Integer> mLineHght = new ArrayList<>();

    // 对每一个View指定位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 因为onLayout()方法会被多次调用，每次先clear
        mAllViews.clear();
        mLineHght.clear();

        // 当前ViewGroup的宽度，直接拿，以为我们已经执行了onMeasure()方法
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        // 一行的View
        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 不用再进行测量，因为我们在onMeasure()方法测量过了
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果需要换行
            if ((childWidth + lineWidth + lp.leftMargin + lp.rightMargin) > (width - getPaddingLeft() - getPaddingRight())) {
                // 记录LineHeight
                mLineHght.add(lineHeight);
                // 记录当前行的Views
                mAllViews.add(lineViews);
                //重置我们的行宽与行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                // 重置我们的View集合，new一个新的
                lineViews = new ArrayList<>();
            }
            lineWidth += (childWidth + lp.leftMargin + lp.rightMargin);
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        } // for end

        // 处理最后一行，与上面处理最后一个View的原因是一样的
        mLineHght.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();

        // 行数,也就是mAllViews中还有多少个List集合
        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            // 当前行的所有的View
            lineViews = mAllViews.get(i); // 复用前面声明的lineView引用
            lineHeight = mLineHght.get(i);
            // 为一行的View进行布局，千万不要把i,j写反了
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                // 判断child的状态，它有可能不需要显示
                if (child.getVisibility() == View.GONE) {
                    // 如果不可见，跳出for循环
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin; // c是child的意思
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                // 为子view布局
                child.layout(lc,tc,rc,bc);
                // 一行中高度不变，左距离累加
                left += (child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            } // for end
            // 到达下一行之后，left要重新初始化，top要累加
            left = getPaddingLeft();
            top += lineHeight;
        } // for end
    }

    /*
     * ViewGroup会对应一个LayoutParams
     * 所以我们通过覆写以下这个方法来指明这个关系
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        // Tag便签主要用的Margin值，所以直接new一个返回
        return new MarginLayoutParams(getContext(),attrs);
    }
}
