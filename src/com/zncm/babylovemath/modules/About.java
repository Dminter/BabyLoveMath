package com.zncm.babylovemath.modules;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zncm.babylovemath.R;
import com.zncm.utils.DeviceUtil;
import com.zncm.utils.ViewUtils;

public class About extends BaseHomeActivity {

    private TextView tvVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_about);
        getSupportActionBar().setTitle("关于我们");
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvVersion.setText(getResources().getText(R.string.app_name) + " V" + DeviceUtil.getVersionName(this));


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