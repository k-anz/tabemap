package com.app.ladies.dailymap.view.search;

import android.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.VisibleRegion;

import java.lang.ref.WeakReference;

/**
 * Mapのカメラ位置が変更された場合のリスナ
 * Created by Kyoko1 on 2016/02/25.
 */
public class SearchMapChangeListener implements GoogleMap.OnCameraChangeListener {

    private WeakReference<SearchMapActivity> mActivityWeakReference;
    private WeakReference<GoogleMap> mMapWeakReference;

    public SearchMapChangeListener(GoogleMap map, SearchMapActivity activity) {
        mMapWeakReference = new WeakReference<>(map);
        mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        Projection proj = mMapWeakReference.get().getProjection();
        VisibleRegion vRegion = proj.getVisibleRegion();


        double latitude = vRegion.latLngBounds.getCenter().latitude;
        double longitude = vRegion.latLngBounds.getCenter().longitude;

        Pair<String,String> rangeSetting = new Pair<>("range", "5");
        Pair<String,String> lat = new Pair<>("latitude", Double.toString(latitude));
        Pair<String,String> lon = new Pair<>("longitude", Double.toString(longitude));

        new SearchRestaurantByLatLonTask(mMapWeakReference, mActivityWeakReference).execute(rangeSetting, lat, lon);
    }
}
