package com.example.sushma.shodatproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<detailObject> list = new ArrayList<detailObject>();
    Button next;
    Button prev;
    public static boolean firstLoad = true;
    public int TOTAL_SIZE;
    public int PAGES_SIZE = 100;
    private int pageCount;
    private int increment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button getImages = (Button) findViewById(R.id.getImages);
        next = (Button) findViewById(R.id.nextButton);
        prev = (Button) findViewById(R.id.prevButton);
        getImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list.clear();
                    getImages.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    new imagesLoader().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment++;
                CheckEnable();
                if(increment >= pageCount ) {
                    CheckEnable();
                }
                else {
                    try {
                        new imagesLoader().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment--;
                CheckEnable();
                if (increment < 0 ) {
                    CheckEnable();
                } else {
                    try {
                        new imagesLoader().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CheckEnable()
    {
        if(increment+1 == pageCount)
        {
            next.setVisibility(View.GONE);
        }
        else if(increment == 0)
        {
            prev.setVisibility(View.GONE);
        }
        else
        {
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
    }

    private class imagesLoader extends AsyncTask<Void, Void, ArrayList<detailObject>> {

        @Override
        protected ArrayList<detailObject> doInBackground(Void... params) {
            if(firstLoad) {
                HttpURLConnection urlConnection = null;
                String shodatURL = "http://jsonplaceholder.typicode.com/photos";
                InputStream is = null;
                try {
                    is = (InputStream) new URL(shodatURL).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    // Log.i("test", is.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                JSONArray jArr = null;
                try {
                    jArr = getJsonfromResult(is);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject objInside = null;
                    try {
                        objInside = jArr.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        list.add(new detailObject(objInside.getInt("albumId"), objInside.getInt("id"),objInside.getString("title"),objInside.getString("url"),objInside.getString("thumbnailUrl")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.i("test", list.size() + "size");
                TOTAL_SIZE = list.size();
                int val = TOTAL_SIZE%PAGES_SIZE;
                val = val==0?0:1;
                pageCount = TOTAL_SIZE/PAGES_SIZE+val;
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<detailObject> list) {
            super.onPostExecute(list);
            imagefragment imageFragment = new imagefragment();
            Bundle data = new Bundle();
            ArrayList<detailObject> l = new ArrayList<detailObject>();
            int start = increment * PAGES_SIZE;
            for(int i=start;i<(start)+PAGES_SIZE;i++)
            {
                l.add(list.get(i));
            }
            data.putParcelableArrayList("list", l);
            imageFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, imageFragment).
                    addToBackStack("list_view_frag").
                    commit();
        }


        private JSONArray getJsonfromResult(InputStream inputStream) throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while((line = br.readLine()) != null) {
                   //Log.i("test", line);
                    sb.append(line)
                            .append("\n");
                }

                return new JSONArray(sb.toString());
            } catch (Exception e) {
                throw e;
            }
            finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
