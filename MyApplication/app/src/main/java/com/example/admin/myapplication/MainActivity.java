package com.example.admin.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import com.gigamole.library.PulseView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    PulseView pulseView;
    ProgressBar myProgressBar;
    TextView txt;

    private boolean isUserClickedBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myProgressBar = (ProgressBar)findViewById(R.id.myprogressbar);

        pulseView = (PulseView) findViewById(R.id.bloodDrop);
        pulseView.startPulse();

       pulseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt =(TextView) findViewById(R.id.myText);
                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
                txt.setTypeface(myCustomFont);
                txt.setText("Measuring...");

                pulseView.setEnabled(false);

                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();

                 }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            myProgressBar.setVisibility(View.VISIBLE);
            myProgressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<100; i++){
                publishProgress(i);
                SystemClock.sleep(100);
                pulseView.finishPulse();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            myProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            myProgressBar.setVisibility(View.GONE);
            pulseView.setEnabled(true);
        }
    }


 /*   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
 @Override
 public void onBackPressed()
 {
     if(!isUserClickedBackButton)
     {
         Toast.makeText(this, "press back button again to exit", Toast.LENGTH_LONG).show();
         isUserClickedBackButton = true;
     }else
     {
         super.onBackPressed();
     }     new CountDownTimer(3000,1000)
     {
         @Override
         public void onTick(long l) {
         }

         @Override
         public void onFinish() {
            isUserClickedBackButton= false;
         }
     }.start();

 }

    //creates the method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action
        // bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.info:
               // Log.i(TAG, "start settings activity");
                Intent intent = new Intent(MainActivity.this, Info.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

  @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
      // Handle navigation view item clicks here.
      int id = item.getItemId();

      if (id == R.id.nav_history) {

          Intent searchIntent = new Intent(MainActivity.this, History.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
      } else if (id == R.id.nav_graph) {

          Intent searchIntent = new Intent(MainActivity.this, Graph.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

      } else if (id == R.id.nav_help) {
          Intent searchIntent = new Intent(MainActivity.this, Help.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
      }else if (id == R.id.nav_setting)
      {
          Intent searchIntent = new Intent(MainActivity.this, Setting.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
  }

}

