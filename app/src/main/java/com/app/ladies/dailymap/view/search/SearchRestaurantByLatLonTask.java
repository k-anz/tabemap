package com.app.ladies.dailymap.view.search;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import com.app.ladies.dailymap.view.model.RestaurantBean;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import db.MySQLiteOpenHelper;

/**
 * Created by Kyoko1 on 2016/07/01.
 */
public class SearchRestaurantByLatLonTask extends SearchRestaurantTask {
    /** DB */
    private static SQLiteDatabase mydb;

    private WeakReference<GoogleMap> mMapWeakReference;
    private WeakReference<SearchMapActivity> mActivityWeakReference;

    private List<String> registeredStoreIds = new ArrayList<>();

    public static final String RANGE = "5";


    public SearchRestaurantByLatLonTask(WeakReference<GoogleMap> mapWeakReference,
                                        WeakReference<SearchMapActivity>  activityWeakReference ) {
        mMapWeakReference = mapWeakReference;
        mActivityWeakReference = activityWeakReference;
    }

    @Override
    protected List<RestaurantBean> doInBackground(Pair<String, String>... params) {

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mActivityWeakReference.get().getApplication());
        mydb = helper.getReadableDatabase();
        Cursor cursor = mydb.query("t_diary", new String[]{"store_id"},null, null, null, null, "store_id DESC");

        // カーソルを先頭に
        boolean isEod = cursor.moveToFirst();
        while(isEod) {
            String storeId = cursor.getString(cursor.getColumnIndex("store_id"));
            if (storeId != null) {
                registeredStoreIds.add(storeId);
            }
            isEod = cursor.moveToNext();
        }
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(List<RestaurantBean> restaurantBeans) {
        super.onPostExecute(restaurantBeans);

        if (restaurantBeans == null) {
            return;
        }
        GoogleMap map = mMapWeakReference.get();

        for (RestaurantBean bean : restaurantBeans) {
            double lat = Double.parseDouble(bean.getLatitude());
            double lon = Double.parseDouble(bean.getLongitude());

            LatLng latLng = new LatLng(lat, lon);
            MarkerOptions marker = new MarkerOptions();
            marker.position(latLng);
            marker.title(bean.getStoreName());
            marker.snippet(bean.getStoreId() + ":" + bean.getAddress());

            // 登録済みの店のみマーカの色を変更
            if (registeredStoreIds.contains(bean.getStoreId())) {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }

            map.addMarker(marker);
        }
        map.setOnInfoWindowClickListener(mActivityWeakReference.get());
    }
}

