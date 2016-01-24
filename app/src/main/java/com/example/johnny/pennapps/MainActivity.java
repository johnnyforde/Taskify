package com.example.johnny.pennapps;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.johnny.pennapps.Model.Events.RepeatingInterval;
import com.example.johnny.pennapps.Model.Events.TaskifyCalendarEvent;
import com.example.johnny.pennapps.Model.Events.TaskifyCommitment;
import com.example.johnny.pennapps.Model.Events.TaskifyTask;
import com.example.johnny.pennapps.Model.Scheduler.Scheduler;
import com.firebase.client.Firebase;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Scheduler schedule = new Scheduler();

        List<TaskifyCalendarEvent> commitments = new ArrayList<TaskifyCalendarEvent>();
        TaskifyCommitment breakfast = new TaskifyCommitment("Breakfast", new DateTime(2016, 1, 25, 6, 0), new DateTime(2016, 1 , 25, 7, 0));
        TaskifyCommitment school = new TaskifyCommitment("School", new LocalTime(8,0), new LocalTime(14,0), new LocalDate(2016, 1, 25), new LocalDate(2016, 1, 29), RepeatingInterval.WEEKDAYS);
        TaskifyTask mathHW = new TaskifyTask("Math Homework", 10, new DateTime(2016, 2, 1, 8, 0), Hours.hours(2).toStandardDuration(), false);
        TaskifyTask chemHW = new TaskifyTask("Chem Homework", 5, new DateTime(2016, 2, 6, 8, 0), Hours.hours(4).toStandardDuration(), false);
        schedule.addCommitment(breakfast);
        schedule.addCommitment(school);
        schedule.addTask(mathHW);
        schedule.addTask(chemHW);
        schedule.reschedule();
        Log.i("KEVIN", "Schedule: " + schedule.getSchedule());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
