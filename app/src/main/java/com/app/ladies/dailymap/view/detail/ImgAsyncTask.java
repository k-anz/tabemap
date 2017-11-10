package com.app.ladies.dailymap.view.detail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 画像の取得を行うためのAsyncTask
 * Created by Kyoko1 on 2016/11/01.
 */
public class ImgAsyncTask extends AsyncTask<Uri.Builder, Void, Bitmap> {
    private ImageView imageView;

    public ImgAsyncTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Uri.Builder... builder) {

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try {

            URL url = new URL(builder[0].toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException exception) {
            // TODO
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException exception) {
                // TODO
            }
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // インターネット通信して取得した画像をImageViewにセットする
        this.imageView.setImageBitmap(result);
    }
}
