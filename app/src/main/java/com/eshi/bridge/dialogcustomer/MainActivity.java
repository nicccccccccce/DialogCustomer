package com.eshi.bridge.dialogcustomer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eshi.bridge.dialogcustomer.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button, button2, button3, button4, button5, button6, button7, button8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button1);
        button.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                DialogUtil.getInstance(this).setTitleU("Title").setMessageU("this is a message")
                        .setPositiveButton("确认", null).setNegativeButton("取消", new DialogUtil.OnNegativeListener() {
                    @Override
                    public void OnDialog(DialogUtil dialog, View v) {

                    }
                }).show();
                break;
            case R.id.button2:
                DialogUtil.getInstance(this).setTitleU("Title").setMessageU("this is a message")
                        .setPositiveButton("确认", null).show();
                break;
            case R.id.button3:
                DialogUtil.getInstance(this).setTitleU("Title").setMessageU("this is a message")
                        .setCancelable(true).show();
                break;
            case R.id.button4:
                DialogUtil.getInstance(this).setTitleU("Title").setMessageU(getResources().getString(R.string.dialog_message))
                        .setMessageGravity(Gravity.LEFT | Gravity.TOP).setCancelable(true).show();
                break;
            case R.id.button5:
                DialogUtil.getInstance(this).setTitleU("Title").setMessageU("this is a message")
                        .setPositiveButton("确认", null).setPositiveButtonColor(Color.BLUE).show();
                break;
            case R.id.button6:
                final List<String> list = getlist();
                DialogUtil.getInstance(this).setTitleU("单选").setSingleChoiceItems(list, 0, new DialogUtil.OnSingleItemListener() {
                    @Override
                    public void OnItem(DialogUtil dialog, int position) {
                        Toast.makeText(MainActivity.this, "" + list.get(position), Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确认", new DialogUtil.OnPositiveListener() {
                    @Override
                    public void OnDialog(DialogUtil dialog, View v) {
                        dialog.onDestroyDialog();
                    }
                }).show();
                break;
            case R.id.button7:
                final List<String> list1 = getlist();
                DialogUtil.getInstance(this).setTitleU("多选").setMultiChoiceItems(list1, null, new DialogUtil.OnMultiItemListener() {
                    @Override
                    public void OnItem(DialogUtil dialog, int position, boolean isChecked) {
                        if (isChecked)
                            Toast.makeText(MainActivity.this, "" + list1.get(position), Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确认", new DialogUtil.OnPositiveListener() {
                    @Override
                    public void OnDialog(DialogUtil dialog, View v) {
                        dialog.onDestroyDialog();
                    }
                }).show();
                break;
            case R.id.button8:
                DialogUtil.getInstance(this).setCustomerView(R.layout.dialog_customer_a).setPositiveButton("确认", new DialogUtil.OnPositiveListener() {
                    @Override
                    public void OnDialog(DialogUtil dialog, View v) {
                        dialog.onDestroyDialog();
                    }
                }).show();
                break;
        }
    }

    private List getlist() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item 第" + sumToChinese(i) + "项");
        }
        return list;
    }

    private String sumToChinese(int i) {
        String[] str = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
        if (i >= str.length || i < 0)
            return "零";
        else
            return str[i];

    }
}
