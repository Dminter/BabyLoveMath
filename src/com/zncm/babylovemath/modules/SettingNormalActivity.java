package com.zncm.babylovemath.modules;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

import com.umeng.analytics.MobclickAgent;
import com.zncm.babylovemath.R;
import com.zncm.babylovemath.utils.XUtil;
import com.zncm.utils.ViewUtils;
import com.zncm.utils.sp.StatedPerference;


public class SettingNormalActivity extends BaseHomeActivity implements OnClickListener {
    private TableRow tableRowFont;
    private String fontType = "";
    private TableRow tableRowDifficulty;
    private TableRow tableRowQNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_setting_normal);
        actionBar.setTitle("常规");
        initViews();
    }

    // umeng

    private void initViews() {
        tableRowFont = (TableRow) findViewById(R.id.tablerow_font);
        tableRowFont.setOnClickListener(this);
        tableRowDifficulty = (TableRow) findViewById(R.id.tablerow_difficulty);
        tableRowDifficulty.setOnClickListener(this);
        tableRowQNumber = (TableRow) findViewById(R.id.tablerow_qNumber);
        tableRowQNumber.setOnClickListener(this);
        initFontType();
        initData();
    }


    private void initFontType() {

        if (StatedPerference.getFontSize() == 15f) {
            fontType = "小";
        } else if (StatedPerference.getFontSize() == 20f) {
            fontType = "中";
        } else if (StatedPerference.getFontSize() == 25f) {
            fontType = "大";
        }
        ViewUtils.setTextView(this, R.id.tvFontType, fontType
        );


    }


    private void initData() {
        ViewUtils.setTextView(this, R.id.tvQNumber, StatedPerference.getQNumber() + "题"
        );
        ViewUtils.setTextView(this, R.id.tvDifficulty, StatedPerference.getDifficulty() + "以内"
        );
    }


    @Override
    protected void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tablerow_font:
                DialogFragment newFragment = new XUtil.FontSizeAlertDialogFragment(R.drawable.font_size, "字体大小") {

                    @Override
                    protected void doClick() {
                        initFontType();
                    }
                };
                newFragment.show(getSupportFragmentManager(), "dialog");
                break;

            default:
                break;
            case R.id.tablerow_difficulty:
                newFragment = new XUtil.DifficultyAlertDialogFragment("难度") {

                    @Override
                    protected void doClick() {
                        initData();
                    }
                };
                newFragment.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.tablerow_qNumber:
                newFragment = new XUtil.QNmberAlertDialogFragment("题数") {
                    @Override
                    protected void doClick() {
                        initData();
                    }
                };
                newFragment.show(getSupportFragmentManager(), "dialog");

                break;
        }
    }


}
