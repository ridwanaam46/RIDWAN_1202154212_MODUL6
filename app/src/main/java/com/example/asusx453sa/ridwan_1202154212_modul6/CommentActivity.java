package com.example.asusx453sa.ridwan_1202154212_modul6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CommentActivity extends RecyclerView.Adapter<CommentActivity.ViewHolder> {
    Context context;
    List<Comment> commentList;
    RoundedBitmapDrawable rBD;

    public CommentActivity(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvusername;
        TextView tvcomment;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvusername = itemView.findViewById(R.id.username_comment);
            tvcomment = itemView.findViewById(R.id.comment_activity);
            imageView = itemView.findViewById(R.id.image_comment);
        }
    }

    @NonNull
    @Override
    public CommentActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Comment comment = commentList.get(position);
        holder.tvusername.setText(comment.getUsername());
        holder.tvcomment.setText(comment.getComment());
        holder.imageView.setImageResource(R.mipmap.ic_user);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }



}

