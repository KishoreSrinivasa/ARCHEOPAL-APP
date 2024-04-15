package com.example.archeopal;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.archeopal.R;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

        RecyclerView recyclerView;
        myAdapter adapter;
        ArrayList<PostDetails> list;
        DrawerLayout drawerLayoutMain;
        ImageButton toolBarButtonMain;
        NavigationView navigationViewMain;
        RelativeLayout relativeLayout;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post Data");

        drawerLayoutMain = findViewById(R.id.main);
        navigationViewMain = findViewById(R.id.mainMenu);
        toolBarButtonMain = findViewById(R.id.toolBarButton);
        recyclerView = findViewById(R.id.mainRecyclerview);
        relativeLayout = findViewById(R.id.headerLayout);

        relativeLayout.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        list = new ArrayList<>();
        //list.add(new PostDetails("kishore", "kishre the king", "hello worold", "hello","tamil", "namakkal"));
        adapter = new myAdapter(MainActivity.this, list);
        recyclerView.setAdapter(adapter);

        toolBarButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayoutMain.isDrawerOpen(navigationViewMain)) {
                    drawerLayoutMain.closeDrawer(navigationViewMain);
                } else {
                    drawerLayoutMain.openDrawer(navigationViewMain);
                }
            }
        });

        navigationViewMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.profileMenu) {

                } else if(id == R.id.addPostMenu) {
                    startActivity(new Intent(MainActivity.this, addPostActivity.class));
                } else if(id == R.id.myPostMenu) {

                } else if(id == R.id.loginMenu) {
                    startActivity(new Intent(MainActivity.this, loginActivity.class));
                } else if(id == R.id.sign_inMenu) {
                    startActivity(new Intent(MainActivity.this, Register.class));
                }

                drawerLayoutMain.closeDrawers();
                return true;
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot postSnap : dataSnapshot.getChildren()) {
                        PostDetails postDetails = postSnap.getValue(PostDetails.class);
                        list.add(postDetails);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}