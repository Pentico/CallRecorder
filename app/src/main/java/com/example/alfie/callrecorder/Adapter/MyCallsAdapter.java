package com.example.alfie.callrecorder.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.alfie.callrecorder.Model;
import com.example.alfie.callrecorder.R;

import java.util.List;

/**
 * Created by Alfie on 2016/04/17.
 */
public class MyCallsAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private List<Model> list;

    public MyCallsAdapter(Context context, List<Model> list){

        super(context, R.layout.rowlayout,list);
        this.list = list;
        this.context = context;

    }

    @Override

}
