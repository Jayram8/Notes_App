package com.example.notesapp;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCreateNote;
    FloatingActionButton fabAdd;
    RecyclerView rvNotes;
    DatabaseHelper databaseHelper;
    LinearLayout  llNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVar();

        showNotes();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();

            }
        });

        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();

            }
        });

        rvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAdd.performClick();
            }
        });
    }

    private void createNote(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_note_lay);

        EditText edtTitle, edtContent;
        Button btnAdd;

        edtTitle = dialog.findViewById(R.id.edtTitle);
        edtContent = dialog.findViewById(R.id.edtContent);
        btnAdd = dialog.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();

                if(!content.equals("")){

                    databaseHelper.noteDao().addNote(new Note(title, content));
                    showNotes();

                    dialog.dismiss();

                }
                else{
                    Toast.makeText(MainActivity.this, "Please Enter a value ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void showNotes() {

        ArrayList<Note> arrNotes = (ArrayList<Note>) databaseHelper.noteDao().getNotes();

        if(arrNotes.size()>0){
            rvNotes.setVisibility(View.VISIBLE);
            llNotes.setVisibility(View.GONE);

            rvNotes.setAdapter(new RecyclerNotesAdapter(this, arrNotes, databaseHelper));
        }
        else{
            llNotes.setVisibility(View.VISIBLE);
            rvNotes.setVisibility(View.GONE);
        }
    }

    private void initVar() {
        btnCreateNote = findViewById(R.id.btnCreateNote);
        fabAdd = findViewById(R.id.fabAdd);
        rvNotes = findViewById(R.id.rvNotes);
        llNotes = findViewById(R.id.llNotes);

        rvNotes.setLayoutManager(new GridLayoutManager(this, 2));

        databaseHelper = DatabaseHelper.getInstance(this);
    }
}