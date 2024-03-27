package com.example.archeopal;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.archeopal.R;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutMain;
    ImageButton toolBarButtonMain;
    NavigationView navigationViewMain;
    //ActionBarDrawerToggle actionBarMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayoutMain = findViewById(R.id.main);
        navigationViewMain = findViewById(R.id.mainMenu);
        toolBarButtonMain = findViewById(R.id.toolBarButton);

        toolBarButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMain.open();
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

                //switch (id) {
                    //case R.id.profileMenu:
                        //startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        //break;
                    //case R.id.addPostMenu:
                        //startActivity(new Intent(MainActivity.this, AddPostActivity.class));
                        //break;
                    //case R.id.myPostMenu:
                        //startActivity(new Intent(MainActivity.this, MyPostActivity.class));
                        //break;

                //}

                drawerLayoutMain.closeDrawers();
                return true;
            }
        });
    }
}