package com.example.shawn.booksrus;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Event>>{
    public String REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    private String result;
    private String search;
    private static final int BOOK_LOADER_ID = 1;
    private BookAdapter myAdapter;
    private TextView myEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.list);
        myEmptyStateTextView = (TextView) findViewById((R.id.empty_view));
        bookListView.setEmptyView(myEmptyStateTextView);

        myAdapter = new BookAdapter(this, new ArrayList<Event>());
        bookListView.setAdapter(myAdapter);

        Button searchButton = (Button) findViewById(R.id.search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.text);
                result = edit.getText().toString();
                result = result.replace(' ', '+');

                search = REQUEST_URL + result;

                clicked();

                //clears Edit Text view
                edit.setText("");
            }
        });



        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderMan = getLoaderManager();
            loaderMan.initLoader(BOOK_LOADER_ID, null, this);

        }else{
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            myEmptyStateTextView.setText("No internet connection");
        }


    }
    public void clicked(){
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, this);
    }
    
    @Override
    public Loader<List<Event>> onCreateLoader(int i, Bundle bundle){

        return new BookLoader(this, search);
    }
    @Override
    public void onLoadFinished(Loader<List<Event>> loader,List<Event> books){
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        myEmptyStateTextView.setText("No Books Found");

        myAdapter.clear();

        if (books != null && !books.isEmpty()) {
            myAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader){
        myAdapter.clear();
    }

}
