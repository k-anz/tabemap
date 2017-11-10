package com.app.ladies.dailymap.view.regist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.BaseActivity;
import com.app.ladies.dailymap.view.model.RestaurantBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import db.MySQLiteOpenHelper;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    /** DB */
    private static SQLiteDatabase mydb;
    /** レストラン情報のオブジェクト */
    private RestaurantBean bean;
    /** 店名 */
    private TextView mNameEdit;
    /** 住所 */
    private TextView mAddressEdit;
    /** コメント */
    private EditText mCommentEdit;
    /** 登録ボタン */
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bean = (RestaurantBean) intent.getSerializableExtra("store");

        String name = bean.getStoreName();
        String address = bean.getAddress();

        mNameEdit = (TextView) findViewById(R.id.storeName);
        mAddressEdit = (TextView) findViewById(R.id.address);
        mCommentEdit = (EditText) findViewById(R.id.comment);
        mRegisterButton  = (Button) findViewById(R.id.registerButton);

        mNameEdit.setText(name);
        mAddressEdit.setText(address);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
        mydb = helper.getWritableDatabase();

        Cursor cursor = mydb.query("t_diary", new String[]{"store_id", "seq_no"},null, null, null, null, "store_id DESC");

        // カーソルを先頭に
        List<Integer> seqNoList = new ArrayList<>();
        boolean isEod = cursor.moveToFirst();
        while(isEod) {
            Integer seqNo = cursor.getInt(cursor.getColumnIndex("seq_no"));
            seqNoList.add(seqNo);
            isEod = cursor.moveToNext();
        }
        Integer maxSeqNo = -1;
        for (Integer seqNo : seqNoList) {
            maxSeqNo = seqNo > maxSeqNo ? seqNo : maxSeqNo;
        }

        ContentValues value = new ContentValues();
        value.put("store_id", bean.getStoreId());
        value.put("seq_no", maxSeqNo + 1);

        EditText titleEdit = (EditText) findViewById(R.id.title);
        value.put("title", titleEdit.getText().toString());

        value.put("comment", mCommentEdit.getText().toString());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rate);
        value.put("evaluation", ratingBar.getNumStars());

        value.put(getString(R.string.dateformat), getYmdDate());

        Long result = mydb.insert("t_diary", null, value);
        if (result == -1) {
            // エラー処理
            Log.e("error", "insert error");
        }

        Intent intent = new Intent();
        intent.setClassName("com.app.ladies.dailymap", "com.app.ladies.dailymap.view.search.SearchMapActivity");
        startActivity(intent);

        cursor.close();
    }

    /**
     * DB登録用の日付を取得する
     * @return yyyy/MM/dd形式の日付
     */
    private String getYmdDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
        return sdf.format(cal.getTime());
    }
}
