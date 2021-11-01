package com.example.ysyzhangben;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {


    private DBHelper helper;
    private  ListView listView;
    private  ImageButton Add;
    private List<costList>list;
    int[] month = new int[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {
        list=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("account",null,null,null,null,
                null,null);
        for(int i = 0; i < month.length; ++i ) {
            month[i] = 0;
        }
        while (cursor.moveToNext()){
            costList clist=new costList();//构造实例
            clist.set_id(cursor.getString(cursor.getColumnIndex("_id")));
            clist.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
            clist.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            //Log.i("date", cursor.getString(cursor.getColumnIndex("Month")));
            //Log.i("date", cursor.getString(cursor.getColumnIndex("Date")));
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            String monthStr = date.substring(date.indexOf('-')+1,date.lastIndexOf('-'));
            // Log.i("minth", month);
            int monthInt = Integer.parseInt(monthStr);
            int money = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Money")));
            month[monthInt-1] += money;
            clist.setMoney(cursor.getString(cursor.getColumnIndex("Money")));
            list.add(clist);
        }
        //绑定适配器
        listView.setAdapter(new ListAdapter(this,list));
        db.close();
    }




    private void initView() {
       helper=new DBHelper(MainActivity.this);
       listView = findViewById(R.id.list_view);
       Add=findViewById(R.id.add);
}

    //事件：添加记录
    public void addAccount(View view){//跳转
        Intent intent=new Intent(MainActivity.this,new_cost.class);
        startActivityForResult(intent,1);
    }

    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,DailyView.class);
        startActivity(intent);
    }

    public void toDashboard(View view) {
        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        Bundle bundle1 = new Bundle();
        bundle1.putIntArray("array",month);
        intent.putExtras(bundle1);
        startActivityForResult(intent,11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //String result = data.getExtras().getString("result");
        //Log.i("result",data.getDataString());
        if(requestCode==1&&resultCode==1)
        {
            this.initData();
        }
    }
}