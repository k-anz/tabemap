package com.app.ladies.dailymap.view.top;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.BaseActivity;

public class TopActivity extends BaseActivity
        implements TopListFragment.OnFragmentInteractionListener, TopMapFragment.OnFragmentInteractionListener {

    private TopListFragment _topListFragment;
    private TopMapFragment _topMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnTopList = (Button) findViewById(R.id.btn_topList);
        btnTopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopList();
            }
        });

        Button btnTopMap = (Button) findViewById(R.id.btn_topMap);
        btnTopMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopMap();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    void showTopList() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        _topListFragment = (TopListFragment) fragmentManager.findFragmentById(R.id.top_list_fragment);
        _topMapFragment = (TopMapFragment) fragmentManager.findFragmentById(R.id.top_map_fragment);
        if (_topMapFragment.isVisible()) {
            fragmentTransaction.hide(_topMapFragment);
        }
        if (!_topListFragment.isVisible()) {
            fragmentTransaction.show(_topListFragment);
        }
        fragmentTransaction.commit();
    }

    void showTopMap() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        _topListFragment = (TopListFragment) fragmentManager.findFragmentById(R.id.top_list_fragment);
        _topMapFragment = (TopMapFragment) fragmentManager.findFragmentById(R.id.top_map_fragment);
        if (_topListFragment.isVisible()) {
            fragmentTransaction.hide(_topListFragment);
        }
        if (!_topMapFragment.isVisible()) {
            fragmentTransaction.show(_topMapFragment);
        }
        fragmentTransaction.commit();
    }
}
