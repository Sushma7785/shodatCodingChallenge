package com.example.sushma.shodatproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sushma on 7/8/16.
 */
public class imageAdapter extends BaseAdapter{
    Context context;
    ArrayList<detailObject> list;

    public imageAdapter(Context context, ArrayList<detailObject> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("test", "adapter called");
        final ViewHolder holder;
        final int itemPosition = position;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.list_view_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(list.get(itemPosition).title);
        String imageURL = list.get(itemPosition).thumbnailURL;
        new DownloadImageTask(holder.viewImage).execute(imageURL);

        return view;
    }

    private static class ViewHolder {
        public View view;
        public ImageView viewImage;
        public TextView textView;

        public ViewHolder(View view) {
            this.view = view;
            viewImage = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.titleText);
        }
    }
}
