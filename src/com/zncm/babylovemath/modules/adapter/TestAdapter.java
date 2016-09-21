package com.zncm.babylovemath.modules.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zncm.babylovemath.R;
import com.zncm.babylovemath.data.BaseData;
import com.zncm.utils.sp.StatedPerference;

import java.util.List;

public abstract class TestAdapter extends BaseAdapter {

    private List<? extends BaseData> items;
    private Context ctx;

    public TestAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setItems(List<? extends BaseData> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (items != null) {
            return items.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (items != null) {
            return position;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoteViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(
                    R.layout.cell_lv_test, null);
            holder = new NoteViewHolder();
            holder.tvContent = (TextView) convertView
                    .findViewById(R.id.tvContent);
            holder.tvScore = (TextView) convertView
                    .findViewById(R.id.tvScore);
            holder.tvStar = (TextView) convertView
                    .findViewById(R.id.tvStar);
            holder.tvTime = (TextView) convertView
                    .findViewById(R.id.tvTime);
            holder.tvDetails = (TextView) convertView
                    .findViewById(R.id.tvDetails);

            holder.tvContent.setTextSize(StatedPerference.getFontSize());
            holder.tvScore.setTextSize(StatedPerference.getFontSize() + 2);
            holder.tvStar.setTextSize(StatedPerference.getFontSize());
            holder.tvTime.setTextSize(StatedPerference.getFontSize());
            holder.tvDetails.setTextSize(StatedPerference.getFontSize());

            convertView.setTag(holder);
        } else {
            holder = (NoteViewHolder) convertView.getTag();
        }
        setData(position, holder);
        return convertView;
    }

    public abstract void setData(int position, NoteViewHolder holder);

    public class NoteViewHolder {
        public TextView tvContent;
        public TextView tvScore;
        public TextView tvStar;
        public TextView tvTime;
        public TextView tvDetails;
    }
}