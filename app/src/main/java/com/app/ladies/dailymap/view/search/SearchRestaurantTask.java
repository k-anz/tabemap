package com.app.ladies.dailymap.view.search;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.app.ladies.dailymap.view.model.RestaurantBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Kyoko1 on 2016/07/01.
 */
public class SearchRestaurantTask extends AsyncTask<Pair<String, String>, Void, List<RestaurantBean>> {
    public static final String API_KEY = "9eeeaa20a6b03eac626beb6c2eb6a3ad";
    public final Uri API_URI = Uri.parse("http://api.gnavi.co.jp/RestSearchAPI/20150630/?format=json");


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
            JSONArray restArray = response.getJSONArray("rest");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(restArray.toString(), new TypeReference<List<RestaurantBean>>(){});

        } catch (IOException | JSONException e) {
            // TODO
            Log.e("error", e.getMessage());
        }
        return null;

    }
}
