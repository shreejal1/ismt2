
package com.example.holiday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class SendMessage extends AppCompatActivity {
    ImageButton sendmsg;
    EditText number, smscontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        number = findViewById(R.id.number);
        sendmsg = findViewById(R.id.sendmsg);



        smscontent = findViewById(R.id.smscontent);






    }
}