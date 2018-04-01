package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    TabLayout tab;

    private FirebaseAuth mAuth;
    public static final String table1 = "Post";
    public static final String table2 = "Comment";
    public static final String table3 = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tab = (TabLayout)findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("TERBARU"));
        tab.addTab(tab.newTab().setText("POTO SAYA"));
        tab.setTabGravity(tab.GRAVITY_FILL);

        mAuth = FirebaseAuth.getInstance();

        final ViewPager view = (ViewPager)findViewById(R.id.mainPager);
        final Adapter adapter = new Adapter(getSupportFragmentManager(), tab.getTabCount());
        view.setAdapter(adapter);
        view.setAdapter(adapter);

        view.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //ketika activity menu dipilih, maka akan dijalankan
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //menggetid dari activity yang dipilih
        int id = item.getItemId();
        //ketika menu setting dipilih
        if (id == R.id.action_logout){
            mAuth.signOut();
            //mengarahkan ke activity pengaturan
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            //menjalankan ke activity pengaturan
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void add(View view){
        Intent intent = new Intent(HomeActivity.this, AddActivity.class);
        startActivity(intent);
    }
}

