package com.example.nikhil.stickyclock.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nikhil.stickyclock.R;
import com.example.nikhil.stickyclock.model.TimeZoneItem;
import com.example.nikhil.stickyclock.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikhil on 10-Oct-16.
 */
public class WelcomeTimeZoneAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<TimeZoneItem> timeZoneItems;

    public WelcomeTimeZoneAdapter(Context context, ArrayList<TimeZoneItem> timeZoneItems) {
        this.context = context;
        this.timeZoneItems = timeZoneItems;
    }

    @Override
    public int getCount() {
        return timeZoneItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ArrayList<TimeZoneItem> getData() {
        return this.timeZoneItems;
    }

    public void setNewData(ArrayList<TimeZoneItem> newList) {
        this.timeZoneItems = newList;
        notifyDataSetChanged();

    }

    public String getTime(String id) {

        Calendar c = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(id);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sdf.setTimeZone(tz);
        return sdf.format(c.getTime());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.time_zone_item_copy, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TimeZoneItem item = timeZoneItems.get(position);
        viewHolder.city.setText(item.getCity());
        viewHolder.country.setText(item.getCountry());
        viewHolder.timezone.setText(getTime(item.getTimezoneID()));
        viewHolder.flag.setBackgroundResource(Utils.getCountryFlag(item.getCountryID()));
        if (!item.isSelected())
            viewHolder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        else
            viewHolder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    static class ViewHolder {

        @BindView(R.id.tv_city)
        TextView city;
        @BindView(R.id.tv_country)
        TextView country;
        @BindView(R.id.tv_time_zone)
        TextView timezone;
        @BindView(R.id.flag)
        ImageView flag;
        @BindView(R.id.list_item_layout)
        RelativeLayout itemLayout;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
