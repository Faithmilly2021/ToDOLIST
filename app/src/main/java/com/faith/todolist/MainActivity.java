package com.faith.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faith.todolist.Adapter.ToDoAdapter;
import com.faith.todolist.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBar actionBar;
    ActionBarDrawerToggle actionBarDrawerToggle;


    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private ToDoAdapter adapter;
    private List<ToDoModel> mList;
    private Query query;
    private ListenerRegistration listenerRegistration;


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
        replaceFragment(new HomeFragment());

        actionBar = getSupportActionBar();
        try {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#192A56")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
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
                    case R.id.nav_home:
                     replaceFragment(new HomeFragment()); break;

                    case R.id.nav_search:
                        Toast.makeText(MainActivity.this, "Search item is clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_account:
                        replaceFragment(new AccountFragment()); break;


                    case R.id.nav_settings:
                        replaceFragment(new SettingsFragment()); break;

                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "Share item is clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "Logout item is clicked",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

            private void replaceFragment(Fragment fragment) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout,fragment);
                fragmentTransaction.commit();
            }

        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG );
            }
        });

        mList = new ArrayList<>();
        adapter = new ToDoAdapter(MainActivity.this , mList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        try {
            showData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);

    }

    private void replaceFragment(HomeFragment homeFragment) {
    }

    private void showData() {

            firestore = FirebaseFirestore.getInstance();
            query = firestore.collection("task").orderBy("time" , Query.Direction.DESCENDING);


        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
              for (DocumentChange documentChange : value.getDocumentChanges())  {
                  if (documentChange.getType() == DocumentChange.Type.ADDED) {
                      String id = documentChange.getDocument().getId();
                      ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);

                      mList.add(toDoModel);
                      adapter.notifyDataSetChanged();
                  }
              }
             listenerRegistration.remove();
            }
        });

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList.clear();
        showData();
        adapter.notifyDataSetChanged();
    }
}