package com.example.kevin.measureme;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayHistory extends AppCompatActivity {

    private DBHelper mydb ;

    TextView id ;
    TextView name ;
    TextView datetime;
    TextView gender;
    TextView shoulder;
    TextView chest;
    TextView waist;
    TextView size;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);

        id = (TextView) findViewById(R.id.textViewId);
        name = (TextView) findViewById(R.id.textViewName);
        datetime = (TextView) findViewById(R.id.textViewDatetime);
        gender = (TextView) findViewById(R.id.textViewGender);
        shoulder = (TextView) findViewById(R.id.textViewShoulder);
        chest = (TextView) findViewById(R.id.textViewChest);
        waist = (TextView) findViewById(R.id.textViewWaist);
        size = (TextView) findViewById(R.id.textViewSize);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String i = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_ID));
                String nam = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_NAME));
                String datetim = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_DATETIME));
                String gende = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_GENDER));
                String shoulde = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_SHOULDER));
                String ches = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_CHEST));
                String wais = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_WAIST));
                String siz = rs.getString(rs.getColumnIndex(DBHelper.HISTORY_COLUMN_SIZE));

                if (!rs.isClosed())  {
                    rs.close();
                }

                id.setText("History #" + (CharSequence)i);
                id.setFocusable(false);
                id.setClickable(false);

                name.setText("Name: " + (CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                datetime.setText("Date and time: " + (CharSequence)datetim);
                datetime.setFocusable(false);
                datetime.setClickable(false);

                gender.setText("Gender: " + (CharSequence)gende);
                gender.setFocusable(false);
                gender.setClickable(false);

                shoulder.setText("Shoulder: " + (CharSequence)shoulde);
                shoulder.setFocusable(false);
                shoulder.setClickable(false);

                chest.setText("Chest: " + (CharSequence)ches);
                chest.setFocusable(false);
                chest.setClickable(false);

                waist.setText("Waist: " + (CharSequence)wais);
                waist.setFocusable(false);
                waist.setClickable(false);

                size.setText("Your size: " + (CharSequence)siz);
                size.setFocusable(false);
                size.setClickable(false);
            }
        }
    }
}
