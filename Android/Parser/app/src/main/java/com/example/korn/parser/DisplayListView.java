package com.example.korn.parser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity
{

    String cpr, name, surname, age, type, phone, address, email;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PatientAdapter patientAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_listview_layout);
        listView = (ListView)findViewById(R.id.listview);
        patientAdapter = new PatientAdapter(this, R.layout.row_layout);
        listView.setAdapter(patientAdapter);
        json_string = getIntent().getExtras().getString("json_data");
        try
        {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;


            while(count < jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                cpr = JO.getString("CPR");
                name = JO.getString("Name");
                surname = JO.getString("Surname");
                age = JO.getString("Age");
                type = JO.getString("Type");
                phone = JO.getString("Phone");
                address = JO.getString("Address");
                email = JO.getString("Email");

                Patients patients = new Patients(cpr, name, surname, age, type, phone, address, email);
                patientAdapter.add(patients);
                count++;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}