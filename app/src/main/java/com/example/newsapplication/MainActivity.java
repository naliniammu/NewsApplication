package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.newsapplication.adapters.TabAdapter;
import com.example.newsapplication.fragments.BusinessFragment;
import com.example.newsapplication.fragments.GeneralFragment;
import com.example.newsapplication.fragments.HealthFragment;
import com.example.newsapplication.fragments.LatestNewsFragment;
import com.example.newsapplication.fragments.SportsFragment;
import com.example.newsapplication.fragments.TechnologyFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        initiateFragments();
    }

    private void initiateFragments() {
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new LatestNewsFragment(), "Latest");
        adapter.addFragment(new BusinessFragment(), "Business");
        adapter.addFragment(new GeneralFragment(), "General");
        adapter.addFragment(new HealthFragment(), "Health");
        adapter.addFragment(new SportsFragment(), "Sports");
        adapter.addFragment(new TechnologyFragment(), "Technology");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
