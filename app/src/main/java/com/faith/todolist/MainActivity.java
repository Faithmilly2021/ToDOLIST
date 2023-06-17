package com.faith.todolist;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.os.Bundle;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faith.todolist.Adapter.GroupAdapter;
import com.faith.todolist.AddTaskActivity;
import com.faith.todolist.Model.Group;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GroupAdapter.OnItemClickListener, DialogInterface.OnDismissListener, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String CHANNEL_ID = "ToDoListChannel";
    private static final int NOTIFICATION_ID = 1;

    private List<Group> groupsList;
    private GroupAdapter groupAdapter;
    private TextView notificationCount;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up ActionBar
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#192A56")));

        // Find views
        notificationCount = findViewById(R.id.notification_count);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerview);

        // Set up ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        // Set up RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create and set up the adapter with sample data
        groupsList = new ArrayList<>();
        groupsList.add(new Group("Click on a group to add tasks", "", Arrays.asList("Task1", "Task2"), Arrays.asList(R.color.groupColor1)));
        groupsList.add(new Group("Personal", "groupId1", Arrays.asList("Task1", "Task2"), Arrays.asList(R.color.groupColor1)));
        groupsList.add(new Group("Work", "groupId2", Arrays.asList("Task3", "Task4"), Arrays.asList(R.color.groupColor2)));
        groupsList.add(new Group("Goals", "groupId3", Arrays.asList("Task5", "Task6"), Arrays.asList(R.color.groupColor3)));
        groupsList.add(new Group("Miscellaneous", "groupId4", Arrays.asList("Task7", "Task8"), Arrays.asList(R.color.groupColor4)));
        groupsList.add(new Group("Private", "groupId5", Arrays.asList("Task9", "Task10"), Arrays.asList(R.color.groupColor5)));
        groupAdapter = new GroupAdapter(groupsList, this, this); // Pass "this" as the listener
        recyclerView.setAdapter(groupAdapter);

        createNotificationChannel();
        showReminderNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.nav_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.nav_notifications) {
            showNotifications("Notification Title", "Notification Message");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotifications(String title, String message) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.notification_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView titleTextView = dialog.findViewById(R.id.notification_title);
        TextView messageTextView = dialog.findViewById(R.id.notification_message);
        titleTextView.setText(title);
        messageTextView.setText(message);

        ImageView closeButton = dialog.findViewById(R.id.notification_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showReminderNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notifications_24)
                .setContentTitle("Task Reminder")
                .setContentText("Your task is due soon, don't forget!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        groupAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        performSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        performSearch(newText);
        return true;
    }

    private void performSearch(String query) {
        Toast.makeText(MainActivity.this, "Search query: " + query, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_account:
                replaceFragment(new AccountFragment());
                break;
            case R.id.nav_settings:
                recyclerView.setVisibility(View.GONE);
                replaceFragment(new MyPreferenceFragment());
                break;
            case R.id.nav_share:
                shareApp();
                break;
            case R.id.nav_logout:
                finishAffinity();
                Toast.makeText(MainActivity.this, "You have logged out. Goodbye!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Download the ToDoList app and manage your tasks efficiently!";
        String shareSubject = "ToDoList App";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    private void updateNotificationCount() {
        int notificationCountValue = 0;

        if (notificationCountValue > 0) {
            notificationCount.setVisibility(View.VISIBLE);
            notificationCount.setText(String.valueOf(notificationCountValue));
        } else {
            notificationCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
        Group group = groupsList.get(position);
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra("groupName", group.getName());
        startActivity(intent);
    }
}
