package com.eshi.bridge.dialogcustomer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * 用于多选对话框
 * Created by user on 2017/1/22.
 */

public class MultiItemAdapter extends BaseAdapter {
    private List<String> list;
    private boolean[] cs;

    public MultiItemAdapter(List<String> list) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.position = position;
        holder.cv.setText(list.get(position));
        if (cs != null)
            for (int i = 0; i < cs.length; i++) {
                if (position == i && i < getCount())
                    holder.cv.setChecked(cs[i]);
            }
        return convertView;
    }

    private class ViewHolder {
        CheckedTextView cv;
        int position;

        public ViewHolder(View view) {
            cv = (CheckedTextView) view.findViewById(android.R.id.text1);
        }


    }

    public void setCs(boolean[] cs) {
        this.cs = cs;
    }
}
