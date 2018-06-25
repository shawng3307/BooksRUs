package com.example.shawn.booksrus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import android.content.Context;
import android.widget.TextView;

public class BookAdapter extends ArrayAdapter<Event> {
    public BookAdapter(Context context, List<Event> books){
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Event currentBook = getItem(position);

        TextView title = (TextView) listItem.findViewById(R.id.book_title);
        String myTitle = currentBook.getTitle();
        title.setText(myTitle);

        return listItem;
    }


}
