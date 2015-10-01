package com.zhuanghongji.flowlayout.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuanghongji.flowlayout.R;
import com.zhuanghongji.flowlayout.view.FlowLayout;
import com.zhuanghongji.flowlayout.view.TagAdapter;
import com.zhuanghongji.flowlayout.view.TagFlowLayout;

public class LimitSelectedFragment extends Fragment{

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView"};

    private TagFlowLayout mFlowLayout;

    public LimitSelectedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag_flowlayout, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        mFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);
        mFlowLayout.setMaxSelectCount(3);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_textview,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }
}
