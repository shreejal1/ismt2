package com.example.holiday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.core.Query;

public class HomePage extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ImageButton menu;
    AdapterNote adapterNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recview);
        menu = findViewById(R.id.menu);
        fab = findViewById(R.id.newnote);
        fab.setOnClickListener(v-> startActivity(new Intent(HomePage.this, NewNote.class)));

        menu.setOnClickListener(v-> showMenu());
        recviewsetup();


    }
    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(HomePage.this, menu);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomePage.this, Login.class));
                    finish();
                    return true;
                }
                return false;

            }
        });
    }

    void recviewsetup(){
        CollectionReference query = Utility.getCollecRef();
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterNote = new AdapterNote(options, this);
        recyclerView.setAdapter(adapterNote);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterNote.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterNote.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterNote.notifyDataSetChanged();
    }
}