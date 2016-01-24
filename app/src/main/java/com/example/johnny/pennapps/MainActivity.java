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
import com.example.johnny.pennapps.Model.Scheduler.ScheduleAlgorithm;
import com.example.johnny.pennapps.Model.Scheduler.Scheduler;
import com.firebase.client.Firebase;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ScheduleAlgorithm taskScheduler = new ScheduleAlgorithm();

        List<TaskifyCalendarEvent> commitments = new ArrayList<TaskifyCalendarEvent>();
        TaskifyCommitment breakfast = new TaskifyCommitment("Breakfast", new DateTime(2016, 1, 25, 7, 30), new DateTime(2016, 1 , 25, 8, 30));
        TaskifyCommitment school = new TaskifyCommitment("School", new LocalTime(7,45), new LocalTime(14,50), new LocalDate(2016, 1, 25), new LocalDate(2016, 5, 17), RepeatingInterval.WEEKDAYS);
        commitments.addAll(breakfast.getScheduledTimes());
        commitments.addAll(school.getScheduledTimes());
        Map<Long, TaskifyCalendarEvent> availabilities = schedule.addCommitments(commitments);
//        Log.i("KEVIN", "Commitments: " + commitments);

//        Firebase database = new Firebase("https://pennTaskify.firebaseio.com/");
//        database.child("lecture").setValue("CS-101");

//        public TaskifyTask(String name, int difficulty, DateTime deadline, Duration estimateTime, Duration timeSpent, boolean optional) {
        TaskifyTask chemlab = new TaskifyTask("Chem Lab", 3, new DateTime(2016,1,28,21, 0), new Duration(Hours.hours(3).toStandardDuration().getMillis()), new Duration(0), false);
        Log.i("chemlab", chemlab.getDeadline().toString());
        List<TaskifyTask> tasks = new ArrayList<TaskifyTask>(); tasks.add(chemlab);
        taskScheduler.scheduleTasksByPriority(tasks, availabilities);


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
