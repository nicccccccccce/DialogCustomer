package com.eshi.bridge.dialogcustomer.utils;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshi.bridge.dialogcustomer.R;
import com.eshi.bridge.dialogcustomer.adapter.MultiItemAdapter;
import com.eshi.bridge.dialogcustomer.adapter.SingleItemAdapter;

import java.util.List;


/**
 * 变量声明
 * Created by user on 2016/11/30.
 */
public class DialogUtil {
    protected TextView bt_confirm, bt_cancel;
    protected TextView tv_title, tv_msg;
    protected View v_middle, v_line_h;
    protected Activity context;
    protected LinearLayout linear_b;
    protected OnPositiveListener mOnPositiveListener;
    protected OnNegativeListener mOnNegativeListener;
    protected RelativeLayout rel_content;
    private static AlertDialog mAlertDialog;
    private static DialogUtil mDialogUtil;

    private DialogUtil(Activity activity) {
        this.context = activity;
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(activity, R.style.dialog_white_angel).create();
            mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mDialogUtil != null)
                        mDialogUtil.onDestroyDialog();
                }
            });
            createU();
        }
    }

    public static DialogUtil getInstance(Activity activity) {
        if (mDialogUtil == null)
            mDialogUtil = new DialogUtil(activity);
        return mDialogUtil;
    }


    public DialogUtil setPositiveButton(String positive, OnPositiveListener mOnPositiveListener) {
        this.mOnPositiveListener = mOnPositiveListener;

        if (bt_confirm != null && positive != null) {
            if (v_line_h.getVisibility() == View.GONE)
                v_line_h.setVisibility(View.VISIBLE);
            linear_b.setVisibility(View.VISIBLE);
            bt_confirm.setText(positive);
            bt_confirm.setVisibility(View.VISIBLE);
        }
        showMiddleLine();
        return mDialogUtil;
    }

    public DialogUtil setNegativeButton(String negative, OnNegativeListener mOnNegativeListener) {
        this.mOnNegativeListener = mOnNegativeListener;
        if (bt_cancel != null && negative != null) {
            if (v_line_h.getVisibility() == View.GONE)
                v_line_h.setVisibility(View.VISIBLE);
            linear_b.setVisibility(View.VISIBLE);
            bt_cancel.setText(negative);
            bt_cancel.setVisibility(View.VISIBLE);
        }
        showMiddleLine();
        return mDialogUtil;
    }

    public DialogUtil setPositiveButtonColor(int color) {
        if (bt_confirm != null)
            bt_confirm.setTextColor(color);
        return mDialogUtil;
    }

    public DialogUtil setNegativeButtonColor(int color) {
        if (bt_cancel != null)
            bt_cancel.setTextColor(color);
        return mDialogUtil;
    }

    private void showMiddleLine() {
        if (bt_confirm != null && bt_confirm.getVisibility() == View.VISIBLE)
            if (bt_cancel != null && bt_cancel.getVisibility() == View.VISIBLE)
                v_middle.setVisibility(View.VISIBLE);
    }

    private void createU() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common_view, null);
        show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.setCancelable(false);
        tv_title = (TextView) view.findViewById(R.id.title);
        tv_msg = (TextView) view.findViewById(R.id.message);
        v_line_h = view.findViewById(R.id.vw_line_h);
        linear_b = (LinearLayout) view.findViewById(R.id.linear_bottom);
        bt_confirm = (TextView) view.findViewById(R.id.sure);
        bt_cancel = (TextView) view.findViewById(R.id.cansel);
        v_middle = view.findViewById(R.id.v_middle);
        rel_content = (RelativeLayout) view.findViewById(R.id.content_view);
        tv_msg.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        tv_msg.setGravity(Gravity.CENTER_HORIZONTAL);
        bt_cancel.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        bt_confirm.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                if (mOnPositiveListener != null && mDialogUtil != null)
                    mOnPositiveListener.OnDialog(mDialogUtil, v);
                onDestroyDialog();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                if (mOnNegativeListener != null && mDialogUtil != null)
                    mOnNegativeListener.OnDialog(mDialogUtil, v);
                onDestroyDialog();
            }
        });
        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                onDestroyDialog();
            }
        });
        Window alertWindow = mAlertDialog.getWindow();
        alertWindow.setWindowAnimations(R.style.dialog_animator);
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = alertWindow.getAttributes();
        lParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//(int) (display.getHeight() * 0.45);
        lParams.width = (int) (display.getWidth() * 0.85);
        alertWindow.setAttributes(lParams);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * activity销毁时调用
     */
    public void onDestroyDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();
        mAlertDialog = null;
        mDialogUtil = null;
    }

    public void dismiss() {
        if (mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();
    }

    public void show() {

        if (!context.isFinishing() && mAlertDialog != null && !mAlertDialog.isShowing())
            mAlertDialog.show();
    }

    public DialogUtil setCancelable(boolean is) {
        mAlertDialog.setCancelable(is);
        return mDialogUtil;
    }

    public DialogUtil setTitleU(String titleS) {
        if (tv_title != null && titleS != null) {
            if (tv_title.getVisibility() == View.GONE)
                tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(titleS);
        }
        return mDialogUtil;
    }

    public DialogUtil setMessageU(String msg) {
        if (tv_msg != null && msg != null) {
            if (rel_content.getVisibility() == View.VISIBLE)
                rel_content.setVisibility(View.GONE);
            if (tv_msg.getVisibility() == View.GONE)
                tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(msg);
        }

        return mDialogUtil;
    }

    public DialogUtil setMessageGravity(int gravity) {
        if (tv_msg != null)
            tv_msg.setGravity(gravity);
        return mDialogUtil;
    }

    public DialogUtil setContextViewU(View view) {
        if (tv_msg != null && view != null) {
            if (tv_msg.getVisibility() == View.VISIBLE)
                tv_msg.setVisibility(View.GONE);
            if (rel_content.getVisibility() == View.GONE)
                rel_content.setVisibility(View.VISIBLE);
            rel_content.removeAllViewsInLayout();
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rel_content.addView(view, params);
        }

        return mDialogUtil;
    }

    public DialogUtil setListViewU(ListAdapter adapter) {
        ListView listView = new ListView(context);
        listView.setAdapter(adapter);
        setContextViewU(listView);
        return mDialogUtil;
    }

    public interface OnPositiveListener {
        void OnDialog(DialogUtil dialog, View v);
    }

    public interface OnNegativeListener {
        void OnDialog(DialogUtil dialog, View v);
    }

    public DialogUtil setSingleChoiceItems(List<String> list, int position, final OnSingleItemListener onSingleItemListener) {
        final SingleItemAdapter adapter = new SingleItemAdapter(list);
        adapter.setIdx(position);
        ListView listView = new ListView(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view;
                adapter.setIdx(i);
                cv.setChecked(true);
                adapter.notifyDataSetChanged();
                if (onSingleItemListener != null)
                    onSingleItemListener.OnItem(mDialogUtil, i);
//                mDialogUtil.onDestroyDialog();
            }
        });
        setContextViewU(listView);
        return mDialogUtil;
    }

    public DialogUtil setCustomerView(int resId) {
        View view = LayoutInflater.from(context).inflate(resId, null);
        setContextViewU(view);
        return mDialogUtil;
    }

    public DialogUtil setMultiChoiceItems(List<String> list, boolean[] bl, final OnMultiItemListener onMultiItemListener) {
        MultiItemAdapter itemAdapter = new MultiItemAdapter(list);
        itemAdapter.setCs(bl);
        ListView listView = new ListView(context);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view;
                if (cv.isChecked())
                    cv.setChecked(false);
                else
                    cv.setChecked(true);
                if (onMultiItemListener != null)
                    onMultiItemListener.OnItem(mDialogUtil, i, cv.isChecked());
//                mDialogUtil.onDestroyDialog();
            }
        });
        setContextViewU(listView);
        return mDialogUtil;
    }

    public interface OnSingleItemListener {
        void OnItem(DialogUtil dialog, int position);
    }

    public interface OnMultiItemListener {
        void OnItem(DialogUtil dialog, int position, boolean isChecked);
    }
}
