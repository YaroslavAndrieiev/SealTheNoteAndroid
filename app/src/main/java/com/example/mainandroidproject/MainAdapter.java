package com.example.mainandroidproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//STAGGERED RECYCLE ADAPTER
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TextViewHolder> {

    private Context context;
    private ArrayList<Note> notesList;
    private onNoteListener mOnNoteListener;
    private ImageButton undoBtn;
    private ImageButton btnPlus;

    //Конструктор с присвоением полей
    public MainAdapter(Context contextInp, ArrayList<Note> notesListInp, onNoteListener onNoteListenerInp, ImageButton btnPlusInp,ImageButton undoBtnInp) {
        this.context=contextInp;
        this.notesList=notesListInp;
        this.mOnNoteListener=onNoteListenerInp;
        this.undoBtn=undoBtnInp;
        this.btnPlus=btnPlusInp;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        return new TextViewHolder(view,mOnNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull final TextViewHolder holder, final int position) {

        holder.textViewTitle.setText(notesList.get(position).getTitle());
        holder.textViewNote.setText(notesList.get(position).getText());

        //Обработчик длинного нажатия на заметку
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                notesList.get(position).setEnabledState(true);
                holder.textViewTitle.setBackgroundResource(R.color.custom_blue_darker);
                holder.textViewTitle.setTextColor(Color.rgb(172,181,159));
                holder.textViewNote.setBackgroundColor(Color.GRAY);
                undoBtn.setVisibility(View.VISIBLE);
                undoBtn.animate().rotation(90F).setInterpolator(new AccelerateDecelerateInterpolator());
                btnPlus.animate().rotation(45F).setInterpolator(new AccelerateDecelerateInterpolator());
                btnPlus.setImageResource(R.drawable.ic_btn_new_red);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }


    //Класс является реализацией каждого отдельного елемента RecyclerView
    public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTitle;
        TextView textViewNote;
        onNoteListener onNoteListener;
        View currentView;

        public TextViewHolder(@NonNull View itemView,onNoteListener onNoteListenerInp) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.titleTextView);
            textViewNote = itemView.findViewById(R.id.noteTextView);
            this.onNoteListener = onNoteListenerInp;
            itemView.setOnClickListener(this);
            currentView=itemView;

        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    //Интерфейс позвалвющий MainClass "All notes" через имплеменатцию вызывать события по нажатию на существующую заметку
    public interface onNoteListener {
        void onNoteClick(int position);
    }
}
