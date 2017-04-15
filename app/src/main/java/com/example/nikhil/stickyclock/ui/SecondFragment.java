package com.example.nikhil.stickyclock.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.nikhil.stickyclock.R;
import com.example.nikhil.stickyclock.adapter.TimeZoneAdapter;
import com.example.nikhil.stickyclock.model.TimeZoneItem;
import com.example.nikhil.stickyclock.utils.DataStore;
import com.example.nikhil.stickyclock.utils.RemainderService;
import com.example.nikhil.stickyclock.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikhil on 01-Nov-16.
 */
public class SecondFragment extends Fragment {

    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.select_countries_layout)
    RelativeLayout countriesRelativeLayout;
    TimeZoneAdapter timeZoneAdapter;

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        ButterKnife.bind(this, view);
        AddTimeZonesTask task = new AddTimeZonesTask();
        task.execute();
        setupViews();

        return view;
    }

    public void setupViews() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doShit(timeZoneAdapter.getData().get(position));
            }
        });
    }

    void doShit(final TimeZoneItem item) {
        Utils.showSnackBar(countriesRelativeLayout, "Timezone added");
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataStore.getInstance(getActivity()).storeTimeZoneItems(item);
                getActivity().startService(new Intent(getActivity(), RemainderService.class));
                getActivity().finish();
                //startActivity(new Intent(getActivity(), WelcomeActivity.class));
            }
        }).start();
    }

    class AddTimeZonesTask extends AsyncTask<Void, Void, ArrayList<TimeZoneItem>> {

        @Override
        protected ArrayList<TimeZoneItem> doInBackground(Void... params) {
            return Utils.addOtherTimeZones();
        }

        @Override
        protected void onPostExecute(ArrayList<TimeZoneItem> timeZoneItems) {
            super.onPostExecute(timeZoneItems);
            timeZoneAdapter = new TimeZoneAdapter(getActivity(), timeZoneItems);
            listView.setAdapter(timeZoneAdapter);
        }
    }

}
