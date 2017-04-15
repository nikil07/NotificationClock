package com.example.nikhil.stickyclock.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nikhil.stickyclock.R;
import com.example.nikhil.stickyclock.adapter.TimeZoneAdapter;
import com.example.nikhil.stickyclock.model.TimeZoneItem;
import com.example.nikhil.stickyclock.utils.DataBaseHelper;
import com.example.nikhil.stickyclock.utils.DataStore;
import com.example.nikhil.stickyclock.utils.RemainderService;
import com.example.nikhil.stickyclock.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Nikhil on 01-Nov-16.
 */
public class FirstFragment extends Fragment {

    static ArrayList<TimeZoneItem> timezones = new ArrayList<>();
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.fragment_countries_auto_et)
    EditText autoCompleteTextView;
    @BindView(R.id.select_countries_layout)
    RelativeLayout countriesRelativeLayout;
    TimeZoneAdapter timeZoneAdapter;
    DataBaseHelper dataBaseHelper;

    public FirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        ButterKnife.bind(this, view);
        dataBaseHelper = new DataBaseHelper(getActivity());

        File database = getActivity().getDatabasePath(DataBaseHelper.DATABASE_NAME);
        if (!database.exists()) {
            dataBaseHelper.getReadableDatabase();
            if (copyDataBase(getActivity()))
                Log.d("nikhil", "DB copied");
                // Toast.makeText(getActivity(), "DB copied", Toast.LENGTH_SHORT).show();
            else
                Log.d("nikhil", "DB error copying");
            //  Toast.makeText(getActivity(), "DB error copying", Toast.LENGTH_SHORT).show();
        }

        timezones = dataBaseHelper.getTimes();
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

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                DataStore.getInstance(getActivity()).storeIfSecondScreenDone(true);
                if (s.length() == 0) {
                    timeZoneAdapter.setNewData(timezones);
                } else if (s.length() > 0) {
                    timeZoneAdapter.setNewData(Utils.updateList(timezones, s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    void doShit(final TimeZoneItem item) {

        if (checkDuplicate(item)) {
            Utils.showSnackBar(countriesRelativeLayout, "Timezone added");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DataStore.getInstance(getActivity()).storeTimeZoneItems(item);
                    getActivity().startService(new Intent(getActivity(), RemainderService.class));
                    getActivity().finish();
                }
            }).start();
        } else {
            Utils.showSnackBar(countriesRelativeLayout, "This timezone is already added");
        }
    }

    private boolean checkDuplicate(TimeZoneItem item) {
        ArrayList<TimeZoneItem> items = DataStore.getInstance(getActivity()).getTimeZones();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCity().equalsIgnoreCase(item.getCity()))
                return false;
        }
        return true;
    }

    private boolean copyDataBase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DataBaseHelper.DATABASE_NAME);
            String outFileName = DataBaseHelper.DATABASE_PATH + DataBaseHelper.DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    class AddTimeZonesTask extends AsyncTask<Void, Void, ArrayList<TimeZoneItem>> {

        @Override
        protected ArrayList<TimeZoneItem> doInBackground(Void... params) {

            return dataBaseHelper.getTimes();
        }

        @Override
        protected void onPostExecute(ArrayList<TimeZoneItem> timeZoneItems) {
            super.onPostExecute(timeZoneItems);
            timeZoneAdapter = new TimeZoneAdapter(getActivity(), timeZoneItems);
            listView.setAdapter(timeZoneAdapter);
        }
    }


}
