package com.app.ladies.dailymap.view.search;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;



public class SearchMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG_MAP_FRAGMENT = "MAP_FRAGMENT";
    private MapFragment mMapFragment;
    private final LatLng SHINJUKU = new LatLng(35.691638, 139.704616);
    private static final float ZOOM_LV = 17;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mMapFragment = (MapFragment) getFragmentManager().findFragmentByTag(TAG_MAP_FRAGMENT);
        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
            getFragmentManager().beginTransaction().add(mMapFragment, TAG_MAP_FRAGMENT).commit(); // TODO 引数3つのにした方が良い？
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); //低い精度
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低い消費電力

        int permission = getPackageManager().checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, getPackageName());
        String provider = locationManager.getBestProvider(criteria, true);

        googleMap.setOnCameraChangeListener(new SearchMapChangeListener(googleMap, this));

        if (permission == PackageManager.PERMISSION_GRANTED) {
            Location myLocate = locationManager.getLastKnownLocation(provider);
            googleMap.setMyLocationEnabled(true);

            if (myLocate != null) {
                LatLng myLocakteLatLng = new LatLng(myLocate.getLatitude(), myLocate.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocakteLatLng, ZOOM_LV));
            } else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SHINJUKU, ZOOM_LV));
            }
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SHINJUKU, ZOOM_LV));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent();
        intent.setClassName("com.app.ladies.dailymap", "com.app.ladies.dailymap.view.detail.ShopDetailActivity");

        // TODO なんとか綺麗な形でIDを渡す。
        String snippet = marker.getSnippet();
        String storeId = snippet.split(":")[0];

        intent.putExtra("id", storeId);
        startActivity(intent);
    }
}

