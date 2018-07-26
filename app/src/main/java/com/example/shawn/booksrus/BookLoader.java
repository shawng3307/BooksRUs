package com.example.shawn.booksrus;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Event>> {
    private String myURL;

    public BookLoader(Context context, String url) {
        super(context);
        myURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Event> loadInBackground() {
        if (myURL == null)
            return null;

        List<Event> books = Query.fetchBookData(myURL);

        return books;
    }
}
