package com.zncm.babylovemath.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.umeng.analytics.MobclickAgent;
import com.zncm.babylovemath.R;
import com.zncm.babylovemath.data.EnumData;
import com.zncm.babylovemath.data.base.QuestionData;
import com.zncm.babylovemath.global.GlobalConstants;
import com.zncm.babylovemath.global.SharedApplication;
import com.zncm.babylovemath.modules.adapter.QuestionDetailsView;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.component.view.HackyViewPager;
import com.zncm.utils.StrUtil;
import com.zncm.utils.TimeUtils;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.util.ArrayList;

public class QuestionPagerActivity extends SherlockFragmentActivity {

    private ViewPager mViewPager;
    private ArrayList list;
    private int current;
    private int _ID;
    private ArrayList<QuestionData> datas = null;
    private RuntimeExceptionDao<QuestionData, Integer> dao = null;
    private ActionBar actionBar;
    private DatabaseHelper databaseHelper = null;
    private boolean bEditable = true;
    private Handler mHandler;
    private TextView tvTime;
    private int second;
    private int questionType;

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(SharedApplication.getInstance().ctx, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_scan);
        mViewPager = (HackyViewPager) findViewById(R.id.vpCy);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        questionType = getIntent().getExtras().getInt(GlobalConstants.KEY_TYPE);
        actionBar.setTitle(EnumData.MathTypeEnum.valueOf(questionType).getStrName());
        current = getIntent().getExtras().getInt("current");
        _ID = getIntent().getExtras().getInt("_ID");
        second = getIntent().getExtras().getInt("second");
        bEditable = getIntent().getExtras().getBoolean("editable");
        list = getIntent().getExtras().getParcelableArrayList(GlobalConstants.KEY_LIST_DATA);
        datas = (ArrayList<QuestionData>) list.get(0);
        mViewPager.setAdapter(new SamplePagerAdapter(datas));
        mViewPager.setCurrentItem(current);
        initCView();

    }

    private void initCView() {
        mHandler = new Handler();
        View customNav = LayoutInflater.from(this).inflate(R.layout.view_time, null);
        tvTime = (TextView) customNav.findViewById(R.id.tvTime);
        actionBar.setCustomView(customNav);
        actionBar.setDisplayShowCustomEnabled(true);
        mHandler.postDelayed(mTimerTask, 1000);
    }


    private Runnable mTimerTask = new Runnable() {

        public void run() {
            if (!bEditable)
                return;
            second++;
            tvTime.setText(TimeUtils.convertSToHMS(second));
            mHandler.postDelayed(mTimerTask, 1000);
        }
    };


    class SamplePagerAdapter extends PagerAdapter {
        private ArrayList<QuestionData> datas;

        public SamplePagerAdapter(ArrayList<QuestionData> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            if (datas == null) {
                return 0;
            } else {
                return datas.size();
            }

        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            final Context ctx = container.getContext();
            final QuestionDetailsView view = new QuestionDetailsView(ctx);
            final QuestionData data = datas.get(position);
            view.getTvContent().setText(data.getContent() + "=?");
            view.getRbA().setText(data.getAnswerA());
            view.getRbB().setText(data.getAnswerB());
            view.getRbC().setText(data.getAnswerC());
            view.getRbD().setText(data.getAnswerD());
            view.getTvPage().setText((position + 1) + "/" + datas.size());
            view.getRbA().setEnabled(bEditable);
            view.getRbB().setEnabled(bEditable);
            view.getRbC().setEnabled(bEditable);
            view.getRbD().setEnabled(bEditable);

            if (StrUtil.notEmptyOrNull(data.getChoose())) {
                if (data.getChoose().equals("A")) {
                    view.getRbA().setChecked(true);
                } else if (data.getChoose().equals("B")) {
                    view.getRbB().setChecked(true);
                } else if (data.getChoose().equals("C")) {
                    view.getRbC().setChecked(true);
                } else if (data.getChoose().equals("D")) {
                    view.getRbD().setChecked(true);
                }
            }

            if (!bEditable) {
                view.getTvAnswer().setVisibility(View.VISIBLE);
                if (StrUtil.notEmptyOrNull(data.getChoose())) {
                    if (data.getChoose().equals(data.getAnswer())) {
                        view.getTvAnswer().setText("√ 回答正确");
                    } else {
                        view.getTvAnswer().setText("× 正确答案:" + data.getAnswer());
                    }

                } else {
                    view.getTvAnswer().setText("× 没作答,正确答案:" + data.getAnswer());
                }
            }
            view.getRbGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String ret = null;
                    switch (checkedId) {
                        case R.id.rbA:
                            ret = "A";
                            break;
                        case R.id.rbB:
                            ret = "B";
                            break;
                        case R.id.rbC:
                            ret = "C";
                            break;
                        case R.id.rbD:
                            ret = "D";
                            break;

                    }
                    try {
                        if (dao == null) {
                            dao = getHelper().getQuestionDataDao();
                        }
                        if (data != null) {
                            data.setChoose(ret);
                            dao.update(data);
                        }
                    } catch (Exception e) {
                        CheckedExceptionHandler.handleException(e);
                    }

                }
            });


            view.getIvPre().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            });
            view.getIvNext().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            });

            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


    // umeng

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
    public boolean onCreateOptionsMenu(Menu menu) {

        if (bEditable) {
            menu.add("finish").setIcon(R.drawable.ok).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getTitle().equals("finish")) {
            backDo();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                backDo();
                break;
        }

        return false;
    }


    private void backDo() {
        Intent back_intent = new Intent();
        setResult(RESULT_OK, back_intent);
        back_intent.putExtra("second", second);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backDo();
        }
        return false;
    }


}
