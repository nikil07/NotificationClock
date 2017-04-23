package com.androidworks.nikhil.stickyclock.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.androidworks.nikhil.stickyclock.R;
import com.androidworks.nikhil.stickyclock.model.TimeZoneItem;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikhil on 10-Oct-16.
 */
public class TimeZoneAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<TimeZoneItem> timeZoneItems;

    public TimeZoneAdapter(Context context, ArrayList<TimeZoneItem> timeZoneItems) {
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.time_zone_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TimeZoneItem item = timeZoneItems.get(position);
        if (StringUtils.isNotEmpty(item.getCity()) && (StringUtils.isNotEmpty(item.getCountry()) && (StringUtils.isNotEmpty(item.getTimezone())))) {
            viewHolder.city.setText(item.getCity());
            viewHolder.country.setText(item.getCountry());
            viewHolder.timezone.setText(item.getTimezone());
        }
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

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
