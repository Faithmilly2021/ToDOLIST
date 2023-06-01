package com.faith.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faith.todolist.Adapter.GroupAdapter;
import com.faith.todolist.Model.Group;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener, SearchView.OnQueryTextListener {
    public static final String TAG = "AddNewTask";

    private SearchView searchView;
    private TextView notificationCount;
    private int notificationCountValue = 0;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private GroupAdapter groupAdapter;
    private AddNewTask AddNewGroup;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);

        // Configure the search menu item
        MenuItem searchMenuItem = menu.findItem(R.id.nav_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the notification icon click listener
        ImageView notificationIcon = findViewById(R.id.notification_icon);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle notification icon click
                showNotifications();
            }
        });

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#192A56")));
        }

        // Set up the notification count view
        notificationCount = findViewById(R.id.notification_count);
        updateNotificationCount();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerview);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_account:
                        replaceFragment(new AccountFragment());
                        break;
                    case R.id.nav_settings:
                        recyclerView.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.GONE);
                        replaceFragment(new MyPreferenceFragment());
                        break;
                    case R.id.nav_share:
                        shareApp();
                        break;
                    case R.id.nav_logout:
                        finishAffinity();
                        Toast.makeText(MainActivity.this, "Logout is Successfully", Toast.LENGTH_SHORT).show();
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
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Group> groupsList = new ArrayList<>();
        groupsList.add(new Group("Personal", "groupId1"));
        groupsList.add(new Group("Work", "groupId2"));
        groupsList.add(new Group("Goals", "groupId3"));
        groupsList.add(new Group("Miscellaneous", "groupId4"));
        groupsList.add(new Group("Private", "groupId5"));

        groupAdapter = new GroupAdapter(groupsList, this);
        recyclerView.setAdapter(groupAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the AddNewTask dialog
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    private void showNotifications() {
        // Handle the notification icon click
        // You can replace the code below with your implementation
        Toast.makeText(MainActivity.this, "Show notifications", Toast.LENGTH_SHORT).show();
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ToDoList App");
            String shareMessage = "\nLet me recommend you this ToDoList app\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        groupAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Perform search operation based on the query
        performSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Perform search operation based on the new text
        performSearch(newText);
        return true;
    }

    private void performSearch(String query) {
        // Handle the search operation
        // Update the UI or perform any other actions based on the search query
        // You can replace the code below with your implementation
        Toast.makeText(MainActivity.this, "Search query: " + query, Toast.LENGTH_SHORT).show();
    }

    private void updateNotificationCount() {
        // Update the notification count view
        if (notificationCountValue > 0) {
            notificationCount.setVisibility(View.VISIBLE);
            notificationCount.setText(String.valueOf(notificationCountValue));
        } else {
            notificationCount.setVisibility(View.GONE);
        }
    }

    // Example method to increment the notification count
    private void incrementNotificationCount() {
        notificationCountValue++;
        updateNotificationCount();
    }
}
