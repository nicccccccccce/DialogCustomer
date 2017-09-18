package com.eshi.bridge.dialogcustomer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.eshi.bridge.dialogcustomer.R;

import java.util.List;


/**
 * 用于单选对话框
 * Created by user on 2017/1/22.
 */

public class SingleItemAdapter extends BaseAdapter {
    private List<String> list;
    private int idx = -1;

    public SingleItemAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_single_choice, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cv.setText(list.get(position));
        holder.cv.setHeight(parent.getContext().getResources().getDimensionPixelSize(R.dimen.dimen_44));
        if (idx == position)
            holder.cv.setChecked(true);
        else
            holder.cv.setChecked(false);
        return convertView;
    }

    private class ViewHolder {
        CheckedTextView cv;

        public ViewHolder(View view) {
            cv = (CheckedTextView) view.findViewById(android.R.id.text1);
        }
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
