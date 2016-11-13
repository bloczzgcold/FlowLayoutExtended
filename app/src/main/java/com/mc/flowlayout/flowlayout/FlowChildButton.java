package com.mc.flowlayout.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mc.flowlayout.R;


/**
 * Created by zhangzhenguo on 2016/11/10.
 * 流式布局child view
 */

public class FlowChildButton extends FrameLayout {
    private TextView tvContent;
    /**
     * 唯一标识
     */
    private String mCode;

    private OnDeleteListener mOnDeleteListener;
    public FlowChildButton(Context context) {
        super(context);
        init(context);
    }
    public FlowChildButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        FrameLayout view = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.item_flow_button_view,null);
        ImageView ivDel = (ImageView) view.findViewById(R.id.iv_item_area_del);
        ivDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteListener.delete();
            }
        });
        tvContent = (TextView) view.findViewById(R.id.tv_item_flow_btn);
        addView(view);
    }

    public void setCode(String code){
        mCode = code;
    }

    public String getCode(){
        return mCode;
    }

    public void setContentText(String content){
        tvContent.setText(content);
    }

    public String getContentText(){
        return tvContent.getText().toString();
    }

    public interface OnDeleteListener{
        void delete();
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.mOnDeleteListener = onDeleteListener;
    }
}
