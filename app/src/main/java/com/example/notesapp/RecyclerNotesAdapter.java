package com.example.notesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerNotesAdapter extends RecyclerView.Adapter<RecyclerNotesAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> arrNotes = new ArrayList<>();
    DatabaseHelper databaseHelper;

    RecyclerNotesAdapter(Context context, ArrayList<Note> arrNotes, DatabaseHelper databaseHelper){
        this.context = context;
        this.arrNotes = arrNotes;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(arrNotes.get(i).getTitle());
        viewHolder.txtContent.setText(arrNotes.get(i).getContent());

        viewHolder.llrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(i);

                return true;
            }
        });
    }


    @Override
    public int getItemCount() {return arrNotes.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtContent;
        LinearLayout llrow;
        public ViewHolder (@NonNull View itemView){
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            llrow = itemView.findViewById(R.id.llRow);

        }
    }

    public void deleteItem(int i){
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure want to Delete ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.noteDao().deleteNote(new Note(arrNotes.get(i).getId(),arrNotes.get(i).getTitle(),
                                arrNotes.get(i).getContent()));

                        ((MainActivity)context).showNotes();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
