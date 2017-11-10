package com.app.ladies.dailymap.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.BaseActivity;
import com.app.ladies.dailymap.view.model.DiaryBean;

public class SearchShopDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        DiaryBean bean = (DiaryBean) intent.getSerializableExtra("diary");

        ((TextView)findViewById(R.id.diary_title)).setText(bean.getTitle());
        ((TextView)findViewById(R.id.diary_date)).setText(bean.getRegDate().toString());
        ((TextView)findViewById(R.id.diary_content)).setText(bean.getComment());
        if (bean.getEvaluation() != null) {
            ((RatingBar)findViewById(R.id.ratingBar)).setNumStars(bean.getEvaluation());
        }
    }
}
