package com.example.nikhil.stickyclock.ui;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.nikhil.stickyclock.R;
import com.example.nikhil.stickyclock.adapter.WelcomeTimeZoneAdapter;
import com.example.nikhil.stickyclock.model.TimeZoneItem;
import com.example.nikhil.stickyclock.utils.DataStore;
import com.example.nikhil.stickyclock.utils.RemainderReceiver;
import com.example.nikhil.stickyclock.utils.RemainderService;
import com.example.nikhil.stickyclock.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    ArrayList<TimeZoneItem> items;
    ArrayList<TimeZoneItem> selectedItems = new ArrayList<>();
    ArrayList<TimeZoneItem> tempList = new ArrayList<>();
    @BindView(R.id.welcome_listView)
    ListView welcomeListView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.welcome_layout)
    RelativeLayout welcomeLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    WelcomeTimeZoneAdapter timeZoneAdapter;
    Context context;
    Menu globalMenu;
    int position = 0;
    RelativeLayout itemLayout;
    MenuItem deleteAllItem;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        context = this;
        createFirstNotification();
        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        globalMenu = menu;
        MenuItem item = menu.findItem(R.id.show_icon);
        deleteAllItem = menu.findItem(R.id.delete_all);

        if (items.size() == 0) {
            deleteAllItem.setEnabled(false);
        } else {
            deleteAllItem.setVisible(true);
            deleteAllItem.setEnabled(true);
        }
        if (DataStore.getInstance(getApplicationContext()).isIconNeeded()) {
            item.setChecked(true);
        } else {
            item.setChecked(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_icon:
                storePreference(item);
                return true;
            case R.id.delete_all:
                deleteAll();
                return true;
            case R.id.edit:
                update();
                return true;
            case R.id.delete:
                delete();
                hideOptions();
                return true;
            case R.id.reset:
                reset();
                hideOptions();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideOptions() {
        MenuItem item = globalMenu.findItem(R.id.edit);
        item.setVisible(false);
        item = globalMenu.findItem(R.id.delete);
        item.setVisible(false);
        item = globalMenu.findItem(R.id.reset);
        item.setVisible(false);
    }

    private void showOption() {
        MenuItem item = globalMenu.findItem(R.id.edit);
        item.setVisible(true);
        item = globalMenu.findItem(R.id.delete);
        item.setVisible(true);
    }

    private void hideUpdateOption() {
        MenuItem item = globalMenu.findItem(R.id.edit);
        item.setVisible(false);
    }

    private void showUpdateOption() {
        MenuItem item = globalMenu.findItem(R.id.edit);
        item.setVisible(true);
    }

    private void showReset() {
        MenuItem item = globalMenu.findItem(R.id.reset);
        item.setVisible(true);
    }

    private void hideReset() {
        MenuItem item = globalMenu.findItem(R.id.reset);
        item.setVisible(false);
    }

    private void showResetAndUpdate() {
        MenuItem item = globalMenu.findItem(R.id.delete);
        item.setVisible(true);
        item = globalMenu.findItem(R.id.edit);
        item.setVisible(true);
        item = globalMenu.findItem(R.id.reset);
        item.setVisible(true);
    }

    private void createFirstNotification() {
        int requestCode = 0;
        Intent intent = new Intent(this, RemainderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 2000, 5 * 60 * 1000, pendingIntent);
    }

    public void setupViews() {

        items = DataStore.getInstance(WelcomeActivity.this).getTimeZones();
        if (items.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            welcomeListView.setVisibility(View.GONE);
            if (deleteAllItem != null)
                deleteAllItem.setEnabled(false);
            emptyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WelcomeActivity.this, TabActivity.class));
                }
            });
        } else {
            if (deleteAllItem != null) {
                deleteAllItem.setVisible(true);
                deleteAllItem.setEnabled(true);
            }
            emptyLayout.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            welcomeListView.setVisibility(View.VISIBLE);
            timeZoneAdapter = new WelcomeTimeZoneAdapter(this, items);
            welcomeListView.setAdapter(timeZoneAdapter);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideOptions();
                    selectedItems.clear();
                    startActivity(new Intent(WelcomeActivity.this, TabActivity.class));
                }
            });

            welcomeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    WelcomeActivity.this.position = position;
                    showOption();
                    itemLayout = (RelativeLayout) view.findViewById(R.id.list_item_layout);
                    itemLayout.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this, android.R.color.white));
                    if (!items.get(position).isSelected()) {
                        itemLayout.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this, android.R.color.darker_gray));
                        items.get(position).setSelected(true);
                        selectedItems.add(items.get(position));
                    } else {
                        itemLayout.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this, android.R.color.white));
                        items.get(position).setSelected(false);
                        selectedItems.remove(items.get(position));
                    }
                    handleOptions();
                    return true;
                }
            });

            welcomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //  Utils.showSnackBar(welcomeListView, "Long click to remove this timezone");
                    hideReset();
                    if (selectedItems.size() > 0) {
                        if (!items.get(position).isSelected()) {
                            selectedItems.add(items.get(position));
                            items.get(position).setSelected(true);
                            itemLayout = (RelativeLayout) view.findViewById(R.id.list_item_layout);
                            itemLayout.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this, android.R.color.darker_gray));
                        } else {
                            selectedItems.remove(items.get(position));
                            items.get(position).setSelected(false);
                            itemLayout = (RelativeLayout) view.findViewById(R.id.list_item_layout);
                            itemLayout.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this, android.R.color.white));
                        }
                        handleOptions();
                    } else {
                        selectedItems.clear();
                        hideOptions();
                        Utils.showSnackBar(welcomeLayout, "Long click for options");
                    }
                }
            });
        }
    }

    private void handleOptions() {
        if (selectedItems.size() > 1)
            hideUpdateOption();
        else
            showUpdateOption();
        if (selectedItems.size() == 1) {
            if (selectedItems.get(0).getMessage() != null)
                showResetAndUpdate();
            else
                showUpdateOption();
        }
        if (selectedItems.size() == 0)
            hideOptions();
    }

    private void delete() {
        for (int i = 0; i < selectedItems.size(); i++) {
            DataStore.getInstance(context).deleteTimeZone(selectedItems.get(i));
            deleteItems(selectedItems.get(i));
        }
        timeZoneAdapter.setNewData(items);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        context.startService(new Intent(context, RemainderService.class));
        Utils.showSnackBar(welcomeListView, "Timezone Removed");
        welcomeListView.clearChoices();
        selectedItems.clear();
        hideOptions();
        if (items.size() == 0)
            setupViews();
    }

    private void deleteAll() {
        tempList.clear();
        tempList.addAll(items);
        DataStore.getInstance(context).deleteAll();
        items.clear();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        context.startService(new Intent(context, RemainderService.class));
        selectedItems.clear();
        hideOptions();
        Snackbar snackbar = Snackbar
                .make(welcomeLayout, "Deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        restore();
                        Snackbar snackbar1 = Snackbar.make(welcomeLayout, "Restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

        snackbar.show();
        setupViews();
    }

    private void restore() {
        items.addAll(tempList);
        tempList.clear();
        DataStore.getInstance(context).restore();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        context.startService(new Intent(context, RemainderService.class));
        setupViews();
    }

    private void deleteItems(TimeZoneItem item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCity().equalsIgnoreCase(item.getCity()))
                items.remove(i);
        }
    }

    private void reset() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Reset message");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                TimeZoneItem newItem = selectedItems.get(0);
                newItem.setMessage(null);
                newItem.setSelected(false);
                DataStore.getInstance(context).updateUserMessage(newItem);
                updateUserMessageItem(newItem);
                hideOptions();
                selectedItems.clear();
                timeZoneAdapter.setNewData(items);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                context.startService(new Intent(context, RemainderService.class));

            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                TimeZoneItem newItem = selectedItems.get(0);
                newItem.setSelected(false);
                DataStore.getInstance(context).updateUserMessage(newItem);
                updateUserMessageItem(newItem);
                hideOptions();
                selectedItems.clear();
                timeZoneAdapter.setNewData(items);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void updateUserMessageItem(TimeZoneItem newItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCity().equalsIgnoreCase(newItem.getCity()))
                items.get(i).setMessage(newItem.getMessage());
        }
    }

    private void update() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = View.inflate(this, R.layout.update_message_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText messageET = (EditText) dialogView.findViewById(R.id.message);

        dialogBuilder.setTitle("Edit Message");
        dialogBuilder.setMessage("Enter a custom message to be shown in the notification");
        dialogBuilder.setPositiveButton("Save", null);
        dialogBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!messageET.getText().toString().isEmpty()) {
                            TimeZoneItem newItem = selectedItems.get(0);
                            newItem.setSelected(false);
                            newItem.setMessage(messageET.getText().toString());
                            DataStore.getInstance(context).updateUserMessage(newItem);
                            updateUserMessageItem(newItem);
                            hideOptions();
                            selectedItems.clear();
                            timeZoneAdapter.setNewData(items);
                            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            context.startService(new Intent(context, RemainderService.class));
                            alertDialog.dismiss();
                        } else {
                            //Utils.showSnackBar(welcomeLayout, "Please enter a message");
                            messageET.setError("Please enter a message");

                        }
                    }
                });
            }
        });
        alertDialog.show();
    }


    private void storePreference(MenuItem item) {
        if (DataStore.getInstance(getApplicationContext()).isIconNeeded()) {
            DataStore.getInstance(this).storeIfIconNeeded(false);
            item.setChecked(false);
        } else {
            DataStore.getInstance(this).storeIfIconNeeded(true);
            item.setChecked(true);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(context, RemainderService.class));
            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViews();
    }
}
