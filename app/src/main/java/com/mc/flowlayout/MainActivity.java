package com.mc.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mc.flowlayout.flowlayout.FlowChild;
import com.mc.flowlayout.flowlayout.FlowChildButton;
import com.mc.flowlayout.flowlayout.FlowLayout;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements FlowLayout.OnChildChangeListener{
    private static String TAG = MainActivity.class.getSimpleName();

    private FlowLayout mFlowLayout;
    private String[] names = new String[]{"青岛","济南","呼和浩特","大夫人摘","漱芳斋","北京","黑龙江省","吉林省","杭州市","无锡市"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_content_layout);
        mFlowLayout.setOnChildChangeListener(this);
    }

    public void addFlowBtn(View view){
        FlowChildButton btn = new FlowChildButton(this);
        btn.setCode(UUID.randomUUID().toString());
        btn.setContentText(names[randomInt()]);
        mFlowLayout.addView(btn);
    }

    @Override
    public void deleteItem(String code, String name) {

    }

    @Override
    public void onChange() {
        List<FlowChild> list = mFlowLayout.getContentChild();
        StringBuffer stringBuffer = new StringBuffer();
        for (FlowChild ares:list){
            stringBuffer.append(",");
            stringBuffer.append("code:");
            stringBuffer.append(ares.getCode());
        }
        if (stringBuffer.length() > 0){
            stringBuffer.deleteCharAt(0);
        }
        Log.d(TAG,stringBuffer.toString());
    }

    private int randomInt(){
        Random random = new Random();
        int result = random.nextInt(10);
        return  result;
    }
}
