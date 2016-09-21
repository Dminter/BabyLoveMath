package com.zncm.babylovemath.modules.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zncm.babylovemath.R;
import com.zncm.babylovemath.data.BaseData;
import com.zncm.utils.sp.StatedPerference;

import java.util.List;

public abstract class MainAdapter extends BaseAdapter {

    private List<? extends BaseData> items;
    private Activity ctx;

    public MainAdapter(Activity ctx) {
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
                    R.layout.cell_lv_main, null);
            holder = new NoteViewHolder();
            holder.ivImage = (ImageView) convertView
                    .findViewById(R.id.ivImage);
            holder.tvText = (TextView) convertView
                    .findViewById(R.id.tvText);
            holder.tvText.setTextSize(StatedPerference.getFontSize() );
            convertView.setTag(holder);
        } else {
            holder = (NoteViewHolder) convertView.getTag();
        }
        setData(position, holder);
        return convertView;
    }

    public abstract void setData(int position, NoteViewHolder holder);

    public class NoteViewHolder {
        public ImageView ivImage;
        public TextView tvText;
    }
}