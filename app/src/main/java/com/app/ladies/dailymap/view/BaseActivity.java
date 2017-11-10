package com.app.ladies.dailymap.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.ladies.dailymap.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.headermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchMap) {
            Intent intent = new Intent();
            intent.setClassName("com.app.ladies.dailymap", "com.app.ladies.dailymap.view.search.SearchMapActivity");
            startActivity(intent);
            return true;
//        } else if (id == R.id.action_searchConditions) {
//            Intent intent = new Intent();
//            intent.setClassName("com.app.ladies.dailymap", "com.app.ladies.dailymap.view.search.SearchConditionsActivity");
//            startActivity(intent);
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
