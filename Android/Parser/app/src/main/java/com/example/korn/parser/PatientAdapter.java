package com.example.korn.parser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KoRn on 22-10-2017.
 */

public class PatientAdapter extends ArrayAdapter
{
    List list = new ArrayList();

    public PatientAdapter(Context context, int resource)
    {
        super(context, resource);
    }


    public void add(Patients object)
    {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;
        row = convertView;
        PatientHolder patientHolder;

        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            patientHolder = new PatientHolder();
            patientHolder.tx_CPR = (TextView)row.findViewById(R.id.tx_CPR);
            patientHolder.tx_name = (TextView)row.findViewById(R.id.tx_name);
            patientHolder.tx_surname = (TextView)row.findViewById(R.id.tx_surname);
            patientHolder.tx_age = (TextView)row.findViewById(R.id.tx_age);
            patientHolder.tx_type = (TextView)row.findViewById(R.id.tx_type);
            patientHolder.tx_phone = (TextView)row.findViewById(R.id.tx_phone);
            patientHolder.tx_address = (TextView)row.findViewById(R.id.tx_address);
            patientHolder.tx_email = (TextView)row.findViewById(R.id.tx_email);
            row.setTag(patientHolder);
        }

        else
        {
            patientHolder = (PatientHolder)row.getTag();
        }

        Patients patients = (Patients)this.getItem(position);
        patientHolder.tx_CPR.setText(patients.getID());
        patientHolder.tx_name.setText(patients.getfk_CPR());
        patientHolder.tx_surname.setText(patients.getMeasured_Level());
        patientHolder.tx_age.setText(patients.getDate());
        /*patientHolder.tx_type.setText(patients.getType());
        patientHolder.tx_phone.setText(patients.getPhone());
        patientHolder.tx_address.setText(patients.getAddress());
        patientHolder.tx_email.setText(patients.getEmail());*/
        return row;
    }

    static class PatientHolder
    {
        TextView tx_CPR, tx_name, tx_surname, tx_age, tx_type, tx_phone, tx_address, tx_email;
    }
}
