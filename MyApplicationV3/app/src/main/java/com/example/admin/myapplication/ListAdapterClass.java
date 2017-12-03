package com.example.admin.myapplication;

/**
 * Created by KoRn on 27-11-2017.
 */


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.List;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.security.auth.Subject;

public class ListAdapterClass extends ArrayAdapter<PatientInformation> {

    Context context;
    List<Subject> valueList;

    public ListAdapterClass(@NonNull Context context, @NonNull List<PatientInformation> objects) {
        super(context, 0, objects);
    }

/*    public ListAdapterClass(List<Subject> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
    }*/
/*
    @Override
    public int getCount()
    {
        return this.valueList.size();
    }*/

/*
    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }
*/

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            //LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            //convertView = layoutInfiater.inflate(R.layout.layout_items, null);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_items,parent,false);


            //viewItem.bloodLevel = convertView.findViewById(R.id.bloodLevel);
            //viewItem.TextViewCPR = convertView.findViewById(R.id.dateLevel);
            viewItem.TextViewMeasuredLevel = convertView.findViewById(R.id.bloodLevel);
            viewItem.TextViewDate = convertView.findViewById(R.id.dateLevel);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        PatientInformation patientInformation= getItem(position);
      //  viewItem.TextViewID.setText("ID: " + patientInformation.getId());
       // viewItem.TextViewCPR.setText("CPR: " + patientInformation.getCpr());

        viewItem.TextViewMeasuredLevel.setText("Measured Level: " + patientInformation.getMeasuredLevel());
        String reformatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(patientInformation.getDate());
        viewItem.TextViewDate.setText("Date: " + reformatedDate);
        //viewItem.TextViewSubjectName.setText(valueList.get(position).SubjectName);

        return convertView;
    }
}

class ViewItem
{
   // TextView TextViewID;
   // TextView TextViewCPR;
    TextView TextViewMeasuredLevel;
    TextView TextViewDate;

}
