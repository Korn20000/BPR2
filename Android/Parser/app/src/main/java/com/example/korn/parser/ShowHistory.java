package com.example.korn.parser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ShowHistory extends AppCompatActivity
{
    Button btnDate;
    int year_date, month_date, day_date;
    static final int DILOG_ID = 0;
    GraphView graphView;
    sqLiteDatabase = databse class..... Peter fucked up

    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_history);

        final Calendar calendar = Calendar.getInstance();
        year_date = calendar.get(Calendar.YEAR);
        month_date = calendar.get(Calendar.MONTH);
        day_date = calendar.get(Calendar.DAY_OF_MONTH);
        graphView = (GraphView) findViewById(R.id.graph);

        showDialogOnButton();
    }


    public void showDialogOnButton()
    {
        btnDate = (Button)findViewById(R.id.b6);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(DILOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id == DILOG_ID)
        {
            return new DatePickerDialog(this, dpickerListener, year_date, month_date, day_date);
        }
        else
        {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            year_date = year;
            month_date = month;
            day_date = dayOfMonth;
            Toast.makeText(ShowHistory.this, year_date + "/" + month_date + "/" + day_date, Toast.LENGTH_LONG).show();
        }
    };


    private DataPoint[] getDataPoint()
    {
        String[] columns = ("xValues", "yValues");
        Cursor cursor = sqLiteDatabase.query("myTable", columns, null, null, null, null, null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getInt(0), cursor.getInt(1));
        }
        return dp;
    }

}
