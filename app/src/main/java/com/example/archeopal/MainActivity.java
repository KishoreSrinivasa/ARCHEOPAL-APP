package com.example.archeopal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
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
    //NavigationView navigationViewMain;
    //ActionBarDrawerToggle actionBarMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayoutMain = findViewById(R.id.main);
        //navigationViewMain = findViewById(R.id.mainMenu);
        toolBarButtonMain = findViewById(R.id.toolBarButton);

        toolBarButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMain.open();
            }
        });
    }
}