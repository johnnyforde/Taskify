package com.example.johnny.pennapps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.SCHEDULE);

        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("train", "101");
        map.put("from", "6:30 AM");
        map.put("to", "7:40 AM");
        mylist.add(map);
        map = new HashMap<String, String>();
        map.put("train", "103(x)");
        map.put("from", "6:35 AM");
        map.put("to", "7:45 AM");
        mylist.add(map);
// ...
        BaseAdapter in = new ListViewAdapter(this, mylist, R.layout.listview_row,
                new String[] {"train", "from", "to"}, new int[] {R.id.FirstText, R.id.SecondText, R.id.ThirdText});
        list.setAdapter();
    }
}

