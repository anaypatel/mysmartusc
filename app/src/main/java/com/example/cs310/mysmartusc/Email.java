package com.example.cs310.mysmartusc;

import java.math.BigInteger;

public class Email implements Comparable {
    private String subject, body, sender;
    long date;

    public Email(){
        this.subject = "";
        this.body = "";
        this.sender = "";
        this.date = 0;
    }

    public Email(String subject, String body, String sender, long date){
        this.subject = subject;
        this.sender = sender;
        this.body = body;
        this.date = date;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getBody(){
        return this.body;
    }

    public String getSender(){
        return this.sender;
    }

    public long getDate(){
        return this.date;
    }

    @Override
    public int compareTo(Object other) {
        long otherDate=((Email)other).getDate();
        /* For Ascending order*/
        return (int) (otherDate-this.date);

    }
}
