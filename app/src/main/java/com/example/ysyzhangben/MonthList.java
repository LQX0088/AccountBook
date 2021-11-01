package com.example.ysyzhangben;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MonthList extends AppCompatActivity {

    private DBHelper helper;
    private ListView listView;
    private List<costList> list;
    private TextView monthTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_list);
        monthTextView = findViewById(R.id.textView4);

        Bundle bundle = getIntent().getExtras();
        String monthlist = bundle.getString("monthlist");
        Log.i("MonthList", monthlist);
        monthTextView.setText(monthlist+"月账单详情如下");

        helper=new DBHelper(MonthList.this);
        listView = findViewById(R.id.daily_listview);
        list=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("account",null,null,null,null,
                null,null);

        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            String monthStr = date.substring(date.indexOf('-')+1,date.lastIndexOf('-'));

            if(Integer.parseInt(monthStr) == Integer.parseInt(monthlist)){
                Log.i("TAG", "jinru ");
                costList clist=new costList();//构造实例
                clist.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                clist.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                clist.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                int money = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Money")));
                clist.setMoney(cursor.getString(cursor.getColumnIndex("Money")));
                list.add(clist);
            }else {
                Log.i("monthlist", monthlist);
                Log.i("monthstr", monthStr);
            }

            //Log.i("date", cursor.getString(cursor.getColumnIndex("Month")));
            //Log.i("date", cursor.getString(cursor.getColumnIndex("Date")));

            // Log.i("minth", month);

        }
        //绑定适配器
        listView.setAdapter(new ListAdapter(this,list));
        db.close();
    }
}