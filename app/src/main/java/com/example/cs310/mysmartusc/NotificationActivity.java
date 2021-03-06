package com.example.cs310.mysmartusc;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class NotificationActivity extends Activity {

    Spinner dropdown;
    private EditText searchNotifications;
    Button saved;
    Button urgent;
    Button spam;
    Button news;
    DatabaseInterface mDatabaseInterface;
    String mUsername;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        saved = (Button) findViewById(R.id.savedButton);
        urgent = (Button) findViewById(R.id.urgentButton);
        spam = (Button) findViewById(R.id.spamButton);
        news = (Button) findViewById(R.id.newsButton);
        mDatabaseInterface = DatabaseInterface.getInstance(this);
        Account account = (Account)getIntent().getParcelableExtra("account");
        mUsername = account.name;

        updateButtons();

        //initialize the EditText field to search for email notifications
        searchNotifications = (EditText) findViewById(R.id.searchNotifications);


        dropdown = findViewById(R.id.numberEmails);
        String[] items = new String[]{"Number of Emails to View", "5", "10", "15", "All"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numEmails = dropdown.getSelectedItem().toString();
                Intent saveIntent = new Intent(NotificationActivity.this, SaveActivity.class);
                saveIntent.putExtra("account", (Parcelable) getIntent().getParcelableExtra("account"));
                saveIntent.putExtra("numEmails", numEmails);
                saveIntent.putExtra("search", searchNotifications.getText().toString());
                startActivity(saveIntent);
            }
        });

        urgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numEmails = dropdown.getSelectedItem().toString();
                Intent urgentIntent = new Intent(NotificationActivity.this, UrgentActivity.class);
                urgentIntent.putExtra("account", (Parcelable) getIntent().getParcelableExtra("account"));
                urgentIntent.putExtra("numEmails", numEmails);
                Log.e("NotifActSearch", "Sending search String " + searchNotifications.getText().toString());
                urgentIntent.putExtra("search", searchNotifications.getText().toString());
                startActivity(urgentIntent);
            }
        });

        spam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numEmails = dropdown.getSelectedItem().toString();
                Intent spamIntent = new Intent(NotificationActivity.this, SpamActivity.class);
                spamIntent.putExtra("account", (Parcelable) getIntent().getParcelableExtra("account"));
                spamIntent.putExtra("numEmails", numEmails);
                spamIntent.putExtra("search", searchNotifications.getText().toString());
                startActivity(spamIntent);
            }
        });

        news.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String numEmails = dropdown.getSelectedItem().toString();
                Intent newsIntent = new Intent(NotificationActivity.this, NewsletterActivity.class);
                newsIntent.putExtra("account", (Parcelable) getIntent().getParcelableExtra("account"));
                newsIntent.putExtra("numEmails", numEmails);
                newsIntent.putExtra("search", searchNotifications.getText().toString());
                startActivity(newsIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtons();
    }

    public void updateButtons() {
        urgent.setText("URGENT (" + mDatabaseInterface.getUnreadEmailsByType(mUsername, "urgent").getCount() + ")");
        spam.setText("SPAM (" + mDatabaseInterface.getUnreadEmailsByType(mUsername, "spam").getCount() + ")");
        saved.setText("SAVED (" + mDatabaseInterface.getUnreadEmailsByType(mUsername, "saved").getCount() + ")");
        news.setText("NEWSLETTERS (" + mDatabaseInterface.getUnreadEmailsByType(mUsername, "news").getCount() + ")");
    }
    
 }
