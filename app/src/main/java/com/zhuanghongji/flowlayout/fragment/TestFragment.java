package com.zhuanghongji.flowlayout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuanghongji.flowlayout.R;
import com.zhuanghongji.flowlayout.view.FlowLayout;

public class TestFragment extends Fragment
{
    private String[] mValues = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private FlowLayout mFlowLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        mFlowLayout = (FlowLayout) view.findViewById(R.id.view_flow_layout);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mValues.length; i++) {
            TextView tv = (TextView) inflater.inflate(R.layout.item_textview, mFlowLayout, false);
            tv.setText(mValues[i]);
            mFlowLayout.addView(tv);
        }

    }
}
