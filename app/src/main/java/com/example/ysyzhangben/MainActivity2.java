package com.example.ysyzhangben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

//记录每月花销统计
public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    TextView jan;
    TextView feb;
    TextView mar;
    TextView apr;
    TextView may;
    TextView jun;
    TextView jul;
    TextView aug;
    TextView sept;
    TextView oct;
    TextView nov;
    TextView dec;
    TextView max;

    TextView jan1;
    TextView feb1;
    TextView mar1;
    TextView apr1;
    TextView may1;
    TextView jun1;
    TextView jul1;
//    TextView aug;
//    TextView sept;
//    TextView oct;
//    TextView nov;
//    TextView dec;
//    TextView max;
private LineChartView lineChart;

    String[] date = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};//X轴的标注
    int[] score;//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
//        Intent intent1=getIntent();
//        //Log.i("TAG", intent.getStringExtra("extra"));
//        String[] month = intent1.getStringArrayExtra("extra");
        Bundle bundle = getIntent().getExtras();
        int[] month = bundle.getIntArray("array");
        score = month;
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
        if(bundle != null)  {
            Log.i("Mainac2", String.valueOf(month[0]));
            jan.setText(String.valueOf(month[0]));
            feb.setText(String.valueOf(month[1]));
            mar.setText(String.valueOf(month[2]));
            apr.setText(String.valueOf(month[3]));
            may.setText(String.valueOf(month[4]));
            jun.setText(String.valueOf(month[5]));
            jul.setText(String.valueOf(month[6]));
            aug.setText(String.valueOf(month[7]));
            sept.setText(String.valueOf(month[8]));
            oct.setText(String.valueOf(month[9]));
            nov.setText(String.valueOf(month[10]));
            dec.setText(String.valueOf(month[11]));
            int maxNum = 0;
            for(int i = 0; i < month.length; ++i ) {
                if(month[i] > month[maxNum]) {
                    maxNum = i;
                }
            }
            if(month[maxNum] == 0) {
                max.setText("本年度没有花销");
            }else {
                switch(maxNum) {
                    case 0:
                        max.setText("January");
                        break;
                    case 1:
                        max.setText("February");
                        break;
                    case 2:
                        max.setText("March");
                        break;
                    case 3:
                        max.setText("April");
                        break;
                    case 4:
                        max.setText("May");
                        break;
                    case 5:
                        max.setText("June");
                        break;
                    case 6:
                        max.setText("July");
                        break;
                    case 7:
                        max.setText("August");
                        break;
                    case 8:
                        max.setText("September");
                        break;
                    case 9:
                        max.setText("October");
                        break;
                    case 10:
                        max.setText("November");
                        break;
                    case 11:
                        max.setText("December");
                        break;
                }
            }
        }

        //todo
//        jan = findViewById(R.id.January);
//        jan.setText(month[0]);


    }
    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 12;
        lineChart.setCurrentViewport(v);
    }


    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }


    public void initView() {
        jan = findViewById(R.id.January);
        feb = findViewById(R.id.February);
        mar = findViewById(R.id.March);
        apr = findViewById(R.id.April);
        may = findViewById(R.id.May);
        jun = findViewById(R.id.June);
        jul = findViewById(R.id.July);
        aug = findViewById(R.id.August);
        sept = findViewById(R.id.September);
        oct = findViewById(R.id.October);
        nov = findViewById(R.id.November);
        dec = findViewById(R.id.December);
        max = findViewById(R.id.max);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle1;
        String monthList;
        Log.i("TAG", "onClick: ");
        //Log.i("type", R.id.jan.typeOf());
        switch(view.getId()){
            case R.id.jan:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "1";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.feb:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "2";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.mar:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "3";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.apr:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "4";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
            case R.id.may:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "5";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.jun:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "6";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.jul:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "7";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.aug:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "8";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.sept:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "9";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.oct:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "10";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.nov:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "11";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
            case R.id.dec:
                intent=new Intent(MainActivity2.this,MonthList.class);
                //Intent intent=new Intent();
                bundle1 = new Bundle();
                monthList = "12";
                bundle1.putString("monthlist",monthList);
                intent.putExtras(bundle1);
                startActivityForResult(intent,11);
                break;
        }
    }
}