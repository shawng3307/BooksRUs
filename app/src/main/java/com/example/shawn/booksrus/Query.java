package com.example.shawn.booksrus;

import android.text.TextUtils;
import android.util.Log;

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

public class Query {
    private Query(){

    }

    public static ArrayList<Event> fetchBookData(String reqUrl){
        URL url = createURL(reqUrl);
        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e("error", "problem in http request");
        }

        ArrayList<Event> book = extractFeatJson(jsonResponse);

        return book;
    }

    private static URL createURL(String newURL){
        URL url = null;
        try{
            url = new URL(newURL);
        }catch(MalformedURLException ex){
            Log.e("error", "creating url");
        }

        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("error", "code: " + urlConnection.getResponseCode());

            }

        }catch(IOException e){
            Log.e("error", "Problem retrieving results");
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static ArrayList<Event> extractFeatJson(String respJSON){
        if(TextUtils.isEmpty(respJSON)){
            return null;
        }

        ArrayList<Event> books = new ArrayList<Event>();

        try{
            JSONObject baseResponse = new JSONObject(respJSON);
            JSONArray itemArray = baseResponse.getJSONArray("items");


            if(itemArray.length() > 0){
                for(int i = 0; i < itemArray.length(); i++){
                    JSONObject myItem = itemArray.getJSONObject(i);
                    JSONObject volInfo = myItem.getJSONObject("volumeInfo");

                    String title = volInfo.getString("title");
                    Log.d("Retrieving: ", title);
                    Event nextEvent = new Event(title);
                    books.add(nextEvent);
                }

                /*JSONArray authorArray = firstItem.getJSONArray("authors");
                if(authorArray.length() > 0){
                    String author = authorArray.getString(0);

                    return new Event(title, author);
                }*/
            }
        }catch (JSONException e){
            Log.e("Error", " Problem parsing JSON");
        }

        return books;
    }
}
