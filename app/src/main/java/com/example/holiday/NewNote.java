package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

public class NewNote extends AppCompatActivity {

    EditText notetitle, price, notecontent;
    ImageButton savenote, deletebtn;
    ProgressBar progress;
    TextView pagetitle;
    String title, pric, content, docid;

    boolean isedit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        notetitle = findViewById(R.id.notetitle);
        price = findViewById(R.id.price);
        notecontent = findViewById(R.id.notecontent);
        savenote = findViewById(R.id.savenote);
        progress = findViewById(R.id.progress);
        pagetitle = findViewById(R.id.pagetitle);
        deletebtn = findViewById(R.id.deletebtn);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null && "text/plain".equals(type)){
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if(sharedText != null){
                notecontent.setText(sharedText);
            }
        }




        title = getIntent().getStringExtra("title");
        pric = getIntent().getStringExtra("price");
        content = getIntent().getStringExtra("content");
        docid = getIntent().getStringExtra("docid");
        if(docid!=null && !docid.isEmpty()){
            isedit = true;
        }

//        Utility.showToast(NewNote.this, "docid is "+ docid);

        notetitle.setText(title);
        price.setText(pric);
        notecontent.setText(content);

        if(isedit){
            pagetitle.setText("Edit your note");
            deletebtn.setVisibility(View.VISIBLE);
        }

        savenote.setOnClickListener(v-> saveNote());
        deletebtn.setOnClickListener(v-> deleteNote());

    }

    void saveNote(){

        String title = notetitle.getText().toString().trim();
        String pr = price.getText().toString().trim();
        String content = notecontent.getText().toString();
        String purchased = "no";

        if(title==null || title.isEmpty()){
            notetitle.setError("Specify the title");
            return;
        }


        Note note = new Note();
        note.setTitle(title);
        note.setPrice(pr);
        note.setContent(content);
        note.setPurchased(purchased);

        savetofbnote(note);
    }

    void savetofbnote(Note note){
        progress(true);
        DocumentReference documentReference;
        if(isedit){
            //update
            documentReference = Utility.getCollecRef().document(docid);
        }else{
            //new file
            documentReference = Utility.getCollecRef().document();
        }


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progress(false);
                if (task.isSuccessful()) {
                    Utility.showToast(NewNote.this, "Note is added");
                    finish();
                } else {
                    Utility.showToast(NewNote.this, "Failed to add note");
                }
            }
        });
        }

    void progress(boolean pro){
        if(pro){
            progress.setVisibility(View.VISIBLE);
            savenote.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.GONE);
            savenote.setVisibility(View.VISIBLE);
        }
    }

    void deleteNote(){
        DocumentReference documentReference;
            documentReference = Utility.getCollecRef().document(docid);


        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progress(false);
                if (task.isSuccessful()) {
                    Utility.showToast(NewNote.this, "Note is deleted");
                    finish();
                } else {
                    Utility.showToast(NewNote.this, "Failed to delete note");
                }
            }
        });
    }


}