

package com.zncm.babylovemath.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.zncm.babylovemath.R;
import com.zncm.babylovemath.global.SharedApplication;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.utils.DeviceUtil;
import com.zncm.utils.ViewUtils;

//欢迎界面
public class SplashActivity extends Activity {
    private Handler handler;
    private TextView tvProgress;
    private DatabaseHelper databaseHelper = null;

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(SharedApplication.getInstance().ctx, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        ViewUtils.setTextView(this, R.id.tvAppInfo,
                getResources().getString(R.string.app_name) + " " + DeviceUtil.getVersionName(SplashActivity.this));
        handler = new Handler();
        handler.postDelayed(startAct, 1500);
    }


    Runnable startAct = new Runnable() {

        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, MainTabsPager.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

    };


}
