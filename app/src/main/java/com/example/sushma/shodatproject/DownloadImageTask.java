package com.example.sushma.shodatproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sushma on 7/8/16.
 */


    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap imageIcon = null;
            InputStream in = null;
            try {
                URL url = new URL(urldisplay);
                URLConnection conn = url.openConnection();
                ((HttpURLConnection) conn).setInstanceFollowRedirects(false);
                URL secondURL = new URL(conn.getHeaderField("Location"));
                URLConnection secondConn = secondURL.openConnection();
                in = secondConn.getInputStream();
                imageIcon = BitmapFactory.decodeStream(in);
            }
            catch (MalformedURLException ex) {

            }
            catch (IOException ex2) {

            }

            return imageIcon;
        }

    protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }



