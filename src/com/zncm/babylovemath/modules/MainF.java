package com.zncm.babylovemath.modules;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.zncm.babylovemath.R;
import com.zncm.babylovemath.data.EnumData;
import com.zncm.babylovemath.data.base.ImageTextData;
import com.zncm.babylovemath.global.GlobalConstants;
import com.zncm.babylovemath.modules.adapter.MainAdapter;
import com.zncm.component.pullrefresh.PullToRefreshBase;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class MainF extends BaseFragment {
    private Activity mActivity;
    public List<ImageTextData> datas = null;
    private int curPosition;
    private MainAdapter mAdapter;
    private boolean bZiCount = false;
    private int ziCount;
    private boolean bSolitaire = false;
    private String solitaireEnd;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivity = (Activity) getActivity();
        datas = new ArrayList<ImageTextData>();
        mAdapter = new MainAdapter(mActivity) {
            @Override
            public void setData(int position, NoteViewHolder holder) {
                ImageTextData data = (ImageTextData) datas.get(position);
                holder.tvText.setText(data.getText());
                holder.ivImage.setImageResource(data.getImgId());
            }
        };
        mAdapter.setItems(datas);
        lvBase.setAdapter(mAdapter);
        plListView.setOnItemClickListener(new OnItemClickListener() {

            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position - lvBase.getHeaderViewsCount();
                if (datas.get(curPosition).getId() == 7) {
                    try {
                        Uri uri = Uri.parse("https://me.alipay.com/dminter");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
                    } catch (Exception e) {
                        CheckedExceptionHandler.handleException(e);
                    }

                } else {
                    Intent intent = new Intent(mActivity, QuestionAc.class);
                    intent.putExtra(GlobalConstants.KEY_TYPE, datas.get(curPosition).getId());
                    startActivity(intent);
                }

            }
        });
        initListView();
        getData();

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("deprecation")
    public void initListView() {
        plListView.setPullToRefreshEnabled(false);
        plListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
            }
        });
    }

    private void getData() {

        datas = new ArrayList<ImageTextData>();
        ImageTextData data1 = new ImageTextData(EnumData.MathTypeEnum.ADD.getValue(), R.drawable.add, EnumData.MathTypeEnum.ADD.getStrName());
        ImageTextData data2 = new ImageTextData(EnumData.MathTypeEnum.SUBTRACTION.getValue(), R.drawable.subtraction, EnumData.MathTypeEnum.SUBTRACTION.getStrName());
        ImageTextData data3 = new ImageTextData(EnumData.MathTypeEnum.MULTIPLICATION.getValue(), R.drawable.multiplication, EnumData.MathTypeEnum.MULTIPLICATION.getStrName());
        ImageTextData data4 = new ImageTextData(EnumData.MathTypeEnum.DIV.getValue(), R.drawable.div, EnumData.MathTypeEnum.DIV.getStrName());
        ImageTextData data5 = new ImageTextData(EnumData.MathTypeEnum.ADD_SUBTRACTION.getValue(), R.drawable.jiajian, EnumData.MathTypeEnum.ADD_SUBTRACTION.getStrName());
        ImageTextData data6 = new ImageTextData(EnumData.MathTypeEnum.MULTIPLICATION_DIV.getValue(), R.drawable.chengchu, EnumData.MathTypeEnum.MULTIPLICATION_DIV.getStrName());
        ImageTextData data7 = new ImageTextData(EnumData.MathTypeEnum.ALL.getValue(), R.drawable.szys, EnumData.MathTypeEnum.ALL.getStrName());
        ImageTextData data8 = new ImageTextData(EnumData.MathTypeEnum.ALIPAY.getValue(), R.drawable.leaf, EnumData.MathTypeEnum.ALIPAY.getStrName());
        datas.add(data1);
        datas.add(data2);
        datas.add(data3);
        datas.add(data4);
        datas.add(data5);
        datas.add(data6);
        datas.add(data7);
        datas.add(data8);
        mAdapter.setItems(datas);

    }

    class GetData extends AsyncTask<Boolean, Integer, Integer> {

        protected Integer doInBackground(Boolean... params) {

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


}
