package com.app.ladies.dailymap.view.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.model.DiaryBean;

import java.util.List;

/**
 * Created by Kyoko1 on 2017/03/23.
 */

public class DiaryListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    List<DiaryBean> mDiaryBeanList;

    public DiaryListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDiaryList(List<DiaryBean> diaryBeanList) {
        this.mDiaryBeanList = diaryBeanList;
    }

    @Override
    public int getCount() {
        return mDiaryBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDiaryBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_diary_item,parent,false);

        DiaryBean diaryElem = mDiaryBeanList.get(position);
        ((TextView)convertView.findViewById(R.id.diary_title)).setText(diaryElem.getTitle());
        ((TextView)convertView.findViewById(R.id.diary_date)).setText(diaryElem.getRegDate().toString());
        ((TextView)convertView.findViewById(R.id.diary_content)).setText(diaryElem.getComment());

        return convertView;
    }
}
