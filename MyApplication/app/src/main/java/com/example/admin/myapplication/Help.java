package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Admin on 09/11/2017.
 */

public class Help extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    String items[] = new String [] {"FAQ", "DIABTES INFO", "SUPPORT"};




/*    String[] names = {"GET HELP", "Question FAQ", "Question INFO"};*/


  /*  Spinner sp;*/
    TextView description;

 /*   ArrayAdapter<String> adapter;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.helpList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);

        description=(TextView) findViewById(R.id.spinnerText);
        description.setMovementMethod(new ScrollingMovementMethod());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                description.setText(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel neque sit amet odio condimentum viverra a et quam. Mauris placerat diam purus, vel pulvinar justo luctus ut. Praesent nisl tortor, maximus suscipit tincidunt non, rutrum non neque. Cras semper mattis turpis ornare molestie. Fusce ac elementum nunc. Nunc laoreet tortor aliquam mi consequat, nec ornare leo pulvinar. Fusce luctus mi dui, id iaculis risus pellentesque eleifend. Ut porta sit amet enim a laoreet. Ut enim enim, efficitur sit amet commodo ut, faucibus convallis magna. Fusce nec feugiat est, in venenatis justo. Nam ac augue urna. Etiam scelerisque lectus sed magna imperdiet, ac auctor elit tristique. Proin lobortis eros dolor, ac rutrum massa auctor eu. Proin at placerat dui, eu feugiat lectus.\n" +
                                "\n" +
                                "Quisque ultrices ligula nisi, efficitur consequat lorem consectetur eget. Mauris sit amet est ut nunc bibendum convallis. Phasellus nec justo varius, gravida orci aliquam, molestie urna. Sed sodales finibus scelerisque. Nam nisi purus, molestie ac dignissim in, viverra eu augue. Nam ultricies finibus diam vitae sodales. Sed eget volutpat ligula. Etiam semper ante ut dui pellentesque, id ultricies diam vehicula. Duis at gravida dolor. Mauris urna libero, aliquam sit amet tristique nec, mattis vel eros. Sed congue massa viverra scelerisque dictum. Sed eget lacus fermentum, porttitor diam ac, pretium ipsum. Nullam eu tempus odio. Cras eu est est. Sed nec suscipit massa. ");

            }
        });




     /*   sp=(Spinner) findViewById(R.id.my_spinner);
        description=(TextView) findViewById(R.id.spinnerText);
        description.setMovementMethod(new ScrollingMovementMethod());

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);

        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        description.setText("");
                        break;

                    case 1:
                        description.setText(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel neque sit amet odio condimentum viverra a et quam. Mauris placerat diam purus, vel pulvinar justo luctus ut. Praesent nisl tortor, maximus suscipit tincidunt non, rutrum non neque. Cras semper mattis turpis ornare molestie. Fusce ac elementum nunc. Nunc laoreet tortor aliquam mi consequat, nec ornare leo pulvinar. Fusce luctus mi dui, id iaculis risus pellentesque eleifend. Ut porta sit amet enim a laoreet. Ut enim enim, efficitur sit amet commodo ut, faucibus convallis magna. Fusce nec feugiat est, in venenatis justo. Nam ac augue urna. Etiam scelerisque lectus sed magna imperdiet, ac auctor elit tristique. Proin lobortis eros dolor, ac rutrum massa auctor eu. Proin at placerat dui, eu feugiat lectus.\n" +
                                "\n" +
                                "Quisque ultrices ligula nisi, efficitur consequat lorem consectetur eget. Mauris sit amet est ut nunc bibendum convallis. Phasellus nec justo varius, gravida orci aliquam, molestie urna. Sed sodales finibus scelerisque. Nam nisi purus, molestie ac dignissim in, viverra eu augue. Nam ultricies finibus diam vitae sodales. Sed eget volutpat ligula. Etiam semper ante ut dui pellentesque, id ultricies diam vehicula. Duis at gravida dolor. Mauris urna libero, aliquam sit amet tristique nec, mattis vel eros. Sed congue massa viverra scelerisque dictum. Sed eget lacus fermentum, porttitor diam ac, pretium ipsum. Nullam eu tempus odio. Cras eu est est. Sed nec suscipit massa. ");
                        break;

                    case 2:
                        description.setText(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel neque sit amet odio condimentum viverra a et quam. Mauris placerat diam purus, vel pulvinar justo luctus ut. Praesent nisl tortor, maximus suscipit tincidunt non, rutrum non neque. Cras semper mattis turpis ornare molestie. Fusce ac elementum nunc. Nunc laoreet tortor aliquam mi consequat, nec ornare leo pulvinar. Fusce luctus mi dui, id iaculis risus pellentesque eleifend. Ut porta sit amet enim a laoreet. Ut enim enim, efficitur sit amet commodo ut, faucibus convallis magna. Fusce nec feugiat est, in venenatis justo. Nam ac augue urna. Etiam scelerisque lectus sed magna imperdiet, ac auctor elit tristique. Proin lobortis eros dolor, ac rutrum massa auctor eu. Proin at placerat dui, eu feugiat lectus.\n" +
                                "\n" +
                                "Quisque ultrices ligula nisi, efficitur consequat lorem consectetur eget. Mauris sit amet est ut nunc bibendum convallis. Phasellus nec justo varius, gravida orci aliquam, molestie urna. Sed sodales finibus scelerisque. Nam nisi purus, molestie ac dignissim in, viverra eu augue. Nam ultricies finibus diam vitae sodales. Sed eget volutpat ligula. Etiam semper ante ut dui pellentesque, id ultricies diam vehicula. Duis at gravida dolor. Mauris urna libero, aliquam sit amet tristique nec, mattis vel eros. Sed congue massa viverra scelerisque dictum. Sed eget lacus fermentum, porttitor diam ac, pretium ipsum. Nullam eu tempus odio. Cras eu est est. Sed nec suscipit massa. ");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/




       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          super.onBackPressed();
        }/*

        super.onBackPressed();
        startActivity(new Intent(Help.this, MainActivity.class));
        finish();*/
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

   /* @Override
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.MainActivity) {
            Intent searchIntent = new Intent(Help.this, MainActivity.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        }else

        if (id == R.id.nav_history) {
            Intent searchIntent = new Intent(Help.this, History.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        } else if (id == R.id.nav_graph) {
            Intent searchIntent = new Intent(Help.this, Graph.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        }else if (id==R.id.nav_setting){
            Intent searchIntent = new Intent(Help.this, Setting.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
