package com.creativity.yourself;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;



import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
//import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.creativity.yourself.fragment.HomeFragment;
import com.creativity.yourself.fragment.AccountFragment;
import com.creativity.yourself.fragment.CommunityFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView mMainNav;
    //private FrameLayout mMainFrame;

    private ViewPager viewPager;

    private Toolbar my_toolbar;

    private HomeFragment homeFragment;
    private AccountFragment accountFragment;
    private CommunityFragment communityFragment;
    MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpager);
        PagerAdapter adapter = viewPager.getAdapter();
        viewPager.setAdapter(adapter);


        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        //homeFragment= new HomeFragment();
       // accountFragment = new AccountFragment();
        //communityFragment = new CommunityFragment();

        //viewPager.setCurrentItem(0);
        viewPager.setCurrentItem(2);

        //setFragment(homeFragment);

        //Initializing the bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        //bottomNavigationView.setSelectedItemId(R.id.nav_home);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home); // change to whichever id should be default
        }
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_community:
                        viewPager.setCurrentItem(0);
                        return true;

                    case R.id.nav_home:
                        // to set up the color of home nav mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        viewPager.setCurrentItem(1);
                        //setFragment(homeFragment);
                        return true;


                    case R.id.nav_profilo:
                        viewPager.setCurrentItem(2);
                        return true;

                    default:
                        return false;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    mMainNav.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                mMainNav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mMainNav.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });




        setupViewPager(viewPager);
    }

   /* private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewpager, fragment);
        fragmentTransaction.commit();
    }
    */

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment=new HomeFragment();
        communityFragment=new CommunityFragment();
        accountFragment=new AccountFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(communityFragment);
        adapter.addFragment(accountFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

}