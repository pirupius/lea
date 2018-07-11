package com.agritech.lea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.agritech.lea.fragments.ForumFragment;
import com.agritech.lea.fragments.HomeFragment;
import com.agritech.lea.fragments.ProfileFragment;
import com.agritech.lea.fragments.SuppliersFragment;
import com.agritech.lea.fragments.TrackerFragment;
import com.agritech.lea.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    FrameLayout content;
    SessionManager session;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        if(!session.isLoggedIn()){
            finish();
        }

        content = findViewById(R.id.content);
        navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_tracker:
                    fragment = new TrackerFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_suppliers:
                    fragment = new SuppliersFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    fragment = new ForumFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    /**
     * load fragment
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
        );
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
