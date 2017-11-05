package com.example.korn.parser;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertData extends AppCompatActivity
{

    EditText CPR, Date, Level;
    Button InsertData;
    RequestQueue requestQueue;
    String insertToTable = "https://neuropterous-object.000webhostapp.com/Bachelor/json_get_Insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);

        CPR = (EditText) findViewById(R.id.insertCPR);
        Date = (EditText) findViewById(R.id.insertDate);
        Level = (EditText) findViewById(R.id.insertLevel);
        InsertData = (Button) findViewById(R.id.b4);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        InsertData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                StringRequest request = new StringRequest(Request.Method.POST, insertToTable, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("CPR", CPR.getText().toString());
                        parameters.put("Date", Date.getText().toString());
                        parameters.put("Level", Level.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }

        });
    }
}