package com.example.korn.parser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    String j_string;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getJson(View view)
    {
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String json_url;
        String json_string;

        @Override
        protected void onPreExecute()
        {
            json_url = "https://neuropterous-object.000webhostapp.com/Bachelor/json_get.php";
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            URL url = null;
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((json_string = bufferedReader.readLine()) !=null)
                {
                    stringBuilder.append(json_string + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
                catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
                catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result)
        {
            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText(result);
            j_string = result;
        }
    }

    public void parseJSON(View view)
    {
        if(j_string == null)
        {
            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(this, DisplayList.class);
            intent.putExtra("json_data", j_string);
            startActivity(intent);
        }
    }
}
