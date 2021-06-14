package com.example.btlandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteItemsRecyclerView extends RecyclerView.Adapter {

    List<NoteModel> noteModelList;
    NoteListener noteListener;


    public NoteItemsRecyclerView(List<NoteModel> noteModels, NoteListener noteListener){
        this.noteModelList=noteModels;
        this.noteListener = noteListener;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView noteText,create_time;
        public CardView card_item;
        public RelativeLayout note_parent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText=itemView.findViewById(R.id.notetext);
            create_time=itemView.findViewById(R.id.time);
            note_parent=itemView.findViewById(R.id.note_parent);
            card_item=itemView.findViewById(R.id.card_item);
        }
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_display,null);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NoteViewHolder noteViewHolder= (NoteViewHolder) holder;
        noteViewHolder.noteText.setText(noteModelList.get(position).getNote_content());
        noteViewHolder.create_time.setText(noteModelList.get(position).getCreated_at());



        noteViewHolder.note_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteListener.onClickItem(noteModelList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }
}
