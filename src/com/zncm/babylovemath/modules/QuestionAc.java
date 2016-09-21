package com.zncm.babylovemath.modules;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zncm.babylovemath.R;
import com.zncm.babylovemath.data.EnumData;
import com.zncm.babylovemath.data.base.QuestionData;
import com.zncm.babylovemath.data.base.TestData;
import com.zncm.babylovemath.global.GlobalConstants;
import com.zncm.babylovemath.modules.adapter.QuestionAdapter;
import com.zncm.babylovemath.utils.XUtil;
import com.zncm.utils.StrUtil;
import com.zncm.utils.TimeUtils;
import com.zncm.utils.exception.CheckedExceptionHandler;
import com.zncm.utils.sp.StatedPerference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuestionAc extends BaseLvActivity {
    public List<QuestionData> datas = null;
    private int curPosition;
    private RuntimeExceptionDao<TestData, Integer> testDao = null;
    private RuntimeExceptionDao<QuestionData, Integer> dao = null;
    private QuestionAdapter mAdapter;
    private Context ctx;
    private int questionType = 0;//加法
    private int questionCount = 10;//加法
    private int questionMax = 10;//10以内加法
    private int _Id;
    private Set<String> setQquation;
    private int second = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ctx = this;
        questionType = getIntent().getExtras().getInt(GlobalConstants.KEY_TYPE);
        actionBar.setTitle(EnumData.MathTypeEnum.valueOf(questionType).getStrName());
        datas = new ArrayList<QuestionData>();
        dao = getHelper().getQuestionDataDao();
        mAdapter = new QuestionAdapter(ctx) {
            @Override
            public void setData(int position, NoteViewHolder holder) {
                QuestionData data = (QuestionData) datas.get(position);
                if (StrUtil.notEmptyOrNull(data.getChoose())) {
                    String ret = "";
                    if (data.getChoose().equals("A")) {
                        ret = data.getAnswerA();
                    } else if (data.getChoose().equals("B")) {
                        ret = data.getAnswerB();
                    } else if (data.getChoose().equals("C")) {
                        ret = data.getAnswerC();
                    } else if (data.getChoose().equals("D")) {
                        ret = data.getAnswerD();
                    }
                    holder.tvContent.setText(data.getContent() + "=" + ret);
                } else {
                    holder.tvContent.setText(data.getContent() + "=?");
                }
            }
        };
        mAdapter.setItems(datas);
        lvBase.setAdapter(mAdapter);
        plListView.setOnItemClickListener(new OnItemClickListener() {

            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position - lvBase.getHeaderViewsCount();
                Intent intent = new Intent(ctx, QuestionPagerActivity.class);
                intent.putExtra("current", curPosition);
                intent.putExtra("_ID", _Id);
                intent.putExtra("editable", true);
                intent.putExtra("second", second);
                intent.putExtra(GlobalConstants.KEY_TYPE, questionType);
                ArrayList list = new ArrayList();
                list.add(datas);
                intent.putParcelableArrayListExtra(GlobalConstants.KEY_LIST_DATA, list);
                startActivityForResult(intent, 0);
            }
        });

        lvBase.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        initListView();
        questionCount = StatedPerference.getQNumber();
        questionMax = StatedPerference.getDifficulty();
        PrepareTest prepareTest = new PrepareTest();
        prepareTest.execute(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @SuppressWarnings("deprecation")
    public void initListView() {
        plListView.needEmptyView("正在出题,请稍候...");
        plListView.setPullToRefreshEnabled(false);
        plListView.setmListLoadMore(false);
    }

    private void getData() {
        GetData getData = new GetData();
        getData.execute();
    }

    class GetData extends AsyncTask<String, Integer, Integer> {

        protected Integer doInBackground(String... params) {
            try {
                if (dao == null) {
                    dao = getHelper().getQuestionDataDao();
                }
                QueryBuilder<QuestionData, Integer> builder = dao.queryBuilder();
                builder.orderBy("id", true);
                builder.where().eq("testId", _Id);
                List<QuestionData> list = dao.query(builder.prepare());
                datas = new ArrayList<QuestionData>();
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

    class PrepareTest extends AsyncTask<Boolean, Integer, Integer> {

        protected Integer doInBackground(Boolean... params) {
            try {
                if (testDao == null) {
                    testDao = getHelper().getTestDataDao();
                }
                if (dao == null) {
                    dao = getHelper().getQuestionDataDao();
                }

                TestData testData = new TestData(questionType, TimeUtils.getDateMD() + EnumData.MathTypeEnum.valueOf(questionType).getStrName() + "(" + questionMax + "以内," + questionCount + "题)"
                        , questionCount);
                testDao.create(testData);
                TestData data = testDao.queryForSameId(testData);
                _Id = data.getId();
                setQquation = new HashSet<String>();
                for (int i = 0; i < questionCount; i++) {
                    QuestionData questionData = prepareQuestion(_Id, questionMax);
                    if (questionData != null) {
                        dao.create(questionData);
                    }
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
            getData();
        }

    }

    private QuestionData prepareQuestion(int _id, int questionMax) {

        int probablyAnswer = 0;
        String equation = "";
        int tmp = new Random().nextInt(2);
        int tmp4 = new Random().nextInt(4);

        QuestionData questionData = new QuestionData();
        questionData.setTestId(_id);
        questionData.setType(questionType);
        int x = 0, y = 0, z = 0;
        int a = 0, b = 0, c = 0, d = 0;
        float zz = 0f;
        switch (questionType) {
            case 0:
                do {
                    x = new Random().nextInt(questionMax);
                    y = new Random().nextInt(questionMax);
                    z = x + y;
                } while (!setQquation.add(String.valueOf(x) + "+" + String.valueOf(y)));
                equation = String.valueOf(x) + "+" + String.valueOf(y);
                probablyAnswer = 2 * questionMax;
                break;
            case 1:
                do {
                    x = new Random().nextInt(questionMax);
                    y = new Random().nextInt(questionMax);
                    z = x - y;
                }
                while (x < y || !setQquation.add(String.valueOf(x) + "-" + String.valueOf(y)));
                equation = String.valueOf(x) + "-" + String.valueOf(y);
                probablyAnswer = questionMax;
                break;
            case 2:
                do {
                    x = new Random().nextInt(questionMax);
                    y = new Random().nextInt(questionMax);
                    z = x * y;
                } while (!setQquation.add(String.valueOf(x) + "×" + String.valueOf(y)));
                equation = String.valueOf(x) + "×" + String.valueOf(y);
                probablyAnswer = questionMax * questionMax;
                break;
            case 3:

                do {
                    x = new Random().nextInt(questionMax);
                    y = new Random().nextInt(questionMax);
                }
                while (y == 0 || x % y != 0 || !setQquation.add(String.valueOf(x) + "÷" + String.valueOf(y)));
                equation = String.valueOf(x) + "÷" + String.valueOf(y);
                z = x / y;
                probablyAnswer = questionMax;
                break;
            case 4:
                if (tmp == 0) {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                        z = x + y;
                    } while (!setQquation.add(String.valueOf(x) + "+" + String.valueOf(y)));
                    equation = String.valueOf(x) + "+" + String.valueOf(y);
                    probablyAnswer = 2 * questionMax;

                } else {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                        z = x - y;
                    }
                    while (x < y || !setQquation.add(String.valueOf(x) + "-" + String.valueOf(y)));
                    equation = String.valueOf(x) + "-" + String.valueOf(y);
                    probablyAnswer = questionMax;
                }
                break;
            case 5:
                if (tmp == 0) {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                        z = x * y;
                    } while (!setQquation.add(String.valueOf(x) + "×" + String.valueOf(y)));
                    equation = String.valueOf(x) + "×" + String.valueOf(y);
                    probablyAnswer = questionMax * questionMax;

                } else {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                    }
                    while (y == 0 || x % y != 0 || !setQquation.add(String.valueOf(x) + "÷" + String.valueOf(y)));
                    equation = String.valueOf(x) + "÷" + String.valueOf(y);
                    z = x / y;
                    probablyAnswer = questionMax;
                }
                break;
            case 6:
                if (tmp4 == 0) {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                        z = x + y;
                    } while (!setQquation.add(String.valueOf(x) + "+" + String.valueOf(y)));
                    equation = String.valueOf(x) + "+" + String.valueOf(y);
                    probablyAnswer = 2 * questionMax;

                } else if (tmp4 == 1) {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                        z = x - y;
                    }
                    while (x < y || !setQquation.add(String.valueOf(x) + "-" + String.valueOf(y)));
                    equation = String.valueOf(x) + "-" + String.valueOf(y);
                    probablyAnswer = questionMax;
                } else if (tmp4 == 2) {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                        z = x * y;
                    } while (!setQquation.add(String.valueOf(x) + "×" + String.valueOf(y)));
                    equation = String.valueOf(x) + "×" + String.valueOf(y);
                    probablyAnswer = questionMax * questionMax;
                } else if (tmp4 == 3) {
                    do {
                        x = new Random().nextInt(questionMax);
                        y = new Random().nextInt(questionMax);
                    }
                    while (y == 0 || x % y != 0 || !setQquation.add(String.valueOf(x) + "÷" + String.valueOf(y)));
                    equation = String.valueOf(x) + "÷" + String.valueOf(y);
                    z = x / y;
                    probablyAnswer = questionMax;
                }
                break;

        }
        questionData.setContent(equation);


        int answer = new Random().nextInt(4);
        String answerKey = null;
        switch (answer) {
            case 0:
                a = z;
                answerKey = "A";
                do {
                    b = new Random().nextInt(probablyAnswer);
                    c = new Random().nextInt(probablyAnswer);
                    d = new Random().nextInt(probablyAnswer);
                } while (a == b || a == c || a == d || b == c || b == d || c == d);

                break;

            case 1:
                b = z;
                answerKey = "B";
                do {
                    a = new Random().nextInt(probablyAnswer);
                    c = new Random().nextInt(probablyAnswer);
                    d = new Random().nextInt(probablyAnswer);
                } while (a == b || a == c || a == d || b == c || b == d || c == d);

                break;

            case 2:
                c = z;
                answerKey = "C";
                do {
                    a = new Random().nextInt(probablyAnswer);
                    b = new Random().nextInt(probablyAnswer);
                    d = new Random().nextInt(probablyAnswer);
                } while (a == b || a == c || a == d || b == c || b == d || c == d);

                break;

            case 3:
                d = z;
                answerKey = "D";
                do {
                    a = new Random().nextInt(probablyAnswer);
                    b = new Random().nextInt(probablyAnswer);
                    c = new Random().nextInt(probablyAnswer);
                } while (a == b || a == c || a == d || b == c || b == d || c == d);

                break;

        }
        questionData.setAnswerA(String.valueOf(a));
        questionData.setAnswerB(String.valueOf(b));
        questionData.setAnswerC(String.valueOf(c));
        questionData.setAnswerD(String.valueOf(d));
        questionData.setAnswer(answerKey);
        return questionData;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            second = data.getExtras().getInt("second");
        }

        //答案写上去
        getData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("check").setIcon(R.drawable.check).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("check")) {
            XUtil.TwoAlertDialogFragment dialogFragment = new XUtil.TwoAlertDialogFragment(R.drawable.info, "确认要交卷么?") {
                @Override
                public void doPositiveClick() {
                    doSubmitTest();
                    Intent intent = new Intent(ctx, TestAc.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void doNegativeClick() {

                }
            };
            dialogFragment.show(getSupportFragmentManager(), "dialog");
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                backDo();
                break;
        }
        return false;
    }

    private void doSubmitTest() {

        //每一道题多少分

        int questionCount = datas.size();
        float everyQuestionScore = 100.0f / questionCount;
        int rightCount = 0;
        float score = 0f;
        int avgSpendTime = 1;
        try {
            if (testDao == null) {
                testDao = getHelper().getTestDataDao();
            }
            if (dao == null) {
                dao = getHelper().getQuestionDataDao();
            }
            TestData testData = testDao.queryForId(_Id);
            if (testData != null) {

                for (int i = 0; i < datas.size(); i++) {
                    QuestionData data = datas.get(i);
                    if (data != null) {
                        if (StrUtil.notEmptyOrNull(data.getChoose()) && data.getChoose().equals(data.getAnswer())) {
                            score += everyQuestionScore;
                            data.setStatus(1);
                            rightCount += 1;
                        }
                        dao.update(data);
                    }
                }
            }
            testData.setQuestionCount(questionCount);
            testData.setRightCount(rightCount);
            testData.setWrongCount((questionCount - rightCount));
            testData.setScore(score);
            testData.setSpendTime(second);
            testData.setAvgSpendTime(avgSpendTime);
            testData.setModifyTime(System.currentTimeMillis());
            testDao.update(testData);
        } catch (Exception e) {
            CheckedExceptionHandler.handleException(e);
        }


    }


    private void backDo() {

        XUtil.TwoAlertDialogFragment dialogFragment = new XUtil.TwoAlertDialogFragment(R.drawable.info, "你还没交卷,确定要返回么?") {
            @Override
            public void doPositiveClick() {
                //把生成好的废试卷删除
                delPaper();
                finish();
            }

            @Override
            public void doNegativeClick() {

            }
        };
        dialogFragment.show(getSupportFragmentManager(), "dialog");

    }

    private void delPaper() {
        try {
            if (testDao == null) {
                testDao = getHelper().getTestDataDao();
            }
            if (dao == null) {
                dao = getHelper().getQuestionDataDao();
            }
            testDao.deleteById(_Id);
            dao.deleteBuilder().where().eq("testId", _Id);
        } catch (Exception e) {
            CheckedExceptionHandler.handleException(e);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backDo();
        }
        return false;
    }


}
