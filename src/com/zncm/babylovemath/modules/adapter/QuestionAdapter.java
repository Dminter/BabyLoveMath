package com.zncm.babylovemath.modules.adapter;

import android.app.Activity;
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

public abstract class QuestionAdapter extends BaseAdapter {

    private List<? extends BaseData> items;
    private Context ctx;

    public QuestionAdapter(Context ctx) {
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
                    R.layout.cell_lv_question, null);
            holder = new NoteViewHolder();
            holder.tvContent = (TextView) convertView
                    .findViewById(R.id.tvContent);

            holder.tvContent.setTextSize(StatedPerference.getFontSize() );

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
    }
}