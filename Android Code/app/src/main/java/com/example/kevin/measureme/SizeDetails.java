package com.example.kevin.measureme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SizeDetails extends AppCompatActivity {

    private static final String LEN_PREFIX = "Count_";
    private static final String VAL_PREFIX = "IntValue_";

    private static final int MY_PASSCODE_DIALOG_ID = 4;

    SharedPreferences prefsSize, prefsCreds;

    EditText male_chest_xs_min, male_chest_xs_max, male_chest_s_min, male_chest_s_max,
            male_chest_m_min, male_chest_m_max, male_chest_l_min, male_chest_l_max,
            male_chest_xl_min, male_chest_xl_max, male_chest_xxl_min, male_chest_xxl_max;

    EditText male_waist_xs_min, male_waist_xs_max, male_waist_s_min, male_waist_s_max,
            male_waist_m_min, male_waist_m_max, male_waist_l_min, male_waist_l_max,
            male_waist_xl_min, male_waist_xl_max, male_waist_xxl_min, male_waist_xxl_max;

    EditText female_chest_xs_min, female_chest_xs_max,female_chest_s_min, female_chest_s_max,
            female_chest_m_min, female_chest_m_max, female_chest_l_min, female_chest_l_max,
            female_chest_xl_min, female_chest_xl_max, female_chest_xxl_min, female_chest_xxl_max;

    EditText female_waist_xs_min, female_waist_xs_max, female_waist_s_min, female_waist_s_max,
            female_waist_m_min, female_waist_m_max, female_waist_l_min, female_waist_l_max,
            female_waist_xl_min, female_waist_xl_max, female_waist_xxl_min, female_waist_xxl_max;

    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_details);

        showInputDialog(MY_PASSCODE_DIALOG_ID);

        prefsSize = getSharedPreferences("sizedetails", 0);
        prefsCreds = getSharedPreferences("credentials", 0);

        // male chest
        male_chest_xs_min = (EditText) findViewById(R.id.male_chest_xs_min);
        male_chest_xs_max = (EditText) findViewById(R.id.male_chest_xs_max);
        male_chest_s_min = (EditText) findViewById(R.id.male_chest_s_min);
        male_chest_s_max = (EditText) findViewById(R.id.male_chest_s_max);
        male_chest_m_min = (EditText) findViewById(R.id.male_chest_m_min);
        male_chest_m_max = (EditText) findViewById(R.id.male_chest_m_max);
        male_chest_l_min = (EditText) findViewById(R.id.male_chest_l_min);
        male_chest_l_max = (EditText) findViewById(R.id.male_chest_l_max);
        male_chest_xl_min = (EditText) findViewById(R.id.male_chest_xl_min);
        male_chest_xl_max = (EditText) findViewById(R.id.male_chest_xl_max);
        male_chest_xxl_min = (EditText) findViewById(R.id.male_chest_xxl_min);
        male_chest_xxl_max = (EditText) findViewById(R.id.male_chest_xxl_max);

        // male waist
        male_waist_xs_min = (EditText) findViewById(R.id.male_waist_xs_min);
        male_waist_xs_max = (EditText) findViewById(R.id.male_waist_xs_max);
        male_waist_s_min = (EditText) findViewById(R.id.male_waist_s_min);
        male_waist_s_max = (EditText) findViewById(R.id.male_waist_s_max);
        male_waist_m_min = (EditText) findViewById(R.id.male_waist_m_min);
        male_waist_m_max = (EditText) findViewById(R.id.male_waist_m_max);
        male_waist_l_min = (EditText) findViewById(R.id.male_waist_l_min);
        male_waist_l_max = (EditText) findViewById(R.id.male_waist_l_max);
        male_waist_xl_min = (EditText) findViewById(R.id.male_waist_xl_min);
        male_waist_xl_max = (EditText) findViewById(R.id.male_waist_xl_max);
        male_waist_xxl_min = (EditText) findViewById(R.id.male_waist_xxl_min);
        male_waist_xxl_max = (EditText) findViewById(R.id.male_waist_xxl_max);

        // female chest
        female_chest_xs_min = (EditText) findViewById(R.id.female_chest_xs_min);
        female_chest_xs_max = (EditText) findViewById(R.id.female_chest_xs_max);
        female_chest_s_min = (EditText) findViewById(R.id.female_chest_s_min);
        female_chest_s_max = (EditText) findViewById(R.id.female_chest_s_max);
        female_chest_m_min = (EditText) findViewById(R.id.female_chest_m_min);
        female_chest_m_max = (EditText) findViewById(R.id.female_chest_m_max);
        female_chest_l_min = (EditText) findViewById(R.id.female_chest_l_min);
        female_chest_l_max = (EditText) findViewById(R.id.female_chest_l_max);
        female_chest_xl_min = (EditText) findViewById(R.id.female_chest_xl_min);
        female_chest_xl_max = (EditText) findViewById(R.id.female_chest_xl_max);
        female_chest_xxl_min = (EditText) findViewById(R.id.female_chest_xxl_min);
        female_chest_xxl_max = (EditText) findViewById(R.id.female_chest_xxl_max);

        // female waist
        female_waist_xs_min = (EditText) findViewById(R.id.female_waist_xs_min);
        female_waist_xs_max = (EditText) findViewById(R.id.female_waist_xs_max);
        female_waist_s_min = (EditText) findViewById(R.id.female_waist_s_min);
        female_waist_s_max = (EditText) findViewById(R.id.female_waist_s_max);
        female_waist_m_min = (EditText) findViewById(R.id.female_waist_m_min);
        female_waist_m_max = (EditText) findViewById(R.id.female_waist_m_max);
        female_waist_l_min = (EditText) findViewById(R.id.female_waist_l_min);
        female_waist_l_max = (EditText) findViewById(R.id.female_waist_l_max);
        female_waist_xl_min = (EditText) findViewById(R.id.female_waist_xl_min);
        female_waist_xl_max = (EditText) findViewById(R.id.female_waist_xl_max);
        female_waist_xxl_min = (EditText) findViewById(R.id.female_waist_xxl_min);
        female_waist_xxl_max = (EditText) findViewById(R.id.female_waist_xxl_max);

        final int[] male_chest_cloth_size;
        final int[] male_waist_cloth_size;
        final int[] female_chest_cloth_size;
        final int[] female_waist_cloth_size;

        int temp = prefsSize.getInt(LEN_PREFIX + "male_chest_cloth_size", 0);
        if(temp == 0) {
            male_chest_cloth_size = new int[]{80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124};
            male_waist_cloth_size = new int[]{68, 72, 76, 80, 84, 88, 92, 96, 100, 104, 108, 112};
            female_chest_cloth_size = new int[]{76, 80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120};
            female_waist_cloth_size = new int[]{60, 64, 68, 72, 76, 80, 84, 88, 92, 96, 100, 104};

            storeIntArray("male_chest_cloth_size", male_chest_cloth_size);
            storeIntArray("male_waist_cloth_size", male_waist_cloth_size);
            storeIntArray("female_chest_cloth_size", female_chest_cloth_size);
            storeIntArray("female_waist_cloth_size", female_waist_cloth_size);
        } else {
            male_chest_cloth_size=getFromPrefs("male_chest_cloth_size");
            male_waist_cloth_size=getFromPrefs("male_waist_cloth_size");
            female_chest_cloth_size=getFromPrefs("female_chest_cloth_size");
            female_waist_cloth_size=getFromPrefs("female_waist_cloth_size");
       }

        male_chest_xs_min.setText(String.valueOf(male_chest_cloth_size[0]));
        male_chest_xs_max.setText(String.valueOf(male_chest_cloth_size[1]));
        male_chest_s_min.setText(String.valueOf(male_chest_cloth_size[2]));
        male_chest_s_max.setText(String.valueOf(male_chest_cloth_size[3]));
        male_chest_m_min.setText(String.valueOf(male_chest_cloth_size[4]));
        male_chest_m_max.setText(String.valueOf(male_chest_cloth_size[5]));
        male_chest_l_min.setText(String.valueOf(male_chest_cloth_size[6]));
        male_chest_l_max.setText(String.valueOf(male_chest_cloth_size[7]));
        male_chest_xl_min.setText(String.valueOf(male_chest_cloth_size[8]));
        male_chest_xl_max.setText(String.valueOf(male_chest_cloth_size[9]));
        male_chest_xxl_min.setText(String.valueOf(male_chest_cloth_size[10]));
        male_chest_xxl_max.setText(String.valueOf(male_chest_cloth_size[11]));

        male_waist_xs_min.setText(String.valueOf(male_waist_cloth_size[0]));
        male_waist_xs_max.setText(String.valueOf(male_waist_cloth_size[1]));
        male_waist_s_min.setText(String.valueOf(male_waist_cloth_size[2]));
        male_waist_s_max.setText(String.valueOf(male_waist_cloth_size[3]));
        male_waist_m_min.setText(String.valueOf(male_waist_cloth_size[4]));
        male_waist_m_max.setText(String.valueOf(male_waist_cloth_size[5]));
        male_waist_l_min.setText(String.valueOf(male_waist_cloth_size[6]));
        male_waist_l_max.setText(String.valueOf(male_waist_cloth_size[7]));
        male_waist_xl_min.setText(String.valueOf(male_waist_cloth_size[8]));
        male_waist_xl_max.setText(String.valueOf(male_waist_cloth_size[9]));
        male_waist_xxl_min.setText(String.valueOf(male_waist_cloth_size[10]));
        male_waist_xxl_max.setText(String.valueOf(male_waist_cloth_size[11]));

        female_chest_xs_min.setText(String.valueOf(female_chest_cloth_size[0]));
        female_chest_xs_max.setText(String.valueOf(female_chest_cloth_size[1]));
        female_chest_s_min.setText(String.valueOf(female_chest_cloth_size[2]));
        female_chest_s_max.setText(String.valueOf(female_chest_cloth_size[3]));
        female_chest_m_min.setText(String.valueOf(female_chest_cloth_size[4]));
        female_chest_m_max.setText(String.valueOf(female_chest_cloth_size[5]));
        female_chest_l_min.setText(String.valueOf(female_chest_cloth_size[6]));
        female_chest_l_max.setText(String.valueOf(female_chest_cloth_size[7]));
        female_chest_xl_min.setText(String.valueOf(female_chest_cloth_size[8]));
        female_chest_xl_max.setText(String.valueOf(female_chest_cloth_size[9]));
        female_chest_xxl_min.setText(String.valueOf(female_chest_cloth_size[10]));
        female_chest_xxl_max.setText(String.valueOf(female_chest_cloth_size[11]));

        female_waist_xs_min.setText(String.valueOf(female_waist_cloth_size[0]));
        female_waist_xs_max.setText(String.valueOf(female_waist_cloth_size[1]));
        female_waist_s_min.setText(String.valueOf(female_waist_cloth_size[2]));
        female_waist_s_max.setText(String.valueOf(female_waist_cloth_size[3]));
        female_waist_m_min.setText(String.valueOf(female_waist_cloth_size[4]));
        female_waist_m_max.setText(String.valueOf(female_waist_cloth_size[5]));
        female_waist_l_min.setText(String.valueOf(female_waist_cloth_size[6]));
        female_waist_l_max.setText(String.valueOf(female_waist_cloth_size[7]));
        female_waist_xl_min.setText(String.valueOf(female_waist_cloth_size[8]));
        female_waist_xl_max.setText(String.valueOf(female_waist_cloth_size[9]));
        female_waist_xxl_min.setText(String.valueOf(female_waist_cloth_size[10]));
        female_waist_xxl_max.setText(String.valueOf(female_waist_cloth_size[11]));

        button_save = (Button) findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_chest_cloth_size[0]=Integer.parseInt(male_chest_xs_min.getText().toString());
                male_chest_cloth_size[1]=Integer.parseInt(male_chest_xs_max.getText().toString());
                male_chest_cloth_size[2]=Integer.parseInt(male_chest_s_min.getText().toString());
                male_chest_cloth_size[3]=Integer.parseInt(male_chest_s_max.getText().toString());
                male_chest_cloth_size[4]=Integer.parseInt(male_chest_m_min.getText().toString());
                male_chest_cloth_size[5]=Integer.parseInt(male_chest_m_max.getText().toString());
                male_chest_cloth_size[6]=Integer.parseInt(male_chest_l_min.getText().toString());
                male_chest_cloth_size[7]=Integer.parseInt(male_chest_l_max.getText().toString());
                male_chest_cloth_size[8]=Integer.parseInt(male_chest_xl_min.getText().toString());
                male_chest_cloth_size[9]=Integer.parseInt(male_chest_xl_max.getText().toString());
                male_chest_cloth_size[10]=Integer.parseInt(male_chest_xxl_min.getText().toString());
                male_chest_cloth_size[11]=Integer.parseInt(male_chest_xxl_max.getText().toString());

                male_waist_cloth_size[0]=Integer.parseInt(male_waist_xs_min.getText().toString());
                male_waist_cloth_size[1]=Integer.parseInt(male_waist_xs_max.getText().toString());
                male_waist_cloth_size[2]=Integer.parseInt(male_waist_s_min.getText().toString());
                male_waist_cloth_size[3]=Integer.parseInt(male_waist_s_max.getText().toString());
                male_waist_cloth_size[4]=Integer.parseInt(male_waist_m_min.getText().toString());
                male_waist_cloth_size[5]=Integer.parseInt(male_waist_m_max.getText().toString());
                male_waist_cloth_size[6]=Integer.parseInt(male_waist_l_min.getText().toString());
                male_waist_cloth_size[7]=Integer.parseInt(male_waist_l_max.getText().toString());
                male_waist_cloth_size[8]=Integer.parseInt(male_waist_xl_min.getText().toString());
                male_waist_cloth_size[9]=Integer.parseInt(male_waist_xl_max.getText().toString());
                male_waist_cloth_size[10]=Integer.parseInt(male_waist_xxl_min.getText().toString());
                male_waist_cloth_size[11]=Integer.parseInt(male_waist_xxl_max.getText().toString());

                female_chest_cloth_size[0]=Integer.parseInt(female_chest_xs_min.getText().toString());
                female_chest_cloth_size[1]=Integer.parseInt(female_chest_xs_max.getText().toString());
                female_chest_cloth_size[2]=Integer.parseInt(female_chest_s_min.getText().toString());
                female_chest_cloth_size[3]=Integer.parseInt(female_chest_s_max.getText().toString());
                female_chest_cloth_size[4]=Integer.parseInt(female_chest_m_min.getText().toString());
                female_chest_cloth_size[5]=Integer.parseInt(female_chest_m_max.getText().toString());
                female_chest_cloth_size[6]=Integer.parseInt(female_chest_l_min.getText().toString());
                female_chest_cloth_size[7]=Integer.parseInt(female_chest_l_max.getText().toString());
                female_chest_cloth_size[8]=Integer.parseInt(female_chest_xl_min.getText().toString());
                female_chest_cloth_size[9]=Integer.parseInt(female_chest_xl_max.getText().toString());
                female_chest_cloth_size[10]=Integer.parseInt(female_chest_xxl_min.getText().toString());
                female_chest_cloth_size[11]=Integer.parseInt(female_chest_xxl_max.getText().toString());

                female_waist_cloth_size[0]=Integer.parseInt(female_waist_xs_min.getText().toString());
                female_waist_cloth_size[1]=Integer.parseInt(female_waist_xs_max.getText().toString());
                female_waist_cloth_size[2]=Integer.parseInt(female_waist_s_min.getText().toString());
                female_waist_cloth_size[3]=Integer.parseInt(female_waist_s_max.getText().toString());
                female_waist_cloth_size[4]=Integer.parseInt(female_waist_m_min.getText().toString());
                female_waist_cloth_size[5]=Integer.parseInt(female_waist_m_max.getText().toString());
                female_waist_cloth_size[6]=Integer.parseInt(female_waist_l_min.getText().toString());
                female_waist_cloth_size[7]=Integer.parseInt(female_waist_l_max.getText().toString());
                female_waist_cloth_size[8]=Integer.parseInt(female_waist_xl_min.getText().toString());
                female_waist_cloth_size[9]=Integer.parseInt(female_waist_xl_max.getText().toString());
                female_waist_cloth_size[10]=Integer.parseInt(female_waist_xxl_min.getText().toString());
                female_waist_cloth_size[11]=Integer.parseInt(female_waist_xxl_max.getText().toString());

                storeIntArray("male_chest_cloth_size", male_chest_cloth_size);
                storeIntArray("male_waist_cloth_size", male_waist_cloth_size);
                storeIntArray("female_chest_cloth_size", female_chest_cloth_size);
                storeIntArray("female_waist_cloth_size", female_waist_cloth_size);

                Toast.makeText(getApplicationContext(), "Size details changed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void storeIntArray(String name, int[] array){
        SharedPreferences.Editor edit= prefsSize.edit();
        edit.putInt(LEN_PREFIX + name, array.length);
        int count = 0;
        for (int i: array){
            edit.putInt(VAL_PREFIX + name + count++, i);
        }
        edit.commit();
    }
    public int[] getFromPrefs(String name){
        int[] ret;
        int count = prefsSize.getInt(LEN_PREFIX + name, 0);
        ret = new int[count];
        for (int i = 0; i < count; i++){
            ret[i] = prefsSize.getInt(VAL_PREFIX+ name + i, i);
        }
        return ret;
    }

    protected void showInputDialog(int id) {
        AlertDialog.Builder builder;

        switch(id) {
            case MY_PASSCODE_DIALOG_ID:
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View layout = inflater.inflate(R.layout.enter_passcode, (ViewGroup) findViewById(R.id.root));
                final EditText passcode = layout.findViewById(R.id.passcode);

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter Admin Passcode");
                builder.setView(layout);

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    }
                });

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String inputPasscode = passcode.getText().toString();
                        String strPasscode = prefsCreds.getString("passcode", "");
                        if (inputPasscode.equals(strPasscode)) {
                            removeDialog(MY_PASSCODE_DIALOG_ID);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Passcode!", Toast.LENGTH_SHORT).show();
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