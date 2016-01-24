package com.example.johnny.pennapps;

/**
 * Created by Johnny on 23/01/2016.
 */
        import static com.example.johnny.pennapps.Constant.FIRST_COLUMN;
        import static com.example.johnny.pennapps.Constant.SECOND_COLUMN;
        import static com.example.johnny.pennapps.Constant.THIRD_COLUMN;

        import java.util.ArrayList;
        import java.util.HashMap;

        import android.app.Activity;
        import android.os.Bundle;
        import android.widget.ListView;


public class MultiColumnActivity extends Activity
{
    private ArrayList<HashMap> list;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lview = (ListView) findViewById(R.id.listView);
        populateList();
        listviewAdapter adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);
    }

    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap temp = new HashMap();
        temp.put(FIRST_COLUMN,"08:00am");
        temp.put(SECOND_COLUMN, "Laundry");
        temp.put(THIRD_COLUMN, "00:30");

        list.add(temp);

        HashMap temp1 = new HashMap();
        temp1.put(FIRST_COLUMN,"10:00am");
        temp1.put(SECOND_COLUMN, "Do Lab Report");
        temp1.put(THIRD_COLUMN, "02:00");

        list.add(temp1);

        HashMap temp2 = new HashMap();
        temp2.put(FIRST_COLUMN,"12:00pm");
        temp2.put(SECOND_COLUMN, "Lunch with Friend");
        temp2.put(THIRD_COLUMN, "01:00");

        list.add(temp2);

        HashMap temp3 = new HashMap();
        temp3.put(FIRST_COLUMN,"6:00pm");
        temp3.put(SECOND_COLUMN, "Cook Dinner");
        temp3.put(THIRD_COLUMN, "00:45");

        list.add(temp3);

        HashMap temp4 = new HashMap();
        temp4.put(FIRST_COLUMN,"8:00pm");
        temp4.put(SECOND_COLUMN, "Study for Test");
        temp4.put(THIRD_COLUMN, "01:30");

        list.add(temp4);

    }
}