package com.example.kevin.measureme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Kevin on 5/26/2018.
 */

public class Tab1Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "Tab1Fragment";

    private Button button;
    Spinner spinner;
    EditText name;

    SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        prefs = this.getActivity().getSharedPreferences("user", 0);
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        button = (Button) view.findViewById(R.id.measureButton);

        String[] gender = {"Male", "Female"};
        spinner = (Spinner) view.findViewById(R.id.spinnerGender);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        name= (EditText) view.findViewById(R.id.editTextName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int genderCode;

                if(spinner.getSelectedItem().toString()=="Male") {
                    genderCode=1; // Male
                } else {
                    genderCode=2; // Female
                }

                SharedPreferences.Editor editor = prefs.edit();
                if(name.getText().toString().matches("")) {
                    editor.putString("name", "Unknown");
                    Toast.makeText(getContext(), "Unknown.", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("name", name.getText().toString());
                }
                editor.putInt("gender", genderCode); // 1: Male, 2: Female
                editor.commit();

                Intent intent = new Intent(getActivity(),Measure.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
