package com.example.archeopal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    Context context;
    ArrayList<PostDetails> list;

    public myAdapter(Context context, ArrayList<PostDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recyclerviewlayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostDetails postDetails = list.get(position);
        holder.Title.setText(postDetails.title);
        holder.Name.setText(postDetails.name);
        holder.Article.setText(postDetails.article);
        Picasso.get().load(postDetails.uri).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Name, Article;
        ImageView postImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.TextViewTitle);
            Name = itemView.findViewById(R.id.TextViewUserName);
            Article = itemView.findViewById(R.id.textViewArticle);
            postImage = itemView.findViewById(R.id.ImageViewPostImage);
        }
    }

}
