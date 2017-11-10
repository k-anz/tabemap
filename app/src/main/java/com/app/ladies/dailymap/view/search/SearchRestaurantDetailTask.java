package com.app.ladies.dailymap.view.search;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ladies.dailymap.R;
import com.app.ladies.dailymap.view.detail.ImgAsyncTask;
import com.app.ladies.dailymap.view.detail.ShopDetailActivity;
import com.app.ladies.dailymap.view.model.ImageUrlBean;
import com.app.ladies.dailymap.view.model.RestaurantBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyoko1 on 2016/10/27.
 */
public class SearchRestaurantDetailTask extends SearchRestaurantTask {

    WeakReference<ShopDetailActivity> mActivityWeakReference;

    public SearchRestaurantDetailTask(WeakReference<ShopDetailActivity> activityWeakReference) {
        mActivityWeakReference = activityWeakReference;
    }



    @Override
    protected List<RestaurantBean> doInBackground(Pair<String, String>... params) {
        Uri.Builder accessUrl = API_URI.buildUpon();
        accessUrl.appendQueryParameter("keyid", API_KEY);

        // build query parameter
        for (Pair<String, String> queryParam : params) {
            accessUrl.appendQueryParameter(queryParam.first, queryParam.second);
        }

        try {
            java.net.URL restSearchURL = new URL(accessUrl.build().toString());
            HttpURLConnection http = (HttpURLConnection)restSearchURL.openConnection();
            http.setRequestMethod("GET");

            Log.i("connected", accessUrl.toString());
            http.connect();

            InputStream in = http.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            Log.i("response", responseStrBuilder.toString()); // TODO delete
            JSONObject response = new JSONObject(responseStrBuilder.toString());
            JSONObject rest = response.getJSONObject("rest");
            ObjectMapper mapper = new ObjectMapper();
            RestaurantBean bean = mapper.readValue(rest.toString(), RestaurantBean.class);

            String imgUrl = rest.getJSONObject("image_url").getString("shop_image1");
            ImageUrlBean imgBean = new ImageUrlBean();
            imgBean.setShopImage1(imgUrl);
            bean.setImgUrl(imgBean);

            List<RestaurantBean> restaurantBeanList = new ArrayList<>();
            restaurantBeanList.add(bean);
            return restaurantBeanList;

        } catch (IOException | JSONException e) {
            // TODO
            Log.e("error", e.getMessage());
        }
        return null;

    }

    @Override
    protected void onPostExecute(List<RestaurantBean> restaurantBeans) {
        super.onPostExecute(restaurantBeans);

        ShopDetailActivity activity = mActivityWeakReference.get();

        TextView shopName = (TextView) activity.findViewById(R.id.shopName);
        TextView address = (TextView) activity.findViewById(R.id.address);

        if (restaurantBeans == null || restaurantBeans.size() == 0) {
            shopName.setText("お店がみつかりませんでした。");
        } else {
            RestaurantBean rest = restaurantBeans.get(0);
            activity.bean = rest; // Activityに保存

            shopName.setText(rest.getStoreName());
            address.setText(rest.getAddress());

            ImageView shopImage = (ImageView) activity.findViewById(R.id.shopImage);
            if (rest.getImgUrl() != null) {
                String url = rest.getImgUrl().getShopImage1();
                if (url != null) {
                    new ImgAsyncTask(shopImage).execute(Uri.parse(url).buildUpon());
                }
            } else {
                // TODO "no image"画像つくる
            }
        }



    }
}
