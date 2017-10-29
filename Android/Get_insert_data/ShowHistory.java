package com.example.korn.parser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ShowHistory extends AppCompatActivity
{
    Button btnDate;
    int year_date, month_date, day_date;
    static final int DILOG_ID = 0;


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

}


