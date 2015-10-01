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
import com.zhuanghongji.flowlayout.view.TagAdapter;
import com.zhuanghongji.flowlayout.view.TagFlowLayout;

public class SimpleFragment extends Fragment
{
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private TagFlowLayout mFlowLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_tag_flowlayout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        mFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);

        mFlowLayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_textview,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }
}
