package com.example.kevin.measureme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryData> {

    private Context mContext;
    private List<HistoryData> historyList = new ArrayList<>();

    public HistoryAdapter(@NonNull Context context, ArrayList<HistoryData> list) {
        super(context, 0 , list);
        mContext = context;
        historyList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.history_adapter,parent,false);

        HistoryData currentHistory = historyList.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.history_id);
        id.setText(String.valueOf(currentHistory.getId()));

        TextView name = (TextView) listItem.findViewById(R.id.history_name);
        name.setText(currentHistory.getName());

        TextView datetime = (TextView) listItem.findViewById(R.id.history_datetime);
        datetime.setText(currentHistory.getDatetime());

        return listItem;
    }
}