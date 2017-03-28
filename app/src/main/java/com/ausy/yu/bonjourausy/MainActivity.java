package com.ausy.yu.bonjourausy;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private Button button_roles, button_sites;
    private PopupWindow popupWindow_roles, popupWindow_sites;
    private ArrayList<String> data_roles, data_sites;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        button_roles = (Button) findViewById(R.id.button_roles);
        button_sites = (Button) findViewById(R.id.button_sites);

        // Roles
        data_roles = new ArrayList<>();
        data_roles.add("Manager");
        data_roles.add("Candidate");

        // Sites
        data_sites = new ArrayList<>();
        data_sites.add("Sèvre");
        data_sites.add("Aix-en-provence");
        data_sites.add("Bordeaux");
        data_sites.add("Brest");
        data_sites.add("Cherbourg");
        data_sites.add("Grenoble");
        data_sites.add("Lannion");
        data_sites.add("Le mans");
        data_sites.add("Lille");
        data_sites.add("Lyon");
        data_sites.add("Montpellier");
        data_sites.add("Nantes");
        data_sites.add("Nice");
        data_sites.add("Niort");
        data_sites.add("Orléans");
        data_sites.add("Pierrelotte");
        data_sites.add("Rennes");
        data_sites.add("Strasbourg");
        data_sites.add("Toulouse");
        data_sites.add("Tours");

        button_roles.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_roles = popupWindowList(data_roles, v);
                popupWindow_roles.showAsDropDown(v); // show pop up like dropdown list
            }
        });

        button_sites.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                popupWindow_sites = popupWindowList(data_sites, v);
                popupWindow_sites.showAsDropDown(v); // show pop up like dropdown list
            }
        });
    }

    private void initData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //HttpUrlConnection
                /**
                 * 1.Instance a url object
                 * 2.Obtein the HttpUrlConnection Object
                 * 3.Set the connection attributes
                 * 4.Get the Response Code to test if the connection successes or not
                 * 5.Read the InputStream and analyse it
                 */
                HttpURLConnection conn = null;
                try {
                    Log.d("msg", "New Thread");
                    //avoid creating several instances, should be singleton
                    /*
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://192.168.213.71:8080/index.php").build();
                    Response response = okHttpClient.newCall(request).execute();
                    Log.d("msg", response.body().toString());
                    */
                    URL url = new URL("http://192.168.99.100:8080/index.php"); //attribute : the link to get the data
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(6*1000);

                    //Get the Response Code to test if the connection successes or not
                    if(conn.getResponseCode() == 200) {
                        Log.d("msg", "Connection Successfully!");
                        // Get the inputStream
                        InputStream in = conn.getInputStream();
                        byte[] b = new byte[1024*512];
                        int len = 0;
                        //Construct the buffer, to store the byte array b of data just read from the inputstream
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while((len = in.read(b)) > -1) {
                            baos.write(b, 0, len);
                        }
                        String msg = baos.toString();
                        Log.d("msg", msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * show popup window method return Popupwindow
     */
    private PopupWindow popupWindowList(ArrayList<String> data, View view) {
        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        // the drop down list is a list view
        ListView dropdown_listView = new ListView(this);
        // set our adapter and pass our pop up window contents
        dropdown_listView.setAdapter(adapter);

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        popupWindow.setWidth(view.getWidth());
        popupWindow.setHeight(((View) view.getParent()).getMeasuredHeight() - view.getHeight());
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // set the list view as pop up window content
        popupWindow.setContentView(dropdown_listView);

        return popupWindow;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
