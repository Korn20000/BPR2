package com.example.admin.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KoRn on 27-11-2017.
 */

public class PatientInformation {

    private int id;
    private int cpr;
    private double measuredLevel;
    //private SimpleDateFormat simple;
    private Date date;

    /*
    public SimpleDateFormat getSimple() {
        return simple;
    }

    public void setSimple(SimpleDateFormat simple) {
        this.simple = simple;
    }*/



    public double getMeasuredLevel() {
        return measuredLevel;
    }

    public void setMeasuredLevel(double measuredLevel) {
        this.measuredLevel = measuredLevel;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCpr()
    {
        return cpr;
    }

    public void setCpr(int cpr)
    {
        this.cpr = cpr;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




}