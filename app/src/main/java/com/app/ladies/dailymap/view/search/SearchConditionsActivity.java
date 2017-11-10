package com.app.ladies.dailymap.view.search;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.BaseActivity;

public class SearchConditionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_conditions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
