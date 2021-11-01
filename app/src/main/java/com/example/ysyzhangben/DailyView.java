package com.example.ysyzhangben;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyView extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private Context context;
    private LinearLayout llDate;
    private TextView tvDate;
    private int year, month, day, realMonth;
    //在TextView上显示的字符
    private StringBuffer date;
    private DBHelper helper;
    private ListView listView;
    private List<costList> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        context = this;
        date = new StringBuffer();
        initView();
        initDateTime();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        llDate = (LinearLayout) findViewById(R.id.ll_date);
        tvDate = (TextView) findViewById(R.id.tv_date);
//        llTime = (LinearLayout) findViewById(R.id.ll_time);
//        tvTime = (TextView) findViewById(R.id.tv_time);
        llDate.setOnClickListener(this);
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        realMonth = calendar.get(Calendar.MONTH) + 1;
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        hour = calendar.get(Calendar.HOUR);
//        minute = calendar.get(Calendar.MINUTE);

    }

    @Override
    public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        dialog.dismiss();
                        realMonth = month+1;
                        tvDate.setText(date.append(String.valueOf(year))
                                .append("年")
                                .append(String.valueOf(realMonth))
                                .append("月")
                                .append(day)
                                .append("日"));
                        String dateString = year + "-" + (realMonth) + "-" + day;
                        helper=new DBHelper(DailyView.this);
                        listView = findViewById(R.id.daily_listview);
                        list=new ArrayList<>();
                        SQLiteDatabase db=helper.getReadableDatabase();
                        Cursor cursor=db.query("account",null,null,null,null,
                                null,null);
                        while (cursor.moveToNext()){
                            String dateAll = cursor.getString(cursor.getColumnIndex("Date"));
                            //String monthStr = date.substring(date.indexOf('-')+1,date.lastIndexOf('-'));

                            if(dateAll.equals(dateString) ){
                                Log.i("TAG", "jinru ");
                                costList clist=new costList();//构造实例
                                clist.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                                clist.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                                clist.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                                int money = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Money")));
                                clist.setMoney(cursor.getString(cursor.getColumnIndex("Money")));
                                list.add(clist);
                            }else {
//                                Log.i("monthlist", monthlist);
//                                Log.i("monthstr", monthStr);
                            }
                        }
                        //绑定适配器
                        listView.setAdapter(new ListAdapter(context,list));
                        db.close();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("选择日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year, realMonth-1, day, this);


    }


    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;


    }
}