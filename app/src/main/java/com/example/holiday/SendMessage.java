
package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import kotlinx.coroutines.channels.Send;

public class SendMessage extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    ImageButton sendmsg;
    EditText number, smscontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        number = findViewById(R.id.number);
        sendmsg = findViewById(R.id.sendmsg);

        String cont = "Title: "+getIntent().getStringExtra("title")+"\nPrice: Rs."+getIntent().getStringExtra("price")+"\nDescription: "+getIntent().getStringExtra("content");

        smscontent = findViewById(R.id.smscontent);
        smscontent.setText(cont);
        sendmsg.setOnClickListener(v-> sendSMS());

    }

    void sendSMS(){
        String con = smscontent.getText().toString();
        String numb = number.getText().toString().trim();
        if(numb.length()>10 || numb.length()<10){
            number.setError("Enter valid mobile number");
            return;
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numb, null, con, null, null);
            startActivity(new Intent(SendMessage.this, HomePage.class));
            finish();
            Toast.makeText(this, "Sent Successsfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}