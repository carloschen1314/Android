package com.androidproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidproject.R;
import com.androidproject.util.DetectionService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView bottomNavigationView;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Intent intent=getIntent();
        TextView txt_userName=navView.getHeaderView(0).findViewById(R.id.userName);
        id=intent.getStringExtra("id");
        txt_userName.setText(id);

        //侧边栏点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_personalInfro:
                        Intent intent1 = new Intent(MainActivity.this, PersonalInformationActivity.class);
                        startActivity(intent1);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_lockApplication:
                        Intent intent2 = new Intent(MainActivity.this, LockedAPPActivity.class);
                        intent2.putExtra("id",id);
                        startActivity(intent2);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_myGroup:
                        Intent intent3 = new Intent(MainActivity.this, GroupChangingActivity.class);
                        startActivity(intent3);
                        mDrawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });

        initFragment();//底边栏初始化
    }

    //底边栏
    private void initFragment() {

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragments = new Fragment[]{fragment1, fragment2, fragment3};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, fragment1).show(fragment1).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lock: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                }
                case R.id.navigation_talk: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                }
                case R.id.navigation_list: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
                }
            }
            return false;
        }
    };

    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }

}
