package com.example.kevin.measureme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private static final int MY_PASSCODE_DIALOG_ID = 4;

    SharedPreferences prefs;

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        prefs = getSharedPreferences("credentials", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if(prefs.getString("passcode","no") == "no") {
            showInputDialog(MY_PASSCODE_DIALOG_ID);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "MEASURE");
        adapter.addFragment(new Tab2Fragment(), "HISTORY");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.change_size:
                Intent intent = new Intent(this,SizeDetails.class);
                startActivity(intent);
                return true;
            case R.id.clear_history:
                if(mydb.numberOfRows()>0) {
                    mydb.deleteAllHistory();
                    Toast.makeText(getApplicationContext(), "All history cleared.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "No history found.",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showInputDialog(int id) {
        AlertDialog.Builder builder;

        switch(id) {
            case MY_PASSCODE_DIALOG_ID:
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View layout = inflater.inflate(R.layout.create_passcode, (ViewGroup) findViewById(R.id.root));
                final EditText passcode1 = layout.findViewById(R.id.passcode1);
                final EditText passcode2 = layout.findViewById(R.id.passcode2);

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Create New Passcode for Admin");
                builder.setView(layout);

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        System.exit(1);
                    }
                });

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String strPasscode1 = passcode1.getText().toString();
                        String strPasscode2 = passcode2.getText().toString();
                        if (strPasscode1.equals(strPasscode2) && !strPasscode1.isEmpty() && !strPasscode2.isEmpty()) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("passcode", strPasscode1);
                            editor.commit();
                            removeDialog(MY_PASSCODE_DIALOG_ID);
                            Intent intent = new Intent(getApplicationContext(), SizeDetails.class);
                            startActivity(intent);
                        } else if (strPasscode1.isEmpty() && strPasscode2.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Passcode empty!", Toast.LENGTH_SHORT).show();
                            showInputDialog(MY_PASSCODE_DIALOG_ID);
                        } else {
                            Toast.makeText(getApplicationContext(), "Passcode did not match!", Toast.LENGTH_SHORT).show();
                            showInputDialog(MY_PASSCODE_DIALOG_ID);
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            default:
        }
    }
}
