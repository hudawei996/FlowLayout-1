package com.zhuanghongji.flowlayout.view;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 这是一个抽象类(适配器)，
 * 那么具体的View的展示需要大家通过复写getView,用法和ListView及其类似，
 * 同时我们提供了notifyDataChanged()的方法，当你的数据集发生变化的时候，
 * 你可以调用该方法，UI会自动刷新。
 */
public abstract class TagAdapter<T> {
    private List<T> mTabDatas;
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<>();

    public TagAdapter(List<T> tabDatas) {
        mTabDatas = tabDatas;
    }

    public TagAdapter(T[] datas) {
        mTabDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    // 类接口
    static interface OnDataChangedListener {
        void OnChanged();
    }

    /**
     * 设置数据改变的监听器
     */
    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    /**
     * 设置选中了的列表
     */
    public void setSelectedList(int... pos) {
        for (int i = 0; i < pos.length; i++) {
            mCheckedPosList.add(pos[i]);
            notifyDataChanged();
        }
    }

    /**
     * 通知数据发生改变
     */
    public void notifyDataChanged() {
        mOnDataChangedListener.OnChanged();
    }

    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    /**
     * 获取数量
     */
    public int getCount() {
        return mTabDatas == null ? 0 : mTabDatas.size();
    }

    public T getItem(int position) {
        return mTabDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);
}
