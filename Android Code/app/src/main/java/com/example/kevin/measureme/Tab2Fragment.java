package com.example.kevin.measureme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/26/2018.
 */

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    private ListView obj;
    DBHelper mydb;
    HistoryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);

        mydb = new DBHelper(getActivity());

        obj = (ListView) view.findViewById(R.id.listView1);
        ArrayList<HistoryData> array_list = mydb.getAllHistory();

        mAdapter = new HistoryAdapter(getContext(),array_list);
        obj.setAdapter(mAdapter);

        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getActivity(),DisplayHistory.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        return view;
    }
}
