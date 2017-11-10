package com.app.ladies.dailymap.view.detail;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.BaseActivity;
import com.app.ladies.dailymap.view.model.DiaryBean;
import com.app.ladies.dailymap.view.model.RestaurantBean;
import com.app.ladies.dailymap.view.search.SearchRestaurantDetailTask;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import db.MySQLiteOpenHelper;

public class ShopDetailActivity extends BaseActivity implements Button.OnClickListener, AdapterView.OnItemClickListener {
    /**
     * DB
     */
    private static SQLiteDatabase mydb;
    /**
     * レストラン情報
     */
    public RestaurantBean bean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Button button = (Button) findViewById(R.id.registerButton);
        button.setOnClickListener(ShopDetailActivity.this);

        new SearchRestaurantDetailTask(new WeakReference<ShopDetailActivity>(this)).execute(new Pair<String, String>("id", id));

        List<DiaryBean> diaryBeanList = getDiariesFromDb(id);
        ListView diaryList = (ListView)findViewById(R.id.diaryList);
        DiaryListAdapter adapter = new DiaryListAdapter(ShopDetailActivity.this);
        adapter.setDiaryList(diaryBeanList);
        diaryList.setAdapter(adapter);
        diaryList.setOnItemClickListener(this);
    }

    private List<DiaryBean> getDiariesFromDb(String storeId) {
        List<DiaryBean> diaries = new ArrayList<>();
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
        mydb = helper.getReadableDatabase();

        String[] cols = new String[]{"title", "comment", "seq_no", "reg_date", "evaluation"};
        String selection = "store_id = ?";
        String[] selectionArgs = {storeId};
        Cursor cursor = mydb.query("t_diary", cols, selection, selectionArgs, null, null, null);
        // カーソルを先頭に
        boolean isEod = cursor.moveToFirst();
        while (isEod) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String comment = cursor.getString(cursor.getColumnIndex("comment"));
            String regDate = cursor.getString(cursor.getColumnIndex("reg_date"));
            Integer seqNo = cursor.getInt(cursor.getColumnIndex("seq_no"));
            Integer evaluation = cursor.getInt(cursor.getColumnIndex("evaluation"));

            if (title != null) {
                DiaryBean diary = new DiaryBean();
                diary.setTitle(title);
                diary.setComment(comment);
                diary.setSeqNo(seqNo);
                diary.setEvaluation(evaluation);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
                // Date型変換
                Date formatDate = null;
                try {
                    formatDate = sdf.parse(regDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (formatDate != null) {
                    diary.setRegDate(formatDate);
                }
                diaries.add(diary);
            }
            isEod = cursor.moveToNext();
        }
        cursor.close();
        return diaries;
    }

    @Override
    public void onClick(View v) {

        if (bean == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.app.ladies.dailymap", "com.app.ladies.dailymap.view.regist.RegisterActivity");
        intent.putExtra("store", bean);

        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DiaryBean bean = (DiaryBean) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.setClassName("com.app.ladies.dailymap", "com.app.ladies.dailymap.view.search.SearchShopDetailActivity");
        intent.putExtra("diary", bean);

        startActivity(intent);
    }
}
