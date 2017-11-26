package com.example.korn.parser;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    String JSON_STRING;
    String json_string;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getJSON(View view)
    {
        new BackgroundTask().execute();
    }

    /*class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String json_url;

        @Override
        protected void onPreExecute()
        {
            json_url = "https://neuropterous-object.000webhostapp.com/src/php/process_history.php";
        }

        @Override
        protected String doInBackground(Void... voids)
        {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((JSON_STRING = bufferedReader.readLine()) !=null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
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
            json_string = result;
        }
    }*/

    class BackgroundTask extends AsyncTask<String... args>
    {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

        // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
        // Checking for SUCCESS TAG
        int success = json.getInt(TAG_SUCCESS);

        if (success == 1) {
            // products found
            // Getting Array of Products
            products = json.getJSONArray(TAG_PRODUCTS);

            // looping through All Products
            for (int i = 0; i < products.length(); i++) {
                JSONObject c = products.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString(TAG_PID);
                String name = c.getString(TAG_NAME);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put(TAG_PID, id);
                map.put(TAG_NAME, name);

                // adding HashList to ArrayList
                productsList.add(map);
            }
        } else {
            // no products found
            // Launch Add New product Activity
            Intent i = new Intent(getApplicationContext(),
                    NewProductActivity.class);
            // Closing all previous activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }

            return null;
    }
    }



    public void parseJSON(View view)
    {
        if(json_string == null)
        {
            Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(this, DisplayListView.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }
    }

    public void sendData(View view)
    {
        Intent intent = new Intent(this, InsertData.class);
        startActivity(intent);
    }

    public void histData(View view)
    {
        Intent intent = new Intent(this, ShowHistory.class);
        startActivity(intent);

    }
}


