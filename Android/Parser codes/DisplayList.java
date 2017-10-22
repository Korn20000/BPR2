package com.example.korn.parser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayList extends AppCompatActivity
{

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PatientAdapter patientAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_list_layout);
        listView = (ListView)findViewById(R.id.listview);
        patientAdapter = new PatientAdapter(this, R.layout.row_layout);
        listView.setAdapter(patientAdapter);
        json_string = getIntent().getExtras().getString("json_data");
        try
        {
            jsonObject = new JSONObject(json_string);
            jsonArray  = new JSONObject(json_string).getJSONArray("server_response");
            int count = 0;
            String cpr, name, surname, age, type, phone, address, email;

            while(count < jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                cpr = JO.getString("cpr");
                name = JO.getString("name");
                surname = JO.getString("surname");
                age = JO.getString("age");
                type = JO.getString("type");
                phone = JO.getString("phone");
                address = JO.getString("address");
                email = JO.getString("email");

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
