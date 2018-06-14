package com.example.shawn.booksrus;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final String REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    public String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.text);
                result = edit.getText().toString();

                Toast toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG);
                toast.show();

                //clears Edit Text view
                edit.setText("");
            }
        });

        BookAsyncTask task = new BookAsyncTask();
        task.execute();

    }

    private class BookAsyncTask extends AsyncTask<URL, Void, Event>{
        @Override
        protected Event doInBackground(URL... urls){
            URL url = createURL(REQUEST_URL);

            String jsonResponse = "";

            try{
                jsonResponse = makeHttpRequest(url);
            }catch (IOException e){
                Log.e("error", "problem in http request");
            }

            Event book = extractFeatJson(jsonResponse);

            return book;
        }

        private URL createURL(String newURL){
            URL url = null;
            try{
                result = result.replace(' ', '+');
                String searched = newURL + result;
                url = new URL(searched);
            }catch(MalformedURLException ex){
                Log.e("error", "creating url");
                return null;
            }

            return url;
        }

        private String makeHttpRequest(URL url) throws IOException{
            String jsonResponse = "";

            if(url == null)
                return jsonResponse;

        }
    }

}
