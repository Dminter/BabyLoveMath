package com.zncm.babylovemath.modules;

import android.os.Bundle;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zncm.babylovemath.R;
import com.zncm.utils.DeviceUtil;

public class EquationAc extends BaseHomeActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_eq);
        getSupportActionBar().setTitle("口诀");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }




}