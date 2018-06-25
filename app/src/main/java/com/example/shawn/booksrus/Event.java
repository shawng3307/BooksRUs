package com.example.shawn.booksrus;

public class Event {
    private final String title;
    private final String author;

    public Event(String bookTitle){
        title = bookTitle;
        author = "";
    }
    public Event(String bookTitle, String bookAuthor){
        title = bookTitle;
        author = bookAuthor;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }


}
