package com.example.sushma.shodatproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sushma on 7/8/16.
 */
public class imagefragment extends Fragment {


    private imageAdapter adapter = null;
    ArrayList<detailObject> list = new ArrayList<detailObject>();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("test", "frag called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater, container,savedInstanceState );
        Log.i("test", "frag create view called");
        Bundle data = getArguments();
        list = data.getParcelableArrayList("list");
        View rootView = inflater.inflate(R.layout.image_fragment_layout, null);
        Log.i("test", "frag inflated");
        adapter = new imageAdapter(getActivity().getApplicationContext(), list);
        ListView lView = (ListView) rootView.findViewById(R.id.listView);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), FullViewActivity.class);
                intent.putExtra("list", list);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        lView.setAdapter(adapter);
        return rootView;
    }
}
