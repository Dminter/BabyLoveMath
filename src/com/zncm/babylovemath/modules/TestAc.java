package com.zncm.babylovemath.modules;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zncm.babylovemath.R;
import com.zncm.babylovemath.data.base.QuestionData;
import com.zncm.babylovemath.data.base.TestData;
import com.zncm.babylovemath.global.GlobalConstants;
import com.zncm.babylovemath.modules.adapter.TestAdapter;
import com.zncm.babylovemath.utils.XUtil;
import com.zncm.component.pullrefresh.PullToRefreshBase;
import com.zncm.utils.L;
import com.zncm.utils.StrUtil;
import com.zncm.utils.TimeUtils;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestAc extends BaseLvActivity {
    private int curPosition;
    private RuntimeExceptionDao<TestData, Integer> testDao = null;
    private RuntimeExceptionDao<QuestionData, Integer> dao = null;
    private ArrayList<TestData> datas;
    private ArrayList<QuestionData> questionDatas = null;
    private TestAdapter mAdapter;
    private Context ctx;
    private int _Id;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        actionBar.setTitle("测试历史");
        ctx = this;
        datas = new ArrayList<TestData>();
        testDao = getHelper().getTestDataDao();
        mAdapter = new TestAdapter(ctx) {
            @Override
            public void setData(int position, NoteViewHolder holder) {
                TestData data = (TestData) datas.get(position);
                holder.tvContent.setText(data.getContent());
                holder.tvScore.setText(new DecimalFormat("###").format(data.getScore()) + "分");
                holder.tvStar.setText(getStar(data.getScore()));
                holder.tvTime.setText(TimeUtils.getFullNoYearDate2(new Date(data.getTime())));
                holder.tvDetails.setText(data.getQuestionCount() + " " + data.getRightCount() + "√ " + data.getWrongCount() + "×" + " 耗时 " + TimeUtils.convertSToHMS(data.getSpendTime()));
            }
        };
        mAdapter.setItems(datas);
        lvBase.setAdapter(mAdapter);
        plListView.setOnItemClickListener(new OnItemClickListener() {

            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position - lvBase.getHeaderViewsCount();
                _Id = datas.get(curPosition).getId();
                getQuestionData();

            }
        });
        lvBase.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                curPosition = position - lvBase.getHeaderViewsCount();
                final TestData data = datas.get(curPosition);


                XUtil.TwoAlertDialogFragment dialogFragment = new XUtil.TwoAlertDialogFragment(R.drawable.del_icon, "真的要删除么?") {
                    @Override
                    public void doPositiveClick() {

                        try {
                            if (data != null) {
                                int _id = data.getId();
                                testDao.deleteById(_id);
                                datas.remove(curPosition);
                                mAdapter.setItems(datas);
                                loadComplete();
                            }
                        } catch (Exception e) {
                            CheckedExceptionHandler.handleException(e);
                        }
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                };
                dialogFragment.show(getSupportFragmentManager(), "dialog");


                return false;
            }
        });


        initListView();
        getData(true);

    }


    private void getQuestionData() {
        getQuestionData getData = new getQuestionData();
        getData.execute();
    }

    class getQuestionData extends AsyncTask<String, Integer, Integer> {

        protected Integer doInBackground(String... params) {
            try {
                if (dao == null) {
                    dao = getHelper().getQuestionDataDao();
                }
                QueryBuilder<QuestionData, Integer> builder = dao.queryBuilder();
                builder.orderBy("id", true);
                builder.where().eq("testId", _Id);
                List<QuestionData> list = dao.query(builder.prepare());
                questionDatas = new ArrayList<QuestionData>();
                if (StrUtil.listNotNull(list)) {
                    questionDatas.addAll(list);
                }
            } catch (Exception e) {
                CheckedExceptionHandler.handleException(e);
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Integer ret) {
            super.onPostExecute(ret);
            Intent intent = new Intent(ctx, QuestionPagerActivity.class);
            intent.putExtra("current", 0);
            intent.putExtra("_ID", _Id);
            intent.putExtra("editable", false);
            intent.putExtra("editable", false);
            intent.putExtra(GlobalConstants.KEY_TYPE, -1);
            ArrayList list = new ArrayList();
            list.add(questionDatas);
            intent.putParcelableArrayListExtra(GlobalConstants.KEY_LIST_DATA, list);
            startActivity(intent);

        }

    }


    private String getStar(float score) {
        String retStar = "";
        if (score == 100) {
            retStar = "★★★★★";
        } else if (score >= 90) {
            retStar = "★★★★☆";
        } else if (score >= 80) {
            retStar = "★★★☆☆";
        } else if (score >= 70) {
            retStar = "★★☆☆☆";
        } else if (score >= 60) {
            retStar = "★☆☆☆☆";
        } else if (score < 60) {
            retStar = "☆☆☆☆☆";
        }
        return retStar;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void initListView() {
        plListView.needEmptyView("还没进行过测试呢~");
        plListView.setPullToRefreshEnabled(false);
        plListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                getData(false);
            }
        });
    }

    private void getData(boolean bFirst) {
        GetData getData = new GetData();
        getData.execute(bFirst);
    }

    class GetData extends AsyncTask<Boolean, Integer, Integer> {

        protected Integer doInBackground(Boolean... params) {
            try {
                if (testDao == null) {
                    testDao = getHelper().getTestDataDao();
                }
                QueryBuilder<TestData, Integer> builder = testDao.queryBuilder();
                builder.orderBy("id", false);
                if (params[0]) {
                    builder.limit(GlobalConstants.DB_PAGE_SIZE);
                } else {
                    builder.limit(GlobalConstants.DB_PAGE_SIZE).offset((long) datas.size());
                }
                List<TestData> list = testDao.query(builder.prepare());
                if (params[0]) {
                    datas = new ArrayList<TestData>();
                }
                if (StrUtil.listNotNull(list)) {
                    datas.addAll(list);
                }
            } catch (Exception e) {
                CheckedExceptionHandler.handleException(e);
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Integer ret) {
            super.onPostExecute(ret);
            loadComplete();
            mAdapter.setItems(datas);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
